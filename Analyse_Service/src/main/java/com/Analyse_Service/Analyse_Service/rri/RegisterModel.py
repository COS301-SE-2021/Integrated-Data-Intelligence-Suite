#!/usr/bin/python

import mlflow
import mlflow.spark
from mlflow.tracking import MlflowClient
import pyspark
from pyspark.ml.pipeline import PipelineModel

mlflow.set_tracking_uri("http://localhost:5000")


client = MlflowClient()
flavouredModel = client.get_latest_versions("LogisticRegressionModel", stages=["None"]
if flavouredModel != null:
    from pyspark.ml.tuning import TrainValidationSplitModel
    flavouredModel.save_model("../models/LogisticRegressionModel")

flavouredModel = client.get_latest_versions("DecisionTreeModel", stages=["None"]
if flavouredModel != null:
    from pyspark.ml.tuning import TrainValidationSplitModel
    flavouredModel.save_model("../models/DecisionTreeModel")

flavouredModel = client.get_latest_versions("KMeansModel", stages=["None"]
if flavouredModel != null:
    from pyspark.ml.tuning import TrainValidationSplitModel
    flavouredModel.save_model("../models/KMeansModel")



#flavouredModel = client.get_registered_model(name)
#flavouredModel.save_model("../Models/LogisticRegression")

#mlflow.spark.save_model(unflavouredModel, "spark-model")
#flavouredModel = mlflow.spark.load_model("LogisticRegression")





#with mlflow.start_run(run_name="YOUR_RUN_NAME") as run:
    #params = {"n_estimators": 5, "random_state": 42}
    #sk_learn_rfr = RandomForestRegressor(**params)

    # Log parameters and metrics using the MLflow APIs
    #mlflow.log_params(params)
    #mlflow.log_param("param_1", randint(0, 100))
    #mlflow.log_metrics({"metric_1": random(), "metric_2": random() + 1})

     # Log the spark model and register as version 1
        #mlflow.spark.log_model(
        #    sk_model=sk_learn_rfr,
        #    artifact_path="sklearn-model",
        #    registered_model_name="sk-learn-random-forest-reg-model"
        #)

