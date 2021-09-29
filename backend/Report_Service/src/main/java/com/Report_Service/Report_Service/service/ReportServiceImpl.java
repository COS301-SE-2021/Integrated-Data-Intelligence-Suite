package com.Report_Service.Report_Service.service;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.exception.ReporterException;
import com.Report_Service.Report_Service.request.GetTrendAnalysisDataRequest;
import com.Report_Service.Report_Service.request.ReportDataRequest;
import com.Report_Service.Report_Service.response.GetTrendAnalysisDataResponse;
import com.Report_Service.Report_Service.response.ReportDataResponse;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl {

    public ReportDataResponse reportData(ReportDataRequest request) throws ReporterException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }


        return new ReportDataResponse();
    }

    public GetTrendAnalysisDataResponse getTrendAnalysisData(GetTrendAnalysisDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GetTrendAnalysisDataResponse(null,null);
    }
}
