package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.AnalyseDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.VisualizeDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.VisualizeDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@FeignClient(name = "Parse-Service" ,  url = "localhost/Parse:9003" , fallback = ParseServiceFallback.class)
public class VisualizeService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Analyse-Service.
     * @param visualizeRequest This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "visualizeDataFallback")
    public VisualizeDataResponse visualizeData(VisualizeDataRequest visualizeRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VisualizeDataRequest> requestEntity =new HttpEntity<>(visualizeRequest,requestHeaders);

        ResponseEntity<VisualizeDataResponse> responseEntity = restTemplate.exchange("http://Visualize-Service/Visualize/visualizeData",  HttpMethod.POST, requestEntity,VisualizeDataResponse.class);
        VisualizeDataResponse visualizeResponse= responseEntity.getBody();

        return visualizeResponse;
    }

    /**
     * This method is used to return fail values if communication to the Analyse-Service fails.
     * @param visualizeRequest This param is used to identify the method.
     * @return AnalyseDataResponse This object contains failure values as data.
     */
    public VisualizeDataResponse visualizeDataFallback(VisualizeDataRequest visualizeRequest){
        VisualizeDataResponse visualizeDataResponse =  new VisualizeDataResponse(null);
        visualizeDataResponse.setFallback(true);
        visualizeDataResponse.setFallbackMessage("{Failed to get visualize Data's data}");
        return visualizeDataResponse;
    }

}
