//Name :Raghav Gupta, Andrew id: rgupta3


/*
 * This class provides capabilities to search for the confirmed number of covid 19 cases for 3 days ie. second day before yesterday,
 * third day before yesterday and fourth day before yesterday on Heroku.com given the state and the county.  The method "search" is the entry to the class.
 * Network operations cannot be done from the UI thread, therefore this class makes use of an AsyncTask inner class that will do the network
 * operations in a separate worker thread.  However, any UI updates should be done in the UI thread so avoid any synchronization problems.
 * onPostExecution runs in the UI thread, and it calls the ImageView pictureReady method to do the update.
 *
 */

package com.example.edu.cmu_mism;
import static androidx.core.content.ContextCompat.getSystemService;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Fetchdata {

    MainActivity ma = null;

    public void search(String searchTerm, String searchTerm2, MainActivity ma) {
        this.ma = ma;

        new AsyncCasesSearch().execute(searchTerm, searchTerm2);
    }

    String data = "";


//      AsyncTask provides  is a way to use a thread separate from the UI thread in which to do network operations.
//      doInBackground is run in the helper thread.
//      onPostExecute is run in the UI thread, allowing for safe UI updates.


    private class AsyncCasesSearch extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return search(urls[0],urls[1]);

        }

        protected void onPostExecute(String confirmed_cases_param2) {

            ma.confirmed_cases_ready(confirmed_cases_param2);

        }

//This method will receive two parameters searchTerm - state and searchTerm2 - county,
// capitalizes the first character of the state and the county
//checks if the user has entered the state and county using alphabets only,
// then reads the data received to find whether it is safe to travel to the state or not


        private String search(String searchTerm, String searchTerm2) {



            try {

                //checking if the user entered only alphabets or not, if not then app will display error message

                Pattern pattern = Pattern.compile("[a-zA-Z]");
                Matcher matcher = pattern.matcher(searchTerm);
                Matcher matcher2 = pattern.matcher(searchTerm2);

                boolean onlycharacters= matcher.find();
                boolean onlycharacters2= matcher2.find();

                if(onlycharacters==true && onlycharacters2==true) {                                                               //Reference : https://java2blog.com/capitalize-first-letter-java/

                    String searchTerm_firstLetStr = searchTerm.substring(0, 1);
                    String searchTerm2_firstLetStr = searchTerm2.substring(0, 1);

                    String searchTerm_remLetStr = searchTerm.substring(1);
                    String searchTerm2_remLetStr = searchTerm2.substring(1);

                    searchTerm_firstLetStr = searchTerm_firstLetStr.toUpperCase();
                    searchTerm2_firstLetStr = searchTerm2_firstLetStr.toUpperCase();
                    String firstLetter_searchTerm_CapitalizedName = searchTerm_firstLetStr + searchTerm_remLetStr;
                    String firstLetter_searchTerm2_CapitalizedName = searchTerm2_firstLetStr + searchTerm2_remLetStr;

                    //appending helloServlet/state/county to the heroku link

                    //task 1 heroku url
                    URL url = new URL("https://rocky-cove-70469.herokuapp.com/helloServlet/" + firstLetter_searchTerm_CapitalizedName + "/" + firstLetter_searchTerm2_CapitalizedName);



                    //Task 2 heroku url
                    //URL url = new URL("https://pure-meadow-30228.herokuapp.com/helloServlet/" + firstLetter_searchTerm_CapitalizedName + "/" + firstLetter_searchTerm2_CapitalizedName);

                    String data = "";


                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    InputStream input_stream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input_stream));
                    String line = "";
                    line = bufferedReader.readLine();
                    data = data + line;
                    JSONObject x = new JSONObject(data);

                    //first checking below if the second_day_before_yesterday is not equal to 1, if this condition is true then checking whether the result
                    // received is true or false, if true then displaying the number of cases for the range of days and safe for travel, otherwise displaying cases again and not safe for travel
                    //and checking if incorrect details or other wrong input is entered.

                    String result = x.getString("result");
                    if (!x.getString("second_day_before_yesterday").equals("-1")){
                        if (result.equals("true")) {
                            return "2nd day before yesterday cases : " + x.getString("second_day_before_yesterday") + "\n3rd day before yesterday cases : " + x.getString("third_day_before_yesterday") + "\n4th day before yesterday cases : " + x.getString("fourth_day_before_yesterday") + "\nSafe for travel";
                        } else if(result.equals("false")) {
                            return "2nd day before yesterday cases : " + x.getString("second_day_before_yesterday") + "\n3rd day before yesterday cases : " + x.getString("third_day_before_yesterday") + "\n4th day before yesterday cases : " + x.getString("fourth_day_before_yesterday") + "\nNot Safe for travel";
                        }
                    } else  {
                        return "Error! Incorrect details entered  or there is some problem with third party API. Pls enter correct state and county names ";
                    }
                }
                else{
                    return "Error, looks like there is some problem or left the fields blank.Pls enter correct state & county name";
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

           return "No data available";
        }

    }
}




