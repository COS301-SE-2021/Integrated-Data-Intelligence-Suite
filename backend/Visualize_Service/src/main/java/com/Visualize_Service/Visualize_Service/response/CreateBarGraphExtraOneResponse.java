package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateBarGraphExtraOneResponse {
    public ArrayList<Graph> BarGraphArray;

    public CreateBarGraphExtraOneResponse(ArrayList<Graph> BarGraphArray){
        this.BarGraphArray = BarGraphArray;
    }

    public ArrayList<Graph> getBarGraphArray(){
        return BarGraphArray;
    }
}
