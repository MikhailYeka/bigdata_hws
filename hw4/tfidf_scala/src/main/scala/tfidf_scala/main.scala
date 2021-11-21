package tfidf_scala

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object Main {
  val TOP_N: Int = 100
  val path_to_data: String = "../tripadvisor_hotel_reviews.csv"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("tfidf").getOrCreate()
    import spark.implicits._
    //  Read file
    val dataframe = spark.read.option("header", value = true).csv(path_to_data)
    dataframe.show()

    //  Preprocess dataframe
    val COLUMN_NAME: String = "Review"
    val dataframe_preprocessed = dataframe.filter(dataframe.col(colName = COLUMN_NAME).isNotNull)
      .withColumn("Review_id", monotonically_increasing_id())
      .withColumn(colName = COLUMN_NAME, col = lower(col(COLUMN_NAME)))
      .withColumn(colName = COLUMN_NAME, col = regexp_replace(
        e = col(COLUMN_NAME),
        pattern = "[^a-z0-9]",
        replacement = " "
      ))
    dataframe_preprocessed.show()

    // calc term frequency
    val COLUMN_TO_DROP: String = "Rating"
    val reviews = dataframe_preprocessed
      .drop(COLUMN_TO_DROP)
      .withColumn(COLUMN_NAME, split(col(COLUMN_NAME), " "))
      .select($"Review_id", explode($"Review") as "token")
    val term_freq = reviews.groupBy("Review_id","token").agg(count("token") as "term_frequency")
    term_freq.show()

    def get_Idf(docs_count: Long, docs_freq: Long): Double = math.log((docs_count.toDouble + 1) / (docs_freq.toDouble + 1))

    //  calc inverse document frequency
    val docs_freq = reviews.groupBy("token")
      .agg(countDistinct("Review_id") as "document_freq")
      .orderBy($"document_freq".desc).limit(n = TOP_N)
    val docs_count = dataframe_preprocessed.count()
    val get_idf_udf = udf { df: Long => get_Idf(docs_count, df)}
    val inv_doc_freq = docs_freq.withColumn("inv_document_freq", get_idf_udf(col("document_freq")))
    println(inv_doc_freq)

    // count tf-idf
    val tfidf = term_freq.join(right = inv_doc_freq, usingColumns = Seq("token"), joinType = "inner")
      .withColumn("tf_idf", col("term_frequency") * col("inv_document_freq"))
      .select(col(colName = "Review_id"), col("token"), col("tf_idf"))
      .sort("tf_idf")
    tfidf.show()
    tfidf.write.format("csv").save("../tfidf")

    // pivot table
    val pivot_result = tfidf.groupBy("Review_id")
      .pivot("token")
      .agg(round(first(col("tf_idf")), 3))
      .orderBy($"Review_id".asc)
    pivot_result.show()
    pivot_result.write.format("csv").save("../pivot_result")
  }
}