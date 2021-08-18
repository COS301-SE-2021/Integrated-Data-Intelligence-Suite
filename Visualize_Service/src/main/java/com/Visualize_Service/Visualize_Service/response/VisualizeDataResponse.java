package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class VisualizeDataResponse {
    public ArrayList<ArrayList> outputData;

    public VisualizeDataResponse(ArrayList<ArrayList> outputData){
        this.outputData = outputData;
    }

    public ArrayList<ArrayList> getOutputData(){
        return outputData;
    }

}