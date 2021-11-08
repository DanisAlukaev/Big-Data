import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object WordCount{
    def main(args: Array[String]){

        val conf = new SparkConf().setAppName("appName")
        val sc = new SparkContext(conf)

        val inputPath = args(0)
        val outputPath = args(1)

        val textFile = sc.textFile(inputPath)
        val counts = textFile.flatMap(line => line.split(" "))
                         .filter(_.matches("[a-zA-Z0-9]+"))
                         .map(word => (word, 1))
                         .reduceByKey(_ + _)
        counts.saveAsTextFile(outputPath)
    }
}




