package com.Gateway_Service.Gateway_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Gateway_Service.Gateway_Service.dataclass.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
//@FeignClient(name = "Analyse-Service" ,  url = "localhost:9002/Analyse" , fallback = AnalyseServiceFallback.class)
public class AnalyseService {

    @Autowired
    private RestTemplate restTemplate;



    //@HystrixCommand(fallbackMethod = "findSentimentFallback")
    public AnalyseDataResponse findSentiment(String line) {

        String url = "http://Analyse-Service/Analyse/findSentiment";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("line",line);

        ResponseEntity<AnalyseDataResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, AnalyseDataResponse.class);
        AnalyseDataResponse analyseResponse = responseEntity.getBody();

        return analyseResponse;
    }


    public AnalyseDataResponse findSentimentFallback(String line){
        AnalyseDataResponse analyseDataResponse =  new AnalyseDataResponse(null);
        analyseDataResponse.setFallback(true);
        analyseDataResponse.setFallbackMessage("{Failed to get findSentiment data}");
        return analyseDataResponse;
    }

}
