package com.Gateway_Service.Gateway_Service.controller;



import com.Gateway_Service.Gateway_Service.dataclass.*;


import com.Gateway_Service.Gateway_Service.service.AnalyseService;
import com.Gateway_Service.Gateway_Service.service.ImportService;
import com.Gateway_Service.Gateway_Service.service.ParseService;





//import com.netflix.discovery.DiscoveryClient;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
@RequestMapping("/")
public class GatewayServiceController {
    @Autowired
    private ImportService importClient;

    @Autowired
    private ParseService parseClient;

    @Autowired
    private AnalyseService analyseClient;


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    private String getServiceURL(String serviceName){
        return this.discoveryClient.getInstances(serviceName).get(0).getUri().toString();
    }

    public static class Graph{
        Graph(){}
    }

    public static class LineGraph extends Graph{
        public String name;
        public ArrayList<String> marker = new ArrayList<>();
        public ArrayList<String> data  = new ArrayList<>();
    }

    public static class NetworkGraph extends Graph{
        public String From;
        public ArrayList<String> to = new ArrayList<>();
    }



    public static class mapGraph extends Graph{
        ArrayList<ArrayList> map = new ArrayList<>();

    }

    public static class ErrorGraph extends Graph{
        public String Error;
    }


    //TEST FUNCTION
    @GetMapping(value ="/{key}", produces = "application/json")
    public String testNothing(@PathVariable String key) {
        String output = "";

        ImportTwitterRequest importReq = new ImportTwitterRequest(key,10);
        ImportTwitterResponse importRes = importClient.getTwitterDataJson(importReq);

        if(importRes.getFallback() == true)
            output = importRes.getFallbackMessage();
        else
            output = importRes.getJsonData();

        return output;
    }

    /*@GetMapping(value ="test/{line}", produces = "application/json")
    public String testNothing2(@PathVariable String line) {

        String output = "";
        AnalyseDataResponse analyseResponse = analyseClient.findSentiment(line);

        if(analyseResponse.getFallback() == true)
            output = analyseResponse.getFallbackMessage();
        else {
            output = analyseResponse.getSentiment().getCssClass();
        }

        return output;
    }*/


    @GetMapping(value = "/man/{key}", produces = "application/json")
    //@CrossOrigin
    //@HystrixCommand(fallbackMethod = "fallback")
    public ArrayList<ArrayList<Graph>> init(@PathVariable String key) throws Exception {
        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        //ArrayList <String> outputData = new ArrayList<>();
        HttpHeaders requestHeaders;

        /*********************IMPORT*************************/

        //String url = "http://Import-Service/Import/importData";
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("value",key);

        ImportDataRequest importRequest = new ImportDataRequest(key,10);
        ImportDataResponse importResponse = importClient.importData(importRequest);

        if(importResponse.getFallback() == true) {
            //outputData.add(importResponse.getFallbackMessage());
            //return new ArrayList<>();//outputData;

            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = importResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return outputData;
        }

        System.out.println("***********************IMPORT HAS BEEN DONE*************************");



        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData());//    DataSource.TWITTER,ImportResponse. getJsonData());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);


        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return outputData;
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(parseResponse.getDataList());//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseDataResponse analyseResponse = analyseClient.analyzeData(analyseRequest);


        if(analyseResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return outputData;
        }



        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        /*********************VISUALISE**********************/

        /*************LINE**********/
        LineGraph vpos = new LineGraph();
        vpos.name = "Very Positive";
        vpos.marker.add("square");

        LineGraph pos = new LineGraph();
        pos.name = "Positive";
        pos.marker.add("square");

        LineGraph net = new LineGraph();
        net.name = "Neutral";
        net.marker.add("square");


        LineGraph neg = new LineGraph();
        neg.name = "Negative";
        neg.marker.add("square");


        LineGraph vneg = new LineGraph();
        vneg.name = "Very Negative";
        vneg.marker.add("square");




        ArrayList<ArrayList> rela = analyseResponse.getRelationshipList();
        for(int i = 0; i < rela.size(); i++) {
            for (int j = 0;j< rela.get(i).size(); j++){
                if (rela.get(i).get(j).toString() == "Very_Negative"){
                    int index = rela.get(i).size()-1;
                    vneg.data.add(rela.get(i).get(index).toString());
                }
                if (rela.get(i).get(j).toString() == "Negative"){
                    int index = rela.get(i).size()-1;
                    neg.data.add(rela.get(i).get(index).toString());
                }
                if (rela.get(i).get(j).toString() == "Neutral"){
                    int index = rela.get(i).size()-1;
                    net.data.add(rela.get(i).get(index).toString());
                }
                if (rela.get(i).get(j).toString() == "Positive"){
                    int index = rela.get(i).size()-1;
                    pos.data.add(rela.get(i).get(index).toString());
                }
                if (rela.get(i).get(j).toString() == "Very_Positive"){
                    int index = rela.get(i).size()-1;
                    vpos.data.add(rela.get(i).get(index).toString());
                }
            }
        }

        ArrayList<Graph> LineGraphArray = new ArrayList<>();
        LineGraphArray.add(vpos);
        LineGraphArray.add(pos);
        LineGraphArray.add(net);
        LineGraphArray.add(neg);
        LineGraphArray.add(vneg);

        /*************NETWORK**********/

        NetworkGraph temp;
        ArrayList<ArrayList> pdata = analyseResponse.getPattenList();
        ArrayList<Graph> NetworkGraphArray = new ArrayList<>();
        for (int i = 0; i < pdata.size(); i++) {
            temp =  new NetworkGraph();
            temp.From = pdata.get(i).get(pdata.get(i).size()-3).toString();
            for (int j = 0; j < pdata.get(i).size()-2; j++) {
                temp.to.add(pdata.get(i).get(j).toString());
            }
            NetworkGraphArray.add(temp);
        }



        /************MAP**********/
        ArrayList<Graph> mapArray = new ArrayList<>();
        ArrayList<String> coordinates;
        mapGraph mapG = new mapGraph();

        coordinates = new ArrayList<>();
        coordinates.add("za-ec");
        coordinates.add("100");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-np");
        coordinates.add("102");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-nl");
        coordinates.add("120");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-wc");
        coordinates.add("300");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-nc");
        coordinates.add("106");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-nw");
        coordinates.add("90");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-fs");
        coordinates.add("130");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-gt");
        coordinates.add("130");
        mapG.map.add(coordinates);

        coordinates = new ArrayList<>();
        coordinates.add("za-mp");
        coordinates.add("134");
        mapG.map.add(coordinates);

        mapArray.add(mapG);





        return  outputData;

    }


}

