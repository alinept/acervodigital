package br.ufma.les.acervodigital.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;

public interface DocumentoDAO {
	
	boolean inserirDocumento(Documento documento) throws Exception;
	boolean deletarDocumento(Documento documento) throws Exception;
	boolean atualizaDocumento(Documento documento) throws Exception;
	
	Documento findByCodigo(int codigo) throws Exception;
	
	List<Documento> buscar(String busca, ArrayList<Tag> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo) throws Exception;
	
}

