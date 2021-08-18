package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateNetworkGraphResponse {
    public ArrayList<Graph> NetworkGraphArray;
    public CreateNetworkGraphResponse(ArrayList<Graph> NetworkGraphArray){
        this.NetworkGraphArray = NetworkGraphArray;
    }

    public ArrayList<Graph> getNetworkGraphArray(){
        return NetworkGraphArray;
    }
}
