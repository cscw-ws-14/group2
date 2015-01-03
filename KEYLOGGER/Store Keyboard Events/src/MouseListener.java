import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class MouseListener implements NativeMouseInputListener {
	int mouse_count;
        public void nativeMouseClicked(NativeMouseEvent e) {
                /*System.out.println("Mosue Clicked: " + e.getClickCount());*/
        	
        }

        public void nativeMousePressed(NativeMouseEvent e) {
                /*System.out.println("Mosue Pressed: " + e.getButton());*/
        	mouse_count = mouse_count + 1;
        }

        public void nativeMouseReleased(NativeMouseEvent e) {
               /*System.out.println("Mosue Released: " + e.getButton());*/
        }

        public void nativeMouseMoved(NativeMouseEvent e) {
               /* System.out.println("Mosue Moved: " + e.getX() + ", " + e.getY());*/
        }

        public void nativeMouseDragged(NativeMouseEvent e) {
                /*System.out.println("Mosue Dragged: " + e.getX() + ", " + e.getY());*/
        }
}