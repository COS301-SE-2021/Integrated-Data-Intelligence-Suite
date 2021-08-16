package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateTimelineGraphResponse {
    public ArrayList<Graph> timelineGraphArray;
    public CreateTimelineGraphResponse(ArrayList<Graph> mapGraphArray){
        this.timelineGraphArray = mapGraphArray;
    }

    public ArrayList<Graph> getLineGraphArray(){
        return timelineGraphArray;
    }
}
