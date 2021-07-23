package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.dataclass.Graph;
import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeAnalyseDataResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VisualizeServiceImpl {
    public VisualizeAnalyseDataResponse visualizeData(VisualizeDataRequest request) throws Exception{
        return new VisualizeAnalyseDataResponse();
    }



    private void createlineGraph(){
        Graph newGraph = new Graph();
    }


    private void createNetworkGraph(){

    }


    private void createMapGraph(){

    }



    private void createTimelineGraph(){

    }

}
