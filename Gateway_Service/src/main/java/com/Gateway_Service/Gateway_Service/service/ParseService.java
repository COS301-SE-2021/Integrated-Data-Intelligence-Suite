package com.Gateway_Service.Gateway_Service.service;


import com.Gateway_Service.Gateway_Service.dataclass.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
//@FeignClient(name = "Parse-Service" ,  url = "localhost/Parse:9003" , fallback = ParseServiceFallback.class)
public class ParseService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest parseRequest) {


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ParseImportedDataRequest> requestEntity =new HttpEntity<>(parseRequest,requestHeaders);

        ResponseEntity<ParseImportedDataResponse> responseEntity = restTemplate.exchange("http://Parse-Service/Parse/parseImportedData",  HttpMethod.POST, requestEntity, ParseImportedDataResponse.class);
        ParseImportedDataResponse parseResponse = responseEntity.getBody();

        return parseResponse;
    }

    /**
     * This method is used to return fail values if communication to the Parse-Service fails.
     * @param parseRequest This param is used to identify the method.
     * @return ParseImportedDataResponse This object contains failure values as data.
     */
    public ParseImportedDataResponse parseImportedDataFallback(ParseImportedDataRequest parseRequest){
        ParseImportedDataResponse parseImportedDataResponse =  new ParseImportedDataResponse(null);
        parseImportedDataResponse.setFallback(true);
        parseImportedDataResponse.setFallbackMessage("{Failed to get parse data}");
        return parseImportedDataResponse;
    }
}
