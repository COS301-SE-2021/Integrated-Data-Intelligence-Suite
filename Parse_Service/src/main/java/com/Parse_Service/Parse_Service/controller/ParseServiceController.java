package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.AddNewsPropertiesRequest;
import com.Parse_Service.Parse_Service.request.AddSocialMediaPropertiesRequest;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.AddNewsPropertiesResponse;
import com.Parse_Service.Parse_Service.response.AddSocialMediaPropertiesResponse;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Parse")
public class ParseServiceController {

    @Autowired
    private ParseServiceImpl service;


    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a ParseImportedDataRequest object.
     * @return ParseImportedDataResponse This object contains imported data which has been processed by Parse-Service.
     * @throws Exception This is thrown if exception caught in Parse-Service.
     */
    @PostMapping("/parseImportedData")
    public @ResponseBody ParseImportedDataResponse parseImportedData(@RequestBody ParseImportedDataRequest request) throws Exception {
        //ParseImportedDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("Request object is null");
        }

        return service.parseImportedData(request);
    }

    @PostMapping("/addSocialMediaProperties")
    public @ResponseBody AddSocialMediaPropertiesResponse addSocialMediaProperties(@RequestBody AddSocialMediaPropertiesRequest request) throws Exception {
        return service.addSocialMediaProperties(request);
    }

    @PostMapping("/addNewsProperties")
    public @ResponseBody AddNewsPropertiesResponse addNewsProperties(@RequestBody AddNewsPropertiesRequest request) throws Exception {
        return service.addNewsProperties(request);
    }

}
