package com.Visualize_Service.Visualize_Service.response;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;

import java.util.ArrayList;

public class CreateWordCloudGraphResponse {
    public String words;

    public CreateWordCloudGraphResponse(String words){
        this.words = words;
    }

    public String getLineGraphArray(){
        return words;
    }
}
