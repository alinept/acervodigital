package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachada;
import br.ufma.les.acervodigital.fachada.AcervoDigitalFachadaImpl;

public class UsuarioDAOImpl implements UsuarioDAO{

	TipoAcessoDAO tipoAcessoDAO;
	AcervoDigitalFachada acervo;
	
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
			
			if(tipoAcesso.getNome().equals("moderador"))
			{
				usuario.setIdDiretorio(resultSet.getInt("fk_diretorio"));
			}
			
			usuario.setTipoAcesso(tipoAcesso);
			
			return usuario;
		}		
	
		return null;
		
		
	}
    
	@Override
	public void inserirUsuario(Usuario usuario) throws Exception{
		
		try{
		PreparedStatement statement = Conexao.get().prepareStatement(
				"INSERT INTO " + "usuario(login,nome,email,senha,fk_perfil, fk_diretorio) values (?,?,?,?,?,?)");
		
		statement.setString(1, usuario.getLogin());
		statement.setString(2, usuario.getNome());
		statement.setString(3, usuario.getEmail());
		statement.setString(4, usuario.getSenha());
		statement.setInt(5, usuario.getTipoAcesso().getId());
		if(usuario.getTipoAcesso().getNome().equals("moderador")) statement.setInt(6, usuario.getIdDiretorio());
		else statement.setInt(6, 0);
		statement.executeUpdate();
		statement.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void alterarUsuario(Usuario usuario) throws Exception{
		
		String sql = "update usuario set login = ?, nome = ?, email = ?, senha = ? where id_usuario = "
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
		
		String sql = "DELETE usuario where id = "+ usuario.getId();
		
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
			
			if(tipoAcesso.getNome().equals("moderador"))
			{
				usuario.setIdDiretorio(resultSet.getInt("fk_diretorio"));
			}
			
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
	public List<Usuario> usuariosNaoValidados() throws Exception {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"usuario ");
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			Usuario u = new Usuario();
			u.setEmail(resultSet.getString("email"));
			u.setId(resultSet.getInt("id_usuario"));
			u.setLogin(resultSet.getString("login"));
			u.setNome(resultSet.getString("nome"));
			u.setTipoAcesso(tipoAcessoDAO.findByCodigo(resultSet.getInt("fk_perfil")));
			u.setSenha(resultSet.getString("senha"));
			
			if(u.getTipoAcesso().getNome().equals("moderador"))
			{
				u.setIdDiretorio(resultSet.getInt("fk_diretorio"));
			}
			
			usuarios.add(u);
		}		
				
		return usuarios;
	}

	@Override
	public List<Usuario> findByNome(String nome) throws Exception {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		nome = "%"+nome+"%";
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"usuario WHERE nome like ? ");
		statement.setString(1, nome);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			Usuario usuario = new Usuario();
			
			usuario.setEmail(resultSet.getString("email"));
			usuario.setId(resultSet.getInt("id_usuario"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setSenha(resultSet.getString("senha"));
			TipoAcesso tipoAcesso = new TipoAcesso();
			int codigoAcesso = resultSet.getInt("fk_perfil");
			tipoAcesso = tipoAcessoDAO.findByCodigo(codigoAcesso);
			
			if(tipoAcesso.getNome().equals("moderador"))
			{
				usuario.setIdDiretorio(resultSet.getInt("fk_diretorio"));
			}
			usuario.setTipoAcesso(tipoAcesso);
			
			usuarios.add(usuario);
		}		
				
		return usuarios;
	}
}
