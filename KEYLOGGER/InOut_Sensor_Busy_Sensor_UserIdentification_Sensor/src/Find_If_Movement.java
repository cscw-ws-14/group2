import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Find_If_Movement {
	
	public long current_time,NN;
	int k_count_no, m_count_no;
	
	
	
	/* Get Table name */
	
	Today_Date t_d = new Today_Date();
	String current_date = t_d.cal();
	String table_name = current_date+"_Counts";
	
	public int[] Find_movement() throws ClassNotFoundException, InterruptedException {
	
	/* Get one before min */	
	long last_one_value = current_time - NN;
	
	Class.forName("org.sqlite.JDBC");
    Connection connection = null;
    
    	try
    	{
      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:main_predict.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      ResultSet rs = statement.executeQuery("select KeyboardCount,MouseCount from '"+table_name+"' WHERE Start_Time='"+last_one_value+"'");
	      
	      while(rs.next())              
	      {
	        // read the result set
	        
	        k_count_no = rs.getInt("KeyboardCount");
	        
	        m_count_no = rs.getInt("MouseCount");
	        	        
	      }
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
    	
    	return new int[] {k_count_no, m_count_no};
    
	}
}
