import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener {
	
	
	List<String> keyPressArray = new CopyOnWriteArrayList<String>();
	List<String> keyReleaseArray = new CopyOnWriteArrayList<String>();
	
        public void nativeKeyPressed(NativeKeyEvent e) {
              //  System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        	
        	long millis = System.currentTimeMillis();            
            String kP = NativeKeyEvent.getKeyText(e.getKeyCode());                       
            String keyPress = kP+";"+millis; 
            keyPressArray.add(keyPress);
        	
        	
        }

        public void nativeKeyReleased(NativeKeyEvent e) {
                /*System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));*/
        	
        	long millis1 = System.currentTimeMillis();            
            String kR = NativeKeyEvent.getKeyText(e.getKeyCode());                                
            String keyRelease = kR+";"+millis1;
            keyReleaseArray.add(keyRelease);
        	
        	
        }

        public void nativeKeyTyped(NativeKeyEvent e) {
                /*System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));*/
        }
}
   