package com.comkeys.commons.server.log;

public class LogAngular {
    private String errUrl;
    private String errMessage;
    private String[] stackTrace;

    /**
     * @return Error's URL
     */
    public String getErrUrl() {
        return errUrl;
    }

    /**
     * @return Error Message
     */
    public String getErrMessage() {
        return errMessage;
    }

    /**
     * @return StackTrace provided by Angular
     */
    public String[] getStackTrace() {
        return stackTrace;
    }
}
