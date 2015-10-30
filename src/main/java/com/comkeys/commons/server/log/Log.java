package com.comkeys.commons.server.log;

public class Log {
	private String errMessage;
	private String errURL;
	private int line;
	private int col;
	
	/**
	 * @return message d'erreur 
	 */
	public String getErrMessage() {
		return errMessage;
	}
	
	/**
	 * @return URL de la page occurante
	 */
	public String getErrURL(){
		return errURL;
	}
	
	/**
	 * @return ligne provoquant l'erreur
	 */
	public int getLine(){
		return line;
	}
	
	/**
	 * @return colonne provoquant l'erreur
	 */
	public int getCol(){
		return col;
	}
}
