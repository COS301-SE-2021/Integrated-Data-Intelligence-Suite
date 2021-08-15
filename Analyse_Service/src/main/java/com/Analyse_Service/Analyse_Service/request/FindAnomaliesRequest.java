package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindAnomaliesRequest {
    ArrayList<ArrayList> dataList;

    public FindAnomaliesRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
