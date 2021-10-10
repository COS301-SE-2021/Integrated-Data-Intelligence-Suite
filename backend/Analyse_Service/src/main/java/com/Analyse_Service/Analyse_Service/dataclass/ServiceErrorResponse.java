package com.Analyse_Service.Analyse_Service.dataclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServiceErrorResponse {
    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timeStamp;
    private String pathUri;
}
