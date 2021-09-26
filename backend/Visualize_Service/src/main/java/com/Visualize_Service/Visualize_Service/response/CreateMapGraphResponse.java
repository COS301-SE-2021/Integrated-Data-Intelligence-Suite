package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateMapGraphResponse {
    public ArrayList<Graph> mapGraphArray;
    public CreateMapGraphResponse(ArrayList<Graph> mapGraphArray){
        this.mapGraphArray = mapGraphArray;
    }

    public ArrayList<Graph> getLineGraphArray(){
        return mapGraphArray;
    }
}
