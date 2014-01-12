package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Tag;

public class TagDAOImpl implements TagDAO{

	@Override
	public boolean salvarTags(ArrayList<Tag> tags) {
		
//		for(Tag tag : tags)
//		{
//				PreparedStatement statement =
//					Conexao.get().prepareStatement("INSERT INTO tag_documento () " +
//							"VALUES ()");
//				
//				statement.setDate(1, (Date) documento.getDataExpedicao() );
//				statement.setDate(2, (Date) documento.getDataUpload() );
//				statement.setInt(3, documento.getProprietario().getId());
//				statement.setString(4, documento.getConteudo());
//				statement.setInt(5, documento.getDiretorio().getId());
//				
//				statement.executeUpdate();
//				statement.close();
//				
//				//salvar tags
//		
//				return true;
//		}
		
		return false;
	}

	@Override
	public List<Tag> findAll() throws Exception{
		
		List<Tag> tags = new ArrayList<Tag>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"tag ");
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			Tag tag = new Tag();
			tag.setDescricao(resultSet.getString("descricao"));
			tag.setNome(resultSet.getString("nome"));
			tag.setId(resultSet.getInt("id_tag"));
			
			tags.add(tag);
			
		}		
				
		return tags;
	}

	@Override
	public Tag findByNome(String nome) throws Exception {
		
		Tag tag = new Tag();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"tag WHERE nome = ? ");
		statement.setString(1, nome);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			tag.setId(resultSet.getInt("id_tag"));
			tag.setNome(resultSet.getString("nome"));
			tag.setDescricao(resultSet.getString("descricao"));
		}		
				
		return tag;

	}

}
