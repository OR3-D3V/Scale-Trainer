import Midi.MidiInputReceiver;
import Midi.MidiKeyboardConnection;
import Midi.ScaleSession;
import UI.MainFrame;

import javax.sound.midi.MidiUnavailableException;

/**
 * App entry point.
 * <p>
 * In plain terms: this is where we wire the MIDI receiver and open the main UI.
 *
 * <p><b>Quick flow</b></p>
 * <pre>{@code
 * ScaleSession session -> MidiInputReceiver receiver -> MidiKeyboardConnection connection -> MainFrame UI
 * }</pre>
 * <pre>
 *      {@code
 *      UI → MIDI Connection → Receiver → Session → UI
 *      }
 * </pre>
 */
public class Main {
    /**
     * Starts the app.
     * <p>
     * We create the session first, then the MIDI receiver, then the MIDI connection,
     * and finally the frame that lets the user pick a device.
     *
     * @param args normal JVM args (not used right now)
     * @throws MidiUnavailableException thrown if the system cannot provide MIDI access
     */
    public static void main(String[] args) throws MidiUnavailableException {
        ScaleSession scaleSession = new ScaleSession();
        MidiInputReceiver midiInputReceiver = new MidiInputReceiver(scaleSession);
        MidiKeyboardConnection midiKeyboardConnection = new MidiKeyboardConnection();
        midiKeyboardConnection.setReceiver(midiInputReceiver);
        MainFrame mainFrame = new MainFrame(midiKeyboardConnection);
        scaleSession.setFrame(mainFrame);
        scaleSession.initSynth();
    }
}
