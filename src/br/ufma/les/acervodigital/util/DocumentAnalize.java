package br.ufma.les.acervodigital.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class DocumentAnalize {
	
	/**
	 * Retorna o texto do documento. Nao aconselhavel para tipos HTML, para tais prefira
	 * {@link #getText(String, short)}
	 * @param inputStream stream de bytes do arquivo a ser analizado.
	 * @param type tipo do arquivo: uma das constantes de {@link FileSupportVerificator}
	 * @see FileSupportVerificator#isDocumentValid(String)
	 */
	
	public static String getText(InputStream inputStream, short type) throws IOException{
		switch (type) {
			case FileSupportVerificator.PDF_TYPE:	return PDF2Text(inputStream);
			default: return null;
		}
	}
	
	/**
	 * Retorna o texto do documento. Adequado para documentos HTML.
	 * @param inputStream stream de bytes do arquivo a ser analizado.
	 * @param type tipo do arquivo, precisa ser
	 * {@link FileSupportVerificator#HTML_TYPE}
	 * @see FileSupportVerificator#isDocumentValid(String)
	 */
	public static String getText(String stream, short type) throws IOException{
		switch (type) {
			case FileSupportVerificator.PDF_TYPE:
				byte [] bytes = stream.getBytes();
				return getText(new ByteArrayInputStream(bytes), type);
			default: return null;
		}
	}
	
	/**
	 * Extrai texto de documento.<br/>
	 * Prefira {@link #getText(InputStream, short) ou {@link #getText(String, short)}}
	 * @see FileSupportVerificator#isDocumentValid(String)
	 */
	@Deprecated
	public static String getText(Reader reader, short type) throws IOException{
		switch(type){
		
			case FileSupportVerificator.PDF_TYPE:
				return getText(Reader2StringConverter.get(reader), type);
			
			default: return null;
		}
	}
	
	
	// PDF (texto)
	private static String PDF2Text(InputStream inputStream) throws IOException{
		PDFParser parser = new PDFParser(inputStream);
		parser.parse();
		PDDocument pdfDocument = parser.getPDDocument();
		PDFTextStripper stripper = new PDFTextStripper();
		return stripper.getText(pdfDocument);
	}
	
	
}
