package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetTextualAnalysisDataRequest {
    public ArrayList<ArrayList> dataList;

    public GetTextualAnalysisDataRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
