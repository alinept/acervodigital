package br.ufma.les.acervodigital.dao;

import br.ufma.les.acervodigital.dominio.Usuario;

public interface UsuarioDAO {
	
	Usuario buscarUsuario(String login, String senha) throws Exception;
	public void inserirUsuario(Usuario usuario) throws Exception;
	public void alterarUsuario(Usuario usuario) throws Exception;
	public void excluirUsuario(Usuario usuario) throws Exception;

}
