package br.ufma.les.acervodigital.window;

import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.ObjectSql;

public class DadosUsuarioWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private List<TipoAcesso> tipoAcessos;
	private TipoAcesso tipoAcesso;
	private List<ObjectSql> diretorios;
	private ObjectSql diretorio;
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
			diretorios = acervoDigitalFachada.findAllDiretorios();
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
			
			if(tipoAcesso == null)
			{
				throw new Exception("Selecione o Tipo de Acesso do usuários");
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
			if(tipoAcesso.getNome().equals("moderador"))
			{
				if(diretorio == null)
				{
					throw new Exception("Selecione o diretório que este usuário será moderador");
				}
			}
			
			//se chegou até aqui, ok, pode salvar !
			
			Usuario u = new Usuario();
			u.setNome(nome);
			u.setLogin(login);
			u.setEmail(email);
			u.setSenha(senha);
			if(tipoAcesso.getNome().equals("moderador"))
			{
				u.setIdDiretorio(diretorio.getCodigo());
			}
			
			u.setTipoAcesso(tipoAcesso);
			acervoDigitalFachada.inserirUsuario(u);
			
			Messagebox.show("Cadastro efetuado com sucesso ",
					"Cadastro Salvo", Messagebox.OK, Messagebox.INFORMATION);
			limpaCampos();
		}catch(Exception e){
			Messagebox.show("Erro: "+ e,
					"Erro ao salvar", Messagebox.OK, Messagebox.ERROR);
		}
	
	}
	
	public void limpaCampos()
	{
		nome = "";
		login = "";
		senha = "";
		email= "";
		
		binder.loadAll();
	}
	
	public void verificaTipoAcesso()
	{
		if(tipoAcesso.getNome().equals("moderador"))
		{
			Div divModerador = (Div) getFellow("acessoModerador");
			divModerador.setVisible(true);
			binder.loadAll();
		}else{
			Div divModerador = (Div) getFellow("acessoModerador");
			divModerador.setVisible(false);
			binder.loadAll();
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

	public List<ObjectSql> getDiretorios() {
		return diretorios;
	}

	public void setDiretorios(List<ObjectSql> diretorios) {
		this.diretorios = diretorios;
	}

	public ObjectSql getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(ObjectSql diretorio) {
		this.diretorio = diretorio;
	}
	
	

}
