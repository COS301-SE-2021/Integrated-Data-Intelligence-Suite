package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.report.ShareReportResponse;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataResponse;
import com.Gateway_Service.Gateway_Service.exception.ReporterException;
import com.Gateway_Service.Gateway_Service.exception.VisualizerException;
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
public class VisualizeService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method is used to communicate to the Analyse-Service.
     * @param visualizeRequest This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data returned by Analyse-Service
     */
    //@HystrixCommand(fallbackMethod = "visualizeDataFallback")
    public VisualizeDataResponse visualizeData(VisualizeDataRequest visualizeRequest) throws VisualizerException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(visualizeRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //VisualizeDataResponse visualizeResponse = restTemplate.postForObject("http://Visualize-Service/Visualize/visualizeData", request, VisualizeDataResponse.class);

        ResponseEntity<?> visualizeResponse = null;
        visualizeResponse = restTemplate.exchange("http://Visualize-Service/Visualize/visualizeData",HttpMethod.POST,request,new ParameterizedTypeReference<VisualizeDataResponse>() {});



        if(visualizeResponse.getBody().getClass() != VisualizeDataResponse.class ) {
            visualizeResponse = restTemplate.exchange("http://Visualize-Service/Visualize/visualizeData", HttpMethod.POST,request,new ParameterizedTypeReference<VisualizeDataResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) visualizeResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new VisualizerException(errors);
            }
        }


        return (VisualizeDataResponse) visualizeResponse.getBody();
    }

    /**
     * This method is used to return fail values if communication to the Analyse-Service fails.
     * @param visualizeRequest This param is used to identify the method.
     * @return AnalyseDataResponse This object contains failure values as data.
     */
    public VisualizeDataResponse visualizeDataFallback(VisualizeDataRequest visualizeRequest){
        VisualizeDataResponse visualizeDataResponse =  new VisualizeDataResponse(null);
        visualizeDataResponse.setFallback(true);
        visualizeDataResponse.setFallbackMessage("{Failed to get visualize Data's data}");
        return visualizeDataResponse;
    }

}
