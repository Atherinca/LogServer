package com.comkeys.commons.server.log;

public class LogAngular {
	private String errUrl;
	private String errMessage;
	private String [] stackTrace;
	
	public String getErrUrl(){
		return errUrl;
	}
	public String getErrMessage(){
		return errMessage;
	}
	public String [] getStackTrace(){
		return stackTrace;
	}
}
