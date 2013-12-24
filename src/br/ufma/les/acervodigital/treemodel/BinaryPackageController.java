package br.ufma.les.acervodigital.treemodel;

import java.sql.SQLException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
 
 
public class BinaryPackageController extends SelectorComposer<Component> {
 
    private static final long serialVersionUID = 43014628867656917L;
    private AcervoDigitalFachada acervoDigitalFachada;
    
	public TreeModel<TreeNode<PackageData>> getTreeModel() {
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        PackageDataUtil pk = new PackageDataUtil();
        try {
			pk.montaArvore(acervoDigitalFachada.retornaCaminhoDiretorioRaiz());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return new DefaultTreeModel<PackageData>(pk.getRoot());
    }
}
