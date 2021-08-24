package com.Visualize_Service.Visualize_Service.dataclass;

public class EdgeNetworkGraph extends NetworkGraph{

    public String group = "edges";

    public data data = new data();

    public void setData(String id, String source, String target){
        this.data.id = id;
        this.data.source = source;
        this.data.target = target;
    }

    class data {
        public String id;

        public String source;

        public String target;
    }

}


