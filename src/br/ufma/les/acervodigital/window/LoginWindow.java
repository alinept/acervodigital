package br.ufma.les.acervodigital.window;

import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class LoginWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	
	private String username, password;
	private Usuario usuario;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        binder.loadAll();
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        usuario = new Usuario();
    }
	
	
	//Esse metodo e chamado ao clicar no botao "Entrar".
	public void realizarLogin() throws Exception{
		Textbox nomeUsuario, senha;
		
		// obtem os componentes da pagina
		try{
			
			nomeUsuario = (Textbox)getFellow("textboxUsername");
			senha = (Textbox)getFellow("textboxPassword");
		}
		catch (ComponentNotFoundException e) {
			Messagebox.show("Os campos de login não puderam ser acessados.\n" +
					"A página pode não ter sido carregada corretamente.\nAtualize " +
					"a página para resolver o problema.","Erro ao processar a pesquisa",
					Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		// obtem os dados do login
		username = nomeUsuario.getText();
		password = senha.getText();
		
		// testa se os dados digitados sao validos
		if( isEmpty(username) || isEmpty(password) ){
			((Label)getFellow("statusLabel")).setValue("Preencha os campos login e senha primeiro");
			return;
		}
		
		usuario = acervoDigitalFachada.buscarUsuario(username, password);
		
		if(usuario!=null)
		{
			Sessions.getCurrent().setAttribute("usuario", usuario);
			Executions.sendRedirect("/novoArquivo.zul");
		
		} else{
			((Label)getFellow("statusLabel")).setValue("Login ou senha incorretos");
		}
	}
	
	public void irNovoUsuario()
	{
		Executions.sendRedirect("/novoUsuario.zul");
	}
	
    private boolean isEmpty(String str){
    	if(str == null) return true;
    	for(int i=0; i<str.length(); i++){
    		if(Character.isLetterOrDigit( str.charAt(i) )){
    			return false;
    		}
    	}
    	return true;
    }

}
