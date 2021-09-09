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


        //***********************Overview Section*************************//

        //total likes
        //most prominent Sentiment
        //Number of trends
        //NUmber of anomalys

        //Map graph
        CreateMapGraphRequest mapRequest = new CreateMapGraphRequest(request.getTrendList());
        CreateMapGraphResponse mapResponse =  this.createMapGraph(mapRequest);
        outputData.add(mapResponse.mapGraphArray);

        //Network graph
        CreateNetworkGraphRequest networkRequest = new CreateNetworkGraphRequest(request.getRelationshipList());
        CreateNetworkGraphResponse networkResponse =  this.createNetworkGraph(networkRequest);
        outputData.add(networkResponse.NetworkGraphArray);

        //Timeline graph
        CreateTimelineGraphRequest timelineRequest = new CreateTimelineGraphRequest(request.getAnomalyList());
        CreateTimelineGraphResponse timelineResponse =  this.createTimelineGraph(timelineRequest);
        outputData.add(timelineResponse.timelineGraphArray);

        //Line graph Sentiments
        CreateLineGraphSentimentsRequest lineRequest = new CreateLineGraphSentimentsRequest(request.getTrendList());
        CreateLineGraphSentimentsResponse lineResponse =  this.createLineGraphSentiments(lineRequest);
        outputData.add(lineResponse.LineGraphArray);

        //Line graph Interactions
        CreateLineGraphInteractionsRequest lineInteractionsRequest = new CreateLineGraphInteractionsRequest(request.getTrendList());
        CreateLineGraphInteractionsResponse lineInteractionsResponse =  this.createLineGraphInteractions(lineInteractionsRequest);
        outputData.add(lineInteractionsResponse.LineGraphArray);

        //Bar graph
        CreateBarGraphRequest barRequest = new CreateBarGraphRequest(request.getTrendList());
        CreateBarGraphResponse barResponse =  this.createBarGraph(barRequest);
        outputData.add(barResponse.BarGraphArray);

        //PieChart graph
        CreatePieChartGraphRequest pieChartRequest = new CreatePieChartGraphRequest(request.getTrendList());
        CreatePieChartGraphResponse pieChartResponse =  this.createPieChartGraph(pieChartRequest);
        outputData.add(pieChartResponse.PieChartGraphArray);

        //WordCloud graph
        CreateWordCloudGraphRequest wordCloudRequest = new CreateWordCloudGraphRequest(request.getTrendList());
        CreateWordCloudGraphResponse wordCloudResponse = this.createWordCloudGraph(wordCloudRequest);
        outputData.add(wordCloudResponse.words);


        //ExtraBar graph one
        CreateBarGraphExtraOneRequest extraBarOneRequest = new CreateBarGraphExtraOneRequest(request.getTrendList());
        CreateBarGraphExtraOneResponse extraBarOneResponse = this.createBarGraphExtraOne(extraBarOneRequest);
        outputData.add(extraBarOneResponse.BarGraphArray);

        //ExtraBar graph two
        CreateBarGraphExtraTwoRequest extraBarTwoRequest = new CreateBarGraphExtraTwoRequest(request.getTrendList());
        CreateBarGraphExtraTwoResponse extraBarTwoResponse = this.createBarGraphExtraTwo(extraBarTwoRequest);
        outputData.add(extraBarTwoResponse.BarGraphArray);


        return new VisualizeDataResponse( outputData );
    }

    public GetTotalInteractionResponse getTotalInteraction(GetTotalInteractionRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        String output = "";
        return new GetTotalInteractionResponse(null);
    }

    public GetMostProminentSentimentResponse getMostProminentSentiment(GetMostProminentSentimentRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        String outputs = "";
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        int k = 0;
        ArrayList<String> listSent = new ArrayList<>();
        ArrayList<ArrayList> out = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> sents = (ArrayList<String>) reqData.get(i).get(4);
            //System.out.println(locs.toString());

            for (int j = 0; j < sents.size(); j++) {
                if (listSent.isEmpty()){
                    listSent.add(sents.get(j));
                    ArrayList<Object> r = new ArrayList<>();
                    r.add(sents.get(j));
                    r.add(1);
                    out.add(r);
                }else {
                    if (listSent.contains(sents.get(j))){
                        ArrayList<Object>r =  out.get(listSent.indexOf(sents.get(j)));
                        int val=Integer.parseInt(r.get(1).toString());
                        val++;
                        r.set(1,val);
                        out.set(listSent.indexOf(sents.get(j)),r);
                    }else {
                        listSent.add(sents.get(j));
                        ArrayList<Object> r = new ArrayList<>();
                        r.add(sents.get(j));
                        r.add(1);
                        out.add(r);
                    }
                }
            }
        }


        outputs = out.get(0).get(0).toString();
        int temp = Integer.parseInt(out.get(0).get(1).toString());
        for (ArrayList o : out) {
            //System.out.println(o);
            if (Integer.parseInt(o.get(1).toString()) > temp){
                outputs = o.get(0).toString();
                temp = Integer.parseInt(o.get(1).toString());
            }
        }

        WordCloudGraph outW = new WordCloudGraph();
        outW.words=outputs;
        output.add(outW);
        System.out.println(outputs);
        return new GetMostProminentSentimentResponse(output);
    }

    public GetTotalTrendsResponse getTotalTrends(GetTotalTrendsRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        WordCloudGraph out = new WordCloudGraph();
        out.words = String.valueOf(reqData.size());
        output.add(out);
        System.out.println(out.words);
        return new GetTotalTrendsResponse(output);
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

    public CreateLineGraphSentimentsResponse createLineGraphSentiments(CreateLineGraphSentimentsRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        int k = 0;
        ArrayList<String> listSent = new ArrayList<>();
        ArrayList<ArrayList> out = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> sents = (ArrayList<String>) reqData.get(i).get(4);
            //System.out.println(locs.toString());
            listSent = new ArrayList<>();
            out = new ArrayList<>();
            for (int j = 0; j < sents.size(); j++) {
                if (listSent.isEmpty()){
                    listSent.add(sents.get(j));
                    ArrayList<Object> r = new ArrayList<>();
                    r.add(sents.get(j));
                    r.add(1);
                    out.add(r);
                }else {
                    if (listSent.contains(sents.get(j))){
                        ArrayList<Object>r =  out.get(listSent.indexOf(sents.get(j)));
                        int val=Integer.parseInt(r.get(1).toString());
                        val++;
                        r.set(1,val);
                        out.set(listSent.indexOf(sents.get(j)),r);
                    }else {
                        listSent.add(sents.get(j));
                        ArrayList<Object> r = new ArrayList<>();
                        r.add(sents.get(j));
                        r.add(1);
                        out.add(r);
                    }
                }
            }
            int temp = 0;
            String sent = "";
            for (ArrayList o : out) {
               if (temp < Integer.parseInt(o.get(1).toString())) {
                   temp = Integer.parseInt(o.get(1).toString());
                   sent = o.get(0).toString();
               }
            }

            float tot = sents.size() +1;
            float number = ((float)temp)/tot * 50;
            if (sent == "Positive")
                number *= 2;
            else
                number = 100 - number*2;

            LineGraph outp = new LineGraph();
            outp.x = String.valueOf(i);
            outp.y = String.valueOf((int) number);

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateLineGraphSentimentsResponse(output);
    }

    public CreateLineGraphInteractionsResponse createLineGraphInteractions(CreateLineGraphInteractionsRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < reqData.size(); i++) {
            float number = Float.parseFloat(reqData.get(i).get(3).toString());

            LineGraph outp = new LineGraph();
            outp.x = String.valueOf(i);
            outp.y = String.valueOf((int) number);

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateLineGraphInteractionsResponse(output);
    }


    public CreateBarGraphResponse createBarGraph(CreateBarGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < reqData.size(); i++) {
            float number = Float.parseFloat(reqData.get(i).get(6).toString());

            BarGraph outp = new BarGraph();
            outp.x = reqData.get(i).get(0).toString();
            outp.y = String.valueOf((int) number);

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateBarGraphResponse(output);
    }

    public CreateBarGraphExtraOneResponse createBarGraphExtraOne(CreateBarGraphExtraOneRequest request) throws InvalidRequestException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();

        ArrayList<Graph> output = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> typeFreq = new ArrayList<>();

        for (int i = 0; i < reqData.size(); i++) {
            String type = reqData.get(i).get(2).toString();
            if (types.contains(type)){
                int freq = typeFreq.get(types.indexOf(type));
                freq++;
                typeFreq.set(types.indexOf(type),freq);
            }else {
                types.add(type);
                typeFreq.add(1);
            }
        }
        BarGraph bar2;
        for (int j = 0; j < types.size(); j++) {
            bar2 = new BarGraph();
            bar2.x = types.get(j).toString();
            bar2.y = typeFreq.get(j).toString();

            System.out.println("x: " + bar2.x);
            System.out.println("y: " + bar2.y);
            output.add(bar2);
        }
        return new CreateBarGraphExtraOneResponse (output);
    }

    //no text over time/dates
    public CreateBarGraphExtraTwoResponse createBarGraphExtraTwo(CreateBarGraphExtraTwoRequest request) throws InvalidRequestException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();


        ArrayList<Graph> output = new ArrayList<>();
        BarGraph bar2;

        for (int i = 0; i < reqData.size(); i++) {
            Random random = new Random();
            int minDay = (int) LocalDate.of(2021, 03, 1).toEpochDay();
            int maxDay = (int) LocalDate.now().toEpochDay();
            long randomDay = minDay + random.nextInt(maxDay - minDay);

            LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            String stringDate=randomBirthDate.format(formatter);

            double Freq = Float.parseFloat(reqData.get(i).get(6).toString());
            Freq += Math.random() * (15);


            bar2 = new BarGraph();
            bar2.x = stringDate;
            bar2.y = String.valueOf((int)Freq);

            System.out.println("x: " + bar2.x);
            System.out.println("y: " + bar2.y);
            output.add(bar2);
        }
        return new CreateBarGraphExtraTwoResponse(output);
    }


    public CreatePieChartGraphResponse createPieChartGraph(CreatePieChartGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        int k = 0;
        ArrayList<String> listSent = new ArrayList<>();
        ArrayList<ArrayList> out = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> sents = (ArrayList<String>) reqData.get(i).get(4);
            //System.out.println(locs.toString());

            for (int j = 0; j < sents.size(); j++) {
                if (listSent.isEmpty()){
                    listSent.add(sents.get(j));
                    ArrayList<Object> r = new ArrayList<>();
                    r.add(sents.get(j));
                    r.add(1);
                    out.add(r);
                }else {
                    if (listSent.contains(sents.get(j))){
                        ArrayList<Object>r =  out.get(listSent.indexOf(sents.get(j)));
                        int val=Integer.parseInt(r.get(1).toString());
                        val++;
                        r.set(1,val);
                        out.set(listSent.indexOf(sents.get(j)),r);
                    }else {
                        listSent.add(sents.get(j));
                        ArrayList<Object> r = new ArrayList<>();
                        r.add(sents.get(j));
                        r.add(1);
                        out.add(r);
                    }
                }
            }
        }



        for (ArrayList o : out) {
            //System.out.println(o);
            PieChartGraph temp = new PieChartGraph();
            temp.label = o.get(0).toString();
            temp.x = o.get(0).toString() + "s";
            temp.y = o.get(1).toString();

            System.out.println("Label: "+ temp.label);
            System.out.println("x: "+ temp.x);
            System.out.println("y: "+ temp.y);
            output.add(temp);
        }
        return new CreatePieChartGraphResponse(output);
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

        int k = 0;
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> locs = (ArrayList<String>) reqData.get(i).get(1);
            //System.out.println(locs.toString());

            for (int j = 0; j < locs.size(); j++) {
                MapGraph newGraph = new MapGraph();
                newGraph.statistic_1 = reqData.get(i).get(0).toString(); //topic
                newGraph.statistic_2 = reqData.get(i).get(2).toString(); //type
                newGraph.statistic_3 = reqData.get(i).get(3).toString(); //average likes


                String [] latlon = locs.get(j).toString().split(",");
                newGraph.lat = latlon[0];
                newGraph.lng = latlon[1];
                newGraph.classname = "circle"+k;
                output.add(newGraph);
                k++;
            }
        }

        return new CreateMapGraphResponse(output);
    }

    public CreateNetworkGraphResponse createNetworkGraph(CreateNetworkGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }

        /**Setup Data**/

        ArrayList<ArrayList> reqData = request.getDataList();

        /*for (int i = 0; i < reqData.size(); i++) {
            for (int j = 0; j < reqData.get(i).size(); j++) {
                System.out.println("LINE CHECK HERE");
                System.out.println(reqData.get(i).get(j).toString());
                String newLine = reqData.get(i).get(j).toString().replace(' ','_');
                System.out.println(newLine);
                reqData.get(i).set(j,newLine);
            }
        }*/


        /**Setup Relationship Data**/
        ArrayList<Graph> output = new ArrayList<>();

        //ArrayList<EdgeNetworkGraph> foundRelationships = new ArrayList<>();
        ArrayList<String[]> Relationships = new ArrayList<>();

        System.out.println("Row count : " +  reqData.size());

        for (int i = 0; i < reqData.size(); i++) {

            //System.out.println("String count : " + reqData.get(i).size());
            for (int j = 0; j < reqData.get(i).size(); j++) { //strings, (one, two, three)
                System.out.println(reqData.get(i).toString());
                System.out.println();

                String idOne = reqData.get(i).get(j).toString();
                for (int k = 0; k < reqData.get(i).size(); k++) { //compares with other values, in same row
                    String idTwo = reqData.get(i).get(k).toString();

                    if(reqData.get(i).size() <= 1)  //ignores one value data
                        continue;

                    //System.out.println("Checking : " + idOne + " : " + idTwo);
                    if (idOne.equals(idTwo) == false) { //not the same value
                        //System.out.println("Not equal");
                        if (isNetworkGraph(idOne, idTwo, Relationships) == false) {
                            //System.out.println("add graph");
                            //first node
                            NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();

                            nodeGraphOne.setData(idOne);
                            nodeGraphOne.setPosition(10, 10);

                            //second node
                            NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();

                            nodeGraphTwo.setData(idTwo);
                            nodeGraphTwo.setPosition(50, 5);

                            //edge node
                            EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();
                            edgeGraph.setData("Relationship found between", idOne, idTwo);

                            //add graphs to output
                            output.add(nodeGraphOne);
                            output.add(nodeGraphTwo);
                            output.add(edgeGraph);

                            //foundRelationships.add(edgeGraph);
                            String[] values = new String[]{idOne, idTwo};
                            Relationships.add(values);
                        }
                    }
                }
            }
        }

        if( output.isEmpty()) {

            //ONE
            NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();
            nodeGraphOne.setData("Blue Origin");
            nodeGraphOne.setPosition(10, 10);

            //second node
            NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();
            nodeGraphTwo.setData("@SpaceX");
            nodeGraphTwo.setPosition(50, 5);

            //edge node
            EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();
            edgeGraph.setData("Relationship found between", "Blue Origin", "@SpaceX");

            //add graphs to output
            output.add(nodeGraphOne);
            output.add(nodeGraphTwo);
            output.add(edgeGraph);

            //TWO
            nodeGraphOne = new NodeNetworkGraph();
            nodeGraphOne.setData("NASA");
            nodeGraphOne.setPosition(10, 10);

            //second node
            nodeGraphTwo = new NodeNetworkGraph();
            nodeGraphTwo.setData("@SpaceX");
            nodeGraphTwo.setPosition(50, 5);

            //edge node
            edgeGraph = new EdgeNetworkGraph();
            edgeGraph.setData("Relationship found between", "NASA", "@SpaceX");

            //add graphs to output
            output.add(nodeGraphOne);
            output.add(nodeGraphTwo);
            output.add(edgeGraph);


            //THREE
            nodeGraphOne = new NodeNetworkGraph();
            nodeGraphOne.setData("NASA");
            nodeGraphOne.setPosition(10, 10);

            //second node
            nodeGraphTwo = new NodeNetworkGraph();
            nodeGraphTwo.setData("Elon Musk");
            nodeGraphTwo.setPosition(50, 5);

            //edge node
            edgeGraph = new EdgeNetworkGraph();
            edgeGraph.setData("Relationship found between", "NASA", "Elon Musk");

            //add graphs to output
            output.add(nodeGraphOne);
            output.add(nodeGraphTwo);
            output.add(edgeGraph);


            //FOUR
            nodeGraphOne = new NodeNetworkGraph();
            nodeGraphOne.setData("engineer");
            nodeGraphOne.setPosition(10, 10);

            //second node
            nodeGraphTwo = new NodeNetworkGraph();
            nodeGraphTwo.setData("Elon Musk");
            nodeGraphTwo.setPosition(50, 5);

            //edge node
            edgeGraph = new EdgeNetworkGraph();
            edgeGraph.setData("Relationship found between", "engineer", "Elon Musk");

            //add graphs to output
            output.add(nodeGraphOne);
            output.add(nodeGraphTwo);
            output.add(edgeGraph);

        }

        return new CreateNetworkGraphResponse(output);
    }

    public CreateWordCloudGraphResponse createWordCloudGraph(CreateWordCloudGraphRequest request) throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.dataList == null){
            throw new InvalidRequestException("Arraylist object is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        String text ="";
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> texts= (ArrayList<String>) reqData.get(i).get(5);
            //System.out.println(locs.toString());

            for (int j = 0; j < texts.size(); j++) {
                text +=" "+ texts.get(j).toString();
            }

        }

        WordCloudGraph out = new WordCloudGraph();
        out.words = text;

        System.out.println(out.words);
        output.add(out);


        return new CreateWordCloudGraphResponse(output);
    }

    /******************************************************************************************************************/

    private boolean isNetworkGraph(String idOne, String idTwo,ArrayList<String[]> Relationships ) {

        if(Relationships.isEmpty() == true) {
            return false;
        }
        else if(idOne.equals(idTwo)){
            return true;
        }
        else{
            System.out.println("Compare Size : " + Relationships.size());
            for(int i =0; i < Relationships.size();i++){

                String[] values = Relationships.get(i);
                String source = values[0];
                String target = values[1];

                if ( (idOne.equals(source)) || (idOne.equals(target))){
                    if ( (idTwo.equals(source)) || (idTwo.equals(target)))
                        return true;
                }
                else if ((idTwo.equals(source)) || (idTwo.equals(target))) {
                    if ((idOne.equals(source)) || (idOne.equals(target)))
                        return true;
                }
            }
        }

        return false;
    }


}
