package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindPatternRequest {
    ArrayList<String> dataList;

    public FindPatternRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
