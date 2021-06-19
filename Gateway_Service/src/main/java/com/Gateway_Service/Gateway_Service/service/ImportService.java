package com.Gateway_Service.Gateway_Service.service;

import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@FeignClient(value = "Import-Service" , url = "localhost:9001/Import" , fallback = ImportServiceFallback.class)
public interface ImportService {

    @GetMapping(value = "/importData")
    ImportDataResponse importData(@RequestParam("request") ImportDataRequest request) throws Exception;

    @GetMapping(value = "/getTwitterDataJson")
    ImportTwitterResponse getTwitterDataJson(@RequestParam("request") ImportTwitterRequest request) throws Exception ;
}
