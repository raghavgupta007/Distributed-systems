//Raghav Gupta
//rgupta3


//This class acts as an controller

package ds.covid19_finder;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


@WebServlet(name = "HelloServlet",urlPatterns = {"/helloServlet/*"})

public class HelloServlet extends HttpServlet {
    private String message;
    JSONObject y= new JSONObject();
    Covidmodel model = new Covidmodel();

    public void init() {

        message = "covid19 details finder!";

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      //Below is the code for extracting the state and the county from the request and then passing them to the process() method of Covidmodel class

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2;
        sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf2.setTimeZone(TimeZone.getTimeZone("EST"));

        String request_time= sdf2.format(calendar.getTime());

        long startTime = System.currentTimeMillis();
        String state = "";
        String county = "";
       String url_path = String.valueOf(request.getRequestURL());
       String[] split_url = url_path.split("/");

        county = split_url[split_url.length-1];
        state = split_url[split_url.length-2];

        Results res = model.process(county, state);                                       //storing result in Results class object res

        try {
            y.put("result", res.getResult());
            y.put("fourth_day_before_yesterday", res.getFourth_day_before_yesterday());
            y.put("third_day_before_yesterday", res.getThird_day_before_yesterday());
            y.put("second_day_before_yesterday", res.getSecond_day_before_yesterday());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        response.setContentType("text/json");

        PrintWriter out = response.getWriter();
        out.println(y);                                                             //sending the response
        res.setyObject(y);

        //calculating the time when response was given
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf3;
        sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf3.setTimeZone(TimeZone.getTimeZone("EST"));

        String response_time= sdf3.format(calendar2.getTime());
        long endTime =  System.currentTimeMillis();
        long total_time = endTime-startTime;

//passing the values to the set methods of Results class

        res.setState(state);
        res.setCounty(county);
        res.setEndTime(endTime);
        res.setRequest_time(request_time);
        res.setResponse_time(response_time);
        res.setTotal_time(total_time);
        res.setRequest(request);


    }
    public void destroy() {
    }
}

