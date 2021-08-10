package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindEntitiesRequest {
    String text;

    public FindEntitiesRequest(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

}
