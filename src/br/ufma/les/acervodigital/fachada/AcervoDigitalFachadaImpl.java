package br.ufma.les.acervodigital.fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufma.les.acervodigital.dao.ArquivoDocumentoDAO;
import br.ufma.les.acervodigital.dao.ArquivoDocumentoDAOImpl;
import br.ufma.les.acervodigital.dao.DiretorioDAO;
import br.ufma.les.acervodigital.dao.DiretorioDAOImpl;
import br.ufma.les.acervodigital.dao.DocumentoDAO;
import br.ufma.les.acervodigital.dao.DocumentoDAOImpl;
import br.ufma.les.acervodigital.dao.TagDAO;
import br.ufma.les.acervodigital.dao.TagDAOImpl;
import br.ufma.les.acervodigital.dao.TagDocumentoDAO;
import br.ufma.les.acervodigital.dao.TagDocumentoDAOImpl;
import br.ufma.les.acervodigital.dao.TipoAcessoDAO;
import br.ufma.les.acervodigital.dao.TipoAcessoDAOImpl;
import br.ufma.les.acervodigital.dao.UsuarioDAO;
import br.ufma.les.acervodigital.dao.UsuarioDAOImpl;
import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Diretorio;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TagDocumento;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;
import br.ufma.les.acervodigital.treemodel.ObjectSql;

public class AcervoDigitalFachadaImpl implements AcervoDigitalFachada{

	ArquivoDocumentoDAO arquivoDocumentoDAO;
	DiretorioDAO diretorioDAO;
	DocumentoDAO documentoDAO;
	TagDAO tagDAO;
	TipoAcessoDAO tipoAcessoDAO;
	UsuarioDAO usuarioDAO;
	TagDocumentoDAO tagDocumentoDAO;
	
	public AcervoDigitalFachadaImpl()
	{
		arquivoDocumentoDAO = new ArquivoDocumentoDAOImpl();
		diretorioDAO = new DiretorioDAOImpl();
		documentoDAO = new DocumentoDAOImpl();
		tagDAO = new TagDAOImpl();
		tipoAcessoDAO = new TipoAcessoDAOImpl();
		usuarioDAO = new UsuarioDAOImpl();
		tagDocumentoDAO = new TagDocumentoDAOImpl();
	}
	
	@Override
	public boolean inserirArquivo(ArquivoDocumento arquivo) {
		return arquivoDocumentoDAO.inserirArquivo(arquivo);
	}

	@Override
	public boolean inserirDocumento(Documento documento) throws Exception {
		return documentoDAO.inserirDocumento(documento);
	}

	@Override
	public boolean deletarDocumento(Documento documento) throws Exception {
		return documentoDAO.deletarDocumento(documento);
	}

	@Override
	public boolean atualizaDocumento(Documento documento) throws Exception {
		return documentoDAO.atualizaDocumento(documento);
	}

	@Override
	public boolean salvarTags(ArrayList<Tag> tags) {
		return tagDAO.salvarTags(tags);
	}

	@Override
	public TipoAcesso findAcessoByCodigo(int codigo) throws Exception {
		return tipoAcessoDAO.findByCodigo(codigo);
	}

	@Override
	public Usuario buscarUsuario(String login, String senha) throws Exception {
		return usuarioDAO.buscarUsuario(login, senha);
	}

	@Override
	public void inserirUsuario(Usuario usuario) throws Exception {
		
		usuarioDAO.inserirUsuario(usuario);
		
	}

	@Override
	public void alterarUsuario(Usuario usuario) throws Exception {
		
		usuarioDAO.alterarUsuario(usuario);
		
	}

	@Override
	public void excluirUsuario(Usuario usuario) throws Exception {
		
		usuarioDAO.excluirUsuario(usuario);
		
	}

	@Override
	public List<Documento> buscarDocumento(String busca, ArrayList<Tag> tags,
			boolean porTitulo, boolean porDescricao, boolean porConteudo)
			throws Exception {
		return documentoDAO.buscar(busca, tags, porTitulo, porDescricao, porConteudo);
	}

	@Override
	public List<ArquivoDocumento> getDocumentFiles(int idDoc) throws Exception {
		return arquivoDocumentoDAO.getDocumentFiles(idDoc);
	}

	@Override
	public Documento findDocumentoByNome(String nome) throws Exception {
		return documentoDAO.findByNome(nome);
	}

	@Override
	public List<Tag> findAllTags() throws Exception {
		return tagDAO.findAll();
	}

	@Override
	public Diretorio findDiretorioByCodigo(int codigo) throws Exception {
		return diretorioDAO.findDiretorioByCodigo(codigo);
	}

	@Override
	public void inserirDiretorio(Diretorio diretorio) throws SQLException,
			Exception {
		diretorioDAO.inserirDiretorio(diretorio);
	}

	@Override
	public void alterarDiretorio(Diretorio diretorio) throws SQLException,
			Exception {
		diretorioDAO.alterarDiretorio(diretorio);
	}

	@Override
	public void excluirDiretorio(int idDiretorio) throws SQLException,
			Exception {
		
		diretorioDAO.excluirDiretorio(idDiretorio);
		
	}

	@Override
	public List<ObjectSql> retornaCaminhoDiretoriosAbaixo(int id)
			throws SQLException, Exception {
		return diretorioDAO.retornaCaminhoDiretoriosAbaixo(id);
	}

	@Override
	public List<ObjectSql> retornaCaminhoDiretorioRaiz() throws SQLException,
			Exception {
		return diretorioDAO.retornaCaminhoDiretorioRaiz();
	}

	@Override
	public boolean isLoginUsuarioValido(String login) throws Exception {
		return usuarioDAO.isLoginValido(login);
	}

	@Override
	public List<Usuario> usuariosNaoValidados() throws Exception {
		
		return usuarioDAO.usuariosNaoValidados();
	}

	@Override
	public List<Usuario> findUsuarioByNome(String nome) throws Exception {
		
		return usuarioDAO.findByNome(nome);
	}

	@Override
	public Diretorio findDiretorioByNome(String nome) throws Exception {
		return diretorioDAO.findDiretorioByNome(nome);
	}

	@Override
	public List<ObjectSql> findAllDiretorios() throws SQLException, Exception {
		return diretorioDAO.findAll();
	}

	@Override
	public Tag findTagByNome(String nome) throws Exception {
		return tagDAO.findByNome(nome);
	}

	@Override
	public void inserirTagDocumento(TagDocumento tagDocumento) throws Exception {
		
		tagDocumentoDAO.inserir(tagDocumento);
		
	}

	@Override
	public List<TipoAcesso> findAllTiposAcessos() throws Exception {
		
		return tipoAcessoDAO.findAll();
	}

	@Override
	public List<Documento> buscaAvancada(String busca,
			ArrayList<TagDocumento> tags, boolean porTitulo,
			boolean porDescricao, boolean porConteudo, int idDiretorio)
			throws Exception {
		
		return documentoDAO.buscaAvancada(busca, tags, porTitulo, porDescricao, porConteudo, idDiretorio);
	}

	@Override
	public List<Documento> ultimosDocumentosEnviados() throws Exception {
		return documentoDAO.ultimosEnvios();
	}
	

}
