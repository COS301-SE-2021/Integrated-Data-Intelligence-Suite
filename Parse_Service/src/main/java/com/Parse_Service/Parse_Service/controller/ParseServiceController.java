package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Parse")
public class ParseServiceController {

    @Autowired
    private ParseServiceImpl service;

    /*@GetMapping("/")
    public ParseImportedDataResponse parseImportedData(String jsonString) throws Exception {
        DataSource type = DataSource.TWITTER;
        ParseImportedDataRequest req = new ParseImportedDataRequest(type,jsonString);
        return service.parseImportedData(req);
    }*/

    @GetMapping("/parseImportedData")
    public ParseImportedDataResponse parseImportedData(@RequestParam("request") ParseImportedDataRequest request) throws Exception {
        //DataSource type = DataSource.TWITTER;
        //ParseImportedDataRequest req = new ParseImportedDataRequest(type,jsonString);
        return service.parseImportedData(request);
    }

}
