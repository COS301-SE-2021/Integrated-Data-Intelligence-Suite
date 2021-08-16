package com.Visualize_Service.Visualize_Service.dataclass;

public class NodeNetworkGraph extends NetworkGraph{

    public String group = "nodes";
    public data data = new data();

    public void setData(String id){
        this.data.id = id;
    }

    //public position position = new position();

    class data {
        public String id;
    }

    class position {
        public int x;
        public int y;
    }

}


