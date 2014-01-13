package br.ufma.les.acervodigital.window;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.ObjectSql;
import br.ufma.les.acervodigital.treemodel.PackageData;
import br.ufma.les.acervodigital.treemodel.PackageDataUtil;

import com.ibm.icu.util.GregorianCalendar;


public class DiretorioWindow extends Window {
	
	
	public Window window;
	private DataBinder binder;
	public String path;
	private AcervoDigitalFachada acervoDigitalFachada;
	private TreeModel<TreeNode<PackageData>> arvore;
	private Diretorio diretorio;
	private Diretorio diretorioPai;
	public int idDiretorio=-1;
	private Date data;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        
        diretorio = new Diretorio();
        
        GregorianCalendar gc = new GregorianCalendar();
        
        data = gc.getTime();
        
        PackageDataUtil pk = new PackageDataUtil();
        try {
        	if(verificaPath(path)){
        		String [] diretorios = path.split("/");
        		
        		pk.montaArvore(idDiretorio);
        	}
        	else{
        		Messagebox.show("false");
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	arvore = new DefaultTreeModel<PackageData>(pk.getRoot());
        binder.loadAll();
    }
	
	public boolean verificaPath(String path) throws SQLException, Exception{
		
		List<ObjectSql> diretorios = acervoDigitalFachada.findAllDiretorios();
		
		for(ObjectSql d : diretorios){
			if(d.getCaminhoDiretorio().equals(path)){
				setIdDiretorio(d.getCodigo());
				return true;
			}
		}
		
		return false;
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	} 
  
    public void print(String s){
    	Messagebox.show(s);
    }

	public TreeModel<TreeNode<PackageData>> getArvore() {
		return arvore;
	}

	public void setArvore(TreeModel<TreeNode<PackageData>> arvore) {
		this.arvore = arvore;
	}

	public Diretorio getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}

	public Diretorio getDiretorioPai() {
		return diretorioPai;
	}

	public void setDiretorioPai(Diretorio diretorioPai) {
		this.diretorioPai = diretorioPai;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getIdDiretorio() {
		return idDiretorio;
	}

	public void setIdDiretorio(int idDiretorio) {
		this.idDiretorio = idDiretorio;
	}
	
}
