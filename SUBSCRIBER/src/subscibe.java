import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class subscibe implements MqttCallback {

MqttClient client;
String motion_value, kmp_value;
int m_count = 0, kmp_count = 0;

public subscibe() {
}

public static void main(String[] args) throws ClassNotFoundException {
	MqttMessage message = new MqttMessage();
    new subscibe().doDemo();
    
    
}

public void doDemo() {
    try {
        client = new MqttClient("tcp://localhost:1883", "22");
        client.connect();
        client.setCallback(this);
        client.subscribe("/le/Motion/Pir");
        client.subscribe("/le/KM_Sensor/KMP");
       
       
        
        
        
       /* message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("foo", message);*/
        
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

@Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub

}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {
	
	
	Class.forName("org.sqlite.JDBC");

    Connection connection = null; 
    
   
	
	byte[] payload = message.getPayload();
	int limit = Math.min(payload.length, 25);
	String string = null;
	try 
	{
		string = new String(payload, 0, limit, "UTF-8");
		System.out.println(string);
		System.out.println(topic);
		
	} 
	catch (Exception e) {
		string = "?";
	}
	
	if(topic.equals("/le/Motion/Pir"))
	{
	
	try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:Motion_Sensor.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      
      String table_name = "Motion";
      
      statement.executeUpdate("create table if not exists '"+table_name+"' (ID integer, Motion_value string)");
      
      PreparedStatement pstatement = connection.prepareStatement("INSERT INTO '"+table_name+"' (ID,Motion_value)VALUES(?,?)");
      
      pstatement.setLong(1,System.currentTimeMillis());
      pstatement.setString(2,string); 
      pstatement.executeUpdate();
      
      ResultSet rs = statement.executeQuery("select * from '"+table_name+"'");
      
      while(rs.next())              
      {
        // read the result set
        
    	motion_value = rs.getString("Motion_value");
        m_count += 1;
      }
      
      if(m_count > 10)
      {
    	  PreparedStatement pstatement1 = connection.prepareStatement("DELETE FROM '"+table_name+"' WHERE ID IN (SELECT ID FROM '"+table_name+"' limit 2)");
    	  pstatement1.executeUpdate();
    	  
      }
      m_count = 0;
      
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
	
	if(topic.equals("/le/KM_Sensor/KMP"))
	{
		try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:KMP_Sensor.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      String table_name = "Keyboard_Mouse_Prediction";
	      
	      statement.executeUpdate("create table if not exists '"+table_name+"' (ID integer, KM_All_values string)");
	      
	      PreparedStatement pstatement = connection.prepareStatement("INSERT INTO '"+table_name+"' (ID,KM_All_values)VALUES(?,?)");
	      
	      pstatement.setLong(1,System.currentTimeMillis());
	      pstatement.setString(2,string); 
	      pstatement.executeUpdate();
	      
	      ResultSet rs = statement.executeQuery("select * from '"+table_name+"'");
	      
	      while(rs.next())              
	      {
	        // read the result set
	        
	    	kmp_value = rs.getString("KM_All_values");
	        kmp_count += 1;
	      }
	      
	      if(kmp_count > 10)
	      {
	    	  PreparedStatement pstatement1 = connection.prepareStatement("DELETE FROM '"+table_name+"' WHERE ID IN (SELECT ID FROM '"+table_name+"' limit 2)");
	    	  pstatement1.executeUpdate();
	    	  
	      }
	      kmp_count = 0;
	      
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

@Override
public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}

}