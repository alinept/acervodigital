package br.ufma.les.acervodigital.treemodel;

import java.util.List;

public class PackageDataUtil {
    private static DiretorioTreeNode<PackageData> root;
    
    
    public void montaArvore(final List<ObjectSql> estrutura)
    {
    	if(root == null)
    	{
    		root = new DiretorioTreeNode<PackageData>(null,
                    new DiretorioTreeNodeCollection<PackageData>() {
                        private static final long serialVersionUID = 9019022379404376015L;
     
                        {
                            add(new DiretorioTreeNode<PackageData>(new PackageData(
                                    estrutura.get(0).getCaminhoDiretorio(), "")));
                        }
                    }, true); // dist opened
    	}
    }
    
    
 
    public static DiretorioTreeNode<PackageData> getRoot() {
        return root;
    }
}
