import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class Component_Read_Input  extends Component {
	
	private InputPort inport;
	private OutputPort outport;
	
	
	String string = null;
	
	
		
	protected void execute() throws ClassNotFoundException
	  {
		Packet p;
		Random rand = new Random();
		String read = null;
		String all_values = null;
		
		Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    	    	      	      	      	      										
		while ((p = inport.receive()) != null) {
															
			int randomInt = rand.nextInt(100);
						
			String decision = (String) p.getContent();
			
			if(randomInt > 49)
			{
				read = "true";
				Packet receiver = create(read);
				outport.send(receiver);
			}
			else
			{
				read = "false";
				Packet receiver = create(read);
				outport.send(receiver);
			}
			
			/*if(decision.equals("FindM"))
			{
				String table_name = "Motion";
				try
			    {
			      // create a database connection
			      connection = DriverManager.getConnection("jdbc:sqlite:Motion_Sensor.db");
			      Statement statement = connection.createStatement();
			      statement.setQueryTimeout(30);  // set timeout to 30 sec.
			      
				
			      ResultSet rs = statement.executeQuery("select Motion_value from '"+table_name+"' WHERE ID = (SELECT MAX(ID)  FROM '"+table_name+"' )");
			      
			      while(rs.next())              
			      {
			        // read the result set
			        
			        read = rs.getString("Motion_value");
			        read = read.toLowerCase();
			       
			      }
			      
			      Packet receiver = create(read);
				  outport.send(receiver);
			      
			    }
				catch(SQLException e)
			    {
			      // if the error message is "out of memory", 
			      // it probably means no database file is found
			      System.err.println(e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
			    }
			}
			else if(decision.equals("FindK"))
			{
				
				String table_name = "Keyboard_Mouse_Prediction";
				try
			    {
			      // create a database connection
			      connection = DriverManager.getConnection("jdbc:sqlite:KMP_Sensor.db");
			      Statement statement = connection.createStatement();
			      statement.setQueryTimeout(30);  // set timeout to 30 sec.
			      
				
			      ResultSet rs = statement.executeQuery("select KM_All_values from '"+table_name+"' WHERE ID = (SELECT MAX(ID)  FROM '"+table_name+"' )");
			      
			      while(rs.next())              
			      {
			        // read the result set
			        
			        all_values = rs.getString("KM_All_values");			        
			       
			      }
			      String[] all_values_split = all_values.split(";");
			      read = all_values_split[0];
			      
			      Packet receiver = create(read);
				  outport.send(receiver);
			      
			    }
				catch(SQLException e)
			    {
			      // if the error message is "out of memory", 
			      // it probably means no database file is found
			      System.err.println(e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
			    }
			}
			
			else if(decision.equals("FindMU"))
			{

				String table_name = "Keyboard_Mouse_Prediction";
				try
			    {
			      // create a database connection
			      connection = DriverManager.getConnection("jdbc:sqlite:KMP_Sensor.db");
			      Statement statement = connection.createStatement();
			      statement.setQueryTimeout(30);  // set timeout to 30 sec.
			      
				
			      ResultSet rs = statement.executeQuery("select KM_All_values from '"+table_name+"' WHERE ID = (SELECT MAX(ID)  FROM '"+table_name+"' )");
			      
			      while(rs.next())              
			      {
			        // read the result set
			        
			        all_values = rs.getString("KM_All_values");			        
			       
			      }
			      String[] all_values_split = all_values.split(";");
			      read = all_values_split[1];
			      
			      Packet receiver = create(read);
				  outport.send(receiver);
			      
			    }
				catch(SQLException e)
			    {
			      // if the error message is "out of memory", 
			      // it probably means no database file is found
			      System.err.println(e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
			    }
			}
			
			else if(decision.equals("BusyK"))
			{
				String table_name = "Keyboard_Mouse_Prediction";
				try
			    {
			      // create a database connection
			      connection = DriverManager.getConnection("jdbc:sqlite:KMP_Sensor.db");
			      Statement statement = connection.createStatement();
			      statement.setQueryTimeout(30);  // set timeout to 30 sec.
			      
				
			      ResultSet rs = statement.executeQuery("select KM_All_values from '"+table_name+"' WHERE ID = (SELECT MAX(ID)  FROM '"+table_name+"' )");
			      
			      while(rs.next())              
			      {
			        // read the result set
			        
			        all_values = rs.getString("KM_All_values");			        
			       
			      }
			      String[] all_values_split = all_values.split(";");
			      read = all_values_split[2];
			      
			      Packet receiver = create(read);
				  outport.send(receiver);
			      
			    }
				catch(SQLException e)
			    {
			      // if the error message is "out of memory", 
			      // it probably means no database file is found
			      System.err.println(e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
			    }
			}
			
			else 
			{
				String table_name = "Keyboard_Mouse_Prediction";
				try
			    {
			      // create a database connection
			      connection = DriverManager.getConnection("jdbc:sqlite:KMP_Sensor.db");
			      Statement statement = connection.createStatement();
			      statement.setQueryTimeout(30);  // set timeout to 30 sec.
			      
				
			      ResultSet rs = statement.executeQuery("select KM_All_values from '"+table_name+"' WHERE ID = (SELECT MAX(ID)  FROM '"+table_name+"' )");
			      
			      while(rs.next())              
			      {
			        // read the result set
			        
			        all_values = rs.getString("KM_All_values");			        
			       
			      }
			      String[] all_values_split = all_values.split(";");
			      read = all_values_split[3];
			      
			      Packet receiver = create(read);
				  outport.send(receiver);
			      
			    }
				catch(SQLException e)
			    {
			      // if the error message is "out of memory", 
			      // it probably means no database file is found
			      System.err.println(e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
			    }
			}*/
		
			drop(p);
		}
		
	  }
	
	
	

	
	@Override
	  protected void openPorts() {

		 outport = openOutput("OUT");

		 inport = openInput("IN");

	  }

}
