package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;
import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.CreateLineGraphRequest;
import com.Visualize_Service.Visualize_Service.request.CreateMapGraphRequest;
import com.Visualize_Service.Visualize_Service.request.CreateNetworkGraphRequest;
import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.CreateLineGraphResponse;
import com.Visualize_Service.Visualize_Service.response.CreateMapGraphResponse;
import com.Visualize_Service.Visualize_Service.response.CreateNetworkGraphResponse;
import com.Visualize_Service.Visualize_Service.response.VisualizeDataResponse;
import org.springframework.stereotype.Service;

@Service
public class VisualizeServiceImpl {
    public VisualizeDataResponse visualizeData(VisualizeDataRequest request) throws InvalidRequestException {
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

        return new VisualizeDataResponse(null);
    }



    public CreateLineGraphResponse createlineGraph(CreateLineGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        Graph newGraph = new Graph();
        return null;
    }


    public CreateNetworkGraphResponse createNetworkGraph(CreateNetworkGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        Graph newGraph = new Graph();
        return null;
    }


    private CreateMapGraphResponse createMapGraph(CreateMapGraphRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateMapGraphRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        Graph newGraph = new Graph();
        return null;
    }



    private void createTimelineGraph(CreateNetworkGraphRequest request){

    }

}
