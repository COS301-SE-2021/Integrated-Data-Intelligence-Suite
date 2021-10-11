package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.parse.AddSocialMediaPropertiesResponse;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParseImportedDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParseImportedDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.report.*;
import com.Gateway_Service.Gateway_Service.exception.ParserException;
import com.Gateway_Service.Gateway_Service.exception.ReporterException;
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
//@FeignClient(name = "Report-Service" ,  url = "localhost/Parse:9003" , fallback = ParseServiceFallback.class)
public class ReportService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Report-Service.
     * @param reportRequest
     * @return ReportDataResponse This object contains report data returned by Report-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ReportDataResponse reportData(ReportDataRequest reportRequest) throws ReporterException {

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
        //ReportDataResponse reportResponse = restTemplate.postForObject("http://Report-Service/Report/reportData", request, ReportDataResponse.class);

        ResponseEntity<?> reportResponse = null;
        reportResponse = restTemplate.exchange("http://Report-Service/Report/reportData", HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(reportResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) reportResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ReporterException(errors);
            }
        }

        reportResponse = restTemplate.exchange("http://Report-Service/Report/reportData",HttpMethod.POST,request,new ParameterizedTypeReference<ReportDataResponse>() {});
        return (ReportDataResponse) reportResponse.getBody();
    }


    /**
     * This method is used to communicate to the Report-Service.
     * @param reportRequest
     * @return ReportDataResponse This object contains report data returned by Report-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public GetReportDataByIdResponse getReportDataById(GetReportDataByIdRequest reportRequest) throws ReporterException {

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
        //GetReportDataByIdResponse reportResponse = restTemplate.postForObject("http://Report-Service/Report/getReportDataById", request, GetReportDataByIdResponse.class);

        ResponseEntity<?> reportResponse = null;
        reportResponse = restTemplate.exchange("http://Report-Service/Report/getReportDataById", HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(reportResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) reportResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ReporterException(errors);
            }
        }

        reportResponse = restTemplate.exchange("http://Report-Service/Report/getReportDataById",HttpMethod.POST,request,new ParameterizedTypeReference<GetReportDataByIdResponse>() {});
        return (GetReportDataByIdResponse) reportResponse.getBody();
    }


    /**
     * This method is used to communicate to the Report-Service.
     * @param reportRequest
     * @return ReportDataResponse This object contains report data returned by Report-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public DeleteReportDataByIdResponse deleteReportDataById(DeleteReportDataByIdRequest reportRequest) throws ReporterException {

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
        //DeleteReportDataByIdResponse reportResponse = restTemplate.postForObject("http://Report-Service/Report/deleteReportDataById", request, DeleteReportDataByIdResponse.class);

        ResponseEntity<?> reportResponse = null;
        reportResponse = restTemplate.exchange("http://Report-Service/Report/deleteReportDataById", HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(reportResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) reportResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ReporterException(errors);
            }
        }

        reportResponse = restTemplate.exchange("http://Report-Service/Report/deleteReportDataById",HttpMethod.POST,request,new ParameterizedTypeReference<DeleteReportDataByIdResponse>() {});
        return (DeleteReportDataByIdResponse) reportResponse.getBody();
    }

    /**
     * This method is used to communicate to the Report-Service.
     * @param reportRequest
     * @return ReportDataResponse This object contains report data returned by Report-Service
     */
    //@HystrixCommand(fallbackMethod = "parseImportedDataFallback")
    public ShareReportResponse shareReport(ShareReportRequest reportRequest) throws ReporterException {

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
        //ShareReportResponse reportResponse = restTemplate.postForObject("http://Report-Service/Report/shareReport", request, ShareReportResponse.class);

        ResponseEntity<?> reportResponse = null;
        reportResponse = restTemplate.exchange("http://Report-Service/Report/shareReport", HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(reportResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) reportResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new ReporterException(errors);
            }
        }

        reportResponse = restTemplate.exchange("http://Report-Service/Report/shareReport",HttpMethod.POST,request,new ParameterizedTypeReference<ShareReportResponse>() {});
        return (ShareReportResponse) reportResponse.getBody();
    }

    /************************************************ FALLBACK ********************************************************/

    /**
     * This method is used to return fail values if communication to the Parse-Service fails.
     * @param reportRequest This param is used to identify the method.
     * @return ParseImportedDataResponse This object contains failure values as data.
     */
    public ReportDataResponse reportDataFallback(ReportDataRequest reportRequest){
        ReportDataResponse reportDataResponse = new ReportDataResponse(null);
        //parseImportedDataResponse.setFallback(true);
        //parseImportedDataResponse.setFallbackMessage("{Failed to get parse data}");
        return reportDataResponse;
    }
}
