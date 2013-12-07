package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO{

	TipoAcessoDAO tipoAcessoDAO;
	
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
    
	public void inserirUsuario(Usuario usuario){
		
		try{
		PreparedStatement statement = Conexao.get().prepareStatement(
				"INSERT INTO " + "usuario(login,nome,email,senha) values ("
						+ usuario.getLogin() + "," + usuario.getNome() + ","
						+ usuario.getEmail() + "," + usuario.getSenha()+")" );
		
		statement.executeUpdate();
		statement.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
