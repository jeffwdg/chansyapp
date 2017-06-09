package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public String errorMessage="";

    //public static void main(String[] args) throws IOException{
    //	getIssues();
    //}


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String sentryIssues = getIssues();
    	JsonParser parser = new JsonParser();
    	JsonElement jsonObject =  parser.parse(sentryIssues);

        response.setContentType("applications/json");
        response.getWriter().print(sentryIssues);


    }


    public static String getIssues() throws IOException{
    	OkHttpClient client = new OkHttpClient();

    	Request request1 = new Request.Builder()
        .header("Authorization", "Bearer 748cce29bbd044ceab40642127ed70e66a3e6f7c9e9841cd9daa4e4786b0a0c9")
        .url("https://sentry.io/api/0/projects/confidential-x2/chansy/issues/?statsPeriod=24h")
        .build();

    	Response response1 = client.newCall(request1).execute();

    	//JsonParser parser = new JsonParser();
    	//JsonElement jsonObject =  parser.parse(response1.body().toString());
    	//JSONObject json = (JSONObject) parser.parse(stringToParse);

    	//System.out.println("Log analysis here");
      return response1.body().string();

    }


    public void classCastException(){
      Object i = Integer.valueOf(42);
      String s = (String)i;

    }

    public void arrayIndexException(){
      int[] array = new int[5];
      int boom = array[10]; // Throws the exception
    }

    public void numberFormatException(){
      String s = "FOOBAR";
      int i = Integer.parseInt(s);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String errorType = "2";

        try {
              //s = "FOOBAR";
              //int i = Integer.parseInt(s);
              // this line of code will never be reached
              //System.out.println("int value = " + s);

              /*
              switch(errorType){
                case "1" : classCastException(); break;
                case "2" : arrayIndexException(); break;
                default: numberFormatException(); break;
              }*/
          }
          catch (NumberFormatException nfe) {
              nfe.printStackTrace(pw);
              sw.toString(); // stack trace as a string
          }
          catch (ClassCastException ce) {
              ce.printStackTrace(pw);
              sw.toString(); // stack trace as a string
          }
          catch (ArrayIndexOutOfBoundsException ae) {
              ae.printStackTrace(pw);
              sw.toString(); // stack trace as a string
          }
          catch (Exception e) {
            e.printStackTrace(pw);
            sw.toString(); // stack trace as a string

          }
          //response.setContentType("text/html");
          //response.getWriter().print("I am CHANSY :D");



    }



}
