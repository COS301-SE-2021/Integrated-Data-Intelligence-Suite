package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedArticle;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceAIModelRepository;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceParsedDataRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;

//import edu.stanford.nlp.ling.CoreAnnotations;
//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.pipeline.*;

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

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.*;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.*;
//import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.tuning.*;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import org.mlflow.tracking.ActiveRun;
import org.mlflow.tracking.MlflowClient;
import org.mlflow.tracking.MlflowContext;
import org.mlflow.api.proto.Service.Experiment;
import org.mlflow.api.proto.Service.RunInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.JavaConversions;
import scala.collection.mutable.WrappedArray;


import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.spark.sql.functions.*;

@Service
public class AnalyseServiceImpl {


    @Autowired
    private AnalyseServiceParsedDataRepository parsedDataRepository;

    @Autowired
    private AnalyseServiceAIModelRepository aiModelRepository;

    static final Logger logger = Logger.getLogger(AnalyseServiceImpl.class);

    /**
     * This method used to analyse data which has been parsed by Parse-Service.
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public AnalyseDataResponse analyzeData(AnalyseDataRequest request)
            throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList of requested parsedData is null");
        }
        else{
            for(int i =0; i<request.getDataList().size(); i++) {
                if (request.getDataList().get(i) == null) {
                    throw new InvalidRequestException("DataList inside data of requested parsedData is null");
                }
            }
        }


        /*******************USE NLP******************/

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

        /**articles**/
        ArrayList<ParsedArticle> articleList = request.getArticleList();
        if (articleList.isEmpty()) System.out.println("no articles");
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

        /**article**/
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


        FindPatternRequest findPatternRequest = new FindPatternRequest(parsedDataList,parsedArticleList); //TODO
        FindPatternResponse findPatternResponse = this.findPattern(findPatternRequest);

        FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(parsedDataList,parsedArticleList);
        FindRelationshipsResponse findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);

        GetPredictionRequest getPredictionRequest = new GetPredictionRequest(parsedDataList); //TODO
        GetPredictionResponse getPredictionResponse = this.getPredictions(getPredictionRequest);

        FindTrendsRequest findTrendsRequest = new FindTrendsRequest(parsedDataList);
        FindTrendsResponse findTrendsResponse = this.findTrends(findTrendsRequest);

        FindAnomaliesRequest findAnomaliesRequest = new FindAnomaliesRequest(parsedDataList);
        FindAnomaliesResponse findAnomaliesResponse = this.findAnomalies(findAnomaliesRequest);


        /*********************Result**************************/

        //TrainFindTrendsArticlesRequest findTrendsArticlesRequest = new TrainFindTrendsArticlesRequest(parsedDataList);
        // trainFindTrendsArticlesLR(findTrendsArticlesRequest);

        //TrainFindTrendsRequest findTrendsRequest = new TrainFindTrendsRequest(parsedDataList);
        //TrainFindTrendsResponse findTrendsResponse = this.trainFindTrends(findTrendsRequest);

        //TrainFindTrendsDTRequest findTrendsDTRequest = new TrainFindTrendsDTRequest(parsedDataList);
        //this.trainFindTrendsDecisionTree(findTrendsDTRequest);

        //TrainFindAnomaliesRequest findAnomaliesRequest = new TrainFindAnomaliesRequest(parsedDataList);
        //TrainFindAnomaliesResponse findAnomaliesResponse = this.trainFindAnomalies(findAnomaliesRequest);


        return new AnalyseDataResponse(//null,null,null,null,null,null);
                findPatternResponse.getPattenList(),//null,null,null,null);
               findRelationshipsResponse.getPattenList(),
                getPredictionResponse.getPattenList(),
                findTrendsResponse.getPattenList(),
                findAnomaliesResponse.getPattenList(),
                wordList);
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

        SparkSession sparkNlpProperties = SparkSession
                .builder()
                .appName("NlpProperties")
                .master("local")
                .getOrCreate();

        /*******************SETUP DATA*****************/

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

        DocumentAssembler document_assembler = (DocumentAssembler) new DocumentAssembler().setInputCol("text").setOutputCol("document");
        Dataset<Row> data2 = document_assembler.transform(data);

        SentenceDetectorDLModel sentence_detector = (SentenceDetectorDLModel) ((SentenceDetectorDLModel) new SentenceDetectorDLModel().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence"); //"sentence_detector_dl", "en"
        Dataset<Row> data3 = sentence_detector.transform(data2);

        TokenizerModel tokenizer =  ((Tokenizer) ((Tokenizer) new Tokenizer().setInputCols(new String[] {"document"})) .setOutputCol("token")).fit(data3);

        NorvigSweetingModel checker = (NorvigSweetingModel) ((NorvigSweetingModel) new NorvigSweetingModel().pretrained().setInputCols(new String[]{"token"})).setOutputCol("Checked"); //checked = token

        WordEmbeddingsModel embeddings = (WordEmbeddingsModel) ((WordEmbeddingsModel) new WordEmbeddingsModel().pretrained().setInputCols(new String[] {"document", "token"})).setOutputCol("embeddings");

        UniversalSentenceEncoder sentenceEmbeddings = (UniversalSentenceEncoder) ((UniversalSentenceEncoder) new UniversalSentenceEncoder().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence_embeddings");

        SentimentDLModel sentimentDetector = (SentimentDLModel) ((SentimentDLModel) new SentimentDLModel().pretrained().setInputCols(new String[] {"sentence_embeddings"})).setOutputCol("sentiment");

        NerDLModel ner = (NerDLModel) ((NerDLModel) new NerDLModel().pretrained().setInputCols(new String[] {"document", "token", "embeddings"})).setOutputCol("ner");

        NerConverter converter = (NerConverter) ((NerConverter) new NerConverter().setInputCols(new String[]{"document", "token", "ner"})).setOutputCol("chunk");

        //pipeline
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{document_assembler, sentence_detector , tokenizer, checker, embeddings, sentenceEmbeddings, sentimentDetector, ner ,converter /*normalizer, lemmatizer, finisher*/});


        PipelineModel pipelineFit = pipeline.fit(data);
        Dataset<Row> results = pipelineFit.transform(data);


        /*******************READ MODEL DATA*****************/

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



        /**OLD NLP
         Properties properties = new Properties();
         String pipelineProperties = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
         properties.setProperty("annotators", pipelineProperties);
         StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);
         CoreDocument coreDocument = new CoreDocument(request.getText());
         stanfordCoreNLP.annotate(coreDocument);
         //List<CoreSentence> coreSentences = coreDocument.sentences();
         /**output of analyser**
         List<CoreSentence> coreSentences = coreDocument.sentences();
         List<CoreLabel> coreLabels = coreDocument.tokens();
         ArrayList<String> row = new ArrayList<>();
         //get sentiment of text
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
         }
         //get parts of named entity
         ArrayList<ArrayList> nameEntities = new ArrayList<>();
         for (CoreEntityMention em : coreDocument.entityMentions()) {
         row = new ArrayList<>();
         row.add(em.text());
         row.add(em.entityType());
         nameEntities.add(row);
         }*/


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

        /*******************SETUP SPARK*****************

         SparkSession sparkPatterns = SparkSession
         .builder()
         .appName("Pattern")
         .master("local")
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

        sparkPatterns.stop();*/

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

        FPGrowth fp = new FPGrowth()
                .setItemsCol("Entities")
                .setMinSupport(0.10)
                .setMinConfidence(0.10);

        FPGrowthModel fpModel = fp.fit(itemsDF);


        fpModel.freqItemsets().show(1000);
        fpModel.associationRules().show(1000);

        List<Row> pData = fpModel.associationRules().select("antecedent","consequent","confidence","support").collectAsList();
        ArrayList<ArrayList> results = new ArrayList<>();

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

        sparkPatterns.stop();
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
    public TrainFindTrendsResponse trainFindTrends(TrainFindTrendsRequest request)
            throws InvalidRequestException, IOException {

        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        logger.setLevel(Level.ERROR);
        LogManager.getRootLogger().setLevel(Level.ERROR);

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
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
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.DoubleType, false, Metadata.empty()),
                });

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });


        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2);

        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

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

        Optional<Experiment> foundExperiment = client.getExperimentByName("LogisticRegression_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("LogisticRegression_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        RunInfo runInfo = client.createRun(experimentID);
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

        lrModel.write().overwrite().save("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");


        File modelFile = new File("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");
        //client.logArtifact(run.getId(), modelFile);
        /*try {
            //lrModel.save("Database");


            //File modelFile = new File("../models/LogisticRegressionModel");
            //client.logArtifact(run.getId(), modelFile);

            //TODO: flavor

            String commandPath = "python ../rri/LogModel.py ../models/LogisticRegressionModel LogisticRegressionModel";
            CommandLine commandLine = CommandLine.parse(commandPath);
            //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(new PumpStreamHandler(System.out));
            executor.execute(commandLine);
            /*try {
                executor.execute(commandLine);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }*

            //TODO: flavor


            //client.logArtifact(run.getId(), modelFile);

            //File artifact = client.downloadModelVersion("LogisticRegressionModel", "1");

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
                    .build();*

        }catch (Exception e){
            e.printStackTrace();
        }*/

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

        ArrayList<ArrayList> results = new ArrayList<>();
        return new TrainFindTrendsResponse(results);
    }

    public void trainFindTrendsArticlesLR(TrainFindTrendsArticlesRequest request) throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        logger.setLevel(Level.ERROR);
        LogManager.getRootLogger().setLevel(Level.ERROR);

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
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

        Optional<Experiment> foundExperiment = client.getExperimentByName("LogisticRegression_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("LogisticRegression_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        RunInfo runInfo = client.createRun(experimentID);
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

        lrModel.write().overwrite().save("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");
        try {
            //lrModel.save("Database");

            File modelFile = new File("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");

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

        ArrayList<ArrayList> results = new ArrayList<>();
    }

    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return void
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public void trainFindTrendsDecisionTree( TrainFindTrendsDTRequest request)
            throws InvalidRequestException, IOException {

        if (request == null) {
            throw new InvalidRequestException("FindTrendsDTRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/
        logger.setLevel(Level.ERROR);
        LogManager.getRootLogger().setLevel(Level.ERROR);

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
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
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.DoubleType, false, Metadata.empty()),
                });

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });


        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2);

        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity
        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

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


        //validator
        /*CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(2);*/

        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(multiclassClassificationEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);


        /***********************SETUP MLFLOW - SAVE ***********************/

        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<Experiment> foundExperiment = client.getExperimentByName("DecisionTree_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("DecisionTree_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun("DecisionTree_Run", runInfo.getRunId());


        TrainValidationSplitModel dtModel = trainValidationSplit.fit(trainSetDF);
        Dataset<Row> predictions = dtModel.transform(testSetDF);
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");

        double accuracy = multiclassClassificationEvaluator.evaluate(predictions);
        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));
        //System.out.println("Test Error = " + (1.0 - accuracy));

        MulticlassMetrics multiclassMetrics = multiclassClassificationEvaluator.getMetrics(predictions);



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


        client.logMetric(run.getId(),"Hamming loss" , multiclassMetrics.hammingLoss());

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));

        dtModel.write().overwrite().save("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/DecisionTreeModel");
        File modelFile = new File("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/DecisionTreeModel");
        //client.logArtifact(run.getId(), modelFile);

        try {

            //File modelFile = new File("../models/DecisionTreeModel");
            //client.logArtifact(run.getId(), modelFile);

            String commandPath = "python ../rri/LogModel.py ../models/DecisionTreeModel DecisionTreeModel";
            CommandLine commandLine = CommandLine.parse(commandPath);
            //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(new PumpStreamHandler(System.out));
            executor.execute(commandLine);
            /*try {
                executor.execute(commandLine);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }





        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************

        ArrayList<ArrayList> results = new ArrayList<>();
        return new TrainFindTrendsResponse(results);*/
    }


    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return FindTrendsResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindTrendsResponse findTrends(FindTrendsRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        logger.setLevel(Level.ERROR);

        LogManager.getRootLogger().setLevel(Level.ERROR);

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


        /*******************LOAD - READ MODEL*****************/

        TrainValidationSplitModel lrModel = TrainValidationSplitModel.load("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/LogisticRegressionModel");
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

        return new FindTrendsResponse(results);
    }



    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a prediction...
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
                .getOrCreate();

        /*******************SETUP DATA*****************/

        /*******************SETUP MODEL*****************/

        /*******************READ MODEL OUTPUT*****************/

        return new TrainGetPredictionResponse(null);
    }


    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a prediction...
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
         .getOrCreate();

         /*******************SETUP DATA*****************/


        /*******************SETUP MODEL*****************/



        /******************Analyse Model Accuracy**************/


        /*******************READ MODEL OUTPUT*****************/


        return new GetPredictionResponse(null);
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

        /*******************SETUP SPARK*****************/

        SparkSession sparkAnomalies = SparkSession
                .builder()
                .appName("Anomalies")
                .master("local")
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

        /*******************SETUP PIPELINE*****************/
        /*******************SETUP MODEL*****************/
        //features

        VectorAssembler assembler = new VectorAssembler()
                //.setInputCols(new String[]{"EntityTypeNumbers", "AmountOfEntities", "Latitude", "Latitude", "Like"})
                .setInputCols(new String[]{"AmountOfEntities", "Latitude", "Latitude", "Like"})
                .setOutputCol("features");

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

        /*TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(clusteringEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.7)  //70% : 30% ratio
                .setParallelism(2);*/


        /***********************SETUP MLFLOW - SAVE ***********************/

        MlflowClient client = new MlflowClient("http://localhost:5000");

        Optional<Experiment> foundExperiment = client.getExperimentByName("KMeans_Experiment");
        String experimentID = "";
        if (foundExperiment.isEmpty() == true){
            experimentID = client.createExperiment("KMeans_Experiment");
        }
        else{
            experimentID = foundExperiment.get().getExperimentId();
        }

        RunInfo runInfo = client.createRun(experimentID);
        MlflowContext mlflow = new MlflowContext(client);
        ActiveRun run = mlflow.startRun("KMeans_Run", runInfo.getRunId());

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

        //param
        client.logParam(run.getId(),"k-value", String.valueOf(km.getK()));
        client.logParam(run.getId(),"Initial step" ,String.valueOf(km.getInitSteps()));
        client.logParam(run.getId(),"Max iterations" , String.valueOf(km.maxIter()));


        //metrics

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        //run.setTag("Accuracy", String.valueOf(accuracy));*/

        kmModel.write().overwrite().save("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel");
        File modelFile = new File("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel");
        //client.logArtifact(run.getId(), modelFile);
        try {


            //File modelFile = new File("../models/KMeansModel");
            //client.logArtifact(run.getId(), modelFile);

            String commandPath = "python ../rri/LogModel.py ../models/KMeansModel KMeansModel";
            CommandLine commandLine = CommandLine.parse(commandPath);
            //commandLine.addArguments(new String[] {"../models/LogisticRegressionModel","LogisticRegressionModel", "1"});
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(new PumpStreamHandler(System.out));
            executor.execute(commandLine);
            /*try {
                executor.execute(commandLine);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

        ArrayList<String> results = new ArrayList<>();
        return new TrainFindAnomaliesResponse(results);
    }

    /**
     * This method used to find a anomalies(s) within a given data.
     * A Anomaly is an outlier in the data, in the context of the data e.g elon musk was trending the whole except one specific date.
     * @param request This is a request object which contains data required to be analysed.
     * @return findAnomaliesResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindAnomaliesResponse findAnomalies(FindAnomaliesRequest request)
            throws InvalidRequestException {
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

        /*******************LOAD & READ MODEL*****************/
        PipelineModel kmModel = PipelineModel.load("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel");

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

        return new FindAnomaliesResponse(results);
    }


    /*******************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     */

    /**
     * This method used to fetch the parsed data from the database
     * @param request This is a request object which contains data required to be fetched.
     * @return FetchParsedDataResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FetchParsedDataResponse fetchParsedData(FetchParsedDataRequest request)
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





