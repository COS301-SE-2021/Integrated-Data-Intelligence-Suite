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
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/Import", produces = "application/json")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    public ImportServiceController() {
    }

    /**
     * This method is used to facilitate communication to the Import-Service.
     *
     * @param requestEntity This is a request entity which contains a ImportDataRequest object.
     * @return ImportDataResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/importData")
    public @ResponseBody ImportDataResponse importData(@RequestBody ImportDataRequest request) throws Exception{
        //ImportDataRequest request = requestEntity.getBody();
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
    public @ResponseBody ImportTwitterResponse getTwitterDataJson(@RequestBody ImportTwitterRequest request) throws Exception {

        //ImportTwitterRequest request = requestEntity.getBody();
        return service.getTwitterDataJson(request);
    }

    /**
     * This method is used  to facilitate communication to the Import_service.
     *
     * @param requestEntity Ta request entity which contains a TwitterRequest object.
     * @return ImportTwitterResponse. This object contains imported data which has been processed by Import-Service.
     */
    @PostMapping(value = "/importDatedData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ImportTwitterResponse importDatedData(@RequestBody ImportTwitterRequest request) throws Exception {

        //ImportTwitterRequest request = requestEntity.getBody();
        return service.importDatedData(request);
    }


    /**
     * This method retrieves twitter data based on a search key
     *
     * @param key a phrase used as a search term for twitter data.
     * @return a json string with a list of tweets
     */
    @GetMapping(value = "test/twitter/{key}")
    public String testTwitter(@PathVariable String key){
        ImportTwitterResponse res = null;
        try{
            res = service.getTwitterDataJson(new ImportTwitterRequest(key));
        } catch (Exception e) {
            return "{\"data\": \""+e.getMessage()+"\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        return res.getJsonData();
    }

    /**
     * This function retrieves twitter data basd on a search key and date.
     *
     * @param key a phrase or sentence used as a search query
     * @param from the date at which the search should start. Date is in the form YYYY-MM-DD
     * @param to the date at which the search should end. Date is in the form YYYY-MM-DD
     * @return a json string with a list of tweets
     */
    @GetMapping(value = "test/twitter/{key}/{from}/{to}")
    public String testTwitterTwo(@PathVariable String key, @PathVariable String from, @PathVariable String to){
        ImportTwitterResponse res = null;
        try{
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            res = service.importDatedData(new ImportTwitterRequest(key, fromDate, toDate));
        } catch (Exception e) {
            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        return res.getJsonData();
    }

    /**
     * This function is used to retrieve newsAPI articles based on a search key
     *
     * @param key a phrase or sentence used as a search query
     * @return a json string with a list of tweets
     */
    @GetMapping(value="test/news/{key}")
    public String testNewsAPI(@PathVariable String key){
        ImportNewsDataResponse res = null;
        try {
            res = service.importNewsData(new ImportNewsDataRequest(key));
        } catch (Exception e) {

            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";


        return res.getData();
    }

    /**
     * This function is used to retrieve data from different data sources based on a search key
     *
     * @param key a phrase or sentence used as a search query
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

            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null) return "{\"data\": \"No data found.\"}";

        ArrayList<String> lst = new ArrayList<>();


        for (ImportedData str : res.getList()) {

            lst.add(str.getData());

        }
        return lst.toString();
    }


}
