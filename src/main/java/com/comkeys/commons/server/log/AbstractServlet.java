package com.comkeys.commons.server.log;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Servlet implementation
 * Receive a HTTP request from a Vanilla JS or Angular JS client which data is a Json String
 * <br/>Create a log file
 */
abstract public class AbstractServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    /**
     * Header Origin
     */
    private String origin;
    /**
     * preflight max-age
     */
    private String maxAge;

    /**
     * read propertie's file
     */

    public void init() throws ServletException {
        try {
            String propFile = getInitParameter("propfile");
            InputStream input = ReceiveLog.class.getResourceAsStream("/" + propFile);
            Properties properties = new Properties();
            properties.load(input);
            origin = properties.getProperty("origin");
            maxAge = properties.getProperty("max-age");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void setLogType(Gson gson, StringBuffer jsonString);
    protected abstract void logMessage(Logger logger);

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
         * Init header
         */
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Max-Age", maxAge);

        /**
         * create a reading buffer
         */
        String line;
        StringBuffer jsonString = new StringBuffer();
        Gson gson = new Gson();
        try {
            /**
             * Retrieve the request data
             */
            BufferedReader reader = request.getReader();

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            setLogType(gson, jsonString);
            logMessage(logger);
        }catch (IOException e) {
            logger.error("error : ", e);
        }
    }
}
