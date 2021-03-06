package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateWordCloudSunBurstGraphRequest {
    private ArrayList<ArrayList> dataList;
    private ArrayList<String> dominantWords;


    public CreateWordCloudSunBurstGraphRequest(ArrayList<ArrayList> dataList, ArrayList<String> dominantWords){
        this.dataList = dataList;
        this.dominantWords = dominantWords;
    }

    public ArrayList<ArrayList> getDataList(){
        return this.dataList;
    }

    public ArrayList<String> getDominantWords(){
        return this.dominantWords;
    }
}
