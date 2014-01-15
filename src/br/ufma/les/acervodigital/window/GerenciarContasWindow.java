package br.ufma.les.acervodigital.window;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.PackageData;
import br.ufma.les.acervodigital.treemodel.PackageDataUtil;

public class GerenciarContasWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private Usuario user;
	private List<Usuario> usuarios;
	private AcervoDigitalFachada acervoDigitalFachada;
	private String campoBusca;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        
        user = new Usuario();
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        try {
			usuarios = acervoDigitalFachada.usuariosNaoValidados();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        binder.loadAll();
    }

	public void buscarUsuario() throws Exception
	{
		if(campoBusca.isEmpty() || campoBusca.equals(""))
		{
			Messagebox.show("Preencha o campo de busca" 
					,"Erro", Messagebox.OK, Messagebox.EXCLAMATION);
			
		}else{
			
			usuarios = acervoDigitalFachada.findUsuarioByNome(campoBusca);
		
			if(usuarios.size() == 0)
			{
				Messagebox.show("Usuario nao encontrado" 
						,"Erro", Messagebox.OK, Messagebox.EXCLAMATION);
				
				usuarios = acervoDigitalFachada.usuariosNaoValidados();
				campoBusca = "";
			}
			
			binder.loadAll();
		}
	}
	
	public void abrirWinUsuario()
	{
		Window winUsuario = (Window) getFellow("winUsuario");
		winUsuario.setVisible(true);
		winUsuario.setPosition("center");
		winUsuario.setMode("modal");
		
		binder.loadAll();
	}
	
	public void cancelar()
	{
		Window winUsuario = (Window) getFellow("winUsuario");
		winUsuario.setVisible(false);
		
		binder.loadAll();
	}
	
	public void salvar() throws Exception
	{
		
		if(user.getNome()!=null && user.getEmail()!=null && user.getLogin()!= null && user.getSenha()!=null)
		{
			acervoDigitalFachada.alterarUsuario(user);
			
			Messagebox.show("Usuario alterado com sucesso" 
					,"Erro", Messagebox.OK, Messagebox.EXCLAMATION);
			
		}else{
			Messagebox.show("Preencha todos os campos" 
					,"Erro", Messagebox.OK, Messagebox.EXCLAMATION);
			
		}
		
		binder.loadAll();
	}
	
	public void excluir()
	{
		if(user != null)
		{
			Messagebox.show("Voce deseja realmente excluir este usuario ?", "Question",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
	                   
						@Override
						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							if(event.getName().equals("onOK")){
								acervoDigitalFachada.excluirUsuario(user);
						    	cancelar();
								binder.loadAll();
								

							}

						}
				
			 
			});

		}
	}
	
	public void limparBusca() throws Exception
	{
		usuarios = acervoDigitalFachada.usuariosNaoValidados();
		campoBusca = "";
		binder.loadAll();
		
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getCampoBusca() {
		return campoBusca;
	}

	public void setCampoBusca(String campoBusca) {
		this.campoBusca = campoBusca;
	}
	
	
}