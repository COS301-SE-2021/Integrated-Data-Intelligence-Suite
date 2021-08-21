package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindPatternRequest {
    ArrayList<ArrayList> dataList;

    public FindPatternRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
