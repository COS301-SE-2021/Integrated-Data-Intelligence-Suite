package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudPieChartGraphResponse {
    private ArrayList<Graph> wordCloudPieChartGraphArray;
    private ArrayList<String> dominantWords;

    public CreateWordCloudPieChartGraphResponse(ArrayList<Graph> wordCloudPieChartGraphArray,ArrayList<String> dominantWords){
        this.wordCloudPieChartGraphArray = wordCloudPieChartGraphArray;
        this.dominantWords = dominantWords;
    }

    public ArrayList<Graph> getWordCloudPieChartGraphArray(){
        return wordCloudPieChartGraphArray;
    }


    public ArrayList<String> getDominantWords(){
        return dominantWords;
    }
}
