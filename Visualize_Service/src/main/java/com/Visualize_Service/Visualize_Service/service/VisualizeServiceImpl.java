package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;
import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeAnalyseDataResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VisualizeServiceImpl {
    public VisualizeAnalyseDataResponse visualizeData(VisualizeDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getPatternList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        if (request.getRelationshipList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        if (request.getPredictionList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }

        return new VisualizeAnalyseDataResponse(null);
    }



    private void createlineGraph(){
        Graph newGraph = new Graph();
    }


    private void createNetworkGraph(){

    }


    private void createMapGraph(){

    }



    private void createTimelineGraph(){

    }

}
