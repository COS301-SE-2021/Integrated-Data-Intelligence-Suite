package com.Report_Service.Report_Service.controller;

import com.Report_Service.Report_Service.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Report")
public class ReportServiceController {

    @Autowired
    private ReportServiceImpl service;
}
