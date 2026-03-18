import UI.MainFrame;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import java.util.Scanner;

/**
 * Bootstraps the application UI and MIDI scale session.
 */
public class Main {
    /**
     * Starts the application, initializes MIDI input, and prompts for scale settings.
     *
     * @param args command-line arguments passed to the application
     * @throws MidiUnavailableException if no selected MIDI device can be opened
     */
    public static void main(String[] args) throws MidiUnavailableException {
        // All Instances Of Required Objects

        // Frame
        MainFrame frame = new MainFrame();

        // Core MIDI connection and scale-session state.
        MidiKeyboardConnection midiKeyboardConnection = new MidiKeyboardConnection();
        MidiDevice currentMidiDevice = MidiKeyboardConnection.getDevices();


        Scanner scanner = new Scanner(System.in);

        ScaleSession scaleSession = new ScaleSession(currentMidiDevice, midiKeyboardConnection);
        MidiInputReceiver inputReceiver = new MidiInputReceiver(scaleSession);

        // Open the active MIDI device before wiring input/output.
        currentMidiDevice.open();

        // Route MIDI messages from the device transmitter.
        midiKeyboardConnection.setTransmitter(currentMidiDevice.getTransmitter());


        // Prompt user to choose a key and interval, then generate the scale.
        System.out.println("Enter a Key");
        String key = scanner.nextLine();

        System.out.println("Enter the Interval (Minor / Major)");
        String interval = scanner.nextLine();

        scaleSession.generateScale(key, interval);

        // Attach custom receiver to process incoming MIDI key events.
        midiKeyboardConnection.setReceiver(inputReceiver);

    }
}
