package com.Gateway_Service.Gateway_Service.service;


import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "Parse-Service" ,  url = "localhost/Parse:9003" , fallback = ParseServiceFallback.class)
public interface ParseService {
    @GetMapping("/parseImportedData")
    public ParseImportedDataResponse parseImportedData(@RequestParam("request") ParseImportedDataRequest request) throws Exception;
}
