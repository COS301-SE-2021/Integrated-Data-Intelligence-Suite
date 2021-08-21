package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateNetworkGraphRequest {
    public ArrayList<ArrayList> dataList;

    public CreateNetworkGraphRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
