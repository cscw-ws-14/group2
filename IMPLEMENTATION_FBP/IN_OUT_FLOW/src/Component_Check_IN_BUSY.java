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
 * Component to Check if the user is BUSY or not in his room based on the input values
 */

@ComponentDescription("Generate String IN or BUSY based on input values from receiver ")
@InPort(value = "IN", description = "Sensor values", type = String.class)
@OutPorts({ @OutPort(value = "OUT", description = "User IN or BUSY")})


public class Component_Check_IN_BUSY extends Component {
	
	private InputPort inport;
	private OutputPort outport;
	
	
	@Override
	  protected void execute()
	  {
		Packet p;
		int count = 0;
		while ((p = inport.receive()) != null) 
		{
			String result = (String) p.getContent();
			String[] sensor_values = result.split(",");
			
			
			for(int i=0;i<sensor_values.length;i++)
			{
				if(sensor_values[i].equals("true"))
				{
					count += 1; 
				}
			}
			if(count > 0)
			{
				Packet decision = create("UserBUSY");
				outport.send(decision);
				
			}
			else
			{
				Packet decision = create("UserIN Not BUSY");
				outport.send(decision);
				
			}
			drop(p);
		
		}
	  }
	
	@Override
	  protected void openPorts() {

		 outport = openOutput("OUT");

		 inport = openInput("IN");

	  }


}
