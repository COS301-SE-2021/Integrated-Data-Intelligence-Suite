package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/Import")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    @GetMapping("/{key}")
    public ImportTwitterResponse getTwitterDataJson(String key) throws Exception {
        ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.getTwitterDataJson(req);
    }
}
