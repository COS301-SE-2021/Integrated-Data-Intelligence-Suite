package com.User_Service.User_Service.exception;

public class InvalidRequestException extends Exception{
    public InvalidRequestException(String message){
        super(message);
    }

    public InvalidRequestException(Throwable cause){
        super(cause);
    }

    public InvalidRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
