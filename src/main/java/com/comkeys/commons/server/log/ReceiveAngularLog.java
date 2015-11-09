package com.comkeys.commons.server.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;



/**
 * Servlet implementation class ReiceiveLog
 * Recoit une requete de type POST contenant la trace d'une erreur sous forme de JSONString
 * <br/>genere un fichier de log
 */

public class ReceiveAngularLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	/**
	 * Header Origin
	 */
	private  String origin;
	/**
	 * Duree du preflight
	 */
	private String maxAge;
	
	/**
	 * Lire le fichier de properties 
	 */
	
	public void init() throws ServletException{
		try {
			String propFile = getInitParameter("propfile");
			InputStream input = ReceiveLog.class.getResourceAsStream("/" + propFile);
			Properties properties = new Properties();
			properties.load(input);			
			origin = properties.getProperty("origin");
			maxAge = properties.getProperty("max-age");
            System.out.println("Call of init");
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * erreur retournee en cas de GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.sendError(405);
	}

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Initiliser le header avec max-age et origin 
		 */
        System.out.println("Call of doPost");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Max-Age", maxAge);
		
		/**
		 * créer un buffer de lecture
		 */
		String line;
		StringBuffer jsonString = new StringBuffer();
		Gson gson = new Gson();

		try {
			/**
			 * Recuperer la data de la request 
			 */
			BufferedReader reader = request.getReader(); 

			while ((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			
			/**
			 * grace à la librairie Gson creer un Objet Java à partir d'une JSONString
			 */
			LogAngular log = gson.fromJson(jsonString.toString(), LogAngular.class); 
			
			/**
			 * Log le message final (rf src/main/resources/logback.xml)
			 */
			String [] stacktrace = log.getStackTrace();
			logger.debug("Incoming message from {}", log.getErrUrl());
			for (int i = 0; i < stacktrace.length; i++){
				logger.debug(stacktrace[i]);
			}
		} catch (IOException e) {
			logger.error("error : ", e);
		}
	}
}