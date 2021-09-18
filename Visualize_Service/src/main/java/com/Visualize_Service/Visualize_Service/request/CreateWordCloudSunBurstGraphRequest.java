package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateWordCloudSunBurstGraphRequest {
    public ArrayList<String> dataList;

    public CreateWordCloudSunBurstGraphRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
