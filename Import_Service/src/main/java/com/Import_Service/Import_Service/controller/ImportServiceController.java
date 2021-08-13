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
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/Import", produces = "application/json")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    /**
     * This method is used to facilitate communication to the Import-Service.
     *
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
     *
     * @param requestEntity This is a request entity which contains a ImportTwitterRequest object.
     * @return ImportTwitterResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/getTwitterDataJson")
    public ImportTwitterResponse getTwitterDataJson(RequestEntity<ImportTwitterRequest> requestEntity) throws Exception {

        ImportTwitterRequest request = requestEntity.getBody();
        return service.getTwitterDataJson(request);
    }

    //TODO add comments
    @PostMapping(value = "/importDatedData")
    public ImportTwitterResponse importDatedData(RequestEntity<ImportTwitterRequest> requestEntity) throws Exception {

        ImportTwitterRequest request = requestEntity.getBody();
        return new ImportTwitterResponse("{\"data\" : \"returned ok\"}");
    }

    //TODO add comments
//    @PostMapping(value = "/TwitterData")
//    public ImportTwitterResponse getTwitterData(RequestEntity<ImportTwitterRequest> requestEntity) throws Exception {
//
//        ImportTwitterRequest request = requestEntity.getBody();
//        return service.importDatedData(request);
//    }

    /**
     * Used to test twitter data retrieval
     *
     * @param key keyword used to search for posts
     * @return a list of posts as a Json string
     */
    @GetMapping(value = "test/twitter/{key}")
    public String testTwitter(@PathVariable String key){
        ImportTwitterResponse res = null;
        try{
            res = service.getTwitterDataJson(new ImportTwitterRequest(key));
        } catch (Exception e) {
            return "{\"data\": \"Import failed.\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        return res.getJsonData();
    }

    //TODO ad comments
    @GetMapping(value = "test/twitter/{key}/{from}/{to}")
    public String testTwitterTwo(@PathVariable String key, @PathVariable String from, @PathVariable String to){
        ImportTwitterResponse res = null;
        try{
            res = service.importDatedData(new ImportTwitterRequest(key, from, to));
        } catch (Exception e) {
            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        return res.getJsonData();
    }

    /**
     * Used to test newsAPI data retrieval
     *
     * @param key keyword used to search for articles
     * @return a list of articles as a Json string
     */
    @GetMapping(value="test/news/{key}")
    public String testNewsAPI(@PathVariable String key){
        ImportNewsDataResponse res = null;
        try {
            res = service.importNewsData(new ImportNewsDataRequest(key));
        } catch (Exception e) {

            return "{\"data\": \"Import failed.\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";


        return res.getData();
    }

    /**
     * Used to test all data sources at the same time
     *
     * @param key keyword used to search multiple data sources
     * @return a list of articles from different data sources related to
     *         search key as a json string
     */
    @GetMapping(value="test/all/{key}")
    public  String searchData(@PathVariable String key){
        ImportDataResponse res = null;
        String retString = "";
        try{
            res = service.importData(new ImportDataRequest("bitcoin", 10));

        } catch (ImporterException e) {

            return "{\"data\": \"Import failed.\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        ArrayList<String> lst = new ArrayList<>();


        for (ImportedData str : res.getList()) {

            lst.add(str.getData());

        }
        return lst.toString();
    }


}
