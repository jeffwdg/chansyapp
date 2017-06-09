package wasdev.sample.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ErrorCorrection extends HttpServlet{

     public static void main(String[] args){

    	System.out.print("\t"    );
    }


	 /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         System.out.println("Log analysis here"+request.getMethod());

         //response.setContentType("text/html");
         //response.getWriter().print(cl);
    }


   
    public void checkLineCode(String fileloc, int linenum){
	   
    	 try
    	  {
    	    BufferedReader reader = new BufferedReader(new FileReader(fileloc));
    	    String line;
    	    while ((line = reader.readLine()) != null)
    	    {
    	      //records.add(line);
    	    }
    	    reader.close();
    	     
    	  }
    	  catch (Exception e)
    	  {
    	    System.err.format("Exception occurred trying to read '%s'.", fileloc);
    	    e.printStackTrace();
    	  
    	  }
 
	}

	 public String syntaxType(String codeline){
		return codeline;

	 

	 }




}
