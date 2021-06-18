package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/Import")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    /*@GetMapping("/{key}")
    public ImportTwitterResponse getTwitterDataJson(@PathVariable String key) throws Exception {
        ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.getTwitterDataJson(req);
    }*/

    @GetMapping(value = "/importData")
    ImportDataResponse importData( ImportDataRequest request) throws Exception{
        //ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.importData(request);
    }

    @GetMapping(value = "/getTwitterDataJson")
    public ImportTwitterResponse getTwitterDataJson( ImportTwitterRequest request) throws Exception {
        //ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.getTwitterDataJson(request);
    }
}
