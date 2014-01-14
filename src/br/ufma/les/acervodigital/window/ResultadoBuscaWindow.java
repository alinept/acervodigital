package br.ufma.les.acervodigital.window;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;


public class ResultadoBuscaWindow extends IndexWindow {

	private static final long serialVersionUID = 6929872073025822757L;
	
	private int lastIndex = 0;
	private int lengthIndex;
	Busca ultimaBusca;
	ArrayList<Documento> docs;
	private AcervoDigitalFachada acervoDigitalFachada;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		acervoDigitalFachada = new AcervoDigitalFachadaImpl();
		ultimaBusca =
			(Busca) Sessions.getCurrent().getAttribute("ultimosParametrosBusca");
		
		docs = (ArrayList<Documento>) Sessions.getCurrent().getAttribute("documentos");
		
		if(ultimaBusca != null){
			
			((Textbox)getFellow("textboxSearch")).setValue( ultimaBusca.strSearch );
			((Checkbox)getFellow("checkboxTitulo")).setChecked( ultimaBusca.byTitle );
			((Checkbox)getFellow("checkboxDescricao")).setChecked( ultimaBusca.byDescription );
			((Checkbox)getFellow("checkboxConteudo")).setChecked( ultimaBusca.byContent );
			lastIndex = ultimaBusca.lastIndex;
		}
		if(docs == null) return;
		
		((Label)getFellow("headLabel")).setValue(docs.size() + " resultados (" +
				ultimaBusca.searchTime/1000.0 + " segundos)");
		
		Vbox resultsVbox = (Vbox)getFellow("resultsVBox");
		lengthIndex = (lastIndex + 10 > docs.size())? docs.size() : lastIndex + 10;
		
		if( docs.isEmpty() ){
			Label label = new Label("Sua pesquisa -\"" + ultimaBusca.strSearch + "\"- não encontrou" +
					" nenhum documento correspondente.");
			((Button)getFellow("previousButton")).setVisible(false);
			((Button)getFellow("nextButton")).setVisible(false);
			resultsVbox.appendChild(label);
			binder.loadAll();
			return;
		}
		
		for(int i=lastIndex; i<lengthIndex; i++){
			// 1º HBOX
			Hbox firstHbox = new Hbox();
			
			Toolbarbutton titleToolbarbutton = new Toolbarbutton( docs.get(i).getTitulo());
			titleToolbarbutton.setStyle("font-size: medium;");
			titleToolbarbutton.addEventListener("onClick", new Visualize(i));
			
			Separator firsSeparator = new Separator();
			firsSeparator.setWidth("10px");
			
//			Label expeditionDateLabel =
//				new Label( DateFormat.getDateInstance().format(docs.get(i).getDataExpedicao()) );
//			expeditionDateLabel.setStyle("font-size: small; font-style:italic; color:gray;");
//			
			firstHbox.appendChild(titleToolbarbutton);
			firstHbox.appendChild(firsSeparator);
			//firstHbox.appendChild(expeditionDateLabel);
			
			// LABEL - CONTENT
			Label descriptionLabel = new Label( docs.get(i).getDescricao() );
			descriptionLabel.setStyle("font-size: small;");
			
			// 2ª HBOX
			Hbox secondHbox = new Hbox();
			Toolbarbutton visualizeToolbarbutton = null;
			Toolbarbutton downloadToolbarbutton = null;
			Separator secondSeparator = null;
			
			
			// SEPARATOR
			Separator thirdSeparator = new Separator();
			thirdSeparator.setHeight("25px");
			
			// fogo!
			resultsVbox.appendChild(firstHbox);
			resultsVbox.appendChild(descriptionLabel);
			resultsVbox.appendChild(secondHbox);
			resultsVbox.appendChild(thirdSeparator);
		}
		Button previousButton = (Button)getFellow("previousButton");
		Button nextButton = (Button)getFellow("nextButton");
		previousButton.setDisabled(lastIndex == 0);
		nextButton.setDisabled(lengthIndex == docs.size());
		if( previousButton.isDisabled() && nextButton.isDisabled() ){
			previousButton.setVisible(false);
			nextButton.setVisible(false);
		}
		binder.loadAll();
		Clients.scrollIntoView( getFellow("textboxSearch") );
	}

	public void nextPage(){
		ultimaBusca.lastIndex = lengthIndex;
		Executions.sendRedirect("/resultadosBusca.zul");
		Clients.scrollIntoView( getFellow("textboxSearch") );
	}
	
	public void previousPage(){
		ultimaBusca.lastIndex -= 10;
		Executions.sendRedirect("/resultadosBusca.zul");
		Clients.scrollIntoView( getFellow("textboxSearch") );
	}
	
	private class Visualize implements EventListener{
		
		private int index;
		
		public Visualize(int index){
			this.index = index;
		}

		@Override
		public void onEvent(Event arg0) throws Exception {
			
			
			List<ArquivoDocumento> files = acervoDigitalFachada.getDocumentFiles(docs.get(index).getId()); 
					
			if( files.isEmpty() ) return;
			
			String filename = files.get(0).getNomeArquivo();
			
			//if( FileSupportVerificator.isPDFType(filename) ){
			Sessions.getCurrent().setAttribute("pdfFile",
						new AMedia(files.get(0).getNomeArquivo(), ".pdf", "application/pdf",
								files.get(0).getByteStream()) );	
				Executions.sendRedirect("/iframe.zul");
			//}
		
				
		}
	}
	
	private class Download implements EventListener{
		private int index;
		
		public Download(int index){
			this.index = index;
		}

		@Override
		public void onEvent(Event arg0) throws Exception {
			
			List<ArquivoDocumento> files = acervoDigitalFachada.getDocumentFiles(docs.get(index).getId()); 
			if( files.isEmpty() ) return;
			
			String filename = files.get(0).getNomeArquivo();
			
			Filedownload.save(files.get(0).getByteStream(), "application/pdf", 
						files.get(0).getNomeArquivo());
			}
	}
}
