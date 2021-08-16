package com.Visualize_Service.Visualize_Service.dataclass;

public class EdgeNetworkGraph extends NetworkGraph{

    public class data {
        public String id;

        public String source;

        public String target;
    }

    public String getSource(){
        return new data().source;
    }

    public String getTarget(){
        return new data().target;
    }


}
