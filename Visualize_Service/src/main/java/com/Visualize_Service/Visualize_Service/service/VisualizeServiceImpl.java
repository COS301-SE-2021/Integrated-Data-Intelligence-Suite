package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;
import com.Visualize_Service.Visualize_Service.dataclass.MapGraph;
import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.*;
import com.Visualize_Service.Visualize_Service.response.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        if (request.getTrendList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        if (request.getAnomalyList() == null){
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


    public CreateMapGraphResponse createMapGraph(CreateMapGraphRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateMapGraphRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }

        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();


        for (int i = 0; i < reqData.size(); i++) {
            MapGraph newGraph = new MapGraph();
            newGraph.statistic_1 = reqData.get(0).get(i).toString();
            newGraph.lat = "12.3223";
            newGraph.lon = "23.3223";
            newGraph.classnamel = "circle1";
            output.add(newGraph);
        }



        return new CreateMapGraphResponse(output);
    }



    public CreateTimelineGraphResponse createTimelineGraph(CreateTimelineGraphRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        Graph newGraph = new Graph();
        return null;
    }

}
