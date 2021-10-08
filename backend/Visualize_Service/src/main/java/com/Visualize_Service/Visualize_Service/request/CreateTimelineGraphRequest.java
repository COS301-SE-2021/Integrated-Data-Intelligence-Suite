package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateTimelineGraphRequest {
    private ArrayList<String> dataList;

    public CreateTimelineGraphRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
