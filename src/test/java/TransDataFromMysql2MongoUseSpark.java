import com.cdboost.mongodb.model.Content;
import com.cdboost.mongodb.model.JsonUtil;
import com.cdboost.mongodb.model.Log;
import com.cdboost.mongodb.model.UserRole;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.bson.Document;
import scala.Function1;
import scala.collection.parallel.ParIterableLike;
import scala.reflect.internal.Trees;

import java.nio.channels.NonWritableChannelException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransDataFromMysql2MongoUseSpark {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .master("local")
                .appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", "mongodb://127.0.0.1:27017/test.l_d_gatewaylog")
                .config("spark.mongodb.output.uri", "mongodb://127.0.0.1:27017/test.l_d_gatewaylog")
                .getOrCreate();

        DataFrameReader reader = sparkSession.read().format("jdbc");
        reader.option("url", "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC");//数据库路径
        reader.option("driver", "com.mysql.jdbc.Driver");
        reader.option("user", "lora_server");
        reader.option("password", "sa.123456");
        reader.option("dbtable", "l_d_gatewaylog");//数据表名

//        Encoder<Log> userRoleEncoder = Encoders.bean(Log.class);
//        Dataset<Log> rows = reader.load().as(userRoleEncoder);


        String url = "jdbc:mysql://10.10.1.250:3306/lora_center?serverTimezone=UTC";
        String[] predicates = {"ID > 0"};
        Properties prop = new Properties();
        Dataset<Row> rows = reader.jdbc(url,"l_d_gatewaylog", predicates, prop);
        rows.show();

        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());

        Encoder<String> stringEncoder = Encoders.STRING();



        JavaRDD<Document> rdd = rows.javaRDD().map(new Function<Row, Document>() {
            public Document call(Row row) throws Exception {
                SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");//时间格式转换
                Log log = new Log();
                log.setID( row.getLong(0));
                log.setGatewayEUI( row.getString(1));
                log.setLogLevel( row.getInt(2));
                log.setLogTime(dataformat.format(row.getTimestamp(3)));
                log.setSend_flag(row.getInt(5));

                Map<String,Object> map = JsonUtil.toHashMap(row.getString(4));
                Content content = new Content();
                content.setAckr(Double.parseDouble(map.get("ackr").toString()));
                content.setDwnb(Integer.parseInt(map.get("dwnb").toString()));
                content.setEui(map.get("dwnb").toString());
                content.setRxfw(Integer.parseInt(map.get("rxfw").toString()));
                content.setRxnb(Integer.parseInt(map.get("rxnb").toString()));
                content.setRxok(Integer.parseInt(map.get("rxok").toString()));
                content.setTime(map.get("time").toString());
                content.setTxnb(Integer.parseInt(map.get("txnb").toString()));
                log.setLogContent(content);

                return Document.parse(JSONObject.fromObject(log).toString());
            }
        });


        //System.out.println("4=======================================" + rows.collectAsList().get(0).getString(4));
        //System.out.println("3=======================================" + rows.collectAsList().get(0).getString(3));

//       JavaRDD<Log> rdds = rows.javaRDD().map(log -> {log.getAs("")
//            Log temp = new Log();
//            temp.setID(log.getID());
//            temp.setGatewayEUI(log.getGatewayEUI());
//            temp.setLogLevel(log.getLogLevel());
//            temp.setLogTime(log.getLogTime());
//            temp.setLogContent(log.getLogContent());
//            temp.setSend_flag(log.getSend_flag());
//            return temp;
//        });
        // MongoSpark.save(rows.map((Function<Log, Log>) log -> log.setLogContent(log.getLogContent().replaceAll("\n",""))));


        // Create a custom WriteConfig
        Map<String, String> writeOverrides = new HashMap<String, String>();
        writeOverrides.put("collection", "log");
        writeOverrides.put("writeConcern.w", "majority");
        WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);

        //MongoSpark.save(sparkSession.createDataFrame(rdd, Log.class),writeConfig);
        MongoSpark.save(rdd);
//        jsc.close();

    }
}