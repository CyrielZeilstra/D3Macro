package Code;


import java.awt.*;
import java.util.Random;

public class keyPressWorker implements Runnable {

    private volatile boolean enabled = true;
    private int keyToPress;
    private int delay;
    Random rand = new Random();

    public void stop() {
        enabled = false;
        return;
    }

    public void start() {
        enabled = true;
    }

    public keyPressWorker(int keyToPress, int delay) {
        this.keyToPress = keyToPress;
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
            robot.delay(rand.nextInt((200 - 10) + 1) + 10);
            robot.keyPress(keyToPress);
            robot.delay(rand.nextInt((200 - 10) + 1) + 10);
            robot.keyRelease(keyToPress);
            robot.delay(delay);
        }
    }
}