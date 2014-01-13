package br.ufma.les.acervodigital.window;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TagDocumento;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;
import br.ufma.les.acervodigital.treemodel.ObjectSql;


public class IndexWindow extends Window{

	private static final long serialVersionUID = 1L;
	
	public Window window;
	protected DataBinder binder;
	private String stringBusca;
	private List<Documento> documentos;
	private List<ObjectSql> diretorios;
	private ObjectSql diretorio;
	private List<Tag> tags;
	private Tag tag1;
	private Tag tag2;
	private Tag tag3;
	private String valorTag1;
	private String valorTag2;
	private String valorTag3;
	
	private AcervoDigitalFachada acervoDigitalFachada;
	
	private boolean porTitulo, porDescricao, porConteudo;
	
	public void onCreate()
    {
    	window = (Window)getFellow("win");
        binder =  new AnnotateDataBinder(window);
        acervoDigitalFachada = new AcervoDigitalFachadaImpl();
        try {
			diretorios = acervoDigitalFachada.findAllDiretorios();
			tags = acervoDigitalFachada.findAllTags();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        diretorio = new ObjectSql();
        tag1 = new Tag();
        tag2 = new Tag();
        tag3 = new Tag();
        
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
	
	public void buscaAvancada() throws Exception
	{
		documentos = new ArrayList<Documento>();
        // obtem os componentes da pagina
   		
		Checkbox checkTitulo = (Checkbox) getFellow("checkboxTitulo");
		Checkbox checkDescricao = (Checkbox) getFellow("checkboxDescricao");
		Checkbox checkConteudo = (Checkbox) getFellow("checkboxConteudo");
		
		porTitulo = checkTitulo.isChecked();
    	porDescricao = checkDescricao.isChecked();
    	porConteudo = checkConteudo.isChecked();
    	
    	if(diretorio == null && tag1 == null && tag2 == null && tag3 == null)
    	{
    		Messagebox.show("Selecione os valores para a busca avançada ",
					"Erro ao processar a pesquisa",
					Messagebox.OK,Messagebox.ERROR);
			return;
    	}
    	
    	ArrayList<TagDocumento> tags = new ArrayList<TagDocumento>();
    	if(tag1 != null)
    	{
    		tags.get(0).setTag(tag1);
    		tags.get(0).setConteudo(valorTag1);
    		
    	}
    	if(tag2 != null)
    	{
    		tags.get(1).setTag(tag1);
    		tags.get(1).setConteudo(valorTag2);
    	}
    	if(tag3 != null)
    	{
    		tags.get(2).setTag(tag1);
    		tags.get(2).setConteudo(valorTag3);
    	}
    	
    	long time1 = System.currentTimeMillis();
    	documentos = acervoDigitalFachada.buscaAvancada(stringBusca, tags, porTitulo, porDescricao, porConteudo, diretorio.getCodigo());	
    	
    	Busca b = new Busca(stringBusca, porTitulo, porDescricao, porConteudo,
				System.currentTimeMillis() - time1, 0 );
    	
    	Sessions.getCurrent().setAttribute("ultimosParametrosBusca", b);
    	Sessions.getCurrent().setAttribute("documentos", documentos);
		
    	Executions.sendRedirect("/resultadosBusca.zul");
	}
	
	
	
	public void abrirBuscaAvancada()
	{
		Div div = (Div) getFellow("buscaAvancada");
		div.setVisible(true);
		Button b = (Button) getFellow("botaoBusca");
		b.setVisible(false);
		
		binder.loadAll();
	}
	
	
	public void cancelarBuscaAvancada()
	{
		Div div = (Div) getFellow("buscaAvancada");
		div.setVisible(false);
		Button b = (Button) getFellow("botaoBusca");
		b.setVisible(true);
		
		binder.loadAll();
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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Tag getTag1() {
		return tag1;
	}

	public void setTag1(Tag tag1) {
		this.tag1 = tag1;
	}

	public Tag getTag2() {
		return tag2;
	}

	public void setTag2(Tag tag2) {
		this.tag2 = tag2;
	}

	public Tag getTag3() {
		return tag3;
	}

	public void setTag3(Tag tag3) {
		this.tag3 = tag3;
	}

	public String getValorTag1() {
		return valorTag1;
	}

	public void setValorTag1(String valorTag1) {
		this.valorTag1 = valorTag1;
	}

	public String getValorTag2() {
		return valorTag2;
	}

	public void setValorTag2(String valorTag2) {
		this.valorTag2 = valorTag2;
	}

	public String getValorTag3() {
		return valorTag3;
	}

	public void setValorTag3(String valorTag3) {
		this.valorTag3 = valorTag3;
	}
	
	

}
