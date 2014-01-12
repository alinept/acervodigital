package br.ufma.les.acervodigital.window;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class MeusDadosWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private Usuario usuario;
	private String nome;
	private String login;
	private String novaSenha;
	private String confirmacaoSenha;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        usuario = new Usuario();
        usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
        nome = usuario.getNome();
        login = usuario.getLogin();
        binder.loadAll();
    }
	
	public void abrirAlterarSenha()
	{
		Div divSenha = (Div) getFellow("alterarSenha");
		divSenha.setVisible(true);
		binder.loadAll();
	}
	
	public void verificaLogin() throws Exception
	{
		if(login != null && !login.equals("") && login != usuario.getLogin())
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
	
	public void atualizarCadastro()
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
			
			if(usuario.getEmail().equals("") || usuario.getEmail() == null)
			{
				throw new Exception("Preecha o campo email");
			}
			Div divSenha = (Div) getFellow("alterarSenha");
			
			if(divSenha.isVisible())
			{
				if(novaSenha.equals("") || novaSenha == null)
				{
					throw new Exception("Preecha o campo Nova Senha");
				}
				
				if(confirmacaoSenha.equals("") || confirmacaoSenha == null)
				{
					throw new Exception("Preecha o campo Confirmação Senha");
				}
				
				if(novaSenha != confirmacaoSenha)
				{
					throw new Exception("Confirmação de Senha divergente");
				}
			}
			
			
			Label lbLogin = (Label) getFellow("statusLogin");
			
			if(lbLogin.getValue().equals("Login já existente"))
			{
				throw new Exception("Insira um login válido");
			}
			
			//se chegou até aqui, ok, pode salvar !

			usuario.setSenha(novaSenha);
			usuario.setLogin(login);
			usuario.setNome(nome);
			
			acervoDigitalFachada.alterarUsuario(usuario);
			
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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
	
	

}
