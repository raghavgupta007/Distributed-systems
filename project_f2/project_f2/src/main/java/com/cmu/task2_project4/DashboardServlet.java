//Raghav Gupta
//rgupta3

//This class contains
package com.cmu.task2_project4;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


@WebServlet(name = "Dashboard",urlPatterns = {"/getDashboard"})

public class DashboardServlet extends HttpServlet {
    Analyticsmodel tm = null;

    public void init() {
        tm = new Analyticsmodel();
    }


    //Below method makes connection to mongoDB and read its content and stores it in string text of which a JSON object is created
    //After that certain parameters are added to the logs using addLog method of class Logs

    public void setupMongoDB() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://Raghav007:Raghav007@cluster0.jztod.mongodb.net/ds_project4?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("ds_project4");
        MongoCollection<Document> document_collection =database.getCollection("logs");
        MongoCursor<Document> cursor = document_collection.find().iterator();

        String log_received="";

        while(cursor.hasNext()){
            String text = cursor.next().toJson();
            JSONObject jsonObject = new JSONObject(text);

            tm.updateState(jsonObject.getString("searched_state"));

            tm.updateTotalTime(jsonObject.getInt("time_taken_to_get_response"));

            tm.addLog(new Logs(jsonObject.getString("request"),
                    jsonObject.getString("response"),
                    jsonObject.getString("request_api"),
                    jsonObject.getString("response_from_api"),
                    jsonObject.getString("method_type_of_request"),
                    jsonObject.getString("searched_state"),
                    jsonObject.getString("searched_county"),
                    jsonObject.getString("time_taken_to_get_response"),
                    jsonObject.getString("request_time"),
                    jsonObject.getString("response_time"),
                    jsonObject.getString("second_day_before_yesterday"),
                    jsonObject.getString("third_day_before_yesterday"),jsonObject.getString("fourth_day_before_yesterday")));
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        tm.resetValues();                                                            //we need updated values on the dashboard, so first resetting the values, and then setting up the values by calling setUpMongoDB
        setupMongoDB();
        request.setAttribute("message", tm.getTopState());
        request.setAttribute("count", tm.getCount());
        request.setAttribute("averagecompute", tm.getAverageCompute());
        request.setAttribute("tablelogs", tm.getLogs());

        RequestDispatcher view = request.getRequestDispatcher("dboard.jsp");
        view.forward(request, response);
    }

    public void destroy() {
    }
}