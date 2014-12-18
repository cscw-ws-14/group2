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


public class Component_Output_Display extends Component {

	private InputPort inport;
	@Override
	  protected void execute()
	  {
		Packet p;
		while ((p = inport.receive()) != null) {
			
			String Result = (String) p.getContent();
			
			System.out.println(Result);
			drop(p);
		}
	  }
	
	@Override
	  protected void openPorts() 
	 {
		
		 inport = openInput("IN");

	  }
	
}
