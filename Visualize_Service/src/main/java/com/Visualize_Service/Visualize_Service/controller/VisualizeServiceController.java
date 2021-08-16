package com.Visualize_Service.Visualize_Service.controller;

import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeDataResponse;
import com.Visualize_Service.Visualize_Service.service.VisualizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Visualize")
public class VisualizeServiceController {
    @Autowired
    private VisualizeServiceImpl service;

    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param requestEntity This is a request entity which contains a AnalyseDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Analyse-Service.
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/visualizeData")
    public VisualizeDataResponse visualizeData(RequestEntity<VisualizeDataRequest> requestEntity) throws Exception{
        VisualizeDataRequest request = requestEntity.getBody();
        return service.visualizeData(request);
    }
}
