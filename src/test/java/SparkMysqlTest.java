/**
 * @author zc
 * @desc
 * @create 2017-09-07 15:12
 **/
import com.cdboost.mongodb.model.UserEntity;
import com.mongodb.spark.config.ReadConfig;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;

import com.mongodb.spark.MongoSpark;
import org.spark_project.guava.base.Predicates;

import java.util.Properties;


public class SparkMysqlTest {

    public static void main(final String[] args) throws InterruptedException {

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.some.config.option", "d:")
                .getOrCreate();

        DataFrameReader reader = spark.read().format("jdbc");
        reader.option("url", "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC");//数据库路径
        reader.option("driver", "com.mysql.jdbc.Driver");
        reader.option("user", "lora_server");
        reader.option("password", "sa.123456");
        reader.option("dbtable", "l_d_gatewaylog");//数据表名

        Dataset<Row> rows = reader.load();
        rows.show();


        String url = "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC";
        String[] predicates = {"ID > 0"};
        Properties prop = new Properties();

        Dataset<Row> sqlDF = reader.jdbc(url,"l_d_gatewaylog", predicates, prop);
        sqlDF.show();

    }
}