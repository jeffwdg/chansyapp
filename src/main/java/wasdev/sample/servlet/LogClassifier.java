package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifiers;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/LogClassifier")
public class LogClassifier extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public String errorMessage="";
    private String user = "688b31c7-d589-430a-a2ff-358753da706f";
    private String pass = "I2R7BPeJOM4g";
    private String javaErrorClassifier = "90e7acx197-nlc-2434";
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         System.out.println("Classifier here"+request.getMethod());
         //createClassifier(); //Already created
         //Classifiers clist = listClassifier();
         //String cl = logclassifierStatus();
         //response.setContentType("text/html");
         //response.getWriter().print(cl);
         //System.out.println("End"+cl);
         
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Posting logline"+request.getMethod());	
    	String logline = "No logline found";
    	logline = request.getParameter("logline");
    	System.out.println(logline);
    	
    	boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
 
        if (ajax) {
            // Handle ajax (JSON or XML) response.
        	System.out.println("ajax");
        } else {
            // Handle regular (JSP) response.
        	System.out.println("not ajax");
        }
        
        LogAnalyzer la = new LogAnalyzer();
        Map<String, String>  logDetails = la.getDateTime(logline);
        System.out.print("\t" + logDetails);
        
    	Classification clog = logClassify(logline);
    	response.setContentType("text/html");
    	System.out.println(clog.getClasses().get(0).getConfidence());
    	response.getWriter().print(clog.getClasses().get(0).getName() + "-" + clog.getClasses().get(0).getConfidence());
 
    }

    public void createClassifier() throws FileNotFoundException{
      System.out.println("Create classifier");
      NaturalLanguageClassifier service = new NaturalLanguageClassifier();
      service.setUsernameAndPassword(user, pass);
      
      try{
          Classifier classifier = service.createClassifier("Java Error Classifier", "en",
            new File("C:/Users/IBM_ADMIN/Documents/GitHub/chansyapp/src/main/webapp/data/logclassifier.csv")).execute();
          System.out.println(classifier);
          
        }catch(IllegalArgumentException e){
        	System.out.println("Illegal Exception");
          e.printStackTrace();
          
        }
        
 
    }
    
    public Classification logClassify(String log){
    	System.out.println("Classify log");
    	NaturalLanguageClassifier service = new NaturalLanguageClassifier();
    	service.setUsernameAndPassword(user, pass);

    	Classification classification = service.classify(javaErrorClassifier, log).execute();
    	System.out.println(classification.toString());
		return classification;
    }

    public Classifiers listClassifier(){
      NaturalLanguageClassifier service = new NaturalLanguageClassifier();
      service.setUsernameAndPassword(user, pass);

      Classifiers classifiers = service.getClassifiers().execute();
      System.out.println(classifiers);
      return classifiers;
    }

    public String logclassifierStatus(){
      NaturalLanguageClassifier service = new NaturalLanguageClassifier();
      service.setUsernameAndPassword(user, pass);

      Classifier classifier = service.getClassifier(javaErrorClassifier).execute();
      System.out.println(classifier.getName()+classifier.getStatusDescription());
      return classifier.getName()+" "+classifier.getStatus();
    }

}
