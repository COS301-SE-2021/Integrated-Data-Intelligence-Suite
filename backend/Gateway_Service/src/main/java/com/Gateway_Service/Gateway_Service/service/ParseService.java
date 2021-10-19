package com.Gateway_Service.Gateway_Service.service;


import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.parse.*;
import com.Gateway_Service.Gateway_Service.exception.AnalyserException;
import com.Gateway_Service.Gateway_Service.exception.ParserException;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.Gateway_Service.Gateway_Service.rri.ServiceErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest parseRequest) throws ParserException {

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
        //ParseImportedDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseImportedData", request, ParseImportedDataResponse.class);

        ResponseEntity<?> parseResponse = null;
        parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseImportedData",HttpMethod.POST,request,new ParameterizedTypeReference<ParseImportedDataResponse>() {});


        if(parseResponse.getBody().getClass() != ParseImportedDataResponse.class ) {
            parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseImportedData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) parseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ParserException(errors);
            }
        }


        return (ParseImportedDataResponse) parseResponse.getBody();
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest This is the request
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseUploadedSocialDataResponse parseUploadedSocialData(ParseUploadedSocialDataRequest parseRequest) throws ParserException {

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
        //ParseUploadedSocialDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseUploadedSocialData", request, ParseUploadedSocialDataResponse.class);

        ResponseEntity<?> parseResponse = null;
        parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseUploadedSocialData",HttpMethod.POST,request,new ParameterizedTypeReference<ParseUploadedSocialDataResponse>() {});



        if(parseResponse.getBody().getClass() != ParseUploadedSocialDataResponse.class ) {

            parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseUploadedSocialData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) parseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ParserException(errors);
            }
        }


        return (ParseUploadedSocialDataResponse) parseResponse.getBody();
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest This is the request
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseUploadedTrainingDataResponse parseUploadedTrainingData(ParseUploadedTrainingDataRequest parseRequest) throws ParserException {

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
        //ParseUploadedTrainingDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseTrainingData", request, ParseUploadedTrainingDataResponse.class);

        ResponseEntity<?> parseResponse = null;
        parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseTrainingData",HttpMethod.POST,request,new ParameterizedTypeReference<ParseUploadedTrainingDataResponse>() {});


        if(parseResponse.getBody().getClass() != ParseUploadedTrainingDataResponse.class ) {

            parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseTrainingData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) parseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ParserException(errors);
            }
        }

        return (ParseUploadedTrainingDataResponse) parseResponse.getBody();
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest This is the request
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ParseUploadedNewsDataResponse parseUploadedNewsData(ParseUploadedNewsDataRequest parseRequest) throws ParserException {

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
        //ParseUploadedNewsDataResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/parseUploadedNewsData", request, ParseUploadedNewsDataResponse.class);

        ResponseEntity<?> parseResponse = null;
        parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseUploadedNewsData",HttpMethod.POST,request,new ParameterizedTypeReference<ParseUploadedNewsDataResponse>() {});



        if(parseResponse.getBody().getClass() != ParseUploadedNewsDataResponse.class ) {
            parseResponse = restTemplate.exchange("http://Parse-Service/Parse/parseUploadedNewsData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) parseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ParserException(errors);
            }
        }


        return (ParseUploadedNewsDataResponse) parseResponse.getBody();
    }

    /**
     * This method is used to communicate to the Parse-Service.
     * @param parseRequest This is the request
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    public AddSocialMediaPropertiesResponse addSocialMediaPropertiesRequest(String parseRequest) throws ParserException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = new HttpEntity<>(parseRequest,requestHeaders);

        //AddSocialMediaPropertiesResponse parseResponse = restTemplate.postForObject("http://Parse-Service/Parse/addSocialMediaProperties", request, AddSocialMediaPropertiesResponse.class);

        ResponseEntity<?> parseResponse = null;
        parseResponse = restTemplate.exchange("http://Parse-Service/Parse/addSocialMediaProperties",HttpMethod.POST,request,new ParameterizedTypeReference<AddSocialMediaPropertiesResponse>() {});


        if(parseResponse.getBody().getClass() != AddSocialMediaPropertiesResponse.class ) {
            parseResponse = restTemplate.exchange("http://Parse-Service/Parse/addSocialMediaProperties",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) parseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ParserException(errors);
            }
        }

        return (AddSocialMediaPropertiesResponse) parseResponse.getBody();
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
