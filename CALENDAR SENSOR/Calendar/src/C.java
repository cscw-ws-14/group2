/* ICalendarExample.java */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

//you need to add the ical4j packge to the build path

public class C {

 public static void main(String[] args) throws InterruptedException {
  

 
  
  FileInputStream fin = null;
try {
	fin = new FileInputStream("example.ics");
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

  CalendarBuilder builder = new CalendarBuilder();
  Calendar calendar2 = null;
  
try {
	calendar2 = builder.build(fin);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (ParserException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


//iterate the calendar and including event to find if the people have event now
while (true)
	
{
	//start time of an event
	Date dend=null;
	//end time of an event
	Date dstart=null;
	//time format in the ics file
	SimpleDateFormat   df   =   new   SimpleDateFormat( "yyyyMMdd'T'hhmmss'Z'");  
	//current file
	Date dt=new Date();
	System.out.println("current time: "+dt); 
	boolean busy=false;
	String sStart="";
	String sEnd="";

	String time =df.format(dt);  
  for (Iterator i = calendar2.getComponents().iterator(); i.hasNext();) {
	    Component component = (Component) i.next();
	   // System.out.println("Component [" + component.getName() + "]");

	    for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
	    	
	        Property property = (Property) j.next();
	        String name = property.getName();
	        if (name.equals("DTSTART"))
	        {
	        	
	        	sStart=property.getValue();
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
	        	try {
					dstart = sdf.parse(sStart);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	System.out.println(dstart); 
	        	/**
	        	 try {
	        		   start = new net.fortuna.ical4j.model.Date(sStart,"yyyyMMdd'T'hhmmss'Z'");
	        		  } catch (ParseException e) {
	        		   e.printStackTrace();
	        		  }
	        	 System.out.println(sStart);
	        */
	        }
	        else  if (name.equals("DTEND"))
	        {
	        	sEnd=property.getValue();
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
	        	try {
					dend = sdf.parse(sEnd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	System.out.println(dend); 
	        	/**
	        	 try {
	        		   start = new net.fortuna.ical4j.model.Date(sStart,"yyyyMMdd'T'hhmmss'Z'");
	        		  } catch (ParseException e) {
	        		   e.printStackTrace();
	        		  }
	        	 System.out.println(sStart);
	        */
	        }
	       
	       
	        //System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
	    }
	    if((!(dstart==null))&&(!(dend==null)))
	    {
	    if (dt.after(dstart)&&dt.before(dend))
        {
        	busy=true;
        	System.out.println("start:"+sStart); 
        	System.out.println("end:"+sEnd); 
        	//System.out.println("busy: "+busy);
        }
       
     dstart=null;
     dend=null;
     System.out.println("busy: "+busy);
     Thread.sleep(1000);
	    }
	}

 }
 }
}