package DAO;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoClientPool  {
    private static Logger logger = LoggerFactory.getLogger(MongoClientPool.class.getName());

    //private static final MongoClientPool INSTANCE = new MongoClientPool();
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    static {
/*        Properties prop = new Properties();
        InputStream input = null;*/

        try {
            //input = new FileInputStream("resources/config.properties");

            // load properties file
            //prop.load(input);

            MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();
            clientOptions.minConnectionsPerHost(10);//min
            clientOptions.connectionsPerHost(100);//max
            mongoClient = new MongoClient(new ServerAddress("localhost", 27017), clientOptions.build());
            database = mongoClient.getDatabase("users");
            mongoClient=  new MongoClient();
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
