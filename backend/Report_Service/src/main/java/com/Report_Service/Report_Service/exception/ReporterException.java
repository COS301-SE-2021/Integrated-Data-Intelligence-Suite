package com.Report_Service.Report_Service.exception;

public class ReporterException extends  Exception{
    public ReporterException(String message){
        super(message);
    }

    public ReporterException(Throwable cause){
        super(cause);
    }

    public ReporterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
