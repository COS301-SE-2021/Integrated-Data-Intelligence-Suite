package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreatePatternGraphRequest {
    public ArrayList<ArrayList> dataList;

    public CreatePatternGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
