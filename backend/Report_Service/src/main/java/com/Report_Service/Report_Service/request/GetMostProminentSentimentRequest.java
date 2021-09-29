package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetMostProminentSentimentRequest {
    public ArrayList<String> dataList;

    public GetMostProminentSentimentRequest(ArrayList<String> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
