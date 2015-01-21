import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class AddCurrentValues {
	
	public void Add(List<String> Words,List<Long> WordTime,List<Long> PP_WordTime,List<Long> RR_WordTime, String tablename) throws ClassNotFoundException
	{
		// load the sqlite-JDBC driver using the current class loader
		
   	 System.out.println("INside");
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    
	    String dbWord="NOTHING";
	    String dbWordTime="";
	    String dbppWordTime="";
	    String dbrrWordTime="";
	    
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:UserIdentification.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      System.out.println("WORDS : "+Words);
	      
	      
	      /* Add values */
	      for(int i=0;i<Words.size();i++)
	      {
	    	  ResultSet rs = statement.executeQuery("select Word, DWELL_Word_Time, PP_FLIGHT_Word_Time, RR_FLIGHT_Word_Time from '"+tablename+"' WHERE Word='"+Words.get(i)+"'");
	    	  
	    	  if(rs.next())              
	      	  {
				  	// read the result set
	    		  
	    		  dbWord = rs.getString("Word");
	    		  dbWordTime = rs.getString("DWELL_Word_Time");
	    		  dbppWordTime = rs.getString("PP_FLIGHT_Word_Time");
	    		  dbrrWordTime = rs.getString("RR_FLIGHT_Word_Time");
	    		  System.out.println("THERE IS A WORD CALLED "+dbWord+dbWordTime+dbppWordTime+dbrrWordTime);
	      	  }
	    	  if(dbWord.equals(Words.get(i)))
	    	  {
	    		  System.out.println(Words.get(i)+" in DB, Updating It");
	    		  
	    		  /* Append */
	    		  String WT = Long.toString(WordTime.get(i));
	    		  String ppWT = Long.toString(PP_WordTime.get(i));
	    		  String rrWT = Long.toString(RR_WordTime.get(i));
	    		  String Full_WT = dbWordTime+WT+";";
	    		  String Full_ppWT = dbppWordTime+ppWT+";";
	    		  String Full_rrWT = dbrrWordTime+rrWT+";";
	    		  statement.executeUpdate("UPDATE '"+tablename+"' SET DWELL_Word_Time = '"+Full_WT+"' WHERE Word='"+Words.get(i)+"'");
	    		  statement.executeUpdate("UPDATE '"+tablename+"' SET PP_FLIGHT_Word_Time = '"+Full_ppWT+"' WHERE Word='"+Words.get(i)+"'");
	    		  statement.executeUpdate("UPDATE '"+tablename+"' SET RR_FLIGHT_Word_Time = '"+Full_rrWT+"' WHERE Word='"+Words.get(i)+"'");
	    		  
	    	  }
	    	  else
	    	  {
	    		  
	    		  System.out.println(Words.get(i)+" not in DB, Adding It");
	    		  /* Insert */
	    		  PreparedStatement pstatement = connection.prepareStatement("INSERT INTO '"+tablename+"' (Word,DWELL_Word_Time,PP_FLIGHT_Word_Time,RR_FLIGHT_Word_Time)VALUES(?,?,?,?)");
	    	      
	    	      pstatement.setString(1,Words.get(i)); 
	    	      
	    	      /* Convert WordTime to String*/
	    	      String WT = Long.toString(WordTime.get(i));
	    	      String ppWT = Long.toString(PP_WordTime.get(i));
	    	      String rrWT = Long.toString(RR_WordTime.get(i));
	    	      pstatement.setString(2,WT+";");
	    	      pstatement.setString(3,ppWT+";");
	    	      pstatement.setString(4,rrWT+";");
	    	      pstatement.executeUpdate();
	    	  }
	      }
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

       	WordTime.clear();
       	PP_WordTime.clear();
       	RR_WordTime.clear();
       	
       	Words.clear();
	}

}
