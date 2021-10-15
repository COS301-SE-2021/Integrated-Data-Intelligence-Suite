package com.Visualize_Service.Visualize_Service.controller;

import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeDataResponse;
import com.Visualize_Service.Visualize_Service.service.VisualizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Visualize")
public class VisualizeServiceController {
    @Autowired
    private VisualizeServiceImpl service;

    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param request This is a request entity which contains a AnalyseDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Analyse-Service.
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/visualizeData")
    public @ResponseBody ResponseEntity<?> visualizeData(@RequestBody VisualizeDataRequest request) throws Exception{
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }

        VisualizeDataResponse visualizeDataResponse = service.visualizeData(request);

        return new ResponseEntity<>(visualizeDataResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
