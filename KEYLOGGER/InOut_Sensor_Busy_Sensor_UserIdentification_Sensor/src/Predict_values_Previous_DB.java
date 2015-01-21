import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Predict_values_Previous_DB {
	
	public int N, keyboard_sum_actual, mouse_sum_actual, once_in, k_average_count = 20, m_average_count = 10, keyboard_sum_predict, mouse_sum_predict;
	public long current_time,temp;
	public List<Long> last_n_values = new ArrayList<Long>();
	public List<Integer> keyboard_count_list = new ArrayList<Integer>();
	public List<Integer> mouse_count_list = new ArrayList<Integer>();
	public List<Integer> keyboard_whole_count_list = new ArrayList<Integer>();
	public List<Integer> mouse_whole_count_list = new ArrayList<Integer>();
	public List<String> table_list = new ArrayList<String>();
	
	/* Get Today Date */
	Today_Date t_d = new Today_Date();
	String current_date = t_d.cal();
	String current_table_name = current_date+"_Counts";
		
	public int[] predict() throws ClassNotFoundException{
		
		int k_count_no,m_count_no,k_sum=0,m_sum=0;
		
		/* Get Last N Actual Values */
		
		
		for (int i=1;i<=N;i++){
			temp = current_time - once_in;
			current_time = temp;
			last_n_values.add(temp);
			/*System.out.println(temp);*/
			
		}
		
		// load the sqlite-JDBC driver using the current class loader
		
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:main_predict.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      ResultSet rs = statement.executeQuery("SELECT tbl_name FROM sqlite_master WHERE type = 'table';");
	      while(rs.next())
	      {
	    	  
	        // read the result set
	    	  table_list.add(rs.getString("tbl_name"));
	      }
	      /*System.out.println(table_list);*/
	      for(int j = 0;j<table_list.size();j++){
	    	  
	      
	      
	    	  /*System.out.println("table name = " + table_list.get(j));*/
	    	  
	    	  if(!current_table_name.equals(table_list.get(j)))
	    	  { 
	    		  	    		  	    		  
	    		  for (int i=0;i<N;i++)
	    		  {
	    			  ResultSet rs1 = statement.executeQuery("select KeyboardCount,MouseCount from '"+table_list.get(j)+"' WHERE Start_Time='"+last_n_values.get(i)+"'");
	    			  while(rs1.next())              
	    	      	  {
	    				  	// read the result set
	    	        
	    				  	k_count_no = rs1.getInt("KeyboardCount");
	    				  	m_count_no = rs1.getInt("MouseCount");
	    				  	keyboard_count_list.add(k_count_no);
	    				  	mouse_count_list.add(m_count_no);
	    				  	/*System.out.println("DB Keyboard values : "+k_count_no);	
	    				  	System.out.println("DB Mouse values : "+m_count_no);*/
	    	        
	    	      	  }
	    		  }
	    		  
	    		  // If the table does not have the particular time entry in database , We add an average constant
	    		  
	    		  while(keyboard_count_list.size()<N)
	    		  {
	    			  keyboard_count_list.add(k_average_count);
	    			  /*System.out.println("Average values : "+k_average_count);*/
	    		  }
	    		  
	    		  while(mouse_count_list.size()<N)
	    		  {
	    			  mouse_count_list.add(m_average_count);
	    			  /*System.out.println("Average values : "+m_average_count);*/
	    		  }
	    		  
	    		  // Add the counts of the n minutes in the table 
	    		  
	    		  for(int k = 0;k<keyboard_count_list.size();k++)
	    		  { 
	    			  k_sum += keyboard_count_list.get(k);
	    		  }	    		  
	    		  keyboard_whole_count_list.add(k_sum);
	    		  keyboard_count_list.clear();
	    		  k_sum = 0;
	    		  
	    		  for(int k = 0;k<mouse_count_list.size();k++)
	    		  { 
	    			  m_sum += mouse_count_list.get(k);
	    		  }	    		  
	    		  mouse_whole_count_list.add(m_sum);
	    		  mouse_count_list.clear();
	    		  m_sum = 0;
	    		  
	    		  
	    	  } 
	    	  
	      }
	      /*System.out.println(keyboard_whole_count_list);
	      System.out.println(mouse_whole_count_list);*/
	      
	      // Add all the counts from all Tables for n minutes
	      
	      for(int k = 0;k<keyboard_whole_count_list.size();k++)
	      {
	    	  keyboard_sum_actual+= keyboard_whole_count_list.get(k);
	      }
	      keyboard_sum_predict = keyboard_sum_actual / keyboard_whole_count_list.size();
	      
	      for(int k = 0;k<mouse_whole_count_list.size();k++)
	      {
	    	  mouse_sum_actual+= mouse_whole_count_list.get(k);
	      }
	      
	      keyboard_sum_predict = keyboard_sum_actual / keyboard_whole_count_list.size();
	      
	      mouse_sum_predict = mouse_sum_actual / mouse_whole_count_list.size();
	      	      	      
	    }
	    catch(Exception e)
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
	    return new int[] {keyboard_sum_predict, mouse_sum_predict};
	    }

}
