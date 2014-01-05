package br.ufma.les.acervodigital.window;

import java.sql.SQLException;

import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.PackageData;
import br.ufma.les.acervodigital.treemodel.PackageDataUtil;

public class GerenciarDiretoriosDocumentosWindow extends Window{
	
private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private TreeModel<TreeNode<PackageData>> arvore;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        binder.loadAll();
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        PackageDataUtil pk = new PackageDataUtil();
        try {
			pk.montaArvore(acervoDigitalFachada.retornaCaminhoDiretorioRaiz());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	arvore = new DefaultTreeModel<PackageData>(pk.getRoot());

        binder.loadAll();
    }

	public TreeModel<TreeNode<PackageData>> getArvore() {
		return arvore;
	}

	public void setArvore(TreeModel<TreeNode<PackageData>> arvore) {
		this.arvore = arvore;
	}
	
	

}
