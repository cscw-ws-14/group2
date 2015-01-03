import com.jpmorrsn.fbp.engine.Network;
import com.jpmorrsn.fbp.examples.networks.Telegram;


public class Network_IN_OUT extends Network {
	
	protected void define() {
		
		for(int i =1;i<=21;i++)
		{	
			
			String Calendar_check = "Main Calendar Check"+i;
			String Office_time_check = "Office Time Check"+i;
			String Meeting_check = "Office Meeting Check"+i;
			
			String Calendar_decider = "Main Calendar Decider"+i;
			String Office_time_decider = "Office Time Decider"+i;
			String Meeting_decider =  "Office Meeting Decider"+i;
			String InOut_decider = "IN OUT Decider"+i;
			String InBusy_decider = "IN BUSY Decider"+i;
			
			String Broadcast_InOut = "Broadcast for IN OUT"+i;
			String Broadcast_InBusy = "Broadcast for IN BUSY"+i;
			
			String Read_Input_Motion_InOut = "Read Input Motion IN OUT"+i;
			String Read_Input_Keyboard_InOut = "Read Input Keyboard IN OUT"+i;
			String Read_Input_Mouse_InOut = "Read Input Mouse IN OUT"+i;
			
			String Read_Input_Keyboard_InBusy = "Read Input Keyboard IN BUSY"+i; 
			String Read_Input_Mouse_InBusy = "Read Input Mouse IN BUSY"+i; 
			
			String Receiver_InOut = "Receiver for IN OUT"+i;
			String Receiver_InBusy = "Receiver for IN BUSY"+i;
			
			String Check_InOut = "Check IN OUT"+i;
			String Check_InBusy = "Check IN BUSY"+i;
			
			String Output_Calendar = "Output for Calendar"+i;
			String Output_Office = "Output for Office"+i;
			String Output_Meeting = "Output for Meeting"+i;
			String Output_InOut = "Output for IN OUT"+i;
			String Output_InBusy = "Output for IN BUSY"+i;
			String Output_InBusyDup = "Output for IN BUSY_DUP"+i;
			
			String Mosquitto_Calendar = "Mosquitto Calendar"+i;
			String Mosquitto_Office = "Mosquitto Office"+i;
			String Mosquitto_Meeting = "Mosquitto Meeting"+i;
			String Mosquitto_InOut = "Mosquitto IN OUT"+i;
			String Mosquitto_InBusy = "Mosquitto IN BUSY"+i;
			String Mosquitto_InBusyDup = "Mosquitto IN BUSY_DUP"+i;
			
			
		component(Calendar_check, Component_Main_Calendar_Check.class);
		component(Office_time_check,Component_Check_Office_Hours.class);	
		component(Meeting_check,Component_Check_Meeting_Calendar.class);
		
		component(Calendar_decider,Component_Decider.class);
		component(Office_time_decider,Component_Decider.class);
		component(Meeting_decider,Component_Decider.class);
		component(InOut_decider,Component_Decider.class);
		component(InBusy_decider,Component_Decider.class);
		
		component(Broadcast_InOut,Component_BroadCaster_3.class);
		component(Broadcast_InBusy,Component_BroadCaster_2.class);
		
		component(Read_Input_Motion_InOut,Component_Read_Input.class);
		component(Read_Input_Keyboard_InOut,Component_Read_Input.class);
		component(Read_Input_Mouse_InOut,Component_Read_Input.class);
		
		component(Read_Input_Keyboard_InBusy,Component_Read_Input.class);
		component(Read_Input_Mouse_InBusy,Component_Read_Input.class);
		
		component(Receiver_InOut,Component_Receiver_3.class);
		component(Receiver_InBusy,Component_Receiver_2.class);
		
		component(Check_InOut,Component_Check_IN_OUT.class);
		component(Check_InBusy,Component_Check_IN_BUSY.class);
		
		component(Output_Calendar,Component_Output_Display.class);
		component(Output_Office,Component_Output_Display.class);
		component(Output_Meeting,Component_Output_Display.class);
		component(Output_InOut,Component_Output_Display.class);
		component(Output_InBusy,Component_Output_Display.class);
		component(Output_InBusyDup,Component_Output_Display.class);
		
		component(Mosquitto_Calendar,Component_Mosquitto.class);
		component(Mosquitto_Office,Component_Mosquitto.class);
		component(Mosquitto_Meeting,Component_Mosquitto.class);
		component(Mosquitto_InOut,Component_Mosquitto.class);
		component(Mosquitto_InBusy,Component_Mosquitto.class);
		component(Mosquitto_InBusyDup,Component_Mosquitto.class);
		
		
        
		
		
		initialize("Calendar.db:Year_2015", component(Calendar_check), port("SOURCE"));		
		/* Specify time in 24 hours format */ 
		initialize("0:00-23:59", component(Office_time_check), port("TIME[1]"));
		initialize("NOMEETING", component(Meeting_check), port("MEETING[1]"));
		
		
		connect(component(Calendar_check), port("OUT"), component(Calendar_decider), port("IN"));
		connect(component(Calendar_decider), port("OUT[0]"), component(Office_time_check), port("TIME[0]"));
		connect(component(Calendar_decider), port("OUT[1]"), component(Output_Calendar), port("IN"));
		
		initialize("user"+i+"Calendar", component(Mosquitto_Calendar), port("CLIENTID"));
        initialize("u"+i+"/decision", component(Mosquitto_Calendar), port("TOPIC"));
        connect(component(Output_Calendar), port("OUT"), component(Mosquitto_Calendar), port("CONTENT"));
        
		
		connect(component(Office_time_check), port("OUT[0]"), component(Office_time_decider), port("IN"));
		connect(component(Office_time_decider), port("OUT[0]"), component(Meeting_check), port("MEETING[0]"));
		connect(component(Office_time_decider), port("OUT[1]"), component(Output_Office), port("IN"));
		
		initialize("user"+i+"office", component(Mosquitto_Office), port("CLIENTID"));
        initialize("u21/decision", component(Mosquitto_Office), port("TOPIC"));
        
        connect(component(Output_Office), port("OUT"), component(Mosquitto_Office), port("CONTENT"));
        
		
		connect(component(Meeting_check), port("OUT[0]"), component(Meeting_decider), port("IN"));
		connect(component(Meeting_decider), port("OUT[0]"), component(Broadcast_InOut), port("IN"));
		connect(component(Meeting_decider), port("OUT[1]"), component(Output_Meeting), port("IN"));
		
		initialize("user"+i+"Meeting", component(Mosquitto_Meeting), port("CLIENTID"));
        initialize("u"+i+"/decision", component(Mosquitto_Meeting), port("TOPIC"));
        
        connect(component(Output_Meeting), port("OUT"), component(Mosquitto_Meeting), port("CONTENT"));
        
        
		
		connect(component(Broadcast_InOut), port("OUT[0]"), component(Read_Input_Motion_InOut), port("IN"));
		connect(component(Broadcast_InOut), port("OUT[1]"), component(Read_Input_Keyboard_InOut), port("IN"));
		connect(component(Broadcast_InOut), port("OUT[2]"), component(Read_Input_Mouse_InOut), port("IN"));
		
		connect(component(Read_Input_Motion_InOut), port("OUT"), component(Receiver_InOut), port("IN[0]"));
		connect(component(Read_Input_Keyboard_InOut), port("OUT"), component(Receiver_InOut), port("IN[1]"));
		connect(component(Read_Input_Mouse_InOut), port("OUT"), component(Receiver_InOut), port("IN[2]"));
		
		connect(component(Receiver_InOut), port("OUT"), component(Check_InOut), port("IN"));
		
		connect(component(Check_InOut), port("OUT"), component(InOut_decider), port("IN"));
		connect(component(InOut_decider), port("OUT[0]"), component(Broadcast_InBusy), port("IN"));
		connect(component(InOut_decider), port("OUT[1]"), component(Output_InOut), port("IN"));
		
		initialize("user"+i+"InOut", component(Mosquitto_InOut), port("CLIENTID"));
        initialize("u"+i+"/decision", component(Mosquitto_InOut), port("TOPIC"));
        
        connect(component(Output_InOut), port("OUT"), component(Mosquitto_InOut), port("CONTENT"));
		
		
		connect(component(Broadcast_InBusy), port("OUT[0]"), component(Read_Input_Keyboard_InBusy), port("IN"));
		connect(component(Broadcast_InBusy), port("OUT[1]"), component(Read_Input_Mouse_InBusy), port("IN"));
		
		connect(component(Read_Input_Keyboard_InBusy), port("OUT"), component(Receiver_InBusy), port("IN[0]"));
		connect(component(Read_Input_Mouse_InBusy), port("OUT"), component(Receiver_InBusy), port("IN[1]"));
		
		connect(component(Receiver_InBusy), port("OUT"), component(Check_InBusy), port("IN"));
		
		connect(component(Check_InBusy), port("OUT"), component(InBusy_decider), port("IN"));
		connect(component(InBusy_decider), port("OUT[0]"), component(Output_InBusy), port("IN"));
		
		initialize("user"+i+"InBusy", component(Mosquitto_InBusy), port("CLIENTID"));
        initialize("u"+i+"/decision", component(Mosquitto_InBusy), port("TOPIC"));
        
        connect(component(Output_InBusy), port("OUT"), component(Mosquitto_InBusy), port("CONTENT"));
        
		connect(component(InBusy_decider), port("OUT[1]"), component(Output_InBusyDup), port("IN"));
		
		initialize("user"+i+"InBusy1", component(Mosquitto_InBusyDup), port("CLIENTID"));
        initialize("u"+i+"/decision", component(Mosquitto_InBusyDup), port("TOPIC"));
        
        connect(component(Output_InBusyDup), port("OUT"), component(Mosquitto_InBusyDup), port("CONTENT"));
        
		
		
		}
		
		
	}
	
	public static void main(final String[] argv) throws Exception {
		while(true)
		{
	    new Network_IN_OUT().go();
	    System.out.print("***************************************************************\n");
	    Thread.sleep(8000);
		}
	  }

}
