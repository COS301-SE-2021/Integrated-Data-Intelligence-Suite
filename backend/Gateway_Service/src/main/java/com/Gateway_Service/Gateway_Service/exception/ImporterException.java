package com.Gateway_Service.Gateway_Service.exception;

public class ImporterException extends Exception{
    public ImporterException(String message){
        super(message);
    }

    public ImporterException(Throwable cause){
        super(cause);
    }

    public ImporterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
