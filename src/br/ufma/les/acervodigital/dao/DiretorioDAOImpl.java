package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.treemodel.ObjectSql;

public class DiretorioDAOImpl implements DiretorioDAO{

	private UsuarioDAO usuarioDAO;
	
	public DiretorioDAOImpl()
	{
		usuarioDAO = new UsuarioDAOImpl(); 
	}
	
	
	@Override
	public List<Diretorio> carregarDiretorios() throws SQLException, Exception{
		
		List<Diretorio> colecaoDiretorio= new ArrayList<Diretorio>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM diretorio");
		ResultSet rs = statement.executeQuery();
		
		
		while(rs.next()){
			Diretorio d = new Diretorio();
			d.setId(rs.getInt("id_diretorio"));
			d.setName(rs.getString("nome"));
			d.setIdpai(rs.getInt("diretorio_pai"));
			d.setDataCriacao(rs.getDate("data_criacao"));
			colecaoDiretorio.add(d);
		}
		rs.close();
		return colecaoDiretorio;
		
	}
	
	
@Override
public List<Diretorio> carregarDiretoriosRoot() throws SQLException, Exception{
		
		List<Diretorio> colecaoDiretorio= new ArrayList<Diretorio>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM diretorio WHERE diretorio_pai is null");
		ResultSet rs = statement.executeQuery();
		
		
		while(rs.next()){
			Diretorio d = new Diretorio();
			d.setId(rs.getInt("id_diretorio"));
			d.setName(rs.getString("nome"));
			d.setIdpai(rs.getInt("diretorio_pai"));
			d.setDataCriacao(rs.getDate("data_criacao"));
			d.setProprietario(usuarioDAO.findByCodigo(rs.getInt("fk_proprietario")));
			
			colecaoDiretorio.add(d);
		}
		rs.close();
		return colecaoDiretorio;
		
	}
	
	@Override
	public List<Diretorio> carregarFilhos(int idDiretorio) throws SQLException, Exception{
		
        List<Diretorio> colecaoDiretorio= new ArrayList<Diretorio>();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM diretorio WHERE diretorio_pai="+idDiretorio);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			Diretorio d = new Diretorio();
			d.setId(rs.getInt("id_diretorio"));
			d.setName(rs.getString("nome"));
			d.setIdpai(rs.getInt("diretorio_pai"));
			d.setDataCriacao(rs.getDate("data_criacao"));
			d.setProprietario(usuarioDAO.findByCodigo(rs.getInt("fk_proprietario")));
			
			colecaoDiretorio.add(d);
		}
		rs.close();
		return colecaoDiretorio;
		
		
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
	
	@Override
  public List<ObjectSql> retornaCaminhoDiretoriosAbaixo(int id) throws SQLException, Exception{
	  	
	  List<ObjectSql> estrutura = new ArrayList<ObjectSql>();
	  
	  String sql = "WITH RECURSIVE cte_recursiva (id_diretorio,nome,nivel,arvore)"
			  	+" AS("
			  	+" SELECT id_diretorio"
			  	+" , nome"
			  	+" , 1 AS nivel"
			  	+" , CAST(nome AS VARCHAR(255)) AS arvore"
			  	+" FROM diretorio"
			  	+" WHERE diretorio_pai = ?"
			  	+" UNION ALL"
			  	+" SELECT g.id_diretorio"
			  	+" , g.nome"
			  	+" , c.nivel + 1 AS nivel"
			  	+" , CAST((c.arvore || '/' || g.nome) AS VARCHAR(255)) AS arvore"
			  	+" FROM diretorio g"
			  	+" INNER JOIN cte_recursiva c "
			  	+" ON g.diretorio_pai = c.id_diretorio" 
			  	+" )"
			  	+" SELECT nivel"
			  	+" , arvore "
			  	+" FROM cte_recursiva;";
	      
	  PreparedStatement statement = Conexao.get().prepareStatement(sql);
	  statement.setInt(1, id);
	  
	  ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			ObjectSql obj = new ObjectSql();
			//obj.setNivel(resultSet.getInt("nivel"));
			obj.setCaminhoDiretorio(resultSet.getString("arvore"));
			
			estrutura.add(obj);
						
		}		
	  
	  return estrutura;
  }
  
  @Override
  public List<ObjectSql> retornaCaminhoDiretorioRaiz() throws SQLException, Exception{
	  	
	  List<ObjectSql> estrutura = new ArrayList<ObjectSql>();
	  
	  String sql = "WITH RECURSIVE cte_recursiva (id_diretorio,nome,nivel,arvore)"
			  	+" AS("
			  	+" SELECT id_diretorio"
			  	+" , nome"
			  	+" , 1 AS nivel"
			  	+" , CAST(nome AS VARCHAR(255)) AS arvore"
			  	+" FROM diretorio"
			  	+" WHERE diretorio_pai IS NULL"
			  	+" UNION ALL"
			  	+" SELECT g.id_diretorio"
			  	+" , g.nome"
			  	+" , c.nivel + 1 AS nivel"
			  	+" , CAST((c.arvore || '/' || g.nome) AS VARCHAR(255)) AS arvore"
			  	+" FROM diretorio g"
			  	+" INNER JOIN cte_recursiva c "
			  	+" ON g.diretorio_pai = c.id_diretorio" 
			  	+" )"
			  	+" SELECT nivel"
			  	+" , arvore "
			  	+" FROM cte_recursiva;";
	      
	  PreparedStatement statement = Conexao.get().prepareStatement(sql);
	  
	  ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			ObjectSql obj = new ObjectSql();
			//obj.setNivel(resultSet.getInt("nivel"));
			obj.setCaminhoDiretorio(resultSet.getString("arvore"));
			
			estrutura.add(obj);
						
		}		
	  
	  return estrutura;
  }
  
  @Override
  public void inserirDiretorio(Diretorio diretorio) throws SQLException, Exception{
	  
	    java.sql.Date sqlDate = new java.sql.Date(diretorio.getDataCriacao().getTime());
		PreparedStatement statement = Conexao
				.get()
				.prepareStatement(
						"INSERT INTO "
								+ "diretorio(nome, data_criacao, fk_proprietario, diretorio_pai) VALUES (?,?,?,?)");

		statement.setString(1, diretorio.getName());
		statement.setDate(2, sqlDate);
		statement.setInt(3, diretorio.getProprietario().getId());
		statement.setInt(4, diretorio.getPai().getId());
		
		statement.executeUpdate();
		
		statement.close();

  }
  
  @Override
  public void alterarDiretorio(Diretorio diretorio) throws SQLException, Exception{
	  
	  PreparedStatement statement = Conexao.get().prepareStatement("UPDATE diretorio set nome = ?, diretorio_pai = ? WHERE id_diretorio = ? ");
	  
	  statement.setString(1, diretorio.getName());
	  //statement.setInt(2,diretorio.getProprietario().getId());
	  statement.setInt(2, diretorio.getPai().getId());
	  statement.setInt(3, diretorio.getId());
	  
	  statement.executeUpdate();
	  
	  statement.close();
	  
  }
  
  @Override
  public void excluirDiretorio(int idDiretorio) throws SQLException, Exception{
	  
	  PreparedStatement statement = Conexao.get().prepareStatement("delete from diretorio where id_diretorio="+idDiretorio);
	  statement.executeUpdate();
	  statement.close();
  }
  
  public void excluirArquivosDiretorio(int idDiretorio) throws SQLException, Exception{
	  PreparedStatement statement = Conexao.get().prepareStatement("delete arquivo where="+idDiretorio);
	  statement.executeUpdate();
	  statement.close();
  }


@Override
public Diretorio findDiretorioByNome(String nome) throws Exception {
	Diretorio diretorio = new Diretorio();
	
	PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
			"diretorio WHERE nome = ? ");
	statement.setString(1, nome);
	
	ResultSet resultSet = statement.executeQuery();
	
	if (resultSet.next()) {
		
		diretorio.setDataCriacao(resultSet.getDate("data_criacao"));
		diretorio.setId(resultSet.getInt("id_diretorio"));
		diretorio.setName(resultSet.getString("nome"));
		diretorio.setPai(findDiretorioByCodigo(resultSet.getInt("diretorio_pai")));
		//diretorio.setProprietario(usuarioDAO.findByCodigo(resultSet.getInt("fk_proprietario")));
		
	}		
			
	return diretorio;
}


@Override
public List<ObjectSql> findAll() throws Exception {
	List<ObjectSql> diretorios = new ArrayList<ObjectSql>();
	
	String sql = "WITH RECURSIVE cte_recursiva (id_diretorio,nome,nivel,arvore)"
		  	+" AS("
		  	+" SELECT id_diretorio"
		  	+" , nome"
		  	+" , 1 AS nivel"
		  	+" , CAST(nome AS VARCHAR(255)) AS arvore"
		  	+" FROM diretorio"
		  	+" WHERE diretorio_pai IS NULL"
		  	+" UNION ALL"
		  	+" SELECT g.id_diretorio"
		  	+" , g.nome"
		  	+" , c.nivel + 1 AS nivel"
		  	+" , CAST((c.arvore || '/' || g.nome) AS VARCHAR(255)) AS arvore"
		  	+" FROM diretorio g"
		  	+" INNER JOIN cte_recursiva c "
		  	+" ON g.diretorio_pai = c.id_diretorio" 
		  	+" )"
		  	+" SELECT id_diretorio"
		  	+" , arvore "
		  	+" FROM cte_recursiva;";
	
	PreparedStatement statement = Conexao.get().prepareStatement(sql);
	  
	  ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			ObjectSql obj = new ObjectSql();
			obj.setCodigo(resultSet.getInt("id_diretorio"));
			obj.setCaminhoDiretorio(resultSet.getString("arvore"));
			
			diretorios.add(obj);
						
		}	
			
		return diretorios;
	}
  
}
