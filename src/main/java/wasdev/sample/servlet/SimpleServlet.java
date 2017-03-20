package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public String errorMessage="";
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String s="";
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
          response.setContentType("text/html");
          response.getWriter().print("I am CHANSY :D");



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
    }

}
