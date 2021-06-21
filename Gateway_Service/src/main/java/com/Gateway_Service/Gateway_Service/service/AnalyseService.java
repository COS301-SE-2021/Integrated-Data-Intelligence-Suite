package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
//@FeignClient(name = "Analyse-Service" ,  url = "localhost:9002/Analyse" , fallback = AnalyseServiceFallback.class)
public class AnalyseService {

    @Autowired
    private RestTemplate restTemplate;



    //@HystrixCommand(fallbackMethod = "findSentimentFallback")
    /*public AnalyseDataResponse findSentiment(String line) {

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
    }*/


    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public AnalyseDataResponse analyzeData(AnalyseDataRequest analyseRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AnalyseDataRequest> requestEntity =new HttpEntity<>(analyseRequest,requestHeaders);

        ResponseEntity<AnalyseDataResponse > responseEntity = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeData",  HttpMethod.POST, requestEntity,AnalyseDataResponse.class);
        AnalyseDataResponse analyseResponse= responseEntity.getBody();

        return analyseResponse;
    }


    public AnalyseDataResponse analyzeDataFallback(String line){
        AnalyseDataResponse analyseDataResponse =  new AnalyseDataResponse(null, null, null);
        analyseDataResponse.setFallback(true);
        analyseDataResponse.setFallbackMessage("{Failed to get analyzeData's data}");
        return analyseDataResponse;
    }

}
