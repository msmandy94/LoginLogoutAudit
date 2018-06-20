package services;

import DAO.MongoClientPool;
import beens.UserCredentials;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UserCredentialsServiceImpl implements UserCredentialsService {
    private static Logger logger = LoggerFactory.getLogger(UserCredentialsServiceImpl.class.getName());

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

        BasicDBObject bsonObject = new BasicDBObject();
        bsonObject.append("userId", userId).append("password", password);
        ArrayList<Document> userCredential = MongoClientPool.getUserCredentialsCollection().find(bsonObject).into(new ArrayList<Document>());
        //ArrayList<Document> userCredentials1 = database.getCollection("userCredentials").find(bsonObject).into(new ArrayList<Document>());
        if (userCredential == null || userCredential.isEmpty()) {
            logger.error("user:" + userId + " not found");
            return false;
        }

        return true;
    }
}