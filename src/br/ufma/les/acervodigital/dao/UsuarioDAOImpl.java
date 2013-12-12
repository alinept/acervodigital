package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Diretorio;
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
			
		}		
				
		return usuario;
	}
    
	@Override
	public void inserirUsuario(Usuario usuario) throws Exception{
		
		try{
		PreparedStatement statement = Conexao.get().prepareStatement(
				"INSERT INTO " + "usuario(login,nome,email,senha) values (?,?,?,?)");
		
		statement.setString(1, usuario.getLogin());
		statement.setString(2, usuario.getNome());
		statement.setString(3, usuario.getEmail());
		statement.setString(4, usuario.getSenha());
		statement.executeUpdate();
		statement.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void alterarUsuario(Usuario usuario) throws Exception{
		
		String sql = "update usuario set login = ?, nome = ?, email = ?, senha = ?  where id = "
				+ usuario.getId();
		
		try{
			PreparedStatement statement = Conexao.get().prepareStatement(sql);
			
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getNome());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getSenha());
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

			TipoAcesso tipoAcesso = new TipoAcesso();
			int codigoAcesso = resultSet.getInt("fk_perfil");
			tipoAcesso = tipoAcessoDAO.findByCodigo(codigoAcesso);
			
			usuario.setTipoAcesso(tipoAcesso);
			
			
		}		
				
		return usuario;
	}
}
