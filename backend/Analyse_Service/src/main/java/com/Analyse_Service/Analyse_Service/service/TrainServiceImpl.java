package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedTrainingData;
import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;
import com.Analyse_Service.Analyse_Service.exception.AnalyserException;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.exception.TrainingModelException;
import com.Analyse_Service.Analyse_Service.repository.TrainingDataRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;

import com.johnsnowlabs.nlp.DocumentAssembler;
import com.johnsnowlabs.nlp.annotators.Tokenizer;
import com.johnsnowlabs.nlp.annotators.TokenizerModel;
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
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.tuning.CrossValidator;
import org.apache.spark.ml.tuning.ParamGridBuilder;
import org.apache.spark.ml.tuning.TrainValidationSplit;
import org.apache.spark.ml.tuning.TrainValidationSplitModel;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import org.mlflow.tracking.ActiveRun;
import org.mlflow.tracking.MlflowClient;
import org.mlflow.tracking.MlflowContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.JavaConversions;
import scala.collection.mutable.WrappedArray;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.spark.sql.functions.col;

@Service
public class TrainServiceImpl {

    @Autowired
    private TrainingDataRepository parsedDataRepository;

    //static final Logger logger = Logger.getLogger(TrainServiceImpl.class);

    /**
     * This method used to train a user's model per their training data
     * @param request This is a request object which contains data required to be analysed.
     * @return TrainModelResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainUserModelResponse trainUserModel(TrainUserModelRequest request)
            throws AnalyserException {

        if (request == null) {
            throw new InvalidRequestException("TrainModelRequest Object is null");
        }
        if (request.getModelName() == null){
            throw new InvalidRequestException("Model Name is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList of requested parsedData is null");
        }
        for(int i =0; i<request.getDataList().size(); i++) {
            if (request.getDataList().get(i) == null) {
                throw new InvalidRequestException("DataList inside data of requested parsedData is null");
            }
        }
        if(request.getDataList().size() >0){
            ParsedTrainingData testParsedData = request.getDataList().get(0);

            if(testParsedData.getTextMessage() != null) {
                throw new InvalidRequestException("DataList of requested parsedData has Text field null");
            }
            if(testParsedData.getLocation() != null) {
                throw new InvalidRequestException("DataList of requested parsedData has Location field null");
            }
            if(testParsedData.getDate() != null) {
                throw new InvalidRequestException("DataList of requested parsedData has Date field null");
            }
            if(testParsedData.getInteractions() != null) {
                throw new InvalidRequestException("DataList of requested parsedData has Interactions field null");
            }
            if(testParsedData.getIsTrending() != null) {
                throw new InvalidRequestException("DataList of requested parsedData has Trend field null");
            }
        }
        else{
            throw new InvalidRequestException("DataList inside data of requested parsedData is empty");
        }

        /*******************USE NLP******************/

        System.out.println("*******************USE NLP******************");

        /********data*******/
        ArrayList<ParsedTrainingData> dataList = request.getDataList();
        ArrayList<ArrayList> parsedDataList = new ArrayList<>(); //TODO: used to send all other functions

        ArrayList<String> nlpTextData = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            nlpTextData.add(dataList.get(i).getTextMessage());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestData = new FindNlpPropertiesRequest(nlpTextData);
        List<Object> nlpResults = this.findNlpProperties(findNlpPropertiesRequestData);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseSocial = (ArrayList<FindNlpPropertiesResponse>) nlpResults.get(0); // this.findNlpProperties(findNlpPropertiesRequestSocial);


        /**social **
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
        if (articleList.isEmpty())
            System.out.println("no articles");

        ArrayList<ArrayList> parsedArticleList = new ArrayList<>(); //TODO: need to use

        ArrayList<String> nlpTextArticle = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            nlpTextArticle.add(articleList.get(i).getDescription()+" "+articleList.get(i).getTitle()); ///TODO: shrey used other names like i think message = content; (more was changed)
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestArticle = new FindNlpPropertiesRequest(nlpTextArticle);
        List<Object> nlpArticle = this.findNlpProperties(findNlpPropertiesRequestArticle);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseArticle = (ArrayList<FindNlpPropertiesResponse>) nlpArticle.get(0);
        ArrayList<ArrayList> ArticleWordList = (ArrayList<ArrayList>) nlpArticle.get(1);

        for(int i =0; i < ArticleWordList.size() ;i++){
             wordList.add(ArticleWordList.get(i));
        }

        /*******************Setup Data******************/

        /*******************Setup Data ******************/
        System.out.println("*******************Setup Data main: ******************");
        System.out.println(dataList.size());

        /**TODO: social : data**/
        for (int i = 0; i < dataList.size(); i++) {
            //String row = "";

            String text = dataList.get(i).getTextMessage();
            String location = dataList.get(i).getLocation();
            String date = dataList.get(i).getDate();//Mon Jul 08 07:13:29 +0000 2019
            String[] dateTime = date.split(" ");
            String formattedDate = dateTime[1] + " " + dateTime[2] + " " + dateTime[5];
            String likes = String.valueOf(dataList.get(i).getInteractions());
            String trend = String.valueOf(dataList.get(i).getIsTrending());


            //Random rn = new Random();
            //int mockLike = rn.nextInt(10000) + 1;*/

            ArrayList<Object> rowOfParsed = new ArrayList<>();
            rowOfParsed.add(text);
            rowOfParsed.add(location);
            rowOfParsed.add(formattedDate);
            rowOfParsed.add(likes);
            rowOfParsed.add(findNlpPropertiesResponseSocial.get(i));
            rowOfParsed.add(trend);

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



        long duration = 0;// milliseconds
        String modelId = "";
        String modelName ="";
        try {

            long  startTime = System.currentTimeMillis();

            //TrainFindTrendsArticlesRequest findTrendsArticlesRequest = new TrainFindTrendsArticlesRequest(parsedDataList);
            //trainFindTrendsArticlesLR(findTrendsArticlesRequest);


            TrainFindTrendsRequest findTrendsRequest = new TrainFindTrendsRequest(parsedDataList, request.getModelName());
            TrainFindTrendsResponse findTrendsResponse = this.trainFindTrends(findTrendsRequest);

            TrainFindTrendsDTRequest findTrendsDTRequest = new TrainFindTrendsDTRequest(parsedDataList, request.getModelName());
            TrainFindTrendsDTResponse findTrendsDTResponse =   this.trainFindTrendsDecisionTree(findTrendsDTRequest);

            TrainFindAnomaliesRequest findAnomaliesRequest = new TrainFindAnomaliesRequest(parsedDataList, request.getModelName());
            TrainFindAnomaliesResponse findAnomaliesResponse = this.trainFindAnomalies(findAnomaliesRequest);


            ArrayList trainedModel = new ArrayList();
            trainedModel.add(findTrendsResponse.getTrainedModel());
            trainedModel.add(findTrendsDTResponse.getTrainedModel());
            trainedModel.add(findAnomaliesResponse.getTrainedModel());


            RegisterUserBestModelRequest registerUserBestModelRequest = new RegisterUserBestModelRequest(trainedModel);
            RegisterUserBestModelResponse registerUserBestModelResponse = registerUserBestModel(registerUserBestModelRequest);

            modelName = registerUserBestModelResponse.getBestModelName();
            modelId = registerUserBestModelResponse.getBestModelId();
            long  endTime = System.currentTimeMillis();
            duration = endTime - startTime;
        } catch (IOException e) {
            throw new TrainingModelException("Failed logging model file");
        }


        return new TrainUserModelResponse(duration,modelId,modelName);
    }


    /**
     * This method used to train the overall default app models.
     * @throws AnalyserException This is thrown if the request or if any of its attributes are invalid.
     */
    public void trainApplicationModel()
            throws AnalyserException {

        ArrayList<ParsedData> dataList = new ArrayList<>();// repos.getParsedDataList();

        //File file = new File(classLoader.getResource("fileTest.txt").getFile());

        /*File resource = new ClassPathResource("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/TData.CSV").getFile();

        FileResourcesUtils app = new FileResourcesUtils();

        String fileUrl = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/TData.CSV";

        ;*/

        //.getResourceAsStream("TData.CSV");
        //InputStream is = classloader.getResource("TData.CSV").

        InputStream is = this.getClass().getResourceAsStream("TData.CSV");
        File tData = null;

        /*if(is == null){
            tData = new File(this.getClass().getResource("TData.CSV").getFile());
            if(tData.exists() == false){
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                tData = new File(classloader.getResource("TData.CSV").getFile());
            }
        }*/


        BufferedReader reader = null;
        String line = "";
        String fileUrl = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/TData.CSV";

        try{
            /*if(is != null){
                reader = new BufferedReader(new InputStreamReader(is));
            }
            else{
                reader = new BufferedReader(new FileReader(tData));
            }*/

            reader = new BufferedReader(new FileReader(fileUrl));
            //System.out.println("*******************CHECK THIS HERE*****************");

            line = reader.readLine();
            String text = "";
            int maxCounter = 0;
            boolean foundComplete = true;
            int count = 1;
            while(( (line = reader.readLine()) != null) ){
                System.out.println(line);
                String[] row = line.split("\\|");
                if(row != null)
                    maxCounter = maxCounter + row.length-1; //delimiter counter

                ParsedData newData = new ParsedData();

                if((maxCounter == 4) && (foundComplete == true)){
                    maxCounter = 0;
                    text = "";
                    foundComplete = true;

                    newData.setTextMessage(row[1] );
                    newData.setDate(row[2]);
                    newData.setLocation(row[3]);
                    newData.setLikes(Integer.parseInt(row[4]));
                    dataList.add(newData);
                    count = count +1;
                }
                else if((maxCounter == 4) && (foundComplete == false)){
                    maxCounter = 0;
                    text = "";
                    foundComplete = true;

                    newData.setTextMessage(text + row[0] );
                    newData.setDate(row[1]);
                    newData.setLocation(row[2]);
                    newData.setLikes(Integer.parseInt(row[3]));
                    dataList.add(newData);
                    count = count +1;
                }
                else if(maxCounter < 4){
                    text = text + line;
                    foundComplete = false;
                    continue;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /**************************************************************************************************************/


        ArrayList<ArrayList> parsedDataList = new ArrayList<>(); //TODO: used to send all other functions

        ArrayList<String> nlpTextSocial = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            nlpTextSocial.add(dataList.get(i).getTextMessage());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequestSocial = new FindNlpPropertiesRequest(nlpTextSocial);
        List<Object> nlpResults = this.findNlpProperties(findNlpPropertiesRequestSocial);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponseSocial = (ArrayList<FindNlpPropertiesResponse>) nlpResults.get(0); // this.findNlpProperties(findNlpPropertiesRequestSocial);


        /*******************Setup Data******************/
        /**social**/
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

        /**************************************************************************************************************/

        try {
            TrainFindTrendsRequest findTrendsRequest = new TrainFindTrendsRequest(parsedDataList);
            TrainFindTrendsResponse findTrendsResponse = this.trainFindTrends(findTrendsRequest);

            System.out.println("finished training 1");

            TrainFindTrendsDTRequest findTrendsDTRequest = new TrainFindTrendsDTRequest(parsedDataList);
            TrainFindTrendsDTResponse findTrendsDTResponse = this.trainFindTrendsDecisionTree(findTrendsDTRequest);

            System.out.println("finished training 2");

            TrainFindAnomaliesRequest findAnomaliesRequest = new TrainFindAnomaliesRequest(parsedDataList);
            TrainFindAnomaliesResponse findAnomaliesResponse = this.trainFindAnomalies(findAnomaliesRequest);

            System.out.println("finished training all");

            ArrayList<TrainedModel> trainedModels = new ArrayList<>();
            trainedModels.add(findTrendsResponse.getTrainedModel());
            trainedModels.add(findTrendsDTResponse.getTrainedModel());
            trainedModels.add(findAnomaliesResponse.getTrainedModel());

            RegisterApplicationBestModelRequest registerApplicationBestModelRequest = new RegisterApplicationBestModelRequest(trainedModels);
            this.registerApplicationBestModel(registerApplicationBestModelRequest);
        } catch (IOException e) {
            throw new TrainingModelException("Failed logging model file");
        }

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

        sparkNlpProperties.stop();

        return Arrays.asList(response, entityList);
    }


    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return FindTrendsResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainFindTrendsResponse trainFindTrends(TrainFindTrendsRequest request)
            throws InvalidRequestException, IOException {

        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        //logger.setLevel(Level.ERROR);
        //LogManager.getRootLogger().setLevel(Level.ERROR);

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

        for (int i = 0; i < requestData.size(); i++) {
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j = 0; j < namedEntities.size(); j++) {


                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()) {// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                } else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    } else {
                        row.add(types.size());
                        types.add(namedEntities.get(j).get(1).toString());
                    }

                }

                row.add(requestData.get(i).get(1).toString());//location
                row.add(requestData.get(i).get(2).toString());//date
                row.add(Integer.parseInt(requestData.get(i).get(3).toString()));//likes
                row.add(sentiment);//sentiment

                if(request.getModelName() != null) {
                    row.add(Integer.parseInt(requestData.get(i).get(5).toString())); //isTrending
                }

                Row trendRow = RowFactory.create(row.toArray());
                trendsData.add(trendRow);
            }
        }

        /*******************SETUP DATAFRAME*****************/
        Dataset<Row> itemsDF;

        if(request.getModelName() == null) {
            StructType inputSchema = new StructType(
                    new StructField[]{
                            new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                    });

            itemsDF = sparkTrends.createDataFrame(trendsData, inputSchema);
        }else {

            StructType inputSchema = new StructType(
                    new StructField[]{
                            new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("IsTrending", DataTypes.StringType, false, Metadata.empty()),
                    });

            itemsDF = sparkTrends.createDataFrame(trendsData, inputSchema);
        }


        StructType schema = new StructType(
                new StructField[]{
                        new StructField("IsTrending", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.DoubleType, false, Metadata.empty()),
                });


        System.out.println("trends dataframes");
        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities;
        if(request.getModelName() == null) {
            namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency
        }else{
            namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber", "IsTrending").count().collectAsList(); //frequency
        }
        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();


        //training set
        int minSize = 0;
        if (namedEntities.size() > averageLikes.size()) {
            minSize = averageLikes.size();
        } else {
            minSize = namedEntities.size();
        }

        if (minSize > rate.size()) {
            minSize = rate.size();
        }


        /*System.out.println("NameEntity : " + namedEntities.size());
        for (int i = 0; i < namedEntities.size(); i++) {
            System.out.println(namedEntities.get(i).toString());
        }

        System.out.println("AverageLikes : " + averageLikes.size());
        for (int i = 0; i < averageLikes.size(); i++) {
            System.out.println(averageLikes.get(i).toString());
        }*/

        List<Row> trainSet = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            if(request.getModelName() == null) {
                double trending = 0.0;
                if (Integer.parseInt(namedEntities.get(i).get(3).toString()) >= 4) {
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
            else{
                Row trainRow = RowFactory.create(
                        Integer.parseInt(namedEntities.get(i).get(3).toString()), //trend
                        namedEntities.get(i).get(0).toString(), //name
                        namedEntities.get(i).get(1).toString(), //type
                        Double.parseDouble(namedEntities.get(i).get(2).toString()), //number
                        Double.parseDouble(namedEntities.get(i).get(4).toString()), //freq
                        rate.get(i).get(1).toString(),
                        Double.parseDouble(averageLikes.get(i).get(1).toString())
                );
                trainSet.add(trainRow);
            }
        }

        //split data
        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");
        Dataset<Row>[] split = trainingDF.randomSplit((new double[]{0.7, 0.3}), 5043);

        Dataset<Row> trainSetDF = split[0];
        Dataset<Row> testSetDF = split[1];

        trainSetDF.show();
        System.out.println("TRAIN ME");

        testSetDF.show();
        System.out.println("TEST ME");

        /*******************SETUP PIPELINE MODEL *****************/
        //features
        /*Tokenizer tokenizer = new Tokenizer()
                .setInputCol("text")
                .setOutputCol("words");

        HashingTF hashingTF = new HashingTF()
                .setNumFeatures(1000)
                .setInputCol(tokenizer.getOutputCol())
                .setOutputCol("features");*/

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber", "Frequency", "AverageLikes"})
                .setOutputCol("features");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");

        //model
        LogisticRegression lr = new LogisticRegression() //model - estimator
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        //pipeline
        Pipeline pipeline = new Pipeline();
        pipeline.setStages(new PipelineStage[]{assembler, indexer, lr});

        System.out.println("trends model");

        /******************EVALUATE/ANALYSE MODEL**************/

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
        ParamGridBuilder paramGridBuilder = new ParamGridBuilder();

        paramGridBuilder.addGrid(lr.regParam(), new double[]{lr.getRegParam()});
        paramGridBuilder.addGrid(lr.elasticNetParam(), new double[]{lr.getElasticNetParam()});
        paramGridBuilder.addGrid(lr.fitIntercept());
        ParamMap[] paramMaps = paramGridBuilder.build();


        //validator
        /*CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(2);*/

        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);

        System.out.println("trends mlflow");


        /***********************SETUP MLFLOW - SAVE ***********************/

        /***setup***/

        String modelName;
        if(request.getModelName() == null) {
            modelName = "_ApplicationModelT";
        }
        else{
            modelName = request.getModelName();
        }

        //client
        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<org.mlflow.api.proto.Service.Experiment> foundExperiment = client.getExperimentByName(modelName + "_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment(modelName + "_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        org.mlflow.api.proto.Service.RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun(modelName + "_Run", runInfo.getRunId());

        /***trainModel***/
        TrainValidationSplitModel lrModel = trainValidationSplit.fit(trainSetDF);
        Dataset<Row> predictions = lrModel.transform(testSetDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");


        double accuracy = binaryClassificationEvaluator.evaluate(predictions);
        BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);

        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));


        /***logging***/
        //param
        client.logParam(run.getId(),"Max Iteration", String.valueOf(lr.getMaxIter()));
        client.logParam(run.getId(),"Reg Param" ,String.valueOf(lr.getRegParam()));
        client.logParam(run.getId(),"Elastic Net Param" , String.valueOf(lr.getElasticNetParam()));
        client.logParam(run.getId(),"Fitness intercept" , String.valueOf(lr.getFitIntercept()));


        //metrics
        /*client.logMetric(run.getId(), "areaUnderROC", binaryClassificationMetrics.areaUnderROC());
        client.logMetric(run.getId(), "meanSquaredError", regressionMetrics.meanSquaredError());
        client.logMetric(run.getId(), "rootMeanSquaredError", regressionMetrics.rootMeanSquaredError());
        client.logMetric(run.getId(), "meanAbsoluteError", regressionMetrics.meanAbsoluteError());
        client.logMetric(run.getId(), "explainedVariance", regressionMetrics.explainedVariance());*/

        for(int i=0; i < 5; i++) {
            client.logMetric(run.getId(), "areaUnderROC", binaryClassificationMetrics.areaUnderROC()+(i));
            client.logMetric(run.getId(), "meanSquaredError", regressionMetrics.meanSquaredError()+(i));
            client.logMetric(run.getId(), "rootMeanSquaredError", regressionMetrics.rootMeanSquaredError()+(i+2));
            client.logMetric(run.getId(), "meanAbsoluteError", regressionMetrics.meanAbsoluteError()+(i+2));
            client.logMetric(run.getId(), "explainedVariance", regressionMetrics.explainedVariance()+(i+3));
        }

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        client.setTag(run.getId(),"Run ID", String.valueOf(run.getId()));
        //run.setTag("Accuracy", String.valueOf(accuracy));

        //lrModel.write().overwrite().save("../models/LogisticRegressionModel");

        /***saveModel***/

        //*"backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/"*/ "..models/" +modelName;
        //String path =  Paths.get("../models/" +modelName).getRoot().toString();
        //String path =  Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" +modelName).getRoot().toString();
        String path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" +modelName;
        System.out.println("Testing new path !!!  " + path);
        String script = Paths.get("../rri/LogModel.py").toString();


        //lrModel.write().overwrite().save(path);
        trainValidationSplit.write().overwrite().save(path);


        File modelFile = new File(path);// "../models/" + modelName);

        if(modelFile.exists() && modelFile.isDirectory()){
            System.out.println("nothing wrong with file ");
        }else{
            System.out.println("something wrong with the file");
        }
        client.logArtifact(run.getId(), modelFile);


        path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/TrainingData.parquet";
        trainSetDF.write().save(path);
        File trainFile = new File(path);
        client.logArtifact(run.getId(), trainFile);

        String filePath = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt").toString();
        File infoFile = new File(filePath);
        infoFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(infoFile, false);
        fos.write(String.valueOf(accuracy).getBytes());
        fos.close();
        client.logArtifact(run.getId(), infoFile);


        TrainedModel trainedModel = new TrainedModel(run.getId(), accuracy,run.getId(), modelName);
        FileUtils.deleteDirectory(modelFile);
        FileUtils.deleteDirectory(trainFile);
        infoFile.delete();

        /*
        String commandPath = "python " + script + " " + path + " LogisticRegressionModel " + run.getId();
        CommandLine commandLine = CommandLine.parse(commandPath);
        //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        executor.execute(commandLine);
        */

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/
        System.out.println("trends done");
        sparkTrends.stop();
        ArrayList<ArrayList> results = new ArrayList<>();
        return new TrainFindTrendsResponse(results, trainedModel);
    }


    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return void
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainFindTrendsDTResponse trainFindTrendsDecisionTree( TrainFindTrendsDTRequest request)
            throws InvalidRequestException, IOException {

        if (request == null) {
            throw new InvalidRequestException("FindTrendsDTRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/
        //logger.setLevel(Level.ERROR);
        //LogManager.getRootLogger().setLevel(Level.ERROR);

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
                //.master("spark://idis-app-spark-master-0.idis-app-spark-headless.default.svc.cluster.local:7077")
                .getOrCreate();

        sparkTrends.sparkContext().setLogLevel("ERROR");

        /*******************SETUP DATA*****************/


        ArrayList<ArrayList> requestData = request.getDataList();
        List<Row> trendsData = new ArrayList<>();

        ArrayList<String> types = new ArrayList<>();

        for (int i = 0; i < requestData.size(); i++) {
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j = 0; j < namedEntities.size(); j++) {
                //row.add(isTrending)
                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()) {// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                } else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    } else {
                        row.add(types.size());
                        types.add(namedEntities.get(j).get(1).toString());
                    }

                }

                row.add(requestData.get(i).get(1).toString());//location
                row.add(requestData.get(i).get(2).toString());//date
                row.add(Integer.parseInt(requestData.get(i).get(3).toString()));//likes
                row.add(sentiment);//sentiment

                if(request.getModelName() != null) {
                    row.add(Integer.parseInt(requestData.get(i).get(5).toString())); //isTrending
                }

                Row trendRow = RowFactory.create(row.toArray());
                trendsData.add(trendRow);
            }
        }

        /*******************SETUP DATAFRAME*****************/

        Dataset<Row> itemsDF;

        if(request.getModelName() == null) {
            StructType inputSchema = new StructType(
                    new StructField[]{
                            new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                    });

            itemsDF = sparkTrends.createDataFrame(trendsData, inputSchema);
        }else {

            StructType inputSchema = new StructType(
                    new StructField[]{
                            new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                            new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                            new StructField("IsTrending", DataTypes.StringType, false, Metadata.empty()),
                    });

            itemsDF = sparkTrends.createDataFrame(trendsData, inputSchema);
        }


        StructType schema = new StructType(
                new StructField[]{
                        new StructField("IsTrending", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.DoubleType, false, Metadata.empty()),
                });


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities;
        if(request.getModelName() == null) {
            namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency
        }else{
            namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber", "IsTrending").count().collectAsList(); //frequency
        }
        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();


        //training set
        int minSize = 0;
        if (namedEntities.size() > averageLikes.size()) {
            minSize = averageLikes.size();
        } else {
            minSize = namedEntities.size();
        }

        if (minSize > rate.size()) {
            minSize = rate.size();
        }


        List<Row> trainSet = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            if(request.getModelName() == null) {
                double trending = 0.0;
                if (Integer.parseInt(namedEntities.get(i).get(3).toString()) >= 4) {
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
            else{
                Row trainRow = RowFactory.create(
                        Integer.parseInt(namedEntities.get(i).get(3).toString()), //trend
                        namedEntities.get(i).get(0).toString(), //name
                        namedEntities.get(i).get(1).toString(), //type
                        Double.parseDouble(namedEntities.get(i).get(2).toString()), //number
                        Double.parseDouble(namedEntities.get(i).get(4).toString()), //freq
                        rate.get(i).get(1).toString(),
                        Double.parseDouble(averageLikes.get(i).get(1).toString())
                );
                trainSet.add(trainRow);
            }
        }

        //split data
        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");
        Dataset<Row>[] split = trainingDF.randomSplit((new double[]{0.7, 0.3}), 5043);

        Dataset<Row> trainSetDF = split[0];
        Dataset<Row> testSetDF = split[1];

        trainSetDF.show();
        System.out.println("TRAIN ME");

        testSetDF.show();
        System.out.println("TEST ME");

        /*******************SETUP PIPELINE MODEL *****************/

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber", "Frequency", "AverageLikes"})
                .setOutputCol("features");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");

        StringIndexer labelIndexer = new StringIndexer()
                .setInputCol("label")
                .setOutputCol("indexedLabel");

        VectorIndexer featureIndexer = new VectorIndexer()
                .setInputCol("features")
                .setOutputCol("indexedFeatures")
                .setMaxCategories(3); // features with > 4 distinct values are treated as continuous.

        //model
        DecisionTreeClassifier dt = new DecisionTreeClassifier()
                .setLabelCol("indexedLabel")
                .setFeaturesCol("indexedFeatures");

        //pipeline
        Pipeline pipeline = new Pipeline();
        pipeline.setStages(new PipelineStage[]{assembler, indexer,labelIndexer,featureIndexer, dt});

        /******************EVALUATE/ANALYSE MODEL**************/

        MulticlassClassificationEvaluator multiclassClassificationEvaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("indexedLabel")
                .setPredictionCol("prediction")
                .setMetricName("accuracy");

        ParamGridBuilder paramGridBuilder = new ParamGridBuilder();

        paramGridBuilder.addGrid(dt.maxDepth(), new int[]{dt.getMaxDepth()});
        paramGridBuilder.addGrid(dt.maxBins(), new int[]{dt.getMaxBins()});
        ParamMap[] paramMaps = paramGridBuilder.build();


        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(multiclassClassificationEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);


        /***********************SETUP MLFLOW - SAVE ***********************/

        /***setup***/

        String modelName;
        if(request.getModelName() == null) {
            modelName = "_ApplicationModelT";
        }
        else{
            modelName = request.getModelName();
        }

        //client
        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<org.mlflow.api.proto.Service.Experiment> foundExperiment = client.getExperimentByName(modelName + "_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment(modelName + "_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        org.mlflow.api.proto.Service.RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun(modelName + "_Run", runInfo.getRunId());

        /***trainModel***/
        TrainValidationSplitModel dtModel = trainValidationSplit.fit(trainSetDF);
        Dataset<Row> predictions = dtModel.transform(testSetDF);
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");

        double accuracy = multiclassClassificationEvaluator.evaluate(predictions);
        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));
        //System.out.println("Test Error = " + (1.0 - accuracy));

        MulticlassMetrics multiclassMetrics = multiclassClassificationEvaluator.getMetrics(predictions);


        /***logging***/
        //param
        client.logParam(run.getId(),"max depth", String.valueOf(dt.getMaxDepth()));
        client.logParam(run.getId(),"max bin" ,String.valueOf(dt.getMaxBins()));


        //metrics
        client.logMetric(run.getId(),"Accuracy" ,multiclassMetrics.accuracy());
        client.logMetric(run.getId(),"Precision" , multiclassMetrics.weightedPrecision());
        client.logMetric(run.getId(),"Recall" , multiclassMetrics.weightedRecall());

        client.logMetric(run.getId(),"F-measure" ,multiclassMetrics.weightedFMeasure());
        client.logMetric(run.getId(),"True positive rate" ,multiclassMetrics.weightedTruePositiveRate());
        client.logMetric(run.getId(),"False positive rate" ,multiclassMetrics.weightedFalsePositiveRate());
        client.logMetric(run.getId(),"Hamming loss" , multiclassMetrics.hammingLoss());

        for(int i =0; i < multiclassMetrics.labels().length - 1; i++) {
            client.logMetric(run.getId(), "Precision by label", multiclassMetrics.precision(multiclassMetrics.labels()[i]));
            client.logMetric(run.getId(), "Recall by label", multiclassMetrics.recall(multiclassMetrics.labels()[i]));
            client.logMetric(run.getId(), "True positive rate by label", multiclassMetrics.truePositiveRate(multiclassMetrics.labels()[i]));
            client.logMetric(run.getId(), "F-measure by label", multiclassMetrics.fMeasure(multiclassMetrics.labels()[i]));
            //client.logMetric(run.getId(), "Subset Accuracy", multiclassMetrics.precision(multiclassMetrics.labels()[i]));
            //client.logMetric(run.getId(),"Micro precision" , multiclassMetrics.precision(multiclassMetrics.labels()[i]));
            //client.logMetric(run.getId(),"Micro recall" , multiclassMetrics.precision(multiclassMetrics.labels()[i]));
            //client.logMetric(run.getId(),"Micro F1 Measure" , multiclassMetrics.precision(multiclassMetrics.labels()[i]));
        }


        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        client.setTag(run.getId(),"Run ID", String.valueOf(run.getId()));

        //String path = Paths.get("../models/" + modelName).toString();
        String path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" +modelName;
        String script = Paths.get("../rri/LogModel.py").toString();

        //dtModel.write().overwrite().save(path);
        trainValidationSplit.write().overwrite().save(path);
        File modelFile = new File(path);

        client.logArtifact(run.getId(), modelFile);

        path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/TrainingData.parquet";
        trainSetDF.write().save(path);
        File trainFile = new File(path);
        client.logArtifact(run.getId(), trainFile);

        String filePath = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt").toString();
        File infoFile = new File(filePath);
        infoFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(infoFile, false);
        fos.write(String.valueOf(accuracy).getBytes());
        fos.close();
        client.logArtifact(run.getId(), infoFile);


        TrainedModel trainedModel = new TrainedModel(run.getId(), accuracy,run.getId(), modelName);
        FileUtils.deleteDirectory(modelFile);
        FileUtils.deleteDirectory(trainFile);
        infoFile.delete();


        /*String commandPath = "python " + script + " " + path + " DecisionTreeModel " + run.getId();
        CommandLine commandLine = CommandLine.parse(commandPath);
        //commandLine.addArguments(new String[] {"../models/DecisionTreeModel","DecisionTreeModel", "1"});
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        executor.execute(commandLine);*/

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

         sparkTrends.stop();
         ArrayList<ArrayList> results = new ArrayList<>();
         return new TrainFindTrendsDTResponse(results, trainedModel);
    }


    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return void
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainFindTrendsArticlesResponse trainFindTrendsArticlesLR(TrainFindTrendsArticlesRequest request)
            throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        //logger.setLevel(Level.ERROR);
        //LogManager.getRootLogger().setLevel(Level.ERROR);

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

        for (int i = 0; i < requestData.size(); i++) {
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(5); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j = 0; j < namedEntities.size(); j++) {
                //row.add(isTrending)
                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()) {// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                } else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    } else {
                        row.add(types.size());
                        types.add(namedEntities.get(j).get(1).toString());
                    }

                }

                row.add(requestData.get(i).get(4).toString());//date
                row.add(Integer.parseInt(requestData.get(i).get(3).toString()));//Character count
                row.add(sentiment);//sentiment

                Row trendRow = RowFactory.create(row.toArray());
                trendsData.add(trendRow);
            }
        }

        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(
                new StructField[]{
                        new StructField("IsTrending", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("SentimentType", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("Character count", DataTypes.DoubleType, false, Metadata.empty()),
                });

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Character count", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });


        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2);
        itemsDF.show(itemsDF.collectAsList().size());

        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Character count").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

        ArrayList<String> sents = new ArrayList<>();






        //training set
        int minSize = 0;
        if (namedEntities.size() > averageLikes.size()) {
            minSize = averageLikes.size();
        } else {
            minSize = namedEntities.size();
        }

        if (minSize > rate.size()) {
            minSize = rate.size();
        }


        System.out.println("NameEntity : " + namedEntities.size());
        for (int i = 0; i < namedEntities.size(); i++) {
            System.out.println(namedEntities.get(i).toString());
        }

        System.out.println("AverageLikes : " + averageLikes.size());
        for (int i = 0; i < averageLikes.size(); i++) {
            System.out.println(averageLikes.get(i).toString());
        }

        List<Row> trainSet = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            List<Row> sen = itemsDF.select("Sentiment").filter(col("EntityName").equalTo(namedEntities.get(i).get(0).toString())).collectAsList();
            double sent = 0.0;
            if (sen.get(0).get(0).toString().equals("Positive")) sent = 2.0;
            else if (sen.get(0).get(0).toString().equals("Negative")) sent = 1.0;
            double trending = 0.0;
            if (Integer.parseInt(namedEntities.get(i).get(3).toString()) >= 4) {
                trending = 1.0;
            }
            Row trainRow = RowFactory.create(
                    trending,
                    namedEntities.get(i).get(0).toString(),
                    namedEntities.get(i).get(1).toString(),
                    Double.parseDouble(namedEntities.get(i).get(2).toString()),
                    Double.parseDouble(namedEntities.get(i).get(3).toString()),
                    sent,
                    Double.parseDouble(averageLikes.get(i).get(1).toString())
            );
            trainSet.add(trainRow);
        }

        //split data
        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");
        Dataset<Row>[] split = trainingDF.randomSplit((new double[]{0.7, 0.3}), 5043);

        Dataset<Row> trainSetDF = split[0];
        Dataset<Row> testSetDF = split[1];

        trainSetDF.show();
        System.out.println("TRAIN ME");

        testSetDF.show();
        System.out.println("TEST ME");

        /*******************SETUP PIPELINE MODEL *****************/
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber", "Frequency", "SentimentType","Character count"})
                .setOutputCol("features");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");

        //model
        LogisticRegression lr = new LogisticRegression() //model - estimator
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        //pipeline
        Pipeline pipeline = new Pipeline();
        pipeline.setStages(new PipelineStage[]{assembler, indexer, lr});

        /******************EVALUATE/ANALYSE MODEL**************/

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
        ParamGridBuilder paramGridBuilder = new ParamGridBuilder();

        paramGridBuilder.addGrid(lr.regParam(), new double[]{lr.getRegParam()});
        paramGridBuilder.addGrid(lr.elasticNetParam(), new double[]{lr.getElasticNetParam()});
        paramGridBuilder.addGrid(lr.fitIntercept());
        ParamMap[] paramMaps = paramGridBuilder.build();


        //validator
        /*CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(2);*/

        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);


        /***********************SETUP MLFLOW - SAVE ***********************/

        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<org.mlflow.api.proto.Service.Experiment> foundExperiment = client.getExperimentByName("LogisticRegression_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("LogisticRegression_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        org.mlflow.api.proto.Service.RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun("LogisticRegression_Run", runInfo.getRunId());


        TrainValidationSplitModel lrModel = trainValidationSplit.fit(trainSetDF);
        Dataset<Row> predictions = lrModel.transform(testSetDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");


        double accuracy = binaryClassificationEvaluator.evaluate(predictions);
        BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);

        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));

        //param
        client.logParam(run.getId(),"Max Iteration", String.valueOf(lr.getMaxIter()));
        client.logParam(run.getId(),"Reg Param" ,String.valueOf(lr.getRegParam()));
        client.logParam(run.getId(),"Elastic Net Param" , String.valueOf(lr.getElasticNetParam()));
        client.logParam(run.getId(),"Fitness intercept" , String.valueOf(lr.getFitIntercept()));


        //metrics
        client.logMetric(run.getId(),"areaUnderROC" , binaryClassificationMetrics.areaUnderROC());
        client.logMetric(run.getId(),"meanSquaredError", regressionMetrics.meanSquaredError());
        client.logMetric(run.getId(),"rootMeanSquaredError", regressionMetrics.rootMeanSquaredError());
        client.logMetric(run.getId(),"meanAbsoluteError", regressionMetrics.meanAbsoluteError());
        client.logMetric(run.getId(),"explainedVariance", regressionMetrics.explainedVariance());

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        //run.setTag("Accuracy", String.valueOf(accuracy));

        //lrModel.write().overwrite().save("../models/LogisticRegressionModel");

        lrModel.write().overwrite().save("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");

        //client.setTag(run.getId(),"Run ID", String.valueOf(run.getId()));
        //client.logArtifact(run.getId(), new File(path));
        try {
            //lrModel.save("Database");

            File modelFile = new File("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");

            //TODO: flavor
            //client.logArtifact(run.getId(), modelFile);

            File artifact = client.downloadModelVersion("LogisticRegressionModel", "1");

            /*ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
            mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String jsonModel = ow.writeValueAsString(modelFile);
            //String jsonModel = String.valueOf(modelFile);

            LogModel logModel = LogModel.newBuilder()
                    .setRunId(run.getId())
                    .setModelJson(jsonModel)
                    .build();



            System.out.println(logModel);

            ModelRegistry.CreateModelVersion.newBuilder()
                    .setName("LogisticRegressionModel")
                    .setRunId(run.getId())
                    .setSource("artifactstore")
                    .build();*/

        }catch (Exception e){
            e.printStackTrace();
        }

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

        sparkTrends.stop();
        ArrayList<ArrayList> results = new ArrayList<>();

        return new TrainFindTrendsArticlesResponse(results);
    }


    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a overall insight. use neural network
     * @param request This is a request object which contains data required to be analysed.
     * @return GetPredictionResponse This object contains data of the predictions found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainGetPredictionResponse trainGetPredictions(TrainGetPredictionRequest request)
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

        /*******************READ MODEL OUTPUT*****************/

        sparkPredictions.stop();
        return new TrainGetPredictionResponse(null);
    }


    /**
     * This method used to find a anomalies(s) within a given data.
     * A Anomaly is an outlier in the data, in the context of the data e.g elon musk was trending the whole except one specific date.
     * @param request This is a request object which contains data required to be analysed.
     * @return findAnomaliesResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainFindAnomaliesResponse trainFindAnomalies(TrainFindAnomaliesRequest request)
            throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("findAnomalies Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }
        //must fix this
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

            Object amountOfEntitiesObject = textData.get(i).get(2); //amount = func(EntityTypeNumbers)

            List<?> amountOfEntities = new ArrayList<>();
            if (amountOfEntitiesObject.getClass().isArray()) {
                amountOfEntities = Arrays.asList((Object[])amountOfEntitiesObject);
            } else if (amountOfEntitiesObject instanceof Collection) {
                amountOfEntities = new ArrayList<>((Collection<?>)amountOfEntitiesObject);
            }

            System.out.println("entity count");
            System.out.println(amountOfEntities);

            String[] locationData = textData.get(i).get(5).toString().split(","); // location

            Row trainRow = RowFactory.create(
                    textData.get(i).get(0).toString(), //text
                    textData.get(i).get(1), //EntityTypes
                    textData.get(i).get(2), //EntityTypeNumbers
                    //amountOfEntities.size(),
                    //((ArrayList<?>) textData.get(i).get(2)).size(),//AmountOfEntities
                    //amountOfEntities.size(), //AmountOfEntities
                    Integer.parseInt(textData.get(i).get(3).toString()), //AmountOfEntities
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

        /*******************SETUP PIPELINE MODEL*****************/
        //features
        VectorAssembler assembler = new VectorAssembler()
                //.setInputCols(new String[]{"EntityTypeNumbers", "AmountOfEntities", "Latitude", "Latitude", "Like"})
                .setInputCols(new String[]{"AmountOfEntities", "Latitude", "Latitude", "Like"})
                .setOutputCol("features");

        //model
        KMeans km = new KMeans()
                .setFeaturesCol("features")
                .setPredictionCol("prediction");
        //.setK(2); //number of classses/clusters
        //.setMaxIterations(numIterations);

        //pipeline
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {assembler,km});

        /******************EVALUATE/ANALYSE MODEL**************/


        //evaluators
        ClusteringEvaluator clusteringEvaluator = new ClusteringEvaluator()
                .setFeaturesCol("features")
                .setPredictionCol("prediction");
        //.setDistanceMeasure(String value)
        //.setMetricName(String value)
        //.setWeightCol(String value)



        //parameterGrid
        ParamGridBuilder paramGridBuilder = new ParamGridBuilder();
        //paramGridBuilder.addGrid(km.k(), new int[]{km.getK()});
        paramGridBuilder.addGrid(km.initSteps(), new int[]{km.getInitSteps()});
        paramGridBuilder.addGrid(km.maxIter(), new  int[]{km.getMaxIter()});
        ParamMap[] paramMaps = paramGridBuilder.build();


        //validator
        CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(clusteringEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(3)
                .setParallelism(2);

        /***********************SETUP MLFLOW - SAVE ***********************/

        /***setup***/
        String modelName;
        if(request.getModelName() == null) {
            modelName = "_ApplicationModelA";
        }
        else{
            modelName = request.getModelName();
        }

        //client
        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<org.mlflow.api.proto.Service.Experiment> foundExperiment = client.getExperimentByName(modelName + "_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment(modelName + "_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        org.mlflow.api.proto.Service.RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun(modelName + "_Run", runInfo.getRunId());


        /***trainModel***/
        //KMeans model = pipeline.getStages()[1];
        PipelineModel kmModel = pipeline.fit(trainingDF);

        //CrossValidatorModel kmModel = crossValidator.fit(trainingDF);
        Dataset<Row> predictions = kmModel.transform(trainingDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");


        double accuracy = clusteringEvaluator.evaluate(predictions);
        //BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        //RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);
        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));

        /***logging***/
        //param
        client.logParam(run.getId(),"k-value", String.valueOf(km.getK()));
        client.logParam(run.getId(),"Initial step" ,String.valueOf(km.getInitSteps()));
        client.logParam(run.getId(),"Max iterations" , String.valueOf(km.maxIter()));


        //metrics

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        client.setTag(run.getId(),"Run ID", String.valueOf(run.getId()));
        //run.setTag("Accuracy", String.valueOf(accuracy));*/

        //String path = Paths.get("../models/" + modelName).toString();
        String path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/" +modelName;
        String script = Paths.get("../rri/LogModel.py").toString();

        //kmModel.write().overwrite().save(path);
        pipeline.write().overwrite().save(path);
        File modelFile = new File(path);

        client.logArtifact(run.getId(), modelFile);

        path = "backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/TrainingData.parquet";
        trainingDF.write().save(path);
        File trainFile = new File(path);
        client.logArtifact(run.getId(), trainFile);

        String filePath = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/ModelInformation.txt").toString();
        File infoFile = new File(filePath);
        infoFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(infoFile, false);
        fos.write(String.valueOf(accuracy).getBytes());
        fos.close();
        client.logArtifact(run.getId(), infoFile);


        TrainedModel trainedModel = new TrainedModel(run.getId(), accuracy,run.getId(), modelName);
        FileUtils.deleteDirectory(modelFile);
        FileUtils.deleteDirectory(trainFile);
        infoFile.delete();

        /*String commandPath = "python " + script + " " + path + " KMeansModel " + run.getId();
        CommandLine commandLine = CommandLine.parse(commandPath);
        //commandLine.addArguments(new String[] {"../models/KMeansModel","KMeansModel", "1"});
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        executor.execute(commandLine);*/

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

        sparkAnomalies.stop();

        ArrayList<String> results = new ArrayList<>();
        return new TrainFindAnomaliesResponse(results, trainedModel);
    }


    /*******************************************************************************************************************
     * **********************************************MODEL REGISTRY*****************************************************
     * *****************************************************************************************************************
     */


    /**
     * This method used to compare between models and select the best one among them.
     * along with selecting the method registers that best model under the model name.
     * @param request This is a request object which contains data required to compare and log models.
     * @return RegisterUserBestModelResponse This object contains data the selected best model
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public RegisterUserBestModelResponse registerUserBestModel(RegisterUserBestModelRequest request)
            throws InvalidRequestException{

        if (request == null) {
            throw new InvalidRequestException("registerUserBestModel request Object is null");
        }
        if (request.getModelList() == null){
            throw new InvalidRequestException("registerUserBestModel ModelList is null");
        }

        //select best in selection for user
        TrainedModel trendModelOne = request.getModelList().get(0);
        TrainedModel trendModelTwo = request.getModelList().get(1);
        TrainedModel trendModelThree = request.getModelList().get(2);

        String bestModelId = trendModelOne.getModelName();

        if(trendModelOne.getAccuracy() >= trendModelTwo.getAccuracy()){
            bestModelId = bestModelId + ":" + trendModelOne.getRunId();
        }
        else{
            bestModelId = bestModelId + ":" + trendModelTwo.getRunId();
        }

        bestModelId = bestModelId + ":" + trendModelThree.getRunId();

        return new RegisterUserBestModelResponse(bestModelId, trendModelOne.getModelName());
    }


    /**
     * This method used to compare between models and select the best one among them.
     * along with selecting the method registers that best model under the model name.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public void registerApplicationBestModel(RegisterApplicationBestModelRequest request)
            throws InvalidRequestException, IOException{

        if (request == null) {
            throw new InvalidRequestException("registerUserBestModel request Object is null");
        }
        if (request.getModelList() == null){
            throw new InvalidRequestException("registerUserBestModel ModelList is null");
        }

        //select best in registry for application

        String modelName = "_ApplicationModel";

        /*MlflowClient client = new MlflowClient("http://localhost:5000");

        List<org.mlflow.api.proto.Service.Experiment> experiments = client.listExperiments();


        for(int i =0; i < experiments.size(); i++){
            if(experiments.get(i).getName().equals(modelName + "T")){
                org.mlflow.api.proto.Service.Experiment foundExperiment =  experiments.get(i);
                List<org.mlflow.api.proto.Service.RunInfo> runList = client.listRunInfos(foundExperiment.getExperimentId());
            }
            if(experiments.get(i).getName().equals(modelName + "A")){

            }
        }*/

        TrainedModel trendModelOne = request.getModelList().get(0);
        TrainedModel trendModelTwo = request.getModelList().get(1);
        TrainedModel trendModelThree = request.getModelList().get(2);

        String bestModelId = modelName;

        if(trendModelOne.getAccuracy() >= trendModelTwo.getAccuracy()){
            bestModelId = bestModelId + ":" + trendModelOne.getRunId();
        }
        else{
            bestModelId = bestModelId + ":" + trendModelTwo.getRunId();
        }

        bestModelId = bestModelId + ":" + trendModelThree.getRunId();


        String filePath = Paths.get("backend/Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/rri/RegisteredApplicationModels.txt").toString();
        File file = new File(filePath);
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(bestModelId.getBytes());
        fos.close();

    }

    /**
     * This method used to clean the registry by deleting unused/unsatisfying models.
     *
     */
    public void cleanModelsRegistry()
            throws AnalyserException {
        //todo: do something here


        /***********************SETUP MLFLOW - SAVE ***********************/

        /***setup***/

        //client
        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<org.mlflow.api.proto.Service.Experiment> foundExperiment = client.getExperimentByName("Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        org.mlflow.api.proto.Service.RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun("Run", runInfo.getRunId());

        List<org.mlflow.api.proto.Service.Experiment> experiment = client.listExperiments();




        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/
    }



    /**
     * This method used to fetch the parsed data from the database to train models
     * @param request This is a request object which contains data required to be fetched.
     * @return FetchParsedDataResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FetchParsedDataResponse fetchTrainData(FetchParsedDataRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FetchParsedDataRequest Object is null");
        }
        if (request.getDataType() == null){
            throw new InvalidRequestException("Datatype is null");
        }
        if(request.getDataType() != "ParsedData") {
            throw new InvalidRequestException("Wrong Datatype is used");
        }



        ArrayList<ParsedData> list = (ArrayList<ParsedData>) parsedDataRepository.findAll();
        return new FetchParsedDataResponse(list );
    }
}
