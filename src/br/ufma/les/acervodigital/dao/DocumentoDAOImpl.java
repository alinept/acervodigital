package br.ufma.les.acervodigital.dao;

import java.sql.Date;
import java.sql.PreparedStatement;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Documento;

public class DocumentoDAOImpl implements DocumentoDAO{

	

	@Override
	public boolean inserirDocumento(Documento documento) throws Exception {
		
		PreparedStatement statement =
				Conexao.get().prepareStatement("INSERT INTO documento (data_criacao, data_upload, fk_proprietario, conteudo, fk_diretorio) " +
						"VALUES (? , ? , ? , ?)");
			
			statement.setDate(1, (Date) documento.getDataExpedicao() );
			statement.setDate(2, (Date) documento.getDataUpload() );
			statement.setInt(3, documento.getProprietario().getId());
			statement.setString(4, documento.getConteudo());
			statement.setInt(5, documento.getDiretorio().getId());
			
			statement.executeUpdate();
			statement.close();
			
			//salvar tags
	
			return true;
	}

	@Override
	public boolean deletarDocumento(Documento documento) throws Exception {
		PreparedStatement statement = Conexao.get().prepareStatement("DELETE FROM arquivo WHERE fk_documento = ?;");
		statement.setInt(1, documento.getId());
		statement.executeUpdate();
		
		statement = Conexao.get().prepareStatement("DELETE FROM documento WHERE id_documento = ?;");
		statement.setInt(1, documento.getId());
		statement.executeUpdate();
		
		return true;
	}

	@Override
	public boolean atualizaDocumento(Documento documento) throws Exception {
		
//			PreparedStatement statement =
//				DatabaseConnection.get().prepareStatement("UPDATE documento SET titulo = ?, " +
//						"descricao = ?, data_expedicao = ? WHERE id_doc = ?;");
//			statement.setString(1, newTitle);
//			statement.setString(2, newDesc);
//			statement.setDate(3, new java.sql.Date( newExpDate.getTime() ));
//			statement.setInt(4, idDoc);
//			statement.executeUpdate();
		
		return true;
	}

	
	

}

