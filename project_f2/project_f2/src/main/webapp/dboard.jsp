<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Covid 19 details finder</title>
</head>
<style>
    table, th, td {
        border:1px solid black;
    }
</style>
<body>
<h1><center>Covid 19 Details Finder Dashboard</center></h1>
<br/>
<h2>Operation Analytics</h2>
<%
    if (request.getAttribute("message")!=null) {
        out.print("Top queried state is " + request.getAttribute("message") + "<br>");
    }
    if (request.getAttribute("count")!=null) {
        out.print("Total call to the API are " + request.getAttribute("count") + "<br>");
    }
    if (request.getAttribute("averagecompute")!=null) {
        out.print("Average end to end time between request and response to and from the mobile application in milliseconds " + request.getAttribute("averagecompute") + " ms<br>");
    }
%>
<br>
<br>
<br>
<h2>Logs</h2>
<br>
<table style="width:100%">
    <%
        if (request.getAttribute("tablelogs")!=null) {
            out.print("<tr>" +
//                    "<th>" + "Request Link" + "</th>" +
//                    "<th>" + "Response" + "</th>" +
//                    "<th>" + "3rd Party API Link" + "</th>" +
//                    "<th>" + "3rd Party API Response" + "</th>" +
                    "<th>" + "Method of Request" + "</th>" +
                    "<th>" + "Requested parameter1 : state" + "</th>" +
                    "<th>" + "Requested parameter2 : county" + "</th>" +
                    "<th>" + "Time taken in milliseconds to process 3rd Party API " + "</th>" +
                    "<th>" + "Request time" + "</th>" +
                    "<th>" + "Response time" + "</th>" +
                    "<th>" + "Response: confirmed cases on second_day_before_yesterday" + "</th>" +
                    "<th>" + "Response confirmed cases on third_day_before_yesterday" + "</th>" +
                    "<th>" + "Response confirmed cases on fourth_day_before_yesterday" + "</th>" +
                    "</tr>");
            out.print(request.getAttribute("tablelogs"));
        }
    %>
</table>
<%--<a href="helloServlet/">Covid19 details finder</a>--%>
<%--<a href="getLogs">Get Server Logs</a>--%>
</body>
</html>