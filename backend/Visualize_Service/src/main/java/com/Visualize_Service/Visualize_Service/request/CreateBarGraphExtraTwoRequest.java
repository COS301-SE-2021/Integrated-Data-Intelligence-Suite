package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateBarGraphExtraTwoRequest {
    private ArrayList<ArrayList> dataList;

    public CreateBarGraphExtraTwoRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
