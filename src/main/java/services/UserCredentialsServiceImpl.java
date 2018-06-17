package services;

import beens.UserCredentials;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserCredentialsServiceImpl implements UserCredentialsService {
    @Override
    public Boolean validateUserCred(UserCredentials userCredentials) {
        String userId= userCredentials.getUserId();
        String password= userCredentials.getPassword();
        // https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
        // throws UnknownHostException
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("users");
        MongoCollection<Document> collection = database.getCollection("credentials");
        List<Document> documents = (List<Document>) collection.find().into(
                new ArrayList<Document>());

        for(Document document : documents){
            System.out.println(document);
        }
        collection.find();// Bson filter
        mongoClient.close();

        return true;
    }
}
