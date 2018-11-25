package Code;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

public class mousePressWorker implements Runnable {

    private volatile boolean enabled = true;
    private int mouseButton;
    private int delay;
    Random rand = new Random();

    public void stop() {
        enabled = false;
        return;
    }

    public void start() {
        enabled = true;
    }

    public mousePressWorker(int mouseButton, int delay) {
        // mouse button 0 = left
        // mouse button 1 = right
        this.mouseButton = mouseButton;
        this.delay = delay;
    }

    @Override
    public void run() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        while (enabled) {
            if (mouseButton == 0) {
                robot.mousePress(MouseEvent.BUTTON1_MASK);
                robot.mouseRelease(MouseEvent.BUTTON1_MASK);
                robot.delay(delay);
            } else if (mouseButton == 1) {
                robot.mousePress(MouseEvent.BUTTON3_MASK);
                robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                robot.delay(delay);
            }
        }
    }
}