package br.ufma.les.acervodigital.dao;

import br.ufma.les.acervodigital.dominio.Usuario;

public interface UsuarioDAO {
	
	Usuario buscarUsuario(String login, String senha) throws Exception;

}
