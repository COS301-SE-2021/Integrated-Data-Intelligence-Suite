package com.Parse_Service.Parse_Service.controller;


import com.Parse_Service.Parse_Service.exception.*;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;

import com.Parse_Service.Parse_Service.service.ParseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<?> parseImportedData(@RequestBody ParseImportedDataRequest request) throws Exception {
        //ParseImportedDataRequest request = requestEntity.getBody();
        if (request == null) {
            throw new InvalidRequestException("Request object is null");
        }

        ParseImportedDataResponse parseImportedDataResponse = service.parseImportedData(request);
        return new ResponseEntity<>(parseImportedDataResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a AddSocialMediaPropertiesRequest object.
     * @return AddSocialMediaPropertiesResponse This object contains the success and message after performing task.
     * @throws Exception This is thrown if an error was encountered.
     */
    @PostMapping("/addSocialMediaProperties")
    public @ResponseBody ResponseEntity<?> addSocialMediaProperties(@RequestBody AddSocialMediaPropertiesRequest request) throws Exception {

        AddSocialMediaPropertiesResponse addSocialMediaPropertiesResponse = service.addSocialMediaProperties(request);
        return new ResponseEntity<>(addSocialMediaPropertiesResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a AddNewsPropertiesRequest object.
     * @return AddNewsPropertiesResponse This object contains the success and message after performing task.
     * @throws Exception This is thrown if an error was encountered.
     */
    @PostMapping("/addNewsProperties")
    public @ResponseBody ResponseEntity<?> addNewsProperties(@RequestBody AddNewsPropertiesRequest request) throws Exception {
        AddNewsPropertiesResponse addNewsPropertiesResponse = service.addNewsProperties(request);
        return new ResponseEntity<>(addNewsPropertiesResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a ParseUploadedSocialDataRequest object.
     * @return ParseUploadedSocialDataResponse This object contains the success and message after performing task and
     * the parsed list.
     * @throws ParserException This is thrown if an error was encountered.
     */
    @PostMapping("/parseUploadedSocialData")
    public @ResponseBody ResponseEntity<?> parseUploadedSocialData(@RequestBody ParseUploadedSocialDataRequest request) throws ParserException {

        ParseUploadedSocialDataResponse parseUploadedSocialDataResponse = service.parseUploadedSocialData(request);
        return new ResponseEntity<>(parseUploadedSocialDataResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/parseTrainingData")
    public @ResponseBody ResponseEntity<?> parseUploadedTrainingData(@RequestBody ParseUploadedTrainingDataRequest request) throws ParserException {
        ParseUploadedTrainingDataResponse parseUploadedTrainingDataResponse = service.parseUploadedTrainingData(request);
        return new ResponseEntity<>(parseUploadedTrainingDataResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * This method is used to facilitate communication to the Parse-Service.
     * @param request This is a request entity which contains a ParseUploadedNewsDataRequest object.
     * @return ParseUploadedNewsDataResponse This object contains the success and message after performing task and the
     * parsed list.
     * @throws ParserException This is thrown if an error was encountered.
     */
    @PostMapping("/parseUploadedNewsData")
    public @ResponseBody ResponseEntity<?> parseUploadedNewsData(@RequestBody ParseUploadedNewsDataRequest request) throws ParserException {
        ParseUploadedNewsDataResponse parseUploadedNewsDataResponse = service.parseUploadedNewsData(request);
        return new ResponseEntity<>(parseUploadedNewsDataResponse, new HttpHeaders(), HttpStatus.OK);
    }

}
