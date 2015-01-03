import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener {
	int keyboard_count;
        public void nativeKeyPressed(NativeKeyEvent e) {
                /*System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));*/
        	keyboard_count =keyboard_count + 1; 
        }

        public void nativeKeyReleased(NativeKeyEvent e) {
                /*System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));*/
        }

        public void nativeKeyTyped(NativeKeyEvent e) {
                /*System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));*/
        }
}
   