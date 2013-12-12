package br.ufma.les.acervodigital.fachada;

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
import br.ufma.les.acervodigital.dao.TipoAcessoDAO;
import br.ufma.les.acervodigital.dao.TipoAcessoDAOImpl;
import br.ufma.les.acervodigital.dao.UsuarioDAO;
import br.ufma.les.acervodigital.dao.UsuarioDAOImpl;
import br.ufma.les.acervodigital.dominio.ArquivoDocumento;
import br.ufma.les.acervodigital.dominio.Documento;
import br.ufma.les.acervodigital.dominio.Tag;
import br.ufma.les.acervodigital.dominio.TipoAcesso;
import br.ufma.les.acervodigital.dominio.Usuario;

public class AcervoDigitalFachadaImpl implements AcervoDigitalFachada{

	ArquivoDocumentoDAO arquivoDocumentoDAO;
	DiretorioDAO diretorioDAO;
	DocumentoDAO documentoDAO;
	TagDAO tagDAO;
	TipoAcessoDAO tipoAcessoDAO;
	UsuarioDAO usuarioDAO;
	
	public AcervoDigitalFachadaImpl()
	{
		arquivoDocumentoDAO = new ArquivoDocumentoDAOImpl();
		diretorioDAO = new DiretorioDAOImpl();
		documentoDAO = new DocumentoDAOImpl();
		tagDAO = new TagDAOImpl();
		tipoAcessoDAO = new TipoAcessoDAOImpl();
		usuarioDAO = new UsuarioDAOImpl();
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
	

}
