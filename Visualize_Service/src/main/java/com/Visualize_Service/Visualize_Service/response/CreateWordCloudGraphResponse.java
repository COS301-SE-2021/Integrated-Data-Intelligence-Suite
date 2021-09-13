package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudGraphResponse {
    public ArrayList<Graph> words;

    public ArrayList<String> wordList;

    public CreateWordCloudGraphResponse(ArrayList<Graph> words, ArrayList<String> wordList){
        this.words = words;
        this.wordList = wordList;
    }

    public ArrayList<Graph> getWordGraphArray(){
        return words;
    }
}
