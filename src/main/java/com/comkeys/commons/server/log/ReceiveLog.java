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
 */
@WebServlet("/receivelog")
public class ReceiveLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	static String origin;
	static String maxAge;
	
	/**
	 * static block. 
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
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.sendError(405);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Init Origin and Max-age
		 */
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Acces-Control-Max-Age", maxAge);
		
		/**
		 * Create a Buffer 
		 */
		String line = null;
		StringBuffer jsonString = new StringBuffer();
		Gson gson = new Gson();

		try {

			BufferedReader reader = request.getReader(); // request for the request data

			while ((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			
			/**
			 * Use of Gson library to build an Object from JSONstring
			 */
			Log log = gson.fromJson(jsonString.toString(), Log.class); 
			logger.debug("Incoming message from js client line[{}], message [{}]", log.getLine(), log.getErrMessage());
		} catch (Exception e) {}
	}
}