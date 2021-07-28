package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class AnalyseServiceImpl {

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

            dataSendList.add(row);
        }

        FindPatternRequest findPatternRequest = new FindPatternRequest(dataSendList);
        FindPatternResponse findPatternResponse = this.findPattern(findPatternRequest);

        FindRelationshipsRequest findRelationshipsRequest = new FindRelationshipsRequest(dataSendList);
        FindRelationshipsResponse findRelationshipsResponse = this.findRelationship(findRelationshipsRequest);

        GetPredictionRequest getPredictionRequest = new GetPredictionRequest(dataSendList);
        GetPredictionResponse getPredictionResponse = this.getPredictions(getPredictionRequest);


        return new AnalyseDataResponse(findPatternResponse.getPattenList(), findRelationshipsResponse.getPattenList(), getPredictionResponse.getPattenList());
    }


    /**
     * This method used to find a pattern(s) within a given data
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

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaFPGrowthExample")
                .master("local")
                .getOrCreate();

        spark.sparkContext().setLogLevel("OFF");

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
        List<Row> data = new ArrayList<>();

        for(int i=0; i < reqData.size(); i++){
            data.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }


        /*******************SETUP MODEL*****************/

        StructType schema = new StructType(new StructField[]{ new StructField(
                "items", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });
        Dataset<Row> itemsDF = spark.createDataFrame(data, schema);

        FPGrowthModel model = new FPGrowth() //pipeline/estimator-model , [can use transformers too]-dataframe,
                .setItemsCol("items")
                .setMinSupport(0.5)
                .setMinConfidence(0.6)
                .fit(itemsDF);
        LogManager.getRootLogger().setLevel(Level.OFF); //what this?


        /*******************READ MODEL OUTPUT*****************/

        /*model.freqItemsets().show();

        //Display generated association rules.
        model.associationRules().show();

        Double  oi = (Double) Adata.get(0).get(2);
        System.out.println(Adata.get(0).getList(0).toString());*/

        /* transform examines the input items against all the association rules and summarize the consequent as a prediction
        model.transform(itemsDF).show();*/

        List<Row> Adata = model.associationRules().select("antecedent","consequent","confidence","support").collectAsList();

        ArrayList<String> row;
        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < Adata.size(); i++) {
            row = new ArrayList<>();

            for (int j = 0; j < Adata.get(i).getList(0).size(); j++)
                row.add(Adata.get(i).getList(0).get(j).toString()); //1) antecedent, feq

            for (int k = 0; k < Adata.get(i).getList(1).size(); k++)
                row.add(Adata.get(i).getList(1).get(k).toString()); //2) consequent

            row.add(Adata.get(i).get(2).toString()); //3) confidence
            row.add(Adata.get(i).get(3).toString()); //4) support
            results.add(row);
        }
        System.out.println(results.toString());

        spark.stop();

        return new FindPatternResponse(results);
    }


    /**
     * This method used to find a relationship(s) within a given data
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


        SparkSession sparkRelationships = SparkSession
                .builder()
                .appName("Relationships")
                .master("local")
                .getOrCreate();

        List<Row> relationshipData  = new ArrayList<>();
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

        ArrayList<String> reqData = request.getDataList();

        for(int i=0; i < reqData.size(); i++){
            relationshipData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }


        StructType schema = new StructType(new StructField[]{ new StructField(
                "Tweets", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkRelationships.createDataFrame(relationshipData, schema);
        itemsDF.show();
        FPGrowthModel model = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.15)
                .setMinConfidence(0.6)
                .fit(itemsDF);

        model.freqItemsets().show();
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
     * This method used to find a predictions(s) within a given data
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

        SparkSession sparkPredictions = SparkSession
                .builder()
                .appName("Predictions")
                .master("local")
                .getOrCreate();

        List<Row> PredictionsData  = new ArrayList<>();
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
        ArrayList<String> reqData = request.getDataList();

        for(int i=0; i < reqData.size(); i++){
            PredictionsData.add( RowFactory.create(Arrays.asList(reqData.get(i).split(" "))));
        }


        StructType schema = new StructType(new StructField[]{
                new StructField("Tweets", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkPredictions.createDataFrame(PredictionsData, schema);
        itemsDF.show();
        FPGrowthModel model = new FPGrowth()
                .setItemsCol("Tweets")
                .setMinSupport(0.15)
                .setMinConfidence(0.6)
                .fit(itemsDF);

        //MOCK DATA USED TO MAKE Prediction
        List<Row> testData = Arrays.asList(
                RowFactory.create(Arrays.asList("1 2i 3ii".split(" ")))
        );

        Dataset<Row> tesRes = sparkPredictions.createDataFrame(testData,schema);

        Dataset<Row> Results = model.transform(itemsDF);

        //Results.show();

        List<Row> Pdata = Results.collectAsList();
        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < Pdata.size(); i++) {
            ArrayList<ArrayList> row = new ArrayList<>();
            ArrayList<String> col1 = new ArrayList<>();
            ArrayList<String> col2 = new ArrayList<>();
            for (int j = 0; j < Pdata.get(i).getList(0).size(); j++){
                col1.add(Pdata.get(i).getList(0).get(j).toString());
            }
            for (int j = 0; j < Pdata.get(i).getList(1).size(); j++){

                col1.add(Pdata.get(i).getList(1).get(j).toString());
            }
            row.add(col1);
            row.add(col2);

            results.add(row);
        }
        System.out.println(results.toString());

        sparkPredictions.stop();

        return new GetPredictionResponse(results);
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
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
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



    /**TODO
     * Fix/update the analysing of data by A.I
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

}





