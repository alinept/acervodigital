package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO{

	TipoAcessoDAO tipoAcessoDAO;
	
	public UsuarioDAOImpl()
	{
		tipoAcessoDAO = new TipoAcessoDAOImpl();
	}
	
	@Override
	public Usuario buscarUsuario(String login, String senha) throws Exception {
		
		Usuario usuario = new Usuario();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"usuario WHERE login = ? AND senha = ?;");
		statement.setString(1, login);
		statement.setString(2, senha);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			usuario.setEmail(resultSet.getString("email"));
			usuario.setId(resultSet.getInt("id_usuario"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSenha(resultSet.getString("senha"));

			TipoAcesso tipoAcesso = new TipoAcesso();
			int codigoAcesso = resultSet.getInt("fk_perfil");
			tipoAcesso = tipoAcessoDAO.findByCodigo(codigoAcesso);
			
			usuario.setTipoAcesso(tipoAcesso);
			
			return usuario;
		}		
	
		return null;
		
		
	}
    
	@Override
	public void inserirUsuario(Usuario usuario) throws Exception{
		
		try{
		PreparedStatement statement = Conexao.get().prepareStatement(
				"INSERT INTO " + "usuario(login,nome,email,senha, validado) values (?,?,?,?,?)");
		
		statement.setString(1, usuario.getLogin());
		statement.setString(2, usuario.getNome());
		statement.setString(3, usuario.getEmail());
		statement.setString(4, usuario.getSenha());
		if(usuario.isValidado()) statement.setInt(5, 1);
		else statement.setInt(5, 0);
		statement.executeUpdate();
		statement.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void alterarUsuario(Usuario usuario) throws Exception{
		
		String sql = "update usuario set login = ?, nome = ?, email = ?, senha = ?, validado = ?  where id = "
				+ usuario.getId();
		
		try{
			PreparedStatement statement = Conexao.get().prepareStatement(sql);
			
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getNome());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getSenha());
			if(usuario.isValidado()) statement.setInt(5, 1);
			else statement.setInt(5, 0);
			statement.executeUpdate();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void excluirUsuario(Usuario usuario) throws Exception{
		
		String sql = "DELETE FROM usuario where id = "+ usuario.getId();
		
		try{
			PreparedStatement statement = Conexao.get().prepareStatement(sql);
			
			statement.executeUpdate();
			statement.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Usuario findByCodigo(int codigo) throws Exception {
		Usuario usuario = new Usuario();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"usuario WHERE id_usuario = ? ");
		statement.setInt(1, codigo);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			usuario.setEmail(resultSet.getString("email"));
			usuario.setId(resultSet.getInt("id_usuario"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSenha(resultSet.getString("senha"));
			if(resultSet.getInt("validado") == 1) usuario.setValidado(true);
			else usuario.setValidado(false);
			TipoAcesso tipoAcesso = new TipoAcesso();
			int codigoAcesso = resultSet.getInt("fk_perfil");
			tipoAcesso = tipoAcessoDAO.findByCodigo(codigoAcesso);
			
			usuario.setTipoAcesso(tipoAcesso);
			
			
		}		
				
		return usuario;
	}

	@Override
	public boolean isLoginValido(String login) throws Exception {
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"usuario WHERE login = ? ");
		statement.setString(1, login);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) return false;
		else return true;
				
		

	}

	@Override
	public List<Usuario> usuariosNaoValidados() {
		
		return null;
	}
}
