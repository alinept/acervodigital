package br.ufma.les.acervodigital.dao;

import java.sql.SQLException;
import java.util.List;

import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.treemodel.ObjectSql;

public interface DiretorioDAO {
	
	Diretorio findDiretorioByCodigo(int codigo) throws Exception;
	Diretorio findDiretorioByNome(String nome) throws Exception;
	void inserirDiretorio(Diretorio diretorio) throws SQLException, Exception;
	void alterarDiretorio(Diretorio diretorio) throws SQLException, Exception;
	void excluirDiretorio(int idDiretorio) throws SQLException, Exception;
	List<ObjectSql> retornaCaminhoDiretoriosAbaixo(int id) throws SQLException, Exception;
	List<ObjectSql> retornaCaminhoDiretorioRaiz() throws SQLException, Exception;
	List<Diretorio> carregarDiretorios() throws SQLException, Exception;
	public abstract List<Diretorio> carregarFilhos(int idDiretorio) throws SQLException,
			Exception;
	public abstract List<Diretorio> carregarDiretoriosRoot() throws SQLException,
			Exception;

	List<ObjectSql> findAll() throws Exception;
	
}
