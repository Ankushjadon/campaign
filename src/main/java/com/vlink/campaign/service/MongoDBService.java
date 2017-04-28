package com.vlink.campaign.service;

/**
 * Created by ankush.jadon on 4/7/2017.
 */

import java.util.List;
import java.util.Map;

import com.eaio.uuid.UUID;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBService {
    static Logger logger = LoggerFactory.getLogger(MongoDBService.class);
    private static final String URI = "mongodb://localhost/campaign";
    private static final String DB_NAME = "campaign";
    private MongoClient mongoClient;

    public String insert(String collectionName, Map<String, String> data) {
        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(collectionName);
        logger.info("collection mycol selected successfully");
        // System.out.println("Collection mycol selected successfully");
        UUID uuid = new UUID();
        logger.info("uuid is " + uuid.toString());
        BasicDBObject doc = new BasicDBObject(data);
     //   BasicDBObject basicDBObject = new BasicDBObject();
       String id = uuid.toString();
        doc.put("_id",id );
        collection.insert(doc);
        logger.info("document inserted successfully");
        //  System.out.println("Document inserted successfully");
        return id;
    }

    public void insertFileData(String filedata, List<BasicDBObject> data) {
        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(filedata);
        logger.info("collection filedata selected successfully");
        // System.out.println("Collection mycol selected successfully");
     //   UUID uuid = new UUID();
     //   logger.info("uuid is " + uuid.toString());
      //  JSON json =new JSON();
      //  BasicDBList basicDBList = new BasicDBList(data);
      //  BasicDBObject doc = new BasicDBObject(data);
        //   BasicDBObject basicDBObject = new BasicDBObject();
     //   doc.put("_id", uuid.toString());
     //   doc.put();
        collection.insert(data);
        logger.info("file data inserted successfully");
        //  System.out.println("Document inserted successfully");

    }
//
//    // inserting data in document
//    public void insert(String collectionName, Map<String, String> data) {
//        DB db = getConnection().getDB(DB_NAME);
//        DBCollection collection = db.getCollection(collectionName);
//        logger.info("collection mycol selected successfully");
//       // System.out.println("Collection mycol selected successfully");
//        UUID uuid = new UUID();
//        logger.info("uuid is " + uuid.toString());
//        BasicDBObject doc = new BasicDBObject(data);
//        doc.put("_id", uuid.toString());
//        collection.insert(doc);
//        logger.info("document inserted successfully");
//      //  System.out.println("Document inserted successfully");
//
//    }
//
//
//
    // updating data in document
    public void update(String collectionName, Map<String, String> data, String id) {

        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(collectionName);
        System.out.println("Collection mycol selected successfully");
        BasicDBObject doc = new BasicDBObject(data);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        collection.update(query, doc);
        System.out.println("Document update successfully");

    }

    // deleting data in document
    public void delete(String collectionName, String id) {

        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(collectionName);
        System.out.println("Collection mycol selected successfully");
        BasicDBObject doc = new BasicDBObject();
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);

        collection.remove(query);

        System.out.println(" Delete  successfully");

    }

    public DBObject findById(String collectionName, String id) {

        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(collectionName);
        logger.info("collection mycol selected successfully");
      //  System.out.println("Collection mycol selected successfully");
        BasicDBObject query = new BasicDBObject("_id", id);
        DBObject dbObject = collection.findOne(query);
        return dbObject;
    }

    public List<DBObject> get(String collectionName) {

        DB db = getConnection().getDB(DB_NAME);
        DBCollection collection = db.getCollection(collectionName);
        List<DBObject> data = collection.find().toArray();
        return data;
    }

    // mongo client connection method
    private MongoClient getConnection() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(new MongoClientURI(URI));
        }
        return mongoClient;
    }
}
