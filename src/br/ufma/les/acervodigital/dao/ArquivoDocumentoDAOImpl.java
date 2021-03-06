package br.ufma.les.acervodigital.dao;

import java.io.File;
import java.io.FileOutputStream;
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
		
		//File f=null;
		
		ArrayList<ArquivoDocumento> r = new ArrayList<ArquivoDocumento>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT " +
				"* FROM arquivo WHERE fk_documento = ? ORDER BY id_arquivo ASC;");
		statement.setInt(1, idDoc);
		
		ResultSet resultSet = statement.executeQuery();
		
		while( resultSet.next() ){
			ArquivoDocumento a = new ArquivoDocumento();
			
			String filename = resultSet.getString("nome");
			byte [] byteStream = resultSet.getBytes("dados");
			
			a.setByteStream(byteStream);
			a.setNomeArquivo(filename);
			
			r.add(a);
			
			/* f = new File( "C:/Users/JoćoVictor/Documents/" + filename );
	            FileOutputStream fos = new FileOutputStream( f);
	            fos.write(byteStream);
	            fos.close();*/
		}
		return r;
	}
	
	@Override
	public List<ArquivoDocumento> carregaArquivos(int idDiretorio) throws SQLException, Exception{
		ArrayList<ArquivoDocumento> r = new ArrayList<ArquivoDocumento>();
		PreparedStatement statement = Conexao
				.get()
				.prepareStatement(
						"SELECT arquivo.* FROM arquivo,documento,diretorio where arquivo.fk_documento=documento.id_documento and  documento.fk_diretorio=diretorio.id_diretorio and diretorio.id_diretorio=?");
		statement.setInt(1, idDiretorio);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			ArquivoDocumento a = new ArquivoDocumento();
			String nome = rs.getString("nome");
			byte[] dados = rs.getBytes("dados");
			a.setNomeArquivo(nome);
			a.setByteStream(dados);
			r.add(a);
			
		}
		
		return r;
	}

}
