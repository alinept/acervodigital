package br.ufma.les.acervodigital.treemodel;

public class PackageData {
    private final String path;
    private final String criacao;
    private final String proprietario;
 
    public PackageData(String path, String criacao, String proprietario) {
        this.path = path;
        this.criacao = criacao;
        this.proprietario = proprietario;
    }
 
 
    public String getPath() {
        return path;
    }


	public String getCriacao() {
		return criacao;
	}


	public String getProprietario() {
		return proprietario;
	}
 
    
}
