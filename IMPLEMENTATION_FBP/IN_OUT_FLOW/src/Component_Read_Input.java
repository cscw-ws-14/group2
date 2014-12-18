import java.util.Random;

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
 * Component to Read values from motion sensor
 */

@ComponentDescription("If Got Yes from the Decider, Then read the values and send it ")
@InPort(value = "IN", description = "Get Yes from decider", type = String.class)
@OutPorts({ @OutPort(value = "OUT", description = "Send the Read values")})

public class Component_Read_Input extends Component {
	
	private InputPort inport;
	private OutputPort outport;
	
	protected void execute()
	  {
		Packet p;
		Random rand = new Random();
		String read;
		
		while ((p = inport.receive()) != null) {
			
			int randomInt = rand.nextInt(100);
			
			if(randomInt > 49)
			{
				read = "true";
			}
			else
			{
				read = "false";
			}
			
			Packet receiver = create(read);
			outport.send(receiver);
		
			drop(p);
		}
		
	  }
	
	@Override
	  protected void openPorts() {

		 outport = openOutput("OUT");

		 inport = openInput("IN");

	  }

}
