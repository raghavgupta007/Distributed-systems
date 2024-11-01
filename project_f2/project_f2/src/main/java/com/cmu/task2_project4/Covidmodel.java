//Raghav Gupta
//rgupta3


//This is the model class which implements the business logic on which the output on the app is shown whether it is safe to travel
//to the state and county or not based on covid cases
package com.cmu.task2_project4;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Covidmodel {


    //The below receives state and county and calculates the range of 3 days by finding the second day before yesterday and fourth day before yesterday
//Reads the data and checks if the 3rd party API returns empty array then sets -1 for the number of cases for the days otherwise provides
    // the number of covid cases for the 3 days.
    public Results process(String county, String state) throws IOException {
        Calendar calendar = Calendar.getInstance();
        //Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'");
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        calendar.add(Calendar.DATE, -3);
        String new_text = sdf.format(calendar.getTime());
        new_text = new_text + "00:00:00.000Z";                        //max date found i.e. second day before yesterday

        SimpleDateFormat sdf_min;
        sdf_min = new SimpleDateFormat("yyyy-MM-dd'T'");
        sdf_min.setTimeZone(TimeZone.getTimeZone("EST"));
        calendar.add(Calendar.DATE, -2);
        String new_text_min = sdf.format(calendar.getTime());
        new_text_min = new_text_min + "00:00:00.000Z";
        Results temp = new Results();

        // Third party api used with state, county and date range.

        URL url = new URL("https://webhooks.mongodb-stitch.com/api/client/v2.0/app/covid-19-qppza/service/REST-API/incoming_webhook/us_only?state=" + state + "&county=" + county + "&min_date=" + new_text_min + "&max_date=" + new_text);

        String data = "";

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        if(httpURLConnection.getResponseCode()!=200)
        {
            temp.setResult(false);
            temp.setFourth_day_before_yesterday("-1");
            temp.setSecond_day_before_yesterday("-1");
            temp.setThird_day_before_yesterday("-1");
        }
        else {
            InputStream input_stream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input_stream));
            String line = "";

            line = bufferedReader.readLine();

            data = data + line;

            JSONArray jArray = null;
            String aJsonString = null;
            String aJsonString2 = null;
            String aJsonString3 = null;
            JSONObject x = new JSONObject();

            try {

                jArray = new JSONArray(data);

                if (jArray.length() > 0) {


                    JSONObject object = jArray.getJSONObject(0);
                    aJsonString = String.valueOf(object.getInt("confirmed_daily"));


                    JSONObject object2 = jArray.getJSONObject(1);
                    aJsonString2 = String.valueOf(object2.getInt("confirmed_daily"));

                    JSONObject object3 = jArray.getJSONObject(2);
                    aJsonString3 = String.valueOf(object3.getInt("confirmed_daily"));

                    boolean b = find_cases(aJsonString, aJsonString2, aJsonString3);
                    System.out.println(b + aJsonString2 + aJsonString + aJsonString3);
                    temp.setResult(b);
                    temp.setFourth_day_before_yesterday(aJsonString);
                    temp.setSecond_day_before_yesterday(aJsonString3);
                    temp.setThird_day_before_yesterday(aJsonString2);

                } else {
                    temp.setResult(false);
                    temp.setFourth_day_before_yesterday("-1");
                    temp.setSecond_day_before_yesterday("-1");
                    temp.setThird_day_before_yesterday("-1");

                }

            } catch (JSONException e) {
                e.printStackTrace();
                temp.setResult(false);
                temp.setFourth_day_before_yesterday("-1");
                temp.setSecond_day_before_yesterday("-1");
                temp.setThird_day_before_yesterday("-1");

            }
            temp.setData(data);
            temp.setUrl(url);
        }
            return temp;
    }

    //passing the request, response, type of request method used, total end to end time taken by api for request and response, the searched state
    //to mongodb method to help add them in the logs on mongodb.

    public void updateDB(Results res) {
        Insert_logs_DB ilogs = new Insert_logs_DB();                               //Insert_db))logs class  makes a connection to mongodb database and inserts documents into collection of the database

        ilogs.set_request(res.getRequest());
        ilogs.set_response(res.getyObject());

        ilogs.set_request_api(res.getUrl());
        ilogs.set_response_api(res.getData());

        ilogs.set_find_method(res.getRequest().getMethod());
        ilogs.set_time_taken(res.getTotal_time());
        ilogs.set_state(res.getState());
        ilogs.set_county(res.getCounty());
        ilogs.set_request_time(res.getRequest_time());
        ilogs.set_response_time(res.getResponse_time());
        ilogs.set_sec_bef_yesterday(res.getSecond_day_before_yesterday());
        ilogs.set_third_bef_yesterday(res.getThird_day_before_yesterday());
        ilogs.set_fourth_bef_yesterday(res.getFourth_day_before_yesterday());
        ilogs.doStuff();                                                             //calling the do stuff method which further help to add the logs to the mongodb

    }

    //Below function provides the business logic which helps to find out whether the user should travel to a state or not.
    // Based on the percentage change of the confirmed cases and number of covid cases, the logic is implemented

    public boolean find_cases(String aJsonString, String aJsonString2, String aJsonString3){


        double second_day_before_yesterday_cases = Double.parseDouble(aJsonString3);
        double third_day_before_yesterday_cases = Double.parseDouble(aJsonString2);

        double fourth_day_before_yesterday_cases = Double.parseDouble(aJsonString);

        double a = second_day_before_yesterday_cases - third_day_before_yesterday_cases;
        double b = third_day_before_yesterday_cases - fourth_day_before_yesterday_cases;

        if(second_day_before_yesterday_cases!=0 && third_day_before_yesterday_cases!=0 && fourth_day_before_yesterday_cases!=0) {

            double per_change_second_day_before_yesterday = ((a) / third_day_before_yesterday_cases) * 100;
            double per_change_third_day_before_yesterday = ((b) / fourth_day_before_yesterday_cases) * 100;

            if (per_change_second_day_before_yesterday > 0 && per_change_third_day_before_yesterday>0 ) {

                return false;
            }

            else if(per_change_second_day_before_yesterday<0 && per_change_third_day_before_yesterday<0 ){

                return true;
            }
            else if(per_change_second_day_before_yesterday > 0 && per_change_third_day_before_yesterday<0)
            {
                return false;
            }

            else if(per_change_second_day_before_yesterday < 0 && per_change_third_day_before_yesterday >0 )  {

                return true;
            }
            else{
                return true;
            }
        }
        else
        {
            if (second_day_before_yesterday_cases- third_day_before_yesterday_cases > 50) {
                return  false;
            } else {
                return true;
            }
        }
    }
}
