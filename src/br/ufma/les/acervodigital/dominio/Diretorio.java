package br.ufma.les.acervodigital.dominio;

import java.util.Date;

public class Diretorio {

	 private Integer id;
     private String name;
     private Date dataCriacao;
     private Usuario proprietario;
     private Diretorio pai;
     private int idpai;
     
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Usuario getProprietario() {
		return proprietario;
	}
	public void setProprietario(Usuario proprietario) {
		this.proprietario = proprietario;
	}
	public Diretorio getPai() {
		return pai;
	}
	public void setPai(Diretorio pai) {
		this.pai = pai;
	}
	public int getIdpai() {
		return idpai;
	}
	public void setIdpai(int idpai) {
		this.idpai = idpai;
	}
     
     
}
