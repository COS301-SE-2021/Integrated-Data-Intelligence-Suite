package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class GetTotalAnomaliesResponse {
    private ArrayList<Graph> words;

    public GetTotalAnomaliesResponse(ArrayList<Graph> words){
        this.words = words;
    }

    public ArrayList<Graph> getWordGraphArray(){
        return words;
    }
}
