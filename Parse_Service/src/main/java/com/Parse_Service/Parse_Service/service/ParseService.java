package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "Parse-Service")
public interface ParseService {
    ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException;
}
