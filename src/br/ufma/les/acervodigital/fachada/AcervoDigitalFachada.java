package br.ufma.les.acervodigital.fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TagDocumento;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.treemodel.ObjectSql;

public interface AcervoDigitalFachada {
	/*##################ArquivoDocumento###################*/
	boolean inserirArquivo(ArquivoDocumento arquivo);
	List<ArquivoDocumento> getDocumentFiles(int idDoc) throws Exception;
	
	/*##################TagDocumento###################*/
	void inserirTagDocumento(TagDocumento tagDocumento) throws Exception ;
	
	
	/*##################Diretorio###################*/
	Diretorio findDiretorioByCodigo(int codigo) throws Exception;
	Diretorio findDiretorioByNome(String nome) throws Exception;
	void inserirDiretorio(Diretorio diretorio) throws SQLException, Exception;
	void alterarDiretorio(Diretorio diretorio) throws SQLException, Exception;
	void excluirDiretorio(int idDiretorio) throws SQLException, Exception;
	List<ObjectSql> retornaCaminhoDiretoriosAbaixo(int id) throws SQLException, Exception;
	List<ObjectSql> retornaCaminhoDiretorioRaiz() throws SQLException, Exception;
	List<ObjectSql> findAllDiretorios() throws SQLException, Exception;
	
	/*##################Documento###################*/
	boolean inserirDocumento(Documento documento) throws Exception ;
	boolean deletarDocumento(Documento documento) throws Exception ;
	boolean atualizaDocumento(Documento documento) throws Exception ;
	Documento findDocumentoByNome(String nome) throws Exception;
	
	List<Documento> ultimosDocumentosEnviados() throws Exception;
	List<Documento> buscarDocumento(String busca, ArrayList<Tag> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo) throws Exception;
	List<Documento> buscaAvancada(String busca, ArrayList<TagDocumento> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo, int idDiretorio) throws Exception;
	/*##################Tag###################*/
	boolean salvarTags(ArrayList<Tag> tags) ;
	List<Tag> findAllTags() throws Exception;
	Tag findTagByNome(String nome) throws Exception;
	
	/*##################TipoAcesso###################*/
	TipoAcesso findAcessoByCodigo(int codigo) throws Exception ;
	List<TipoAcesso> findAllTiposAcessos() throws Exception ;
	
	/*##################Usuario###################*/
	Usuario buscarUsuario(String login, String senha) throws Exception ;
	public void inserirUsuario(Usuario usuario) throws Exception ;
	public void alterarUsuario(Usuario usuario) throws Exception ;
	public void excluirUsuario(Usuario usuario) throws Exception ;
	boolean isLoginUsuarioValido(String login) throws Exception ;
	List<Usuario> usuariosNaoValidados() throws Exception ;
	List<Usuario> findUsuarioByNome(String nome) throws Exception ;
}
