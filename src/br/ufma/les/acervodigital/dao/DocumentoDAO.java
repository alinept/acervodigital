package br.ufma.les.acervodigital.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TagDocumento;

public interface DocumentoDAO {
	
	boolean inserirDocumento(Documento documento) throws Exception;
	boolean deletarDocumento(Documento documento) throws Exception;
	boolean atualizaDocumento(Documento documento) throws Exception;
	
	Documento findByCodigo(int codigo) throws Exception;
	Documento findByNome(String nome) throws Exception;
	Documento findByNomeArquivo(String nome) throws Exception ; 
	List<Documento> ultimosEnvios() throws Exception;
	
	List<Documento> buscar(String busca, ArrayList<Tag> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo) throws Exception;
	List<Documento> buscaAvancada(String busca, ArrayList<TagDocumento> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo, int idDiretorio) throws Exception;
	
}

