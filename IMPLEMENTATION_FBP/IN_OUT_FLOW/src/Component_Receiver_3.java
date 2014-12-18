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
 * Component to fuse all Read values
 */

@ComponentDescription("If Got Read values from the Read Input Component, Then fuse the values and send it ")
@InPort(value = "IN", arrayPort = true, description = "Get Read values", type = String.class)
@OutPorts({ @OutPort(value = "OUT", description = "Send the Read values")})


public class Component_Receiver_3 extends Component {
	
	private InputPort[] inport;
	private OutputPort outport;
	
	protected void execute()
	  {
		Packet ip1;
		Packet ip2;
		Packet ip3;
		while (((ip1 = inport[0].receive()) != null) && ((ip2 = inport[1].receive()) != null) && ((ip3 = inport[2].receive()) != null)) 
		{
			/* Receive all Inputs */
			String Motion_Value = (String) ip1.getContent();
			String Keyboard_Value = (String) ip2.getContent();
			String Mouse_Value = (String) ip3.getContent();
			
			/* Fuse Inputs */
			String fused_inputs = (Motion_Value+","+Keyboard_Value+","+Mouse_Value);
			
			/* Send to Check IN OUT Component */
			System.out.println("Motion Value : "+Motion_Value+" Keyboard Value : "+Keyboard_Value+" Mouse_Value : "+Mouse_Value);
			Packet InOut = create(fused_inputs);
			outport.send(InOut);
			
			drop(ip1);
			drop(ip2);
			drop(ip3);
		}
		
	  }

	@Override
	  protected void openPorts() {

		 outport = openOutput("OUT");

		 inport = openInputArray("IN");

	  }
}
