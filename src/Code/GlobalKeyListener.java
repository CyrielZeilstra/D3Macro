package Code;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    Controller c;

    public GlobalKeyListener(Controller c) {
        this.c = c;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        c.checkKey(e.getKeyCode());
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        return;
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        return;
    }

}