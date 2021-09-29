package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetAnomalyDataRequest {
    public ArrayList<ArrayList> dataList;

    public GetAnomalyDataRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
