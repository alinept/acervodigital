package br.ufma.les.acervodigital.util;

public class FileSupportVerificator {
	
	
	/** Constante que define o tipo: Portable Document Format */
	public static final short PDF_TYPE = 0x03;
	

	/**
	 * Retorna se o arquivo e suportado pelo sistema.
	 */
	public static boolean isValid(String filename){
		return
			isDocumentValid(filename);
	}
	
	/**
	 * Retorna se o arquivo e um documento suportado pelo sistema.
	 */
	public static boolean isDocumentValid(String filename){
		return
			isPDFType(filename);
	}
	
	
	public static boolean isPDFType(String filename){
		return filename.endsWith(".pdf") || filename.endsWith(".PDF");
	}
	
	
	public static String DocDescription() {
		return
			"Portable Document Format PDF\n";
	}
}
