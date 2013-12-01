package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.ArquivoDocumento;

public class ArquivoDocumentoDAOImpl implements ArquivoDocumentoDAO{

	@Override
	public boolean inserirArquivo(ArquivoDocumento arquivo) {

		try {
			Conexao.get().setAutoCommit(false);
			PreparedStatement statement = Conexao.get().prepareStatement(
					"INSERT INTO arquivo (fk_documento, nome, dados) VALUES (?, ?, ?)");
			
				statement.setInt(1, arquivo.getDocumento().getId());
				statement.setString(2, arquivo.getNomeArquivo());
				statement.setBytes(3, arquivo.getByteStream());
			
				statement.executeUpdate();
				Conexao.get().commit();
				statement.close();
			
				Conexao.get().setAutoCommit(true);

				return true;
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

}
