package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetTrendAnalysisDataRequest {
    public ArrayList<ArrayList> dataList;

    public GetTrendAnalysisDataRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
