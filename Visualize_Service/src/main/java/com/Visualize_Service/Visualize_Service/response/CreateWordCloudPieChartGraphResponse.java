package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudPieChartGraphResponse {
    public ArrayList<Graph> wordCloudPieChartGraphArray;
    public CreateWordCloudPieChartGraphResponse(ArrayList<Graph> wordCloudPieChartGraphArray){
        this.wordCloudPieChartGraphArray = wordCloudPieChartGraphArray;
    }

    public ArrayList<Graph> getWordCloudPieChartGraphArray(){
        return wordCloudPieChartGraphArray;
    }
}
