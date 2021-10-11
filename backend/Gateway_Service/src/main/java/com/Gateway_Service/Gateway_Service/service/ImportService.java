package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.impor.*;
import com.Gateway_Service.Gateway_Service.exception.AnalyserException;
import com.Gateway_Service.Gateway_Service.exception.ImporterException;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.Gateway_Service.Gateway_Service.rri.ServiceErrorResponse;
import com.Import_Service.Import_Service.response.AddAPISourceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest importRequest) throws ImporterException {

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;*/

        //restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        //ImportTwitterResponse importResponse = restTemplate.postForObject("http://Import-Service/Import/getTwitterDataJson", request, ImportTwitterResponse.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",HttpMethod.POST,request,new ParameterizedTypeReference<ImportTwitterResponse>() {});
        return (ImportTwitterResponse) importResponse.getBody();
    }


    /**
     * This method is used to communicate to the Import-Service.
     * @param importRequest
     * @return ImportDataResponse This object contains imported data returned by Import-Service
     */
    //@HystrixCommand(fallbackMethod = "importDataFallback")
    public ImportDataResponse importData(ImportDataRequest importRequest) throws ImporterException {

        //restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        //ImportDataResponse importResponse = restTemplate.postForObject("http://Import-Service/Import/importData", request, ImportDataResponse.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/importData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/importData",HttpMethod.POST,request,new ParameterizedTypeReference<ImportDataResponse>() {});
        return (ImportDataResponse) importResponse.getBody();
    }



    public ImportTwitterResponse importDatedData(ImportTwitterRequest importRequest) throws ImporterException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //HttpEntity<ImportTwitterRequest> requestEntity = new HttpEntity<>(importRequest, requestHeaders);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(importRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/importDatedData", HttpMethod.POST, requestEntity, ImportTwitterResponse.class);
        //ImportTwitterResponse importTwitterResponse = responseEntity.getBody();

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/importDatedData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/importDatedData",HttpMethod.POST,request,new ParameterizedTypeReference<ImportTwitterResponse>() {});
        return (ImportTwitterResponse) importResponse.getBody();
    }



    public AddAPISourceResponse addApiSource(String jsonString) throws ImporterException {

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
        //String jsonResponse = restTemplate.postForObject("http://Import-Service/Import/addApiSource", request, String.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/addApiSource",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/addApiSource",HttpMethod.POST,request,new ParameterizedTypeReference<AddAPISourceResponse>() {});
        return (AddAPISourceResponse) importResponse.getBody();
    }

    public GetAPISourceByIdResponse getSourceById(GetAPISourceByIdRequest sourceByIdRequest) throws ImporterException {

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
        //GetAPISourceByIdResponse response = restTemplate.postForObject("http://Import-Service/Import/getSourceById", request, GetAPISourceByIdResponse.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/getSourceById",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/getSourceById",HttpMethod.POST,request,new ParameterizedTypeReference<GetAPISourceByIdResponse>() {});
        return (GetAPISourceByIdResponse) importResponse.getBody();
    }

    public DeleteSourceResponse deleteSource(DeleteSourceRequest sourceByIdRequest) throws ImporterException {
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
        //DeleteSourceResponse response = restTemplate.postForObject("http://Import-Service/Import/deleteSourceById", request, DeleteSourceResponse.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/deleteSourceById",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/deleteSourceById",HttpMethod.POST,request,new ParameterizedTypeReference<DeleteSourceResponse>() {});
        return (DeleteSourceResponse) importResponse.getBody();
    }

    public GetAllAPISourcesResponse getAllAPISources() throws ImporterException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestHeaders);
        //GetAllAPISourcesResponse response = restTemplate.getForObject("http://Import-Service/Import/getAllSources", GetAllAPISourcesResponse.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",HttpMethod.GET,null,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",HttpMethod.GET,null,new ParameterizedTypeReference<GetAllAPISourcesResponse>() {});
        return (GetAllAPISourcesResponse) importResponse.getBody();
    }

    public EditAPISourceResponse editAPISource(String jsonString) throws ImporterException {

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
        //String jsonResponse = restTemplate.postForObject("http://Import-Service/Import/updateAPI", request, String.class);

        ResponseEntity<?> importResponse = null;
        importResponse = restTemplate.exchange("http://Import-Service/Import/updateAPI",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(importResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) importResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ImporterException(errors);
            }
        }

        importResponse = restTemplate.exchange("http://Import-Service/Import/updateAPI",HttpMethod.POST,request,new ParameterizedTypeReference<EditAPISourceResponse>() {});
        return (EditAPISourceResponse) importResponse.getBody();
    }

    /*@GetMapping(value = "/importData")
    ImportDataResponse importData(@RequestParam("request") ImportDataRequest request) throws Exception;

    @GetMapping(value = "/getTwitterDataJson")
    ImportTwitterResponse getTwitterDataJson(@RequestParam("request") ImportTwitterRequest request) throws Exception ;*/


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

    public ImportTwitterResponse getDatedDataFallback(ImportTwitterRequest importRequest){
        ImportTwitterResponse importTwitterResponse = new ImportTwitterResponse(null);
        importTwitterResponse.setFallback(true);
        importTwitterResponse.setFallbackMessage("{failed to get import data}");
        return importTwitterResponse;
    }


}
