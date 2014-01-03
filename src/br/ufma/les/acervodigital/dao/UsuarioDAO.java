package br.ufma.les.acervodigital.dao;

import java.util.List;

import br.ufma.les.acervodigital.dominio.Usuario;

public interface UsuarioDAO {
	
	Usuario buscarUsuario(String login, String senha) throws Exception;
	public void inserirUsuario(Usuario usuario) throws Exception;
	public void alterarUsuario(Usuario usuario) throws Exception;
	public void excluirUsuario(Usuario usuario) throws Exception;
	
	Usuario findByCodigo(int codigo) throws Exception;
	
	boolean isLoginValido(String login) throws Exception;
	
	List<Usuario> usuariosNaoValidados() throws Exception ;

}
