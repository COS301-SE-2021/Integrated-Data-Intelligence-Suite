package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreatePatternGraphResponse {
    private ArrayList<Graph> NetworkGraphArray;

    public CreatePatternGraphResponse(ArrayList<Graph> NetworkGraphArray){
        this.NetworkGraphArray = NetworkGraphArray;
    }

    public ArrayList<Graph> getNetworkGraphArray(){
        return NetworkGraphArray;
    }
}
