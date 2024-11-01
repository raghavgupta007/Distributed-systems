//Raghav Gupta
//rgupta3


package ds.covid19_finder;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.net.URL;


//This class consolidates data from Helloservlet and Covidmodel for easily passing the variables between the classes

public class Results {
    boolean result;
    String fourth_day_before_yesterday;
    String third_day_before_yesterday;
    String second_day_before_yesterday;
    String response_time;
    long endTime;
    String county;
    String state;
    long total_time;
    String request_time;
    HttpServletRequest request;
    JSONObject yObject;
    String data;


    public void setyObject(JSONObject yObject) {
        this.yObject = yObject;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public JSONObject getyObject() {
        return yObject;
    }

    public String getData() {
        return data;
    }

    public URL getUrl() {
        return url;
    }

    URL url;

    public void setCounty(String county) {
        this.county = county;
    }

    public boolean isResult() {
        return result;
    }


    public void setState(String state) {
        this.state = state;
    }

       public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Results(){
    }

    public boolean getResult() {
        return result;
    }

    public String getFourth_day_before_yesterday() {
        return fourth_day_before_yesterday;
    }

    public String getThird_day_before_yesterday() {
        return third_day_before_yesterday;
    }

    public String getSecond_day_before_yesterday() {
        return second_day_before_yesterday;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setFourth_day_before_yesterday(String fourth_day_before_yesterday) {
        this.fourth_day_before_yesterday = fourth_day_before_yesterday;
    }

    public void setThird_day_before_yesterday(String third_day_before_yesterday) {
        this.third_day_before_yesterday = third_day_before_yesterday;
    }

    public void setSecond_day_before_yesterday(String second_day_before_yesterday) {
        this.second_day_before_yesterday = second_day_before_yesterday;
    }
}
