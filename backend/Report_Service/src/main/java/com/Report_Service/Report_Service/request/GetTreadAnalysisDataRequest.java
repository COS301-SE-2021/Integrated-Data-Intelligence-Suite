package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetTreadAnalysisDataRequest {
    public ArrayList<ArrayList> dataList;

    public GetTreadAnalysisDataRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
