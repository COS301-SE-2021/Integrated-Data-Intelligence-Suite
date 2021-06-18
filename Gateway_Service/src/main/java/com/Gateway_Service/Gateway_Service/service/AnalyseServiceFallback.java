package com.Gateway_Service.Gateway_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class AnalyseServiceFallback implements AnalyseService{
    @Override
    public TweetWithSentiment findSentiment(String line) throws Exception{
        return null;
    }
}
