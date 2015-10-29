package com.comkeys.commons.server.log;

public class Log {
	private String errMessage;
	private String errURL;
	private int line;
	private int col;
	
	public String getErrMessage() {
		return errMessage;
	}
	
	public String getErrURL(){
		return errURL;
	}
	
	public int getLine(){
		return line;
	}
	
	public int getCol(){
		return col;
	}
}
