package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.TrainModelRequest;
import com.Analyse_Service.Analyse_Service.response.TrainModelResponse;
import org.springframework.stereotype.Service;

@Service
public class TrainServiceImpl {

    /**
     * This method used to analyse data which has been parsed by Parse-Service.
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainModelResponse trainModel(TrainModelRequest request)
            throws InvalidRequestException {

        return new TrainModelResponse();
    }
}
