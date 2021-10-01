package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateLineGraphSentimentsRequest {
    private ArrayList<ArrayList> dataList;

    public CreateLineGraphSentimentsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
