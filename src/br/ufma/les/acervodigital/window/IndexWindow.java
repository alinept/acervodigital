package br.ufma.les.acervodigital.window;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;


public class IndexWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	protected DataBinder binder;
	private String stringBusca;
	private List<Documento> documentos;
	private AcervoDigitalFachada acervoDigitalFachada;
	
	private boolean porTitulo, porDescricao, porConteudo;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        binder.loadAll();
      
    }
	
	public void buscarDocumento() throws Exception
	{
		documentos = new ArrayList<Documento>();
        // obtem os componentes da pagina
   		
		Checkbox checkTitulo = (Checkbox) getFellow("checkboxTitulo");
		Checkbox checkDescricao = (Checkbox) getFellow("checkboxDescricao");
		Checkbox checkConteudo = (Checkbox) getFellow("checkboxConteudo");
		
		porTitulo = checkTitulo.isChecked();
    	porDescricao = checkDescricao.isChecked();
    	porConteudo = checkConteudo.isChecked();

    	// testa se os valores setados sao validos
    	if(stringBusca.equals("") || isEmpty(stringBusca)){
    		Messagebox.show("Você deve digitar alguma coisa na caixa de texto e " +
					"selecionar alguma tag de pesquisa ",
					"Erro ao processar a pesquisa",
					Messagebox.OK,Messagebox.ERROR);
			return;
    	}
    	
    	long time1 = System.currentTimeMillis();
    	documentos = acervoDigitalFachada.buscarDocumento(stringBusca, null, porTitulo, porDescricao, porConteudo);	
    	
    	Busca b = new Busca(stringBusca, porTitulo, porDescricao, porConteudo,
				System.currentTimeMillis() - time1, 0 );
    	
    	Sessions.getCurrent().setAttribute("ultimosParametrosBusca", b);
    	Sessions.getCurrent().setAttribute("documentos", documentos);
		
    	Executions.sendRedirect("/resultadosBusca.zul");
	}
	
	public void irLogin()
	{
		Executions.sendRedirect("/login.zul");
		binder.loadAll();
	}
	
	public String getStringBusca() {
		return stringBusca;
	}

	public void setStringBusca(String stringBusca) {
		this.stringBusca = stringBusca;
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

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	
	protected class Busca{
    	
    	protected String strSearch; // String da caixa de texto da pesquisa
    	protected boolean byTitle, byDescription, byContent; // Tipo da pesquisa (setado nos checkboxs)
    	protected long searchTime;
    	protected int lastIndex;
    	
    	public Busca(String strSearch, boolean byTitle, boolean byDescription,
				boolean byContent, long initDate, int lastIndex) {
			this.strSearch = strSearch;
			this.byTitle = byTitle;
			this.byDescription = byDescription;
			this.byContent = byContent;
			this.searchTime = initDate;
		}
    }

}
