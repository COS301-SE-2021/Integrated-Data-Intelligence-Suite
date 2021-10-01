package com.Report_Service.Report_Service.request;

import java.util.ArrayList;

public class GetPatternAndRelationshipDataRequest {
    public ArrayList<ArrayList> dataListP;
    public ArrayList<ArrayList> dataListR;

    public GetPatternAndRelationshipDataRequest(ArrayList<ArrayList> dataListP,ArrayList<ArrayList> dataListR){
        this.dataListP = dataListP;
        this.dataListR = dataListR;
    }

    public ArrayList<ArrayList> getDataListP(){
        return dataListP;
    }
    public ArrayList<ArrayList> getDataListR(){
        return dataListR;
    }
}
