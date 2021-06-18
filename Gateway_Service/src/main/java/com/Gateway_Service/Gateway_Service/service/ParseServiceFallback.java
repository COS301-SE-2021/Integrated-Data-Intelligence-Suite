package com.Gateway_Service.Gateway_Service.service;

import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ParseServiceFallback implements ParseService{
    @Override
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws Exception{
        return null;
    }
}
