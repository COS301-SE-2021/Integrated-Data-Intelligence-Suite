package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Analyse")
public class AnalyseServiceController {

    @Autowired
    private AnalyseServiceImpl service;

    @GetMapping("/")
    public TweetWithSentiment findSentiment(String line) throws Exception {
        return service.findSentiment(line);
    }
}
