package br.ufma.les.acervodigital.dao;

import java.util.ArrayList;

import br.ufma.les.acervodigital.dominio.Tag;

public interface TagDAO {

	boolean salvarTags(ArrayList<Tag> tags);
}
