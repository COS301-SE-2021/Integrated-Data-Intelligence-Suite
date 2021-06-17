package com.Analyse_Service.Analyse_Service.service;


import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "Analyse-Service")
public interface AnalyseService {
    TweetWithSentiment findSentiment(String line);

    String toCss(int sentiment);
}
