package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class VisualizeAnalyseDataResponse {
    public ArrayList<ArrayList> outputData;

    public VisualizeAnalyseDataResponse(ArrayList<ArrayList> outputData){
        this.outputData = outputData;
    }

    public ArrayList<ArrayList> getOutputData(){
        return outputData;
    }

}
