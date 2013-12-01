package br.ufma.les.acervodigital.dao;

import br.ufma.les.acervodigital.dominio.Documento;

public interface DocumentoDAO {
	
	boolean inserirDocumento(Documento documento) throws Exception;
	boolean deletarDocumento(Documento documento) throws Exception;
	boolean atualizaDocumento(Documento documento) throws Exception;
	
}

