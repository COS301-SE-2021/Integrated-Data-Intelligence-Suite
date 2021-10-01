package com.Gateway_Service.Gateway_Service.service;


import com.Gateway_Service.Gateway_Service.dataclass.parse.*;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(parseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ParseImportedDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseImportedData", request, ParseImportedDataResponse.class);

        return parseResponse;
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseUploadedSocialDataResponse parseUploadedSocialData(ParseUploadedSocialDataRequest parseRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(parseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ParseUploadedSocialDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseUploadedSocialData", request, ParseUploadedSocialDataResponse.class);

        return parseResponse;
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseUploadedNewsDataResponse parseUploadedNewsData(ParseUploadedNewsDataRequest parseRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(parseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ParseUploadedNewsDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseUploadedNewsData", request, ParseUploadedNewsDataResponse.class);

        return parseResponse;
    }

    /**
     * This method is used to return fail values if communication to the Parse-Service fails.
     * @param parseRequest This param is used to identify the method.
     * @return ParseImportedDataResponse This object contains failure values as data.
     */
    public ParseImportedDataResponse parseImportedDataFallback(ParseImportedDataRequest parseRequest){
        ParseImportedDataResponse parseImportedDataResponse =  new ParseImportedDataResponse(false, null, null, null);
        parseImportedDataResponse.setFallback(true);
        parseImportedDataResponse.setFallbackMessage("{Failed to get parse data}");
        return parseImportedDataResponse;
    }
}
