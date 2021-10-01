package com.Analyse_Service.Analyse_Service.exception;

public class AnalyserException extends Exception{
    public AnalyserException(String message){
        super(message);
    }

    public AnalyserException(Throwable cause){
        super(cause);
    }

    public AnalyserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
