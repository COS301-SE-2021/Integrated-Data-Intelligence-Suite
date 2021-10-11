package com.Gateway_Service.Gateway_Service.service;


import com.Gateway_Service.Gateway_Service.dataclass.analyse.*;
import com.Gateway_Service.Gateway_Service.exception.AnalyserException;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.Gateway_Service.Gateway_Service.rri.ServiceErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
    public AnalyseDataResponse analyzeData(AnalyseDataRequest analyseRequest) throws AnalyserException {
        //restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(analyseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResponseEntity<?> analyseResponse = null;
        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(analyseResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) analyseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                throw new AnalyserException(serviceErrorResponse.getErrors().get(0));
            }
        }

        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeData",HttpMethod.POST,request,new ParameterizedTypeReference<AnalyseDataResponse>() {});
        return (AnalyseDataResponse) analyseResponse.getBody();
    }


    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return AnalyseUserDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public AnalyseUserDataResponse analyzeUserData(AnalyseUserDataRequest analyseRequest) throws AnalyserException {
        //restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(analyseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResponseEntity<?> analyseResponse = null;
        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeUserData",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(analyseResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) analyseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                throw new AnalyserException(serviceErrorResponse.getErrors().get(0));
            }
        }

        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/analyzeUserData",HttpMethod.POST,request,new ParameterizedTypeReference<AnalyseUserDataResponse>() {});
        return (AnalyseUserDataResponse) analyseResponse.getBody();
    }


    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return AnalyseUserDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public GetModelByIdResponse getModelById(GetModelByIdRequest analyseRequest) throws AnalyserException {
        //restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(analyseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        ResponseEntity<?> analyseResponse = null;
        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/getModelById",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(analyseResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) analyseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                throw new AnalyserException(serviceErrorResponse.getErrors().get(0));
            }
        }

        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/getModelById",HttpMethod.POST,request,new ParameterizedTypeReference<GetModelByIdResponse>() {});
        return (GetModelByIdResponse) analyseResponse.getBody();
    }




    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return TrainUserModelResponse This object contains trained data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public TrainUserModelResponse trainUserModel(TrainUserModelRequest analyseRequest) throws AnalyserException {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(analyseRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //TrainUserModelResponse analyseResponse = restTemplate.postForObject("http://Analyse-Service/Analyse/trainUserModel", request, TrainUserModelResponse.class);

        ResponseEntity<?> analyseResponse = null;
        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/trainUserModel",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

        if(analyseResponse.getBody().getClass() == ServiceErrorResponse.class ) {
            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) analyseResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                throw new AnalyserException(serviceErrorResponse.getErrors().get(0));
            }
        }

        analyseResponse = restTemplate.exchange("http://Analyse-Service/Analyse/trainUserModel",HttpMethod.POST,request,new ParameterizedTypeReference<AnalyseDataResponse>() {});
        return (TrainUserModelResponse) analyseResponse.getBody();
    }



    /**
     * This method is used to communicate to the Analyse-Service.
     * @return Boolean This object results in failed/succeeded training
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public boolean trainApplicationData() {

        Boolean analyseResponse = restTemplate.getForObject("http://Analyse-Service/Analyse/trainApplicationData",boolean.class);

        return analyseResponse;
    }


    /************************************************ FALLBACK ********************************************************/

    /**
     * This method is used to return fail values if communication to the Analyse-Service fails.
     * @param analyseRequest This param is used to identify the method.
     * @return AnalyseDataResponse This object contains failure values as data.
     */
    public AnalyseDataResponse analyzeDataFallback(AnalyseDataRequest analyseRequest){
        AnalyseDataResponse analyseDataResponse =  new AnalyseDataResponse(null, null, null, null, null, null);
        analyseDataResponse.setFallback(true);
        analyseDataResponse.setFallbackMessage("{Failed to get analyzeData's data}");
        return analyseDataResponse;
    }

}
