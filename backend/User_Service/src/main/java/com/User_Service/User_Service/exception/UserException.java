package com.User_Service.User_Service.exception;

public class UserException extends Exception {
    public UserException(String message){
        super(message);
    }

    public UserException(Throwable cause){
        super(cause);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
