package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.dataclass.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportNewsDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportNewsDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/Import", produces = "application/json")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    /**
     * This method is used to facilitate communication to the Import-Service.
     * @param requestEntity This is a request entity which contains a ImportDataRequest object.
     * @return ImportDataResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/importData")
    public ImportDataResponse importData(RequestEntity<ImportDataRequest> requestEntity) throws Exception{
        ImportDataRequest request = requestEntity.getBody();
        return service.importData(request);
    }

    /**
     * This method is used to facilitate communication to the Import-Service.
     * @param requestEntity This is a request entity which contains a ImportTwitterRequest object.
     * @return ImportTwitterResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/getTwitterDataJson")
    public ImportTwitterResponse getTwitterDataJson(RequestEntity<ImportTwitterRequest> requestEntity) throws Exception {
        //ImportTwitterRequest req = new ImportTwitterRequest(key);
        ImportTwitterRequest request = requestEntity.getBody();
        return service.getTwitterDataJson(request);
    }




}
