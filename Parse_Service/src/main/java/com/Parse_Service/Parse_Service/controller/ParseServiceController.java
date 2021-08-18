package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Parse")
public class ParseServiceController {

    @Autowired
    private ParseServiceImpl service;


    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param requestEntity This is a request entity which contains a ParseImportedDataRequest object.
     * @return ParseImportedDataResponse This object contains imported data which has been processed by Parse-Service.
     * @throws Exception This is thrown if exception caught in Parse-Service.
     */
    @PostMapping("/parseImportedData")
    public ParseImportedDataResponse parseImportedData(RequestEntity<ParseImportedDataRequest> requestEntity) throws Exception {
        ParseImportedDataRequest request = requestEntity.getBody();
        return service.parseImportedData(request);
    }

}
