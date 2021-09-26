package com.Analyse_Service.Analyse_Service.exception;

public class AnalyzerException extends Exception{
    public AnalyzerException(String message){
        super(message);
    }

    public AnalyzerException(Throwable cause){
        super(cause);
    }

    public AnalyzerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
