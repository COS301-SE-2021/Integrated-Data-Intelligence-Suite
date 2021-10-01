package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetAnomalyDataRequest {
    public ArrayList<String> dataList;

    public GetAnomalyDataRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
