package services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * Created by mandeepsingh on 16/06/18.
 */
public class UserAuditServiceImpl implements UserAuditService {
    private static Logger logger = Logger.getLogger(UserAuditServiceImpl.class.getName());

    private UserAuditServiceImpl() { }

    private static class UserAuditSingletonHelper{
        private static final UserAuditServiceImpl INSTANCE= new UserAuditServiceImpl();
    }
    public static UserAuditServiceImpl getInstance(){
        return UserAuditSingletonHelper.INSTANCE;
    }
    @Override
    public Boolean saveAuditAction(String userId, String action) {

        // https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
        // throws UnknownHostException
        // todo implement connection pool
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("users");
        MongoCollection<Document> collection = database.getCollection("loginLogoutAudit");

        DBObject audit = new BasicDBObject("user",userId).append("action", action);
        logger.info("saving audit log for: "+ userId);
        try{
            collection.insertOne((Document) audit);
        } catch (ClassCastException e){
            logger.error("ClassCastException while saving log" , e);
        } catch (Exception e){
            logger.error("Exception while saving audit trail", e);
        }


        mongoClient.close();

        return false;
    }
}
