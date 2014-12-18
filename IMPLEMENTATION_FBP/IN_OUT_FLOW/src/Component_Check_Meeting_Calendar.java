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

public class Component_Check_Meeting_Calendar extends Component {
	
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
			 String Meeting_Calendar = (String) u_meet.getContent();
			 
			 /* Send to Decider */
			 
			 Packet p1 = create(Meeting_Calendar);
		     outport[0].send(p1);
				 
			 
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
