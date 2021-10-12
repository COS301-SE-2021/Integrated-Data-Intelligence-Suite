package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.dataclass.ServiceErrorResponse;
import com.Analyse_Service.Analyse_Service.exception.AnalyserException;
import com.Analyse_Service.Analyse_Service.exception.AnalysingModelException;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import com.Analyse_Service.Analyse_Service.service.TrainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Analyse")
public class AnalyseServiceController {

    @Autowired
    private AnalyseServiceImpl analyseService;

    @Autowired
    private TrainServiceImpl trainService;

    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param request This is a request entity which contains a AnalyseDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Analyse-Service.
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/analyzeData")
    public @ResponseBody ResponseEntity<?> analyzeData(@RequestBody AnalyseDataRequest request) throws AnalyserException {
        //AnalyseDataRequest request = getBody();

        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }

        if (request.getDataList() == null){
            throw new InvalidRequestException("AnalyseData DataList is null");
        }

        AnalyseDataResponse analyseDataResponse = null;
        try{
            analyseDataResponse = analyseService.analyzeData(request);
        } catch (AnalyserException e){
            throw new AnalyserException(e.getMessage());
        }

        return new ResponseEntity<>(analyseDataResponse, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param request This is a request entity which contains a AnalyseDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Analyse-Service.
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/analyzeUserData")
    public @ResponseBody ResponseEntity<?> analyzeUserData(@RequestBody AnalyseUserDataRequest request) throws AnalyserException {
        //AnalyseDataRequest request = getBody();
        if (request == null) {
            throw new InvalidRequestException("getModelById Request Object is null");
        }

        if (request.getModelId() == null) {
            throw new InvalidRequestException("getModelById Request ID is null");
        }

        AnalyseUserDataResponse analyseUserDataResponse = null;

        try{
            analyseUserDataResponse = analyseService.analyzeUserData(request);
        } catch (AnalyserException e){
            throw new AnalyserException(e.getMessage());
        }

        return new ResponseEntity<>(analyseUserDataResponse, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * This method is used to facilitate communication to the Analyse-Service.
     * @param request This is a request entity which contains a GetModelByIdRequest object.
     * @return GetModelByIdResponse This object contains model information of id
     * @throws Exception This is thrown if exception caught in Analyse-Service.
     */
    @PostMapping("/getModelById")
    public @ResponseBody ResponseEntity<?> getModelById(@RequestBody GetModelByIdRequest request) throws AnalyserException {
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("getModelById Request Object is null");
        }

        if (request.getModelId() == null) {
            throw new InvalidRequestException("getModelById Request ID is null");
        }

        GetModelByIdResponse getModelByIdResponse = null;
        //try{
            getModelByIdResponse = analyseService.getModelById(request);
        //} catch (AnalyserException e){
            //throw new AnalyserException(e.getMessage());
        //}

        return new ResponseEntity<>(getModelByIdResponse, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * This method is used to facilitate communication to the Train-Service.
     * @param request This is a request entity which contains a TrainModelRequest object.
     * @return TrainModelResponse This object contains trained data which has been processed by Train-Service.
     * @throws Exception This is thrown if exception caught in Train-Service.
     */
    @PostMapping("/trainUserModel")
    public @ResponseBody ResponseEntity<?> trainUserModel(@RequestBody TrainUserModelRequest request) throws AnalyserException {
        //AnalyseDataRequest request = getBody();
        if (request == null) {
            throw new InvalidRequestException("TrainModelRequest Object is null");
        }

        if (request.getDataList() == null){
            throw new InvalidRequestException("TrainModel DataList is null");
        }

        if (request.getModelName() == null){
            throw new InvalidRequestException("Model Name is null");
        }

        TrainUserModelResponse trainUserModelResponse = null;
        try{
            trainUserModelResponse = trainService.trainUserModel(request);
        } catch (AnalyserException e){
            throw new AnalyserException(e.getMessage());
        }

        return new ResponseEntity<>(trainUserModelResponse, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/trainApplicationData")
    public boolean trainApplicationData() {
        //AnalyseDataRequest request = getBody();
        try {
            trainService.trainApplicationModel();
            return true;
        } catch (AnalyserException e){
            e.printStackTrace();
            return false;
        }
    }
}
