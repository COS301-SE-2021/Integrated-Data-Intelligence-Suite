package com.Gateway_Service.Gateway_Service.rri;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServiceSuccesResponse {
    public ServiceSuccesResponse(){}

    private HttpStatus status;
    private Object data;
    private LocalDateTime timeStamp;
    private String pathUri;
}
