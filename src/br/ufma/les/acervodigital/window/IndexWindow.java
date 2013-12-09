package br.ufma.les.acervodigital.window;

import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Window;


public class IndexWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        binder.loadAll();
      
    }

}
