package UI;

import UI.KeyboardUI.KeyboardPanel;
import Midi.MidiKeyboardConnection;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main app window.
 * <p>
 * This frame owns the visual parts (control bar + keyboard) and forwards
 * MIDI-device selection to the MIDI connection helper.
 */
public class MainFrame extends JFrame implements ActionListener {
    private final KeyboardPanel keyboardPanel;
    private MidiKeyboardConnection midiKeyboardConnection;

    /**
     * Builds the UI and injects the MIDI connection object.
     *
     * <p><b>Example</b></p>
     * <pre>{@code
     * MidiKeyboardConnection connection = new MidiKeyboardConnection();
     * MainFrame frame = new MainFrame(connection);
     * }</pre>
     *
     * @param midiKeyboardConnection shared MIDI connection manager for device selection
     * @throws MidiUnavailableException if device enumeration fails while building the control bar
     */
    public MainFrame(MidiKeyboardConnection midiKeyboardConnection) throws MidiUnavailableException {
        this.midiKeyboardConnection = midiKeyboardConnection;
        this.setTitle("Scale Trainer App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.setSize(1160, 500);
        setResizable(false);

        // Pass this frame so the top bar can call back when device selection changes.
        ControlBarPanel controlBarPanel = new ControlBarPanel(midiKeyboardConnection.getDevices(), this);
        this.add(controlBarPanel, BorderLayout.NORTH);

        // Piano section is independent from device selection UI.
        keyboardPanel = new KeyboardPanel(this.getHeight());
        add(keyboardPanel, BorderLayout.SOUTH);
        setVisible(true);
        keyboardPanel.generateWhiteKeyLayout();
        keyboardPanel.generateBlackKeyLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public KeyboardPanel getKeyboardPanel(){

        return keyboardPanel;
    }

    /**
     * Called by the control bar when the user chooses a MIDI device name.
     *
     * @param name visible device name from the combo box
     */
    public void onDeviceSelected(String name){
        System.out.println(name);
        midiKeyboardConnection.setMidiDevice(name);
    }


}
