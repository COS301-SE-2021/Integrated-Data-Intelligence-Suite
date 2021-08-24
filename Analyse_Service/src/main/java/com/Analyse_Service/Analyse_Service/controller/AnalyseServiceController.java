package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Analyse")
public class AnalyseServiceController {

    @Autowired
    private AnalyseServiceImpl service;

    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param requestEntity This is a request entity which contains a AnalyseDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Analyse-Service.
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/analyzeData")
    public @ResponseBody AnalyseDataResponse analyzeData(@RequestBody AnalyseDataRequest request) throws Exception{
        //AnalyseDataRequest request = getBody();

        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }

        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        return service.analyzeData(request);
    }
    /*@PostMapping("/analyzeData")
    public AnalyseDataResponse analyzeData(RequestEntity<AnalyseDataRequest> requestEntity) throws Exception{
        AnalyseDataRequest request = requestEntity.getBody();
        return service.analyzeData(request);
    }*/


}
