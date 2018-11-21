package sample;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class keyPressBot {

    Controller c;
    keyPressWorker worker1;
    keyPressWorker worker2;
    keyPressWorker worker3;
    keyPressWorker worker4;

    public keyPressBot(Controller c) {
        this.c = c;
    }

    public void start() {
        if (c.getButton1EnabledCheck().isSelected()) {
            worker1 = new keyPressWorker(c.getButton1(), Integer.parseInt(c.getButton1DelayTxT().getText()));
            worker1.start();
            Thread t1 = new Thread(worker1);
            t1.start();
        }

        if (c.getButton2EnabledCheck().isSelected()) {
            worker2 = new keyPressWorker(c.getButton2(), Integer.parseInt(c.getButton2DelayTxT().getText()));
            worker2.start();
            Thread t2 = new Thread(worker2);
            t2.start();
        }

        if (c.getButton3EnabledCheck().isSelected()) {
            worker3 = new keyPressWorker(c.getButton3(), Integer.parseInt(c.getButton3DelayTxT().getText()));
            worker3.start();
            Thread t3 = new Thread(worker3);
            t3.start();
        }

        if (c.getButton4EnabledCheck().isSelected()) {
            worker4 = new keyPressWorker(c.getButton4(), Integer.parseInt(c.getButton4DelayTxT().getText()));
            worker4.start();
            Thread t4 = new Thread(worker4);
            t4.start();
        }
    }

    public void stop() {
        // kill runnables
        if (c.getButton1EnabledCheck().isSelected()) {
            worker1.stop();
        }
        if (c.getButton2EnabledCheck().isSelected()) {
            worker2.stop();
        }
        if (c.getButton3EnabledCheck().isSelected()) {
            worker3.stop();
        }
        if (c.getButton4EnabledCheck().isSelected()) {
            worker4.stop();
        }
    }

}


