package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class GetTotalInteractionRequest {
    public ArrayList<ArrayList> dataList;

    public GetTotalInteractionRequest(ArrayList<ArrayList> wordBank){
        this.dataList = wordBank;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
