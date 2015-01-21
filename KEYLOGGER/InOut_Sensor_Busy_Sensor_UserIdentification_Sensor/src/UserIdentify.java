import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;


public class UserIdentify {
	
	List<String> keyPressArray = new CopyOnWriteArrayList<String>();
	List<String> keyReleaseArray = new CopyOnWriteArrayList<String>();

	List<Long> RR_WordTime = new<Long>ArrayList();
	List<Long> PP_WordTime = new<Long>ArrayList();	
	List<Long> WordTime = new<Long>ArrayList();	
	
	List<Long> DB_rrWordTime = new ArrayList<Long>();
	List<Long> DB_ppWordTime = new ArrayList<Long>();
	List<Long> DB_WordTime = new ArrayList<Long>();
	
	List<String> Words = new<String>ArrayList();
	List<String> DB_Words = new<String>ArrayList();
	
	public String UserIDentification() throws ClassNotFoundException
	{
		
		// Initialization 
		
		List<Long> releaseSortTime = new<Long> ArrayList();
    	List<Long> EachWordTime = new ArrayList<Long>();
    	List<Long> ppEachWordTime = new ArrayList<Long>();
    	List<Long> rrEachWordTime = new ArrayList<Long>();
    	       	       	        	
    	List<Long> CurrentPressTime = new<Long> ArrayList();
    	List<Long> CurrentReleaseTime = new<Long>ArrayList();
    	
    	List<String> CurrentText = new<String>ArrayList();
    	List<String> CurrentTextBuffer = new<String>ArrayList();
    	        	
    	List<Long> DwellTime = new<Long>ArrayList();        	        	       	
    	List<Long> FlightTimePP = new<Long>ArrayList();
    	List<Long> FlightTimeRR = new<Long>ArrayList();
    	List<Long> FlightTimeRP = new<Long>ArrayList();
    	        	        	
    	List<String> COPYkeyPressArray = new ArrayList<String>(); 
        List<String> COPYkeyReleaseArray = new ArrayList<String>(); 
        
        List<Integer> NoOfWords = new<Integer>ArrayList();
       
        List<Boolean> DwellFinal = new<Boolean>ArrayList();
        List<Boolean> PPFlightFinal = new<Boolean>ArrayList();
        List<Boolean> RRFlightFinal = new<Boolean>ArrayList();
        List<Boolean> Final = new<Boolean>ArrayList();
        
        String UserResult = "null";
        
        if(keyPressArray.size() > 0)
    	{
		    
        	System.out.println("PRESS   "+keyPressArray+keyPressArray.size());
        	System.out.println("RELEASE   "+keyReleaseArray+keyReleaseArray.size());
		    
		    StringBuffer Ptexts = new StringBuffer(); 
		    StringBuffer Rtexts = new StringBuffer(); 
		    
		    // Remove Special chararcters for Button Press
		    
		    for(String element : keyPressArray)
		    {
		    	
		    	if((element.split(";")[0]).matches("[A-Z]") || (element.startsWith("Space")))
		    	{
		    		COPYkeyPressArray.add(element);
		    	}
		    	
		    }
		    
		    // Remove Special chararcters for Button Release
		    for(String element : keyReleaseArray)
		    {
		    	if((element.split(";")[0]).matches("[A-Z]") || (element.startsWith("Space")))
		    	{
		    		COPYkeyReleaseArray.add(element);
		    	}
		    }
		  
		    System.out.println("COPY PRESS   "+COPYkeyPressArray+COPYkeyPressArray.size());
		    System.out.println("COPY RELEASE   "+COPYkeyReleaseArray+COPYkeyReleaseArray.size());
		    
		    // Get the Text as StringBuffer separated by Delimiters
		    
		    for(String CPelement : COPYkeyPressArray)
		    {
		    	
		    	String[] text_time = CPelement.split(";");
		    	String text = text_time[0];
		    	Ptexts.append(text);
		    	Ptexts.append("|||");
		    }
		    
		   
		    
		    // Convert to String
		    
		    String text_String = Ptexts.toString();
		   
		    
		    //System.out.println("PRESS   "+ text_String);
		    
		    
		    String[] text_split = text_String.split(Pattern.quote("|||"));
		    
		    
		   // System.out.println("PRESS   "+ text_split.length);
		    
		    // Make sure if fist element of Press and Release list are same
		    
		    long pr = Long.parseLong((((COPYkeyPressArray.get(0)).split(";"))[1]));
		    long re = Long.parseLong((((COPYkeyReleaseArray.get(0)).split(";"))[1]));
		    
		    String Spress = ((COPYkeyPressArray.get(0)).split(";"))[0];
		    String Srelease = ((COPYkeyReleaseArray.get(0)).split(";"))[0];
		    
		    if((!Spress.equals(Srelease)) || (pr > re))
		    {
		    	COPYkeyPressArray.remove(0);
		    }
		    
		    // Make sure Press and Release Same size
		    int difference = 0;
		    if((COPYkeyPressArray.size()) > (COPYkeyReleaseArray.size()))
		    {
		    	difference = COPYkeyPressArray.size() - COPYkeyReleaseArray.size();
		    }
		    for(int i=0;i<difference;i++)
		    {
		    	int size = COPYkeyPressArray.size();
		    	COPYkeyPressArray.remove(size - 1);
		    }
		    
		    System.out.println("COPY PRESS   "+COPYkeyPressArray+COPYkeyPressArray.size());
		    System.out.println("COPY RELEASE   "+COPYkeyReleaseArray+COPYkeyReleaseArray.size());
		    
		    // Get Current Press and Current Release of each character pressed
		  
			 for(String PElement : COPYkeyPressArray)
			 {
				   
		   	   	   
		   	//   System.out.println("PRESS LENGTH : "+ COPYkeyPressArray.size());
		   //	System.out.println("Release LENGTH : "+ COPYkeyReleaseArray.size());
		   	    String[] Ptext_time = PElement.split(";");
		  	    String Ptext = Ptext_time[0];
		  	    String Ptime = Ptext_time[1];
		  	    long PtimeL = Long.parseLong(Ptime);  
		   	    System.out.println("Press : "+PElement);
		   	 
		   	 
		   	   	
		   	    for(String RElement : COPYkeyReleaseArray)
		   	    	{
		   	    	
		   		
		         	   	if((RElement.split(";")[0]).equals(Ptext))
		    	     		
		    	     	{
		    	   			System.out.println("Release : "+RElement);
		    	         	String[] Rtext_time = RElement.split(";");
		    	         	String Rtext = Rtext_time[0];
		    	         	String Rtime = Rtext_time[1];
		    	         	long toSortTimeR = Long.parseLong(Rtime);
		    	   			releaseSortTime.add(toSortTimeR);
		    	     	}
		   	    	}
		   	    
		   	if(releaseSortTime.size() != 0)
		   	{
		   		
		   		Collections.sort(releaseSortTime);
		     	//System.out.println("Removing : "+Ptext+";"+releaseSortTime.get(0));
		     	CurrentReleaseTime.add(releaseSortTime.get(0));
		     	
		     	COPYkeyReleaseArray.remove(Ptext+";"+releaseSortTime.get(0));
		   	}
		 	
		 	
		   	CurrentPressTime.add(PtimeL);
		   	CurrentText.add(Ptext);
		   	
		   	
		   	releaseSortTime.clear(); 
		   	 	   
			}
		   
		  // Add empty space if the text has "Space" in it
		  
		  for(String TBuffer : CurrentText)
		  {
			  if(TBuffer.equals("Space"))
			  {
				  CurrentTextBuffer.add(" ");
			  }
			  else
			  {
			  CurrentTextBuffer.add(TBuffer);
			  }
		  }
		   
		   
		   // Dwell Time Calculation 
		   
		   for(int i = 0; i<CurrentPressTime.size();i++)
		   {
		   	   long Dwell = CurrentReleaseTime.get(i) - CurrentPressTime.get(i);
		   	   DwellTime.add(Dwell);
		   }
		   
		   // Flight Time Calculation - Press to Press
		   
		   for(int i = 0; i<(CurrentPressTime.size()-1);i++)
		   {
			   long PP = CurrentPressTime.get(i+1) - CurrentPressTime.get(i);
			   FlightTimePP.add(PP);
		   }
		   
		   // Flight Time Calculation - Release to Release
		   
		   for(int i = 0; i<(CurrentReleaseTime.size()-1);i++)
		   {
			   long RR = CurrentReleaseTime.get(i+1) - CurrentReleaseTime.get(i);
			   FlightTimeRR.add(RR);
		   }
		   
		   
		   
		  long wdTimeTemp=0; 
		  long wfpTimeTemp=0;
		  long wfrTimeTemp=0;
		  String w= "";
		  
		  // Words Extraction 
		  
		  CurrentTextBuffer.add(" ");
		  FlightTimePP.add((long) 100);
		  FlightTimeRR.add((long) 100);
		  for(int i=0;i<CurrentTextBuffer.size();i++)
		  {
			  if(CurrentTextBuffer.get(i).equals(" "))
			  {
				  if((wdTimeTemp != 0))
				  {
				  WordTime.add(wdTimeTemp);
				  PP_WordTime.add(wfpTimeTemp);
				  RR_WordTime.add(wfrTimeTemp);
				  wdTimeTemp = 0;
				  wfpTimeTemp = 0;
				  wfrTimeTemp = 0;
				  Words.add(w);
				  w = "";
				  }
			  }
			  else
			  {
				  wdTimeTemp = wdTimeTemp + DwellTime.get(i);
				  wfpTimeTemp = wfpTimeTemp + FlightTimePP.get(i);
				  wfrTimeTemp = wfrTimeTemp + FlightTimeRR.get(i);
				  w = w + CurrentTextBuffer.get(i);
			  }
		  }
		  
		  System.out.println("\n WORDS :"+Words+Words.size()); 
		  System.out.println("\n WORDS TIME :"+WordTime+WordTime.size()); 
		  
		Class.forName("org.sqlite.JDBC");
		
		  Connection connection = null;
		  
		  String table_name = "Words_Table"; 
		
		
		// Get the Words From Database to check the Timings of each word
		
		try
		{
		// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:UserIdentification.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		
		statement.executeUpdate("create table if not exists '"+table_name+"' (Word, DWELL_Word_Time, PP_FLIGHT_Word_Time, RR_FLIGHT_Word_Time)");
		
		for(String text : Words)
		{
			System.out.println("WORDS : "+text);  
			Statement statement1 = connection.createStatement();  
			ResultSet rs1 = statement1.executeQuery("select Word from '"+table_name+"' WHERE Word='"+text+"'");
			
				if (rs1.next())
				{
					System.out.println("WORD FROM DB : "+rs1.getString("Word"));
					DB_Words.add(rs1.getString("Word"));
					NoOfWords.add(1);
				}
				else
				{
				//System.out.println("WORD FROM DB : "+rs1.getString("Word"));
			  	// read the result set to a list of Number of words
				//if((rs1.getString("Word")).equals(text))
				//{
				//NoOfWords.add(1);
				//}
				}
			  
		}
		
		  System.out.println("NO. OF WORDS IN DB : "+NoOfWords);
		  
		  int sum = 0;
		  String time_word="";
		  String pptime_word="";
		  String rrtime_word="";
		  
		  
		  // Sum the number of words
		  
		  for(int i : NoOfWords)
		  {
		  	sum += i;
		  }
		  
		  // Number of words from database should be greater than half the current Number of words
		  
		  if((Words.size()/2) < sum)
		  {
		  	for(String text : DB_Words)
		      {
		  		Statement statement2 = connection.createStatement();
		    	ResultSet rs2 = statement2.executeQuery("select DWELL_Word_Time, PP_FLIGHT_Word_Time, RR_FLIGHT_Word_Time from '"+table_name+"' WHERE Word='"+text+"'");
		    	
		    	if(rs2.next())              
		    	  {
					  	// read the result set to a list of Number of words
		    		
		    		time_word = rs2.getString("DWELL_Word_Time");
		    		pptime_word = rs2.getString("PP_FLIGHT_Word_Time");
		    		rrtime_word = rs2.getString("RR_FLIGHT_Word_Time");
		    		
		    	  }
		    	String [] time_array = time_word.split(";");
		    	String [] pptime_array = pptime_word.split(";");
		    	String [] rrtime_array = rrtime_word.split(";");
		    	
		    	for(String t : time_array)
		    	{
		    		long tt = Long.parseLong(t);
		    		EachWordTime.add(tt);
		    	}
		    	for(String t : pptime_array)
		    	{
		    		long tt = Long.parseLong(t);
		    		ppEachWordTime.add(tt);
		    	}
		    	for(String t : rrtime_array)
		    	{
		    		long tt = Long.parseLong(t);
		    		rrEachWordTime.add(tt);
		    	}
		    	Collections.sort(EachWordTime);
		    	Collections.sort(ppEachWordTime);
		    	Collections.sort(rrEachWordTime);
		    	
		    	// Get Median Value
		    	DB_WordTime.add(EachWordTime.get(EachWordTime.size()/2));
		    	DB_ppWordTime.add(ppEachWordTime.get(ppEachWordTime.size()/2));
		    	DB_rrWordTime.add(rrEachWordTime.get(rrEachWordTime.size()/2));
		    	EachWordTime.clear();	
		    	ppEachWordTime.clear();
		    	rrEachWordTime.clear();
		    		    	    		    	    		    	    	
		      }
		  	
		  	for(int i=0;i<DB_Words.size();i++)
		  	{
		  		for(int j=0;j<Words.size();j++)
		  		{
		  			if(Words.get(j).equals(DB_Words.get(i)))
		  			{
		  				long CurrentWordTime = WordTime.get(j);
		  				long CurrentppWordTime = PP_WordTime.get(j);
		  				long CurrentrrWordTime = RR_WordTime.get(j);
		  				
		  				long DBWordTime = DB_WordTime.get(i);
		  				long DBppWordTime = DB_ppWordTime.get(i);
		  				long DBrrWordTime = DB_rrWordTime.get(i);
		  				
		  				System.out.println(Words.get(j)+" : "+CurrentWordTime);
		  				System.out.println(DB_Words.get(i)+" : "+DBWordTime);
		  				System.out.println(DB_Words.get(i)+" : "+DBppWordTime);
		  				System.out.println(DB_Words.get(i)+" : "+DBrrWordTime);
		  				
		  				if(((DBWordTime-100) < CurrentWordTime) && (CurrentWordTime < (DBWordTime+100)))
		  				{
		  					DwellFinal.add(true);
		  				}
		  				else
		  				{
		  					DwellFinal.add(false);
		  				}
		  				
		  				if(((DBppWordTime-100) < CurrentppWordTime) && (CurrentppWordTime < (DBppWordTime+100)))
		  				{
		  					PPFlightFinal.add(true);
		  				}
		  				else
		  				{
		  					PPFlightFinal.add(false);
		  				}
		  				
		  				if(((DBrrWordTime-100) < CurrentrrWordTime) && (CurrentrrWordTime < (DBrrWordTime+100)))
		  				{
		  					RRFlightFinal.add(true);
		  				}
		  				else
		  				{
		  					RRFlightFinal.add(false);
		  				}
		  				
		  			}
		  		}
		  	}
		  	
		  	int dcount = 0,ppcount = 0,rrcount=0;
			    for (Boolean value : DwellFinal) {
			        if (value.booleanValue()) {
			            dcount ++;
			        }
			    }
			    
			  for (Boolean value : PPFlightFinal) {
			        if (value.booleanValue()) {
			            ppcount ++;
			        }
			    }
			  
				for (Boolean value : RRFlightFinal) {
			        if (value.booleanValue()) {
			            rrcount ++;
			        }
			    }
		  	
		  	System.out.println("\n CURRENT WORDS "+Words+Words.size());
		  	System.out.println("\n DWELL TIME "+WordTime+WordTime.size());
		  	System.out.println("\n FLIGHT PP TIME "+PP_WordTime+PP_WordTime.size());
		  	System.out.println("\n FLIGHT RR TIME "+RR_WordTime+RR_WordTime.size());
		  	
		  	System.out.println("\n DB WORDS "+DB_Words+DB_Words.size());
			System.out.println("\n DB DWELL TIME "+DB_WordTime+DB_WordTime.size()); 
			System.out.println("\n DB FLIGHT Press to Press TIME "+DB_ppWordTime+DB_ppWordTime.size()); 
			System.out.println("\n DB FLIGHT Release to Release TIME "+DB_rrWordTime+DB_rrWordTime.size());
			
			// Dwell and Flight Result
			
			if(dcount > (DwellFinal.size()/2))
			{
				Final.add(true);
			}
			else
			{
				Final.add(false);
			}
			
			if(ppcount > (PPFlightFinal.size()/2))
			{
				Final.add(true);
			}
			else
			{
				Final.add(false);
			}
			
			if(rrcount > (RRFlightFinal.size()/2))
			{
				Final.add(true);
			}
			else
			{
				Final.add(false);
			}
			
			int finalCount=0;
			    for (Boolean value : Final) {
			        if (value.booleanValue()) {
			        	finalCount ++;
			        }
			    }
			    
			  if(finalCount > (Final.size()/2))
			{
				UserResult = "true";
			}
			else
			{
				UserResult = "false";
			}
			  
			System.out.println("USER IDENTIFIED : "+UserResult);	
			
		  }
		  else
		  {
		  	System.out.println("NOT ENOUGH WORDS in DATABASE TO COMPARE");
		  	UserResult = "null"; // Not Enough Words
		  		  	    	
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
		
		    
		  /*
		   for(int i=0; i<(text_split.length - 1);i++)
		   {
		   	   String combine_2_text = text_split[i]+ text_split[i+1];
		   	   
		   	   
		   	   long PPTime = CurrentPressTime.get(i+1) - CurrentPressTime.get(i);
		   	   if(PPTime < 0)
		   	   {
		   		   PPTime = 0;
		   	   }
		   	   BiFlightTimePP.add(PPTime);
		   	   
		   	   
		   	   long RRTime = CurrentReleaseTime.get(i+1) - CurrentReleaseTime.get(i);
		   	   if(RRTime < 0)
			   {
				   RRTime = 0;
			   }
		   	   BiFlightTimeRR.add(RRTime);
		   	   
		   	   long RPTime = CurrentPressTime.get(i+1) - CurrentReleaseTime.get(i);
		       if(RPTime < 0)
		 	   {
		 		   RPTime = 0;
		 	   }
		   	   BiFlightTimeRP.add(RPTime);
		   	   
		   	   biGram.add(combine_2_text);
		   }
		   
		  
		   for(int i=0; i<(text_split.length - 2);i++)
		   {
		   	   String combine_3_text = text_split[i]+ text_split[i+1] + text_split[i+2];
		   	   
		   	   long PPTime = CurrentPressTime.get(i+2) - CurrentPressTime.get(i);
		   	   TriFlightTimePP.add(PPTime);
		   	   
		       	long RRTime = CurrentReleaseTime.get(i+2) - CurrentReleaseTime.get(i);
		       	TriFlightTimeRR.add(RRTime); 
		       	
		       	long RPTime = CurrentPressTime.get(i+2) - CurrentReleaseTime.get(i);
		       	TriFlightTimeRP.add(RPTime);
		       	
		   	   triGram.add(combine_3_text);
		   }*/
		   
		   
		   
		   System.out.println("\n TEXT "+text_String);
		   System.out.println("Press Time : "+CurrentPressTime+CurrentPressTime.size());
		  // System.out.println("Release Time : "+CurrentReleaseTime+CurrentReleaseTime.size());
		   
		   System.out.println("\n DWELL TIME "+DwellTime+DwellTime.size()); 
		   //System.out.println("\n FINAL TEXT :"+CurrentTextBuffer+CurrentTextBuffer.size()); 
		   
		  
		   
		  /* System.out.println("\n BI GRAM "+biGram);
		   System.out.println("\n PP FLIGHT TIME "+BiFlightTimePP);
		   System.out.println("\n RR FLIGHT TIME "+BiFlightTimeRR);
		   System.out.println("\n RP FLIGHT TIME "+BiFlightTimeRP);
		   System.out.println("\n TRI GRAM "+triGram);
		   System.out.println("\n PP FLIGHT TIME "+TriFlightTimePP);
		   System.out.println("\n RR FLIGHT TIME "+TriFlightTimeRR);
		   System.out.println("\n RP FLIGHT TIME "+TriFlightTimeRP);
		   
		   System.out.println("\n PRESS SIZE " + keyPressArray.size());*/
		   
		   
			
			// get UserResult to add the current value to the database
			
			if(UserResult.equals("true") || UserResult.equals("null"))
			{
				
				AddCurrentValues thisObj = new AddCurrentValues();
				    //call instance method using object
				    thisObj.Add(Words,WordTime,PP_WordTime,RR_WordTime,table_name);
				    	    	    		    
			}
			
			 CurrentPressTime.clear();
		     CurrentReleaseTime.clear();
		 	
		 		CurrentText.clear();
		 		CurrentTextBuffer.clear();
		 	        	
		     	DwellTime.clear();       	        	       	
		     	FlightTimePP.clear();
		     	FlightTimeRR.clear();
		     	FlightTimeRP.clear();  	           	  	           	
		 	
		     	COPYkeyPressArray.clear(); 
		     	COPYkeyReleaseArray.clear(); 
		     	
		     	NoOfWords.clear();
		     
		    DwellFinal.clear();
		    PPFlightFinal.clear();
		    RRFlightFinal.clear();
		    Final.clear();
		     
		     	
		     	keyPressArray.clear();
		  	keyReleaseArray.clear();
		  	
		  	WordTime.clear();
		  	PP_WordTime.clear();
		  	RR_WordTime.clear();
		  	
		  	Words.clear();
		  	DB_Words.clear();
		  	
		  	DB_WordTime.clear();
		  	DB_ppWordTime.clear();
		  	DB_rrWordTime.clear();
			
    	}
    	else
    	{
    		System.out.println("No words Pressed");
    	}
        return UserResult;
	}

}
