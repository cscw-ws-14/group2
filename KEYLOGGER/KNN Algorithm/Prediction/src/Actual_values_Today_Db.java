import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Actual_values_Today_Db {
	
	/* Get Today's date */
	
	
		
	
	public int N,sum_actual,once_in;
	public List<Long> last_n_values = new ArrayList<Long>();
	public long current_time; 
	
	Today_Date t_d = new Today_Date();
	String current_date = t_d.cal();
	String table_name = current_date+"_Keyboard_Counts";
	
	public int Actual_values() throws ClassNotFoundException, InterruptedException {
		
		long temp,sum=0;
		int count_no;
		
		/* Get current time */
		
		
		System.out.println("Current Time"+current_time);
		
		/* Get Last N Actual Values */
		
		
		for (int i=1;i<=N;i++){
			temp = current_time - once_in;
			current_time = temp;
			last_n_values.add(temp);
			System.out.println(temp);
			
		}
		
		
		Class.forName("org.sqlite.JDBC");
	    Connection connection = null;
	    
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:predict.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      for (int i=0;i<N;i++){
	      ResultSet rs = statement.executeQuery("select Count from '"+table_name+"' WHERE Start_Time='"+last_n_values.get(i)+"'");
	      
	      while(rs.next())              
	      {
	        // read the result set
	        
	        count_no = rs.getInt("Count");
	        sum_actual = sum_actual + count_no;       
	        System.out.println("DB values : "+count_no);	        
	        
	      }
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
	    last_n_values.clear();
	    return sum_actual;
	}

}
