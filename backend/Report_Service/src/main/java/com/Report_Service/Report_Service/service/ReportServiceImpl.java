package com.Report_Service.Report_Service.service;

import com.Report_Service.Report_Service.request.ReportDataRequest;
import com.Report_Service.Report_Service.response.ReportDataResponse;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl {

    public ReportDataResponse reportData(ReportDataRequest request){

        return new ReportDataResponse();
    }
}
