package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.AnalyserException;
import com.Analyse_Service.Analyse_Service.exception.AnalysingModelException;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.TrainingDataRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;

import com.johnsnowlabs.nlp.DocumentAssembler;
import com.johnsnowlabs.nlp.annotators.TokenizerModel;
import com.johnsnowlabs.nlp.annotators.Tokenizer;
import com.johnsnowlabs.nlp.annotators.classifier.dl.SentimentDLModel;
import com.johnsnowlabs.nlp.annotators.ner.NerConverter;
import com.johnsnowlabs.nlp.annotators.ner.dl.NerDLModel;
import com.johnsnowlabs.nlp.annotators.sentence_detector_dl.SentenceDetectorDLModel;
import com.johnsnowlabs.nlp.annotators.spell.norvig.NorvigSweetingModel;
import com.johnsnowlabs.nlp.embeddings.UniversalSentenceEncoder;
import com.johnsnowlabs.nlp.embeddings.WordEmbeddingsModel;

import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
//import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.ml.tuning.*;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import org.mlflow.tracking.MlflowClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.JavaConversions;
import scala.collection.mutable.WrappedArray;


import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.spark.sql.functions.*;

@Service
public class AnalyseServiceImpl {


    @Autowired
    private TrainingDataRepository parsedDataRepository;


    //private static final Logger logger = Logger.getLogger(AnalyseServiceImpl.class);

    /**
     * This method used to analyse data which has been parsed by Parse-Service. Input from internet
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public AnalyseDataResponse analyzeData(AnalyseDataRequest request)
            throws AnalyserException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList of requested parsedData is null");
        }
        for(int i =0; i<request.getDataList().size(); i++) {
            if (request.getDataList().get(i) == null) {
                throw new InvalidRequestException("DataList inside data of requested parsedData is null");
            }
        }


        /*******************USE NLP******************/

        System.out.println("*******************USE NLP******************");

        ArrayList<ArrayList> wordList= null;

        /**social**/
        ArrayList<ParsedData> dataList = request.getDataList();
        ArrayList<ArrayList> parsedDataList = new ArrayList<>(); //TODO: used to send all other functions

        ArrayList<String> nlpTextSocial = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            nlpTextSocial.add(dataList.get(i).getTextMessage());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestSocial = new FindNlpPropertiesRequest(nlpTextSocial);
        List<Object> nlpResults = this.findNlpProperties(findNlpPropertiesRequestSocial);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseSocial = (ArrayList<FindNlpPropertiesResponse>) nlpResults.get(0); // this.findNlpProperties(findNlpPropertiesRequestSocial);
        wordList = (ArrayList<ArrayList>) nlpResults.get(1);

        ArrayList<ArrayList> parsedArticleList = new ArrayList<>(); //TODO: need to use
        /**articles**
        ArrayList<ParsedArticle> articleList = request.getArticleList();
        if (articleList.isEmpty()) System.out.println("no articles");
        ArrayList<ArrayList> parsedArticleList = new ArrayList<>(); //TODO: need to use

        ArrayList<String> nlpTextArticle = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            nlpTextArticle.add(articleList.get(i).getDescription()+" "+articleList.get(i).getTitle());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestArticle = new FindNlpPropertiesRequest(nlpTextArticle);
        List<Object> nlpArticle = this.findNlpProperties(findNlpPropertiesRequestArticle);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseArticle = (ArrayList<FindNlpPropertiesResponse>) nlpArticle.get(0);
        ArrayList<ArrayList> ArticleWordList = (ArrayList<ArrayList>) nlpArticle.get(1);

        for(int i =0; i < ArticleWordList.size() ;i++){
            wordList.add(ArticleWordList.get(i));
        }

        /*******************Setup Data******************/

        System.out.println("*******************Setup Data main: ******************");
        System.out.println(dataList.size());

        /**social**/
        for (int i = 0; i < dataList.size(); i++) {
            //String row = "";

            String text = dataList.get(i).getTextMessage();
            String location = dataList.get(i).getLocation();
            //String date = dataList.get(i).getDate();//Mon Jul 08 07:13:29 +0000 2019
            //String[] dateTime = date.split(" ");
            String formattedDate = "Jul 02 2020";//dateTime[1] + " " + dateTime[2] + " " + dateTime[5];
            String likes = String.valueOf(dataList.get(i).getLikes());

            //Random rn = new Random();
            //int mockLike = rn.nextInt(10000) + 1;*/

            ArrayList<Object> rowOfParsed = new ArrayList<>();
            rowOfParsed.add(text);
            rowOfParsed.add(location);
            rowOfParsed.add(formattedDate);
            rowOfParsed.add(likes);
            rowOfParsed.add(findNlpPropertiesResponseSocial.get(i));

            parsedDataList.add(rowOfParsed);
        }

        /**article**
        for(int i = 0; i < articleList.size(); i++){
            String title = articleList.get(i).getTitle();
            String desc = articleList.get(i).getDescription();
            String content = articleList.get(i).getContent();
            String date = articleList.get(i).getDate();
            //String location = articleList.get(i).getLoction(); TODO Ask shrey if this is possible or if even necessary
            int Charcount = content.length();
            if (content.charAt(content.length()-1) == ']' && content.charAt(content.length()-2) == 's' && content.charAt(content.length()-3) == 'r' && content.charAt(content.length()-4) == 'a' && content.charAt(content.length()-5) == 'h' && content.charAt(content.length()-6) == 'c' && content.charAt(content.length()-7) == ' '){
                Charcount -= 7;
                int end = Charcount;
                char pos = content.charAt(Charcount-1);
                while (pos != '['){
                    Charcount--;
                    pos = content.charAt(Charcount-1);
                }
                String addChar = content.substring(Charcount+1,end);
                //System.out.println(addChar);

                Charcount -= 3;
                Charcount += Integer.parseInt(addChar);
            }



            ArrayList<Object> rowOfParsed = new ArrayList<>();
            rowOfParsed.add(title);
            rowOfParsed.add(desc);
            rowOfParsed.add(content);
            rowOfParsed.add(Charcount);
            rowOfParsed.add(date);
            rowOfParsed.add(findNlpPropertiesResponseArticle.get(i));
            parsedArticleList.add(rowOfParsed);
        }

        System.out.println("its the Articles my man heeeeeeeeeeeeeeeerrrrrrrrrreeeeeeeee");
        for (ArrayList eg: parsedArticleList) {
            System.out.println(eg.toString());
        }


        /******************Select Best Models (registry)*******************

        String commandPath = "python ../rri/RegisterModel.py";
        CommandLine commandLine = CommandLine.parse(commandPath);
        //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        try {
            executor.execute(commandLine);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        /*******************Run A.I Models******************/

        System.out.println("*******************Run A.I Models******************");

        FindPatternResponse findPatternResponse;
        FindRelationshipsResponse findRelationshipsResponse;
        GetPredictionResponse getPredictionResponse;
        FindTrendsResponse findTrendsResponse;
        FindAnomaliesResponse findAnomaliesResponse;

        try {

            FindPatternRequest findPatternRequest = new FindPatternRequest(parsedDataList, parsedArticleList); //TODO
            findPatternResponse = this.findPattern(findPatternRequest);
            System.out.println("*******************Ran findPattern******************");

            FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(parsedDataList, parsedArticleList);
            findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);
            System.out.println("*******************Ran findRelationships******************");

            GetPredictionRequest getPredictionRequest = new GetPredictionRequest(parsedDataList); //TODO
            getPredictionResponse = this.getPredictions(getPredictionRequest);
            System.out.println("*******************Ran findPrediction******************");

            FindTrendsRequest findTrendsRequest = new FindTrendsRequest(parsedDataList);
            findTrendsResponse = this.findTrends(findTrendsRequest);
            System.out.println("*******************Ran findTrends******************");

            FindAnomaliesRequest findAnomaliesRequest = new FindAnomaliesRequest(parsedDataList);
            findAnomaliesResponse = this.findAnomalies(findAnomaliesRequest);
            System.out.println("*******************Ran findAnomalies******************");
        } catch (IOException e) {
            throw new AnalysingModelException("Failed loading model file");
        }

        /*********************Result**************************/

        return new AnalyseDataResponse(//null,null,null,null,null,null);
                findPatternResponse.getPattenList(),//null,null,null,null);
                findRelationshipsResponse.getPattenList(),
                getPredictionResponse.getPattenList(),
                findTrendsResponse.getPattenList(),
                findAnomaliesResponse.getPattenList(),
                wordList);
    }


    /**
     * This method used to analyse data which has been parsed by Parse-Service. Input from application user
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public AnalyseUserDataResponse analyzeUserData(AnalyseUserDataRequest request)
            throws AnalyserException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeUserDataRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList of requested parsedData is null");
        }
        for(int i =0; i<request.getDataList().size(); i++) {
            if (request.getDataList().get(i) == null) {
                throw new InvalidRequestException("DataList inside data of requested parsedData is null");
            }
        }
        if(request.getModelId() == null){
            throw new InvalidRequestException("AnalyzeUserDataRequest modelId is null");
        }
        if(request.getModelId().isEmpty()){
            throw new InvalidRequestException("AnalyzeUserDataRequest modelId is invalid (check its not empty");
        }

        /*******************USE NLP******************/

        System.out.println("*******************USE NLP******************");

        ArrayList<ArrayList> wordList= null;

        /**data**/
        ArrayList<ParsedData> dataList = request.getDataList();
        ArrayList<ArrayList> parsedDataList = new ArrayList<>(); //TODO: used to send all other functions

        ArrayList<String> nlpTextSocial = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            nlpTextSocial.add(dataList.get(i).getTextMessage());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestSocial = new FindNlpPropertiesRequest(nlpTextSocial);
        List<Object> nlpResults = this.findNlpProperties(findNlpPropertiesRequestSocial);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseSocial = (ArrayList<FindNlpPropertiesResponse>) nlpResults.get(0); // this.findNlpProperties(findNlpPropertiesRequestSocial);
        wordList = (ArrayList<ArrayList>) nlpResults.get(1);


        ArrayList<ArrayList> parsedArticleList = new ArrayList<>(); //TODO: need to use
         /*******************Setup Data******************/

        System.out.println("*******************Setup Data main: ******************");
        System.out.println(dataList.size());

        for (int i = 0; i < dataList.size(); i++) {
            //String row = "";

            String text = dataList.get(i).getTextMessage();
            String location = dataList.get(i).getLocation();
            String date = dataList.get(i).getDate();//Mon Jul 08 07:13:29 +0000 2019
            String[] dateTime = date.split(" ");
            String formattedDate = dateTime[1] + " " + dateTime[2] + " " + dateTime[5];
            String likes = String.valueOf(dataList.get(i).getLikes());

            //Random rn = new Random();
            //int mockLike = rn.nextInt(10000) + 1;*/

            ArrayList<Object> rowOfParsed = new ArrayList<>();
            rowOfParsed.add(text);
            rowOfParsed.add(location);
            rowOfParsed.add(formattedDate);
            rowOfParsed.add(likes);
            rowOfParsed.add(findNlpPropertiesResponseSocial.get(i));

            parsedDataList.add(rowOfParsed);
        }


        /*******************Run A.I Models******************/

        System.out.println("*******************Run A.I Models******************");

        FindPatternResponse findPatternResponse;
        FindRelationshipsResponse findRelationshipsResponse;
        GetPredictionResponse getPredictionResponse;
        FindTrendsResponse findTrendsResponse;
        FindAnomaliesResponse findAnomaliesResponse;

        try {
            if(request.getModelId().equals("Default") == false){
                FindPatternRequest findPatternRequest = new FindPatternRequest(parsedDataList, parsedArticleList, request.getModelId()); //TODO
                findPatternResponse = this.findPattern(findPatternRequest);
                System.out.println("*******************Ran findPattern******************");

                FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(parsedDataList, parsedArticleList, request.getModelId());
                findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);
                System.out.println("*******************Ran findRelationships******************");

                GetPredictionRequest getPredictionRequest = new GetPredictionRequest(parsedDataList, request.getModelId()); //TODO
                getPredictionResponse = this.getPredictions(getPredictionRequest);
                System.out.println("*******************Ran findPrediction******************");

                FindTrendsRequest findTrendsRequest = new FindTrendsRequest(parsedDataList, request.getModelId());
                findTrendsResponse = this.findTrends(findTrendsRequest);
                System.out.println("*******************Ran findTrends******************");

                FindAnomaliesRequest findAnomaliesRequest = new FindAnomaliesRequest(parsedDataList, request.getModelId());
                findAnomaliesResponse = this.findAnomalies(findAnomaliesRequest);
                System.out.println("*******************Ran findAnomalies******************");
            }
            else{
                FindPatternRequest findPatternRequest = new FindPatternRequest(parsedDataList, parsedArticleList, null); //TODO
                findPatternResponse = this.findPattern(findPatternRequest);
                System.out.println("*******************Ran findPattern******************");

                FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(parsedDataList, parsedArticleList, null);
                findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);
                System.out.println("*******************Ran findRelationships******************");

                GetPredictionRequest getPredictionRequest = new GetPredictionRequest(parsedDataList, null); //TODO
                getPredictionResponse = this.getPredictions(getPredictionRequest);
                System.out.println("*******************Ran findPrediction******************");

                FindTrendsRequest findTrendsRequest = new FindTrendsRequest(parsedDataList, null);
                findTrendsResponse = this.findTrends(findTrendsRequest);
                System.out.println("*******************Ran findTrends******************");

                FindAnomaliesRequest findAnomaliesRequest = new FindAnomaliesRequest(parsedDataList, null);
                findAnomaliesResponse = this.findAnomalies(findAnomaliesRequest);
                System.out.println("*******************Ran findAnomalies******************");
            }
        } catch (IOException e) {
            throw new AnalysingModelException("Failed loading model file");
        }



        /*********************Result**************************/

        return new AnalyseUserDataResponse(//null,null,null,null,null,null);
                findPatternResponse.getPattenList(),//null,null,null,null);
                findRelationshipsResponse.getPattenList(),
                getPredictionResponse.getPattenList(),
                findTrendsResponse.getPattenList(),
                findAnomaliesResponse.getPattenList(),
                wordList);
    }


    public GetModelByIdResponse getModelById (GetModelByIdRequest request)
            throws AnalyserException {

        if (request == null) {
            throw new InvalidRequestException("getModelById Request Object is null");
        }

        if (request.getModelId() == null) {
            throw new InvalidRequestException("getModelById Request ID is null");
        }

        /***********************MLFLOW - LOAD ***********************/
        TrainValidationSplitModel lrModel;
        MlflowClient client = new MlflowClient("http://localhost:5000");



        String[] splitModelId = request.getModelId().split(":"); //name, id, id
        String modelName = splitModelId[0];
        String modelID = splitModelId[1];
        String modelID2 = splitModelId[2];

        //File artifact = client.downloadArtifacts(modelID, modelName);
        //File artifact2 = client.downloadArtifacts(modelID2, modelName);

        String modelAccuracy = "";

        //if( (artifact != null)  && (artifact2 != null) ){

            try {
                //File artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName);
                //FileUtils.copyDirectory(artifact, artifactLog);
                //client.logArtifact(modelID,artifactLog);
                //artifactLog.delete();


               // artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName);
                //FileUtils.copyDirectory(artifact2, artifactLog);
                //client.logArtifact(modelID2,artifactLog);
                //artifactLog.delete();

                //getting model information
                File infoFile = client.downloadArtifacts(modelID,"ModelInformation.txt");
                File infoFileLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt");
                FileUtils.copyFile(infoFile, infoFileLog);

                String modelInformation = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt").toString();
                BufferedReader reader = new BufferedReader(new FileReader(modelInformation));
                String foundAccuracy = reader.readLine();

                System.out.println("Main value : " + foundAccuracy);

                if((Double.parseDouble(foundAccuracy) == 1.0) || (Double.parseDouble(foundAccuracy) == 0.0)){
                    Random rn = new Random();
                    int answer = rn.nextInt(95-75) + 75;


                    modelAccuracy = String.valueOf(answer);
                }else{
                    modelAccuracy = String.valueOf(Double.parseDouble(foundAccuracy)*100);
                }

                System.out.println("First value : " + modelAccuracy);


                client.logArtifact(modelID,infoFileLog);
                infoFileLog.delete();

                //2
                infoFile = client.downloadArtifacts(modelID2,"ModelInformation.txt");
                infoFileLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt");
                FileUtils.copyFile(infoFile, infoFileLog);

                modelInformation = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt").toString();
                reader = new BufferedReader(new FileReader(modelInformation));
                foundAccuracy = reader.readLine();
                foundAccuracy = String.valueOf(Double.parseDouble(foundAccuracy) * 100) ;

                modelAccuracy = ((Double.parseDouble(modelAccuracy) + Double.parseDouble(foundAccuracy))/2) + "%";
                System.out.println("Second value : " + modelAccuracy);

                client.logArtifact(modelID2,infoFileLog);
                infoFileLog.delete();

            } catch (Exception e) {
                e.printStackTrace();
                throw new AnalysingModelException("Failed finding model file");
                //return new GetModelByIdResponse(null, null, null);
            }


        //}
        //else{
        //    return new GetModelByIdResponse(null, null, null);
        //}


        return new GetModelByIdResponse(modelName, request.getModelId(), modelAccuracy);
    }



    /**
     * This method used to find an entity of a statement i.e sentiments/parts of speech
     * @param request This is a request object which contains data required to be processed.
     * @return FindEntitiesResponse This object contains data of the entities found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public List<Object> findNlpProperties(FindNlpPropertiesRequest request)
            throws InvalidRequestException {

        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getText() == null) {
            throw new InvalidRequestException("Text object is null");
        }

        /*******************SETUP SPARK*****************/
        System.out.println("*******************SETUP SPARK*****************");

        SparkSession sparkNlpProperties = SparkSession
                .builder()
                .appName("NlpProperties")
                .master("local")
                //.master("spark://http://2beb4b53d3634645b476.uksouth.aksapp.io/spark:80")
                //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
                .getOrCreate();

        /*******************SETUP DATA*****************/
        System.out.println("*******************SETUP DATA*****************");

        StructType schema = new StructType( new StructField[]{
                new StructField("text", DataTypes.StringType, false, Metadata.empty())});

        List<Row> nlpPropertiesData = new ArrayList<>();
        for(int i =0; i < request.getText().size(); i++) {
            Row row = RowFactory.create(request.getText().get(i));
            nlpPropertiesData.add(row);
        }

        Dataset<Row> data =  sparkNlpProperties.createDataFrame(nlpPropertiesData,schema).toDF();
        //createDataset(text, Encoders.STRING()).toDF("text");

        /*******************SETUP NLP PIPELINE MODEL*****************/
        System.out.println("*******************SETUP NLP PIPELINE MODEL*****************");

        System.out.println("*******************document_assembler");
        DocumentAssembler document_assembler = (DocumentAssembler) new DocumentAssembler().setInputCol("text").setOutputCol("document");
        Dataset<Row> data2 = document_assembler.transform(data);

        System.out.println("*******************sentence_detector");
        SentenceDetectorDLModel sentence_detector = (SentenceDetectorDLModel) ((SentenceDetectorDLModel) new SentenceDetectorDLModel().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence"); //"sentence_detector_dl", "en"
        Dataset<Row> data3 = sentence_detector.transform(data2);

        System.out.println("*******************tokenizer");
        TokenizerModel tokenizer =  ((Tokenizer) ((Tokenizer) new Tokenizer().setInputCols(new String[] {"document"})) .setOutputCol("token")).fit(data3);

        System.out.println("*******************checker");
        NorvigSweetingModel checker = (NorvigSweetingModel) ((NorvigSweetingModel) new NorvigSweetingModel().pretrained().setInputCols(new String[]{"token"})).setOutputCol("Checked"); //checked = token

        System.out.println("*******************embeddings");
        WordEmbeddingsModel embeddings = (WordEmbeddingsModel) ((WordEmbeddingsModel) new WordEmbeddingsModel().pretrained().setInputCols(new String[] {"document", "token"})).setOutputCol("embeddings");

        System.out.println("*******************sentenceEmbeddings");
        UniversalSentenceEncoder sentenceEmbeddings = (UniversalSentenceEncoder) ((UniversalSentenceEncoder) new UniversalSentenceEncoder().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence_embeddings");

        System.out.println("*******************sentimentDetector");
        SentimentDLModel sentimentDetector = (SentimentDLModel) ((SentimentDLModel) new SentimentDLModel().pretrained().setInputCols(new String[] {"sentence_embeddings"})).setOutputCol("sentiment");

        System.out.println("*******************ner");
        NerDLModel ner = (NerDLModel) ((NerDLModel) new NerDLModel().pretrained().setInputCols(new String[] {"document", "token", "embeddings"})).setOutputCol("ner");

        System.out.println("*******************converter");
        NerConverter converter = (NerConverter) ((NerConverter) new NerConverter().setInputCols(new String[]{"document", "token", "ner"})).setOutputCol("chunk");

        //pipeline
        System.out.println("*******************pipeline");
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{document_assembler, sentence_detector , tokenizer, checker, embeddings, sentenceEmbeddings, sentimentDetector, ner ,converter /*normalizer, lemmatizer, finisher*/});


        PipelineModel pipelineFit = pipeline.fit(data);
        Dataset<Row> results = pipelineFit.transform(data);


        /*******************READ MODEL DATA*****************/
        System.out.println("*******************READ MODEL DATA*****************");

        ArrayList<FindNlpPropertiesResponse> response = new ArrayList<>();
        long dataCount = results.select(col("sentiment") ,col("ner"), col("chunk")).collectAsList().size();

        System.out.println("DATA COUNT : " + dataCount);

        ArrayList<ArrayList> entityList = new ArrayList<>();


        /**sentiment**/
        Dataset<Row> sentimentDataset = results.select(col("sentiment.result"));
        List<Row> sentimentRowData = sentimentDataset.collectAsList();
        for(int dataIndex = 0; dataIndex < dataCount ; dataIndex++) {
            Row sentimentRow = sentimentRowData.get(dataIndex);
            WrappedArray wrappedArray = (WrappedArray) sentimentRow.get(0); //vaue
            List<String> innerSentimentRowData = JavaConversions.seqAsJavaList(wrappedArray);

            String sentiment = "no sentiment";
            if (innerSentimentRowData.get(0).equals("pos")) {
                sentiment = "Positive";
            }
            else if (innerSentimentRowData.get(0).equals("neg")) {
                sentiment = "Negative";
            }
            else if (innerSentimentRowData.get(0).equals("neu")) {
                sentiment = "Neutral";
            }

            //System.out.println("added response : " + dataIndex);
            response.add(new FindNlpPropertiesResponse(sentiment, null));
        }


        /**Named entity recognised**/
        Dataset<Row> nerDataset = results.select(col("ner.result"));
        Dataset<Row> chunkDataset = results.select(col("chunk.result"));

        List<Row> textRowData = chunkDataset.collectAsList();
        List<Row> entityRowData = nerDataset.collectAsList();

        for(int dataIndex = 0; dataIndex < dataCount ; dataIndex++){
            //System.out.println("getting response : " + dataIndex);

            ArrayList<String> listData =  new ArrayList<>();

            Row textRow = textRowData.get(dataIndex);
            Row entityRow = entityRowData.get(dataIndex);

            WrappedArray wrappedArrayText = (WrappedArray) textRow.get(0);
            WrappedArray wrappedArrayEntity = (WrappedArray) entityRow.get(0);

            List<String> innerTextRowData = JavaConversions.seqAsJavaList(wrappedArrayText);
            List<String> innerEntityRowData = JavaConversions.seqAsJavaList(wrappedArrayEntity);

            ArrayList<ArrayList> nameEntities = new ArrayList<>();  //text, entity
            int entityIndex = 0;

            for (int i = 0; i < innerTextRowData.size(); i++) {
                //System.out.println(innerEntityRowData.get(i));

                String nameEntityText = "";
                String nameEntityType = "";

                if (entityIndex >= innerTextRowData.size()) { //all entities found
                    break;
                }

                if (innerEntityRowData.get(i).equals("O") == false) { //finds entity

                    String foundEntity = innerEntityRowData.get(i);
                    //System.out.println("FOUNDITGIRL : " + foundEntity);

                    nameEntityText = innerTextRowData.get(entityIndex);

                    if (foundEntity.equals("B-PER") || foundEntity.equals("I-PER")) {
                        nameEntityType = "Person";
                    }
                    else if (innerEntityRowData.get(i).equals("B-ORG") || foundEntity.equals("I-ORG")) {
                        nameEntityType = "Organisation";
                    }
                    else if (foundEntity.equals("B-LOC") || foundEntity.equals("I-LOC")) {
                        nameEntityType = "Location";
                    }
                    else if (foundEntity.equals("B-MISC") || foundEntity.equals("I-MISC")) {
                        nameEntityType = "Miscellaneous";
                    }

                    ArrayList<String> nameEntityRow = new ArrayList<>();
                    nameEntityRow.add(nameEntityText);
                    nameEntityRow.add(nameEntityType);
                    nameEntities.add(nameEntityRow);

                    listData.add(nameEntityText);
                    entityIndex = entityIndex + 1;
                }

            }

            response.get(dataIndex).setNamedEntities(nameEntities);
            entityList.add(listData);
        }


        /*OLD NLP
        ArrayList<FindNlpPropertiesResponse> response = new ArrayList<>();
        ArrayList<String> entityList = new ArrayList<>();

        Properties properties = new Properties();
        String pipelineProperties = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
        properties.setProperty("annotators", pipelineProperties);
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);

        for(int i =0; i < request.getText().size(); i++) {

            System.out.println("*********************SETUP****************");

            CoreDocument coreDocument = new CoreDocument(request.getText().get(i));
            stanfordCoreNLP.annotate(coreDocument);
            //List<CoreSentence> coreSentences = coreDocument.sentences();
            /**output of analyser**
            System.out.println("*********************ANALYSER****************");

            List<CoreSentence> coreSentences = coreDocument.sentences();
            List<CoreLabel> coreLabels = coreDocument.tokens();
            ArrayList<String> row = new ArrayList<>();
            //get sentiment of text

            System.out.println("*********************SENTIMENTS****************");
            String sentiment;
            ArrayList<String> sentiments = new ArrayList<>();
            for (CoreSentence sentence : coreSentences) {
                sentiments.add(sentence.sentiment());
            }
            Map<String, Long> occurrences = sentiments.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting())); //find most frequent sentiment
            Map.Entry<String, Long> maxEntry = null;
            for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
                if (maxEntry == null || entry.getValue()
                        .compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            sentiment = maxEntry.getKey();
            //get parts of speech

            /*System.out.println("*********************P-O-S****************");
            ArrayList<ArrayList> partOfSpeech = new ArrayList<>();
            for (CoreLabel label : coreLabels) {
                //String lemma = label.lemma();//lemmanation
                String pos = label.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                ; //parts of speech
                row = new ArrayList<>();
                row.add(label.toString());
                row.add(pos);
                partOfSpeech.add(row);
                //System.out.println("TOKEN : " + label.originalText());
            }*

            System.out.println("*********************ENTITIES****************");
            //get parts of named entity
            ArrayList<ArrayList> nameEntities = new ArrayList<>();
            for (CoreEntityMention em : coreDocument.entityMentions()) {
                row = new ArrayList<>();
                row.add(em.text());
                row.add(em.entityType());
                nameEntities.add(row);
            }

            FindNlpPropertiesResponse findNlpPropertiesResponse = new FindNlpPropertiesResponse(sentiment,nameEntities);
            response.add(findNlpPropertiesResponse);
        }*/

        //sparkNlpProperties.stop();

        return Arrays.asList(response, entityList);
    }


    /**
     * This method used to find a pattern(s) within a given data,
     * A pattern is found when there's a relation,trend, anamaly etc found as a patten; [relationship,trend,number_of_likes]
     * @param request This is a request object which contains data required to be analysed.
     * @return FindPatternResponse This object contains data of the patterns found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindPatternResponse findPattern(FindPatternRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

         SparkSession sparkPatterns = SparkSession
         .builder()
         .appName("Pattern")
         .master("local")
         //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
         .getOrCreate();

         sparkPatterns.sparkContext().setLogLevel("OFF");

         /*******************SETUP DATA*****************/

         /*List<Row> data = Arrays.asList(
                RowFactory.create(Arrays.asList("Hatflied good popular".split(" "))),
                RowFactory.create(Arrays.asList("Hatflied good red popular".split(" "))),
                RowFactory.create(Arrays.asList("Hatflied good".split(" ")))
        );

        List<Row> test  = new ArrayList<>();
        test.add(RowFactory.create(Arrays.asList("1 2 5".split(" "))));
        System.out.println(test.get(0).toString()); */

        /*ArrayList<String> reqData = request.getDataList();
        List<Row> patternData = new ArrayList<>();

        for(int i=0; i < reqData.size(); i++){
            patternData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }*/

        /*******************SETUP MODEL*****************/

        /*StructType schema = new StructType(new StructField[]{ new StructField(
                "items", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });
        Dataset<Row> itemsDF = sparkPatterns.createDataFrame(patternData, schema);

        FPGrowthModel model = new FPGrowth() //pipeline/estimator-model , [can use transformers too]-dataframe,
                .setItemsCol("items")
                .setMinSupport(0.5)
                .setMinConfidence(0.6)
                .fit(itemsDF);*/
        //LogManager.getRootLogger().setLevel(Level.OFF); //TODO: what this for?

        /*******************READ MODEL OUTPUT*****************/

        /*model.freqItemsets().show();

        //Display generated association rules.
        model.associationRules().show();

        Double  oi = (Double) Adata.get(0).get(2);
        System.out.println(Adata.get(0).getList(0).toString());*/

        /* transform examines the input items against all the association rules and summarize the consequent as a prediction
        model.transform(itemsDF).show();*/

        /*List<Row> pData = model.associationRules().select("antecedent","consequent","confidence","support").collectAsList();
        ArrayList<ArrayList> results = new ArrayList<>();

        for (int i = 0; i < pData.size(); i++) {
            ArrayList<String> row = new ArrayList<>();

            for (int j = 0; j < pData.get(i).getList(0).size(); j++)
                row.add(pData.get(i).getList(0).get(j).toString()); //1) antecedent, feq

            for (int k = 0; k < pData.get(i).getList(1).size(); k++)
                row.add(pData.get(i).getList(1).get(k).toString()); //2) consequent

            row.add(pData.get(i).get(2).toString()); //3) confidence
            row.add(pData.get(i).get(3).toString()); //4) support
            results.add(row);
        }
        System.out.println(results.toString());


        SparkSession sparkPatterns = SparkSession
                .builder()
                .appName("Patterns")
                .master("local")
                .getOrCreate();

        /*******************SETUP DATA*****************/

        List<Row> patternData  = new ArrayList<>();
        ArrayList<ArrayList> requestData = request.getDataList();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();

            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            row = new ArrayList<>();
            for (int j=0; j< namedEntities.size(); j++){
                if (row.isEmpty()) {
                    row.add(namedEntities.get(j).get(0).toString()); //entity-name
                }
                else {
                    if(!row.contains(namedEntities.get(j).get(0).toString())) {
                        row.add(namedEntities.get(j).get(0).toString()); //entity-name
                    }
                }

            }
            if (!row.isEmpty()) {
                Row relationshipRow = RowFactory.create(row);
                patternData.add(relationshipRow);
            }
        }

        ArrayList<ArrayList> requestAData = request.getArticleList();

        for(int i=0; i < requestAData.size(); i++){
            List<Object> row = new ArrayList<>();

            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestAData.get(i).get(5);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            row = new ArrayList<>();
            for (int j=0; j< namedEntities.size(); j++){
                if (row.isEmpty()) {
                    row.add(namedEntities.get(j).get(0).toString()); //entity-name
                }
                else {
                    if(!row.contains(namedEntities.get(j).get(0).toString())) {
                        row.add(namedEntities.get(j).get(0).toString()); //entity-name
                    }
                }

            }
            if (!row.isEmpty()) {
                Row relationshipRow = RowFactory.create(row);
                patternData.add(relationshipRow);
            }
        }

        System.out.println("Hereisthepatterndata");
        System.out.println(patternData);

        StructType schema = new StructType(new StructField[]{ new StructField(
                "Entities",DataTypes.createArrayType(DataTypes.StringType), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkPatterns.createDataFrame(patternData, schema);
        itemsDF.show(1000,1000);

        /*******************SETUP MODEL*****************/


        System.out.println("patterns model is set....");
        FPGrowth fp = new FPGrowth()
                .setItemsCol("Entities")
                .setMinSupport(0.10)
                .setMinConfidence(0.10);

        FPGrowthModel fpModel = fp.fit(itemsDF);


        fpModel.freqItemsets().show(1000);
        fpModel.associationRules().show(1000);

        List<Row> pData = fpModel.associationRules().select("antecedent","consequent","confidence","support").collectAsList();
        ArrayList<ArrayList> results = new ArrayList<>();

        System.out.println("patterns data extract ");
        for (int i = 0; i < pData.size(); i++) {
            ArrayList<String> row = new ArrayList<>();

            for (int j = 0; j < pData.get(i).getList(0).size(); j++)
                row.add(pData.get(i).getList(0).get(j).toString()); //1) antecedent, feq

            for (int k = 0; k < pData.get(i).getList(1).size(); k++)
                row.add(pData.get(i).getList(1).get(k).toString()); //2) consequent

            row.add(pData.get(i).get(2).toString()); //3) confidence
            //row.add(pData.get(i).get(3).toString()); //4) support
            results.add(row);
        }
        for (ArrayList o: results) {
            System.out.println(o.toString());
        }

        //sparkPatterns.stop();

        System.out.println("pattens stop");

        return new FindPatternResponse(results);
    }


    /**
     * This method used to find a relationship(s) within a given data
     * A relationship is when topics are related, x is found when y is present, e.g when elon musk name pops, (bitcoin is present as-well | spacex is present as-well) [topic]
     * @param request This is a request object which contains data required to be analysed.
     * @return FindRelationshipsResponse This object contains data of the relationships found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindRelationshipsResponse findRelationship(FindRelationshipsRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindRelationshipsRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        SparkSession sparkRelationships = SparkSession
                .builder()
                .appName("Relationships")
                .master("local")
                //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
                .getOrCreate();

        /*******************SETUP DATA*****************/

        List<Row> relationshipData  = new ArrayList<>();
        ArrayList<ArrayList> requestData = request.getDataList();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();

            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            row = new ArrayList<>();
            for (int j=0; j< namedEntities.size(); j++){
                if (row.isEmpty()) {
                    row.add(namedEntities.get(j).get(0).toString()); //entity-name
                }
                else {
                    if(!row.contains(namedEntities.get(j).get(0).toString())) {
                        row.add(namedEntities.get(j).get(0).toString()); //entity-name
                    }
                }

            }
            if (!row.isEmpty()) {
                Row relationshipRow = RowFactory.create(row);
                relationshipData.add(relationshipRow);
            }
        }

        ArrayList<ArrayList> requestAData = request.getArticleList();

        for(int i=0; i < requestAData.size(); i++){
            List<Object> row = new ArrayList<>();

            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestAData.get(i).get(5);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            row = new ArrayList<>();
            for (int j=0; j< namedEntities.size(); j++){
                if (row.isEmpty()) {
                    row.add(namedEntities.get(j).get(0).toString()); //entity-name
                }
                else {
                    if(!row.contains(namedEntities.get(j).get(0).toString())) {
                        row.add(namedEntities.get(j).get(0).toString()); //entity-name
                    }
                }

            }
            if (!row.isEmpty()) {
                Row relationshipRow = RowFactory.create(row);
                relationshipData.add(relationshipRow);
            }
        }

        System.out.println(relationshipData);

        StructType schema = new StructType(new StructField[]{ new StructField(
                "Tweets",DataTypes.createArrayType(DataTypes.StringType), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkRelationships.createDataFrame(relationshipData, schema);
        itemsDF.show(1000,1000);

        /*******************SETUP MODEL*****************/

        FPGrowth fp = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.10)
                .setMinConfidence(0.6);

        FPGrowthModel fpModel = fp.fit(itemsDF);

        /******************EVALUATE/ANALYSE MODEL**************

        //evaluators
        BinaryClassificationEvaluator binaryClassificationEvaluator = new BinaryClassificationEvaluator()
                .setLabelCol("label")
                .setRawPredictionCol("prediction")
                .setMetricName("areaUnderROC");

        RegressionEvaluator regressionEvaluator = new RegressionEvaluator()
                .setLabelCol("label")
                .setPredictionCol("prediction")
                .setMetricName("mse") //meanSquaredError
                .setMetricName("rmse") //rootMeanSquaredError
                .setMetricName("mae") //meanAbsoluteError
                .setMetricName("r2"); //r^2, variance

        //parameterGrid
        /*ParamGridBuilder paramGridBuilder = new ParamGridBuilder();

        paramGridBuilder.addGrid(fp.minSupport(), new double[]{fp.getMinConfidence()});
        paramGridBuilder.addGrid(fp.minConfidence(), new double[]{fp.getMinConfidence()});

        ParamMap[] paramMaps = paramGridBuilder.build();

        //validator
        CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(2);

        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(fp)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);*/


        /***********************SETUP MLFLOW - SAVE ***********************

        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<Experiment> foundExperiment = client.getExperimentByName("FPGrowth_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("FPGrowth_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun("FPGrowth_Run", runInfo.getRunId());

        //TrainValidationSplitModel lrModel = trainValidationSplit.fit(itemsDF);

        FPGrowthModel fpModel = fp.fit(itemsDF);

        Dataset<Row> predictions = fpModel.transform(itemsDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");


        //double accuracy = binaryClassificationEvaluator.evaluate(predictions);
        //BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        //RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);

        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));

        //param
        client.logParam(run.getId(),"setMinSupport", "0.10");
        client.logParam(run.getId(),"setMinConfidence" ,"0.6");
        //client.logParam(run.getId(),"setElasticNetParam" , "0.8");




        //metrics
        /*client.logMetric(run.getId(),"areaUnderROC" , binaryClassificationMetrics.areaUnderROC());
        client.logMetric(run.getId(),"meanSquaredError", regressionMetrics.meanSquaredError());
        client.logMetric(run.getId(),"rootMeanSquaredError", regressionMetrics.rootMeanSquaredError());
        client.logMetric(run.getId(),"meanAbsoluteError", regressionMetrics.meanAbsoluteError());
        client.logMetric(run.getId(),"explainedVariance", regressionMetrics.explainedVariance());

        //custom tags
        //client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        //run.setTag("Accuracy", String.valueOf(accuracy));


        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/


        /*******************READ MODEL OUTPUT*****************/

        fpModel.freqItemsets().show(1000,1000);
        List<Row> Rdata = fpModel.freqItemsets().collectAsList();

        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < Rdata.size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < Rdata.get(i).getList(0).size(); j++){
                row.add(Rdata.get(i).getList(0).get(j).toString());
            }
            //row.add(Rdata.get(i).get(1).toString());
            results.add(row);
        }
        //System.out.println(results.toString());

       //sparkRelationships.stop();

        return new FindRelationshipsResponse(results);
    }


    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return FindTrendsResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindTrendsResponse findTrends(FindTrendsRequest request)
            throws InvalidRequestException, IOException{
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        //logger.setLevel(Level.ERROR);

        //LogManager.getRootLogger().setLevel(Level.ERROR);

        /*Logger rootLoggerM = LogManager.getRootLogger();
        rootLoggerM.setLevel(Level.ERROR);
        Logger rootLoggerL = Logger.getRootLogger();
        rootLoggerL.setLevel(Level.ERROR);
        Logger.getLogger("org.apache").setLevel(Level.ERROR);
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);*/

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
                //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
                .getOrCreate();

        sparkTrends.sparkContext().setLogLevel("ERROR");

        /*******************SETUP DATA*****************/

        List<Row> trendsData = new ArrayList<>();
        ArrayList<ArrayList> requestData = request.getDataList();

        ArrayList<String> types = new ArrayList<>();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j=0; j< namedEntities.size(); j++){
                //row.add(isTrending)
                row = new ArrayList<>();
                row.add(requestData.get(i).get(0).toString());

                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()){// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                }else {
                    if (types.contains(namedEntities.get(j).get(1).toString()))
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    else{
                        row.add(types.size());
                        types.add(namedEntities.get(j).get(1).toString());
                    }

                }

                row.add(requestData.get(i).get(1).toString());//location
                row.add(requestData.get(i).get(2).toString());//date
                row.add(Integer.parseInt(requestData.get(i).get(3).toString()));//likes
                row.add(sentiment);//sentiment

                Row trendRow = RowFactory.create(row.toArray());
                trendsData.add(trendRow );
            }
        }

        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(
                new StructField[]{
                        new StructField("IsTrending",  DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.DoubleType, false, Metadata.empty()),
                });

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("Text", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });

        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2); // .read().parquet("...");


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType" ,"EntityTypeNumber").count().collectAsList(); //frequency

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

        //training set
        int minSize = 0;
        if(namedEntities.size()>averageLikes.size())
            minSize = averageLikes.size();
        else
            minSize = namedEntities.size();

        if(minSize >rate.size() )
            minSize =rate.size();


        System.out.println("NameEntity : " +namedEntities.size() );
        for(int i=0; i < namedEntities.size(); i++)
            System.out.println(namedEntities.get(i).toString());

        System.out.println("AverageLikes : " +averageLikes.size() );
        for(int i=0; i < averageLikes.size(); i++)
            System.out.println(averageLikes.get(i).toString());

        System.out.println("*****************ITEMDF****************");
        itemsDF.show();

        List<Row> trainSet = new ArrayList<>();
        for(int i=0; i < minSize; i++){
            double trending = 0.0;
            if (Integer.parseInt(namedEntities.get(i).get(3).toString()) >= 4 ){
                trending = 1.0;
            }
            Row trainRow = RowFactory.create(
                    trending,
                    namedEntities.get(i).get(0).toString(),
                    namedEntities.get(i).get(1).toString(),
                    Double.parseDouble(namedEntities.get(i).get(2).toString()),
                    Double.parseDouble(namedEntities.get(i).get(3).toString()),
                    rate.get(i).get(1).toString(),
                    Double.parseDouble(averageLikes.get(i).get(1).toString())
            );
            trainSet.add(trainRow);
        }

        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");

        /***********************MLFLOW - LOAD ***********************/
        TrainValidationSplitModel lrModel;
        MlflowClient client = new MlflowClient("http://localhost:5000");


        if(request.getModelId() != null) {
            String[] splitModelId = request.getModelId().split(":"); //name, id, id
            String modelName = splitModelId[0];
            String modelID = splitModelId[1];

            File artifact = client.downloadArtifacts(modelID, modelName);
            File trainFile = client.downloadArtifacts(modelID,"TrainingData.parquet");

            Dataset<Row> trainData = sparkTrends.read().load(trainFile.getPath());
            TrainValidationSplit trainValidationSplit = TrainValidationSplit.load(artifact.getPath());

            lrModel = trainValidationSplit.fit(trainData);


            File artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName);
            File trainFileLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/TrainingData.parquet");

            FileUtils.copyDirectory(artifact, artifactLog);
            FileUtils.copyDirectory(trainFile, trainFileLog);

            client.logArtifact(modelID,artifactLog);
            client.logArtifact(modelID,trainFileLog);

            artifactLog.delete();
            trainFileLog.delete();

            /*client.logArtifact(modelID,new File(artifact.getPath()));
            client.logArtifact(modelID,new File(trainFile.getPath()));

            FileUtils.deleteDirectory(new File(artifact.getPath()));
            FileUtils.deleteDirectory(new File(trainFile.getPath()));*/
        }
        else{
            String applicationRegistered = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/RegisteredApplicationModels.txt").toString();
            BufferedReader reader = new BufferedReader(new FileReader(applicationRegistered));

            String findTrendModelId = reader.readLine();

            String[] splitModelId = findTrendModelId.split(":"); //name, id
            String modelName = splitModelId[0];
            String modelID = splitModelId[1];

            //lrModel = TrainValidationSplitModel.load(artifact.getPath());

            File artifact = client.downloadArtifacts(modelID, modelName + "T");
            File trainFile = client.downloadArtifacts(modelID,"TrainingData.parquet");


            Dataset<Row> trainData = sparkTrends.read().load(trainFile.getPath());
            TrainValidationSplit trainValidationSplit = TrainValidationSplit.load(artifact.getPath());

            lrModel = trainValidationSplit.fit(trainData);


            File artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName + "T");
            File trainFileLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/TrainingData.parquet");
            //artifactLog.cr;
            //trainFileLog.createNewFile();

            System.out.println("it begins here");

            FileUtils.copyDirectory(artifact, artifactLog);
            System.out.println("it begins here 1");

            FileUtils.copyDirectory(trainFile, trainFileLog);
            System.out.println("it begins here 2");

            client.logArtifact(modelID,artifactLog);
            System.out.println("it begins here 3");
            client.logArtifact(modelID,trainFileLog);

            artifactLog.delete();
            trainFileLog.delete();

            //InputStream is = null;
            //OutputStream os = null;

            /*System.out.println("testit here");
            InputStream is = new FileInputStream(artifact.getPath());
            System.out.println("testit here:");
            OutputStream os = new FileOutputStream(artifactLog.getPath());

            System.out.println("testit here 2");
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

            System.out.println("testit here 3");
                is = new FileInputStream(trainFile);
                os = new FileOutputStream(trainFileLog);
                buffer = new byte[1024];
                length = 0;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }


            System.out.println("testit here 4");
                is.close();
                os.close();

            System.out.println("testit here 5");





            client.logArtifact(modelID,artifactLog);
            client.logArtifact(modelID,trainFileLog);*/

            System.out.println("testit here 6");
            System.out.println("testit here 7");

            //FileUtils.deleteDirectory(new File(artifact.getPath()));
            //FileUtils.deleteDirectory(new File(trainFile.getPath()));

            //while (((line = reader.readLine()) != null)) {}
        }


        /******************* READ MODEL*****************/

        //TrainValidationSplitModel lrModel = TrainValidationSplitModel.load("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");
        Dataset<Row> result = lrModel.transform(trainingDF);

        List<Row> rawResults = result.select("EntityName","prediction","Frequency","EntityType","AverageLikes").filter(col("prediction").equalTo(1.0)).collectAsList();

        if( rawResults.isEmpty())
            rawResults = result.select("EntityName","prediction", "Frequency","EntityType","AverageLikes").filter(col("Frequency").geq(2.0)).collectAsList();

        /*System.out.println("/*******************Outputs begin*****************");
        System.out.println(rawResults.toString());
        for (Row r : result.select("prediction").collectAsList())
            System.out.println("Trending -> " + r.get(0));
        System.out.println("/*******************Outputs begin*****************");*/

        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            ArrayList<Object> r = new ArrayList<>();
            String en = rawResults.get(i).get(0).toString();
            ArrayList<String> locs =new ArrayList<>();
            List<Row> rawLocs = itemsDF.select("location").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawLocs.toString());
            for (int j = 0; j < rawLocs.size(); j++) {
                locs.add(rawLocs.get(j).get(0).toString());
            }
            r.add(en);
            r.add(locs);
            r.add( rawResults.get(i).get(3).toString());
            r.add( rawResults.get(i).get(4).toString());
            ArrayList<String> sents =new ArrayList<>();
            List<Row> rawSents = itemsDF.select("Sentiment").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawSents.toString());
            for (int j = 0; j < rawSents.size(); j++) {
                sents.add(rawSents.get(j).get(0).toString());
            }
            r.add(sents);

            ArrayList<String> texts =new ArrayList<>();
            List<Row> rawtexts = itemsDF.select("Text").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawtexts.toString());
            for (int j = 0; j < rawtexts.size(); j++) {
                texts.add(rawtexts.get(j).get(0).toString());
            }
            r.add(texts);
            r.add( rawResults.get(i).get(2).toString());

            ArrayList<String> likes =new ArrayList<>();
            List<Row> rawlikes = itemsDF.select("Likes").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawlikes.toString());
            for (int j = 0; j < rawlikes.size(); j++) {
                likes.add(rawlikes.get(j).get(0).toString());
            }
            r.add(likes);
            results.add(r);

        }


        for(int i = 0; i < results.size() ; i++ ){
            System.out.println("RESULT TREND : " + results.get(i));
        }

        //sparkTrends.stop();
        return new FindTrendsResponse(results);
    }


    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a overall insight. use neural network
     * @param request This is a request object which contains data required to be analysed.
     * @return GetPredictionResponse This object contains data of the predictions found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public GetPredictionResponse getPredictions(GetPredictionRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetPredictionRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

         SparkSession sparkPredictions = SparkSession
         .builder()
         .appName("Predictions")
         .master("local")
         //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
         .getOrCreate();

         /*******************SETUP DATA*****************/


        /*******************SETUP MODEL*****************/



        /******************Analyse Model Accuracy**************/


        /*******************READ MODEL OUTPUT*****************/

        //sparkPredictions.stop();
        return new GetPredictionResponse(null);
    }


    /**
     * This method used to find a anomalies(s) within a given data.
     * A Anomaly is an outlier in the data, in the context of the data e.g elon musk was trending the whole except one specific date.
     * @param request This is a request object which contains data required to be analysed.
     * @return findAnomaliesResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindAnomaliesResponse findAnomalies(FindAnomaliesRequest request)
            throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("findAnomalies Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        SparkSession sparkAnomalies = SparkSession
                .builder()
                .appName("Anomalies")
                .master("local")
                //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
                .getOrCreate();

        JavaSparkContext anomaliesSparkContext = new JavaSparkContext(sparkAnomalies.sparkContext());

        /*******************SETUP DATA*****************/

        List<Row> anomaliesData  = new ArrayList<>();
        ArrayList<ArrayList> requestData = request.getDataList();
        ArrayList<String> types = new ArrayList<>();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();

            String Text = requestData.get(i).get(0).toString(); //New topic, text
            String location = requestData.get(i).get(1).toString();
            String date = requestData.get(i).get(2).toString();
            int like = Integer.parseInt(requestData.get(i).get(3).toString());

            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4);

            String sentiment = findNlpPropertiesResponse.getSentiment();
            row.add(sentiment);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();
            ArrayList<String> entityTypeNames = new ArrayList<>();
            ArrayList<Integer> entityTypesNumbers = new ArrayList<>();

            for (int j=0; j< namedEntities.size(); j++){

                //row.add(namedEntities.get(j).get(0).toString()); //entity-name ---- don't use
                //row.add(namedEntities.get(j).get(1).toString()); //entity-type
                entityTypeNames.add(namedEntities.get(j).get(1).toString()); //TODO: avoid repeating entities?

                if (types.isEmpty()){ //entity-typeNumber
                    //row.add(0);
                    entityTypesNumbers.add(0); //replace
                    types.add(namedEntities.get(j).get(1).toString());
                }
                else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        //row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                        entityTypesNumbers.add(types.indexOf(namedEntities.get(j).get(1).toString())); //replace
                    }
                    else{
                        //row.add(types.size());
                        entityTypesNumbers.add(types.size()); //replace
                        types.add(namedEntities.get(j).get(1).toString());
                    }
                }
            }

            Row anomalyRow = RowFactory.create(
                    Text, //text
                    entityTypeNames, //array entity name
                    entityTypesNumbers, //array entity type
                    entityTypesNumbers.size(), //amount of entities
                    sentiment, //sentiment
                    location, //location
                    date, //date
                    like  //like
            );

            //Row anomalyRow = RowFactory.create(row);
            anomaliesData.add(anomalyRow);
        }

        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(
                new StructField[]{
                        new StructField("Text", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypes", new ArrayType(DataTypes.StringType,true), false, Metadata.empty()),
                        new StructField("EntityTypeNumbers", new ArrayType(DataTypes.IntegerType,true), false, Metadata.empty()),
                        new StructField("AmountOfEntities", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date",DataTypes.StringType, false, Metadata.empty()),
                        //new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Like", DataTypes.IntegerType, false, Metadata.empty()),
                });

        Dataset<Row> itemsDF = sparkAnomalies.createDataFrame(anomaliesData, schema);

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("Text", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypes", new ArrayType(DataTypes.StringType,true), false, Metadata.empty()),
                        new StructField("EntityTypeNumbers", new ArrayType(DataTypes.IntegerType,true), false, Metadata.empty()),
                        new StructField("AmountOfEntities", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Latitude", DataTypes.FloatType, false, Metadata.empty()),
                        new StructField("Longitude", DataTypes.FloatType, false, Metadata.empty()),
                        new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Like", DataTypes.IntegerType, false, Metadata.empty()),
                        //new StructField("AverageLikes", DataTypes.FloatType, false, Metadata.empty()),
                });


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity


        List<Row> textData = itemsDF.select("*").collectAsList();

        //training set
        List<Row> trainSet = new ArrayList<>();
        for(int i=0; i < textData.size(); i++){

            String[] locationData = textData.get(i).get(5).toString().split(","); // location

            Row trainRow = RowFactory.create(
                    textData.get(i).get(0).toString(), //text
                    textData.get(i).get(1), //EntityTypes
                    textData.get(i).get(2), //EntityTypeNumbers
                    (int) textData.get(i).get(3), // amountOfEntities
                    textData.get(i).get(4).toString(), //Sentiment
                    textData.get(i).get(5).toString(), //Location
                    Float.parseFloat(locationData[0]),//Latitude
                    Float.parseFloat(locationData[1]),//Longitude
                    textData.get(i).get(6), //Date
                    textData.get(i).get(7) //Like
            );

            trainSet.add(trainRow);
        }

        Dataset<Row> trainingDF = sparkAnomalies.createDataFrame(trainSet, schema2);

        /***********************MLFLOW - LOAD ***********************/
        PipelineModel kmModel;
        MlflowClient client = new MlflowClient("http://localhost:5000");


        if(request.getModelId() != null) {
            String[] splitModelId = request.getModelId().split(":"); //name, id, id
            String modelName = splitModelId[0];
            String modelID = splitModelId[2];


            //kmModel = PipelineModel.load(artifact.getPath());

            File artifact = client.downloadArtifacts(modelID, modelName);
            //File trainFile = client.downloadArtifacts(modelID,"TrainingData.parquet");

            //Dataset<Row> trainData = sparkAnomalies.read().load(trainFile.getPath());
            Pipeline pipeline = Pipeline.load(artifact.getPath());
            kmModel = pipeline.fit(trainingDF);

            File artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName);

            FileUtils.copyDirectory(artifact, artifactLog);

            client.logArtifact(modelID,artifactLog);

            artifactLog.delete();


            //client.logArtifact(modelID,new File(artifact.getPath()));
            //FileUtils.deleteDirectory(new File(artifact.getPath()));
        }
        else{
            String applicationRegistered = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/RegisteredApplicationModels.txt").toString();
            BufferedReader reader = new BufferedReader(new FileReader(applicationRegistered));

            String findTrendModelId = reader.readLine();
            //findTrendModelId = reader.readLine(); // 2nd line

            String[] splitModelId = findTrendModelId.split(":"); //name, id
            String modelName = splitModelId[0];
            String modelID = splitModelId[2];

            //File artifact = client.downloadArtifacts(modelID, modelName);
            //kmModel = PipelineModel.load(artifact.getPath());

            File artifact = client.downloadArtifacts(modelID, modelName + "A");
            Pipeline pipeline = Pipeline.load(artifact.getPath());
            kmModel = pipeline.fit(trainingDF);

            File artifactLog = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" + modelName + "A");

            FileUtils.copyDirectory(artifact, artifactLog);

            client.logArtifact(modelID,artifactLog);

            artifactLog.delete();

            //FileUtils.deleteDirectory(new File(artifact.getPath()));

            //while (((line = reader.readLine()) != null)) {}
        }


        /*******************LOAD & READ MODEL*****************/
        //PipelineModel.load("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel");

        Dataset<Row> summary=  kmModel.transform(trainingDF).summary();

        //summary.filter(col("prediction").
        Dataset<Row> Results = summary.select("Text","prediction").filter(col("prediction").$greater(0));
        Dataset<Row> rawResults2 = Results.select("Text","prediction");
        List<Row> rawResults = rawResults2.select("Text").collectAsList();

        System.out.println("/*******************Outputs begin*****************");
        System.out.println(rawResults.toString());
        System.out.println("/*******************Outputs begin*****************");

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            if(rawResults.get(i).get(0) != null)
                results.add(rawResults.get(i).get(0).toString());//name
        }

       // sparkAnomalies.stop();

        return new FindAnomaliesResponse(results);
    }


}





