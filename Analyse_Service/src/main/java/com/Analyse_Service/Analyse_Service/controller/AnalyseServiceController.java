package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Analyse_Service.Analyse_Service.response.AnalyseDataResponse;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Analyse")
public class AnalyseServiceController {

    @Autowired
    private AnalyseServiceImpl service;

    @GetMapping("/findSentiment")
    public AnalyseDataResponse findSentiment(@RequestParam("line") String line) throws Exception {
        TweetWithSentiment sentiment = service.findSentiment(line);

        if(sentiment != null)
            System.out.println("CHECKING HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );

        return new AnalyseDataResponse(sentiment);
    }


}
