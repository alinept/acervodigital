package br.ufma.les.acervodigital.window;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TagDocumento;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.ObjectSql;
import br.ufma.les.acervodigital.util.DocumentAnalize;
import br.ufma.les.acervodigital.util.FileSupportVerificator;
import br.ufma.les.acervodigital.util.InputStream2ByteConverter;
import br.ufma.les.acervodigital.util.Reader2StringConverter;

public class NovoArquivoWindow extends Window{
	
private static final long serialVersionUID = 7268970269306314382L;
	
	private Media doc = null;
	private Media docaux = null;
	public Window window;
	protected DataBinder binder;
	private AcervoDigitalFachada acervoDigitalFachada;
	private List<Tag> tags;
	private Tag tag;
	private List<Tag> tagsUtilizadas;
	private List<ObjectSql> diretorios;
	private ObjectSql diretorio;
	private List<TagDocumento> tagDocumento;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        tags = new ArrayList<Tag>();
        tagsUtilizadas = new ArrayList<Tag>();
        tagDocumento = new ArrayList<TagDocumento>();
        try {
			tags = acervoDigitalFachada.findAllTags();
			diretorios = acervoDigitalFachada.findAllDiretorios();
			diretorio = new ObjectSql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        binder.loadAll();
      
    }
	
	public void goBack(){
		Executions.sendRedirect("/index.zul");
	}

	/**
	 * Botao "?" do upload de documento
	 */
	
	public void uploadDoc(){
		
		//docaux = Fileupload.get();
		
		if( docaux != null){
			
			if(FileSupportVerificator.isDocumentValid(docaux.getName()) ){
				doc = docaux;
				((Textbox)getFellow("fileUploadedTextBox")).setValue( docaux.getName() );

			}
			else{
				Messagebox.show("Esse tipo de arquivo não é suportado. Clique no botão " +
						"\"?\" para mais informações.",
						"Arquivo inválido", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	public void helpDoc(){
		
		Messagebox.show("Ao enviar um documento de texto, " +
				"o sistema extrai o conteúdo automaticamente e o armazena " +
				"para que possa ser consultado posteriormente. Clique em \"" +
				"Upload\", selecione o documento e depois clique em \"" +
				"Enviar\". Mas atenção, o arquivo deve estar em formato PDF"
				,"Ajuda",Messagebox.OK, Messagebox.INFORMATION);
	}
	

	
	/**
	 * Botao "Enviar"
	 * @throws Exception 
	 */
	public void send() throws Exception{
		
		tagDocumento.clear();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		String title = ((Textbox)getFellow("titleTextBox")).getValue();
		String description = ((Textbox)getFellow("descriptionTextBox")).getValue();
		
		// true = doc | false = imagem
		boolean isDocMediaType = true;
		
		//checando dados
			
		//checando titulo
		if( isEmpty(title) ){
			Messagebox.show("O título está em branco. O preenchimeto desse campo é " +
					"obrigatório.", "Erro no título", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		//checando titulo
		if( diretorio == null ){
			Messagebox.show("O campo do Diretório está em branco. Este campo é " +
					"obrigatório.", "Erro no Diretório", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		//checando a descrição
		if( isEmpty(description) ){
			Messagebox.show("A descrição está em branco. O preenchimeto desse campo é " +
					"obrigatório.", "Erro na descrição", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		//checando tags
		if(tagsUtilizadas.size() > 0)
		{
			for(int i=0; i<tagsUtilizadas.size(); i++)
			{
				if(tagsUtilizadas.get(i).getNome().contains("data"))
				{
					Datebox date = (Datebox) getFellow(""+tagsUtilizadas.get(i).getId());
					if(date.getValue() == null)
					{
						Messagebox.show("Preecha todos os campos das tags selecionadas\n"+ 
								"Campo "+tagsUtilizadas.get(i).getNome()+" não está preenchido", "Erro nas Tags", Messagebox.OK, Messagebox.ERROR);
						return;
					}else{
						
						TagDocumento tagDoc = new TagDocumento();
						tagDoc.setTag(tagsUtilizadas.get(i));
						tagDoc.setConteudo(df.format(date.getValue()));
						tagDocumento.add(tagDoc);
					}
				}else{
					Textbox text = (Textbox) getFellow(""+tagsUtilizadas.get(i).getId());
					if(text.getValue() == null || text.getValue().equals(""))
					{
						Messagebox.show("Preecha todos os campos das tags selecionadas\n"+ 
								"Campo "+tagsUtilizadas.get(i).getNome()+" não está preenchido", "Erro nas Tags", Messagebox.OK, Messagebox.ERROR);
						return;
					}else{
						TagDocumento tagDoc = new TagDocumento();
						tagDoc.setTag(tagsUtilizadas.get(i));
						tagDoc.setConteudo(text.getValue());
						tagDocumento.add(tagDoc);
					}
				}
			}
		}
		
		//checando data
		//		if( date == null ){
		//			Messagebox.show("A data de expedição está em branco. O preenchimeto desse campo é " +
		//					"obrigatório.", "Erro na data de expedição", Messagebox.OK, Messagebox.ERROR);
		//			return;
		//		}
		
		//checando conteudo
		boolean fail = false;
		
		if( isDocMediaType ){			
			fail = (doc == null);
		}
		
		if( fail ){
			Messagebox.show("Você não fez upload de nenhum" +
					(" documento." ),
					"Falta de documento", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		
		// beleza! se chegou ate aqui entao eh pq ta tudo certo
		//DbDocManager dbDocManager = new DbDocManager();
		String content = "";
		
		// INSERE DOCUMENTO DE TEXTO
		if( isDocMediaType ){
			
			// PDF
			if( FileSupportVerificator.isPDFType(doc.getName()) ){
				content = getContent(doc, FileSupportVerificator.PDF_TYPE);
				
				Documento d = new Documento();
				d.setTitulo(title);
				d.setDescricao(description);
				d.setConteudo(content);
				d.setDataUpload(new Date(System.currentTimeMillis()));
				//d.setDataExpedicao(date);
				d.setProprietario((Usuario)Sessions.getCurrent().getAttribute("usuario"));
				d.setDiretorio(acervoDigitalFachada.findDiretorioByCodigo(diretorio.getCodigo()));
				acervoDigitalFachada.inserirDocumento(d);
				
				for(int i = 0; i < tagDocumento.size(); i++)
				{
					tagDocumento.get(i).setDocumento(d);
					acervoDigitalFachada.inserirTagDocumento(tagDocumento.get(i));
				}
				
				insertFile(doc , FileSupportVerificator.PDF_TYPE,d);
				
								
			}
		}
		
		Messagebox.show("Documento inserido com sucesso!", "Sucesso na operação", 
					Messagebox.OK, Messagebox.INFORMATION);
		
		Executions.sendRedirect("/index.zul");
	}
	
	public void informacaoTag()
	{
		if(tag != null)
		{
		
			Messagebox.show(tag.getDescricao(), "Informação sobre Tag", 
					Messagebox.OK, Messagebox.INFORMATION);
		}
		binder.loadAll();
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
	
	private String getContent(Media doc, short type) throws Exception{
		String content = "";
		switch (type) {
			case FileSupportVerificator.PDF_TYPE:
				try{
					content = DocumentAnalize.getText( doc.getStreamData(), type);
				}
				catch (IllegalStateException e ) {
					try{
						content = DocumentAnalize.getText( doc.getStringData(), type);
						e.printStackTrace();
					}
					catch (IllegalStateException e1 ) {
						content = DocumentAnalize.getText( doc.getReaderData(), type);
						e1.printStackTrace();
					}
				}
				break;
		}
				return content;
	}
	
	
	private void insertFile(Media doc, short type, Documento documento ) throws Exception{
		switch (type) {
			case FileSupportVerificator.PDF_TYPE:
				String title = ((Textbox)getFellow("titleTextBox")).getValue();
				
				try{
					//InputStream2ByteConverter.get(inputStream) 
					ArquivoDocumento a = new ArquivoDocumento();
					a.setByteStream(InputStream2ByteConverter.get(doc.getStreamData()));
					a.setNomeArquivo(doc.getName().replace(' ', '_'));
					a.setDocumento(documento);
					//a.setDocumento(acervoDigitalFachada.findDocumentoByNome(title));
					acervoDigitalFachada.inserirArquivo(a);
					
				}
				catch (IllegalStateException e ) {
					try{
						
						ArquivoDocumento a = new ArquivoDocumento();
						a.setByteStream(doc.getStringData().getBytes());
						a.setNomeArquivo(doc.getName().replace(' ', '_'));
						//a.setDocumento(acervoDigitalFachada.findDocumentoByNome(title));
						
						acervoDigitalFachada.inserirArquivo(a);
					}
					catch (IllegalStateException e1 ) {
						ArquivoDocumento a = new ArquivoDocumento();
						a.setByteStream(Reader2StringConverter.get(doc.getReaderData()).getBytes());
						a.setNomeArquivo(doc.getName().replace(' ', '_'));
						//a.setDocumento(acervoDigitalFachada.findDocumentoByNome(title));
						
						acervoDigitalFachada.inserirArquivo(a);
						
					}
				}
			break;
				
		}
	}

	public void inserirValoresTags()
	{
		Listbox list = (Listbox) getFellow("right");
		List<Listitem> items = list.getItems();
		Groupbox groupTags = (Groupbox) getFellow("groupTags");
		if(items.size() > 0)
		{
			groupTags.setVisible(true);
			groupTags.getChildren().clear();
			tagsUtilizadas.clear();
			
			Vbox vbox = new Vbox();
			
			for(int i=0; i< items.size(); i++)
			{
				Tag tag = new Tag();
				Listitem item = items.get(i);
				try {
					tag = acervoDigitalFachada.findTagByNome(item.getLabel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				tagsUtilizadas.add(tag);
				
				Hbox hbox = new Hbox();
				
				Label label = new Label();
				label.setValue(tag.getNome());
				label.setId(""+tag.getId());
				
				Div div = new Div();
				div.setAlign("right");
				div.setWidth("150px");
				div.appendChild(label);
				
				hbox.appendChild(div);
				if(tag.getNome().contains("data"))
				{
					Datebox date = new Datebox();
					date.setId(""+tag.getId());
					hbox.appendChild(date);
				}else{
					
					Textbox text = new Textbox();
					text.setWidth("400px");
					text.setId(""+tag.getId());
					hbox.appendChild(text);
				}
				
				
				
				vbox.appendChild(hbox);
			}
			
			groupTags.appendChild(vbox);
			
		}else{
			Messagebox.show("Arraste as tags disponíveis para as tags a serem utilizadas", "Erro ao carregar Tags", 
					Messagebox.OK, Messagebox.INFORMATION);
		}
		
		binder.loadAll();
	}
	
	public Media getDoc() {
		return doc;
	}

	public void setDoc(Media doc) {
		this.doc = doc;
	}

	public Media getDocaux() {
		return docaux;
	}

	public void setDocaux(Media docaux) {
		this.docaux = docaux;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
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

	public List<Tag> getTagsUtilizadas() {
		return tagsUtilizadas;
	}

	public void setTagsUtilizadas(List<Tag> tagsUtilizadas) {
		this.tagsUtilizadas = tagsUtilizadas;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public List<TagDocumento> getTagDocumento() {
		return tagDocumento;
	}

	public void setTagDocumento(List<TagDocumento> tagDocumento) {
		this.tagDocumento = tagDocumento;
	}
	
	
}
