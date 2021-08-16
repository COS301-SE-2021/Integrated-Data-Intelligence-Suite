package com.Visualize_Service.Visualize_Service.dataclass;

public class EdgeNetworkGraph extends NetworkGraph{

    public String group = "edges";

    public data data = new data();

    class data {
        public String id;

        public String source;

        public String target;
    }

}


