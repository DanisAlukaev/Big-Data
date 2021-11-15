import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions.udf
import org.apache.spark.ml.feature.{HashingTF, IDF, RegexTokenizer}
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.PipelineModel
import org.apache.spark.ml.classification.LogisticRegressionModel
import org.apache.spark.ml.classification.{DecisionTreeClassificationModel, DecisionTreeClassifier, LogisticRegression}
import org.apache.spark.ml.Pipeline


object TwitterClassifier {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder()
      .appName("lab07")
      .getOrCreate()
    val inputPath = args(0)

    // load data
    val schema = StructType("ItemID Sentiment SentimentText"
      .split(" ")
      .map(fieldName => {
        if (fieldName == "ItemID" || fieldName == "Sentiment")
          StructField(fieldName, IntegerType, nullable = false)
        else
          StructField(fieldName, StringType, nullable = false)
      }))
    import spark.implicits._
    val data = spark.read.format("csv")
      .schema(schema)
      .option("header", "true")
      .load(inputPath)

    // remove repetitive letters
    val dropRepetitive = udf { str: String => str.replaceAll("((.))\\1+", "$1").trim.toLowerCase() }
    val noRepetitiveCharsData = data.withColumn("Collapsed", dropRepetitive('SentimentText))

    // create processing stages
    val tokenizer = new RegexTokenizer().setInputCol("Collapsed")
      .setOutputCol("tokens")
      .setPattern("\\s+")
    val hashingTF = new HashingTF().setInputCol("tokens")
      .setOutputCol("tf")
      .setNumFeatures(200000)
    val idf = new IDF().setInputCol("tf").setOutputCol("tfidf")

    // tokenize and compute tf
    val tokenized = tokenizer.transform(noRepetitiveCharsData)
    val tf = hashingTF.transform(tokenized)

    // train IDF transformer
    val idfModel = idf.fit(tf)
    val tfidf = idfModel.transform(tf)

    // create logistic regression model
    val lr = new LogisticRegression().setFeaturesCol("tfidf").setLabelCol("Sentiment")

    // assembling pipes
    val pipe = new Pipeline().setStages(Array(tokenizer, hashingTF, idf, lr))

    // split dataset
    val Array(training, test) = noRepetitiveCharsData.randomSplit(Array[Double](0.7, 0.3), 18)

    // tune parameters
    val paramGrid = new ParamGridBuilder()
      .addGrid(lr.tol, Array(1e-20, 1e-10, 1e-5))
      .addGrid(lr.maxIter, Array(100, 200, 300))
      .addGrid(lr.regParam, Array(0.1, 0.01))
      .build()

    val cv = new CrossValidator()
      .setEstimator(pipe)
      .setEvaluator(new BinaryClassificationEvaluator()
        .setRawPredictionCol("prediction")
        .setLabelCol("Sentiment"))
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(3)
      .setParallelism(2)

    // train model on training set and then apply it to test set
    val model = cv.fit(training)
    val result = model.transform(test)

    // print best parameters for your pipeline
    println("Regularization Parameter " + model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LogisticRegressionModel].getRegParam)
    println("Max # of Iterations " + model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LogisticRegressionModel].getMaxIter)
    println("Convergence Tolerance " + model.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LogisticRegressionModel].getTol)

    // print predictions
    result.select("Collapsed", "prediction").show()

    // print accuracy
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("Sentiment")
      .setRawPredictionCol("prediction")

    val accuracy = evaluator.evaluate(result)
    println("Classification accuracy: " + accuracy)
  }
}
