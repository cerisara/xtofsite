import java.io.*;
import java.util.*;

public class Qcm {
  public static void main(String args[]) throws Exception {
    Random r = new Random();
    {
      BufferedReader f = new BufferedReader(new FileReader(args[0]));
      int qi=0;
      for (qi=0;;qi++) {
	String s=f.readLine();
	if (s==null) break;
	s=s.trim();
	if (s.length()>0) {
	  for (;;) {
	    s=f.readLine().trim();
	    if (s.length()==0) break;
	  }
        }	
      }
      System.out.println("<script>var totalquestions="+qi+"</script>");
      f.close();
    }

    BufferedReader f = new BufferedReader(new FileReader(args[0]));
    for (int qi=0;;qi++) {
	String s=f.readLine();
	if (s==null) break;
	s=s.trim();
	if (s.length()>0) {
	  String q=s;
	  ArrayList<String> reps = new ArrayList<String>();
	  s=f.readLine().trim();
	  reps.add(s); // la 1ere rep est la bonne
	  String good = s;
	  for (;;) {
	    s=f.readLine().trim();
	    if (s.length()==0) break;
	    reps.add(s);
	  }
	  System.out.println("<div class=\"qheader\">");
	  System.out.println(q+"</div>");
	  System.out.println("<div class=\"qselections\">");
	  int v=0;
	  while (reps.size()>0) {
		int i=r.nextInt(reps.size());
		String re=reps.remove(i);
	  	System.out.println("<input type=\"radio\" value=\""+re+"\" name=\"question"+qi+"\">"+re+"<br>");
		v++;
	  }
	  System.out.println("</div><br>");
	}
    }
    f.close();
  }
}
