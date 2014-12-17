import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Predict_values_Previous_DB {
	
	public int N,sum_actual,once_in,average_count = 20,sum_predict;
	public long current_time,temp;
	public List<Long> last_n_values = new ArrayList<Long>();
	public List<Integer> count_list = new ArrayList<Integer>();
	public List<Integer> whole_count_list = new ArrayList<Integer>();
	public List<String> table_list = new ArrayList<String>();
	
	/* Get Today Date */
	Today_Date t_d = new Today_Date();
	String current_date = t_d.cal();
	String current_table_name = current_date+"_Keyboard_Counts";
		
	public int predict() throws ClassNotFoundException{
		
		int count_no,sum=0;;
		
		/* Get Last N Actual Values */
		
		
		for (int i=1;i<=N;i++){
			temp = current_time - once_in;
			current_time = temp;
			last_n_values.add(temp);
			System.out.println(temp);
			
		}
		
		// load the sqlite-JDBC driver using the current class loader
		
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:predict.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      ResultSet rs = statement.executeQuery("SELECT tbl_name FROM sqlite_master WHERE type = 'table';");
	      while(rs.next())
	      {
	    	  
	        // read the result set
	    	  table_list.add(rs.getString("tbl_name"));
	      }
	      System.out.println(table_list);
	      for(int j = 0;j<table_list.size();j++){
	    	  
	      
	      
	    	  System.out.println("table name = " + table_list.get(j));
	    	  
	    	  if(!current_table_name.equals(table_list.get(j)))
	    	  { 
	    		  	    		  	    		  
	    		  for (int i=0;i<N;i++)
	    		  {
	    			  ResultSet rs1 = statement.executeQuery("select Count from '"+table_list.get(j)+"' WHERE Start_Time='"+last_n_values.get(i)+"'");
	    			  while(rs1.next())              
	    	      	  {
	    				  	// read the result set
	    	        
	    				  	count_no = rs1.getInt("Count");
	    				  	count_list.add(count_no);      
	    				  	System.out.println("DB values : "+count_no);	        
	    	        
	    	      	  }
	    		  }
	    		  
	    		  while(count_list.size()<N)
	    		  {
	    			  count_list.add(average_count);
	    			  System.out.println("Average values : "+average_count);
	    		  }
	    		  for(int k = 0;k<count_list.size();k++)
	    		  { 
	    			  sum += count_list.get(k);
	    		  }	    		  
	    		  whole_count_list.add(sum);
	    		  count_list.clear();
	    		  sum = 0;
	    		  
	    		  
	    	  } 
	    	  
	      }
	      System.out.println(whole_count_list);
	      
	      for(int k = 0;k<whole_count_list.size();k++)
	      {
	    	  sum_actual+= whole_count_list.get(k);
	      }
	      sum_predict = sum_actual / whole_count_list.size();
	      	      	      
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
	    return sum_predict;
	    }

}
