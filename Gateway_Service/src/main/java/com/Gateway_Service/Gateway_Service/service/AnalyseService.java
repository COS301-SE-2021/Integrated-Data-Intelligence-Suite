package com.Gateway_Service.Gateway_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "Analyse-Service" ,  url = "localhost:9002/Analyse" , fallback = AnalyseServiceFallback.class)
public interface AnalyseService {
    @GetMapping("/findSentiment")
    TweetWithSentiment findSentiment(@RequestParam("line") String line) throws Exception;
}
