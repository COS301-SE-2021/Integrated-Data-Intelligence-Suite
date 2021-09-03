package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.request.*;
import com.Import_Service.Import_Service.response.*;
import com.Import_Service.Import_Service.rri.AuthorizationType;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.json.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping(value = "/Import", produces = "application/json")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    public ImportServiceController() {
    }

    /**
     * This method is used to facilitate communication to the Import-Service.
     *
     * @param request This is a request entity which contains a ImportDataRequest object.
     * @return ImportDataResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/importData")
    public @ResponseBody ImportDataResponse importData(@RequestBody ImportDataRequest request) throws Exception{

        if(request == null) {
            throw new InvalidImporterRequestException("Request object is null.");
        }

        return service.importData(request);
    }

    /**
     * This method is used to facilitate communication to the Import-Service.
     *
     * @param request This is a request entity which contains a ImportTwitterRequest object.
     * @return ImportTwitterResponse This object contains imported data which has been processed by Import-Service.
     * @throws Exception This is thrown if exception caught in Import-Service.
     */
    @PostMapping(value = "/getTwitterDataJson")
    public @ResponseBody ImportTwitterResponse getTwitterDataJson(@RequestBody ImportTwitterRequest request) throws Exception {

        return service.getTwitterDataJson(request);
    }

    /**
     * This method is used  to facilitate communication to the Import_service.
     *
     * @param request Ta request entity which contains a TwitterRequest object.
     * @return ImportTwitterResponse. This object contains imported data which has been processed by Import-Service.
     */
    @PostMapping(value = "/importDatedData", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ImportTwitterResponse importDatedData(@RequestBody ImportTwitterRequest request) throws Exception {

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
        ImportTwitterResponse res;
        try{
            res = service.getTwitterDataJson(new ImportTwitterRequest(key));
        } catch (Exception e) {
            return "{\"data\": \""+e.getMessage()+"\"}";
        }
        if(res == null){
            return "{\"data\": \"No data found.\"}";
        }

        return res.getJsonData();
    }

    /**
     * This function retrieves twitter data based on a search key and date.
     *
     * @param key a phrase or sentence used as a search query
     * @param from the date at which the search should start. Date is in the form YYYY-MM-DD
     * @param to the date at which the search should end. Date is in the form YYYY-MM-DD
     * @return a json string with a list of tweets
     */
    @GetMapping(value = "test/twitter/{key}/{from}/{to}")
    public String testTwitterTwo(@PathVariable String key, @PathVariable String from, @PathVariable String to){
        ImportTwitterResponse res;
        try{
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            res = service.importDatedData(new ImportTwitterRequest(key, fromDate, toDate));
        } catch (Exception e) {
            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null){
            return "{\"data\": \"No data found.\"}";
        }

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
        ImportNewsDataResponse res;
        try {
            res = service.importNewsData(new ImportNewsDataRequest(key));
        } catch (Exception e) {

            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }
        if(res == null) {
            return "{\"data\": \"No data found.\"}";
        }


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
        ImportDataResponse res;
        try{
            res = service.importData(new ImportDataRequest(key, 100));

        } catch (ImporterException e) {

            return "{\"data\": \"Import failed.\", \"message\" : \""+ e.getMessage() + "\"}";
        }

        if(res == null) {
            return "{\"data\": \"No data found.\"}";
        }

        ArrayList<String> lst = new ArrayList<>();


        for (ImportedData str : res.getList()) {

            lst.add(str.getData());

        }
        return lst.toString();
    }

    @PostMapping(value = "/addApiSource")
    public @ResponseBody AddAPISourceResponse addApiSource(@RequestBody String jsonString) throws Exception {
        JSONObject obj = new JSONObject(jsonString);
        String name = obj.getString("name");
        String url = obj.getString("url");
        String method = obj.getString("method");
        String searchKey = obj.getString("searchKey");
        String auth = obj.getString("authorization");
        AuthorizationType authType = AuthorizationType.valueOf(obj.getString("authType"));
        Map<String, String> params = new LinkedHashMap<>();
        JSONArray paramsArray = obj.getJSONArray("parameters");
        for(int i = 0; i < paramsArray.length(); i++) {
            JSONObject paramObj = paramsArray.getJSONObject(i);
            params.put(paramObj.getString("parameter"), paramObj.getString("value"));
        }

        AddAPISourceRequest request = new AddAPISourceRequest(name, url, method, searchKey, authType, auth, params);
        return service.addAPISource(request);
    }

    @PostMapping(value = "/updateAPI")
    public @ResponseBody EditAPISourceResponse editAPISource(@RequestBody String jsonString) throws Exception {
        JSONObject obj = new JSONObject(jsonString);
        Long id = obj.getLong("id");
        String name = obj.getString("name");
        String url = obj.getString("url");
        String method = obj.getString("method");
        String searchKey = obj.getString("searchKey");
        String auth = obj.getString("authorization");
        AuthorizationType authType = AuthorizationType.valueOf(obj.getString("authType"));
        Map<String, String> params = new LinkedHashMap<>();
        JSONArray paramsArray = obj.getJSONArray("parameters");
        for(int i = 0; i < paramsArray.length(); i++) {
            JSONObject paramObj = paramsArray.getJSONObject(i);
            params.put(paramObj.getString("parameter"), paramObj.getString("value"));
        }

        EditAPISourceRequest request = new EditAPISourceRequest(id, name, url, method, searchKey, authType, auth, params);
        return service.editAPISource(request);
    }

    @GetMapping(value = "/getAllSources")
    public @ResponseBody GetAllAPISourcesResponse getAllAPISources() {
        return service.getAllAPISources();
    }

    @PostMapping(value = "/getSourceById")
    public @ResponseBody GetAPISourceByIdResponse getAPISourceById(@RequestBody GetAPISourceByIdRequest request) throws Exception {
        return service.getAPISourceById(request);
    }
}
