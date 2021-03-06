import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


public class Main_predict {
	
	static KeyboardListener Kl = new KeyboardListener();
	
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
		
	try {
			
            GlobalScreen.registerNativeHook();                             
    }
	
    catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
    }
	
    //Construct the example object and initialze native hook.         
    GlobalScreen.getInstance().addNativeKeyListener(Kl);
		
		while(true)
		{
			Thread.sleep(10000);
		// TODO Auto-generated method stub
		Clock c = new Clock();
		long current_time= c.cl();
		int N = 5,sum_actual=0,once_in=1;
		long NN=1;
		
		/* Get Actual Values of Keyboard, Mouse */
		
		Actual_values_Today_Db a = new Actual_values_Today_Db();
		a.current_time = current_time;
		a.N = N;
		a.keyboard_sum_actual = sum_actual;
		a.mouse_sum_actual = sum_actual;
		a.once_in = once_in;
		int actual_values[] = a.Actual_values();
		
		
		/* Get Predict Values of Keyboard, Mouse  */
		
		Predict_values_Previous_DB p = new Predict_values_Previous_DB();
		p.current_time = current_time;
		p.N = N;
		p.keyboard_sum_actual = sum_actual;
		p.mouse_sum_actual = sum_actual;
		p.once_in = once_in;
		int predict_values[] = p.predict();
		
		
		
		String Keyboard_busy, Mouse_busy, Keyboard_in, Mouse_in;
		
		/* Compare Values and say "FREE" or "BUSY"*/
		
		if(actual_values[0] > predict_values[0])
		{
			Keyboard_busy = "true";
		}
		else
		{
			Keyboard_busy = "false";
		}
		
		if(actual_values[1] > predict_values[1])
		{
			Mouse_busy = "true";
		}
		else
		{
			Mouse_busy = "false";
		}
		
		/* FIND IF MOVEMENT IS THERE IN KEYBOARD OR MOUSE */
		
		Find_If_Movement f = new Find_If_Movement();
		f.current_time = current_time;
		f.NN = NN;
		
		int find_values[] = f.Find_movement();
		
		
		/* Compare Keyboard values IN/OUT */
		
		if(find_values[0] > 0)
		{
			Keyboard_in = "true";
		}
		else
		{
			Keyboard_in = "false";
		}
		
		/* Compare Mouse values IN/OUT */
		
		if(find_values[1] > 0)
		{
			Mouse_in = "true";
		}
		else
		{
			Mouse_in = "false";
		}
		
		// Identify the User 
		
		UserIdentify Uid = new UserIdentify();
		
		
		Uid.keyPressArray = Kl.keyPressArray;
		
		Uid.keyReleaseArray = Kl.keyReleaseArray;
		
		
		
		String UserResult = Uid.UserIDentification();
		
		System.out.println(UserResult+";"+Keyboard_in+";"+Mouse_in+";"+Keyboard_busy+";"+Mouse_busy);
		
		Kl.keyPressArray.clear();
		Kl.keyReleaseArray.clear();
		
	}         
				
		
	}

}
