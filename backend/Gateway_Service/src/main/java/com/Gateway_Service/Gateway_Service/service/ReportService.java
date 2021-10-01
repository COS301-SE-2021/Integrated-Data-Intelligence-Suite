package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.parse.ParseImportedDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParseImportedDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.report.ReportDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.report.ReportDataResponse;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@FeignClient(name = "Report-Service" ,  url = "localhost/Parse:9003" , fallback = ParseServiceFallback.class)
public class ReportService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Parse-Service.
     * @param reportRequest
     * @return ParseImportedDataResponse This object contains parsed data returned by Parse-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ReportDataResponse reportData(ReportDataRequest reportRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(reportRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ReportDataResponse reportResponse = restTemplate.postForObject("http://Report-Service/Report/reportData", request, ReportDataResponse.class);

        return reportResponse;
    }

    /**
     * This method is used to return fail values if communication to the Parse-Service fails.
     * @param reportRequest This param is used to identify the method.
     * @return ParseImportedDataResponse This object contains failure values as data.
     */
    public ReportDataResponse reportDataFallback(ReportDataRequest reportRequest){
        ReportDataResponse reportDataResponse = new ReportDataResponse();
        //parseImportedDataResponse.setFallback(true);
        //parseImportedDataResponse.setFallbackMessage("{Failed to get parse data}");
        return reportDataResponse;
    }
}
