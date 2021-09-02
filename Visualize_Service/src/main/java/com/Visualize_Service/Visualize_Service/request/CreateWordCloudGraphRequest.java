package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class CreateWordCloudGraphRequest {
    public ArrayList<String> wordBank;

    public CreateWordCloudGraphRequest(ArrayList<String> wordBank){
        this.wordBank = wordBank;
    }

    public ArrayList<String> getWordBank(){
        return wordBank;
    }
}
