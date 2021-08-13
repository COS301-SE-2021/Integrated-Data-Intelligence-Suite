package com.Visualize_Service.Visualize_Service.exception;

public class VisualizerException extends  Exception{
    public VisualizerException(String message){
        super(message);
    }

    public VisualizerException(Throwable cause){
        super(cause);
    }

    public VisualizerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
