package br.ufma.les.acervodigital.dao;

import java.util.ArrayList;
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

}
