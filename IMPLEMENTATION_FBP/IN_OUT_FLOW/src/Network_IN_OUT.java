import com.jpmorrsn.fbp.engine.Network;
import com.jpmorrsn.fbp.examples.networks.Telegram;


public class Network_IN_OUT extends Network {
	
	protected void define() {
		component("Main Calendar Check", Component_Main_Calendar_Check.class);
		/*component("Write", com.jpmorrsn.fbp.components.WriteToConsole.class);*/
		component("Office Time Check",Component_Check_Office_Hours.class);	
		component("Office Meeting Check",Component_Check_Meeting_Calendar.class);
		
		component("Main Calendar Decider",Component_Decider.class);
		component("Office Time Decider",Component_Decider.class);
		component("Office Meeting Decider",Component_Decider.class);
		component("IN OUT Decider",Component_Decider.class);
		component("IN BUSY Decider",Component_Decider.class);
		
		component("Broadcast for IN OUT",Component_BroadCaster_3.class);
		component("Broadcast for IN BUSY",Component_BroadCaster_2.class);
		
		component("Read Input Motion IN OUT",Component_Read_Input.class);
		component("Read Input Keyboard IN OUT",Component_Read_Input.class);
		component("Read Input Mouse IN OUT",Component_Read_Input.class);
		
		component("Read Input Keyboard IN BUSY",Component_Read_Input.class);
		component("Read Input Mouse IN BUSY",Component_Read_Input.class);
		
		component("Receiver for IN OUT",Component_Receiver_3.class);
		component("Receiver for IN BUSY",Component_Receiver_2.class);
		
		component("Check IN OUT",Component_Check_IN_OUT.class);
		component("Check IN BUSY",Component_Check_IN_BUSY.class);
		
		component("Output for Calendar",Component_Output_Display.class);
		component("Output for Office",Component_Output_Display.class);
		component("Output for Meeting",Component_Output_Display.class);
		component("Output for IN OUT",Component_Output_Display.class);
		component("Output for IN BUSY",Component_Output_Display.class);
		component("Output for IN BUSY 1",Component_Output_Display.class);
		
		
		initialize("Calendar.db:Year_2014", component("Main Calendar Check"), port("SOURCE"));		
		/* Specify time in 24 hours format */ 
		initialize("1:00-23:00", component("Office Time Check"), port("TIME[1]"));
		initialize("NOMEETING", component("Office Meeting Check"), port("MEETING[1]"));
		
		connect(component("Main Calendar Check"), port("OUT"), component("Main Calendar Decider"), port("IN"));
		connect(component("Main Calendar Decider"), port("OUT[0]"), component("Office Time Check"), port("TIME[0]"));
		connect(component("Main Calendar Decider"), port("OUT[1]"), component("Output for Calendar"), port("IN"));
		
		connect(component("Office Time Check"), port("OUT[0]"), component("Office Time Decider"), port("IN"));
		connect(component("Office Time Decider"), port("OUT[0]"), component("Office Meeting Check"), port("MEETING[0]"));
		connect(component("Office Time Decider"), port("OUT[1]"), component("Output for Office"), port("IN"));
		
		connect(component("Office Meeting Check"), port("OUT[0]"), component("Office Meeting Decider"), port("IN"));
		connect(component("Office Meeting Decider"), port("OUT[0]"), component("Broadcast for IN OUT"), port("IN"));
		connect(component("Office Meeting Decider"), port("OUT[1]"), component("Output for Meeting"), port("IN"));
		
		connect(component("Broadcast for IN OUT"), port("OUT[0]"), component("Read Input Motion IN OUT"), port("IN"));
		connect(component("Broadcast for IN OUT"), port("OUT[1]"), component("Read Input Keyboard IN OUT"), port("IN"));
		connect(component("Broadcast for IN OUT"), port("OUT[2]"), component("Read Input Mouse IN OUT"), port("IN"));
		
		connect(component("Read Input Motion IN OUT"), port("OUT"), component("Receiver for IN OUT"), port("IN[0]"));
		connect(component("Read Input Keyboard IN OUT"), port("OUT"), component("Receiver for IN OUT"), port("IN[1]"));
		connect(component("Read Input Mouse IN OUT"), port("OUT"), component("Receiver for IN OUT"), port("IN[2]"));
		
		connect(component("Receiver for IN OUT"), port("OUT"), component("Check IN OUT"), port("IN"));
		
		connect(component("Check IN OUT"), port("OUT"), component("IN OUT Decider"), port("IN"));
		connect(component("IN OUT Decider"), port("OUT[0]"), component("Broadcast for IN BUSY"), port("IN"));
		connect(component("IN OUT Decider"), port("OUT[1]"), component("Output for IN OUT"), port("IN"));
		
		connect(component("Broadcast for IN BUSY"), port("OUT[0]"), component("Read Input Keyboard IN BUSY"), port("IN"));
		connect(component("Broadcast for IN BUSY"), port("OUT[1]"), component("Read Input Mouse IN BUSY"), port("IN"));
		
		connect(component("Read Input Keyboard IN BUSY"), port("OUT"), component("Receiver for IN BUSY"), port("IN[0]"));
		connect(component("Read Input Mouse IN BUSY"), port("OUT"), component("Receiver for IN BUSY"), port("IN[1]"));
		
		connect(component("Receiver for IN BUSY"), port("OUT"), component("Check IN BUSY"), port("IN"));
		
		connect(component("Check IN BUSY"), port("OUT"), component("IN BUSY Decider"), port("IN"));
		connect(component("IN BUSY Decider"), port("OUT[0]"), component("Output for IN BUSY"), port("IN"));
		connect(component("IN BUSY Decider"), port("OUT[1]"), component("Output for IN BUSY 1"), port("IN"));
		
		
		
		
		
	}
	
	public static void main(final String[] argv) throws Exception {
		while(true)
		{
	    new Network_IN_OUT().go();
	    System.out.print("***************************************************************\n");
	    Thread.sleep(5000);
		}
	  }

}
