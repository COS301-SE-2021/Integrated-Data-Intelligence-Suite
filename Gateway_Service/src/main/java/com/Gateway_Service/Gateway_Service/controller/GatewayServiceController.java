package com.Gateway_Service.Gateway_Service.controller;



import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Gateway_Service.Gateway_Service.dataclass.*;


import com.Gateway_Service.Gateway_Service.service.AnalyseService;
import com.Gateway_Service.Gateway_Service.service.ImportService;
import com.Gateway_Service.Gateway_Service.service.ParseService;





//import com.netflix.discovery.DiscoveryClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@RestController
@RequestMapping("/")
public class GatewayServiceController {
    @Autowired
    private ImportService importClient;

    @Autowired
    private ParseService parseClient;

    @Autowired
    private AnalyseService analyseClient;


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    private String getServiceURL(String serviceName){
        return this.discoveryClient.getInstances(serviceName).get(0).getUri().toString();
    }


    //TEST FUNCTION
    @GetMapping(value ="/{key}", produces = "application/json")
    public String testNothing(@PathVariable String key) {
        String output = "";

        ImportTwitterRequest importReq = new ImportTwitterRequest(key,10);
        ImportTwitterResponse importRes = importClient.getTwitterDataJson(importReq);

        if(importRes.getFallback() == true)
            output = importRes.getFallbackMessage();
        else
            output = importRes.getJsonData();

        return output;
    }

    @GetMapping(value ="test/{line}", produces = "application/json")
    public String testNothing2(@PathVariable String line) {

        String output = "";
        AnalyseDataResponse analyseResponse = analyseClient.findSentiment(line);

        if(analyseResponse.getFallback() == true)
            output = analyseResponse.getFallbackMessage();
        else {
            output = analyseResponse.getSentiment().getCssClass();
        }

        return output;
    }


    @GetMapping(value = "/man/{key}", produces = "application/json")
    //@CrossOrigin
    //@HystrixCommand(fallbackMethod = "fallback")
    public Collection<String> init(@PathVariable String key) throws Exception {


        ArrayList <String> outputData = new ArrayList<>();
        HttpHeaders requestHeaders;

        /*********************IMPORT*************************/

        //String url = "http://Import-Service/Import/importData";
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("value",key);

        ImportDataRequest importRequest = new ImportDataRequest(key,10);
        ImportDataResponse importResponse = importClient.importData(importRequest);

        if(importResponse.getFallback() == true) {
            outputData.add(importResponse.getFallbackMessage());
            return outputData;
        }

        System.out.println("***********************IMPORT HAS BEEN DONE*************************");



        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData());//    DataSource.TWITTER,ImportResponse. getJsonData());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);


        if(parseResponse.getFallback() == true) {
            outputData.add(parseResponse.getFallbackMessage());
            return outputData;
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        for(int i =0; i < parseResponse.getDataList().size();i++){

            String line = parseResponse.getDataList().get(0).getTextMessage();
            AnalyseDataResponse analyseResponse = analyseClient.findSentiment(line);


            if(analyseResponse.getFallback() == true) {
                outputData.add(analyseResponse.getFallbackMessage());
                return outputData;
            }
            else{
                outputData.add(analyseResponse.getSentiment().toString());
            }
        }

        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        return outputData;

    }


}

