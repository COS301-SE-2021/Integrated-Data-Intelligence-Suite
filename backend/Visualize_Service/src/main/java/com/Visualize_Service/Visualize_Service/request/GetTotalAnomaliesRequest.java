package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class GetTotalAnomaliesRequest {
    private ArrayList<String> dataList;

    public GetTotalAnomaliesRequest(ArrayList<String> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
