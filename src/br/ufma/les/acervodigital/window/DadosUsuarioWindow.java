package br.ufma.les.acervodigital.window;

import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class DadosUsuarioWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private String nome;
	private String login;
	private String senha;
	private String email;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        binder.loadAll();
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
    
    }
	
	public void verificaLogin() throws Exception
	{
		if(login != null && !login.equals(""))
		{
			if(acervoDigitalFachada.isLoginUsuarioValido(login))
			{
				((Label)getFellow("statusLogin")).setValue("Login válido");
				((Label)getFellow("statusLogin")).setClass("label-success");
			}else{
				((Label)getFellow("statusLogin")).setValue("Login já existente");
				((Label)getFellow("statusLogin")).setClass("label label-important");
			}
		}else{
			((Label)getFellow("statusLogin")).setValue("");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
