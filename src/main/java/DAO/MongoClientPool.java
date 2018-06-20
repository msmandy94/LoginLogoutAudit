package DAO;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoClientPool {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    static {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("resources/config.properties");

            // load a properties file
            prop.load(input);

            MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();
            clientOptions.minConnectionsPerHost(10);//min
            clientOptions.connectionsPerHost(100);//max
            mongoClient = new MongoClient(new ServerAddress(prop.getProperty("database"),
                    Integer.getInteger(prop.getProperty("mongoDBPort"))), clientOptions.build());
            database = mongoClient.getDatabase("users");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static MongoCollection<Document> getUserCredentialsCollection() {
        return database.getCollection("userCredentials");
    }

    public static MongoCollection<Document> getUserAuditCollection() {
        return database.getCollection("userAudits");
    }

}
