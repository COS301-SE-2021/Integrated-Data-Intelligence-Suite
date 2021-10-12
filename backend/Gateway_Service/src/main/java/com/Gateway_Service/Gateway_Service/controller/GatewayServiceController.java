package com.Gateway_Service.Gateway_Service.controller;



import com.Gateway_Service.Gateway_Service.dataclass.analyse.*;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.ErrorGraph;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.ReportGraph;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.TrainResponseGraph;
import com.Gateway_Service.Gateway_Service.dataclass.impor.*;
import com.Gateway_Service.Gateway_Service.dataclass.report.*;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserRequest;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserResponse;
import com.Gateway_Service.Gateway_Service.dataclass.parse.*;
import com.Gateway_Service.Gateway_Service.dataclass.user.*;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataResponse;
import com.Gateway_Service.Gateway_Service.exception.*;
import com.Gateway_Service.Gateway_Service.rri.DataSource;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.Graph;
import com.Gateway_Service.Gateway_Service.rri.ServiceSuccesResponse;
import com.Gateway_Service.Gateway_Service.service.*;



//import com.netflix.discovery.DiscoveryClient;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/")
public class GatewayServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ImportService importClient;

    @Autowired
    private ParseService parseClient;

    @Autowired
    private AnalyseService analyseClient;

    @Autowired
    private VisualizeService visualizeClient;

    @Autowired
    private ReportService reportClient;
  
    @Autowired
    private UserService userClient;

    @Autowired
    private StorageService storageService;

    private static final Logger log = LoggerFactory.getLogger(GatewayServiceController.class);

    //@Autowired
    //private RestTemplate restTemplate;



    /**
     * This method is used the map/convert the name os a service to its respective url on a specific host
     * @param serviceName This is string value of a service's name identity
     * @return String This is string value that would represent a url of a service
     */
    private String getServiceURL(String serviceName){
        return this.discoveryClient.getInstances(serviceName).get(0).getUri().toString();
    }


    /*******************************************************************************************************************
    ***************************************************FRONT-END********************************************************
    ********************************************************************************************************************/


    /**
     * This method is used to facilitate communication to all the Services.
     * Outputs data related to a topic/key.
     * @param key This is a path variable of string value
     * @return ResponseEntity<ArrayList<ArrayList<Graph>>>
     *     This object contains data representing a response from all the services combined.
     * @throws Exception This is thrown if exception caught in any of the Services.
     */
    @PostMapping(value = "/main/{key}", produces = "application/json")
    @CrossOrigin
    //@HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<?> init(@PathVariable String key, @RequestBody SearchRequest request) throws Exception {
        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        System.out.println(request.getUsername());
        System.out.println(request.getPermission());
        //ArrayList <String> outputData = new ArrayList<>();
        HttpHeaders requestHeaders;

        /*********************IMPORT*************************/

        //String url = "http://Import-Service/Import/importData";
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("value",key);

        ImportDataRequest importRequest = new ImportDataRequest(key,100);
        ImportDataResponse importResponse = importClient.importData(importRequest);

        if(importResponse.getFallback() == true) {
            //outputData.add(importResponse.getFallbackMessage());
            //return new ArrayList<>();//outputData;

            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = importResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);


            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************IMPORT HAS BEEN DONE*************************");



        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData(), request.getPermission());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);
        ArrayList<ParsedData> socialMediaData = parseResponse.getDataList();

        ParseImportedDataRequest parseRequestNews = new ParseImportedDataRequest(DataSource.NEWSARTICLE, importResponse.getList().get(1).getData(), request.getPermission());
        parseResponse = parseClient.parseImportedData(parseRequestNews);
        ArrayList<ParsedArticle> newsData = parseResponse.getArticleList();
        ArrayList<ArrayList<ParsedData>> otherdata = new ArrayList<>();
        if(importResponse.getList().size() > 2) {
            ParseImportedDataRequest parseRequestOther = new ParseImportedDataRequest(DataSource.ADDED, importResponse.getList().get(1).getData(), request.getPermission());

            for(int i = 2; i < importResponse.getList().size(); i++) {
                parseRequestOther.setSourceName(importResponse.getList().get(i).getSourceName());
                parseResponse = parseClient.parseImportedData(parseRequestOther);
                otherdata.add(parseResponse.getDataList());
            }
        }

        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);

            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(socialMediaData, newsData);//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseDataResponse analyseResponse = null;
        //try {
            analyseResponse = analyseClient.analyzeData(analyseRequest);
        //}catch (Exception e){
         //   e.printStackTrace();
        //}


        if(analyseResponse.getFallback() == true) {
            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);

            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        if (analyseResponse.getAnomalyList() == null) System.out.println("Oi its empty");


        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        /*********************VISUALISE**********************/

        VisualizeDataRequest visualizeRequest = new VisualizeDataRequest(
                analyseResponse.getPattenList(),
                analyseResponse.getRelationshipList(),
                analyseResponse.getPredictionList(),
                analyseResponse.getTrendList(),
                analyseResponse.getAnomalyList(),
                analyseResponse.getWordList());//    DataSource.TWITTER,ImportResponse. getJsonData());
        VisualizeDataResponse visualizeResponse = visualizeClient.visualizeData(visualizeRequest);


        if(visualizeResponse.getFallback() == true) {
            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);

            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************VISUALIZE HAS BEEN DONE*************************");


        /*********************REPORT**********************/

        ReportDataRequest reportRequest = new ReportDataRequest(
                analyseResponse.getTrendList(),
                analyseResponse.getRelationshipList(),
                analyseResponse.getPattenList(),
                analyseResponse.getAnomalyList(),
                analyseResponse.getWordList());
        ReportDataResponse reportResponse = reportClient.reportData(reportRequest);

        GetReportDataByIdRequest reportRequest2 = new GetReportDataByIdRequest(reportResponse.getId());
        GetReportDataByIdResponse reportResponse2 = reportClient.getReportDataById(reportRequest2);



        System.out.println("***********************REPORT HAS BEEN DONE*************************");


        for(int i =0; i < visualizeResponse.outputData.size(); i++)
            outputData.add(visualizeResponse.outputData.get(i));

        ReportGraph reportGraph = new ReportGraph();
        //reportGraph.reportId = reportResponse.getId().toString();
        reportGraph.report = reportResponse2;
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(outputData);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(outputData,HttpStatus.OK);

    }


    /**
     * This endpoint will be use for uploading a file and saving the file to
     * a temporary directory such that it can be analyzed.
     * @param file This parameter will contain the file itself.
     * @param col1 This is the text
     * @param col2 This is the location
     * @param col3 This is the interactions
     * @param col4 This is the date
     * @param modelID If the data is social media or articles
     * @return This contains if the request of uploading a file was successful or not.
     */
    @PostMapping("/analyzeUpload")
    @CrossOrigin
    public ResponseEntity<?> fileAnalyzeUpload(@RequestParam("file") MultipartFile file,
                                                                         @RequestParam("c1") String col1,
                                                                         @RequestParam("c2") String col2,
                                                                         @RequestParam("c3") String col3,
                                                                         @RequestParam("c4") String col4,
                                                                         @RequestParam("modelID") String modelID) throws GatewayException, VisualizerException, ReporterException, AnalyserException {
        Map<String, String> response = new HashMap<>();
        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        assert extension != null;
        if(!extension.equals("csv")) {
            response.put("message", "Incorrect file type uploaded.");

            throw new GatewayException("Incorrect file type uploaded");

            //return new ResponseEntity<>(outputData, HttpStatus.NOT_ACCEPTABLE);
        }
        ArrayList<ParsedData> socialMediaData = new ArrayList<>();
        try {

            String filename = storageService.store(file);
            System.out.println("test location : " + filename);

            //response.put("message", "Successfully saved file");
            log.info("[Gateway API] Successfully saved file");
            log.info("[Gateway API] Running parser");
            //log.info(file.getOriginalFilename());
            ParseUploadedSocialDataResponse response1 = parseClient.parseUploadedSocialData(new ParseUploadedSocialDataRequest(filename, col1, col2, col3, col4));
            socialMediaData = response1.getSocialDataList();
            if(response1.isSuccess()) {
                response.put("success", "true");
            }
            else {
                response.put("success", "false");
            }
            response.put("message", response1.getMessage());


            if(storageService.deleteFile(filename)) {
                log.info("[Gateway API] Delete file: " + filename);
            }
            else {
                log.info("[Gateway API] Failed to delete file: " + filename);
            }

            log.info("[Gateway API] Successfully parsed. Attempting to analyze data");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GatewayException("Failed reading uploaded file");
        }

        AnalyseUserDataRequest userDataRequest = new AnalyseUserDataRequest(socialMediaData, modelID);

        /*return this.analyseUserData(userDataRequest);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(outputData);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());*/
        //return new ResponseEntity<>(outputData, HttpStatus.OK);
        return this.analyseUserData(userDataRequest);
    }

    @PostMapping(value = "/trainUpload")
    @CrossOrigin
    public ResponseEntity<?> fileTrainUpload(@RequestParam("file") MultipartFile file,
                                                                       @RequestParam("c1") String col1,
                                                                       @RequestParam("c2") String col2,
                                                                       @RequestParam("c3") String col3,
                                                                       @RequestParam("c4") String col4,
                                                                       @RequestParam("c5") String col5,
                                                                       @RequestParam("modelName") String modelname,
                                                                       @RequestParam("user") String userId)
    {
        Map<String, String> response = new HashMap<>();
        ArrayList<GetModelByIdResponse> outputData = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        System.out.println(file.getOriginalFilename());

        assert extension != null;
        if(!extension.equals("csv")) {
            response.put("message", "Incorrect file type uploaded.");
            return new ResponseEntity<>(outputData, HttpStatus.NOT_ACCEPTABLE);
        }
        ArrayList<ParsedTrainingData> trainingData = new ArrayList<>();
        try {
            String filename = storageService.store(file);

            log.info("[Gateway API] Successfully saved file");
            log.info("[Gateway API] Running parser");
            ParseUploadedTrainingDataResponse response1 = parseClient.parseUploadedTrainingData(new ParseUploadedTrainingDataRequest(filename, col1, col2, col3, col4, col5));
            trainingData = response1.getTrainingDataList();
            if (response1.isSuccess()) {
                response.put("success", "true");
            } else {
                response.put("success", "false");
            }
            response.put("message", response1.getMessage());


            if (storageService.deleteFile(filename)) {
                log.info("[Gateway API] Delete file: " + filename);
            } else {
                log.info("[Gateway API] Failed to delete file: " + filename);
            }

            log.info("[Gateway API] Successfully parsed training data. Attempting to analyze data");

            /****************Analyse****************/


            TrainUserModelRequest analyseRequest = new TrainUserModelRequest(modelname,trainingData);
            ResponseEntity<ArrayList<ArrayList<Graph>>> analyseResponse = (ResponseEntity<ArrayList<ArrayList<Graph>>>) this.trainUserModel(analyseRequest);

            GetModelsRequest analyseRequest2 = new GetModelsRequest(userId);


            ArrayList<ArrayList<Graph>> graphArray =  analyseResponse.getBody();
            TrainResponseGraph trainGraph = (TrainResponseGraph) graphArray.get(0).get(0);
            TrainUserModelResponse trainResponse = trainGraph.trainResponse;

            /****************User****************/

            ModelRequest modelRequest = new ModelRequest(userId, trainResponse.getModelId());
            ModelResponse userResponse = userClient.addModelForUser(modelRequest);


            return this.getAllModelsByUser(analyseRequest2);
        }
        catch (Exception e) {
            e.printStackTrace();
            //response.put("message", e.getMessage());
        }

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(outputData);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(outputData, HttpStatus.OK);
    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/generateReport",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> generateReport(@RequestBody ReportRequest request) throws ReporterException, UserException {

        GetReportDataByIdRequest repRequest = new GetReportDataByIdRequest(UUID.fromString(request.getReportID()));
        GetReportDataByIdResponse output = reportClient.getReportDataById(repRequest);

        ReportResponse userResponse = userClient.addReportForUser(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(output);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(output, HttpStatus.OK);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/getAllReportsByUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> getAllReportsByUser(@RequestBody GetUserReportsRequest request) throws ReporterException, UserException {

        /*********************USER******************/

        //GET ALL IDS
        int maxSizeId;

        GetUserReportsResponse userResponse = userClient.getUserReports(request);

        List<String> reportsList = userResponse.getReports();

        maxSizeId = reportsList.size();

        /*********************REPORT******************/

        ArrayList<GetReportDataByIdResponse> output = new ArrayList<>();

        GetReportDataByIdRequest reportRequest = new GetReportDataByIdRequest();

        for(int i =0; i < maxSizeId; i++) {
            reportRequest.setReportId(UUID.fromString(reportsList.get(i)));
            output.add(reportClient.getReportDataById(reportRequest));
        }

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(output);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(output, HttpStatus.OK);
    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     *
    @PostMapping(value = "/getUserReportById",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<GetReportDataByIdResponse> getUserReportById(@RequestBody ReportRequest request) {

        /*********************USER******************


        ReportResponse response = userClient.removeReportForUser(request);

        /*********************REPORT******************


        GetReportDataByIdRequest reportRequest = new GetReportDataByIdRequest();

        GetReportDataByIdResponse output = reportClient.getReportDataById(reportRequest);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }*/


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/deleteUserReportById",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> deleteUserReportById(@RequestBody ReportRequest request) throws ReporterException, UserException {


        /*********************REPORT******************/


        DeleteReportDataByIdRequest reportRequest = new DeleteReportDataByIdRequest(UUID.fromString(request.getReportID()));

        DeleteReportDataByIdResponse output = reportClient.deleteReportDataById(reportRequest);



        /*********************USER******************/
        if(output.getDeleted()) {
            ReportResponse userResponse = userClient.removeReportForUser(request);
        }


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(output);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(output, HttpStatus.OK);


    }



    @PostMapping(value = "/shareReport" , produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> shareReport(@RequestBody ShareReportRequest request) throws ReporterException {
        ShareReportResponse response = reportClient.shareReport(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/trainUserModel",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> trainUserModel(@RequestBody TrainUserModelRequest request) throws AnalyserException {


        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        /*********************PARSE*************************

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData(), request.getPermission());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);
        ArrayList<ParsedData> socialMediaData = parseResponse.getDataList();

        ParseImportedDataRequest parseRequestNews = new ParseImportedDataRequest(DataSource.NEWSARTICLE, importResponse.getList().get(1).getData(), request.getPermission());
        parseResponse = parseClient.parseImportedData(parseRequestNews);
        ArrayList<ParsedArticle> newsData = parseResponse.getArticleList();

        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ReportGraph errorGraph = new ReportGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/


        TrainUserModelRequest analyseRequest = new TrainUserModelRequest(request.getModelName(),request.getDataList() );
        TrainUserModelResponse analyseResponse = analyseClient.trainUserModel(analyseRequest);


        TrainResponseGraph reportGraph = new TrainResponseGraph();
        //reportGraph.reportId = reportResponse.getId().toString();
        reportGraph.trainResponse = analyseResponse;
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);


        /*if(analyseResponse.getFallback() == true) {
            ReportGraph errorGraph = new ReportGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }*/



        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(outputData);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(outputData,HttpStatus.OK);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/analyseUserData",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> analyseUserData(@RequestBody AnalyseUserDataRequest request) throws AnalyserException, ReporterException, VisualizerException {

        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        /*********************PARSE*************************

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData(), request.getPermission());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);
        ArrayList<ParsedData> socialMediaData = parseResponse.getDataList();

        ParseImportedDataRequest parseRequestNews = new ParseImportedDataRequest(DataSource.NEWSARTICLE, importResponse.getList().get(1).getData(), request.getPermission());
        parseResponse = parseClient.parseImportedData(parseRequestNews);
        ArrayList<ParsedArticle> newsData = parseResponse.getArticleList();

        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ReportGraph errorGraph = new ReportGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseUserDataRequest analyseRequest = new AnalyseUserDataRequest(request.getDataList(), request.getModelId());//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseUserDataResponse analyseResponse = analyseClient.analyzeUserData(analyseRequest);


        if(analyseResponse.getFallback()) {
            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);

            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }



        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        /*********************VISUALISE**********************/

        VisualizeDataRequest visualizeRequest = new VisualizeDataRequest(
                analyseResponse.getPattenList(),
                analyseResponse.getRelationshipList(),
                analyseResponse.getPredictionList(),
                analyseResponse.getTrendList(),
                analyseResponse.getAnomalyList(),
                analyseResponse.getWordList());//    DataSource.TWITTER,ImportResponse. getJsonData());
        VisualizeDataResponse visualizeResponse = visualizeClient.visualizeData(visualizeRequest);


        if(visualizeResponse.getFallback()) {
            ErrorGraph informationGraph = new ErrorGraph();
            informationGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(informationGraph);

            outputData.add( data);

            ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
            serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
            //serviceSuccesResponse.setPathUri(request.getDescription(true));
            serviceSuccesResponse.setStatus(HttpStatus.OK);
            serviceSuccesResponse.setData(outputData);

            return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
            //return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************VISUALIZE HAS BEEN DONE*************************");


        /*********************REPORT**********************/

        ReportDataRequest reportRequest = new ReportDataRequest(
                analyseResponse.getTrendList(),
                analyseResponse.getRelationshipList(),
                analyseResponse.getPattenList(),
                analyseResponse.getAnomalyList(),
                analyseResponse.getWordList());
        ReportDataResponse reportResponse = reportClient.reportData(reportRequest);

        GetReportDataByIdRequest reportRequest2 = new GetReportDataByIdRequest(reportResponse.getId());
        GetReportDataByIdResponse reportResponse2 = reportClient.getReportDataById(reportRequest2);

        /*ErrorGraph reportGraph = new ErrorGraph();
        reportGraph.Error = reportResponse.getId().toString();
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);*/

        for(int i =0; i < visualizeResponse.outputData.size(); i++)
            outputData.add(visualizeResponse.outputData.get(i));


        ReportGraph reportGraph = new ReportGraph();
        //reportGraph.reportId = reportResponse.getId().toString();
        reportGraph.report = reportResponse2;
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);

        System.out.println("***********************REPORT HAS BEEN DONE*************************");


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(outputData);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(outputData,HttpStatus.OK);
    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/getAllModelsByUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> getAllModelsByUser(@RequestBody GetModelsRequest request) throws AnalyserException, UserException {

        /*********************USER******************/

        //GET ALL IDS
        int maxSizeId;


        GetModelsResponse userResponse = userClient.getUserModels(request);

        Map<String, Boolean> models = userResponse.getModels();

        maxSizeId = 0;//reportsList.size();

        /*********************REPORT******************/

        ArrayList<GetModelByIdResponse> output = new ArrayList<>();

        GetModelByIdRequest analyseRequest = new GetModelByIdRequest();

        //add/show default
        GetModelByIdResponse defaultModel = new GetModelByIdResponse("Default", "Default", "Dynamic");
        boolean foundSelected = true;
        output.add(defaultModel);

        for (Map.Entry<String,Boolean> entry : models.entrySet()) {
            analyseRequest.setModelId(entry.getKey());

            GetModelByIdResponse AnalyseResponse = analyseClient.getModelById(analyseRequest);
            if(entry.getValue().equals(true))
                foundSelected= false;
            AnalyseResponse.setIsModelDefault(entry.getValue());
            output.add(AnalyseResponse);
        }
        output.get(0).setIsModelDefault(foundSelected);


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(output);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(output, HttpStatus.OK);
    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/getSelectedModelId",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> getModelInfoById(@RequestBody ModelRequest request) throws AnalyserException, UserException {

        //TODO: user returns selected model id

        /*********************USER******************/

        GetUserReportsResponse userResponse = userClient.getUserReports(new GetUserReportsRequest());

        /*********************ANALYSE******************/

        GetModelByIdRequest analyseRequest = new GetModelByIdRequest(request.getModelID());
        GetModelByIdResponse output = analyseClient.getModelById(analyseRequest);


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(output);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(output, HttpStatus.OK);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/deleteUserModelsByUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> deleteUserModelById(@RequestBody ModelRequest request) throws AnalyserException, UserException {

        //TODO: user removes from list

        /*********************USER******************/

        ModelResponse userResponse = userClient.removeModelForUser(request);


        GetModelsRequest analyseRequest2 = new GetModelsRequest(request.getUserID());
        return this.getAllModelsByUser(analyseRequest2);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/selectModel",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> selectModel(@RequestBody ModelRequest request) throws AnalyserException, UserException {

        GetModelsRequest getAllReq = new GetModelsRequest(request.getUserID());

        GetModelsResponse getAll = userClient.getUserModels(getAllReq);

        Map<String, Boolean> models = getAll.getModels();


        ModelRequest deselectReq = new ModelRequest(request.getUserID(), "");
        for (Map.Entry<String,Boolean> entry : models.entrySet()) {
            deselectReq.setModelID(entry.getKey());
            ModelResponse deselectResp = userClient.deselectModel(deselectReq);
        }

        ModelResponse userResponse = userClient.selectModel(request);


        GetModelsRequest analyseRequest2 = new GetModelsRequest(request.getUserID());
        return this.getAllModelsByUser(analyseRequest2);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/addUserModel",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> addUserModel(@RequestBody ModelRequest request) throws AnalyserException, UserException {

        GetModelByIdRequest analyseRequest = new GetModelByIdRequest(request.getModelID());
        GetModelByIdResponse analyseResponse = analyseClient.getModelById(analyseRequest);

        GetModelsRequest analyseRequest2 = new GetModelsRequest(request.getUserID());

        if(analyseResponse.getModelID() == null) { // doesn't find model
            //return this.getAllModelsByUser(analyseRequest2); //todo: out failure
            throw new AnalyserException("Model id is not found");
        }

        /*********************USER******************/

        ModelResponse userResponse = userClient.addModelForUser(request);

        return this.getAllModelsByUser(analyseRequest2);
    }




    /*******************************************************************************************************************
     *****************************************************USER**********************************************************
     *******************************************************************************************************************/

    /**
     * This the endpoint for registering the user.
     * @param form This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/register",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> register(@RequestBody RegisterForm form) throws UserException {
        RegisterRequest registerRequest = new RegisterRequest(form.getUsername(), form.getFirstName(), form.getLastName(), form.getPassword(), form.getEmail());
        RegisterResponse registerResponse = userClient.register(registerRequest);


        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(registerResponse);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @GetMapping(value ="user/getUser/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> getUser(@PathVariable String id) throws UserException {
        GetUserRequest getUserRequest = new GetUserRequest(UUID.fromString(id));
        GetUserResponse getUserResponse = userClient.getUser(getUserRequest);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(getUserResponse);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(getUserResponse, HttpStatus.OK);
    }

    /**
     * This is the endpoint to allow the user to login.
     * @param request This is the body send by POST
     * @return This is the response http entity
     */
    @PostMapping(value = "/user/login",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws UserException {
        LoginResponse response = userClient.login(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This is the endpoint to allow the user to verify their account
     * via email.
     * @param request This is the body send by POST
     * @return This is the response http entity
     */
    @PostMapping(value = "/user/verify",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> verify(@RequestBody VerifyAccountRequest request) throws UserException {
        System.out.println("Verifying User: " + request.getEmail());
        VerifyAccountResponse response = userClient.verifyAccount(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for resending the verification code.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/resend",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> resendCode(@RequestBody ResendCodeRequest request) throws UserException {
        ResendCodeResponse response = userClient.resendCode(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for sending the OTP code.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/sendOTP",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> sendOTP(@RequestBody ResendCodeRequest request) throws UserException {
        ResendCodeResponse response = userClient.sendOTP(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for resetting the password for the user.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/resetPassword",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws UserException {
        System.out.println(request.getNewPassword());
        ResetPasswordResponse response = userClient.resetPassword(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for resending the verification code.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/updateProfile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) throws UserException {
        UpdateProfileResponse response = userClient.updateProfile(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for changing the permission of a user
     * @param request This is the body send by POST
     * @return This is the response http entity
     */
    @PostMapping(value = "/changeUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> changeUser(@RequestBody ChangeUserRequest request) throws UserException {
        ChangeUserResponse response = userClient.managePermissions(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @return This is the response http entity. It contains all the users.
     */
    @GetMapping(value = "/user/getAll", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> getAllUsers() throws UserException {
        System.out.println("Getting all users from the database");
        GetAllUsersResponse response = userClient.getAllUsers();

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/retrievePrevious", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> retrievePreviousData() {
        return null;
    }

    /*******************************************************************************************************************
     *****************************************************IMPORT********************************************************
     *******************************************************************************************************************/

    /**
     * This the endpoint for getting all the users registered on the system.
     * @param jsonRequest This is the body send by POST.
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/addNewApiSource", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> addApiSource(@RequestBody String jsonRequest) throws ImporterException {
        String response = ""; //importClient.addApiSource(jsonRequest); //TODO

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @param request This is the body send by POST
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/getSourceById", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> getSourceById(@RequestBody GetAPISourceByIdRequest request) throws ImporterException {
        GetAPISourceByIdResponse response = importClient.getSourceById(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @param jsonRequest This is the body send by POST
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/updateAPI", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> editAPISource(@RequestBody String jsonRequest) throws ImporterException {
        String response = "" ; //importClient.editAPISource(jsonRequest); //TODO

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @param request This is the body send by POST
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/deleteSource", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> deleteSource(@RequestBody DeleteSourceRequest request) throws ImporterException {
        log.info("[API] Deleting api source");
        DeleteSourceResponse response = importClient.deleteSource(request);

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the api sources.
     * @return This is the response http entity. It contains all the users.
     */
    @GetMapping(value = "/getAllSources", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> editAPISource() throws ImporterException {
        GetAllAPISourcesResponse response = importClient.getAllAPISources();

        ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
        serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
        //serviceSuccesResponse.setPathUri(request.getDescription(true));
        serviceSuccesResponse.setStatus(HttpStatus.OK);
        serviceSuccesResponse.setData(response);

        return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
        //return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/collect/{key}/{from}/{to}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<?> collectDatedData(@PathVariable String key, @PathVariable String from, @PathVariable String to) throws ParserException, ImporterException {


        ImportTwitterResponse res = importClient.importDatedData(new ImportTwitterRequest(key, from, to));

        if(!res.getFallback()){
            System.out.println(".........................Import completed successfully..................\n\n\n");


            ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, res.getJsonData(), "VIEWING");
            ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);

            if(!parseResponse.getFallback()) {
                System.out.println("........................Parsed Data Successfully...........................\n\n\n");

                ServiceSuccesResponse serviceSuccesResponse = new ServiceSuccesResponse();
                serviceSuccesResponse.setTimeStamp(LocalDateTime.now());
                //serviceSuccesResponse.setPathUri(request.getDescription(true));
                serviceSuccesResponse.setStatus(HttpStatus.OK);
                serviceSuccesResponse.setData("{ \n \"success\" : true \n}");

                return new ResponseEntity<>(serviceSuccesResponse, new HttpHeaders(), serviceSuccesResponse.getStatus());
                //return new ResponseEntity<>("{ \n \"success\" : true \n}",HttpStatus.OK);
            }

        }
        System.out.println("///////////////////////////////////////    FAILED //////////////////////////////////////////");
        return null;
    }


    /*******************************************************************************************************************
     ***************************************************ANALYSE*********************************************************
     *******************************************************************************************************************/

    @GetMapping(value ="/analyse/trainApplicationData", produces = "application/json")
    public String trainData() {
        String output = "";

        //analyseClient.trainData();
        if(analyseClient.trainApplicationData())
            output = "Success training application";
        else{
            output = "Fail training application";
        }
        return output;
    }


}

