import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.Timer;
import java.util.TimerTask;


public class Main_Project extends TimerTask {
	static KeyboardListener k = new KeyboardListener();
	static MouseListener m = new MouseListener();
	
	Database_Keylogger k_db = new Database_Keylogger();
	Clock c = new Clock();
	
	long start_time;
	 
	public void run() {	       
		try {
			start_time = c.cl();
		} catch (InterruptedException e1) {
			
			// TODO Auto-generated catch block  
			
			e1.printStackTrace();
		}
		
		
		/* Send Count, Starttime, Endtime to Database */
		
		k_db.start_time = start_time;
		
		k_db.store_keyboard_count = k.keyboard_count;  
		 
		k_db.store_mouse_count = m.mouse_count;
		
		
		try {
			k_db.KeyBoardDB();
			
		} catch (ClassNotFoundException e) {      
			// TODO Auto-generated catch block
			e.printStackTrace();        
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		/* Refresh Count every n minutes */ 
		            
		k.keyboard_count=0;
		m.mouse_count=0;
	    }

	public static void main(String[] args)  throws ClassNotFoundException, InterruptedException{
		// TODO Auto-generated method stub
						             
		try {
			
            GlobalScreen.registerNativeHook();                             
    }
	
    catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
    }
	
    //Construct the example object and initialze native hook.         
    GlobalScreen.getInstance().addNativeKeyListener(k);
    GlobalScreen.getInstance().addNativeMouseListener(m);
    Timer timer = new Timer();              
	timer.schedule(new Main_Project(), 0, 60000);                                                        
	}
     
}                                                                                 
            