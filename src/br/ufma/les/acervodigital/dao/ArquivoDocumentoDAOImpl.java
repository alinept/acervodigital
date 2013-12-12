package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<ArquivoDocumento> getDocumentFiles(int idDoc) throws Exception{
		ArrayList<ArquivoDocumento> r = new ArrayList<ArquivoDocumento>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT " +
				"* FROM arquivo WHERE fk_documento = ? ORDER BY indice ASC;");
		statement.setInt(1, idDoc);
		
		ResultSet resultSet = statement.executeQuery();
		while( resultSet.next() ){
			String filename = resultSet.getString(1);
			byte [] byteStream = resultSet.getBytes(2);
			
			r.add( new DocumentFile(filename, byteStream) );
		}
		return r;
	}

}
