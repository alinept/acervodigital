package br.ufma.les.acervodigital.dao;

import java.util.List;

import br.ufma.les.acervodigital.dominio.TipoAcesso;

public interface TipoAcessoDAO {

	TipoAcesso findByCodigo(int codigo) throws Exception;
	List<TipoAcesso> findAll() throws Exception;
}
