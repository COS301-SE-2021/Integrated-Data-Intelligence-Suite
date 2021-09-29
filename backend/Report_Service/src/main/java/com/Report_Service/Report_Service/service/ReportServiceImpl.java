package com.Report_Service.Report_Service.service;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.exception.ReporterException;
import com.Report_Service.Report_Service.request.*;
import com.Report_Service.Report_Service.response.*;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl {

    public ReportDataResponse reportData(ReportDataRequest request) throws ReporterException {

        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }
        //does this work

        return new ReportDataResponse();
    }

    public GetTrendAnalysisDataResponse getTrendAnalysisData(GetTrendAnalysisDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GetTrendAnalysisDataResponse(null,null);
    }

    public GetPatternAndRelationshipDataResponse getPatternAndRelationshipData(GetPatternAndRelationshipDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GetPatternAndRelationshipDataResponse(null,null);
    }

    public GetAnomalyDataResponse getAnomalyData(GetAnomalyDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GetAnomalyDataResponse(null,null);
    }

    public GetTextualAnalysisDataResponse getTextualAnalysisData(GetTextualAnalysisDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GetTextualAnalysisDataResponse(null,null);
    }

    public GenerateReportPDFResponse generateReportPDF(GenerateReportPDFRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("Request Object is null");
        }

        return new GenerateReportPDFResponse();
    }
}
