/**
 * @author zc
 * @desc
 * @create 2017-09-07 15:12
 **/
import com.cdboost.mongodb.model.UserEntity;
import com.mongodb.spark.config.ReadConfig;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.mongodb.spark.MongoSpark;


public final class DatasetSQLDemo {

    public static void main(final String[] args) throws InterruptedException {

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", "mongodb://10.10.1.251:27017/test.test")
                .config("spark.mongodb.output.uri", "mongodb://10.10.1.251:27017/test.user")
                .getOrCreate();

        // Create a JavaSparkContext using the SparkSession's SparkContext object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Load data and infer schema, disregard toDF() name as it returns Dataset
        Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
        implicitDS.printSchema();
        implicitDS.show();

        // Load data with explicit schema
        Dataset<UserEntity> explicitDS = MongoSpark.load(jsc).toDS(UserEntity.class);
        explicitDS.printSchema();
        explicitDS.show();

        // Create the temp view and execute the query
        explicitDS.createOrReplaceTempView("test");
        Dataset<Row> centenarians = spark.sql("SELECT name, age FROM test WHERE age >= 100");
        centenarians.show();

        // Write the data to the "hundredClub" collection
        MongoSpark.write(centenarians).option("collection", "user").mode("overwrite").save();

        // Load the data from the "hundredClub" collection
        MongoSpark.load(spark, ReadConfig.create(spark).withOption("collection", "user"), Character.class).show();

        jsc.close();

    }
}