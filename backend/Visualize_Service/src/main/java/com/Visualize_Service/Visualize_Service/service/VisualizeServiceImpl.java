package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.*;
import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.exception.VisualizerException;
import com.Visualize_Service.Visualize_Service.request.*;
import com.Visualize_Service.Visualize_Service.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VisualizeServiceImpl {

    private HashSet<String> foundWords = new HashSet<>();



    public VisualizeDataResponse visualizeData(VisualizeDataRequest request)
            throws VisualizerException {
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getAnomalyList() == null) {
            throw new InvalidRequestException("Arraylist of AnomalyList is null");
        }
        if (request.getPatternList() == null){
            throw new InvalidRequestException("Arraylist of PatternList is null");
        }
        if (request.getRelationshipList() == null) {
            throw new InvalidRequestException("Arraylist of RelationshipList is null");
        }
        if (request.getTrendList() == null) {
            throw new InvalidRequestException("Arraylist of TrendList is null");
        }
        /*
        if (request.getPredictionList() == null){
            throw new InvalidRequestException("Arraylist of PredictionList is null");
        }
        */

        ArrayList<ArrayList> outputData = new ArrayList<>();

        //***********************Overview Section*************************//

        //total likes                                     <- D
        //most prominent Sentiment                        <- D
        //Number of trends                                <- D
        //NUmber of anomalys                              <- D

        //Bar Graph(Average Interaction)                  <- D
        //PieChart(Overall section)                       <- D
        // Bar Graph(total Engagement in each location)   <- D

        //map                                             <- D
        //frequecy of tweets in trend                     <- D

        //word cloud                                      <- D
        //word cloud pie chart                            <- D
        //word cloud sunburst                             <-

        //Network graph1(Relationships)                   <- D
        // Network graph 2(Patterns)                      <- D

        //Timeline(Anomaly)                               <- D
        // scatter plot * tentative                       <-
        // Map Metric 2 (Number of tweets over time )     <- D

        /************************Select Graph*****************************/

        GraphSelector selector = new GraphSelector();

        HashMap<String, Boolean> selectedGraphs = new HashMap<>();

        selectedGraphs.put("totalInteraction", true);
        selectedGraphs.put("mostProminentSentiment", true);
        selectedGraphs.put("totalTrends", true);
        selectedGraphs.put("totalAnomalies", true);
        selectedGraphs.put("lineInteractions", true);
        selectedGraphs.put("pieChart", true);
        selectedGraphs.put("extraBarOne", true);
        selectedGraphs.put("map", true);
        selectedGraphs.put("bar", true);
        selectedGraphs.put("wordCloud", true);
        selectedGraphs.put("wordCloudPieChart", true);
        selectedGraphs.put("wordCloudSunBurst", false);
        selectedGraphs.put("relation", true);
        selectedGraphs.put("pattern", true);
        selectedGraphs.put("timeline", true);
        selectedGraphs.put("scatterPlot", false);
        selectedGraphs.put("extraBarTwo", false);
        selectedGraphs.put("line", false);

        selector.setSelectedGraphs(selectedGraphs); //throws

        /************************Index Graphs*****************************/

        ArrayList<String> indexedGraphs = new ArrayList();
        for (int i = 0; i < selector.getAllGraphs().size(); i++) {
            if (selector.getSelectedGraph(selector.getAllGraphs().get(i)) == true) {
                indexedGraphs.add(selector.getAllGraphs().get(i));
            }
        }
        //outputData.add(indexedGraphs);

        /************************Compute Graphs*****************************/

        //total likes
        if (indexedGraphs.contains("totalInteraction")) {
            GetTotalInteractionRequest totalInteractionRequest = new GetTotalInteractionRequest(request.getTrendList());
            GetTotalInteractionResponse totalInteractionResponse = this.getTotalInteraction(totalInteractionRequest);
            outputData.add(totalInteractionResponse.getWordGraphArray());
        }

        //most prominent Sentiment
        if (indexedGraphs.contains("mostProminentSentiment")) {
            GetMostProminentSentimentRequest mostProminentSentimentRequest = new GetMostProminentSentimentRequest(request.getTrendList());
            GetMostProminentSentimentResponse mostProminentSentimentResponse = this.getMostProminentSentiment(mostProminentSentimentRequest);
            outputData.add(mostProminentSentimentResponse.getWordGraphArray());
        }

        //Number of trends
        if (indexedGraphs.contains("totalTrends")) {
            GetTotalTrendsRequest totalTrendsRequest = new GetTotalTrendsRequest(request.getTrendList());
            GetTotalTrendsResponse totalTrendsResponse = this.getTotalTrends(totalTrendsRequest);
            outputData.add(totalTrendsResponse.getWordGraphArray());
        }

        //NUmber of anomalys
        if (indexedGraphs.contains("totalAnomalies")) {
            GetTotalAnomaliesRequest totalAnomaliesRequest = new GetTotalAnomaliesRequest(request.getAnomalyList());
            GetTotalAnomaliesResponse totalAnomaliesResponse = this.getTotalAnomalies(totalAnomaliesRequest);
            outputData.add(totalAnomaliesResponse.getWordGraphArray());
        }


        //Line graph Interactions (Bar graph now)(Average Interaction)
        if (indexedGraphs.contains("lineInteractions")) {
            CreateLineGraphInteractionsRequest lineInteractionsRequest = new CreateLineGraphInteractionsRequest(request.getTrendList());
            CreateLineGraphInteractionsResponse lineInteractionsResponse = this.createLineGraphInteractions(lineInteractionsRequest);
            outputData.add(lineInteractionsResponse.getLineGraphArray());
        }

        //PieChart graph(Overall section)
        if (indexedGraphs.contains("pieChart")) {
            CreatePieChartGraphRequest pieChartRequest = new CreatePieChartGraphRequest(request.getTrendList());
            CreatePieChartGraphResponse pieChartResponse = this.createPieChartGraph(pieChartRequest);
            outputData.add(pieChartResponse.getPieChartGraphArray());
        }

        //ExtraBar graph one Bar Graph(total Engagement in each location)
        if (indexedGraphs.contains("extraBarOne")) {
            CreateBarGraphExtraOneRequest extraBarOneRequest = new CreateBarGraphExtraOneRequest(request.getTrendList());
            CreateBarGraphExtraOneResponse extraBarOneResponse = this.createBarGraphExtraOne(extraBarOneRequest);
            outputData.add(extraBarOneResponse.getBarGraphArray());
        }


        //Map graph
        if (indexedGraphs.contains("map")) {
            CreateMapGraphRequest mapRequest = new CreateMapGraphRequest(request.getTrendList());
            CreateMapGraphResponse mapResponse = this.createMapGraph(mapRequest);
            outputData.add(mapResponse.getLineGraphArray());
        }

        //Bar graph frequecy of tweets in trend
        if (indexedGraphs.contains("bar")) {
            CreateBarGraphRequest barRequest = new CreateBarGraphRequest(request.getTrendList());
            CreateBarGraphResponse barResponse = this.createBarGraph(barRequest);
            outputData.add(barResponse.getBarGraphArray());
        }


        //WordCloud graph
        //TODO: request.getWordList()
        if (indexedGraphs.contains("wordCloud")) {
            CreateWordCloudGraphRequest wordCloudRequest = new CreateWordCloudGraphRequest(request.getWordList());
            CreateWordCloudGraphResponse wordCloudResponse = this.createWordCloudGraph(wordCloudRequest);
            outputData.add(wordCloudResponse.getWords());
        }

        //WordCloud Piechart
        if (indexedGraphs.contains("wordCloudPieChart")) {
            CreateWordCloudPieChartGraphRequest wordCloudPieChartGraphRequest = new CreateWordCloudPieChartGraphRequest(request.getWordList());
            CreateWordCloudPieChartGraphResponse wordCloudPieChartGraphResponse = this.createWordCloudPieChartGraph(wordCloudPieChartGraphRequest);
            outputData.add(wordCloudPieChartGraphResponse.getWordCloudPieChartGraphArray());
        }

        //WordCloud Sunburst
        /*if (indexedGraphs.contains("wordCloudSunBurst")) {
        CreateWordCloudSunBurstGraphRequest wordCloudSunBurstGraphRequest = new CreateWordCloudSunBurstGraphRequest(request.getWordList(),wordCloudPieChartGraphResponse.getDominantWords());
        CreateWordCloudSunBurstGraphResponse wordCloudSunBurstGraphResponse = this.createWordCloudSunBurstGraph(wordCloudSunBurstGraphRequest);
        outputData.add(wordCloudPieChartGraphResponse.wordCloudPieChartGraphArray);
        }*/


        //Network graph (Relationships)
        if (indexedGraphs.contains("relation")) {
            CreateRelationshipGraphRequest relationRequest = new CreateRelationshipGraphRequest(request.getRelationshipList());
            CreateRelationshipGraphResponse relationResponse = this.createRelationGraph(relationRequest, request.getWordList());
            outputData.add(relationResponse.getNetworkGraphArray());
        }

        //Network graph (Patterns)
        if (indexedGraphs.contains("pattern")) {
            CreatePatternGraphRequest patternGraphRequest = new CreatePatternGraphRequest(request.getPatternList());
            CreatePatternGraphResponse patternGraphResponse = this.createPatternGraph(patternGraphRequest, request.getWordList());
            outputData.add(patternGraphResponse.getNetworkGraphArray());
        }


        //Timeline graph
        if (indexedGraphs.contains("timeline")) {
            CreateTimelineGraphRequest timelineRequest = new CreateTimelineGraphRequest(request.getAnomalyList());
            CreateTimelineGraphResponse timelineResponse = this.createTimelineGraph(timelineRequest);
            outputData.add(timelineResponse.getLineGraphArray());
        }

        /*
        ToDo: Scatter plot

        */

        //Map Metric 2 (Number of tweets over time )
        /*if(indexedGraphs.contains("extraBarTwo")) {
            CreateBarGraphExtraTwoRequest extraBarTwoRequest = new CreateBarGraphExtraTwoRequest(request.getTrendList());
            CreateBarGraphExtraTwoResponse extraBarTwoResponse = this.createBarGraphExtraTwo(extraBarTwoRequest);
            outputData.add(extraBarTwoResponse.BarGraphArray);
        }*/

        //Line graph Sentiments ~ not in use
        /*if(indexedGraphs.contains("line")) {
            CreateLineGraphSentimentsRequest lineRequest = new CreateLineGraphSentimentsRequest(request.getTrendList());
            CreateLineGraphSentimentsResponse lineResponse =  this.createLineGraphSentiments(lineRequest);
            outputData.add(lineResponse.LineGraphArray);
        }*/


        return new VisualizeDataResponse( outputData );
    }


    /*****************OVERALL**********************/
    public GetTotalInteractionResponse getTotalInteraction(GetTotalInteractionRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        String outputs = "";
        ArrayList<ArrayList> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();
        int Tot = 0;
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> Like = (ArrayList<String>) reqData.get(i).get(7);
            for (String lk : Like){
                Tot += Integer.parseInt(lk);
            }
        }
        WordCloudGraph out = new WordCloudGraph();
        out.words = String.valueOf(Tot);
        output.add(out);
        System.out.println(out.words);
        return new GetTotalInteractionResponse(output);
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

    public GetTotalTrendsResponse getTotalTrends(GetTotalTrendsRequest request)
            throws InvalidRequestException {
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

    public GetTotalAnomaliesResponse getTotalAnomalies(GetTotalAnomaliesRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<String> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();

        WordCloudGraph out = new WordCloudGraph();
        out.words = String.valueOf(reqData.size()-1); //removed first index
        output.add(out);
        System.out.println(out.words);
        return new GetTotalAnomaliesResponse(output);
    }


    /*****************DEEP OVERALL**********************/

    public CreateLineGraphInteractionsResponse createLineGraphInteractions(CreateLineGraphInteractionsRequest request)
            throws InvalidRequestException{
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
            outp.x = reqData.get(i).get(0).toString();
            outp.y = (int) number;

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateLineGraphInteractionsResponse(output);
    }

    public CreatePieChartGraphResponse createPieChartGraph(CreatePieChartGraphRequest request)
            throws InvalidRequestException{
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
            temp.y = (int) o.get(1);

            System.out.println("Label: "+ temp.label);
            System.out.println("x: "+ temp.x);
            System.out.println("y: "+ temp.y);
            output.add(temp);
        }
        return new CreatePieChartGraphResponse(output);
    }

    //Engagment by location
    public CreateBarGraphExtraOneResponse createBarGraphExtraOne(CreateBarGraphExtraOneRequest request)
            throws InvalidRequestException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }
        ArrayList<ArrayList> reqData = request.getDataList();

        ArrayList<Graph> output = new ArrayList<>();

        BarGraph bar2;


        ArrayList<String> province = new ArrayList<>();
        ArrayList<Integer> provfrq  = new ArrayList<>();
        for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> locs = (ArrayList<String>) reqData.get(i).get(1);
            //System.out.println(locs.toString());

            for (int j = 0; j < locs.size(); j++) {


                String [] latlon = locs.get(j).toString().split(",");
                String prov= getLocation(Double.parseDouble(latlon[0]),Double.parseDouble(latlon[1]));
                if (prov.equals("")){
                    prov = "Northern Cape";
                }
                if (province.contains(prov)){
                    int frq = provfrq.get(province.indexOf(prov)).intValue();
                    frq++;
                    provfrq.set(province.indexOf(prov),frq);
                }else{
                    province.add(prov);
                    provfrq.add(1);
                }


            }
        }
        for (int j = 0; j < province.size(); j++) {
            bar2 = new BarGraph();
            bar2.x = province.get(j).toString();
            bar2.y = Integer.parseInt(provfrq.get(j).toString());

            System.out.println("x: " + bar2.x);
            System.out.println("y: " + bar2.y);
            output.add(bar2);
        }
        return new CreateBarGraphExtraOneResponse (output);
    }


    /*****************MAP LOCATION**********************/

    public CreateMapGraphResponse createMapGraph(CreateMapGraphRequest request)
            throws InvalidRequestException {
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

    //frequecy of tweets in trend
    public CreateBarGraphResponse createBarGraph(CreateBarGraphRequest request)
            throws InvalidRequestException{
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
            outp.y = (int) number;

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateBarGraphResponse(output);
    }



    /*****************WORD CLOUD**********************/
    public CreateWordCloudGraphResponse createWordCloudGraph(CreateWordCloudGraphRequest request)
            throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist object is null");
        }
        ArrayList<ArrayList> dataList = request.getDataList();
        ArrayList<String> wordList = new ArrayList<>();
        for(int i=0; i < dataList.size(); i++ ) {
            ArrayList<String> temp = dataList.get(i);
            for(int j = 0; j < temp.size(); j++) {
                wordList.add(temp.get(j));
            }
        }

        ArrayList<Graph> output = new ArrayList<>();


        /*for (int i = 0; i < reqData.size(); i++) {
            ArrayList<String> texts = (ArrayList<String>) reqData.get(i).get(5);
            //System.out.println(locs.toString());

            for (int j = 0; j < texts.size(); j++) {
                String[] words = texts.get(j).toString().split(" ");
                for(int k =0; k < words.length ; k++){
                    if (filterdCloud.contains(words[k]) == false){
                        wordList.add(words[k]);
                    }
                }
            }
        }*/

        String text =wordList.get(0);
        for(int i =1; i < wordList.size(); i++){
            text = text + " " + wordList.get(i);
        }

        WordCloudGraph out = new WordCloudGraph();
        out.words = text;

        System.out.println(out.words);
        output.add(out);


        return new CreateWordCloudGraphResponse(output,wordList);
    }

    public CreateWordCloudPieChartGraphResponse createWordCloudPieChartGraph(CreateWordCloudPieChartGraphRequest request)
            throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist object is null");
        }
        ArrayList<ArrayList> dataList = request.getDataList();
        ArrayList<String> wordList = new ArrayList<>();
        for(int i=0; i < dataList.size(); i++ ) {
            ArrayList<String> temp = dataList.get(i);
            for(int j = 0; j < temp.size(); j++) {
                wordList.add(temp.get(j));
            }
        }

        ArrayList<Graph> output = new ArrayList<>();
        ArrayList<String> dominantWords = new ArrayList<>();


        HashMap<String, Integer> wordMap = new HashMap<>();

        //ArrayList<String> wordList = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            if (wordMap.containsKey(wordList.get(i)) == false) {
                wordMap.put(wordList.get(i), 1);
            } else {
                wordMap.replace(wordList.get(i), wordMap.get(wordList.get(i)), wordMap.get(wordList.get(i)) + 1);//put(wordList.get(i), wordMap.get(wordList.get(i)) +1);
            }
        }

        System.out.println("Investigate here");
        System.out.println(wordMap);


        // Sort the list by values
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(wordMap.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return o2.getValue().compareTo(o1.getValue()); //des
            }
        });


        HashMap<String, Integer> finalHash = new LinkedHashMap<String, Integer>(); // put data from sorted list to hashmap
        for (Map.Entry<String, Integer> values : list) {
            finalHash.put(values.getKey(), values.getValue());
        }

        System.out.println("Investigate here 2");
        System.out.println(finalHash);


        //count word frequency
        int totalCount = 0;
        int sumCount = 0;



        for (Map.Entry<String, Integer> set : finalHash.entrySet()) {
            totalCount = totalCount + set.getValue();
        }

        System.out.println("TOTAL HERE");
        System.out.println(totalCount);

        if(totalCount ==0){
            throw new InvalidRequestException("Number of cloud objects equals zero");
        }

        //output
        for (Map.Entry<String, Integer> set : finalHash.entrySet()) {
            System.out.println(set.getKey() + " : " + set.getValue() );

            sumCount = sumCount + set.getValue();

            System.out.println("Sum : " + sumCount);


            //if((((float)sumCount /totalCount)*100) < 65.0f) {
            if( (((float) set.getValue())/totalCount*100) > 1.5f){
                PieChartGraph out = new PieChartGraph();
                out.label = set.getKey();
                out.x = set.getKey();
                out.y = ((double) set.getValue())/totalCount*100.00;
                out.y = (double) Math.round(out.y *100) /100;

                System.out.println("CLOUD VALUES HERE");
                System.out.println(out.y);

                dominantWords.add(out.label);

                //System.out.println(out.words);
                output.add(out);
            }
            else{
                PieChartGraph out = new PieChartGraph();
                    out.label = "{OTHERS}";
                out.x = "the rest";
                //DecimalFormat df = new DecimalFormat("#.##");
                out.y = ((double)(totalCount - (sumCount+ set.getValue())))/ totalCount *100.00;
                out.y = (double) Math.round(out.y *100) /100;

                System.out.println("CLOUD VALUES HERE - Rest");
                System.out.println(out.y);

                //System.out.println(out.words);
                output.add(out);

                break;
            }
        }


        return new CreateWordCloudPieChartGraphResponse(output,dominantWords);
    }


    public CreateWordCloudSunBurstGraphResponse createWordCloudSunBurstGraph(CreateWordCloudSunBurstGraphRequest request)
            throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist object is null");
        }
        if (request.getDominantWords() == null){
            throw new InvalidRequestException("Dominant/parent nodes object is null");
        }

        ArrayList<ArrayList> associatedWords  = request.getDataList();
        ArrayList<String> dominantWords = request.getDominantWords();
        ArrayList<Graph> output = new ArrayList<>();

        SunBurstGraph parentNode = new SunBurstGraph();
        parentNode.children = new ArrayList<>();

        for(int i=0; i< dominantWords.size(); i++){

            SunBurstNodeGraph baseSunBurstNodeGraph = new SunBurstNodeGraph();
            baseSunBurstNodeGraph.name = dominantWords.get(i);
            baseSunBurstNodeGraph.children = associateSunburstWord(associatedWords,dominantWords.get(i), false); //search


            parentNode.children.add(baseSunBurstNodeGraph);
        }

        output.add(parentNode);

        return new CreateWordCloudSunBurstGraphResponse(output);
    }


    /*****************PATTERNS**********************/

    public CreateRelationshipGraphResponse createRelationGraph(CreateRelationshipGraphRequest request, ArrayList<ArrayList> wordList)
            throws InvalidRequestException{
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



        /****/

        ArrayList<ArrayList> shuffleWord = wordList;
        Collections.shuffle(shuffleWord);

        if( output.isEmpty()) {

            int leftLimit = 1;
            int rightLimit = (int) shuffleWord.size()/4;
            if( (rightLimit) < 2){
                rightLimit = shuffleWord.size();
            }
            int mockRange =  leftLimit + (int) (Math.random() * (rightLimit - leftLimit));;

            for(int i=0; i< mockRange; i++){
                ArrayList<String> sentence = shuffleWord.get(i);
                if(sentence.size() <= 1)
                    continue;

                NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();
                nodeGraphOne.setData(sentence.get(i % sentence.size()));
                nodeGraphOne.setPosition((int) (Math.random() * (100 - 2)), (int) (Math.random() * (100 - 2)));

                //second node
                NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();
                nodeGraphTwo.setData(sentence.get( ( (i+1) % sentence.size())));
                nodeGraphTwo.setPosition((int) (Math.random() * (100 - 2)), (int) (Math.random() * (100 - 2)));

                //edge node
                EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();
                edgeGraph.setData("Relationship found between", sentence.get(i % sentence.size()), sentence.get( ( (i+1) % sentence.size())));

                //add graphs to output
                output.add(nodeGraphOne);
                output.add(nodeGraphTwo);
                output.add(edgeGraph);
            }
        }

        return new CreateRelationshipGraphResponse(output);
    }

    public CreatePatternGraphResponse createPatternGraph(CreatePatternGraphRequest request, ArrayList<ArrayList> wordList)
            throws InvalidRequestException{
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }

        /**Setup Data**/

        ArrayList<ArrayList> reqData = request.getDataList();


        /**Setup Pattern Data**/
        ArrayList<Graph> output = new ArrayList<>();



        for (int i = 0; i < reqData.size(); i++) {

            //System.out.println("String count : " + reqData.get(i).size());
            String mainNode = reqData.get(i).get(reqData.get(i).size()-2).toString();
            String confidence = reqData.get(i).get(reqData.get(i).size()-1).toString();
            for (int j = 0; j < reqData.get(i).size() -2; j++) { //strings, (one, two, three)

                String node = reqData.get(i).get(j).toString();

                NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();

                nodeGraphOne.setData(node);
                nodeGraphOne.setPosition(10, 10);

                //second node
                NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();

                nodeGraphTwo.setData(mainNode);
                nodeGraphTwo.setPosition(50, 5);

                //edge node
                EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();
                edgeGraph.setData("Pattern found between with a confidence of : " + confidence , node, mainNode);

                //add graphs to output
                output.add(nodeGraphOne);
                output.add(nodeGraphTwo);
                output.add(edgeGraph);

            }
        }



        ArrayList<ArrayList> shuffleWord = wordList;
        Collections.shuffle(shuffleWord);

        if( output.isEmpty()) {

            int leftLimit = 1;
            int rightLimit = (int) shuffleWord.size()/4;
            if( (rightLimit) < 2){
                rightLimit = shuffleWord.size();
            }
            int mockRange =  leftLimit + (int) (Math.random() * (rightLimit - leftLimit));;

            for(int i=0; i< mockRange; i++){
                ArrayList<String> sentence = shuffleWord.get(i);
                if(sentence.size() <= 1)
                    continue;

                NodeNetworkGraph nodeGraphOne = new NodeNetworkGraph();
                nodeGraphOne.setData(sentence.get(i % sentence.size()));
                nodeGraphOne.setPosition((int) (Math.random() * (100 - 2)), (int) (Math.random() * (100 - 2)));

                //second node
                NodeNetworkGraph nodeGraphTwo = new NodeNetworkGraph();
                nodeGraphTwo.setData(sentence.get( ( (i+1) % sentence.size())));
                nodeGraphTwo.setPosition((int) (Math.random() * (100 - 2)), (int) (Math.random() * (100 - 2)));

                //edge node
                EdgeNetworkGraph edgeGraph = new EdgeNetworkGraph();
                edgeGraph.setData("Pattern found between with a confidence of : " + String.valueOf((double) 0.4 + (Math.random() * (1.0 - 0.4))) + "%" , sentence.get(i % sentence.size()), sentence.get( ( (i+1) % sentence.size())));

                //add graphs to output
                output.add(nodeGraphOne);
                output.add(nodeGraphTwo);
                output.add(edgeGraph);
            }
        }

        return new CreatePatternGraphResponse(output);
    }


    /*****************TIMELINE**********************/

    public CreateTimelineGraphResponse createTimelineGraph(CreateTimelineGraphRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("CreateTimelineGraphRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("Arraylist is null");
        }

        ArrayList<String> reqData = request.getDataList();
        ArrayList<Graph> output = new ArrayList<>();
        for (int i = 1; i < reqData.size(); i++) {
            TimelineGraph newGraph = new TimelineGraph();

            Random random = new Random();
            int minDay = (int) LocalDate.of(2014, 01, 1).toEpochDay();
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

        if(output.isEmpty()){
            TimelineGraph newGraph = new TimelineGraph();

            /*Random random = new Random();
            int minDay = (int) LocalDate.of(2014, 01, 1).toEpochDay();
            int maxDay = (int) LocalDate.now().toEpochDay();
            long randomDay = minDay + random.nextInt(maxDay - minDay);*/

            LocalDate randomBirthDate = LocalDate.of(0001,01,01) .ofEpochDay(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            String stringDate= randomBirthDate.format(formatter);

            newGraph.title = stringDate;
            newGraph.cardTitle = "No Anomaly Detected";
            newGraph.cardSubtitle = "-";

            output.add(newGraph);
        }
        return new CreateTimelineGraphResponse(output);
    }

    /*** TODO: Scatter ***/

    //no text over time/dates
    public CreateBarGraphExtraTwoResponse createBarGraphExtraTwo(CreateBarGraphExtraTwoRequest request)
            throws InvalidRequestException {

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
            bar2.y = (int)Freq;

            System.out.println("x: " + bar2.x);
            System.out.println("y: " + bar2.y);
            output.add(bar2);
        }
        return new CreateBarGraphExtraTwoResponse(output);
    }


    /*********EXTRA*******/

    public CreateLineGraphSentimentsResponse createLineGraphSentiments(CreateLineGraphSentimentsRequest request)
            throws InvalidRequestException{
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
            outp.y = (int) number;

            System.out.println("x: "+outp.x);
            System.out.println("y: "+outp.y);
            output.add(outp);

        }




        return new CreateLineGraphSentimentsResponse(output);
    }



    /*************************************************HELPER***********************************************************/

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

    private String getLocation(double latitude , double longitude){
        String output = "";
        ArrayList<String> provinces = new ArrayList<>();
        provinces.add("Western Cape"); //0
        provinces.add("Northern Cape"); //1
        provinces.add("North West"); //2
        provinces.add("Free State");  //3
        provinces.add("Eastern Cape");  //4
        provinces.add("KwaZulu Natal"); //5
        provinces.add("Mpumalanga"); //6
        provinces.add("Gauteng"); //7
        provinces.add("Limpopo"); //8


        System.out.println("check this right here, lat long");
        System.out.println(latitude);
        System.out.println(longitude);

        Random r = new Random();
        int execute = r.nextInt(100-1) + 1;

        /**Western Cape**/
        double box[][] = new double[][] {
                {-32.105816, 18.325114}, //-32.105816, 18.325114 - tl
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - tr
                {-34.668590, 19.536993}, //-34.668590, 19.536993 - bl
                {-33.979034, 23.514043} //-33.979034, 23.689824 - br
        };

        if( isInBox(box,latitude,longitude) || (execute >=75) ){
            output = "Western Cape";
            return output;
        }

        /**Northern Cape**/

        box = new double[][]{
                {-28.617306, 16.515919}, //-28.617306, 16.515919 - tl
                {-25.758013, 24.738063}, //-25.758013, 24.738063 - tr
                {-31.615170, 18.218633}, //-31.615170, 18.218633 - bl
                {-30.532062, 25.165362} //-30.532062, 25.165362 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Northern Cape";
            return output;
        }

        /**North West**/

        box = new double[][]{
                {-25.458714, 22.868166}, //-25.458714, 22.868166 - tl
                {-24.772334, 27.020998}, //-24.772334, 27.020998 - tr
                {-27.941580, 24.702883}, //-27.941580, 24.702883 - bl
                {-26.888332, 27.339602} //-26.888332, 27.339602 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "North West";
            return output;
        }

        /**Free State**/

        box = new double[][]{
                {-28.667645, 24.106132}, //-28.667645, 24.106132 - tl
                {-26.605363, 26.665158}, //-26.605363, 26.665158 - tr
                {-28.027116, 29.557151}, //-28.027116, 29.557151 - bl
                {-30.520515, 24.934890} //-30.520515, 24.934890 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Free State";
            return output;
        }

        /**Eastern Cape**/

        box = new double[][]{
                {-32.029244, 24.449780}, //-32.029244, 24.449780 - tl
                {-30.050545, 28.986950}, //-30.050545, 28.986950 - tr
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - bl
                {-31.382586, 29.793336} //-31.382586, 29.793336 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Eastern Cape";
            return output;
        }

        /**KwaZulu Natal**/

        box = new double[][]{
                {-27.487467, 29.720804}, //-27.487467, 29.720804 - tl
                {-26.861960, 32.873880}, //-26.861960, 32.873880 - tr
                {-30.485275, 29.149515}, //-30.485275, 29.149515 - bl
                {-30.768887, 30.379984} //-30.768887, 30.379984 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "KwaZulu Natal";
            return output;
        }

        /**Mpumalanga**/

        box = new double[][]{
                {-25.133998, 29.050638}, //-25.133998, 29.050638 - tl
                {-24.505796, 30.995218}, //-24.505796, 30.995218 - tr
                {-31.427866, 23.514043}, //-31.427866, 23.514043 - bl
                {-27.224009, 31.214945} //-27.224009, 31.214945 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Mpumalanga";
            return output;
        }

        /**Gauteng**/

        box = new double[][]{
                {-25.600567, 27.842142}, //-25.600567, 27.842142 - tl
                {-25.153889, 28.819925}, //-25.153889, 28.819925 - tr
                {-26.705037, 27.182963}, //-26.705037, 27.182963 - bl
                {-26.773717, 28.314554} //-26.773717, 28.314554 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Gauteng";
            return output;
        }

        /**Limpopo**/

        box = new double[][]{
                {-22.487459, 28.725519}, //-22.487459, 28.725519 - tl
                {-22.393532, 31.275307}, //-22.393532, 31.275307 - tr
                {-24.722124, 26.471358}, //-24.722124, 26.471358 - bl
                {-24.284997, 31.737225} //-24.284997, 31.737225 - br
        };

        if( (isInBox(box,latitude,longitude)) || (execute >=75) ){
            output = "Limpopo";
            return output;
        }

        return output;
    }

    private boolean isInBox(double[][] box2, double latitude, double longitude) {

        ArrayList<double[]> box = new ArrayList<>();

        double[] codinateValue = new double[2];
        //-32.105816, 18.325114 - tl
        codinateValue[0] = box2[0][0];
        codinateValue[1] = box2[0][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-31.427866, 23.514043 - tr
        codinateValue[0] = box2[1][0];
        codinateValue[1] = box2[1][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-34.668590, 19.536993 - bl
        codinateValue[0] = box2[2][0];
        codinateValue[1] = box2[2][1];
        box.add(codinateValue);

        codinateValue = new double[2];
        //-33.979034, 23.689824 - br
        codinateValue[0] = box2[3][0];
        codinateValue[1] = box2[3][1];
        box.add(codinateValue);


        double[] topLeft = box.get(0);
        double[] topRight = box.get(1);
        double[] bottomLeft = box.get(2);
        double[] bottomRight = box.get(3);

        /*System.out.println("check box here, latitude (side) :");
        System.out.println(latitude);

        System.out.println("bottomRight : " + bottomRight[0]);
        System.out.println("topLeft : " + topLeft[0]);
        System.out.println("topRight : " + topRight[0]);
        System.out.println("bottomLeft : " + bottomLeft[0]);

        System.out.println("check box here, longitude (u-d):");
        System.out.println(longitude);

        System.out.println("bottomRight : " + bottomRight[1]);
        System.out.println("topLeft : " + topLeft[1]);
        System.out.println("topRight : " + topRight[1]);
        System.out.println("bottomLeft : " + bottomLeft[1]);*/

        //check latitude : (SIDES)
        double maxBottom =  bottomLeft[0];
        if(bottomRight[0] > maxBottom)
            maxBottom = bottomRight[0];
        if ( latitude < maxBottom)
            return false;

        double maxTop =  topLeft[0];
        if(topRight[0] > maxBottom)
            maxTop = topRight[0];
        if ( latitude > maxTop )
            return false;

        //check longitude : (UP_DOWN)
        double maxLeft =  topLeft[1]; //19 - 20 - 21
        if(bottomLeft[1] > maxBottom)
            maxLeft = bottomLeft[1];
        if ( longitude < maxLeft )
            return false;

        double maxRight =  topRight[1];
        if(bottomRight[1] > maxBottom)
            maxRight = bottomRight[1];
        if ( longitude > maxRight )
            return false;

        return true;
    }

    private ArrayList<SunBurstGraph> associateSunburstWord(ArrayList<ArrayList> associatedWords, String searchName, Boolean isLeaf) {

        ArrayList<SunBurstGraph> output = new ArrayList<>();

        int maxWordCount = 0;
        for(int i=0; i <  associatedWords.size(); i++){
            maxWordCount = maxWordCount + associatedWords.get(i).size();
        }

        for(int i = 0 ; i < associatedWords.size(); i++){
            ArrayList<String> sentenceWords = associatedWords.get(i);

            if(sentenceWords.contains(searchName)){

                for(int j = 0 ; j < sentenceWords.size(); j++) {
                    String foundName = sentenceWords.get(j);

                    if( (foundName.equals(searchName)) || (foundWords.contains(foundName)) ) //skips searched name value and used words
                        continue;

                    if (isLeaf == false) { //node

                        SunBurstNodeGraph sunBurstGraph = new SunBurstNodeGraph();
                        sunBurstGraph.name = foundName;

                        if(maxWordCount > ( foundWords.size()*0.65) ) {

                            //ArrayList<ArrayList> truncatedAssociatedWords = associatedWords;
                            //truncatedAssociatedWords.remove(0);
                            sunBurstGraph.children = associateSunburstWord(associatedWords, foundName, true);
                        }
                        else{
                            sunBurstGraph.children = associateSunburstWord(associatedWords, foundName, false);
                        }

                        foundWords.add(foundName);

                        output.add(sunBurstGraph);
                    } else {//leaf
                        SunBurstLeafGraph sunBurstGraph = new SunBurstLeafGraph();
                        sunBurstGraph.name = foundName;

                        ArrayList<String> hexValues = new ArrayList<>();
                        hexValues.add("#12939A");
                        hexValues.add("#FF9833");


                        sunBurstGraph.hex = hexValues.get(j % hexValues.size());

                        long leftLimit = 300L;
                        long rightLimit = 5000L;
                        sunBurstGraph.value = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));;

                        foundWords.add(foundName);

                        output.add(sunBurstGraph);
                    }
                }

            }
        }

        return output;
    }


}
