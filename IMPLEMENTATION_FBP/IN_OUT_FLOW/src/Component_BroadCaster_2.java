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
 * Component to get the Decision from Decider and broadcast it to 2 inputs
 */

@ComponentDescription("Generate decision from decider and broadcast it ")
@InPort(value = "IN", description = "Decider Result", type = String.class)
@OutPorts({ @OutPort(value = "OUT", arrayPort = true, description = "Broascast to 2 input values")})

public class Component_BroadCaster_2 extends Component{
	
	private InputPort inport;
	private OutputPort[] outport;
	
	@Override
	  protected void execute()
	  {
		Packet d;
		while ((d = inport.receive()) != null) 
		{
			String decision = (String) d.getContent();
			
			Packet read_keyboard = create(decision);
			Packet read_mouse = create(decision);
			
			outport[0].send(read_keyboard);
			outport[1].send(read_mouse);
			drop(d);
		}
	  }
	@Override
	  protected void openPorts() {

		 outport = openOutputArray("OUT");

		 inport = openInput("IN");

	  }

}
