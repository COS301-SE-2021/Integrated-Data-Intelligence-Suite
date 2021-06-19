package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/parseImportedData")
    public ParseImportedDataResponse parseImportedData(RequestEntity<ParseImportedDataRequest> requestEntity) throws Exception {
        //DataSource type = DataSource.TWITTER;
        //ParseImportedDataRequest req = new ParseImportedDataRequest(type,jsonString);
        ParseImportedDataRequest request = requestEntity.getBody();
        return service.parseImportedData(request);
    }

}
