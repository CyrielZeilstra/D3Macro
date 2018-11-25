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
import javazoom.jl.player.Player;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    Boolean soundQue = true;

    public Label statusLbl;
    public ChoiceBox startstopBtn;

    public CheckBox button1EnabledCheck;
    public CheckBox button2EnabledCheck;
    public CheckBox button3EnabledCheck;
    public CheckBox button4EnabledCheck;
    public CheckBox rightClickEnabledCheck;
    public CheckBox leftClickEnabledCheck;

    public TextField button1DelayTxT;
    public TextField button2DelayTxT;
    public TextField button3DelayTxT;
    public TextField button4DelayTxT;
    public TextField rightClickDelayTxT;
    public TextField leftClickDelayTxT;

    public ChoiceBox button1Selector;
    public ChoiceBox button2Selector;
    public ChoiceBox button3Selector;
    public ChoiceBox button4Selector;

    public volatile boolean enabled = false;
    public keyPressBot keyBot;

    LinkedHashMap<String, Integer> keyCodes = new LinkedHashMap<String, Integer>();
    LinkedHashMap<String, Integer> nativekeyCodes = new LinkedHashMap<String, Integer>();

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
        keyCodes.put("F1", KeyEvent.VK_F1);
        keyCodes.put("F2", KeyEvent.VK_F2);
        keyCodes.put("F3", KeyEvent.VK_F3);
        keyCodes.put("F4", KeyEvent.VK_F4);
        keyCodes.put("F5", KeyEvent.VK_F5);
        keyCodes.put("F6", KeyEvent.VK_F6);
        keyCodes.put("F7", KeyEvent.VK_F7);
        keyCodes.put("F8", KeyEvent.VK_F8);
        keyCodes.put("F9", KeyEvent.VK_F9);
        keyCodes.put("F10", KeyEvent.VK_F10);
        keyCodes.put("F11", KeyEvent.VK_F11);
        keyCodes.put("F12", KeyEvent.VK_F12);
        keyCodes.put("Q", KeyEvent.VK_Q);
        keyCodes.put("W", KeyEvent.VK_W);
        keyCodes.put("E", KeyEvent.VK_E);
        keyCodes.put("R", KeyEvent.VK_R);
        keyCodes.put("~", KeyEvent.VK_BACK_QUOTE);

        nativekeyCodes.put("1", NativeKeyEvent.VC_1);
        nativekeyCodes.put("2", NativeKeyEvent.VC_2);
        nativekeyCodes.put("3", NativeKeyEvent.VC_3);
        nativekeyCodes.put("4", NativeKeyEvent.VC_4);
        nativekeyCodes.put("F1", NativeKeyEvent.VC_F1);
        nativekeyCodes.put("F2", NativeKeyEvent.VC_F2);
        nativekeyCodes.put("F3", NativeKeyEvent.VC_F3);
        nativekeyCodes.put("F4", NativeKeyEvent.VC_F4);
        nativekeyCodes.put("F5", NativeKeyEvent.VC_F5);
        nativekeyCodes.put("F6", NativeKeyEvent.VC_F6);
        nativekeyCodes.put("F7", NativeKeyEvent.VC_F7);
        nativekeyCodes.put("F8", NativeKeyEvent.VC_F8);
        nativekeyCodes.put("F9", NativeKeyEvent.VC_F9);
        nativekeyCodes.put("F10", NativeKeyEvent.VC_F10);
        nativekeyCodes.put("F11", NativeKeyEvent.VC_F11);
        nativekeyCodes.put("F12", NativeKeyEvent.VC_F12);
        nativekeyCodes.put("Q", NativeKeyEvent.VC_Q);
        nativekeyCodes.put("W", NativeKeyEvent.VC_W);
        nativekeyCodes.put("E", NativeKeyEvent.VC_E);
        nativekeyCodes.put("R", NativeKeyEvent.VC_R);
        nativekeyCodes.put("~", NativeKeyEvent.VC_BACKQUOTE);
    }

    @FXML
    public void initialize() {
        setStatusLabel();
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> nativeItems = FXCollections.observableArrayList();
        items.setAll(keyCodes.keySet());
        nativeItems.setAll(nativekeyCodes.keySet());

        startstopBtn.setItems(nativeItems);
        startstopBtn.getSelectionModel().select(20);
        startstopBtn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selected = nativekeyCodes.get((String) newValue);
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
            if (soundQue) {
                try {
                    InputStream disabledInputstream = Controller.class.getResourceAsStream("/Resources/Disabled.mp3");
                    new Player(disabledInputstream).play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            updateUI();
        } else {
            // make robot and start.
            keyBot.start();
            setEnabled(true);
            if (soundQue) {
                try {
                    InputStream enabledInputstream = Controller.class.getResourceAsStream("/Resources/Enabled.mp3");
                    new Player(enabledInputstream).play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
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

        if (keyPressed == nativekeyCodes.get(startstopBtn.getSelectionModel().getSelectedItem())) {
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
        return keyCodes.get(button1Selector.getSelectionModel().getSelectedItem());
    }

    public int getButton2() {
        return keyCodes.get(button2Selector.getSelectionModel().getSelectedItem());
    }

    public int getButton3() {
        return keyCodes.get(button3Selector.getSelectionModel().getSelectedItem());
    }

    public int getButton4() {
        return keyCodes.get(button4Selector.getSelectionModel().getSelectedItem());
    }

    public CheckBox getRightClickEnabledCheck() {
        return rightClickEnabledCheck;
    }

    public CheckBox getLeftClickEnabledCheck() {
        return leftClickEnabledCheck;
    }

    public TextField getRightClickDelayTxT() {
        return rightClickDelayTxT;
    }

    public TextField getLeftClickDelayTxT() {
        return leftClickDelayTxT;
    }
}
