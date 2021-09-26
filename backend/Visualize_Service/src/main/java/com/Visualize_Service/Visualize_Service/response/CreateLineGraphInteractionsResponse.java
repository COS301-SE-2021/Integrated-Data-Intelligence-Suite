package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateLineGraphInteractionsResponse {
    public ArrayList<Graph> LineGraphArray;
    public CreateLineGraphInteractionsResponse(ArrayList<Graph> LineGraphArray){
        this.LineGraphArray = LineGraphArray;
    }

    public ArrayList<Graph> getLineGraphArray(){
        return LineGraphArray;
    }
}
