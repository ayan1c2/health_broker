package com.mqtt.pahodao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException; 

public class ConnectToDB { 
	
   final static Logger logger = Logger.getLogger(ConnectToDB.class);
	
   public static MongoClient mongo = null;
   public static DB db = null;
   public static DBCollection table = null;
   
   static {	
	   System.out.println("Inside static block...");
	   
	   //logs a debug message
	   if(logger.isDebugEnabled()){	
		   System.out.println("ddw");
			logger.debug("This is debug : ");
		}
		
		if(logger.isInfoEnabled()){
			System.out.println("dd");
			logger.info("This is info : ");
		}
		
		logger.warn("This is warn : ");
		logger.error("This is error : ");
		logger.fatal("This is fatal : ");
		logger.error("Sorry, something wrong!");  
	   
	   
		try {
			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			mongo = new MongoClient("localhost", 27017);
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			db = mongo.getDB("myproject");
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			table = db.getCollection("mycollection");
		} catch (Exception e) {			
			e.printStackTrace();
		}
   }
	   
   
   public static void main( String args[] ) {  
      
	   System.out.println("Inside main...");
	   try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			//MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			//DB db = mongo.getDB("myproject");

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			//DBCollection table = db.getCollection("mycollection");

			/**** Insert ****/
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			document.put("name", "achatterjee2");
			document.put("age", 32);
			
			List <String> degrees  = new ArrayList <String>();
			degrees.add("B.Tech");
			degrees.add("M.E");
			degrees.add("PhD.");
			
			document.put("degrees", degrees);
			document.put("country", "India");
			document.put("createdDate", new Date());
			table.insert(document);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "achatterjee2");

			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "achatterjee");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Ayan Chatterjee2");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 
			    = new BasicDBObject().append("name", "Ayan Chatterjee2");

			DBCursor cursor2 = table.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			/**** Done ****/
			System.out.println("Done");

		    }catch (MongoException e) {
			e.printStackTrace();
		   }finally {
			   System.out.println("Inside finally...");			   
		   }
   		}
	}
