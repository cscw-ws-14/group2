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
 * Component to Display the Output
 */

@ComponentDescription("Print Values from Inport")
@InPort(value = "IN", description = "Values to print in UI", type = String.class)
@OutPort(value = "OUT", description = "Values to send", type = String.class)


public class Component_Output_Display extends Component {

	private InputPort inport;
	private OutputPort outport;
	
	
	@Override
	  protected void execute()
	  {
		Packet p;
		while ((p = inport.receive()) != null) {
			
			String Result = (String) p.getContent();
			
			System.out.println(Result);
			
			Packet p1 =  create(Result);
			
			outport.send(p1);
			
			drop(p);
		}
	  }
	
	@Override
	  protected void openPorts() 
	 {
		
		 inport = openInput("IN");
		 outport = openOutput("OUT");

	  }
	
}
