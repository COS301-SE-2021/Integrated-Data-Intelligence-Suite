package com.Gateway_Service.Gateway_Service.exception;

public class GatewayException extends Exception {
    public GatewayException(String message){
        super(message);
    }

    public GatewayException(Throwable cause){
        super(cause);
    }

    public GatewayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
