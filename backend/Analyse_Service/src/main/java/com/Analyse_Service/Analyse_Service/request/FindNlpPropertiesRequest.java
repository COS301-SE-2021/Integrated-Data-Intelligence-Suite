package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindNlpPropertiesRequest {
    ArrayList<String> text;

    public FindNlpPropertiesRequest(ArrayList<String> text){
        this.text = text;
    }

    public ArrayList<String> getText(){
        return text;
    }

}
