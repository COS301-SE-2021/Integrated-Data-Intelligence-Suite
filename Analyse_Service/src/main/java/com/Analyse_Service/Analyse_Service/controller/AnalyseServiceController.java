package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Analyse")
public class AnalyseServiceController {

    @Autowired
    private AnalyseServiceImpl service;

    /*@GetMapping("/findSentiment")
    public AnalyzeDataResponse findSentiment(@RequestParam("line") String line) throws Exception {
        TweetWithSentiment sentiment = service.findSentiment(line);

        if(sentiment != null)
            System.out.println("CHECKING HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );

        return new AnalyzeDataResponse(sentiment);
    }*/

    @PostMapping("/analyzeData")
    public AnalyseDataResponse analyzeData(RequestEntity<AnalyseDataRequest> requestEntity) throws Exception{
        AnalyseDataRequest request = requestEntity.getBody();
        return service.analyzeData(request);
    }


}
