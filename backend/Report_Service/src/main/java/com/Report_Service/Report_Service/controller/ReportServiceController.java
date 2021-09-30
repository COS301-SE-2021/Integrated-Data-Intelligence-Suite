package com.Report_Service.Report_Service.controller;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.exception.ReporterException;
import com.Report_Service.Report_Service.request.GetReportDataRequest;
import com.Report_Service.Report_Service.request.ReportDataRequest;
import com.Report_Service.Report_Service.response.GetReportDataResponse;
import com.Report_Service.Report_Service.response.ReportDataResponse;
import com.Report_Service.Report_Service.service.ReportServiceImpl;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/Report")
public class ReportServiceController {

    @Autowired
    private ReportServiceImpl service;

    /**
     * This method is used to facilitate communication to the Report-Service.
     * @param request This is a request entity which contains a ReportDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Report-Service.
     * @throws Exception This is thrown if exception caught in Report-Service.
     */
    @PostMapping("/visualizeData")
    public @ResponseBody
    ReportDataResponse reportData(@RequestBody ReportDataRequest request) throws ReporterException {
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("reportData Request Object is null");
        }

        return service.reportData(request);
    }

    /**
     * This method is used to facilitate communication to the Report-Service.
     * @param request This is a request entity which contains a ReportDataRequest object.
     * @return AnalyseDataResponse This object contains analysed data which has been processed by Report-Service.
     * @throws Exception This is thrown if exception caught in Report-Service.
     */
    @PostMapping("/visualizeData")
    public @ResponseBody
    GetReportDataResponse getReportData(@RequestBody GetReportDataRequest request) throws ReporterException {
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("getReportData Request Object is null");
        }

        if (request.getReportId() == null) {
            throw new InvalidRequestException("getReportData Request ID is null");
        }

        return service.getReportData(request);
    }
}
