package com.comkeys.commons.server.log;

public class Log {

    private String errMessage;
    private String errURL;
    private int line;
    private int col;

    /**
     * @return Error Message
     */
    public String getErrMessage() {
        return errMessage;
    }

    /**
     * @return Error's URL
     */
    public String getErrURL() {
        return errURL;
    }

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
}
