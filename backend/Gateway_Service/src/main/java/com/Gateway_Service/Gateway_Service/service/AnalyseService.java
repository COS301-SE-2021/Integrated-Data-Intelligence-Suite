package com.Gateway_Service.Gateway_Service.service;

import com.Analyse_Service.Analyse_Service.request.AnalyseUserDataRequest;
import com.Analyse_Service.Analyse_Service.request.TrainUserModelRequest;
import com.Analyse_Service.Analyse_Service.response.AnalyseUserDataResponse;
import com.Analyse_Service.Analyse_Service.response.TrainUserModelResponse;
import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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
    public AnalyseDataResponse analyzeData(AnalyseDataRequest analyseRequest) {
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
        AnalyseDataResponse analyseResponse = restTemplate.postForObject("http://Analyse-Service/Analyse/analyzeData", request, AnalyseDataResponse.class);

        return analyseResponse;
    }


    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return AnalyseUserDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public AnalyseUserDataResponse analyzeUserData(AnalyseUserDataRequest analyseRequest) {
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
        AnalyseUserDataResponse analyseResponse = restTemplate.postForObject("http://Analyse-Service/Analyse/analyzeUserData", request, AnalyseUserDataResponse.class);

        return analyseResponse;
    }


    /**
     * This method is used to communicate to the Analyse-Service.
     * @param analyseRequest This is a request object which contains data required to be analysed.
     * @return TrainUserModelResponse This object contains trained data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "analyzeDataFallback")
    public TrainUserModelResponse trainUserModel(TrainUserModelRequest analyseRequest) {
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
        TrainUserModelResponse analyseResponse = restTemplate.postForObject("http://Analyse-Service/Analyse/trainUserModel", request, TrainUserModelResponse.class);

        return analyseResponse;
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
