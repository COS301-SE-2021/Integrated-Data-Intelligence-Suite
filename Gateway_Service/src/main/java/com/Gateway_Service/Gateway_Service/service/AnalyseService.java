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

    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public AnalyseDataResponse analyzeData(AnalyseDataRequest analyseRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AnalyseDataRequest> requestEntity =new HttpEntity<>(analyseRequest,requestHeaders);

        ResponseEntity<AnalyseDataResponse > responseEntity = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeData",  HttpMethod.POST, requestEntity,AnalyseDataResponse.class);
        AnalyseDataResponse analyseResponse= responseEntity.getBody();

        return analyseResponse;
    }

    /**
     * This method is used to return fail values if communication to the Analyse-Service fails.
     * @param analyseRequest This param is used to identify the method.
     * @return AnalyseDataResponse This object contains failure values as data.
     */
    public AnalyseDataResponse analyzeDataFallback(AnalyseDataRequest analyseRequest){
        AnalyseDataResponse analyseDataResponse =  new AnalyseDataResponse(null, null, null, null, null);
        analyseDataResponse.setFallback(true);
        analyseDataResponse.setFallbackMessage("{Failed to get analyzeData's data}");
        return analyseDataResponse;
    }

}
