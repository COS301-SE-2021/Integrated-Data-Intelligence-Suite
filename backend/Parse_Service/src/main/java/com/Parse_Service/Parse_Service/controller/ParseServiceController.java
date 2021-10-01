package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.exception.*;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;

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

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a AddSocialMediaPropertiesRequest object.
     * @return AddSocialMediaPropertiesResponse This object contains the success and message after performing task.
     * @throws Exception This is thrown if an error was encountered.
     */
    @PostMapping("/addSocialMediaProperties")
    public @ResponseBody AddSocialMediaPropertiesResponse addSocialMediaProperties(@RequestBody AddSocialMediaPropertiesRequest request) throws Exception {
        return service.addSocialMediaProperties(request);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a AddNewsPropertiesRequest object.
     * @return AddNewsPropertiesResponse This object contains the success and message after performing task.
     * @throws Exception This is thrown if an error was encountered.
     */
    @PostMapping("/addNewsProperties")
    public @ResponseBody AddNewsPropertiesResponse addNewsProperties(@RequestBody AddNewsPropertiesRequest request) throws Exception {
        return service.addNewsProperties(request);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a ParseUploadedSocialDataRequest object.
     * @return ParseUploadedSocialDataResponse This object contains the success and message after performing task and
     * the parsed list.
     * @throws ParserException This is thrown if an error was encountered.
     */
    @PostMapping("/parseUploadedSocialData")
    public @ResponseBody ParseUploadedSocialDataResponse parseUploadedSocialData(@RequestBody ParseUploadedSocialDataRequest request) throws ParserException {
        return service.parseUploadedSocialData(request);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a ParseUploadedNewsDataRequest object.
     * @return ParseUploadedNewsDataResponse This object contains the success and message after performing task and the
     * parsed list.
     * @throws ParserException This is thrown if an error was encountered.
     */
    @PostMapping("/parseUploadedNewsData")
    public @ResponseBody ParseUploadedNewsDataResponse parseUploadedNewsData(@RequestBody ParseUploadedNewsDataRequest request) throws ParserException {
        return service.parseUploadedNewsData(request);
    }

}
