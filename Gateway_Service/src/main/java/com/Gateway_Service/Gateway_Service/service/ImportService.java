package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.ImportDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.ImportDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.ImportTwitterRequest;
import com.Gateway_Service.Gateway_Service.dataclass.ImportTwitterResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;


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
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest importRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST,null, ImportTwitterResponse.class);
        ImportTwitterResponse importResponse = new ImportTwitterResponse("hello world"); // responseEntity.getBody();

        return importResponse;
    }


    /**
     * This method is used to communicate to the Import-Service.
     * @param importRequest
     * @return ImportDataResponse This object contains imported data returned by Import-Service
     */
    //@HystrixCommand(fallbackMethod = "importDataFallback")
    public ImportDataResponse importData(ImportDataRequest importRequest) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImportDataRequest> requestEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportDataResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/importData",  HttpMethod.POST, requestEntity,ImportDataResponse.class);
        ImportDataResponse importResponse = responseEntity.getBody();

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
        ImportDataResponse importDataResponse =  new ImportDataResponse(null);
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



    /*@GetMapping(value = "/importData")
    ImportDataResponse importData(@RequestParam("request") ImportDataRequest request) throws Exception;

    @GetMapping(value = "/getTwitterDataJson")
    ImportTwitterResponse getTwitterDataJson(@RequestParam("request") ImportTwitterRequest request) throws Exception ;*/

}
