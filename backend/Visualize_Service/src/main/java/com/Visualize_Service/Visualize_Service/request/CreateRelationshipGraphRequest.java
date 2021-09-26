package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateRelationshipGraphRequest {
    public ArrayList<ArrayList> dataList;

    public CreateRelationshipGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
