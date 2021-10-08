package com.Report_Service.Report_Service.response;

import java.util.ArrayList;

public class GetTextualAnalysisDataResponse {
    public ArrayList<ArrayList> dataList;
    String summary;

    public GetTextualAnalysisDataResponse(ArrayList<ArrayList> dataList, String summary){
        this.summary = summary;
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
    public String getSummary(){
        return summary;
    }
}
