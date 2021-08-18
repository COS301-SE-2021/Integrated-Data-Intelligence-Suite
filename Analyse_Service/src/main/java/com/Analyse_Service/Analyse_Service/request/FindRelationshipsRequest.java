package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindRelationshipsRequest {
    ArrayList<ArrayList> dataList;

    public FindRelationshipsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
