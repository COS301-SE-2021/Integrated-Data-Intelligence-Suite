package com.Gateway_Service.Gateway_Service.controller;



import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.impor.*;
import com.Gateway_Service.Gateway_Service.dataclass.report.GetReportDataByIdRequest;
import com.Gateway_Service.Gateway_Service.dataclass.report.GetReportDataByIdResponse;
import com.Gateway_Service.Gateway_Service.dataclass.report.ReportDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.report.ReportDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserRequest;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserResponse;
import com.Gateway_Service.Gateway_Service.dataclass.parse.*;
import com.Gateway_Service.Gateway_Service.dataclass.user.*;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.visualize.VisualizeDataResponse;
import com.Gateway_Service.Gateway_Service.exception.GatewayException;
import com.Gateway_Service.Gateway_Service.rri.DataSource;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.ErrorGraph;
import com.Gateway_Service.Gateway_Service.dataclass.gateway.Graph;
import com.Gateway_Service.Gateway_Service.service.*;



//import com.netflix.discovery.DiscoveryClient;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ArrayList<ArrayList<Graph>>> init(@PathVariable String key, @RequestBody SearchRequest request) throws Exception {
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

            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = importResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************IMPORT HAS BEEN DONE*************************");



        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData(), request.getPermission());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);
        ArrayList<ParsedData> socialMediaData = parseResponse.getDataList();

        ParseImportedDataRequest parseRequestNews = new ParseImportedDataRequest(DataSource.NEWSARTICLE, importResponse.getList().get(1).getData(), request.getPermission());
        parseResponse = parseClient.parseImportedData(parseRequestNews);
        ArrayList<ParsedArticle> newsData = parseResponse.getArticleList();

        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(socialMediaData, newsData);//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseDataResponse analyseResponse = analyseClient.analyzeData(analyseRequest);


        if(analyseResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
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


        if(visualizeResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
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

        ErrorGraph reportGraph = new ErrorGraph();
        reportGraph.Error = reportResponse.getId().toString();
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);

        System.out.println("***********************REPORT HAS BEEN DONE*************************");


        for(int i =0; i < visualizeResponse.outputData.size(); i++)
            outputData.add(visualizeResponse.outputData.get(i));

        return new ResponseEntity<>(outputData,HttpStatus.OK);

    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/generateReport",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<GetReportDataByIdResponse> generateReport(@RequestBody GetReportDataByIdRequest request) {


        GetReportDataByIdResponse output = reportClient.getReportDataById(request);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/getAllReportsByUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<ArrayList<GetReportDataByIdResponse>> getAllReportsByUser(@RequestBody GetReportDataByIdRequest request) {

        /*********************USER******************/

        //GET ALL IDS
        int maxSizeId =0;

        /*********************REPORT******************/

        ArrayList<GetReportDataByIdResponse> output = new ArrayList<>();

        for(int i =0; i < maxSizeId; i++){
            output.add(reportClient.getReportDataById(request));
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }


    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/trainUserModel",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<TrainUserModelResponse> trainUserModel(@RequestBody TrainUserModelRequest request) {



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
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/


        TrainUserModelRequest analyseRequest = new TrainUserModelRequest("", new ArrayList<ParsedData>());
        TrainUserModelResponse analyseResponse = analyseClient.trainUserModel(analyseRequest);


        /*if(analyseResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }*/



        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");



        return new ResponseEntity<>(analyseResponse, HttpStatus.OK);
    }

    /**
     * This the endpoint for registering the user.
     * @param request This is the body sent by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/analyseUserData",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<ArrayList<ArrayList<Graph>>> analyseUserData(@RequestBody AnalyseUserDataRequest request) {

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
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseUserDataRequest analyseRequest = new AnalyseUserDataRequest(new ArrayList<ParsedData>(), "");//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseUserDataResponse analyseResponse = analyseClient.analyzeUserData(analyseRequest);


        if(analyseResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
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


        if(visualizeResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
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

        ErrorGraph reportGraph = new ErrorGraph();
        reportGraph.Error = reportResponse.getId().toString();
        ArrayList<Graph> reportData = new ArrayList<>();
        reportData.add(reportGraph);
        outputData.add(reportData);

        System.out.println("***********************REPORT HAS BEEN DONE*************************");


        for(int i =0; i < visualizeResponse.outputData.size(); i++)
            outputData.add(visualizeResponse.outputData.get(i));

        return new ResponseEntity<>(outputData,HttpStatus.OK);
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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterForm form) {
        RegisterRequest registerRequest = new RegisterRequest(form.getUsername(), form.getFirstName(), form.getLastName(), form.getPassword(), form.getEmail());
        RegisterResponse registerResponse = userClient.register(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }


    /*
    @PostMapping(value = "/user/requestAdmin",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<RegisterAdminResponse> registerAdmin(@RequestBody RegisterForm form) {
        RegisterAdminRequest registerRequest = new RegisterAdminRequest(form.getUsername(), form.getFirstName(), form.getLastName(), form.getPassword(), form.getEmail());
        RegisterAdminResponse registerResponse = userClient.requestAdmin(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }
    */


    @GetMapping(value ="user/getUser/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String id){
        GetUserRequest getUserRequest = new GetUserRequest(UUID.fromString(id));
        GetUserResponse getUserResponse = userClient.getUser(getUserRequest);
        return new ResponseEntity<>(getUserResponse, HttpStatus.OK);
    }

    /**
     * This is the endpoint to allow the user to login.
     * @param request This is the body send by POST
     * @return This is the response http entity
     */
    @PostMapping(value = "/user/login",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userClient.login(request);
        System.out.println(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<VerifyAccountResponse> verify(@RequestBody VerifyAccountRequest request) {
        System.out.println("Verifying User: " + request.getEmail());
        VerifyAccountResponse response = userClient.verifyAccount(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for resending the verification code.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/resend",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<ResendCodeResponse> resendCode(@RequestBody ResendCodeRequest request) {
        ResendCodeResponse response = userClient.resendCode(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for resending the verification code.
     * @param request This is the body send by POST
     * @return This is the response http entity.
     */
    @PostMapping(value = "/user/updateProfile",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<UpdateProfileResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
        UpdateProfileResponse response = userClient.updateProfile(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * This the endpoint for changing the permission of a user
     * @param request This is the body send by POST
     * @return This is the response http entity
     */
    @PostMapping(value = "/changeUser",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<ChangeUserResponse> changeUser(@RequestBody ChangeUserRequest request) {
        ChangeUserResponse response = userClient.managePermissions(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @return This is the response http entity. It contains all the users.
     */
    @GetMapping(value = "/user/getAll", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        System.out.println("Getting all users from the database");
        GetAllUsersResponse response = userClient.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/retrievePrevious", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<ArrayList<ArrayList<Graph>>> retrievePreviousData() {
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
    public ResponseEntity<String> addApiSource(@RequestBody String jsonRequest) {
        String response = importClient.addApiSource(jsonRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @param request This is the body send by POST
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/getSourceById", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<GetAPISourceByIdResponse> getSourceById(@RequestBody GetAPISourceByIdRequest request) {
        GetAPISourceByIdResponse response = importClient.getSourceById(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the users registered on the system
     * @param jsonRequest This is the body send by POST
     * @return This is the response http entity. It contains all the users.
     */
    @PostMapping(value = "/updateAPI", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<String> editAPISource(@RequestBody String jsonRequest) {
        String response = importClient.editAPISource(jsonRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This the endpoint for getting all the api sources.
     * @return This is the response http entity. It contains all the users.
     */
    @GetMapping(value = "/getAllSources", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<GetAllAPISourcesResponse> editAPISource() {
        GetAllAPISourcesResponse response = importClient.getAllAPISources();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This endpoint will be use for uploading a file and saving the file to
     * a temporary directory such that it can be analyzed.
     * @param file This parameter will contain the file itself.
     * @param col1 This is the text/content
     * @param col2 This is the location/title
     * @param col3 This is the interactions/description
     * @param col4 This is the date
     * @param isSocial If the data is social media or articles
     * @return This contains if the request of uploading a file was successful or not.
     */
    @PostMapping("/upload")
    public ResponseEntity<ArrayList<ArrayList<Graph>>> fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("c1") String col1, @RequestParam("c2") String col2, @RequestParam("c3") String col3, @RequestParam("c4") String col4, @RequestParam boolean isSocial) {
        Map<String, String> response = new HashMap<>();
        ArrayList<ArrayList<Graph>> outputData = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        assert extension != null;
        if(!extension.equals("csv")) {
            response.put("message", "Incorrect file type uploaded.");
            return new ResponseEntity<>(outputData, HttpStatus.NOT_ACCEPTABLE);
        }
        ArrayList<ParsedData> socialMediaData = new ArrayList<>();
        ArrayList<ParsedArticle> newsData = new ArrayList<>();
        try {
            String filename = storageService.store(file);
            //response.put("message", "Successfully saved file");
            log.info("[Gateway API] Successfully saved file");
            log.info("[Gateway API] Running parser");
            //log.info(file.getOriginalFilename());
            if(isSocial) {
                ParseUploadedSocialDataResponse response1 = parseClient.parseUploadedSocialData(new ParseUploadedSocialDataRequest(filename, col1, col2, col3, col4));
                socialMediaData = response1.getSocialDataList();
                if(response1.isSuccess()) {
                    response.put("success", "true");
                }
                else {
                    response.put("success", "false");
                }
                response.put("message", response1.getMessage());
            }
            else {
                ParseUploadedNewsDataResponse response1 = parseClient.parseUploadedNewsData(new ParseUploadedNewsDataRequest(filename, col1, col2, col3, col4));
                newsData = response1.getNewsDataList();
                if(response1.isSuccess()) {
                    response.put("success", "true");
                }
                else {
                    response.put("success", "false");
                }
                response.put("message", response1.getMessage());
            }

            if(storageService.deleteFile(filename)) {
                log.info("[Gateway API] Delete file: " + filename);
            }
            else {
                log.info("[Gateway API] Failed to delete file: " + filename);
            }

            log.info("[Gateway API] Successfully parsed. Attempting to analyze data");

            AnalyseDataRequest analyseRequest = new AnalyseDataRequest(socialMediaData, newsData);//    DataSource.TWITTER,ImportResponse. getJsonData());
            AnalyseDataResponse analyseResponse = analyseClient.analyzeData(analyseRequest);


            if(analyseResponse.getFallback() == true) {
                ErrorGraph errorGraph = new ErrorGraph();
                errorGraph.Error = analyseResponse.getFallbackMessage();

                ArrayList<Graph> data = new ArrayList<>();
                data.add(errorGraph);

                outputData.add( data);

                return new ResponseEntity<>(outputData,HttpStatus.OK);
            }



            System.out.println("***********************ANALYSE HAS BEEN DONE*************************");

            log.info("[Gateway API] Completed analysis. Preparing data for visualization");


            /*********************VISUALISE**********************/

            VisualizeDataRequest visualizeRequest = new VisualizeDataRequest(
                    analyseResponse.getPattenList(),
                    analyseResponse.getRelationshipList(),
                    analyseResponse.getPattenList(),
                    analyseResponse.getTrendList(),
                    analyseResponse.getAnomalyList(),
                    analyseResponse.getWordList());//    DataSource.TWITTER,ImportResponse. getJsonData());
            VisualizeDataResponse visualizeResponse = visualizeClient.visualizeData(visualizeRequest);


            if(visualizeResponse.getFallback() == true) {
                ErrorGraph errorGraph = new ErrorGraph();
                errorGraph.Error = analyseResponse.getFallbackMessage();

                ArrayList<Graph> data = new ArrayList<>();
                data.add(errorGraph);

                outputData.add( data);

                return new ResponseEntity<>(outputData,HttpStatus.OK);
            }

            System.out.println("***********************VISUALIZE HAS BEEN DONE*************************");
            log.info("[Gateway API] Visualize success");


            for(int i =0; i < visualizeResponse.outputData.size(); i++)
                outputData.add(visualizeResponse.outputData.get(i));
        }
        catch (Exception e) {
            e.printStackTrace();
            //response.put("message", e.getMessage());
        }

        return new ResponseEntity<>(outputData, HttpStatus.OK);
    }

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
    public ResponseEntity<ArrayList<ArrayList<Graph>>> init(@PathVariable String key, @RequestBody SearchRequest request) throws Exception {
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

            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = importResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************IMPORT HAS BEEN DONE*************************");



        /*********************PARSE*************************/

        ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, importResponse.getList().get(0).getData(), request.getPermission());
        ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);
        ArrayList<ParsedData> socialMediaData = parseResponse.getDataList();

        ParseImportedDataRequest parseRequestNews = new ParseImportedDataRequest(DataSource.NEWSARTICLE, importResponse.getList().get(1).getData(), request.getPermission());
        parseResponse = parseClient.parseImportedData(parseRequestNews);
        ArrayList<ParsedArticle> newsData = parseResponse.getArticleList();

        if(parseResponse.getFallback() == true) {
            //outputData.add(parseResponse.getFallbackMessage());
            //outputData.add();
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = parseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************PARSE HAS BEEN DONE*************************");



        /*********************ANALYSE*************************/

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(socialMediaData, newsData);//    DataSource.TWITTER,ImportResponse. getJsonData());
        AnalyseDataResponse analyseResponse = analyseClient.analyzeData(analyseRequest);


        if(analyseResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }



        System.out.println("***********************ANALYSE HAS BEEN DONE*************************");


        /*********************VISUALISE**********************/

        VisualizeDataRequest visualizeRequest = new VisualizeDataRequest(
                analyseResponse.getPattenList(),
                analyseResponse.getRelationshipList(),
                analyseResponse.getPattenList(),
                analyseResponse.getTrendList(),
                analyseResponse.getAnomalyList(),
                analyseResponse.getWordList());//    DataSource.TWITTER,ImportResponse. getJsonData());
        VisualizeDataResponse visualizeResponse = visualizeClient.visualizeData(visualizeRequest);


        if(visualizeResponse.getFallback() == true) {
            ErrorGraph errorGraph = new ErrorGraph();
            errorGraph.Error = analyseResponse.getFallbackMessage();

            ArrayList<Graph> data = new ArrayList<>();
            data.add(errorGraph);

            outputData.add( data);

            return new ResponseEntity<>(outputData,HttpStatus.OK);
        }

        System.out.println("***********************VISUALIZE HAS BEEN DONE*************************");


        for(int i =0; i < visualizeResponse.outputData.size(); i++)
            outputData.add(visualizeResponse.outputData.get(i));

        return new ResponseEntity<>(outputData,HttpStatus.OK);

    }


    @GetMapping(value = "/collect/{key}/{from}/{to}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<String> collectDatedData(@PathVariable String key, @PathVariable String from, @PathVariable String to){


        ImportTwitterResponse res = importClient.importDatedData(new ImportTwitterRequest(key, from, to));

        if(!res.getFallback()){
            System.out.println(".........................Import completed successfully..................\n\n\n");


            ParseImportedDataRequest parseRequest = new ParseImportedDataRequest(DataSource.TWITTER, res.getJsonData(), "VIEWING");
            ParseImportedDataResponse parseResponse = parseClient.parseImportedData(parseRequest);

            if(!parseResponse.getFallback()) {
                System.out.println("........................Parsed Data Successfully...........................\n\n\n");
                return new ResponseEntity<>("{ \n \"success\" : true \n}",HttpStatus.OK);
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

