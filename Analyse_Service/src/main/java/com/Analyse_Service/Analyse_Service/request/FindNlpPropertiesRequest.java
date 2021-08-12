package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindNlpPropertiesRequest {
    String text;

    public FindNlpPropertiesRequest(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

}
