package br.ufma.les.acervodigital.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.ufma.les.acervodigital.database.Conexao;
import br.ufma.les.acervodigital.dominio.TipoAcesso;

public class TipoAcessoDAOImpl implements TipoAcessoDAO{

	@Override
	public TipoAcesso findByCodigo(int codigo) throws Exception {
		TipoAcesso tipoAcesso = new TipoAcesso();
		
		PreparedStatement statement = Conexao.get().prepareStatement("SELECT * FROM " +
				"perfil WHERE id_perfil = ? ");
		statement.setInt(1, codigo);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			
			tipoAcesso.setDescricao(resultSet.getString("descricao"));
			tipoAcesso.setId(resultSet.getInt("id_perfil"));
			tipoAcesso.setNome(resultSet.getString("nome"));
			tipoAcesso.setPermissao(resultSet.getInt("permissao"));
		}		
				
		return tipoAcesso;
	}

}
