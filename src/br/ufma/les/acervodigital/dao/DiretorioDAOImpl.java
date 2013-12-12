package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Diretorio;

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
	

}
