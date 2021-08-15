package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.AnalyseRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.*;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;

import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.struct;

import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyseServiceImpl {


    @Autowired
    private AnalyseRepository repository;

    static final Logger logger = Logger.getLogger(AnalyseServiceImpl.class);

    /**
     * This method used to analyse data which has been parsed by Parse-Service.
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public AnalyseDataResponse analyzeData(AnalyseDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        ArrayList<ParsedData> dataList =  request.getDataList();

        //set data
        ArrayList<ArrayList> trendDatalist = new ArrayList<>();

        /*******************Find pattern******************/


        ArrayList<String> sentiments = new ArrayList<>();
        ArrayList<String> dataSendList = new ArrayList<>();
        for (int i=0 ;i < dataList.size(); i++){
            String row = "";

            String text = dataList.get(i).getTextMessage();
            String location = dataList.get(i).getLocation();
            String date = dataList.get(i).getDate();
            System.out.println("HERE IS THE DATE1 _______________________________ : " + date);
            date = date.replaceAll("\\s+","/");

            System.out.println("HERE IS THE DATE2 _______________________________ : " + date);
            String likes = String.valueOf(dataList.get(i).getLikes());

            FindSentimentRequest sentimentRequest = new FindSentimentRequest(dataList.get(i).getTextMessage());
            FindSentimentResponse sentimentResponse = this.findSentiment(sentimentRequest);


            row = sentimentResponse.getSentiment().getCssClass() + " " + date + " "+ likes;
            ArrayList<String> rowforTrends = new ArrayList<>();
            rowforTrends.add(text);
            rowforTrends.add(location);
            rowforTrends.add(date);
            rowforTrends.add(dataList.get(i).getLikes().toString());
            rowforTrends.add(sentimentResponse.getSentiment().getCssClass());

            trendDatalist.add(rowforTrends);

            dataSendList.add(row);
        }

        FindPatternRequest findPatternRequest = new FindPatternRequest(dataSendList);
        FindPatternResponse findPatternResponse = this.findPattern(findPatternRequest);

        FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(trendDatalist);
        FindRelationshipsResponse findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);

        GetPredictionRequest getPredictionRequest = new GetPredictionRequest(dataSendList);
        GetPredictionResponse getPredictionResponse = this.getPredictions(getPredictionRequest);

        FindTrendsRequest findTrendsRequest = new FindTrendsRequest(trendDatalist);
        FindTrendsResponse findTrendsResponse = this.findTrends(findTrendsRequest);

        return new AnalyseDataResponse(findPatternResponse.getPattenList(), findRelationshipsResponse.getPattenList(), getPredictionResponse.getPattenList());
    }


    /**
     * This method used to find a pattern(s) within a given data,
     * A pattern is found when there's a relation,trend, anamaly etc found as a patten; [relationship,trend,number_of_likes]
     * @param request This is a request object which contains data required to be analysed.
     * @return FindPatternResponse This object contains data of the patterns found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindPatternResponse findPattern(FindPatternRequest request) throws InvalidRequestException {
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

        ArrayList<String> reqData = request.getDataList();
        List<Row> patternData = new ArrayList<>();

        for(int i=0; i < reqData.size(); i++){
            patternData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }

        /*******************SETUP MODEL*****************/

        StructType schema = new StructType(new StructField[]{ new StructField(
                "items", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });
        Dataset<Row> itemsDF = sparkPatterns.createDataFrame(patternData, schema);

        FPGrowthModel model = new FPGrowth() //pipeline/estimator-model , [can use transformers too]-dataframe,
                .setItemsCol("items")
                .setMinSupport(0.5)
                .setMinConfidence(0.6)
                .fit(itemsDF);
        //LogManager.getRootLogger().setLevel(Level.OFF); //TODO: what this for?

        /*******************READ MODEL OUTPUT*****************/

        /*model.freqItemsets().show();

        //Display generated association rules.
        model.associationRules().show();

        Double  oi = (Double) Adata.get(0).get(2);
        System.out.println(Adata.get(0).getList(0).toString());*/

        /* transform examines the input items against all the association rules and summarize the consequent as a prediction
        model.transform(itemsDF).show();*/

        List<Row> pData = model.associationRules().select("antecedent","consequent","confidence","support").collectAsList();
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
    public FindRelationshipsResponse findRelationship(FindRelationshipsRequest request) throws InvalidRequestException {
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

        /*for (int i = 0; i < 100; i++) {
            //MOCK DATASET WITH 5 "features"
            ArrayList<String> attempt = new ArrayList<>();
            for (int j = 0 ; j < 5 ; j++){
                int unique = (int)(Math.random()*(9)+1);
                String adding = Integer.toString(unique);
                for (int k = 0; k < j; k ++)
                    adding+= "i";
                attempt.add(adding);
            }
            relationshipData.add(RowFactory.create(attempt));
        }*/

        List<Row> relationshipData  = new ArrayList<>();
        ArrayList<ArrayList> requestData = request.getDataList();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(requestData.get(i).get(0).toString());
            FindNlpPropertiesResponse findNlpPropertiesResponse = this.findNlpProperties(findNlpPropertiesRequest);

            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();
            row = new ArrayList<>();
            for (int j=0; j< namedEntities.size(); j++){
                row.add(namedEntities.get(j).get(0).toString()); //entity-name

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
        itemsDF.show();

        /*******************SETUP MODEL*****************/

        FPGrowthModel model = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.10)
                .setMinConfidence(0.6)
                .fit(itemsDF);

        model.freqItemsets().show();

        /*******************READ MODEL OUTPUT*****************/

        List<Row> Rdata = model.freqItemsets().collectAsList();


        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < Rdata.size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < Rdata.get(i).getList(0).size(); j++){
                row.add(Rdata.get(i).getList(0).get(j).toString());
            }
            row.add(Rdata.get(i).get(1).toString());
            results.add(row);
        }
        System.out.println(results.toString());

        sparkRelationships.stop();

        return new FindRelationshipsResponse(results);
    }

    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     * @param request This is a request object which contains data required to be analysed.
     * @return FindTrendsResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindTrendsResponse findTrends(FindTrendsRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null){
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

        logger.setLevel(Level.ERROR);

        LogManager.getRootLogger().setLevel(Level.ERROR);

        //Logger rootLoggerM = LogManager.getRootLogger();
        //rootLoggerM.setLevel(Level.ERROR);


        /*Logger rootLoggerL = Logger.getRootLogger();
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

        /*for(int i=0; i < requestData.size(); i++){
            trendsData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }*/

        //ArrayList<ArrayList> formatedData = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();

        for(int i=0; i < requestData.size(); i++){
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(requestData.get(i).get(0).toString());
            FindNlpPropertiesResponse findNlpPropertiesResponse = this.findNlpProperties(findNlpPropertiesRequest);

            String sentiment = findNlpPropertiesResponse.getSentiment();
            ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();


            for (int j=0; j< namedEntities.size(); j++){
                //row.add(isTrending)
                row = new ArrayList<>();
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
               // row.add(sentiment);//PoS

                Row trendRow = RowFactory.create(row.toArray());
                trendsData.add(trendRow );
            }
        }
        //System.out.println(trendsData);
        /*Example of input ArrayList of Tweets

        * [Elon Musk works at Google, hatfield, 20/05/20201, 456],
        * [Elon Musk works at Amazon, hatfield, 20/05/20201, 44],
        * [Elon Musk works at awesome, hatfield, 20/05/20201, 22],
        * [Google is a great place, hatfield, 20/05/20201, 45644],
        * [i like Microsoft, hatfield, 20/05/20201, 34]
        *
        *
        * */
        /* Example output of trendsData
            [[Elon Musk, PERSON, hatfield, 20/05/20201, 456, Neutral],
            [Google, ORGANIZATION, hatfield, 20/05/20201, 456, Neutral],
            [Elon Musk, PERSON, hatfield, 20/05/20201, 44, Neutral],
            [Amazon, ORGANIZATION, hatfield, 20/05/20201, 44, Neutral],
            [Elon Musk, PERSON, hatfield, 20/05/20201, 22, Positive],
            [Google, ORGANIZATION, hatfield, 20/05/20201, 45644, Positive],
            [Microsoft, ORGANIZATION, hatfield, 20/05/20201, 34, Neutral]]
        */

        /*ArrayList<ArrayList> structureData = new ArrayList<>();

        ArrayList<String> FoundEntities = new ArrayList<>();
        ArrayList<Float> Totallikes = new ArrayList<>();

        for (int k = 0; k < trendsData.size(); k++){
            ArrayList<String> r = new ArrayList<>();
            String en = trendsData.get(k).get(0).toString();//Entity name eg. Elon Musk
            Float likes = Float.parseFloat(trendsData.get(k).get(4).toString()); // geting Number of likes

            if (structureData.isEmpty()){
                FoundEntities.add(en);//Registering Enitiy eg. Elon Musk
                Totallikes.add(likes);
                r.add("0");// is Trending
                r.add(trendsData.get(k).get(1).toString());//Entity type
                r.add("1");//Frequency of Enitity in dataset
                r.add("1");// Rate of tweets per hour
                r.add(Float.toString(likes));// average likes
                structureData.add(r);
            }else{
                boolean found = false;
                int pos = 0;
                /********Check if Entity is in the list of registerd enitities **********/
                /*for (int x = 0; x < FoundEntities.size(); x++){
                    if (en.equals(FoundEntities.get(x))){
                        found = true;
                        pos = FoundEntities.indexOf(en);
                        break;
                    }
                }

                if (found){
                    ArrayList<String> temp = structureData.get(pos);
                    int freq = Integer.parseInt(temp.get(2));
                    freq++;
                    temp.set(2,Integer.toString(freq));   ///increasing frequecy

                    float avglikes = Totallikes.get(pos) + likes;
                    Totallikes.set(pos,avglikes);
                    avglikes = avglikes/freq;
                    temp.set(4,Float.toString(avglikes));///changing average

                    structureData.set(pos,temp);
                }else {
                    FoundEntities.add(en); //Registering Enitiy eg. Elon Musk
                    Totallikes.add(likes);
                    r.add("0");// is Trending
                    r.add(trendsData.get(k).get(1).toString());//Entity type
                    r.add("1");//Frequency of Enitity in dataset
                    r.add("1");// Rate of tweets per hour
                    r.add(Float.toString(likes));// average likes
                    structureData.add(r);
                }
            }
        }
        /*System.out.println(FoundEntities);
        System.out.println(Totallikes);
        System.out.println(structureData);*/

        /*Example output of Found Enities , Total Likes  structureData
            Found Enities
            1.Elon Musk,
            2.Google,
            3.Amazon,
            4.Microsoft

            Total Likes
            1) 522.0,
            2) 46100.0,
            3) 44.0,
            4) 34.0

            structureData
            1. [0, PERSON, 3, 1, 174.0],
            2. [0, ORGANIZATION, 2, 1, 23050.0],
            3. [0, ORGANIZATION, 1, 1, 44.0],
            4. [0, ORGANIZATION, 1, 1, 34.0]]

         */



        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(
                new StructField[]{
                        new StructField("IsTrending",  DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.FloatType, false, Metadata.empty()),
        });

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });

        //List<Row> strData = null; ///TODO Need to convert structureData Arraylist to of type ListRow
        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2); // .read().parquet("...");


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity

        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType" ,"EntityTypeNumber").count().collectAsList(); //frequency
        namedEntities.get(0); /*name entity*/
        namedEntities.get(1); /*name type*/
        namedEntities.get(2); /*name type-number*/
        namedEntities.get(3); /*name frequency*/

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

        //training set
        List<Row> trainSet = new ArrayList<>();
        for(int i=0; i < namedEntities.size(); i++){
            Row trainRow = RowFactory.create(
                    (int)Math.round(Math.random()),
                    namedEntities.get(i).get(0).toString(),
                    namedEntities.get(i).get(1).toString(),
                    Integer.parseInt(namedEntities.get(i).get(2).toString()),
                    Integer.parseInt(namedEntities.get(i).get(3).toString()),
                    rate.get(i).get(1).toString(),
                    Float.parseFloat(averageLikes.get(i).get(1).toString())
            );
            trainSet.add(trainRow);
        }

        //save to database

        //split data
        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");
        Dataset<Row> [] split = trainingDF.randomSplit((new double[]{0.7, 0.3}),5043);

        Dataset<Row> trainSetDF = split[0];
        Dataset<Row> testSetDF = split[1];


        //display
        itemsDF.show();
        System.out.println("/*******************Train Set*****************/");
        trainSetDF.show();
        System.out.println("/*******************Test Set*****************/");
        testSetDF.show();

        /*******************SETUP PIPELINE*****************/
        /*******************SETUP MODEL*****************/
        //features
        Tokenizer tokenizer = new Tokenizer()
                .setInputCol("text")
                .setOutputCol("words");

        HashingTF hashingTF = new HashingTF()
                .setNumFeatures(1000)
                .setInputCol(tokenizer.getOutputCol())
                .setOutputCol("features");

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber","Frequency", "AverageLikes"})
                .setOutputCol("features");

        Dataset<Row> testDF = assembler.transform(trainSetDF);

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");

        Dataset<Row> indexed = indexer.fit(testDF).transform(testDF);

        indexed.show();

        //model
        LogisticRegression lr = new LogisticRegression() //estimator
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(indexed);
        // Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: " + lrModel.coefficients() + " Intercept: " + lrModel.intercept());


        //pipeline
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[] {assembler,indexer,lr});

        // Fit the pipeline to training documents.
        PipelineModel model = pipeline.fit(trainSetDF);



        /******************Analyse Model Accuracy**************/
        //test
         Dataset<Row> test = null;

        Dataset<Row> predictions = model.transform(testSetDF);
        predictions.show();
        System.out.println("/*******************Predictions*****************/");


        for (Row r : predictions.select("isTrending").collectAsList())
            System.out.println("Trending -> " + r.get(0));


        BinaryClassificationEvaluator evaluator = new BinaryClassificationEvaluator()
                .setLabelCol("label")
                .setRawPredictionCol("prediction")
                .setMetricName("areaUnderROC");
        double accuracy = evaluator.evaluate(predictions);
        System.out.println("/**********************    Accuracy: "+ Double.toString(accuracy));

        /*******************summary (REMOVE)*****************/
        //summaries
        /* BinaryLogisticRegressionTrainingSummary trainingSummary = lrModel.binarySummary();

        // Obtain the loss per iteration.
        double[] objectiveHistory = trainingSummary.objectiveHistory();
        for (double lossPerIteration : objectiveHistory) {
            System.out.println(lossPerIteration);
        }
        //System.out.println("******************SomeStuff****************")'

        //Obtain the receiver-operating characteristic as a dataframe and areaUnderROC.
        Dataset<Row> roc = trainingSummary.roc();
        roc.show();
        roc.select("FPR").show();
        System.out.println(trainingSummary.areaUnderROC());

        // Get the threshold corresponding to the maximum F-Measure and rerun LogisticRegression with this selected threshold.
        Dataset<Row> fMeasure = trainingSummary.fMeasureByThreshold();
        double maxFMeasure = fMeasure.select(functions.max("F-Measure")).head().getDouble(0);
        double bestThreshold = fMeasure.where(fMeasure.col("F-Measure").equalTo(maxFMeasure))
                .select("threshold").head().getDouble(0);
        lrModel.setThreshold(bestThreshold);*/


        /*******************READ MODEL OUTPUT*****************/
        Dataset<Row> input = assembler.transform(testSetDF); //TODO this is an example of input will be changed once database is calibrated

        Dataset<Row> res = lrModel.transform(input);

        List<Row> rawResults = res.select("EntityName","prediction").collectAsList();

        System.out.println("/*******************Outputs begin*****************/");
        System.out.println(rawResults.toString());
        System.out.println("/*******************Outputs begin*****************/");


        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            ArrayList<Object> r = new ArrayList<>();
            r.add(rawResults.get(i).get(0).toString());
            r.add(Double.parseDouble(rawResults.get(i).get(1).toString()));
            results.add(r);
        }

        ArrayList<ArrayList> results2 = new ArrayList<>();
        return new FindTrendsResponse(results);
    }

    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a prediction...
     * @param request This is a request object which contains data required to be analysed.
     * @return GetPredictionResponse This object contains data of the predictions found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public GetPredictionResponse getPredictions(GetPredictionRequest request) throws InvalidRequestException {
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

        /*for (int i = 0; i < 100; i++) {
            //MOCK DATASET WITH 5 "features"
            ArrayList<String> attempt = new ArrayList<>();
            for (int j = 0 ; j < 5 ; j++){
                int unique = (int)(Math.random()*(9)+1);
                String adding = Integer.toString(unique);
                for (int k = 0; k < j; k ++)
                    adding+= "i";
                attempt.add(adding);
            }
            PredictionsData.add(RowFactory.create(attempt));
        }*/

        List<Row> predictionsData  = new ArrayList<>();
        ArrayList<String> reqData = request.getDataList();

        for(int i=0; i < reqData.size(); i++){
            predictionsData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }

        /*******************SETUP MODEL*****************/

        /*MOCK DATA USED TO MAKE Prediction
        List<Row> testData = Arrays.asList(
                RowFactory.create(Arrays.asList("1 2i 3ii".split(" ")))
        );

        //Dataset<Row> tesRes = sparkPredictions.createDataFrame(testData,schema);
        //Dataset<Row> Results = model.transform(itemsDF);

        //Results.show();*/

        /*StructType schema = new StructType(new StructField[]{
                new StructField("Tweets", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkPredictions.createDataFrame(predictionsData, schema);
        itemsDF.show();
        FPGrowthModel model = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.15)
                .setMinConfidence(0.6)
                .fit(itemsDF);*/


        String data = "mllib/layers/data.txt"; //TODO: default
        Dataset<Row> dataFrame = sparkPredictions.read().format("libsvm").load(data);

        // Split the data into train and test
        Dataset<Row>[] splits = dataFrame.randomSplit(new double[]{0.6, 0.4}, 1234L);
        Dataset<Row> train = splits[0];
        Dataset<Row> test = splits[1];

        // specify layers for the neural network:
        // input layer of size 4 (features), two intermediate of size 5 and 4
        // and output of size 3 (classes)
        int[] layers = new int[] {4, 5, 4, 3};

        // create the trainer and set its parameters
        MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier()
                .setLayers(layers)
                .setBlockSize(128)
                .setSeed(1234L)
                .setMaxIter(100);

        // train the model
        MultilayerPerceptronClassificationModel model = trainer.fit(train);

        /******************Analyse Model Accuracy**************/

        // compute accuracy on the test set
        Dataset<Row> result = model.transform(test);
        Dataset<Row> predictionAndLabels = result.select("prediction", "label");
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setMetricName("accuracy");

        System.out.println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels));

        /*******************READ MODEL OUTPUT*****************/

        //List<Row> pData = model.transform(itemsDF).collectAsList();
        List<Row> pData = new ArrayList<>(); //TODO: default
        ArrayList<ArrayList> results = new ArrayList<>();

        for (int i = 0; i < pData.size(); i++) {
            ArrayList<ArrayList> row = new ArrayList<>();
            ArrayList<String> col1 = new ArrayList<>();
            ArrayList<String> col2 = new ArrayList<>();

            for (int j = 0; j < pData.get(i).getList(0).size(); j++)
                col1.add(pData.get(i).getList(0).get(j).toString());

            for (int j = 0; j < pData.get(i).getList(1).size(); j++)
                col1.add(pData.get(i).getList(1).get(j).toString());

            row.add(col1);
            row.add(col2);

            results.add(row);
        }
        System.out.println(results.toString());

        sparkPredictions.stop();

        return new GetPredictionResponse(results);
    }

    /**
     * This method used to find a anomalies(s) within a given data.
     * A Anomaly is an outlier in the data, in the context of the data e.g elon musk was trending the whole except one specific date.
     * @param request This is a request object which contains data required to be analysed.
     * @return findAnomaliesResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindAnomaliesResponse findAnomalies(FindAnomaliesRequest request) throws InvalidRequestException {
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
            FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(requestData.get(i).get(0).toString());
            FindNlpPropertiesResponse findNlpPropertiesResponse = this.findNlpProperties(findNlpPropertiesRequest);

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();


            for (int j=0; j< namedEntities.size(); j++){
                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()){ //entity-typeNumber
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

                Row anomalyRow = RowFactory.create(row.toArray());
                anomaliesData.add(anomalyRow);
            }
        }

        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Date", DataTypes.StringType, false, Metadata.empty()),
                        //new StructField("FrequencyRatePerHour", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Sentiment", DataTypes.StringType, false, Metadata.empty()),
                });

        Dataset<Row> itemsDF = sparkAnomalies.createDataFrame(anomaliesData, schema);

        StructType schema2 = new StructType(
                new StructField[]{
                        new StructField("EntityName", DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityType",DataTypes.StringType, false, Metadata.empty()),
                        new StructField("EntityTypeNumber", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Frequency", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("Location", new ArrayType(DataTypes.StringType, true), false, Metadata.empty()),
                        new StructField("Sentiment", new ArrayType(DataTypes.StringType,true), false, Metadata.empty()),
                        new StructField("Date", new ArrayType(DataTypes.StringType, true), false, Metadata.empty()),
                        //new StructField("Likes", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("AverageLikes", DataTypes.FloatType, false, Metadata.empty()),
                });


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity

        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType" ,"EntityTypeNumber").count().collectAsList(); //frequency
        namedEntities.get(0); /*name entity*/
        namedEntities.get(1); /*name type*/
        namedEntities.get(2); /*name type-number*/
        namedEntities.get(3); /*name frequency*/

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        //List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();

        //training set
        List<Row> trainSet = new ArrayList<>();
        for(int i=0; i < namedEntities.size(); i++){

            String name = namedEntities.get(i).get(0).toString();

            List<Row> loc = itemsDF.select("Location").filter(col("EntityName").equalTo(name)).collectAsList();
            ArrayList<String> locations = new ArrayList<>();
            for(int j =0; j<  loc.size(); j++)
                locations.add(loc.get(0).toString());

            List<Row> sen = itemsDF.select("Sentiment").filter(col("EntityName").equalTo(name)).collectAsList();
            ArrayList<String> sentiments = new ArrayList<>();
            for(int j =0; j<  sen.size(); j++)
                sentiments.add(sen.get(0).toString());

            List<Row> da = itemsDF.select("Date").filter(col("EntityName").equalTo(name)).collectAsList();
            ArrayList<String> dates = new ArrayList<>();
            for(int j =0; j<  da.size(); j++)
                dates.add(da.get(0).toString());

            Row trainRow = RowFactory.create(
                    namedEntities.get(i).get(0).toString(), //EntityName
                    namedEntities.get(i).get(1).toString(), //EntityType
                    Integer.parseInt(namedEntities.get(i).get(2).toString()), //EntityTypeNumber
                    Integer.parseInt(namedEntities.get(i).get(3).toString()), //Frequency
                    locations, //Location
                    sentiments, //Sentiment
                    dates, //Date
                    Float.parseFloat(averageLikes.get(i).get(1).toString()) //AverageLikes
            );
            trainSet.add(trainRow);
        }


        Dataset<Row> trainingDF = sparkAnomalies.createDataFrame(trainSet, schema2);

        /*******************SETUP PIPELINE*****************/
        /*******************SETUP MODEL*****************/
        //features

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"Frequency", "AverageLikes"})
                .setOutputCol("features");

        Dataset<Row> testDF = assembler.transform(trainingDF);

        //model
     /*int numClusters = 2; //number of classses
     int numIterations = 20;
     //KMeansModel clusters = KMeans.train(testDF, numClusters, numIterations);*/

        KMeans km = new KMeans()
                //.setK(2) //number of classses/clusters
                .setFeaturesCol("features")
                .setPredictionCol("prediction");
        //.setMaxIterations(numIterations);

        KMeansModel kmModel = km.fit(testDF);

        Dataset<Row> summary=  kmModel.summary().predictions();


        summary.show();



        System.out.println(kmModel.summary().clusterSizes().toString());
        System.out.println("*******************************************************************************************");
        System.out.println("***************************************SUMMARY*********************************************");
        System.out.println("*******************************************************************************************");
        System.out.println("*******************************************************************************************");


        //summary.filter(col("prediction").


        //pipeline
        //Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {assembler,km});

        // Fit the pipeline to training documents.
        //PipelineModel model = pipeline.fit(trainingDF);

        /*******************summary (REMOVE)*****************/
        //summary
     /*System.out.println("Cluster centers:");
     for (Vector center: clusters.clusterCenters()) {
         System.out.println(" " + center);
     }
     double cost = clusters.computeCost(parsedData.rdd());
     System.out.println("Cost: " + cost);

     // Evaluate clustering by computing Within Set Sum of Squared Errors
     double WSSSE = clusters.computeCost(parsedData.rdd());
     System.out.println("Within Set Sum of Squared Errors = " + WSSSE);*/

        // Save and load model
     /*clusters.save(anomaliesSparkContext.sc(), "target/org/apache/spark/JavaKMeansExample/KMeansModel");
     KMeansModel sameModel = KMeansModel.load(anomaliesSparkContext.sc(),
             "target/org/apache/spark/JavaKMeansExample/KMeansModel");*/

        /*******************READ MODEL OUTPUT*****************/

     /*ArrayList<ArrayList> results = new ArrayList<>();
     Dataset<Row> input = assembler.transform(testSetDF); //TODO this is an example of input will be changed once database is calibrated

     Dataset<Row> res = lrModel.transform(input);

     List<Row> rawResults = res.select("EntityName","prediction").collectAsList();*/

        Dataset<Row> Results = summary.select("EntityName","prediction").filter(col("prediction").$greater(0));
        List<Row> rawResults = Results.select("EntityName","prediction").collectAsList();

        System.out.println("/*******************Outputs begin*****************/");
        System.out.println(rawResults.toString());
        System.out.println("/*******************Outputs begin*****************/");


        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            results.add(rawResults.get(0).get(i).toString());
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
     * This method used to find an entity of a statement i.e sentiments/parts of speech
     * @param request This is a request object which contains data required to be processed.
     * @return FindEntitiesResponse This object contains data of the entities found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindNlpPropertiesResponse findNlpProperties(FindNlpPropertiesRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getText() == null){
            throw new InvalidRequestException("Text is null");
        }

        /**setup analyser**/
        Properties properties = new Properties();
        String pipelineProperties = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
        properties.setProperty("annotators", pipelineProperties);
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);
        CoreDocument coreDocument = new CoreDocument(request.getText());
        stanfordCoreNLP.annotate(coreDocument);

        //List<CoreSentence> coreSentences = coreDocument.sentences();

        /**output of analyser**/
        List<CoreSentence> coreSentences = coreDocument.sentences();
        List<CoreLabel> coreLabels = coreDocument.tokens();
        ArrayList<String> row = new ArrayList<>();

        //get sentiment of text
        String sentiment;
        ArrayList<String> sentiments = new ArrayList<>();
        for (CoreSentence sentence : coreSentences )
            sentiments.add(sentence.sentiment());

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
        for (CoreLabel label : coreLabels){
            //String lemma = label.lemma();//lemmanation
            String pos = label.get(CoreAnnotations.PartOfSpeechAnnotation.class);; //parts of speech
            row = new ArrayList<>();
            row.add(label.toString());
            row.add(pos);
            partOfSpeech.add(row);

            //System.out.println("TOKEN : " + label.originalText());
        }

        //get parts of named entity
        ArrayList<ArrayList> nameEntities = new ArrayList<>();
        for (CoreEntityMention em : coreDocument.entityMentions()){
            row = new ArrayList<>();
            row.add(em.text());
            row.add(em.entityType());
            nameEntities.add(row);
        }

        FindNlpPropertiesResponse response = new FindNlpPropertiesResponse(sentiment, partOfSpeech, nameEntities);
        return response;
    }

    /**
     * This method used to fetch the parsed data from the database
     * @param request This is a request object which contains data required to be fetched.
     * @return FetchParsedDataResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FetchParsedDataResponse fetchParsedData(FetchParsedDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FetchParsedDataRequest Object is null");
        }
        if (request.getDataType() == null){
            throw new InvalidRequestException("Datatype is null");
        }

        if(request.getDataType() != "ParsedData") {
            return null;
        }


        ArrayList<ParsedData> list = (ArrayList<ParsedData>) repository.findAll();
        return new FetchParsedDataResponse(list );
    }


    /**
     * This method used to find a sentiment of a statement
     * @param request This is a request object which contains data required to be analysed.
     * @return FindSentimentResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindSentimentResponse findSentiment(FindSentimentRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindSentimentRequest Object is null");
        }
        if (request.getTextMessage() == null){
            throw new InvalidRequestException("Text is null");
        }


        String line = request.getTextMessage();

        //Setup the Core NLP
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);
        int mainSentiment = 0;

        //apply NLP to each tweet AKA line
        if (line != null && !line.isEmpty()) {
            int longest = 0;
            Annotation annotation = stanfordCoreNLP.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }

        TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
        return new FindSentimentResponse(tweetWithSentiment);
    }


    /**
     * Helper function, this method used to map sentiments
     * @param sentiment This is a int value which represent a sentiment.
     * @return String This is a String value which represents a sentiment.
     */
    private String toCss(int sentiment) {
        switch (sentiment) {
            case 0:
                return "Very_Negative";
            case 1:
                return "Negative";
            case 2:
                return "Neutral";
            case 3:
                return "Positive";
            case 4:
                return "Very_Positive";
            default:
                return "";
        }
    }


    /*******************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     */


    private void test(){
        List<Integer> inputList = new ArrayList<>();
        inputList.add(1);
        inputList.add(1);
        inputList.add(1);
        inputList.add(1);
        inputList.add(1);

        //Logger.getLogger("org.apache").setLevel(Level.ERROR); //Level.OFF

        SparkConf sparkConf = new SparkConf().setAppName("Test").setMaster("local[*]"); //session. replace
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<Integer > javaRDD = javaSparkContext.parallelize(inputList);

        javaSparkContext.close();
    }

    private void test2(){
        Properties properties = new Properties();

        String pipelineProperties = "tokenize, ssplit, pos, lemma, ner, parse, sentiment";
        pipelineProperties = "tokenize, ssplit, parse, sentiment";
        properties.setProperty("annotators", pipelineProperties);
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);


        String text = "This is a test text. lets goo!";

        CoreDocument coreDocument = new CoreDocument(text);

        stanfordCoreNLP.annotate(coreDocument);

        List<CoreSentence> coreSentences = coreDocument.sentences();
        List<CoreLabel> coreLabels = coreDocument.tokens();


        for (CoreSentence sentence : coreSentences ){
            String sentiment = sentence.sentiment(); //sentiment
            System.out.println("SENTENCE : " + sentence.toString() + " - " + sentiment);
        }

        for (CoreLabel label : coreLabels){
            String pos = label.get(CoreAnnotations.PartOfSpeechAnnotation.class);; //parts of speech
            String lemma = label.lemma();//lemmanation
            String ner = label.get(CoreAnnotations.NamedEntityTagAnnotation.class); //named entity recognition
            System.out.println("TOKEN : " + label.originalText());
        }

        //svae models
        /*clusters.save(anomaliesSparkContext.sc(), "target/org/apache/spark/JavaKMeansExample/KMeansModel");
        KMeansModel sameModel = KMeansModel.load(anomaliesSparkContext.sc(),
                "target/org/apache/spark/JavaKMeansExample/KMeansModel");*/

    }

    private void test3(){
        /**RDD's**/
        /**Initialise**/
        SparkConf conf = new SparkConf().setAppName("Test3").setMaster("local[*]"); //session. replace
        JavaSparkContext sc = new JavaSparkContext(conf);


        /**Paralising data**/
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);
        JavaRDD<Integer> distData2 = sc.parallelize(data,10); // add 10 as a slice (no. of partitions), 2-4 the norm

        //external data
        JavaRDD<String> distFile = sc.textFile("url"); //return one record per line in "each" file [using the spark context, i.e hadoop]
        JavaRDD<String> distFile2 = sc.textFile("url", 10); //no. of partitions
        JavaPairRDD<String,String> distFile3 = sc.wholeTextFiles("url"); // takes multiple files

        //working with key-value pairs - example
        JavaRDD<String> lines = sc.textFile("data.txt");
        JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1)); //uses scala
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);

        //save rdd
        distFile.saveAsObjectFile("url");


        /**Operations to data {transformation and reduce} **/

        //example [reduce]
        JavaRDD<String> lines2 = sc.textFile("data.txt"); //pointer
        JavaRDD<Integer> lineLengths = lines2.map(s -> s.length()); //pointer

        lineLengths.persist(StorageLevel.MEMORY_ONLY()); //[Optional] saves rdd to memory to be reused, after running
        int totalLength = lineLengths.reduce((a, b) -> a + b); //only here is the rdd ran. [return to driver program.]


        /**NOTE**/
        //passing with function [Spark’s API relies heavily on passing functions in the driver program]
        JavaRDD<String> lines3 = sc.textFile("data.txt");
        JavaRDD<Integer> lineLengths3 = lines3.map(new Function<String, Integer>() {
            public Integer call(String s) { return s.length(); }
        });

        /*int totalLength2 = lineLengths2.reduce(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer a, Integer b) { return a + b; }
        });*/

        /**NOTE**/
        distData.foreach(x -> x += x); //this has a change to not work. locally is okay, distributed is not okay.

    }

    private void test4(){
        /**DATAFRAMES or DATASET**/
        /**Initialise and create**/
        SparkSession spark = SparkSession
                .builder()
                .appName("test 4")
                .master("local")
                .getOrCreate();

        //A dataframe, like rdd but with scheme like table of database, collection of partitions

        Dataset<Row> df = spark.read().json("url");

        // Displays the content of the DataFrame to stdout
        df.show();

        /**Opertaion to data
         * assume...
         *  | age|   name|
         *  ______________
         *  |null|Michael|
         *  |  30|   Andy|
         * **/

        // Print the schema in a tree format
        df.printSchema();

        // Select only the "name" column
        df.select("name").show();

        // Select everybody, but increment the age by 1
        df.select(col("name"), col("age").plus(1)).show(); // df.col() works, but using static import here [better?]

        // Select people older than 21
        df.filter(col("age").gt(21)).show();

        // Count people by age
        df.groupBy("age").count().show();

        /**Convert to sql querying**/

        df.createOrReplaceTempView("sqlpeople");

        Dataset<Row> sqlDF = null;
        //sqlDF = spark.sql("SELECT * FROM sqlpeople");
        sqlDF.show();

        try { //to persist the sql query among sessions
            df.createGlobalTempView("people");
            // Global temporary view is tied to a system preserved database `global_temp`
            //spark.sql("SELECT * FROM global_temp.people").show();
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
    }

    //adds one entity into database
    public ParsedData testdbAdd(){
        if ( repository.count() <= 0) {
            ParsedData newData = new ParsedData();
            Date date = new Date();
            newData.setDate(date.toString());
            newData.setTextMessage("This is a test/demo text message.");
            newData.setLikes(100);
            newData.setLocation("Default Location");
            repository.save(newData);


            return repository.findAll().get(0);
        }
        return null;
    }

    //adds one entity into database
    public int testdbDelete(){
        if ( repository.count() == 1) {

            ParsedData data = repository.findAll().get(0);
            repository.delete(data);


            return (int) repository.count();
        }
        return -1;
    }






}





