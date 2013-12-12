package br.ufma.les.acervodigital.dominio;

import java.util.Date;
import java.util.List;

public class Documento {
	
      
      private int id;
      private Date dataExpedicao;
      private Date dataUpload;
      private Usuario proprietario;
      private List<Tag> tags;
      private String conteudo;
      private Diretorio diretorio;
      
      private String titulo;
      private String descricao;
      
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDataExpedicao() {
		return dataExpedicao;
	}
	public void setDataExpedicao(Date dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}
	public Date getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}
	public Usuario getProprietario() {
		return proprietario;
	}
	public void setProprietario(Usuario proprietario) {
		this.proprietario = proprietario;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Diretorio getDiretorio() {
		return diretorio;
	}
	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}        
     
	
      
}
