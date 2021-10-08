#!/usr/bin/env python3

import sys
import os
import mlflow
import mlflow.spark
from mlflow.tracking import MlflowClient

#import pyspark
#from pyspark.sql import SparkSession
from pyspark.sql import SparkSession
import pyspark

spark = SparkSession.builder.appName("logModel").getOrCreate()
sc = spark.sparkContext

path = sys.argv[1]
name = sys.argv[2]
runId = sys.argv[3]

mlflow.set_tracking_uri("http://localhost:5000")
client = MlflowClient()

#from pyspark.ml.tuning import TrainValidationSplitModel
from pyspark.ml.pipeline import PipelineModel, Pipeline
File = client.download_artifacts(runId, name, path)
unflavouredModel = PipelineModel.read().load(File)
mlflow.spark.log_model(unflavouredModel, name)





