package com.Report_Service.Report_Service.response;

import java.util.ArrayList;

public class GetPatternAndRelationshipDataResponse {
    public ArrayList<ArrayList> dataList;
    String summary;

    public GetPatternAndRelationshipDataResponse(ArrayList<ArrayList> dataList, String summary){
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
