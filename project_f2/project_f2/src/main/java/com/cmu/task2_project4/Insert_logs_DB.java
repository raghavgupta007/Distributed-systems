//Raghav Gupta
//rgupta3

//This class makes a connection to mongodb database and inserts documents into collection of the database
package com.cmu.task2_project4;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONObject;

import java.net.URL;

public class Insert_logs_DB {
    String req;
    String res;
    String req_api;
    String res_api;
    String find_method_type;
    String Time_taken;
    String searched_state;
    String searched_county;
    String request_time;
    String response_time;
    String second_day_before_yesterday;
    String third_day_before_yesterday;
    String fourth_day_before_yesterday;

     public void doStuff() {

        //connecting to mongodb and inserting the logs to the database

        ConnectionString connectionString = new ConnectionString("mongodb+srv://Raghav007:Raghav007@cluster0.jztod.mongodb.net/ds_project4?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("ds_project4");
        MongoCollection<Document> document_collection =database.getCollection("logs");                                 //reference : https://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials
        MongoCursor<Document> cursor = document_collection.find().iterator();

         Document doc = new Document();

         //inserting attributes like request link and its response, request api link , response from api, method_type_of_request,Time_Taken_to_get_response and searched_state i.e. the state entered by the user on the app in the document
        doc.put("request",req);
        doc.put("response",res);
        doc.put("request_api",req_api);
         doc.put("response_from_api",res_api);
       doc.put("method_type_of_request",find_method_type);
       doc.put("searched_state",searched_state);
       doc.put("searched_county",searched_county);
         doc.put("time_taken_to_get_response",Time_taken);
       doc.put("request_time",request_time);
       doc.put("response_time",response_time);
       doc.put("second_day_before_yesterday",second_day_before_yesterday);
       doc.put("third_day_before_yesterday",third_day_before_yesterday);
       doc.put("fourth_day_before_yesterday",fourth_day_before_yesterday);

       //inserting the document into collection

         document_collection.insertOne(doc);
    }

    //created the below methods to receive and set the values of requests and responses
    public void set_request(jakarta.servlet.http.HttpServletRequest request ) {

          req = String.valueOf(request.getRequestURL());
    }

    public void set_response(JSONObject response) {

        res = String.valueOf(response);
    }


    public void set_request_api(URL url) {

                 req_api=String.valueOf(url);
    }

    public void set_response_api(String response_api) {

        res_api = String.valueOf(response_api);
    }

    //set_find_method provides the method type of request call. e.g. GET
    public void set_find_method(String x) {

        find_method_type = String.valueOf(x);
    }

    //set_time_taken provides the total time taken between receiving the request from mobila app and sending the response to mobile app
    public void set_time_taken(Long a) {

        Time_taken  = String.valueOf(a);
    }

    //set_state provides the name of the state searched by the user
    public void set_state(String state) {

       searched_state = state;
    }
    public void set_county(String county) {

        searched_county = county;
    }

    public void set_request_time(String b) {

        request_time = b;
    }
    public void set_response_time(String c) {
         response_time = c;
    }


    public void set_sec_bef_yesterday(String sec_day) {

        second_day_before_yesterday = String.valueOf(sec_day);
    }
    public void set_third_bef_yesterday(String third_day) {

     third_day_before_yesterday = String.valueOf(third_day);
    }
    public void set_fourth_bef_yesterday(String fourth_day) {

        fourth_day_before_yesterday = String.valueOf(fourth_day);
    }
}
