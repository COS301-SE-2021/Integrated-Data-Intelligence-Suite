package com.Gateway_Service.Gateway_Service.controller;


import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Gateway_Service.Gateway_Service.dataclass.*;


import com.Gateway_Service.Gateway_Service.service.AnalyseService;
import com.Gateway_Service.Gateway_Service.service.ImportService;
import com.Gateway_Service.Gateway_Service.service.ParseService;





//import com.netflix.discovery.DiscoveryClient;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


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



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /*@RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        List<ServiceInstance> Listing = this.discoveryClient.getInstances(applicationName);

        ServiceInstance inst = Listing.get(0);
        inst.getServiceId();
        String clientURI = inst.getUri().toString();

        return Listing;
    }*/

    GatewayServiceController() {
        //importTemplate = new RestTemplate();
        //restTemplate.getForObject("http://localhost:9001/Import", FallbackController.class); //http://Import-Service/Import
        //restTemplate = new RestTemplate();
    }

    private String getServiceURL(String serviceName){
        return this.discoveryClient.getInstances(serviceName).get(0).getUri().toString();
    }


    //TEST FUNCTION
    @GetMapping(value ="/{key}", produces = "application/json")
    public String nothing(@PathVariable String key) throws NoSuchAlgorithmException {

        /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:9001/Import/importData")
                .method("GET", null)
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  "";



        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setSSLContext(SSLContext.getDefault())
                .useSystemProperties();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));*/


        //String body = "";
        //System.out.println(getServiceURL("Import-Service") +"/Import/importData");
        //String body = restTemplate.getForObject("http://Import-Service/Import/importData", String.class);


        /*String url = "http://Import-Service/Import/getTwitterDataJson/keyword";
        ImportTwitterRequest importReq = new ImportTwitterRequest(key,10);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("key",key);

        System.out.println("BUILDER!!!!!!!!!!!!!!!!.................." + builder.toUriString() );

        String Data =  restTemplate.getForObject("http://Import-Service/Import/importData",  String.class);

        System.out.println();
        System.out.println(Data);*/


        HttpHeaders requestHeaders = new HttpHeaders();
        //set up HTTP Basic Authentication Header
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        ImportTwitterRequest importReq = new ImportTwitterRequest(key,10);
        HttpEntity<ImportTwitterRequest> requestEntity =new HttpEntity<>(importReq,requestHeaders);



        //ImportTwitterResponse ImportRes = restTemplate.getForObject("https://Import-Service/Import/getTwitterDataJson", ImportTwitterResponse.class, importReq);
        ResponseEntity<ImportTwitterResponse> responseEntity = restTemplate.exchange("http://Import-Service/Import/getTwitterDataJson",  HttpMethod.POST, requestEntity,ImportTwitterResponse.class);
        //ImportTwitterResponse ImportRes =  restTemplate.getForObject("http://localhost:9001/Import/getTwitterDataJson/keyword",  ImportTwitterResponse.class);
        ImportTwitterResponse ImportRes = responseEntity.getBody();
        return ImportRes.getJsonData();
    }


    @GetMapping(value = "/man/{key}", produces = "application/json")
    //@CrossOrigin
    //@HystrixCommand(fallbackMethod = "fallback")
    public Collection<String> init(@PathVariable String key) throws Exception {


        ArrayList <String> outputData = new ArrayList<>();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        /*********************IMPORT*************************/

        /*ImportDataRequest importReq =  new ImportDataRequest(key,10);
        ImportDataResponse ImportRes = importClient.importData(importReq)

        if (ImportRes == null) {
            Data.add("Import Service Fail");
            return Data;
        }*/

        //String url = "http://Import-Service/Import/importData";
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("value",key);
        ImportDataRequest importRequest = new ImportDataRequest(key,10);
        HttpEntity<ImportDataRequest> requestImportEntity =new HttpEntity<>(importRequest,requestHeaders);

        ResponseEntity<ImportDataResponse> responseImportEntity = restTemplate.exchange("http://Import-Service/Import/importData",  HttpMethod.POST, requestImportEntity,ImportDataResponse.class);
        ImportDataResponse importResponse = responseImportEntity.getBody();


        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData());//    DataSource.TWITTER,ImportResponse. getJsonData());
        HttpEntity<ParseImportedDataRequest> requestParseEntity =new HttpEntity<>(parseRequest,requestHeaders);

        ResponseEntity<ParseImportedDataResponse> responseParseEntity = restTemplate.exchange("http://Parse-Service/Parse/parseImportedData",  HttpMethod.POST, requestParseEntity,ParseImportedDataResponse.class);
        ParseImportedDataResponse parseResponse = responseParseEntity.getBody();


        /*********************ANALYSE*************************/

        for(int i =0; i < parseResponse.getDataList().size();i++){

            String line = parseResponse.getDataList().get(i).getTextMessage();

            String url = "http://Analyse-Service/Analyse/findSentiment";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("line",line);

            //ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(com.Gateway_Service.Gateway_Service.dataclass.DataSource.TWITTER, importResponse.getList().get(0).getData());//    DataSource.TWITTER,ImportResponse. getJsonData());
            //HttpEntity<ParseImportedDataRequest> requestParseEntity =new HttpEntity<>(parseRequest,requestHeaders);

            ResponseEntity<AnalyseDataResponse> responseAnalyseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,AnalyseDataResponse.class);
            AnalyseDataResponse analyseResponse = responseAnalyseEntity.getBody();
        }



        //List<TweetWithSentiment> sentiments = new ArrayList<>();
        //sentiments.add(tweetWithSentiment);


        return outputData;

    }


}

