import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database_Keylogger
{
	
	public int kcount_no,mcount_no,store_keyboard_count, store_mouse_count;
	
	public long start_time,sstart_time;
	
	Today_Date t_d = new Today_Date();
	
	String current_date = t_d.cal();
	
	String table_name = current_date+"_Counts";
	
  public void KeyBoardDB() throws ClassNotFoundException, InterruptedException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");
   
    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:KeyBoardEvents_Counts.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      /*statement.executeUpdate("drop table if exists person");*/
      
      statement.executeUpdate("create table if not exists '"+table_name+"' (Start_Time integer, KeyboardCount integer, MouseCount integer)");
     
      PreparedStatement pstatement = connection.prepareStatement("INSERT INTO '"+table_name+"' (Start_Time,KeyboardCount,MouseCount)VALUES(?,?,?)");
      
      pstatement.setLong(1,start_time);     
      pstatement.setInt(2,store_keyboard_count);
      pstatement.setInt(3,store_mouse_count);
      pstatement.executeUpdate();
      ResultSet rs = statement.executeQuery("select * from '"+table_name+"'");
    
      while(rs.next())              
      {
        // read the result set
        
        kcount_no = rs.getInt("KeyboardCount");
        mcount_no = rs.getInt("MouseCount");
        sstart_time = rs.getLong("Start_Time");        
          
        
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
  }
}