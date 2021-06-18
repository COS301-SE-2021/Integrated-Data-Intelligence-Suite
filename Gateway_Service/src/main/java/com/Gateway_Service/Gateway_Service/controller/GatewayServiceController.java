package com.Gateway_Service.Gateway_Service.controller;


import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Gateway_Service.Gateway_Service.service.AnalyseService;
import com.Gateway_Service.Gateway_Service.service.ImportService;
import com.Gateway_Service.Gateway_Service.service.ParseService;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;


@RestController
@RequestMapping("/")
public class GatewayServiceController {

    @Qualifier("com.Gateway_Service.Gateway_Service.service.ImportService")
    @Autowired
    private ImportService importClient;

    @Qualifier("com.Gateway_Service.Gateway_Service.service.ParseService")
    @Autowired
    private ParseService parseClient;

    @Qualifier("com.Gateway_Service.Gateway_Service.service.AnalyseService")
    @Autowired
    private AnalyseService analyseClient;

    //GatewayServiceController(){ }

    @GetMapping(value = "/{key}", produces = "application/json")
    //@CrossOrigin
    //@HystrixCommand(fallbackMethod = "fallback")
    public Collection<String> init(@PathVariable String key) throws Exception {

        ArrayList <String> Data = new ArrayList<>();

        ImportTwitterRequest importReq = new ImportTwitterRequest(key,10);
        ImportTwitterResponse ImportRes = importClient.getTwitterDataJson(importReq);

        if (ImportRes == null) {
            Data.add("Import Service Fail");
            return Data;
        }

        ParseImportedDataRequest parseReq = new ParseImportedDataRequest(DataSource.TWITTER,ImportRes.getJsonData());
        ParseImportedDataResponse ParseRes = parseClient.parseImportedData(parseReq);

        if (ParseRes == null) {
            Data.add("Import Service Fail");
            return Data;
        }


        for(int i = 0 ; i < ParseRes.getDataList().size(); i++) {
            TweetWithSentiment sentiment = analyseClient.findSentiment(ParseRes.getDataList().get(i).getTextMessage());
            if (sentiment == null) {
                Data.add("Analyse-sentiment Service Fail");
                return Data;
            }

            Data.add(sentiment.toString());
        }

        return Data;


        /*return carClient.readCars()
                .getContent()
                .stream()
                .filter(this::isCool)
                .collect(Collectors.toList());*/
    }


}

