//Name :Raghav Gupta, Andrew id: rgupta3

package com.example.edu.cmu_mism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//part of the code for this whole project has been taken from android lab 8 : AndroidInterestingPicture
public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText state_input;
    private EditText county_input;
    public TextView confirmed_cases;
    public static TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.submit_state_button);
        state_input = findViewById(R.id.state_input);
        county_input = findViewById(R.id.county_input);
        confirmed_cases = (TextView)findViewById(R.id.confirmed_cases);
        result= (TextView)findViewById(R.id.result);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final MainActivity ma = this;


        // Adding a listener to the submit button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = state_input.getEditableText().toString();
                String name_county = county_input.getEditableText().toString();

                System.out.println("selected state name = " + name);
                Fetchdata process = new Fetchdata();
                process.search(name,name_county,ma);

            }
        });
    }



    // This is called by the MainActivity object when the confirmed daily covid-19 cases are present.
    // This allows for passing back the details for updating the TextView
    //confirmed_cases is where the cases and the message that it is safe to travel or not shows up



    public void confirmed_cases_ready(String confirmed_cases_param) {

        System.out.println("checking confirmed cases in main activity below "+confirmed_cases_param);
        if (confirmed_cases_param != null) {

            confirmed_cases.setText("");
            confirmed_cases.setText(confirmed_cases_param);
            System.out.println("Confirmed cases are: "+confirmed_cases.getText());
            confirmed_cases.setVisibility(View.VISIBLE);

            //state_input.setVisibility(View.INVISIBLE);
        } else {

            System.out.println("No data available");
            confirmed_cases.setVisibility(View.INVISIBLE);
        }
        confirmed_cases.invalidate();
    }



}


















