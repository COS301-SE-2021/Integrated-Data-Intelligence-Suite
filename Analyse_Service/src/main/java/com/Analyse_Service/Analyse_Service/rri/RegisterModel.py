import mlflow
import mlflow.spark
import pyspark
from pyspark.ml.pipeline import PipelineModel

mlflow.set_tracking_uri("http://localhost:5000")

with mlflow.start_run(run_name="YOUR_RUN_NAME") as run:
    #params = {"n_estimators": 5, "random_state": 42}
    #sk_learn_rfr = RandomForestRegressor(**params)

    # Log parameters and metrics using the MLflow APIs
    #mlflow.log_params(params)
    #mlflow.log_param("param_1", randint(0, 100))
    #mlflow.log_metrics({"metric_1": random(), "metric_2": random() + 1})


    foundModel = LogisticRegression.load(Path)

    model = mlflow.spark.load_model("../Models/LogisticRegression")

    mlflow.spark.log_model(foundModel, "LogisticRegression")

    mlflow.spark.save_model(foundModel, "spark-model")

    # Log the spark model and register as version 1
    mlflow.spark.log_model(
        sk_model=sk_learn_rfr,
        artifact_path="sklearn-model",
        registered_model_name="sk-learn-random-forest-reg-model"
    )

