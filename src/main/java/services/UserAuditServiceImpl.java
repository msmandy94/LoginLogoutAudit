package services;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mandeepsingh on 16/06/18.
 */
public class UserAuditServiceImpl {
    /*public static void main(String[] args) {
        UserAuditServiceImpl userAuditService = new UserAuditServiceImpl();
        userAuditService.saveAuditAction("testuser1","testpass1");

    }*/
    public boolean saveAuditAction(String userId, String action) {
        // https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
        // throws UnknownHostException
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("audit");
        MongoCollection<Document> collection = database.getCollection("loginLogoutAudit");

        DBObject audit = new BasicDBObject("user",userId).append("action", action);

        collection.insertOne((Document) audit);

        mongoClient.close();

        return false;
    }
}
