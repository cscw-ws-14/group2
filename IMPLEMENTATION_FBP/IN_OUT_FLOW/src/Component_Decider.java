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
 * Component to get the Result from the Check Meeting Component and Send YES to Read Input Component If User has Meeting, Else Send BUSY to Output
 */

@ComponentDescription("Generate String YES or BUSY based on result from Check Meeting ")
@InPort(value = "IN", description = "User MEETING Result", type = String.class)
@OutPorts({ @OutPort(value = "OUT",arrayPort = true, description = "If MEETING send to Read Input, else BUSY to Display Output")})

public class Component_Decider extends Component {
	
	private InputPort inport;
	private OutputPort[] outport;
	
	@Override
	  protected void execute()
	  {
		Packet p;
		int current_time = 0,start_time = 0,end_time = 0;
		String office_in_out_result = "OUT";
		
		while ((p = inport.receive()) != null) {
			
			/* Get Result from Check Meeting Component */
			String Result = (String) p.getContent();
			
			
			if(Result.equals("NOMEETING") || Result.equals("WORKING") || Result.equals("InTIME") || Result.equals("UserIN") || Result.equals("UserBUSY"))
			{
				
				if(Result.equals("InTIME"))
				{					
					
					String decision = "OFFICE TIME : YES";
					System.out.println(decision);
					Packet output = create(decision);
					outport[0].send(output);											
				}
				
				if(Result.equals("NOMEETING"))
				{
					String decision = "MEETING : NO";
					System.out.println(decision);
					Packet output = create(decision);
					outport[0].send(output);
				}
				
				if(Result.equals("WORKING"))
				{
					String decision = "Calendar Status : WORKING";
					System.out.println(decision);
					Packet output = create(decision);
					outport[0].send(output);
				}
				
				if(Result.equals("UserIN"))
				{
					String decision = "User Status : IN";
					System.out.println(decision);
					Packet output = create(decision);
					outport[0].send(output);
				}
				
				if(Result.equals("UserBUSY"))
				{
					String status = "User Status : BUSY";
					String decision = "BUSY";
					System.out.println(status);
					Packet output = create(decision);
					outport[0].send(output);
				}
				
			}
			else
			{
				
				if(Result.equals("MEETING"))
				{
				String decision = "OUT";
				Packet output = create(decision);
				outport[1].send(output);
				}
				
				if(Result.equals("HOLIDAY"))
				{
				String decision = "OUT";
				Packet output = create(decision);
				outport[1].send(output);
				}
				
				if(Result.equals("OutTime"))
				{
				String decision = "OUT";
				Packet output = create(decision);
				outport[1].send(output);
				}
				

				if(Result.equals("UserOUT"))
				{
				String decision = "OUT";
				Packet output = create(decision);
				outport[1].send(output);
				}
				
				if(Result.equals("UserIN not BUSY"))
				{
				String decision = "IN";
				Packet output = create(decision);
				outport[1].send(output);
				}
				
			}
			drop(p);
		}
		
	  }
	
	@Override
	  protected void openPorts() {

		 outport = openOutputArray("OUT");

		 inport = openInput("IN");

	  }
	

}
