package br.ufma.les.acervodigital.window;

import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class DadosUsuarioWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private List<TipoAcesso> tipoAcessos;
	private TipoAcesso tipoAcesso;
	private String nome;
	private String login;
	private String senha;
	private String email;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        try {
			tipoAcessos = acervoDigitalFachada.findAllTiposAcessos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        binder.loadAll();
        
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
			
			if(nome.equals("") || nome == null)
			{
				throw new Exception("Preecha o campo nome");
				
			}
			if(login.equals("") || login == null)
			{
				throw new Exception("Preecha o campo login");
			}
			
			if(email.equals("") || email == null)
			{
				throw new Exception("Preecha o campo email");
			}
			
			if(senha.equals("") || senha == null)
			{
				throw new Exception("Preecha o campo senha");
			}
			
			Label lbLogin = (Label) getFellow("statusLogin");
			if(lbLogin.getValue().equals("Login já existente"))
			{
				throw new Exception("Insira um login válido");
			}
			Label lbValidacao = (Label) getFellow("captchaResult");
			if(!lbValidacao.getValue().equals("OK"))
			{
				throw new Exception("Erro no campo de validação");
			}
			//se chegou até aqui, ok, pode salvar !
			
			Usuario u = new Usuario();
			u.setNome(nome);
			u.setLogin(login);
			u.setEmail(email);
			u.setSenha(senha);
			u.setValidado(false);
			u.setTipoAcesso(acervoDigitalFachada.findAcessoByCodigo(1));
			acervoDigitalFachada.inserirUsuario(u);
			
			Messagebox.show("Cadastro efetuado com sucesso " +
					"Contate um moderador para alteração de perfil",
					"Cadastro Salvo", Messagebox.OK, Messagebox.INFORMATION);
			
		}catch(Exception e){
			Messagebox.show("Erro: "+ e,
					"Erro ao salvar", Messagebox.OK, Messagebox.ERROR);
		}
	
	}
	
	public void cancelarCadastro()
	{
		Executions.sendRedirect("/novoArquivo.zul");
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

	public List<TipoAcesso> getTipoAcessos() {
		return tipoAcessos;
	}

	public void setTipoAcessos(List<TipoAcesso> tipoAcessos) {
		this.tipoAcessos = tipoAcessos;
	}

	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
	
	

}
