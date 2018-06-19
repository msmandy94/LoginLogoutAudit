package services;

import beens.UserCredentials;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.logging.Level;

public class UserCredentialsServiceImpl implements UserCredentialsService {
    @Override
    public Boolean validateUserCred(UserCredentials userCredentials) {
        String userId = userCredentials.getUserId();
        String password = userCredentials.getPassword();
        // https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
        // throws UnknownHostException

        // use get mongoClient -- singleton
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("users");
        BasicDBObject bsonObject = new BasicDBObject();
        bsonObject.append("userId", userId).append("password", password);
        ArrayList<Document> userCredentials1 = database.getCollection("userCredentials").find(bsonObject).into(new ArrayList<Document>());
        if (userCredentials1 == null || userCredentials1.isEmpty()) {
            java.util.logging.Logger.getGlobal().log(Level.SEVERE, "user:" + userId + " not found");
            return false;
        }
        // closing the mongo client
        mongoClient.close();
        return true;
    }
}