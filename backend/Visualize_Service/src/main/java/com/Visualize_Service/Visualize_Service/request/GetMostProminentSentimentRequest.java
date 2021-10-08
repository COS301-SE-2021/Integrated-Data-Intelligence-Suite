package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class GetMostProminentSentimentRequest {
    private ArrayList<ArrayList> dataList;

    public GetMostProminentSentimentRequest(ArrayList<ArrayList> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
