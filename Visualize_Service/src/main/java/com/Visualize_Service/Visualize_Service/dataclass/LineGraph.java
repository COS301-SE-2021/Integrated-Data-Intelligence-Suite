package com.Visualize_Service.Visualize_Service.dataclass;

import java.util.ArrayList;

public class LineGraph extends Graph {
    public String name;
    //public ArrayList<String> marker = new ArrayList<>();

    public static class marker {
        public String symbol = "square";
    }

    public ArrayList<Object> data  = new ArrayList<>();
}
