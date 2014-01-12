package br.ufma.les.acervodigital.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dominio.Tag;

public interface TagDAO {

	boolean salvarTags(ArrayList<Tag> tags);
	List<Tag> findAll() throws Exception;
	Tag findByNome(String nome) throws Exception;

}
