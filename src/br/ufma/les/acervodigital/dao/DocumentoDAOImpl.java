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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizaDocumento(Documento documento) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
