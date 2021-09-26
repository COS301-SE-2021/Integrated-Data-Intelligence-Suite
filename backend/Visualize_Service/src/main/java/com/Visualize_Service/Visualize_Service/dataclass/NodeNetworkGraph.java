package com.Visualize_Service.Visualize_Service.dataclass;

public class NodeNetworkGraph extends NetworkGraph{

    public String group = "nodes";
    public data data = new data();
    public position position = new position();

    public void setData(String id){
        this.data.id = id;
    }

    public void setPosition(int x, int y){
        this.position.x = x;
        this.position.y = y;
    }

    class data {
        public String id;
    }

    class position {
        public int x;
        public int y;
    }

}


