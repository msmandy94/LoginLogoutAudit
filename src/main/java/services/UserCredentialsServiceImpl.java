package services;

import DAO.MongoClientPool;
import beens.UserCredentials;
import com.mongodb.BasicDBObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.logging.Level;

public class UserCredentialsServiceImpl implements UserCredentialsService {
    private UserCredentialsServiceImpl() {
    }

    private static class UserCredentialsSingletonHelper {
        private static final UserCredentialsServiceImpl INSTANCE = new UserCredentialsServiceImpl();
    }

    public static UserCredentialsServiceImpl getInstance() {
        return UserCredentialsSingletonHelper.INSTANCE;
    }

    @Override
    public Boolean validateUserCred(UserCredentials userCredentials) {
        String userId = userCredentials.getUserId();
        String password = userCredentials.getPassword();
        // https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
        // throws UnknownHostException

        /*MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("users");*/
        BasicDBObject bsonObject = new BasicDBObject();
        bsonObject.append("userId", userId).append("password", password);
        ArrayList<Document> userCredential = MongoClientPool.getUserCredentialsCollection().find(bsonObject).into(new ArrayList<Document>());
        //ArrayList<Document> userCredentials1 = database.getCollection("userCredentials").find(bsonObject).into(new ArrayList<Document>());
        if (userCredential == null || userCredential.isEmpty()) {
            java.util.logging.Logger.getGlobal().log(Level.SEVERE, "user:" + userId + " not found");
            return false;
        }

        return true;
    }
}