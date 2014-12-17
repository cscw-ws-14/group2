import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Today_Date {
	 public String cal() {
		 
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		   //get current date time with Date()
		   Date date = new Date();
		   return (dateFormat.format(date));
		  	 
	  }

}
