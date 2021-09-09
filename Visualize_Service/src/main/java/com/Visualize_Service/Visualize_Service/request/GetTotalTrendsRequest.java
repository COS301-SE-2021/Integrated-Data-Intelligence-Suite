package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class GetTotalTrendsRequest {
    public ArrayList<ArrayList> dataList;

    public GetTotalTrendsRequest(ArrayList<ArrayList> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
