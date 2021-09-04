package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.AIModel;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceAIModelRepository;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceParsedDataRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;
import com.johnsnowlabs.nlp.DocumentAssembler;
import com.johnsnowlabs.nlp.EmbeddingsFinisher;
import com.johnsnowlabs.nlp.Finisher;
import com.johnsnowlabs.nlp.annotators.LemmatizerModel;
import com.johnsnowlabs.nlp.annotators.Normalizer;
import com.johnsnowlabs.nlp.annotators.Tokenizer;
import com.johnsnowlabs.nlp.annotators.TokenizerModel;
import com.johnsnowlabs.nlp.annotators.classifier.dl.SentimentDLModel;
import com.johnsnowlabs.nlp.annotators.ner.NamedEntity;
import com.johnsnowlabs.nlp.annotators.ner.NerApproach;
import com.johnsnowlabs.nlp.annotators.ner.NerConverter;
import com.johnsnowlabs.nlp.annotators.ner.dl.NerDLModel;
import com.johnsnowlabs.nlp.annotators.sbd.pragmatic.SentenceDetector;
import com.johnsnowlabs.nlp.annotators.sda.pragmatic.SentimentDetector;
import com.johnsnowlabs.nlp.annotators.sentence_detector_dl.SentenceDetectorDLModel;
import com.johnsnowlabs.nlp.annotators.spell.norvig.NorvigSweetingModel;
import com.johnsnowlabs.nlp.embeddings.SentenceEmbeddings;
import com.johnsnowlabs.nlp.embeddings.UniversalSentenceEncoder;
import com.johnsnowlabs.nlp.embeddings.WordEmbeddingsModel;
import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline;


import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.*;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator$;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.StringIndexer;

import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;

import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.tuning.CrossValidator;
import org.apache.spark.ml.tuning.CrossValidatorModel;
import org.apache.spark.ml.tuning.ParamGridBuilder;
import org.apache.spark.ml.tuning.TrainValidationSplitModel;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import org.mlflow.tracking.ActiveRun;
import org.mlflow.tracking.MlflowClient;
import org.mlflow.tracking.MlflowContext;
import org.mlflow.api.proto.Service.RunInfo;
import org.mlflow.api.proto.Service.Experiment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.Function1;
import scala.collection.*;
import scala.collection.mutable.WrappedArray;


import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     *
     * @param request This is a request object which contains data required to be analysed.
     * @return AnalyseDataResponse This object contains analysed data which has been processed.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public AnalyseDataResponse analyzeData(AnalyseDataRequest request)
            throws InvalidRequestException, IOException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList of requested parsedData is null");
        }
        else {
            for (int i = 0;
                 i < request.getDataList().size();
                 i++) {
                if (request.getDataList().get(i) == null) {
                    throw new InvalidRequestException("DataList inside data of requested parsedData is null");
                }
            }
        }

        /*******************Setup Data******************/

        ArrayList<ParsedData> dataList = request.getDataList();
        ArrayList<ArrayList> parsedDatalist = new ArrayList<>();

        ArrayList<String> nlpText = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            nlpText.add(dataList.get(i).getTextMessage());
        }

        FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(nlpText);
        ArrayList<FindNlpPropertiesResponse> findNlpPropertiesResponse = this.findNlpProperties(findNlpPropertiesRequest);


        for (int i = 0; i < dataList.size(); i++) {
            //String row = "";

            String text = dataList.get(i).getTextMessage();
            String location = dataList.get(i).getLocation();
            String date = dataList.get(i).getDate();//Mon Jul 08 07:13:29 +0000 2019
            String[] dateTime = date.split(" ");
            String formattedDate = dateTime[1] + " " + dateTime[2] + " " + dateTime[5];
            String likes = String.valueOf(dataList.get(i).getLikes());

            //FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(text);
            //FindNlpPropertiesResponse findNlpPropertiesResponse = this.findNlpProperties(findNlpPropertiesRequest);

            //FindSentimentRequest sentimentRequest = new FindSentimentRequest(dataList.get(i).getTextMessage());
            //FindSentimentResponse sentimentResponse = this.findSentiment(sentimentRequest);
            //row = sentimentResponse.getSentiment().getCssClass() + " " + date + " "+ likes;

            //Random rn = new Random();
            //int mockLike = rn.nextInt(10000) + 1;

            ArrayList<Object> rowOfParsed = new ArrayList<>();
            rowOfParsed.add(text);
            rowOfParsed.add(location);
            rowOfParsed.add(formattedDate);
            rowOfParsed.add(likes);
            rowOfParsed.add(findNlpPropertiesResponse.get(i));
            System.out.println("THE MAIN GUY HERE");
            System.out.println(findNlpPropertiesResponse.get(i).getSentiment());
            System.out.println(findNlpPropertiesResponse.get(i).getNamedEntities());

            parsedDatalist.add(rowOfParsed);
        }

        /*******************Run A.I Models******************/

        FindPatternRequest findPatternRequest = new FindPatternRequest(parsedDatalist); //TODO
        //TrainFindPatternResponse findPatternResponse = this.findPattern(findPatternRequest);

        TrainFindRelationshipsRequest findRelationshipsRequest = new TrainFindRelationshipsRequest(parsedDatalist);
        //TrainFindRelationshipsResponse findRelationshipsResponse = this.trainFindRelationship(findRelationshipsRequest);

        TrainGetPredictionRequest getPredictionRequest = new TrainGetPredictionRequest(parsedDatalist); //TODO
        //TrainGetPredictionResponse getPredictionResponse = this.trainGetPredictions(getPredictionRequest);

        TrainFindTrendsRequest findTrendsRequest = new TrainFindTrendsRequest(parsedDatalist);
        TrainFindTrendsResponse findTrendsResponse = this.trainFindTrends(findTrendsRequest);

        //FindTrendsRequest findTrendsRequest = new FindTrendsRequest(parsedDatalist);
        //FindTrendsResponse findTrendsResponse = this.findTrends(findTrendsRequest);

        TrainFindAnomaliesRequest findAnomaliesRequest = new TrainFindAnomaliesRequest(parsedDatalist);
        //TrainFindAnomaliesResponse findAnomaliesResponse = this.trainFindAnomalies(findAnomaliesRequest);


        return new AnalyseDataResponse(
                null,
                null,
                null,
                findTrendsResponse.getPattenList(),
                null);

        /*return new AnalyseDataResponse(
                findPatternResponse.getPattenList(),
                findRelationshipsResponse.getPattenList(),
                getPredictionResponse.getPattenList(),
                findTrendsResponse.getPattenList(),
                findAnomaliesResponse.getPattenList());*/
    }


    /**
     * This method used to find a pattern(s) within a given data,
     * A pattern is found when there's a relation,trend, anamaly etc found as a patten; [relationship,trend,number_of_likes]
     *
     * @param request This is a request object which contains data required to be analysed.
     * @return FindPatternResponse This object contains data of the patterns found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindPatternResponse findPattern(FindPatternRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("AnalyzeDataRequest Object is null");
        }
        if (request.getDataList() == null) {
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

        return new FindPatternResponse(null);
    }


    /**
     * This method used to find a relationship(s) within a given data
     * A relationship is when topics are related, x is found when y is present, e.g when elon musk name pops, (bitcoin is present as-well | spacex is present as-well) [topic]
     *
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

        System.out.println(relationshipData);

        StructType schema = new StructType(new StructField[]{ new StructField(
                "Tweets",DataTypes.createArrayType(DataTypes.StringType), false, Metadata.empty())
        });

        Dataset<Row> itemsDF = sparkRelationships.createDataFrame(relationshipData, schema);
        itemsDF.show();

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
     *
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


        /*
        Logger rootLoggerM = LogManager.getRootLogger();
        rootLoggerM.setLevel(Level.ERROR);

        Logger rootLoggerL = Logger.getRootLogger();
        rootLoggerL.setLevel(Level.ERROR);

        Logger.getLogger("org.apache").setLevel(Level.ERROR);
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);
        */

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
            //FindNlpPropertiesRequest findNlpPropertiesRequest = new FindNlpPropertiesRequest(requestData.get(i).get(0).toString());
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j = 0;  j < namedEntities.size();  j++) {
                //row.add(isTrending)
                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()) {// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                }
                else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    }
                    else {
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

        //List<Row> strData = null; ///TODO Need to convert structureData Arraylist to of type ListRow
        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2); // .read().parquet("...");

        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity

        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency
        //namedEntities.get(0); /*name entity*/
        //namedEntities.get(1); /*name type*/
        //namedEntities.get(2); /*name type-number*/
        //namedEntities.get(3); /*name frequency*/

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

        //training set
        int minSize = 0;
        if (namedEntities.size() > averageLikes.size()) {
            minSize = averageLikes.size();
        }
        else {
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

        System.out.println("*****************ITEMDF****************");
        itemsDF.show();

        List<Row> trainSet = new ArrayList<>();
        for (int i = 0;
             i < minSize;
             i++) {
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


        //TODO:
        //split data
        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");
        Dataset<Row>[] split = trainingDF.randomSplit((new double[]{0.7, 0.3}), 5043);

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
        /*Tokenizer tokenizer = new Tokenizer()
                .setInputCol("text")
                .setOutputCol("words");

        HashingTF hashingTF = new HashingTF()
                .setNumFeatures(1000)
                .setInputCol(tokenizer.getOutputCol())
                .setOutputCol("features");
        */

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber", "Frequency", "AverageLikes"})
                .setOutputCol("features");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");




        LogisticRegression lr = new LogisticRegression() //model - estimator
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);


        //pipeline
        Pipeline pipeline = new Pipeline();
        pipeline.setStages(new PipelineStage[] {assembler,indexer,lr});

        /* Fit the pipeline to training documents.
        //PipelineModel lrModel = pipeline.fit(trainSetDF);

        // Fit the model
        //Dataset<Row> trainedDF = assembler.transform(trainSetDF);
        //trainedDF = indexer.fit(trainedDF).transform(trainedDF);

        //LogisticRegressionModel lrModel = lr.fit(trainedDF);
        //System.out.println("Coefficients: " + lrModel.coefficients() + " Intercept: " + lrModel.intercept());  // Print coefficients and intercept
        */

        /******************EVALUATE/ANALYSE MODEL**************/

        //test TODO: do with pipline
        //Dataset<Row> testedDF = assembler.transform(testSetDF);
        //testedDF = indexer.fit(testedDF).transform(testedDF);


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

        paramGridBuilder.addGrid(lr.regParam(),  new double[] {lr.getRegParam()});
        paramGridBuilder.addGrid(lr.elasticNetParam(),  new double[] {lr.getElasticNetParam()});
        paramGridBuilder.addGrid(lr.fitIntercept());
        ParamMap[] paramMaps = paramGridBuilder.build();


        CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps).setNumFolds(2);

        /*for (Row r : trainSetDF.select("isTrending").collectAsList()) {
        //    System.out.println("Trending -> " + r.get(0));
        //}

        //save
        //lrModel.write().overwrite().save("C:/Users/user pc/Desktop/models/RhuliLogisticRegesionmodel");

        //System.out.println("SAVED");
        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));*/

        /***********************SETUP MLFLOW***********************/

        MlflowClient client = new MlflowClient("http://localhost:5000");
        String experimentID = client.createExperiment("LogisticRegression_Experiment");
        RunInfo runInfo = client.createRun(experimentID);


        MlflowContext mlflow = new MlflowContext(client);


        ActiveRun run = mlflow.startRun("LogisticRegression_Run", runInfo.getRunId());




        CrossValidatorModel lrModel = crossValidator.fit(trainSetDF);

        Dataset<Row> predictions = lrModel.transform(testSetDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        predictions.show();
        System.out.println("*****************Predictions Of Test Data*****************");



        double accuracy = binaryClassificationEvaluator.evaluate(predictions);
        BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);

        System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));


        //param
        client.logParam(run.getId(),"MaxIter", "10");
        client.logParam(run.getId(),"setRegParam" ,"0.3");
        client.logParam(run.getId(),"setElasticNetParam" , "0.8");

        //run.logParam("MaxIter", "10");
        //run.logParam("setRegParam" ,"0.3");
        //run.logParam("setElasticNetParam" , "0.8");

        //metrics
        client.logMetric(run.getId(),"areaUnderROC" , binaryClassificationMetrics.areaUnderROC());
        client.logMetric(run.getId(),"meanSquaredError", regressionMetrics.meanSquaredError());
        client.logMetric(run.getId(),"rootMeanSquaredError", regressionMetrics.rootMeanSquaredError());
        client.logMetric(run.getId(),"meanAbsoluteError", regressionMetrics.meanAbsoluteError());
        client.logMetric(run.getId(),"explainedVariance", regressionMetrics.explainedVariance());

        //run.logMetric("areaUnderROC" , binaryClassificationMetrics.areaUnderROC());
        //run.logMetric("MSE", regressionMetrics);
        //run.logMetric("rmse", rmse);
        //run.logMetric("r2", r2);
        //run.logMetric("mae", mae);

        //custom tags
        client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        //run.setTag("Accuracy", String.valueOf(accuracy));


        run.endRun();

        /***********************SETUP MLFLOW***********************/


        /*******************READ MODEL OUTPUT*****************
        //LogisticRegressionModel model1 = LogisticRegressionModel.load("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/SteveLogisticRegesionmodel");
        //Dataset<Row> input = assembler.transform(trainingDF); //TODO this is an example of input will be changed once database is calibrated

        Dataset<Row> res = lrModel.transform(trainedDF); //trained data used to output

        List<Row> rawResults = res.select("EntityName", "prediction", "Frequency", "EntityType", "AverageLikes").filter(col("prediction").equalTo(1.0)).collectAsList();

        if (rawResults.isEmpty()) {
            rawResults = res.select("EntityName", "prediction", "Frequency", "EntityType", "AverageLikes").filter(col("Frequency").geq(2.0)).collectAsList();
        }

        System.out.println("*******************Outputs begin*****************");
        System.out.println(rawResults.toString());
        for (Row r : res.select("prediction").collectAsList()) {
            System.out.println("Trending -> " + r.get(0));
        }
        System.out.println("*******************Outputs begin*****************");


        /*ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            ArrayList<Object> r = new ArrayList<>();
            r.add(rawResults.get(i).get(0).toString());
            r.add(Double.parseDouble(rawResults.get(i).get(1).toString()));
            results.add(r);
        }

        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0;
             i < rawResults.size();
             i++) {
            ArrayList<Object> r = new ArrayList<>();
            String en = rawResults.get(i).get(0).toString();
            ArrayList<String> locs = new ArrayList<>();
            List<Row> rawLocs = itemsDF.select("location").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawLocs.toString());
            for (int j = 0;
                 j < rawLocs.size();
                 j++) {
                locs.add(rawLocs.get(j).get(0).toString());
            }
            r.add(en);
            r.add(locs);
            r.add(rawResults.get(i).get(3).toString());
            r.add(rawResults.get(i).get(4).toString());


            results.add(r);
        }*/

        ArrayList<ArrayList> results = new ArrayList<>();
        return new TrainFindTrendsResponse(results);
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

        /*******************LOAD & READ MODEL*****************/
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

            results.add(r);

        }


        for(int i = 0; i < results.size() ; i++ ){
            System.out.println("RESULT TREND : " + results.get(i));
        }

        return new FindTrendsResponse(results);
    }

    /**
     * This method used to find a trends(s) within a given data.
     * A trend is when topic frequent over time and location for minimum a day, e.g elon musk name keeps popping [topic].
     *
     * @param request This is a request object which contains data required to be analysed.
     * @return FindTrendsResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FindTrendsResponse findTrendsUnsed(FindTrendsRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindTrendsRequest Object is null");
        }
        if (request.getDataList() == null) {
            throw new InvalidRequestException("DataList is null");
        }

        /*******************SETUP SPARK*****************/

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

        for (int i = 0;
             i < requestData.size();
             i++) {
            List<Object> row = new ArrayList<>();
            FindNlpPropertiesResponse findNlpPropertiesResponse = (FindNlpPropertiesResponse) requestData.get(i).get(4); //response Object

            String sentiment = findNlpPropertiesResponse.getSentiment();
            //ArrayList<ArrayList> partsOfSpeech = findNlpPropertiesResponse.getPartsOfSpeech();
            ArrayList<ArrayList> namedEntities = findNlpPropertiesResponse.getNamedEntities();

            for (int j = 0;
                 j < namedEntities.size();
                 j++) {
                //row.add(isTrending)
                row = new ArrayList<>();
                row.add(namedEntities.get(j).get(0).toString()); //entity-name
                row.add(namedEntities.get(j).get(1).toString()); //entity-type
                if (types.isEmpty()) {// entity-typeNumber
                    row.add(0);
                    types.add(namedEntities.get(j).get(1).toString());
                }
                else {
                    if (types.contains(namedEntities.get(j).get(1).toString())) {
                        row.add(types.indexOf(namedEntities.get(j).get(1).toString()));
                    }
                    else {
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

        //List<Row> strData = null; ///TODO Need to convert structureData Arraylist to of type ListRow
        Dataset<Row> itemsDF = sparkTrends.createDataFrame(trendsData, schema2); // .read().parquet("...");


        /*******************MANIPULATE DATAFRAME*****************/

        //group named entity

        List<Row> namedEntities = itemsDF.groupBy("EntityName", "EntityType", "EntityTypeNumber").count().collectAsList(); //frequency
        namedEntities.get(0); /*name entity*/
        namedEntities.get(1); /*name type*/
        namedEntities.get(2); /*name type-number*/
        namedEntities.get(3); /*name frequency*/

        List<Row> averageLikes = itemsDF.groupBy("EntityName").avg("Likes").collectAsList(); //average likes of topic
        averageLikes.get(1); //average likes

        List<Row> rate = itemsDF.groupBy("EntityName", "date").count().collectAsList();
        rate.get(1); //rate ???

        //training set
        int minSize = 0;
        if (namedEntities.size() > averageLikes.size()) {
            minSize = averageLikes.size();
        }
        else {
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

        System.out.println("*****************ITEMDF****************");
        itemsDF.show();

        List<Row> trainSet = new ArrayList<>();
        for (int i = 0;
             i < minSize;
             i++) {
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

        Dataset<Row> trainingDF = sparkTrends.createDataFrame(trainSet, schema); //.read().parquet("...");



        /*******************SETUP PIPELINE*****************/
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"EntityTypeNumber", "Frequency", "AverageLikes"})
                .setOutputCol("features");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("IsTrending")
                .setOutputCol("label");

        Dataset<Row> testDF = assembler.transform(trainingDF); //testing new input data
        Dataset<Row> indexed = indexer.fit(testDF).transform(testDF);
        indexed.show();


        /*******************Summary*****************/
        //model
       /* LogisticRegression lr = new LogisticRegression() //estimator
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
       /*  Dataset<Row> test = null;

        Dataset<Row> predictions = model.transform(testSetDF);
        predictions.show();
        System.out.println("/*******************Predictions*****************///");


      /*  for (Row r : trainSetDF.select("isTrending").collectAsList())
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


        /*******************LOAD MODEL*****************/
        /**************Analyse Accuracy************/

        //test
        Dataset<Row> parquetModel = sparkTrends.read().parquet("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/SteveLogisticRegesionmodel/data/part-00000-a6345197-7a7a-4bc0-b55d-db32697b7e4e-c000.snappy.parquet");
        parquetModel.show();

        //Function1<Dataset<Row>> func = new Function1<indexed>;

        Dataset<Row> res2 = parquetModel.toDF();
        System.out.println("*******************Found parquet Model*****************");

        //Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/SteveLogisticRegesionmodel
        LogisticRegressionModel lrModel = LogisticRegressionModel.load("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/RhuliLogisticRegesionmodel");
        Dataset<Row> res = lrModel.transform(indexed);

        BinaryClassificationEvaluator evaluator = new BinaryClassificationEvaluator()
                .setLabelCol("label")
                .setRawPredictionCol("prediction")
                .setMetricName("areaUnderROC");
        double accuracy = evaluator.evaluate(res);
        System.out.println("/********************Found Model Accuracy : " + Double.toString(accuracy));


        res.show();
        System.out.println("******************RES 1****************");
        res2.show();
        System.out.println("******************RES 2****************");

        /*input.show();
        /System.out.println("*******************Steve Predictions*****************");
        /res1.show();
        /System.out.println("*******************RES 1 : Predictions*****************");
        /res.show();
        /System.out.println("*******************RES 2 : Predictions*****************");
        /res3.show();
        //ystem.out.println("*******************RES 3 : Predictions*****************");*/

        /*******************READ MODEL OUTPUT*****************/

        List<Row> rawResults = res.select("EntityName", "prediction", "Frequency", "EntityType", "AverageLikes").filter(col("prediction").equalTo(1.0)).collectAsList();

        if (rawResults.isEmpty()) {
            rawResults = res.select("EntityName", "prediction", "Frequency", "EntityType", "AverageLikes").filter(col("Frequency").geq(2.0)).collectAsList();
        }

        System.out.println("/*******************Outputs begin*****************/");
        System.out.println(rawResults.toString());
        for (Row r : res.select("prediction").collectAsList()) {
            System.out.println("Trending -> " + r.get(0));
        }
        System.out.println("/*******************Outputs begin*****************/");



        res2.show();
        System.out.println("******************RES 2****************");
        res.show();
        System.out.println("******************RES 1****************");


        ArrayList<ArrayList> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
            ArrayList<Object> r = new ArrayList<>();
            String en = rawResults.get(i).get(0).toString();
            ArrayList<String> locs = new ArrayList<>();
            List<Row> rawLocs = itemsDF.select("location").filter(col("EntityName").equalTo(en)).collectAsList();
            System.out.println(rawLocs.toString());
            for (int j = 0; j < rawLocs.size(); j++) {
                locs.add(rawLocs.get(j).get(0).toString());
            }
            r.add(en);
            r.add(locs);
            r.add(rawResults.get(i).get(3).toString());
            r.add(rawResults.get(i).get(4).toString());

            results.add(r);
        }

        return new FindTrendsResponse(results);
    }


    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a prediction...
     *
     * @param request This is a request object which contains data required to be analysed.
     * @return GetPredictionResponse This object contains data of the predictions found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public TrainGetPredictionResponse trainGetPredictions(TrainGetPredictionRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetPredictionRequest Object is null");
        }
        if (request.getDataList() == null) {
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


        return new TrainGetPredictionResponse(null);
    }

    /**
     * This method used to find a predictions(s) within a given data
     * A prediction is a prediction...
     *
     * @param request This is a request object which contains data required to be analysed.
     * @return GetPredictionResponse This object contains data of the predictions found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public GetPredictionResponse getPredictions(GetPredictionRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetPredictionRequest Object is null");
        }
        if (request.getDataList() == null) {
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
     *
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
        //.setK(2) //number of classses/clusters
        //.setMaxIterations(numIterations);

        //pipeline
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {assembler,km});

        /******************EVALUATE/ANALYSE MODEL**************/

        /*evaluators
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
        /*paramGridBuilder.addGrid(km. .regParam(), new double[]{lr.getRegParam()});
        paramGridBuilder.addGrid(km.elasticNetParam(), new double[]{lr.getElasticNetParam()});
        paramGridBuilder.addGrid(km.fitIntercept());
        ParamMap[] paramMaps = paramGridBuilder.build();
        //validator
        CrossValidator crossValidator = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
                .setEstimatorParamMaps(paramMaps)
                .setNumFolds(2);
        /*TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(pipeline)
                .setEvaluator(regressionEvaluator)
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
        PipelineModel kmModel =  pipeline.fit(trainingDF);

        /*Dataset<Row> predictions = kmModel.transform(trainingDF); //features does not exist. Available: IsTrending, EntityName, EntityType, EntityTypeNumber, Frequency, FrequencyRatePerHour, AverageLikes
        //predictions.show();
        //System.out.println("*****************Predictions Of Test Data*****************");
        //double accuracy = binaryClassificationEvaluator.evaluate(predictions);
        //BinaryClassificationMetrics binaryClassificationMetrics = binaryClassificationEvaluator.getMetrics(predictions);
        //RegressionMetrics regressionMetrics = regressionEvaluator.getMetrics(predictions);
        //System.out.println("********************** Found Model Accuracy : " + Double.toString(accuracy));
        //param
        /*client.logParam(run.getId(),"MaxIter", "10");
        client.logParam(run.getId(),"setRegParam" ,"0.3");
        client.logParam(run.getId(),"setElasticNetParam" , "0.8");
        //metrics
        //client.logMetric(run.getId(),"areaUnderROC" , binaryClassificationMetrics.areaUnderROC());
        /*client.logMetric(run.getId(),"meanSquaredError", regressionMetrics.meanSquaredError());
        client.logMetric(run.getId(),"rootMeanSquaredError", regressionMetrics.rootMeanSquaredError());
        client.logMetric(run.getId(),"meanAbsoluteError", regressionMetrics.meanAbsoluteError());
        client.logMetric(run.getId(),"explainedVariance", regressionMetrics.explainedVariance());
        //custom tags
        //client.setTag(run.getId(),"Accuracy", String.valueOf(accuracy));
        //run.setTag("Accuracy", String.valueOf(accuracy));*/

        kmModel.write().overwrite().save("Analyse_Service/src/main/java/com/Analyse_Service/Analyse_Service/models/KMeansModel");

        run.endRun();

        /***********************SETUP MLFLOW - SAVE ***********************/

        ArrayList<String> results = new ArrayList<>();
        return new TrainFindAnomaliesResponse(results);
    }

    /**
     * This method used to find a anomalies(s) within a given data.
     * A Anomaly is an outlier in the data, in the context of the data e.g elon musk was trending the whole except one specific date.
     *
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
        List<Row> rawResults = Results.select("Text","prediction").collectAsList();

        System.out.println("/*******************Outputs begin*****************");
        System.out.println(rawResults.toString());
        System.out.println("/*******************Outputs begin*****************");

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < rawResults.size(); i++) {
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
     * This method used to find an entity of a statement i.e sentiments/parts of speech
     *
     * @param request This is a request object which contains data required to be processed.
     * @return FindEntitiesResponse This object contains data of the entities found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public ArrayList<FindNlpPropertiesResponse> findNlpProperties(FindNlpPropertiesRequest request)
            throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("FindEntitiesRequest Object is null");
        }
        if (request.getText() == null) {
            throw new InvalidRequestException("Text is null");
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
        //createDataset(text, Encoders.STRING()).toDF("text");

        List<Row> nlpPropertiesData = new ArrayList<>();
        for(int i =0; i < request.getText().size(); i++) {
            Row row = RowFactory.create(request.getText().get(i));
            nlpPropertiesData.add(row);
        }


        Dataset<Row> data =  sparkNlpProperties.createDataFrame(nlpPropertiesData,schema).toDF();
        //data.show();
        //System.out.println("Data checkup 1");

        /*******************SETUP NLP PIPELINE MODEL*****************/

        DocumentAssembler document_assembler = (DocumentAssembler) new DocumentAssembler().setInputCol("text").setOutputCol("document");
        Dataset<Row> data2 = document_assembler.transform(data);
        //data2.show();
        //System.out.println("Data checkup 2");

        //SentenceDetector sentence_detector = (SentenceDetector) ((SentenceDetector) new SentenceDetector().setInputCols(new String[] {"document"})).setOutputCol("sentence");
        SentenceDetectorDLModel sentence_detector = (SentenceDetectorDLModel) ((SentenceDetectorDLModel) new SentenceDetectorDLModel().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence"); //"sentence_detector_dl", "en"
        //.pretrained("sentence_detector_dl", "en")
        //sentence_detector.setExplodeSentences(true);
        Dataset<Row> data3 = sentence_detector.transform(data2);
        //data3.show();
        //System.out.println("Data checkup 3");

        TokenizerModel tokenizer =  ((Tokenizer) ((Tokenizer) new Tokenizer().setInputCols(new String[] {"document"})) .setOutputCol("token")).fit(data3);
        //TokenizerModel tokenizer = (TokenizerModel) ((TokenizerModel) new TokenizerModel().pretrained().setInputCols(new String[] {"sentence"})).setOutputCol("token");
        Dataset<Row> data4 = tokenizer.transform(data3);
        //data4.show();
        //System.out.println("Data checkup 4");


        NorvigSweetingModel checker = (NorvigSweetingModel) ((NorvigSweetingModel) new NorvigSweetingModel().pretrained().setInputCols(new String[]{"token"})).setOutputCol("Checked"); //checked = token
        Dataset<Row> data5 = checker.transform(data4);
        //data5.show();
        //System.out.println("Data checkup 5");


        WordEmbeddingsModel embeddings = (WordEmbeddingsModel) ((WordEmbeddingsModel) new WordEmbeddingsModel().pretrained().setInputCols(new String[] {"document", "token"})).setOutputCol("embeddings");
        //embeddings.setDimension(4);
        Dataset<Row> data6 = embeddings.transform(data5);
        //data6.show();
        //System.out.println("Data checkup 6");

        //SentenceEmbeddings sentenceEmbeddings = (SentenceEmbeddings) ((SentenceEmbeddings) new SentenceEmbeddings().setInputCols(new String[] {"document", "embeddings"})).setOutputCol("sentence_embeddings");
        UniversalSentenceEncoder sentenceEmbeddings = (UniversalSentenceEncoder) ((UniversalSentenceEncoder) new UniversalSentenceEncoder().pretrained().setInputCols(new String[] {"document"})).setOutputCol("sentence_embeddings");

        Dataset<Row> data7 = sentenceEmbeddings.transform(data6);
        //data7.select(col("sentence_embeddings")).show(false);
        //System.out.println("Data checkup 7");

        SentimentDLModel sentimentDetector = (SentimentDLModel) ((SentimentDLModel) new SentimentDLModel().pretrained().setInputCols(new String[] {"sentence_embeddings"})).setOutputCol("sentiment");

        //Dataset<Row> data8 = sentimentDetector.transform(data7);
        //data8.show();
        //System.out.println("Data checkup 8");

        NerDLModel ner = (NerDLModel) ((NerDLModel) new NerDLModel().pretrained().setInputCols(new String[] {"document", "token", "embeddings"})).setOutputCol("ner");
        //Dataset<Row> data9 = ner.transform(data8);
        //data9.show();
        //System.out.println("Data checkup 9");

        NerConverter converter = (NerConverter) ((NerConverter) new NerConverter().setInputCols(new String[]{"document", "token", "ner"})).setOutputCol("chunk");
        //Dataset<Row> data10 = converter.transform(data9);
        //data10.show();
        //System.out.println("Data checkup 10");


        //EmbeddingsFinisher embeddingsFinisher = new EmbeddingsFinisher().setInputCols(new String[] {"embeddings"}).setOutputCols(new String[] {"finishedembeddings"});
        //Dataset<Row> data11 = embeddings.transform(data6);
        //data11.show();
        //System.out.println("Data checkup 11");

        //Normalizer normalizer = (Normalizer)((Normalizer) new Normalizer().setInputCols(new String[]{"token"})).setOutputCol("normalized");

        //LemmatizerModel lemmatizer = (LemmatizerModel)((LemmatizerModel) LemmatizerModel.pretrained("lemma_antbnc", "en", "public/models").setInputCols(new String[]{"normalized"})).setOutputCol("lemma");

        //Finisher finisher = new Finisher().setInputCols(new String[]{"document", "lemma"}).setOutputCols(new String[]{"document", "lemma"});

        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{document_assembler, sentence_detector , tokenizer, checker, embeddings, sentenceEmbeddings, sentimentDetector, ner ,converter /*normalizer, lemmatizer, finisher*/});

// Fit the pipeline to training documents.
        PipelineModel pipelineFit = pipeline.fit(data);
        Dataset<Row> results = pipelineFit.transform(data);



        data.show();
        System.out.println("OLD DATA");

        results.show();
        System.out.println("NEW DATA");

        /*results.select(col("text")).show(false);
        System.out.println("text =results");

        results.select(col("document.result")).show(false);
        System.out.println("document =results");

        results.select(col("sentence.result")).show(false);
        System.out.println("sentence =results");

        results.select(col("token.result")).show(false);
        System.out.println("token =results");

        results.select(col("Checked.result")).show(false);
        System.out.println("Checked =results");

        results.select(col("embeddings.result")).show(false);
        System.out.println("embeddings =results");*/

        results.select(col("sentence_embeddings.result")).show(false);
        System.out.println("sentence_embeddings =results");

        results.select(col("sentiment.result")).show(false);
        System.out.println("sentiment =results");

        /*results.select(col("ner.result")).show(false);
        System.out.println("ner =results");

        results.select(col("chunk.result")).show(false);
        System.out.println("chunk =results");*/


        /*******************READ MODEL DATA*****************/

        ArrayList<FindNlpPropertiesResponse> response = new ArrayList<>();
        long dataCount = results.select(col("sentiment") ,col("ner"), col("chunk")).collectAsList().size();

        System.out.println("DATA COUNT : " + dataCount);


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

            System.out.println("added response : " + dataIndex);
            response.add(new FindNlpPropertiesResponse(sentiment, null));
        }

            /*String sentiment ="";

            sentiment = innerSentimentRowData.toString();
            System.out.println("*********ARRAY FOUND : " + sentiment);

            sentiment = innerSentimentRowData.get(0).toString();
            System.out.println("*********INSIDE ARRAY FOUND : " + sentiment);

            sentiment = innerSentimentRow;
            System.out.println("*********SENTIMENT FOUND : " + sentiment);*/

            /*for (int i = 0; i < sentimentRow.size(); i++) {

            Row rows = sentimentRow.get(i);
            sentiment = rows.get(0).toString();

            //results.add(rawResults.get(i).get(0).toString());//name
            }*/

        /**Named entity recognised**/
        Dataset<Row> nerDataset = results.select(col("ner.result"));
        Dataset<Row> chunkDataset = results.select(col("chunk.result"));

        List<Row> textRowData = chunkDataset.collectAsList();
        List<Row> entityRowData = nerDataset.collectAsList();

        for(int dataIndex = 0; dataIndex < dataCount ; dataIndex++){

            System.out.println("getting response : " + dataIndex);

            Row textRow = textRowData.get(dataIndex);
            Row entityRow = entityRowData.get(dataIndex);

            WrappedArray wrappedArrayText = (WrappedArray) textRow.get(0);
            WrappedArray wrappedArrayEntity = (WrappedArray) entityRow.get(0);

            List<String> innerTextRowData = JavaConversions.seqAsJavaList(wrappedArrayText);
            List<String> innerEntityRowData = JavaConversions.seqAsJavaList(wrappedArrayEntity);

            /*System.out.println("List of - text array : " + innerTextRowData);
            System.out.println("List of - entity array : " + innerEntityRowData);

            //System.out.println("List of Size - text array : " + innerTextRowData.size());
            System.out.println("List of values - text array : " + innerTextRowData.get(0));
            //System.out.println("List of Size - entity array : " + innerEntityRowData.size());
            System.out.println("List of values- entity array : " + innerEntityRowData.get(0));

            //System.out.println("List of value[0] - text array : " + innerTextRowData.get(0)[0]);
            //System.out.println("List of value[0]- entity array : " + innerEntityRowData.get(0)[0]);*/


            //ArrayList<String> nameEntityRow = new ArrayList<>(); //text, entity
            ArrayList<ArrayList> nameEntities = new ArrayList<>();
            int entityIndex = 0;

            for (int i = 0; i < innerTextRowData.size(); i++) {
                //System.out.println(innerEntityRowData.get(i));

                String nameEntityText = "";
                String nameEntityType = "";

                if (entityIndex >= innerTextRowData.size()) { //all entities found
                    break;
                }

                if (innerEntityRowData.get(i).equals("O") == false) { //finds entity

                    //System.out.println("FOUNDITGIRL : ");
                    String foundEntity = innerEntityRowData.get(i);
                    System.out.println(foundEntity);

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
                    entityIndex = entityIndex + 1;
                }
            }

            response.get(dataIndex).setNamedEntities(nameEntities);
        }

        //nameEntities.add(nameEntityRow);

        //System.out.println("List of final values- entity array : " + nameEntities);




        /**setup analyser**
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


        return response;
    }

    /**
     * This method used to fetch the parsed data from the database
     *
     *  This is a request object which contains data required to be fetched.
     * @return FetchParsedDataResponse This object contains data of the sentiment found within the input data.
     * @throws InvalidRequestException This is thrown if the request or if any of its attributes are invalid.
     */
    public FetchParsedDataResponse fetchParsedData()
            throws InvalidRequestException {
        /*if (request == null) {
            throw new InvalidRequestException("FetchParsedDataRequest Object is null");
        }
        if (request.getDataType() == null) {
            throw new InvalidRequestException("Datatype is null");
        }
        if (request.getDataType() != "ParsedData") {
            throw new InvalidRequestException("Wrong Datatype is used");
        }*/

        System.out.println("here 1");
        if(parsedDataRepository == null)
            System.out.println( " Is null ");
        System.out.println("here 2");
        ArrayList<ParsedData> list = (ArrayList<ParsedData>) parsedDataRepository.findAll();
        System.out.println("here 3");
        return new FetchParsedDataResponse(list);
    }


    public SaveAIModelResponse saveAIModel(SaveAIModelRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("SaveAIModelRequest Object is null");
        }
        if (request.getSaveAIModel() == null) {
            throw new InvalidRequestException("SaveAIModelRequest AIModel Object is null");
        }

        AIModel model = request.getSaveAIModel();
        aiModelRepository.save(model);

        return new SaveAIModelResponse(true);
    }

    public SaveAIModelResponse fetchAIModel(SaveAIModelRequest request) {

        return new SaveAIModelResponse(true);
    }


    /*******************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     * *****************************************************************************************************************
     */

    public void mlFlowTest(){

        /**ActiveRuns**/
        //ActiveRun run ;
        //logging
        //run.logParam();

        /**Clinet (network)**/
        MlflowClient client = new MlflowClient("http://localhost:5000/");
        //client.createRun();
        //client.logParam();

        /**Context**/
        MlflowContext context = new MlflowContext();
        //client = context.getClient();


        /***PROCESS FUNCTIONALTY***/
        /**Running**/
        MlflowContext mlflow = new MlflowContext();
        ActiveRun run = mlflow.startRun("run-name");
        run.logParam("alpha", "0.5");
        run.logMetric("MSE", 0.0);
        //run.setTag();
        run.endRun();

        /**Performance check**/
        //MlflowClient client = new MlflowClient();
        RunInfo runInfo = client.createRun();
        for (int epoch = 0; epoch < 3; epoch ++) {
            client.logMetric(runInfo.getRunId(), "quality", 2 * epoch, System.currentTimeMillis(), epoch);
        }

        /**Experiments Managing **/
        List<Experiment> list =  client.listExperiments();

        for(int i=0; i<list.size();i++){
            runInfo = client.createRun(list.get(i).getExperimentId());
            client.logParam(runInfo.getRunId(), "hello", "world");
            client.setTerminated(runInfo.getRunId());
        }

        /***NETWORKING***/
        /**setUI**/
        //client.getModelVersionDownloadUri();
        //run.getArtifactUri();





    }
}





