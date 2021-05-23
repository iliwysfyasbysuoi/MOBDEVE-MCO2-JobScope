//package com.mobdeve.nievas.jobscope;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DB;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.DBCollection;
//import com.mongodb.MongoClientURI;
//
//import java.net.UnknownHostException;
//
//public class MongoDb {
//
//    public static MongoClient mongoClient;
//    public static DB database; // creates db inside mongoclient
//    public static DBCollection userCollection; //collection inside DB
//
//    public static void main(String[] args) throws UnknownHostException {
//
//        mongoClient = new MongoClient((new MongoClientURI("mongodb://admin:jobscope@cluster0.eeog6.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")));
//        database = mongoClient.getDB("jobscope");
//        userCollection = database.getCollection("user");
//
//        UserObject userObject = new UserObject();
//        userObject.setName("Sean Nieva");
//        userObject.setEmail("seannieva@gmail.com");
//        userObject.setUsername("seannieva");
//        userObject.setPassword("12345");
//        userCollection.insert(convert(userObject));
//
////        DBObject query = new BasicDBObject("username", "seannieva");
////        DBCursor cursor = userCollection.find(query);
//
////        System.out.println(cursor.one());
//
//    }
//
//    public static DBObject convert(UserObject userObject){
//        return new BasicDBObject("name", userObject.getName()).append("email", userObject.getEmail()).append("username", userObject.getUsername()).append("password", userObject.getPassword());
//    }
//
//}
