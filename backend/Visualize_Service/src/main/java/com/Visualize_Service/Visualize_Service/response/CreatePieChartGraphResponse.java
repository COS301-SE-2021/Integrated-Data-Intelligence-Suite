package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreatePieChartGraphResponse {
    public ArrayList<Graph> PieChartGraphArray;
    public CreatePieChartGraphResponse(ArrayList<Graph> PieChartGraphArray){
        this.PieChartGraphArray = PieChartGraphArray;
    }

    public ArrayList<Graph> getPieChartGraphArray(){
        return PieChartGraphArray;
    }
}
