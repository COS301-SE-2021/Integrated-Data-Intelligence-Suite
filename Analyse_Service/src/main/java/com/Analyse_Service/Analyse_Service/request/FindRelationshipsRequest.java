package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindRelationshipsRequest {
    ArrayList<String> dataList;

    public FindRelationshipsRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
