package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateMapGraphRequest {
    public ArrayList<ArrayList> dataList;

    public CreateMapGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
