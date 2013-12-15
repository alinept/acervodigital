package br.ufma.les.acervodigital.treemodel;

import org.zkoss.zul.DefaultTreeNode;

public class DiretorioTreeNode<T> extends DefaultTreeNode<T>  {

	private static final long serialVersionUID = 1L;
	// Node Control the default open

	private boolean open = false;
 
    public DiretorioTreeNode(T data, DiretorioTreeNodeCollection<T> children, boolean open) {
        super(data, children);
        this.setOpen(open);
    }
 
    public DiretorioTreeNode(T data, DiretorioTreeNodeCollection<T> children) {
        super(data, children);
    }
 
    public DiretorioTreeNode(T data) {
        super(data);
    }
 
    public boolean isOpen() {
        return open;
    }
 
    public void setOpen(boolean open) {
        this.open = open;
    }
}
