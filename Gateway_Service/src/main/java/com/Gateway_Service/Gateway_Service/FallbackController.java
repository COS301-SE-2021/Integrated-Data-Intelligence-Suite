package com.Gateway_Service.Gateway_Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/ImportServiceFallback")
    String ImportServiceFallback(){
        return "Import Service is not working...try again later";
    }

    @GetMapping("/AnalyseServiceFallback")
    String AnalyseServiceFallback(){
        return "Analyse Service is not working...try again later";
    }

    @GetMapping("/ParseServiceFallback")
    String ParseServiceFallback(){
        return "Parse Service is not working...try again later";
    }
}
