package com.comkeys.commons.server.log;

public class Log {
    private String errMessage;
    private String errURL;

    /**
     * @return Error's URL
     */
    public String getErrUrl() {
        return errURL;
    }

    /**
     * @return Error Message
     */
    public String getErrMessage() {
        return errMessage;
    }

    private int line;
    private int col;

    /**
     * @return Error's line
     */
    public int getLine() {
        return line;
    }

    /**
     * @return Error's row
     */
    public int getCol() {
        return col;
    }

    private String[] stackTrace;

    /**
     * @return StackTrace provided by Angular
     */
    public String[] getStackTrace() {
        return stackTrace;
    }
}
