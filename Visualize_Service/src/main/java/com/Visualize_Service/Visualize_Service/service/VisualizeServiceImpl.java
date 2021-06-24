package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeAnalyseDataResponse;
import org.springframework.stereotype.Service;

@Service
public class VisualizeServiceImpl {
    public VisualizeAnalyseDataResponse visualizeData(VisualizeDataRequest request) throws Exception{
        return new VisualizeAnalyseDataResponse();
    }
}
