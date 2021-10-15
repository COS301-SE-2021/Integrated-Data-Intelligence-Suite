package com.Gateway_Service.Gateway_Service.controller;


import com.Gateway_Service.Gateway_Service.exception.*;
import com.Gateway_Service.Gateway_Service.rri.ServiceErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler({
            AnalyserException.class,
            ImporterException.class,
            ParserException.class,
            ReporterException.class,
            UserException.class,
            VisualizerException.class
    })
    ResponseEntity<?> ServiceExceptionFound(Exception exc, ServletWebRequest request) {

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse();

        serviceErrorResponse.setTimeStamp(LocalDateTime.now());
        serviceErrorResponse.setPathUri(exc.getClass().getName() + "; " + request.getDescription(true));
        serviceErrorResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceErrorResponse.setErrors(Arrays.asList(exc.getMessage()));
        //exc.printStackTrace();

        return new ResponseEntity<>(serviceErrorResponse, new HttpHeaders(), serviceErrorResponse.getStatus());
        //return serviceErrorResponse;
    }

    @ExceptionHandler({GatewayException.class})
    ResponseEntity<?> GatewayExceptionFound(Exception exc, ServletWebRequest request) {

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse();

        serviceErrorResponse.setTimeStamp(LocalDateTime.now());
        serviceErrorResponse.setPathUri(request.getDescription(true));
        serviceErrorResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceErrorResponse.setErrors(Arrays.asList(exc.getMessage()));
        //exc.printStackTrace();

        return new ResponseEntity<>(serviceErrorResponse, new HttpHeaders(), serviceErrorResponse.getStatus());
        //return serviceErrorResponse;
    }
}
