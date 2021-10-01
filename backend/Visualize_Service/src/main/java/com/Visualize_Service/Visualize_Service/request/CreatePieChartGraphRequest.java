package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreatePieChartGraphRequest {
    private ArrayList<ArrayList> dataList;

    public CreatePieChartGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
