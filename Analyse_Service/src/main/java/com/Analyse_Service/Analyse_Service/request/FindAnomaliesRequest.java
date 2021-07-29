package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindAnomaliesRequest {
    ArrayList<String> dataList;

    public FindAnomaliesRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
