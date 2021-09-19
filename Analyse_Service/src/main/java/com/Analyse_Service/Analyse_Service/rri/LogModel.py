#!/usr/bin/python

import sys
import mlflow
import mlflow.spark
from mlflow.tracking import MlflowClient
import pyspark


#0 = name
path = sys.argv[1]
name = sys.argv[2]

mlflow.set_tracking_uri("http://localhost:5000")

if (name == "LogisticRegressionModel"):
    from pyspark.ml.tuning import TrainValidationSplitModel
    unflavouredModel = TrainValidationSplitModel.log_model(path)
    mlflow.spark.log_model(unflavouredModel, "LogisticRegressionModel")

if (name == "DecisionTreeModel"):
    from pyspark.ml.tuning import TrainValidationSplitModel
    unflavouredModel = TrainValidationSplitModel.log_model(path)
    mlflow.spark.log_model(unflavouredModel, "DecisionTreeModel")

if(name == "KMeansModel"):
    from pyspark.ml.pipeline import PipelineModel
    unflavouredModel = PipelineModel.log_model(path)
    mlflow.spark.log_model(unflavouredModel, "KMeansModel")

#elif
#else: