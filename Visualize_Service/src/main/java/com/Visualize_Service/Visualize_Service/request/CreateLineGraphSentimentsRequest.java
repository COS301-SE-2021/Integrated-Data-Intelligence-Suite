package com.Visualize_Service.Visualize_Service.request;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateLineGraphSentimentsRequest {
    public ArrayList<ArrayList> dataList;

    public CreateLineGraphSentimentsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
