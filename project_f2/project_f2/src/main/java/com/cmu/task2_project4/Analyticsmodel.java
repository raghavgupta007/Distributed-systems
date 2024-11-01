//Raghav Gupta
//rgupta3

//This model class contains methods to perform analytics on the logged data in the database

package com.cmu.task2_project4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Analyticsmodel {
    private Map<String, Integer> state;
    private int totaltime;
    private int count;
    private ArrayList<Logs> logs;


    //resetValues method is used to set the initial values for the parameters.
    public void resetValues() {
        state = new HashMap<>();
        totaltime = 0;
        count = 0;
        logs = new ArrayList<>();
    }


 // updateState method checks whether state map contains the state passed to it and if found increases the value for it by 1,
 // or else it sets the value to 1.
    public void updateState(String loc) {

        if (state.containsKey(loc)) {
            state.put(loc, state.get(loc)+1);
        } else {
            state.put(loc, 1);
        }

    }

    //updateTotalTime calculates the total time taken between receiving the request from mobile app and the sending out the response to the mobile app
    public void updateTotalTime(int time) {
        count += 1;
        totaltime += time;
    }

    public void addLog(Logs log) {
        logs.add(log);
    }


// getTopState finds the state which has been queried the most by the user for checking confirmed cases,
// if there are more than 1 state which have been queried the most, then all of them will show up on the dashboard
    public String getTopState() {

        String topState = "No state in logs";
        int maxi = 0;
        for (String loc: state.keySet()){
            if (maxi<state.get(loc)) {
                maxi = state.get(loc);
            }
        }
        if (maxi>0) {
            topState = "";
            for (String loc: state.keySet()) {
                if (maxi == state.get(loc)) {
                    topState += loc + " , ";
                }
            }
            return topState.substring(0,topState.length()-2);
        }

        return topState;
    }

    public String getCount() {
        return String.valueOf(count);
    }


    //getAverageCompute calculates the average of the total time taken between receiving the requests from mobile app and the sending out the responses to the mobile app

    public String getAverageCompute() {
        if (count>0) {
            return String.valueOf(((float) totaltime) / ((float) count));
        } else {
            return "infinity";
        }
    }


    public String getLogs() {
        String logging = "";
        for (Logs l: logs) {
            logging += l.toHTMLContent();
        }
        return logging;
    }


}
