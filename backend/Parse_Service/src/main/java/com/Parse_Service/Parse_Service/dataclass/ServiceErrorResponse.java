package com.Parse_Service.Parse_Service.dataclass;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServiceErrorResponse {
    public ServiceErrorResponse(){}

    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timeStamp;
    private String pathUri;
}
