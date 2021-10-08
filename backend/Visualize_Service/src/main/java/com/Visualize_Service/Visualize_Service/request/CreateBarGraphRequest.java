package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateBarGraphRequest {
    private ArrayList<ArrayList> dataList;

    public CreateBarGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
