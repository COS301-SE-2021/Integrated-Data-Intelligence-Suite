package com.Visualize_Service.Visualize_Service.dataclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphSelector {

    HashSet<String> allGraphs = new HashSet<>();
    HashMap<String, Boolean> selectedGraphs = new HashMap<>();

    GraphSelector(){

    }

    public void selectGraph(Boolean select){

    }

    public void selectAllGraph(HashMap<String, Boolean> selectedGraphs){

    }

    public HashSet<String> getAllGraph(Boolean select){
        return allGraphs;
    }

    public void setAllGraph(HashSet<String> allGraphs){

    }
}
