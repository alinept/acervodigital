package br.ufma.les.acervodigital.window;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
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

        Menuitem menuNovaConta = (Menuitem) getFellow("menuNovaConta");
        Menuitem menuGerenciaDiretorios = (Menuitem) getFellow("menuGerenciarDiretorios");
        Menuitem menuGerenciaContas = (Menuitem) getFellow("menuGerenciarContas");
        Menupopup menuGerenciar = (Menupopup) getFellow("menuGerenciar");

        if(usuario != null)
        {
        	if(usuario.getTipoAcesso().getNome().equals("comum"))
        	{
        		menuNovaConta.setVisible(false);
                menuGerenciaDiretorios.setVisible(false);
                menuGerenciaContas.setVisible(false);
                menuGerenciar.invalidate();

        	}else if(usuario.getTipoAcesso().getNome().equals("moderador"))
        	{
        		menuNovaConta.setVisible(false);
                menuGerenciaDiretorios.setVisible(true);
                menuGerenciaContas.setVisible(false);
                //menuGerenciar.setVisible(true);
        	}else if(usuario.getTipoAcesso().getNome().equals("administrador"))
        	{
        		menuNovaConta.setVisible(true);
                menuGerenciaDiretorios.setVisible(true);
                menuGerenciaContas.setVisible(true);
                //menuGerenciar.setVisible(true);
        	}
        }
        
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
	
	public void meusDados()
	{
		Executions.sendRedirect("/meusDados.zul");
	}
	
	public void logout()
	{
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/login.zul");
	}
	
	public void novaConta()
	{
		Executions.sendRedirect("/novoUsuario.zul");
		binder.loadAll();
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
