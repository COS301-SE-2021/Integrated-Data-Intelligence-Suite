package com.Report_Service.Report_Service.controller;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.exception.ReporterException;
import com.Report_Service.Report_Service.request.DeleteReportDataByIdRequest;
import com.Report_Service.Report_Service.request.GetReportDataByIdRequest;
import com.Report_Service.Report_Service.request.ReportDataRequest;
import com.Report_Service.Report_Service.response.DeleteReportDataByIdResponse;
import com.Report_Service.Report_Service.response.GetReportDataByIdResponse;
import com.Report_Service.Report_Service.request.ShareReportRequest;
import com.Report_Service.Report_Service.response.ReportDataResponse;
import com.Report_Service.Report_Service.response.ShareReportResponse;
import com.Report_Service.Report_Service.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/Report")
public class ReportServiceController {

    @Autowired
    private ReportServiceImpl service;

    /**
     * This method is used to facilitate communication to the Report-Service.
     * @param request This is a request entity which contains a ReportDataRequest object.
     * @return ReportDataResponse This object contains analysed data which has been processed by Report-Service.
     * @throws Exception This is thrown if exception caught in Report-Service.
     */
    @PostMapping("/reportData")
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
     * @return GetReportDataByIdResponse This object contains analysed data which has been processed by Report-Service.
     * @throws Exception This is thrown if exception caught in Report-Service.
     */
    @PostMapping("/getReportDataById")
    public @ResponseBody
    GetReportDataByIdResponse getReportDataById(@RequestBody GetReportDataByIdRequest request) throws ReporterException {
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("getReportData Request Object is null");
        }

        if (request.getReportId() == null) {
            throw new InvalidRequestException("getReportData Request ID is null");
        }

        return service.getReportDataById(request);
    }

    /**
     * This method is used to facilitate communication to the Report-Service.
     * @param request This is a request entity which contains a ReportDataRequest object.
     * @return DeleteReportDataByIdResponse This object contains analysed data which has been processed by Report-Service.
     * @throws Exception This is thrown if exception caught in Report-Service.
     */
    @PostMapping("/deleteReportDataById")
    public @ResponseBody
    DeleteReportDataByIdResponse deleteReportDataById(@RequestBody DeleteReportDataByIdRequest request) throws ReporterException {
        //VisualizeDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("getReportData Request Object is null");
        }

        if (request.getReportId() == null) {
            throw new InvalidRequestException("getReportData Request ID is null");
        }

        return service.deleteReportDataById(request);
    }

    @PostMapping(value = "/shareReport")
    public @ResponseBody
    ShareReportResponse shareReport(@RequestBody ShareReportRequest request) throws Exception {
        return service.shareReport(request);
    }
}
