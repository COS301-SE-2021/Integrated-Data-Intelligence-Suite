package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.dataclass.ServiceErrorResponse;
import com.Import_Service.Import_Service.exception.ImporterException;
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
    @ExceptionHandler(value = ImporterException.class)
    ResponseEntity<?> ImporterExceptionFound(Exception exc, ServletWebRequest request) {
        //ServiceErrorResponse AnalyserExceptionFound(HttpServletRequest request, Exception exc) {

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse();

        serviceErrorResponse.setTimeStamp(LocalDateTime.now());
        serviceErrorResponse.setPathUri(request.getContextPath());
        serviceErrorResponse.setStatus(HttpStatus.OK);
        serviceErrorResponse.setErrors(Arrays.asList(exc.getMessage()));
        //exc.printStackTrace();

        return new ResponseEntity<>(serviceErrorResponse, new HttpHeaders(), serviceErrorResponse.getStatus());
        //return serviceErrorResponse;
    }
}
