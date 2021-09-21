package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateLineGraphInteractionsRequest {
    public ArrayList<ArrayList> dataList;

    public CreateLineGraphInteractionsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
