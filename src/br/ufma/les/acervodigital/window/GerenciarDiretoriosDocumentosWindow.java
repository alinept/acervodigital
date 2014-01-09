package br.ufma.les.acervodigital.window;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.PackageData;
import br.ufma.les.acervodigital.treemodel.PackageDataUtil;

import com.ibm.icu.util.GregorianCalendar;

public class GerenciarDiretoriosDocumentosWindow extends Window{
	
private static final long serialVersionUID = 1L;
	
	public Window window;
	private DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private TreeModel<TreeNode<PackageData>> arvore;
	private String diretorioSelecionado;
	private Diretorio diretorio;
	private Diretorio diretorioPai;
	private Date data;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        diretorioSelecionado = null;
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        
        diretorio = new Diretorio();
        
        GregorianCalendar gc = new GregorianCalendar();
        
        data = gc.getTime();
        
        PackageDataUtil pk = new PackageDataUtil();
        try {
			pk.montaArvore(acervoDigitalFachada.retornaCaminhoDiretorioRaiz());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	arvore = new DefaultTreeModel<PackageData>(pk.getRoot());

        binder.loadAll();
    }
	
	public void abrirNovoDiretorio(){
		
		try {

			if (!diretorioSelecionado.equals("")
					&& diretorioSelecionado != null) {
				Window winNovoDiretorio = (Window) getFellow("novoDiretorio");
				winNovoDiretorio.setVisible(true);
				winNovoDiretorio.setPosition("center");
				winNovoDiretorio.setMode("modal");

				try {

					diretorioPai = acervoDigitalFachada
							.findDiretorioByNome(diretorioSelecionado);
					
					Sessions.getCurrent().setAttribute("modo", "novo");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (NullPointerException e) {
			Messagebox
					.show("Para criar um diretório é necessário selecionar o diretório pai na árvore.\n"
							+ "Selecione um diretório abaixo e tente novamente ",
							"Erro ao criar diretório", Messagebox.OK,
							Messagebox.ERROR);
		}
		binder.loadAll();

	}
	
	public void salvarDiretorio() throws SQLException, Exception
	{
		String modo = (String) Sessions.getCurrent().getAttribute("modo");
		
		if(!diretorio.getName().equals("") || diretorio.getName() != null)
		{
			if(modo.equals("novo"))
			{
				diretorio.setPai(diretorioPai);
				diretorio.setDataCriacao(data);
				
				Usuario usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
				
				diretorio.setProprietario(usuario);
				
				acervoDigitalFachada.inserirDiretorio(diretorio);
				
				Messagebox
				.show("Diretório salvo com sucesso",
						"Informação", Messagebox.OK,
						Messagebox.INFORMATION);
			}
			else if(modo.equals("editar"))
			{
				acervoDigitalFachada.alterarDiretorio(diretorio);
				Messagebox
				.show("Diretório editado com sucesso",
						"Informação", Messagebox.OK,
						Messagebox.INFORMATION);
			}
				
			PackageDataUtil pk = new PackageDataUtil();
	        pk.setRoot(null);
	        
			try {
				pk.montaArvore(acervoDigitalFachada.retornaCaminhoDiretorioRaiz());
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	arvore = new DefaultTreeModel<PackageData>(pk.getRoot());
	    	
	    	binder.loadAll();
		}else{
			
			Messagebox
			.show("Insira um nome para o diretório.\n"
					,"Erro ", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void abrirEditarDiretorio()
	{
		try {
			Usuario usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
			
			if (!diretorioSelecionado.equals("")
					&& diretorioSelecionado != null) {
				
				Window winNovoDiretorio = (Window) getFellow("novoDiretorio");
				winNovoDiretorio.setVisible(true);
				winNovoDiretorio.setPosition("center");
				winNovoDiretorio.setMode("modal");

				try {

					diretorio = acervoDigitalFachada
							.findDiretorioByNome(diretorioSelecionado);
					
					data = diretorio.getDataCriacao();
					diretorioPai = diretorio.getPai();
					
//					if(!diretorio.getProprietario().equals(usuario))
//					{
//						cancelarNovoDiretorio();
//						Messagebox
//						.show("Desculpe. Você só pode editar um diretório em que seja proprietário\n"
//								+ "Selecione um diretório abaixo e tente novamente ",
//								"Erro de autorização", Messagebox.OK,
//								Messagebox.ERROR);
//					}
//					else{
						Sessions.getCurrent().setAttribute("modo", "editar");
					//}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (NullPointerException e) {
			Messagebox
					.show("Para editar um diretório é necessário selecionar o diretório na árvore.\n"
							+ "Selecione um diretório abaixo e tente novamente ",
							"Erro ao editar diretório", Messagebox.OK,
							Messagebox.ERROR);
		}
		binder.loadAll();

	}
	
	public void excluirDiretorio()
	{
		
	}
	
	public void cancelarNovoDiretorio()
	{
		Window winNovoDiretorio = (Window) getFellow("novoDiretorio");
		winNovoDiretorio.setVisible(false);
		
		diretorioSelecionado = null;
		diretorioPai = null;
		diretorio = null;
		
		binder.loadAll();
	}
	
	public TreeModel<TreeNode<PackageData>> getArvore() {
		return arvore;
	}

	public void setArvore(TreeModel<TreeNode<PackageData>> arvore) {
		this.arvore = arvore;
	}

	public String getDiretorioSelecionado() {
		return diretorioSelecionado;
	}

	public void setDiretorioSelecionado(String diretorioSelecionado) {
		this.diretorioSelecionado = diretorioSelecionado;
	}

	public Diretorio getDiretorioPai() {
		return diretorioPai;
	}

	public void setDiretorioPai(Diretorio diretorioPai) {
		this.diretorioPai = diretorioPai;
	}

	public Date getData() throws ParseException {
		
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Diretorio getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}

	
}
