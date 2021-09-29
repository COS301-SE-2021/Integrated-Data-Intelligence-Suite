package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetMostProminentSentimentRequest {
    public ArrayList<ArrayList> dataList;

    public GetMostProminentSentimentRequest(ArrayList<ArrayList> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
