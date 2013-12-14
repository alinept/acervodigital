package br.ufma.les.acervodigital.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Usuario;

public class DiretorioDAOImpl implements DiretorioDAO{

	private UsuarioDAO usuarioDAO;
	
	public DiretorioDAOImpl()
	{
		usuarioDAO = new UsuarioDAOImpl(); 
	}
	
	@Override
	public Diretorio findDiretorioByCodigo(int codigo) throws Exception{
		
		Diretorio diretorio = new Diretorio();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"diretorio WHERE id_diretorio = ? ");
		statement.setInt(1, codigo);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			diretorio.setDataCriacao(resultSet.getDate("data_criacao"));
			diretorio.setId(resultSet.getInt("id_diretorio"));
			diretorio.setName(resultSet.getString("nome"));
			diretorio.setProprietario(usuarioDAO.findByCodigo(resultSet.getInt("fk_proprietario")));
			
		}		
				
		return diretorio;
	}
	
  public void UsuarioDiretorio(Usuario usuario) throws SQLException, Exception{
	  
	  Diretorio dir = new Diretorio();
	  int diretorio_pai = 0;
	  
	  PreparedStatement sql = Conexao.get().prepareStatement("SELECT id_diretorio FROM diretorio WHERE proprietario = "+ usuario.getId()+"");
	  
	  ResultSet resultSet = sql.executeQuery();
	  if(resultSet.next()){
		  diretorio_pai = resultSet.getInt("diretorio_pai");	  
	  }
	  
		
		  PreparedStatement statement = Conexao.get().prepareStatement(
		  "WITH RECURSIVE arvore_diretorios(id,nome) AS (id_diretorio,nome FROM diretorio WHERE diretorio_pai="
		  + diretorio_pai+"");
		  
    
		 
  }
  
  public void InserirDiretorio(Diretorio diretorio) throws SQLException, Exception{
	  
		PreparedStatement statement = Conexao
				.get()
				.prepareStatement(
						"INSERT INTO"
								+ "diretorio(id_diretorio,nome,data_criacao,proprietario,diretorio_pai) VALUES (?,?,?,?,?)");
		statement.setInt(1, diretorio.getId());
		statement.setString(2, diretorio.getName());
		statement.setDate(3, (Date) diretorio.getDataCriacao());
		statement.setInt(4, diretorio.getProprietario().getId());
		statement.setInt(5, diretorio.getPai().getId());
		statement.executeUpdate();
		statement.close();

  }
  
  public void alterarDiretorio(Diretorio diretorio) throws SQLException, Exception{
	  
	  PreparedStatement statement = Conexao.get().prepareStatement("UPDATE diretorio set nome=?,proprietario=?,diretorio_pai=? WHERE id_diretorio=?");
	  
	  statement.setString(1, diretorio.getName());
	  statement.setInt(2,diretorio.getProprietario().getId());
	  statement.setInt(3, diretorio.getPai().getId());
	  statement.setInt(4, diretorio.getId());
	  statement.executeUpdate();
	  statement.close();
	  
  }
  
  public void excluirDiretorio(int idDiretorio) throws SQLException, Exception{
	  
	  PreparedStatement statement = Conexao.get().prepareStatement("delete from where="+idDiretorio);
	  statement.executeUpdate();
	  statement.close();
  }
  
}
