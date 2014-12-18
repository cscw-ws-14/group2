import com.jpmorrsn.fbp.engine.Component;
import com.jpmorrsn.fbp.engine.ComponentDescription;
import com.jpmorrsn.fbp.engine.InPort;
import com.jpmorrsn.fbp.engine.InputPort;
import com.jpmorrsn.fbp.engine.OutPort;
import com.jpmorrsn.fbp.engine.OutPorts;
import com.jpmorrsn.fbp.engine.OutputPort;
import com.jpmorrsn.fbp.engine.Packet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Component to check if today is Working or not
 */

@ComponentDescription("Today is WORKING or HOLIDAY")
@InPort(value = "SOURCE", type = String.class)
@OutPorts({ @OutPort(value = "OUT", description = "Send status to Decider", type = String.class)})



public class Component_Main_Calendar_Check extends Component {
	
	private OutputPort outport;

	private InputPort source;
	public String work_status;
	
	
	
	@Override
	  protected void execute() throws ClassNotFoundException, InterruptedException {
		
		
	  
		Packet rp = source.receive();
	    if (rp == null) {
	      return;
	    }
	    
	    source.close();
	    
	    /* Get Calendar database name and Table name */	    
	    String sf = (String) rp.getContent();
	    
	    /* Format the received Strings */ 
	    String format[];
	    format = sf.split(":");
	    
	    /* Database and Table name */
	    String database_name = format[0];
	    String table_name = format[1];
	    
	    /* Get Current Date */
	    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    Calendar cal = Calendar.getInstance();
	    String date = dateFormat.format(cal.getTime());
	    
	    /* SQLite Database Connect */
	    Class.forName("org.sqlite.JDBC");
	    Connection connection = null;
	    
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:"+database_name);
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      ResultSet rs = statement.executeQuery( "SELECT Status FROM '"+table_name+"' WHERE Date='"+date+"'");
	      
	      while(rs.next())
	      {
	    	  work_status = rs.getString("Status");	    	  
	      }
	      
	      /* Send status to Decider */
	      
	      
	      Packet p = create(work_status);
	      outport.send(p);
	      
	      
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
	    
	    drop(rp);
	    
	   
	}
	
	 @Override
	  protected void openPorts() {

		 outport = openOutput("OUT");

	    source = openInput("SOURCE");

	  }

}
