package com.comkeys.commons.server.log;


import com.google.gson.Gson;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReceiveAngularLog extends AbstractServlet {
    private LogAngular log;

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
    protected void setLogType(Gson gson, StringBuffer jsonString) {
        /**
         * With the GSON library create a Java Object
         */
        log = gson.fromJson(jsonString.toString(), LogAngular.class);
    }

    @Override
    protected void logMessage(Logger logger) {
        /**
         * Log the message (rf logback.xml)
         */
        String[] stacktrace = log.getStackTrace();
        logger.debug("Incoming message from {}", log.getErrUrl());
        for (int i = 0; i < stacktrace.length; i++) {
            logger.debug(stacktrace[i]);
        }
    }
}