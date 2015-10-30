package com.comkeys.commons.server.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/receivelog")
public class ReceiveLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	/**
	 * Header Origin
	 */
	static String origin;
	/**
	 * Duree du preflight
	 */
	static String maxAge;
	
	/**
	 * Lire le fichier de properties 
	 */
	static {
		try {
			Properties properties = new Properties();
			InputStream input = ReceiveLog.class.getResourceAsStream("/log-server.properties");
			properties.load(input);			
			origin = properties.getProperty("origin");
			maxAge = properties.getProperty("max-age");
		} catch (IOException e) {}
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
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Acces-Control-Max-Age", maxAge);
		
		/**
		 * créer un buffer de lecture
		 */
		String line = null;
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
			Log log = gson.fromJson(jsonString.toString(), Log.class); 
			
			/**
			 * Log le message final (rf src/main/resources/logback.xml)
			 */
			logger.debug("Incoming message from js client line[{}], message [{}]", log.getLine(), log.getErrMessage());
		} catch (Exception e) {}
	}
}