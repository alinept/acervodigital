package br.ufma.les.acervodigital.dao;

import br.ufma.les.acervodigital.dominio.TipoAcesso;

public interface TipoAcessoDAO {

	TipoAcesso findByCodigo(int codigo) throws Exception;
}
