package br.ufma.les.acervodigital.dominio;

public class ArquivoDocumento {
	
	private Documento documento;
	private String nomeArquivo;
    private byte[] byteStream;
    
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public byte[] getByteStream() {
		return byteStream;
	}
	public void setByteStream(byte[] byteStream) {
		this.byteStream = byteStream;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	  
    

}
