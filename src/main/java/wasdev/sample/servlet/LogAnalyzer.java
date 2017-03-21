package wasdev.sample.servlet;

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
public class LogAnalyzer extends HttpServlet{
	
     public static void main(String[] args){
    	String x = "09:54:44.565 Jan/23/2012 [main] ERROR java.lang.NullPointerException: java.lang.String incompatible with java.lang.Integer at ClassCastExceptionTest.storeItem(ClassCastExceptionTest.java:6)";
    	System.out.println(x);
    	Map<String, String> logDetails = getDateTime(x);
    	//System.out.println(logDetails[0]  + logDetails[1] + logDetails[2] +logDetails[3]+logDetails[4]);
    	System.out.print("\t" + logDetails);
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
    

    
    public Map<String, String> getLogDetails(String logline){
    	Map<String, String> logdetails = new HashMap<String, String>();
    	
 
    	
    	
    	return logdetails; 
    }
    
 
    //Returns a string array - 0- date 1-time 2-filename 3-linenum
    public static Map<String, String> getDateTime(String logline){
    	System.out.println("Get log details");
    	String logtime="not found";
    	String logdate="not found";
    	String filename="not found";
    	String linenum = "not found";
    	String exception = "None";
    	String lmethod = "none";
        String[] logl = logline.split(" ");
        Pattern time = Pattern.compile(".*([01]?[0-9]|2[0-3]):[0-5][0-9].*");
        Pattern date = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
        Pattern file = Pattern.compile("([^\\s]+(\\.(?i)(java|js|class))$)");
        Pattern getF = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)\\(([^)]+)\\)");
        Pattern eP = Pattern.compile("([^\\s])+([^\\s])+(\\.(?i)[_A-Za-z0-9-]+(exception|Exception|error)+[\\s_A-Za-z0-9-:])$");
        Pattern fileP = Pattern.compile("^\\([_A-Za-z0-9-\\.]+[_A-Za-z]+(:[0-9][0-9][0-9][0-9])\\)");
        
        boolean timefound = false;
        boolean datefound = false;
        boolean filefound = false;
        boolean exceptionfound = false;
        Map<String, String> logdetails = new HashMap<String, String>();
        
        for(int i=0; i<logl.length; i++){
        	System.out.println(logl[i]);
        	
        	if(timefound && datefound && filefound){break;}
        	
        	 
        	 
        	if(logl[i].contains("(") && logl[i].contains(")") ){

        		String methodR[] = logl[i].split("\\(");
        		String[] logmethod = logl[i].split(":");
        		
        		System.out.println("Method:"+methodR[0]);
        		 
        		String[] filex = logmethod[0].split("\\(");
        		filename = filex[1];
        		linenum  = logmethod[1].replace(")", "");
        		lmethod = methodR[0];
 
        	}
        	
        	 
        	 
        	 Matcher ep = eP.matcher(logl[i]);
             //get exception match
              if(ep.matches()){
             		System.out.println("Found match "+logl[i]);
             		exception = logl[i];
             		exceptionfound = true;
              }else{
                  System.out.println("N");
              }
 
          
            Matcher m = time.matcher(logl[i]);
            //get time match
             if(m.matches()){
            		//System.out.println("Found match "+logl[i]);
            		logtime = logl[i];
            		timefound = true;
             }else{
                 System.out.println("N");
             }
             
             Matcher d = date.matcher(logl[i]);
             //get date match
             if(d.matches()){
            		logdate= logl[i];
            		datefound = true;
             }else{
                 System.out.println("N");
             } 
             
        }
        
        logdetails.put("logdate",logdate);
        logdetails.put("logtime",logtime);
        logdetails.put("method",lmethod);
        logdetails.put("filename",filename);
        logdetails.put("linenum",linenum);
        logdetails.put("exception",exception);
        
       
    	
    	return logdetails;
    }
    
    
    
}
