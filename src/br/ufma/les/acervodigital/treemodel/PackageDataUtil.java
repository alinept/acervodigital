package br.ufma.les.acervodigital.treemodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import br.ufma.les.acervodigital.dao.ArquivoDocumentoDAO;
import br.ufma.les.acervodigital.dao.ArquivoDocumentoDAOImpl;
import br.ufma.les.acervodigital.dao.DiretorioDAO;
import br.ufma.les.acervodigital.dao.DiretorioDAOImpl;
import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Diretorio;

public class PackageDataUtil {
	
    private DiretorioTreeNode<PackageData> root;
      
    public void montaArvore(int diretorio) throws SQLException, Exception
    {
    	
	  final DiretorioDAO d = new DiretorioDAOImpl();
	  final ArquivoDocumentoDAO a = new ArquivoDocumentoDAOImpl();
	  final List<Diretorio> colecao; 
	  
	  if(diretorio == 0)
	  {
		colecao =  d.carregarDiretoriosRoot();
	  }else{
		List<Diretorio> l = new ArrayList<Diretorio>();
		l.add(d.findDiretorioByCodigo(diretorio));
		colecao = l;
	  }
    	
    	if(root == null)

		{
			root = new DiretorioTreeNode<PackageData>(null,
	                new DiretorioTreeNodeCollection<PackageData>() {
	                    private static final long serialVersionUID = 9019022379404376015L;
	 
	                    {   
	                    	
	                    	for(Diretorio dir:colecao){
		                    	List<Diretorio> colecaoFilhos = d.carregarFilhos(dir.getId());	
		                    	List<ArquivoDocumento> colecaoArquivos = a.carregaArquivos(dir.getId());
		                    	
		                        add(new DiretorioTreeNode<PackageData>(new PackageData(
		                                dir.getName(), ""+dir.getDataCriacao(), dir.getProprietario().getNome()),carregaColecao(colecaoFilhos)));
		                        for(ArquivoDocumento arquivo : colecaoArquivos){
		                    		add(new DiretorioTreeNode<PackageData>(new PackageData(arquivo.getNomeArquivo()
	                                ,"" , "")));
		                    		
		                    	}
	                    	}
	                    	
	                    }
	                }, true); // dist opened
		}
    }
    
    public DiretorioTreeNodeCollection<PackageData> carregaColecao(final List<Diretorio> colecao) throws Exception{
    	
    	 final DiretorioDAO d= new DiretorioDAOImpl();
    	 final ArquivoDocumentoDAO a = new ArquivoDocumentoDAOImpl();
    	 DiretorioTreeNodeCollection<PackageData> root=null;
    	 
    	 root = new DiretorioTreeNodeCollection<PackageData>() {
             private static final long serialVersionUID = 9019022379404376015L;

             {   
             	for(Diretorio dir : colecao){
             	List<Diretorio> colecaoFilhos = d.carregarFilhos(dir.getId());
             	List<ArquivoDocumento> colecaoArquivos = a.carregaArquivos(dir.getId());
            	for(ArquivoDocumento arquivo : colecaoArquivos){
            		add(new DiretorioTreeNode<PackageData>(new PackageData(arquivo.getNomeArquivo()
                    ,"" , "")));
            		
            	}
                 add(new DiretorioTreeNode<PackageData>(new PackageData(
                         dir.getName(), ""+dir.getDataCriacao(), dir.getProprietario().getNome()),carregaColecao(colecaoFilhos)));
             	}
             }
         };
    	 
    	
    	
    	return root;
    }
    
//    public void addFilhos(ObjectSql obj,List<ObjectSql> estrutura, int posicao)
//    {
//    	for(int i = posicao; i<estrutura.size();i++)
//    	{
//    		if(!(estrutura.get(i).nivel == obj.nivel + 1)) break;
//    	}
//    }
    
    
    
    public void setRoot(DiretorioTreeNode<PackageData> root) {
		this.root = root;
	}

	public DiretorioTreeNode<PackageData> getRoot() {
        return root;
    }
}
