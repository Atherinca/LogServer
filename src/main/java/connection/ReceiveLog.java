package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;


/**
 * Servlet implementation class ReiceiveLog
 */
@WebServlet("/receivelog")
public class ReceiveLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	/**
	 * Default constructor. 
	 */
	public ReceiveLog() {
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

		try {
			String path= new File("").getAbsolutePath();
			File fXmlFile = new File(path + "/src/test/resources/property.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			if (doc.getDocumentElement().getNodeName() == "configuration"){
				NodeList originList = doc.getElementsByTagName("origin");
				NodeList ageList = doc.getElementsByTagName("max-age");
				String origin = originList.item(0).getTextContent();
				String maxAge = ageList.item(0).getTextContent();
				response.setHeader("Access-Control-Allow-Origin", origin);
				response.setHeader("Access-Control-Max-Age", maxAge);
			}
		} catch (Exception e) {}		
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		String line = null;
		StringBuffer jsonString = new StringBuffer();
		Gson gson = new Gson();

		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			Log log = gson.fromJson(jsonString.toString(), Log.class);
			logger.debug("Incoming message from js client line[{}], message [{}]", log.getLine(), log.getErrMessage());
		} catch (Exception e) {}
	}
}


