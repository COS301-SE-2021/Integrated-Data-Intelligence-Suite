package com.Analyse_Service.Analyse_Service.controller;


import com.Analyse_Service.Analyse_Service.dataclass.ServiceErrorResponse;
import com.Analyse_Service.Analyse_Service.exception.AnalyserException;
import com.Analyse_Service.Analyse_Service.request.GetModelByIdRequest;
import com.Analyse_Service.Analyse_Service.response.GetModelByIdResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = AnalyserException.class)
    ResponseEntity<?> AnalyserExceptionFound(Exception exc, ServletWebRequest request) {
    //ServiceErrorResponse AnalyserExceptionFound(HttpServletRequest request, Exception exc) {

        ServiceErrorResponse serviceErrorResponse = new ServiceErrorResponse();

        serviceErrorResponse.setTimeStamp(LocalDateTime.now());
        serviceErrorResponse.setPathUri(request.getContextPath());
        serviceErrorResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceErrorResponse.setErrors(Arrays.asList(exc.getMessage()));
        //exc.printStackTrace();

        return new ResponseEntity<>(serviceErrorResponse, new HttpHeaders(), serviceErrorResponse.getStatus());
        //return serviceErrorResponse;
    }
}
