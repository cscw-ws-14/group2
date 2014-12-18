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
 * Component to get the Office hours from the User and check if the current time is within the range, if so then output as "IN" else "OUT"
 */
@ComponentDescription("Generate String IN or OUT based on User giver Time ")
@InPort(value = "TIME", arrayPort = true, description = "User Office Time", type = String.class)
@OutPorts({ @OutPort(value = "OUT", arrayPort = true, description = "If IN send to Component_Check_Meeting_Calendar, if OUT send to Display")})



public class Component_Check_Office_Hours extends Component {
	
	private OutputPort[] outport;

	private InputPort[] user_time;
	
	
	@Override
	  protected void execute()
	  {
		 Packet u_time;
		 Packet u_status;
		 String start_end_time[];
		 String decision;

		    while ((u_status = user_time[0].receive()) != null) {
		    	
		    	/* Get User Office Time */
		    	u_time = user_time[1].receive();
		    	String office_time = (String) u_time.getContent();
		    	start_end_time = office_time.split("-");
		    	String start_time = start_end_time[0];
		    	String end_time = start_end_time[1];
		    	
		    	/* Convert Start Time from String to Int */ 
		    	String start_time_hr_min[] = start_time.split(":");
		    	String start_time_hr = start_time_hr_min[0];
		    	String start_time_min = start_time_hr_min[1];
		    	int s_time_hr = Integer.parseInt(start_time_hr);
		    	int s_time_min = Integer.parseInt(start_time_min);
		    	
		    	/* Convert Start hours to minutes */ 
		    	int officeStartTime = (s_time_hr * 60) + s_time_min;
		    	
		    	/* Convert End Time from String to Int */ 
		    	String end_time_hr_min[] = end_time.split(":");
		    	String end_time_hr = end_time_hr_min[0];
		    	String end_time_min = end_time_hr_min[1];
		    	int e_time_hr = Integer.parseInt(end_time_hr);
		    	int e_time_min = Integer.parseInt(end_time_min);
		    	
		    	/* Convert End hours to minutes */ 
		    	int officeEndTime = (e_time_hr * 60) + e_time_min;
		    	
		    	/* Get current Time in mins */
		    	long millisSinceGMTMidnight = System.currentTimeMillis() % (24L * 60*60*1000);
		    	
		    	/* GMT time + one hour*/
		    	long currentTime = (millisSinceGMTMidnight/(60*1000)) + 60;
		    	
		    	/* Send to Decider */
		    	
		    	if ((currentTime > officeStartTime) && (currentTime < officeEndTime) )
		    	{
		    		decision = "InTIME";
		    		Packet p1 = create(decision);
			        outport[0].send(p1);
		    	}
		    	else
		    	{
		    		decision = "OutTIME";	
		    		Packet p1 = create(decision);
		    		outport[0].send(p1);
		    	}
		    	
		    	

		    	drop(u_time);
		    	drop(u_status);
		    			    			    	
		    }
		
	  }
	
	 @Override
	  protected void openPorts() {

		 outport = openOutputArray("OUT");

	    user_time = openInputArray("TIME");

	  }
	 
	
	

}
