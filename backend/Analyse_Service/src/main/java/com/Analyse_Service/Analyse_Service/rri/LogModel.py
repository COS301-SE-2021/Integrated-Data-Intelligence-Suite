
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
#sc = spark.sparkContext


#0 = name
path = sys.argv[1]
name = sys.argv[2]
runId = sys.argv[3]

print(spark.version)
print("path: " + path)

mlflow.set_tracking_uri("http://localhost:5000")
client = MlflowClient()

if (name == "LogisticRegressionModel"):
    #from pyspark.ml.tuning import TrainValidationSplitModel
    from pyspark.ml.pipeline import PipelineModel
    print("here sys")

    File = client.download_artifacts(runId, name, path)
    #unflavouredModel = TrainValidationSplitModel.load(File)
    unflavouredModel = PipelineModel.load(File)
    print("here sys")

    mlflow.spark.log_model(unflavouredModel, path)
    print("here sys")

if (name == "DecisionTreeModel"):
    from pyspark.ml.tuning import TrainValidationSplitModel
    File = client.download_artifacts(runId, path) #"features"
    unflavouredModel = TrainValidationSplitModel.load(path)
    mlflow.spark.log_model(unflavouredModel, "DecisionTreeModel")

if(name == "KMeansModel"):
    from pyspark.ml.pipeline import PipelineModel, Pipeline
    File = client.download_artifacts(runId, path) #"features"
    unflavouredModel = PipelineModel.read().load(File)
    mlflow.spark.log_model(unflavouredModel, "KMeansModel")

#elif
#else: