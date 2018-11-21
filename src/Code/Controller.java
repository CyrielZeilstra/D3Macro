package Code;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    String enabledMp3 = "resources/Enabled.mp3";
    String disabledMp3 = "resources/Disabled.mp3";

    public Label statusLbl;
    public ChoiceBox startstopBtn;

    public CheckBox button1EnabledCheck;
    public CheckBox button2EnabledCheck;
    public CheckBox button3EnabledCheck;
    public CheckBox button4EnabledCheck;

    public TextField button1DelayTxT;
    public TextField button2DelayTxT;
    public TextField button3DelayTxT;
    public TextField button4DelayTxT;

    public ChoiceBox button1Selector;
    public ChoiceBox button2Selector;
    public ChoiceBox button3Selector;
    public ChoiceBox button4Selector;

    public volatile boolean enabled = false;
    public keyPressBot keyBot;

    public int Button1 = KeyEvent.VK_1;
    public int Button2 = KeyEvent.VK_2;
    public int Button3 = KeyEvent.VK_3;
    public int Button4 = KeyEvent.VK_4;


    LinkedHashMap<String, Integer> keyCodes = new LinkedHashMap<String, Integer>();

    public Controller() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(this));
        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);

        // Populate keyCodes map
        keyCodes.put("1", KeyEvent.VK_1);
        keyCodes.put("2", KeyEvent.VK_2);
        keyCodes.put("3", KeyEvent.VK_3);
        keyCodes.put("4", KeyEvent.VK_4);
        keyCodes.put("F1", NativeKeyEvent.VC_F1);
        keyCodes.put("F2", NativeKeyEvent.VC_F2);
        keyCodes.put("F3", NativeKeyEvent.VC_F3);
        keyCodes.put("F4", NativeKeyEvent.VC_F4);
        keyCodes.put("F5", NativeKeyEvent.VC_F5);
        keyCodes.put("F6", NativeKeyEvent.VC_F6);
        keyCodes.put("F7", NativeKeyEvent.VC_F7);
        keyCodes.put("F8", NativeKeyEvent.VC_F8);
        keyCodes.put("F9", NativeKeyEvent.VC_F9);
        keyCodes.put("F10", NativeKeyEvent.VC_F10);
        keyCodes.put("F11", NativeKeyEvent.VC_F11);
        keyCodes.put("F12", NativeKeyEvent.VC_F12);
        keyCodes.put("~", NativeKeyEvent.VC_BACKQUOTE);
        keyCodes.put("Q", NativeKeyEvent.VC_Q);
        keyCodes.put("W", NativeKeyEvent.VC_W);
        keyCodes.put("E", NativeKeyEvent.VC_E);
        keyCodes.put("R", NativeKeyEvent.VC_R);
    }

    @FXML
    public void initialize() {
        setStatusLabel();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.setAll(keyCodes.keySet());

        startstopBtn.setItems(items);
        startstopBtn.getSelectionModel().select(16);
        startstopBtn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = keyCodes.get((String) newValue);
                startstopBtn.getSelectionModel().select(selected);
            }
        });


        button1Selector.setItems(items);
        button1Selector.getSelectionModel().select(0);
        button1Selector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = keyCodes.get((String) newValue);
                button1Selector.getSelectionModel().select(selected);
            }
        });

        button2Selector.setItems(items);
        button2Selector.getSelectionModel().select(1);
        button2Selector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = keyCodes.get((String) newValue);
                button2Selector.getSelectionModel().select(selected);
            }
        });

        button3Selector.setItems(items);
        button3Selector.getSelectionModel().select(2);
        button3Selector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = keyCodes.get((String) newValue);
                button3Selector.getSelectionModel().select(selected);
            }
        });

        button4Selector.setItems(items);
        button4Selector.getSelectionModel().select(3);
        button4Selector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = keyCodes.get((String) newValue);
                button4Selector.getSelectionModel().select(selected);
            }
        });

        keyBot = new keyPressBot(this);
    }


    public void startStopLoop() {
        if (isEnabled()) {
            // disable
            keyBot.stop();
            setEnabled(false);
            updateUI();
        } else {
            // make robot and start.
            keyBot.start();
            setEnabled(true);
            updateUI();
        }
    }

    public void updateUI() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setStatusLabel();
            }
        });
    }

    public void stopLoop() {
        // disable
        keyBot.stop();
        setEnabled(false);
        updateUI();
    }

    public void checkKey(int e) {
        int keyPressed = e;

        if (keyPressed == keyCodes.get(startstopBtn.getSelectionModel().getSelectedItem())) {
            startStopLoop();
        }

        if (keyPressed == NativeKeyEvent.VC_T) {
            stopLoop();
        }
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setStatusLabel() {
        if (isEnabled()) {
            statusLbl.setText("Enabled");
        } else {
            statusLbl.setText("Disabled");
        }
    }

    public CheckBox getButton1EnabledCheck() {
        return button1EnabledCheck;
    }

    public CheckBox getButton2EnabledCheck() {
        return button2EnabledCheck;
    }

    public CheckBox getButton3EnabledCheck() {
        return button3EnabledCheck;
    }

    public CheckBox getButton4EnabledCheck() {
        return button4EnabledCheck;
    }

    public TextField getButton1DelayTxT() {
        return button1DelayTxT;
    }

    public TextField getButton2DelayTxT() {
        return button2DelayTxT;
    }

    public TextField getButton3DelayTxT() {
        return button3DelayTxT;
    }

    public TextField getButton4DelayTxT() {
        return button4DelayTxT;
    }

    public int getButton1() {
        return Button1;
    }

    public int getButton2() {
        return Button2;
    }

    public int getButton3() {
        return Button3;
    }

    public int getButton4() {
        return Button4;
    }
}
