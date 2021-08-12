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
import edu.stanford.nlp.simple.Sentence;
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
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import static org.apache.spark.sql.functions.col;
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

        FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(dataSendList);
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
        LogManager.getRootLogger().setLevel(Level.OFF); //TODO: what this for?

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
        ArrayList<String> reqData = request.getDataList();

        for(int i=0; i < reqData.size(); i++){
            relationshipData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }

        /*******************SETUP MODEL*****************/

        /*StructType schema = new StructType(new StructField[]{ new StructField(
                "Tweets", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkRelationships.createDataFrame(relationshipData, schema);
        itemsDF.show();
        FPGrowthModel model = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.15)
                .setMinConfidence(0.6)
                .fit(itemsDF);*/


        Dataset<Row> training = sparkRelationships.read().parquet("..."); //TODO: default

        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(training);

        // Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: " + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        // We can also use the multinomial family for binary classification
        LogisticRegression mlr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8)
                .setFamily("multinomial");

        // Fit the model
        LogisticRegressionModel mlrModel = mlr.fit(training);

        // Print the coefficients and intercepts for logistic regression with multinomial family
        System.out.println("Multinomial coefficients: " + lrModel.coefficientMatrix()
                + "\nMultinomial intercepts: " + mlrModel.interceptVector());


        /******************Analyse Model Accuracy**************/

        BinaryLogisticRegressionTrainingSummary trainingSummary = lrModel.binarySummary();

        // Obtain the loss per iteration.
        double[] objectiveHistory = trainingSummary.objectiveHistory();
        for (double lossPerIteration : objectiveHistory) {
            System.out.println(lossPerIteration);
        }

        // Obtain the receiver-operating characteristic as a dataframe and areaUnderROC.
        Dataset<Row> roc = trainingSummary.roc();
        roc.show();
        roc.select("FPR").show();
        System.out.println(trainingSummary.areaUnderROC());

        // Get the threshold corresponding to the maximum F-Measure and rerun LogisticRegression with this selected threshold.
        Dataset<Row> fMeasure = trainingSummary.fMeasureByThreshold();
        double maxFMeasure = fMeasure.select(functions.max("F-Measure")).head().getDouble(0);
        double bestThreshold = fMeasure.where(fMeasure.col("F-Measure").equalTo(maxFMeasure))
                .select("threshold").head().getDouble(0);
        lrModel.setThreshold(bestThreshold);

        /*******************READ MODEL OUTPUT*****************/

        //model.freqItemsets().show();
        //List<Row> rData = model.freqItemsets().collectAsList();
        List<Row> rData =new ArrayList<>(); //TODO: default
        ArrayList<ArrayList> results = new ArrayList<>();

        for (int i = 0; i < rData.size(); i++) {
            ArrayList<String> row = new ArrayList<>();

            for (int j = 0; j < rData.get(i).getList(0).size(); j++)
                row.add(rData.get(i).getList(0).get(j).toString());

            row.add(rData.get(i).get(1).toString());
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

        SparkSession sparkTrends = SparkSession
                .builder()
                .appName("Trends")
                .master("local")
                .getOrCreate();

        /*******************SETUP DATA*****************/



        List<Row> trendsData  = new ArrayList<>();

        ArrayList<ArrayList> reqData = request.getDataList();


        /*for(int i=0; i < reqData.size(); i++){
            trendsData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }*/
        ArrayList<ArrayList> formatedData = new ArrayList<>();



        for(int i=0; i < reqData.size(); i++){
            ArrayList<String> r = new ArrayList<>();
            FindEntitiesRequest en = new FindEntitiesRequest(reqData.get(i).get(0).toString());
            FindEntitiesResponse enr = this.findEntities(en);
            for (int j=0; j< enr.getEntitiesList().size(); j++){
                r.add(enr.getEntitiesList().get(j).get(0).toString());
                r.add(enr.getEntitiesList().get(j).get(1).toString());
                r.add(reqData.get(i).get(1).toString());
                r.add(reqData.get(i).get(2).toString());
                r.add(reqData.get(i).get(3).toString());
                r.add(reqData.get(i).get(4).toString());
                formatedData.add(r);
            }
        }



        /*******************SETUP DATAFRAME*****************/

        StructType schema = new StructType(new StructField[]{ new StructField(
                "Tweets", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema); // .read().parquet("...");
        itemsDF.show();


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


        //model
        LogisticRegression lr = new LogisticRegression() //estimator
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(itemsDF);
        // Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: " + lrModel.coefficients() + " Intercept: " + lrModel.intercept());


        //pipeline
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[] {tokenizer, hashingTF, lr});

        // Fit the pipeline to training documents.
        PipelineModel model = pipeline.fit(itemsDF);


        /******************Analyse Model Accuracy**************/
        //test
        Dataset<Row> test = null;

        Dataset<Row> predictions = model.transform(test);
        for (Row r : predictions.select("isTrending").collectAsList())
            System.out.println("Trending -> " + r.get(0));


        //summaries
        BinaryLogisticRegressionTrainingSummary trainingSummary = lrModel.binarySummary();

        // Obtain the loss per iteration.
        double[] objectiveHistory = trainingSummary.objectiveHistory();
        for (double lossPerIteration : objectiveHistory) {
            System.out.println(lossPerIteration);
        }

        // Obtain the receiver-operating characteristic as a dataframe and areaUnderROC.
        Dataset<Row> roc = trainingSummary.roc();
        roc.show();
        roc.select("FPR").show();
        System.out.println(trainingSummary.areaUnderROC());

        // Get the threshold corresponding to the maximum F-Measure and rerun LogisticRegression with this selected threshold.
        Dataset<Row> fMeasure = trainingSummary.fMeasureByThreshold();
        double maxFMeasure = fMeasure.select(functions.max("F-Measure")).head().getDouble(0);
        double bestThreshold = fMeasure.where(fMeasure.col("F-Measure").equalTo(maxFMeasure))
                .select("threshold").head().getDouble(0);
        lrModel.setThreshold(bestThreshold);


        /*******************READ MODEL OUTPUT*****************/

        ArrayList<ArrayList> results = new ArrayList<>();

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

        /*******************SETUP MODEL*****************/

        // Load and parse data
        String data = "mllib/kmeans/data.txt"; //TODO: default
        JavaRDD<String> rddData = anomaliesSparkContext.textFile(data);
        JavaRDD<Vector> parsedData =  rddData.map(s -> {
            String[] sarray = s.split(" ");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();

        // Cluster the data into two classes using KMeans
        int numClusters = 2;
        int numIterations = 20;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        System.out.println("Cluster centers:");
        for (Vector center: clusters.clusterCenters()) {
            System.out.println(" " + center);
        }
        double cost = clusters.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

        // Save and load model
        clusters.save(anomaliesSparkContext.sc(), "target/org/apache/spark/JavaKMeansExample/KMeansModel");
        KMeansModel sameModel = KMeansModel.load(anomaliesSparkContext.sc(),
                "target/org/apache/spark/JavaKMeansExample/KMeansModel");

        /*******************READ MODEL OUTPUT*****************/

        ArrayList<ArrayList> results = new ArrayList<>();

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
    public FindEntitiesResponse findEntities(FindEntitiesRequest request) throws InvalidRequestException {
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
            sentiments.add(sentence.sentiment()); //sentiment

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
        ArrayList<ArrayList> PartOfSpeech = new ArrayList<>();
        for (CoreLabel label : coreLabels){
            //String lemma = label.lemma();//lemmanation
            String pos = label.get(CoreAnnotations.PartOfSpeechAnnotation.class);; //parts of speech
            row = new ArrayList<>();
            row.add(label.toString());
            row.add(pos);

            //System.out.println("TOKEN : " + label.originalText());
            //String lemma = label.lemma();//lemmanation
        }

        //get parts of named entity
        ArrayList<ArrayList> NameEntities = new ArrayList<>();
        for (CoreEntityMention em : coreDocument.entityMentions()){
            row = new ArrayList<>();
            row.add(em.text());
            row.add(em.entityType());
            NameEntities.add(row);
        }

        FindEntitiesResponse result = new FindEntitiesResponse(sentiment, PartOfSpeech, NameEntities);
        return result;
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

        Logger.getLogger("org.apache").setLevel(Level.ERROR); //Level.OFF

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





