package br.ufma.les.acervodigital.dominio;

public class ArquivoDocumento {
	
	private String nomeArquivo;
    private byte[] byteStream;
    private Diretorio diretorio;
    
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
	public Diretorio getDiretorio() {
		return diretorio;
	}
	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}
    
    

}
