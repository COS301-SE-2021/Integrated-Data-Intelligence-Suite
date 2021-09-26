package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateBarGraphExtraOneRequest {
    public ArrayList<ArrayList> dataList;

    public CreateBarGraphExtraOneRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
