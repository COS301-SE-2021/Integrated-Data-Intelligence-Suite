package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class GetTotalInteractionResponse {
    private ArrayList<Graph> words;

    public GetTotalInteractionResponse(ArrayList<Graph> words){
        this.words = words;
    }

    public ArrayList<Graph> getWordGraphArray(){
        return words;
    }
}
