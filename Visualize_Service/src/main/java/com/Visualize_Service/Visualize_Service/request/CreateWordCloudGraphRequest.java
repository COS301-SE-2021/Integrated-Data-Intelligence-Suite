package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateWordCloudGraphRequest {
    public ArrayList<ArrayList> dataList;

    public CreateWordCloudGraphRequest(ArrayList<ArrayList> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
