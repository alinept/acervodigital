package br.ufma.les.acervodigital.fachada;

import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;

public interface AcervoDigitalFachada {
	/*##################ArquivoDocumento###################*/
	boolean inserirArquivo(ArquivoDocumento arquivo);
	List<ArquivoDocumento> getDocumentFiles(int idDoc) throws Exception;
	
	/*##################Diretorio###################*/
	
	/*##################Documento###################*/
	boolean inserirDocumento(Documento documento) throws Exception ;
	boolean deletarDocumento(Documento documento) throws Exception ;
	boolean atualizaDocumento(Documento documento) throws Exception ;
	Documento findDocumentoByNome(String nome) throws Exception;
	
	List<Documento> buscarDocumento(String busca, ArrayList<Tag> tags, boolean porTitulo, boolean porDescricao, boolean porConteudo) throws Exception;
	
	/*##################Tag###################*/
	boolean salvarTags(ArrayList<Tag> tags) ;
	List<Tag> findAllTags() throws Exception;
	
	/*##################TipoAcesso###################*/
	TipoAcesso findAcessoByCodigo(int codigo) throws Exception ;
	
	/*##################Usuario###################*/
	Usuario buscarUsuario(String login, String senha) throws Exception ;
	public void inserirUsuario(Usuario usuario) throws Exception ;
	public void alterarUsuario(Usuario usuario) throws Exception ;
	public void excluirUsuario(Usuario usuario) throws Exception ;
	
}
