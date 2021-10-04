package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.impor.*;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
//@FeignClient(value = "Import-Service" , url = "localhost:9001/Import" , fallback = ImportServiceFallback.class)
public class ImportService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Import-Service.
     * @param importRequest This is a request object which contains data required to be imported.
     * @return ImportTwitterResponse This object contains imported twitter data returned by Import-Service
     */
    @HystrixCommand(/*commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "90000") },*/
            fallbackMethod = "getTwitterDataJsonFallback")
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest importRequest)  {

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;*/

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(importRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ImportTwitterResponse importResponse = restTemplate.postForObject("http://Import-Service/Import/getTwitterDataJson", request, ImportTwitterResponse.class);

        return importResponse;
    }


    /**
     * This method is used to communicate to the Import-Service.
     * @param importRequest
     * @return ImportDataResponse This object contains imported data returned by Import-Service
     */
    //@HystrixCommand(fallbackMethod = "importDataFallback")
    public ImportDataResponse importData(ImportDataRequest importRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(importRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ImportDataResponse importResponse = restTemplate.postForObject("http://Import-Service/Import/importData", request, ImportDataResponse.class);

        return importResponse;
    }

    /**
     * This method is used to return fail values if communication to the Import-Service fails.
     * @param importRequest This param is used to identify the method.
     * @return ImportTwitterResponse This object contains failure values as data.
     */
    public ImportTwitterResponse getTwitterDataJsonFallback(ImportTwitterRequest importRequest){
        ImportTwitterResponse importTwitterResponse =  new ImportTwitterResponse(null);
        importTwitterResponse.setFallback(true);
        importTwitterResponse.setFallbackMessage("{Failed to get twitter data}");
        return importTwitterResponse;
    }

    /**
     * This method is used to return fail values if communication to the Import-Service fails.
     * @param importRequest This param is used to identify the method.
     * @return ImportDataResponse This object contains failure values as data.
     */
    public ImportDataResponse importDataFallback(ImportDataRequest importRequest){
        //return "Import Service is not working...try again later";
        ImportDataResponse importDataResponse =  new ImportDataResponse(false, null, null);
        importDataResponse.setFallback(true);
        importDataResponse.setFallbackMessage("{Failed to get import data}");
        return importDataResponse;
    }


    public ImportTwitterResponse importDatedData(ImportTwitterRequest importRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity = new HttpEntity<>(importRequest, requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/importDatedData", HttpMethod.POST, requestEntity, ImportTwitterResponse.class);
        ImportTwitterResponse importTwitterResponse = responseEntity.getBody();

        return  importTwitterResponse;
    }

    public ImportTwitterResponse getDatedDataFallback(ImportTwitterRequest importRequest){
        ImportTwitterResponse importTwitterResponse = new ImportTwitterResponse(null);
        importTwitterResponse.setFallback(true);
        importTwitterResponse.setFallbackMessage("{failed to get import data}");
        return importTwitterResponse;
    }


    public String addApiSource(String jsonString) {

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;*/


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request;
        request = new HttpEntity<>(jsonString ,requestHeaders);
        String jsonResponse = restTemplate.postForObject("http://Import-Service/Import/addApiSource", request, String.class);

        return jsonResponse;
    }

    public GetAPISourceByIdResponse getSourceById(GetAPISourceByIdRequest sourceByIdRequest)  {

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;*/


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(sourceByIdRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        GetAPISourceByIdResponse response = restTemplate.postForObject("http://Import-Service/Import/getSourceById", request, GetAPISourceByIdResponse.class);

        return response;
    }

    public DeleteSourceResponse deleteSource(DeleteSourceRequest sourceByIdRequest)  {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(sourceByIdRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DeleteSourceResponse response = restTemplate.postForObject("http://Import-Service/Import/deleteSourceById", request, DeleteSourceResponse.class);

        return response;
    }

    public GetAllAPISourcesResponse getAllAPISources()  {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestHeaders);
        GetAllAPISourcesResponse response = restTemplate.getForObject("http://Import-Service/Import/getAllSources", GetAllAPISourcesResponse.class);

        return response;
    }

    public String editAPISource(String jsonString)  {

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;*/


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request;
        request = new HttpEntity<>(jsonString ,requestHeaders);
        String jsonResponse = restTemplate.postForObject("http://Import-Service/Import/updateAPI", request, String.class);

        return jsonResponse;
    }

    /*@GetMapping(value = "/importData")
    ImportDataResponse importData(@RequestParam("request") ImportDataRequest request) throws Exception;

    @GetMapping(value = "/getTwitterDataJson")
    ImportTwitterResponse getTwitterDataJson(@RequestParam("request") ImportTwitterRequest request) throws Exception ;*/

}
