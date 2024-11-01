//Raghav Gupta
//rgupta3


//This class is used for formatting the logs. This toHTMLContent method does the formatting
package com.cmu.task2_project4;

public class Logs {
    String request;
    String response;
    String request_api;
    String response_from_api;
    String method_type_of_request;
    String time_taken_to_get_response;
    String requested_state;
    String requested_county;
    String request_time;
    String response_time;
    String second_day_before_yesterday;
    String third_day_before_yesterday;
    String fourth_day_before_yesterday;

    public Logs(String request, String response, String request_api, String response_from_api, String method_type_of_request, String requested_state, String requested_county,
                String time_taken_to_get_response,String request_time,
                String response_time,String second_day_before_yesterday, String third_day_before_yesterday,String fourth_day_before_yesterday ) {
        this.request = request;
        this.response = response;
        this.request_api = request_api;
        this.response_from_api = response_from_api;
        this.method_type_of_request = method_type_of_request;
        this.time_taken_to_get_response = time_taken_to_get_response;
        this.requested_state = requested_state;
        this.requested_county = requested_county;
        this.request_time = request_time;
        this.response_time = response_time;
        this.second_day_before_yesterday = second_day_before_yesterday;
        this.third_day_before_yesterday = third_day_before_yesterday;
        this.fourth_day_before_yesterday = fourth_day_before_yesterday;
    }

    //gives formatted table
    public String toHTMLContent() {
        return "<tr>" +
//                "<td>" + request + "</td>" +
//                "<td>" + response + "</td>" +
//                "<td>" + request_api + "</td>" +
//                "<td>" + response_from_api + "</td>" +
                "<td>" + method_type_of_request + "</td>" +
                "<td>" + requested_state + "</td>" +
                "<td>" + requested_county + "</td>" +
                "<td>" + time_taken_to_get_response + "</td>" +
                "<td>" + request_time + "</td>" +
                "<td>" + response_time + "</td>" +
                "<td>" + second_day_before_yesterday + "</td>" +
                "<td>" + third_day_before_yesterday + "</td>" +
                "<td>" + fourth_day_before_yesterday + "</td>" +
                "</tr>";
    }
}
