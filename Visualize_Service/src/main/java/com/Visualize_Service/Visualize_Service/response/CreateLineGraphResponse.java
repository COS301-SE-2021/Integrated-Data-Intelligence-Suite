package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateLineGraphResponse {
    public ArrayList<Graph> LineGraphArray;
    public CreateLineGraphResponse(ArrayList<Graph> LineGraphArray){
        this.LineGraphArray = LineGraphArray;
    }

    public ArrayList<Graph> getLineGraphArray(){
        return LineGraphArray;
    }
}
