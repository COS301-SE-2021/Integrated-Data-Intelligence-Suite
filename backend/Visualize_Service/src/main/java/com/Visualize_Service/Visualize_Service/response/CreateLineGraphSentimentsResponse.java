package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateLineGraphSentimentsResponse {
    public ArrayList<Graph> LineGraphArray;
    public CreateLineGraphSentimentsResponse(ArrayList<Graph> LineGraphArray){
        this.LineGraphArray = LineGraphArray;
    }

    public ArrayList<Graph> getLineGraphArray(){
        return LineGraphArray;
    }
}
