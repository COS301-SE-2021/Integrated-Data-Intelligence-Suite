package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetMostProminentLocationRequest {
    public ArrayList<String> dataList;

    public GetMostProminentLocationRequest(ArrayList<String> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
