package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;

public class DocumentoDAOImpl implements DocumentoDAO{

	private UsuarioDAO usuarioDAO;
	private DiretorioDAO diretorioDAO;
	
	public DocumentoDAOImpl()
	{
		usuarioDAO = new UsuarioDAOImpl();
		diretorioDAO = new DiretorioDAOImpl();
	}
	
	@Override
	public boolean inserirDocumento(Documento documento) throws Exception {
		 int id = 0;
		 java.sql.Date dataExpedicao = new java.sql.Date(documento.getDataExpedicao().getTime());
		 java.sql.Date dataUpload = new java.sql.Date(documento.getDataUpload().getTime());
		
		PreparedStatement statement =
				Conexao.get().prepareStatement("INSERT INTO documento (data_criacao, data_upload, fk_proprietario, conteudo, fk_diretorio) " +
						"VALUES (? , ? , ? , ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			statement.setDate(1, dataExpedicao);
			statement.setDate(2, dataUpload);
			statement.setInt(3, documento.getProprietario().getId());
			statement.setString(4, documento.getConteudo());
			statement.setInt(5, 1);
			
			statement.executeUpdate();
			ResultSet rsId = statement.getGeneratedKeys();
			if(rsId.next()){
				id = rsId.getInt("id_documento");
			}
			
			statement.close();
			documento.setId(id);
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

	@Override
	public List<Documento> buscar(String busca, ArrayList<Tag> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo) throws Exception{
		List<Documento> r = new ArrayList<Documento>();
		
		PreparedStatement statement;
		ResultSet docResultSet;
		
		List<String> searchWords = getStrings(busca);
		
		if(searchWords.isEmpty() ) return r;
		
		// PRE-MONTANDO A STRING SQL
		String sql = "SELECT * FROM documento WHERE ";
		
		for(int i=0; i<searchWords.size(); i++){
			sql += "(";
			if( porTitulo ){
				sql += "titulo ~* ?";
				if( porDescricao || porConteudo ) sql += " OR ";
			}
			if( porDescricao ){
				sql += "descricao ~* ?";
				if( porConteudo ) sql += " OR ";
			}
			if( porConteudo )	sql += "conteudo ~* ?";
			
			sql += ") ";
			if(i < searchWords.size() - 1){
				sql += "AND ";
			}
		}
		sql += "ORDER BY data_criacao DESC LIMIT 50;";
		// STRING SQL PRE-MONTADA
		
		statement = Conexao.get().prepareStatement(sql);
		int parameterIndex = 1;
		
		// TERMINANDO DE MONTAR A STRING SQL DENTRO DO STATEMENT
		ListIterator<String> iterator = searchWords.listIterator();
		
		while( iterator.hasNext() ){
		
			String current = iterator.next();
			if( porTitulo ){
				statement.setString(parameterIndex, current);
				parameterIndex++;
			}
			if( porDescricao ){
				statement.setString(parameterIndex, current);
				parameterIndex++;
			}
			if( porConteudo ){
				statement.setString(parameterIndex, current);
				parameterIndex++;
			}
		}
		// COMANDO SQL PRONTO
		
		// fogo!
		docResultSet = statement.executeQuery();
		
		while( docResultSet.next() ){
			
			r.add(findByCodigo(docResultSet.getInt("id_documento")));
		}
		
		return r;
	}
	
	private List<String> getStrings(String search){
		List<String> r = new ArrayList<String>();
		
		if( isEmpty(search) ) return r;
		String cur = "";
		for(int i=0; i<search.length(); i++){
			if( search.charAt(i) == ' ' ){
				if( cur.isEmpty() )	continue;
				else{
					r.add(cur);
					cur = new String("");
				}
			}
			else if( search.charAt(i) == '"' ){
				for(i++; i<search.length(); i++){
					if( search.charAt(i) == '"' ){
						if( !cur.isEmpty() ){
							r.add(cur);
							cur = new String("");
						}
						break;
					}
					else cur += search.charAt(i);
				}
			}
			else cur += search.charAt(i);
		}
		if( !cur.isEmpty() ) r.add(cur);
		return r;
	}
	
	private boolean isEmpty(String str){
    	if(str == null) return true;
    	for(int i=0; i<str.length(); i++){
    		if(Character.isLetterOrDigit( str.charAt(i) )){
    			return false;
    		}
    	}
    	return true;
    }

	@Override
	public Documento findByCodigo(int codigo) throws Exception {
		Documento documento = new Documento();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"documento WHERE id_documento  = ? ");
		statement.setInt(1, codigo);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			documento.setId(resultSet.getInt("id_documento"));
			documento.setConteudo(resultSet.getString("conteudo"));
			documento.setDataExpedicao(resultSet.getDate("data_criacao"));
			documento.setDataUpload(resultSet.getDate("data_upload"));
			documento.setDiretorio(diretorioDAO.findDiretorioByCodigo(resultSet.getInt("fk_diretorio")));
			documento.setProprietario(usuarioDAO.findByCodigo(resultSet.getInt("fk_proprietario")));
			documento.setTitulo(resultSet.getString("titulo"));
			documento.setDescricao(resultSet.getString("descricao"));

			//documento.setTags(tags);
			
		}
		
		return documento;
	}

	@Override
	public Documento findByNome(String nome) throws Exception {
		Documento documento = new Documento();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"documento WHERE titulo  = ? ");
		statement.setString(1, nome);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			documento.setId(resultSet.getInt("id_documento"));
			documento.setConteudo(resultSet.getString("conteudo"));
			documento.setDataExpedicao(resultSet.getDate("data_criacao"));
			documento.setDataUpload(resultSet.getDate("data_upload"));
			documento.setDiretorio(diretorioDAO.findDiretorioByCodigo(resultSet.getInt("fk_diretorio")));
			documento.setProprietario(usuarioDAO.findByCodigo(resultSet.getInt("fk_proprietario")));
			documento.setTitulo(resultSet.getString("titulo"));
			documento.setDescricao(resultSet.getString("descricao"));

			//documento.setTags(tags);
			
		}
		
		return documento;
	}
	

}

