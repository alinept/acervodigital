package br.ufma.les.acervodigital.window;

import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class GerenciarContasWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private Usuario usuario;
	private List<Usuario> usuarios;
	private AcervoDigitalFachada acervoDigitalFachada;
	private String campoBusca;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        
        usuario = new Usuario();
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
				Messagebox.show("Usuário não encontrado" 
						,"Erro", Messagebox.OK, Messagebox.EXCLAMATION);
				
				usuarios = acervoDigitalFachada.usuariosNaoValidados();
				campoBusca = "";
			}
			
			binder.loadAll();
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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