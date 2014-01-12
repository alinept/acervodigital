package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.TagDocumento;

public class TagDocumentoDAOImpl implements TagDocumentoDAO{

	@Override
	public void inserir(TagDocumento tagDocumento) throws Exception {
		
		PreparedStatement statement = Conexao
				.get()
				.prepareStatement(
						"INSERT INTO "
								+ "tag_documento(fk_documento, fk_tag, conteudo) VALUES (?,?,?)");

		statement.setInt(1, tagDocumento.getDocumento().getId());
		statement.setInt(2, tagDocumento.getTag().getId());
		statement.setString(3, tagDocumento.getConteudo());
		
		statement.executeUpdate();
		
		statement.close();

		
	}

}
