package com.comkeys.commons.server.log;


import com.google.gson.Gson;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReceiveLog extends AbstractServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected Log convertToLogType(Gson gson, StringBuffer jsonString) {
        /**
         * With GSON library create a Java Object
         */
        return  gson.fromJson(jsonString.toString(), Log.class);
    }

    @Override
    protected void logMessage(Logger logger, Object log) {

        /**
         * Log the message (rf logback.xml)
         */

        Log logObject = (Log)log;
        logger.debug("Incoming message from js client line[{}], message [{}]", logObject.getLine(), logObject.getErrMessage());
    }
}