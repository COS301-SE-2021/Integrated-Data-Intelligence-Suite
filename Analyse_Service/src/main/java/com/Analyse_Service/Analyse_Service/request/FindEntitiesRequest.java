package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindEntitiesRequest {
    ArrayList<String> dataList;

    public FindEntitiesRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }

}
