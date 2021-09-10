package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class GetTotalTrendsResponse {
    public ArrayList<Graph> words;

    public GetTotalTrendsResponse(ArrayList<Graph> words){
        this.words = words;
    }

    public ArrayList<Graph> getWordGraphArray(){
        return words;
    }
}
