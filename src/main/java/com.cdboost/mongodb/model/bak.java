import com.cdboost.mongodb.model.Log;
import com.cdboost.mongodb.model.UserRole;
import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import scala.Function1;
import scala.collection.parallel.ParIterableLike;
import scala.reflect.internal.Trees;

import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

public class bak  {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .master("local")
                .appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", "mongodb://10.10.1.251:27017/test.l_d_gatewaylog")
                .config("spark.mongodb.output.uri", "mongodb://10.10.1.251:27017/test.l_d_gatewaylog")
                .getOrCreate();

        DataFrameReader reader = sparkSession.read().format("jdbc");
        reader.option("url", "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC");//数据库路径
        reader.option("driver", "com.mysql.jdbc.Driver");
        reader.option("user", "lora_server");
        reader.option("password", "sa.123456");
        reader.option("dbtable", "l_d_gatewaylog");//数据表名

        Encoder<Log> userRoleEncoder = Encoders.bean(Log.class);
        Dataset<Log> rows = reader.load().as(userRoleEncoder);


        /*String url = "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC";
        String[] predicates = {"ID > 0"};
        Properties prop = new Properties();
        Dataset<Row> rows = reader.jdbc(url,"l_d_gatewaylog", predicates, prop);
        rows.show();*/

        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());

       /* JavaRDD<Log> rdds = rows.javaRDD().map(log -> {
            Log temp = new Log();
            temp.setID(log.getID());
            temp.setGatewayEUI(log.getGatewayEUI());
            temp.setLogLevel(log.getLogLevel());
            temp.setLogTime(log.getLogTime());
            temp.setLogContent(log.getLogContent().replaceAll("\n",""));
            temp.setSend_flag(log.getSend_flag());
            return temp;
        });*/
        // MongoSpark.save(rows.map((Function<Log, Log>) log -> log.setLogContent(log.getLogContent().replaceAll("\n",""))));
        // MongoSpark.save(sparkSession.createDataFrame(rdds, Log.class));
        MongoSpark.save(rows);
        jsc.close();

    }
}