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
		if(!diretorio.getName().equals("") || diretorio.getName() != null)
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
		}
	}

	public void editarDiretorio()
	{
		
	}
	
	public void excluirDiretorio()
	{
		
	}
	
	public void cancelarNovoDiretorio()
	{
		Window winNovoDiretorio = (Window) getFellow("novoDiretorio");
		winNovoDiretorio.setVisible(false);
		
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
