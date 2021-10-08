package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudGraphResponse {
    private ArrayList<Graph> words;

    private ArrayList<String> wordList;

    public CreateWordCloudGraphResponse(ArrayList<Graph> words, ArrayList<String> wordList){
        this.words = words;
        this.wordList = wordList;
    }

    public ArrayList<Graph> getWords(){
        return words;
    }

    public ArrayList<String> getWordList(){
        return wordList;
    }
}
