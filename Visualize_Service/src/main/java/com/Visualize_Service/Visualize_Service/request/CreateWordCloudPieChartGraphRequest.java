package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateWordCloudPieChartGraphRequest {
    public ArrayList<String> dataList;

    public CreateWordCloudPieChartGraphRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
