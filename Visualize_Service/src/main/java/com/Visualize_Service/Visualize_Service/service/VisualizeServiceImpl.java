package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.*;
import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.*;
import com.Visualize_Service.Visualize_Service.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@Service
public class VisualizeServiceImpl {
    public VisualizeDataResponse visualizeData(VisualizeDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getAnomalyList() == null){
            throw new InvalidRequestException("Arraylist of AnomalyList is null");
        }
        /*if (request.getPatternList() == null){
            throw new InvalidRequestException("Arraylist of PatternList is null");
        }
        if (request.getPredictionList() == null){
            throw new InvalidRequestException("Arraylist of PredictionList is null");
        }*/
        if (request.getRelationshipList() == null){
            throw new InvalidRequestException("Arraylist of RelationshipList is null");
        }
        if (request.getTrendList() == null){
            throw new InvalidRequestException("Arraylist of TrendList is null");
        }

        ArrayList<ArrayList> outputData = new ArrayList<>();

        //map graph
        CreateMapGraphRequest mapRequest = new CreateMapGraphRequest(request.getTrendList());
        CreateMapGraphResponse mapResponse =  this.createMapGraph(mapRequest);

        outputData.add(mapResponse.mapGraphArray);


        //network graph
        CreateNetworkGraphRequest networkRequest = new CreateNetworkGraphRequest(request.getRelationshipList());
        CreateNetworkGraphResponse networkResponse =  this.createNetworkGraph(networkRequest);

        outputData.add(networkResponse.NetworkGraphArray);

        //timeline graph
        CreateTimelineGraphRequest timelineRequest = new CreateTimelineGraphRequest(request.getAnomalyList());
        CreateTimelineGraphResponse timelineResponse =  this.createTimelineGraph(timelineRequest);

        outputData.add(timelineResponse.timelineGraphArray);


        //line graph
        CreateLineGraphRequest lineRequest = new CreateLineGraphRequest(request.getRelationshipList());
        //CreateLineGraphResponse lineResponse =  this.createLineGraph(lineRequest);

        //outputData.add(lineResponse.lineGraphArray);


        return new VisualizeDataResponse( outputData );
    }



    public CreateLineGraphResponse createLineGraph(CreateLineGraphRequest request) throws InvalidRequestException{
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

        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        ArrayList<EdgeNetworkGraph> foundRelationships = new ArrayList<>();

        for(int i =0; i < reqData.size(); i++ ){
            for(int j=0; j < reqData.get(i).size(); j++){ //strings, (one, two, three)

                String idOne = reqData.get(j).toString();

                for(int k=0; k<reqData.get(i).size(); k++ ){ //compares with other values, in same row

                    String idTwo = reqData.get(k).toString();

                    if(idOne != idTwo){ //not the same value
                        if (isNetworkGraph(idOne, idTwo,foundRelationships) != false){

                            //first node
                            NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();

                            NodeNetworkGraph.data dataNodeOne = nodeGraphOne .new data();
                            dataNodeOne.id = idOne;

                            NodeNetworkGraph.position positionOne = nodeGraphOne .new position();
                            positionOne.x = 0;
                            positionOne.y = 0;

                            //second node
                            NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();

                            NodeNetworkGraph.data dataNodeTwo = nodeGraphTwo .new data();
                            dataNodeTwo.id = idTwo;

                            NodeNetworkGraph.position positionTwo = nodeGraphTwo .new position();
                            positionTwo.x = 0;
                            positionTwo.y = 0;

                            //edge node
                            EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();

                            EdgeNetworkGraph.data dataEdge = edgeGraph.new data();
                            dataEdge.id = "Relationship found between";
                            dataEdge.source = idOne;
                            dataEdge.target = idTwo;

                            //add graphs to output
                            output.add(nodeGraphOne);
                            output.add(nodeGraphTwo);
                            output.add(edgeGraph);

                            foundRelationships.add(edgeGraph);
                        }
                    }
                }
            }
        }



        return new CreateNetworkGraphResponse(output);
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
            ArrayList<String> locs = (ArrayList<String>) reqData.get(i).get(1);
            //System.out.println(locs.toString());
            for (int j = 0; j < locs.size(); j++) {
                MapGraph newGraph = new MapGraph();
                newGraph.statistic_1 = reqData.get(i).get(0).toString();
                String [] latlon = locs.get(j).toString().split(",");
                newGraph.lat = latlon[0];
                newGraph.lon = latlon[1];
                newGraph.classnamel = "circle1";
                output.add(newGraph);
            }
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

        ArrayList<String> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            TimelineGraph newGraph = new TimelineGraph();

            Random random = new Random();
            int minDay = (int) LocalDate.of(2021, 03, 1).toEpochDay();
            int maxDay = (int) LocalDate.now().toEpochDay();
            long randomDay = minDay + random.nextInt(maxDay - minDay);

            LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            String stringDate=randomBirthDate.format(formatter);

            newGraph.title = stringDate;
            newGraph.cardTitle = "Anomaly Detected";
            newGraph.cardSubtitle = reqData.get(i);

            output.add(newGraph);
        }
        return new CreateTimelineGraphResponse(output);
    }


    /******************************************************************************************************************/

    private boolean isNetworkGraph(String idOne, String idTwo, ArrayList<EdgeNetworkGraph> foundRelationships) {

        if(foundRelationships.isEmpty() == true) {
            return true;
        }
        else{
            for(int i =0; i < foundRelationships.size();i++){

                EdgeNetworkGraph edgeNetworkGraph = foundRelationships.get(i);

                String source = edgeNetworkGraph.getSource();
                String target = edgeNetworkGraph.getTarget();

                if ( (idOne == source) || (idOne == target)){
                    if((idOne == source) && (idTwo == target))
                        return true;
                    else if((idOne == target) && (idTwo == source))
                        return true;
                }
                else if ( (idTwo == source) || (idTwo == target)){
                    if((idOne == source) && (idTwo == target))
                        return true;
                    else if((idOne == target) && (idTwo == source))
                        return true;
                }
            }
        }

        return false;
    }


}
