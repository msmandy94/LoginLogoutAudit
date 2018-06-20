package DAO;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.Properties;

public class MongoClientPool  {
    private static Logger logger = LoggerFactory.getLogger(MongoClientPool.class.getName());

    //private static final MongoClientPool INSTANCE = new MongoClientPool();
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    static {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            //input = new FileInputStream("resources/config.properties");

            // load properties file
            String mongoURI = "mongodb://msmandy94:8591112397@35.188.19.56:27017";

            MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();
            clientOptions.minConnectionsPerHost(10);//min
            clientOptions.connectionsPerHost(100);//max


            MongoClientURI uri = new MongoClientURI(mongoURI,clientOptions);
            mongoClient = new MongoClient(uri);
            database = mongoClient.getDatabase("users");
        }
        catch (Exception e){
            logger.error("exception occurred:"+ e.getMessage(), e);
            //throw new Exception("exception occurred while getting mongo db", e);
        }
    }

    public static MongoCollection<Document> getUserCredentialsCollection() {
        return database.getCollection("userCredentials");
    }

    public static MongoCollection<Document> getUserAuditCollection() {
        return database.getCollection("userAudits");
    }

}
