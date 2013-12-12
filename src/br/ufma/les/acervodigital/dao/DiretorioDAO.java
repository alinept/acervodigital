package br.ufma.les.acervodigital.dao;

import br.ufma.les.acervodigital.dominio.Diretorio;

public interface DiretorioDAO {
	
	Diretorio findDiretorioByCodigo(int codigo) throws Exception;

}
