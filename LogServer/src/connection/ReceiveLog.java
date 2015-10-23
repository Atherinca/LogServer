package connection;

import java.io.BufferedReader;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;

import ch.qos.logback.core.util.StatusPrinter;


/**
 * Servlet implementation class ReiceiveLog
 */
@WebServlet("/receivelog")
public class ReceiveLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor. 
	 */
	public ReceiveLog() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientOrigin = request.getHeader("origin");
		response.setHeader("Access-Control-Allow-Origin", clientOrigin);
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		String line = null;
		StringBuffer jsonString = new StringBuffer();
		Gson gson = new Gson();

		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			Log log = gson.fromJson(jsonString.toString(), Log.class);

			Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME); 
			LoggerContext logContext = logger.getLoggerContext(); 
			logContext.reset(); 

			PatternLayoutEncoder encoder1 = new PatternLayoutEncoder(); 
			encoder1.setPattern("%date %level [%thread] [%file:%line] %msg%n"); 
			encoder1.setContext(logContext); 
			encoder1.start(); 

			PatternLayoutEncoder encoder2 = new PatternLayoutEncoder(); 
			encoder2.setPattern("%date %level [%thread] [%file:%line] %msg%n"); 
			encoder2.setContext(logContext); 
			encoder2.start();


			ConsoleAppender<ILoggingEvent> consoleOut = new ConsoleAppender<ILoggingEvent>(); 
			consoleOut.setEncoder(encoder2); 

			FileAppender<ILoggingEvent> logfileOut = new FileAppender<ILoggingEvent>();  
			logfileOut.setAppend(true); 
			logfileOut.setFile("/home/alexandre/Travail/LogServer/error.log"); 
			logfileOut.setContext(logContext); 
			logfileOut.setEncoder(encoder1); 
			logfileOut.start(); 

			Logger logback = logContext.getLogger("ReceiveLog");
			logback.addAppender(logfileOut);
			logback.addAppender(consoleOut);
			logback.debug(log.getErrorMessage());
		} catch (Exception e) {}
	}
}
