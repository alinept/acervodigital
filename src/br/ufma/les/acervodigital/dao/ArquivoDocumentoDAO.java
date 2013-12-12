package br.ufma.les.acervodigital.dao;

import java.util.List;

import br.ufma.les.acervodigital.dominio.ArquivoDocumento;


public interface ArquivoDocumentoDAO {
	
	boolean inserirArquivo(ArquivoDocumento arquivo);
	List<ArquivoDocumento> getDocumentFiles(int idDoc) throws Exception;
	
}
