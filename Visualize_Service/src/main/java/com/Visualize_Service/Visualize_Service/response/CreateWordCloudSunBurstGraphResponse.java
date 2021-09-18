package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudSunBurstGraphResponse {
    public ArrayList<Graph> wordCloudSunBurstGraphArray;

    public CreateWordCloudSunBurstGraphResponse(ArrayList<Graph> wordCloudSunBurstGraphArray){
        this.wordCloudSunBurstGraphArray = wordCloudSunBurstGraphArray;
    }

    public ArrayList<Graph> getWordCloudSunBurstGraphArray(){
        return wordCloudSunBurstGraphArray;
    }
}
