package br.ufma.les.acervodigital.window;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
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
	
	public void submeterCadastro()
	{
		try{
			
			if(nome.equals("") || nome != null)
			{
				new Exception("Preecha o campo nome");
			}
			if(login.equals("") || login != null)
			{
				new Exception("Preecha o campo login");
			}
			
			if(email.equals("") || email != null)
			{
				new Exception("Preecha o campo email");
			}
			
			if(senha.equals("") || senha != null)
			{
				new Exception("Preecha o campo senha");
			}
			
			Label lbLogin = (Label) getFellow("statusLogin");
			if(lbLogin.getValue().equals("Login já existente"))
			{
				new Exception("Insira um login válido");
			}
			Label lbValidacao = (Label) getFellow("captchaResult");
			if(!lbValidacao.getValue().equals("OK"))
			{
				new Exception("Erro no campo de validação");
			}
			//se chegou até aqui, ok, pode salvar !
			
			Usuario u = new Usuario();
			u.setNome(nome);
			u.setLogin(login);
			u.setEmail(email);
			u.setSenha(senha);
		}catch(Exception e){
			
		}
	
	}
	
	public void cancelarCadastro()
	{
		Executions.sendRedirect("/login.zul");
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