import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;

import com.jpmorrsn.fbp.engine.Component;
import com.jpmorrsn.fbp.engine.ComponentDescription;
import com.jpmorrsn.fbp.engine.InPort;
import com.jpmorrsn.fbp.engine.InputPort;
import com.jpmorrsn.fbp.engine.MustRun;
import com.jpmorrsn.fbp.engine.OutPort;
import com.jpmorrsn.fbp.engine.OutPorts;
import com.jpmorrsn.fbp.engine.OutputPort;
import com.jpmorrsn.fbp.engine.Packet;

/**
 * Component to get the Meeting schedule from the ICS file and check if the user has Meeting, if so then output as "IN" else "BUSY"
 */

@ComponentDescription("Generate String IN or BUSY based on Meeting Schedule (ICS File) ")
@InPort(value = "MEETING", arrayPort = true, description = "User MEETING Calendar", type = String.class)
@OutPorts({ @OutPort(value = "OUT", arrayPort = true, description = "If IN send to Component_Meeting_Calendar_Check, if OUT send to Display")})

public class Component_Check_Meeting_Calendar_test extends Component {
	
	private InputPort[] user_meeting;
	private OutputPort[] outport;
	
	
	@Override
	  protected void execute()
	  {
		 Packet u_meet;
		 Packet u_status;
		 
		 while ((u_status = user_meeting[0].receive()) != null) {
			 
			 /* Get Meeting Calendar FileName */
			 u_meet = user_meeting[1].receive();
			 String Meeting_Calendar = (String) u_meet.getContent()+".ics";
			 
			 /* Parsing the ics file according to the fiilname */
			 File ICS=new File(Meeting_Calendar);
			 if (ICS.exists()){
		     System.out.println("The related ics file is existed"+" "+Meeting_Calendar);
			 FileInputStream fin = null;
			 try {
			 	   fin = new FileInputStream(ICS);
			 } 
			 catch (FileNotFoundException e) {
			 	e.printStackTrace();
			 }
			 
			 /* Construct the ics file parser*/
			 CalendarBuilder builder = new CalendarBuilder();
			 Calendar calendar = null;
			 
			 try {
				   calendar = builder.build(fin);
			 } catch (IOException e) {
					e.printStackTrace();
			 } catch (ParserException e) {
			 e.printStackTrace();
			 }
			 
			 /*Parsing*/
			 
			 //start time of an event
			 Date dend=null;
			 //end time of an event
			 Date dstart=null;
			 //time format in the ics file
			 SimpleDateFormat   df   =   new   SimpleDateFormat( "yyyyMMdd'T'hhmmss'Z'");  
			 //current time
			 Date dt=new Date();
			 System.out.println("current time: "+dt); 
			 boolean busy=false;
			 String sStart="";
			 String sEnd="";

			 String time =df.format(dt);  
			 /*Iterating the file to find is there now a meeting*/
			 for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
				 net.fortuna.ical4j.model.Component component = (net.fortuna.ical4j.model.Component) i.next();
				  

				    for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
				    	
				        Property property = (Property) j.next();
				        String name = property.getName();
				        
				        if (name.equals("DTSTART")){
				        	
				        	sStart=property.getValue();
				        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
				        	try {
								dstart = sdf.parse(sStart);
							} catch (ParseException e) {
						     	e.printStackTrace();
							}
				        	System.out.println("Start: "+dstart); 
				        }
				        else  if (name.equals("DTEND")) {
				        	sEnd=property.getValue();
				        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
				        	try {
								dend = sdf.parse(sEnd);
							} catch (ParseException e) {
								e.printStackTrace();
							}
				        	System.out.println("End: "+dend); 
				    }
				    if((!(dstart==null))&&(!(dend==null)))
				    {
				    if (dt.after(dstart)&&dt.before(dend))
			        {
			        	busy=true;
			        	System.out.println("start:"+sStart); 
			        	System.out.println("end:"+sEnd); 
			        }
			       
			     System.out.println("busy: "+busy);
				    }
				  }
		   }
			 /* Send to Decider */
			 if (busy){
			 Packet p1 = create("MEETING");
		     outport[0].send(p1);
			 }
			 else {
				 Packet p1 = create("NOMEETING");
			     outport[0].send(p1);
			 }
			}
			 else{
				 System.out.println("The related ics file is not existed"+" "+Meeting_Calendar);
				 Packet p1 = create("NOMEETING");
			     outport[0].send(p1);
			 }
			 
			 drop(u_meet);
			 drop(u_status);
		 }
	  }
	
	@Override
	  protected void openPorts() {

		 outport = openOutputArray("OUT");

		user_meeting = openInputArray("MEETING");

	  }

}