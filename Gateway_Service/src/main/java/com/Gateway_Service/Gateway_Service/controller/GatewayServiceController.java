package com.Gateway_Service.Gateway_Service.controller;


import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;


@FeignClient("Import-Service")
interface ImportClient {

    @GetMapping("/{key}")
    @CrossOrigin
    ImportTwitterResponse getTwitterDataJson(@PathVariable String key);
}


@FeignClient("Parse-Service")
interface ParseClient {

    @GetMapping("/")
    @CrossOrigin
    ParseImportedDataResponse parseImportedData(String jsonString);
}


@FeignClient("Analyse-Service")
interface AnalyseClient {

    @GetMapping("/")
    @CrossOrigin
    TweetWithSentiment findSentiment(String line);
}


@RestController
public class GatewayServiceController {
    private final ImportClient importClient;
    private final ParseClient parseClient;
    private final AnalyseClient analyseClient;

    GatewayServiceController(ImportClient importClient, ParseClient parseClient, AnalyseClient analyseClient){
        this.importClient = importClient;
        this.parseClient = parseClient;
        this.analyseClient = analyseClient;
    }

    @GetMapping(value = "/{key}", produces = "application/json")
    @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public Collection<String> init(@PathVariable String key) {

        ImportTwitterResponse ImportRes = importClient.getTwitterDataJson(key);
        ParseImportedDataResponse ParseRes = parseClient.parseImportedData(ImportRes.getJsonData());

        ArrayList <String> Data = new ArrayList<>();
        for(int i = 0 ; i < ParseRes.getDataList().size(); i++) {
            TweetWithSentiment sentiment = analyseClient.findSentiment(ParseRes.getDataList().get(i).getTextMessage());
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

