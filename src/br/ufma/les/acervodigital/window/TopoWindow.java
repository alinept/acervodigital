package br.ufma.les.acervodigital.window;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class TopoWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private Usuario usuario;
	private AcervoDigitalFachada acervoDigitalFachada;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        binder.loadAll();
        usuario = new Usuario();
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        
        usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
        
        if(usuario == null)
        {
        	Messagebox.show("É necessário efetuar login para acessar esta página" 
					,"Usuário não autenticado", Messagebox.OK, Messagebox.EXCLAMATION);
        
        	Executions.sendRedirect("/login.zul");
        }
    }
	
	public void irNovoArquivo()
	{
		Executions.sendRedirect("/novoArquivo.zul");
	}
	
	public void logout()
	{
		Sessions.getCurrent().removeAttribute("usuario");
		Executions.sendRedirect("/login.zul");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void gerenciarDiretorios()
	{
		Executions.sendRedirect("/gerenciarDiretorios.zul");
	}
	
	public void gerenciarContas()
	{
		Executions.sendRedirect("/gerenciarContas.zul");
	}
}
