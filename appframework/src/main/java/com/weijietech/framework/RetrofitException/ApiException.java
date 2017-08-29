package com.weijietech.framework.RetrofitException;

/**
 * Created by 12262 on 2016/5/31.
 */
public class ApiException extends Exception {
    private int code;
    private int myErrCode;
    private String displayMessage;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public void setMyErrorCode(int code) {
        this.myErrCode = code;
    }

    public int getMyErrorCode() {
        return myErrCode;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
