package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindRelationshipsRequest {
    ArrayList<ArrayList> dataList;

    public TrainFindRelationshipsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
