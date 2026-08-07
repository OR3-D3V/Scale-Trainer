package Midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * Tiny bridge from raw MIDI messages to your session logic.
 * <p>
 * We listen for NOTE_ON / NOTE_OFF events and then call session methods that
 * update the keyboard UI.
 *
 * <p><b>Example</b></p>
 * <pre>{@code
 * NOTE_ON (note=60, velocity=90)  -> session.sendPressedNote(60)
 * NOTE_OFF(note=60, velocity=0)   -> session.sendReleasedNote(60)
 * }</pre>
 */
public class MidiInputReceiver implements javax.sound.midi.Receiver {
    private final ScaleSession session;

    /**
     * @param session active scale session that handles key press/release events
     */
    public MidiInputReceiver(ScaleSession session){
        this.session = session;
    }

    /**
     * Called by Java MIDI every time a message arrives from the active device.
     *
     * <p><b>Event policy</b></p>
     * <ul>
     *   <li>{@code NOTE_ON} with velocity {@code > 0}: note press</li>
     *   <li>{@code NOTE_OFF} with velocity {@code == 0}: note release (current app policy)</li>
     * </ul>
     *
     * <p>When the session is already complete, this receiver triggers completion cleanup
     * and does not forward further note-on events to the session.</p>
     *
     * @param message raw MIDI payload
     * @param timeStamp event timestamp from the MIDI system
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(!(message instanceof ShortMessage)){
            return;
        }

        // command = event type, data1 = note number, data2 = velocity.
        ShortMessage sm = (ShortMessage) message;

        if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() > 0) {
            // Example: middle C is note 60.
            int noteNumber = sm.getData1();
            int velocity   = sm.getData2();
            int channel    = sm.getChannel();

            // Completion is checked at input time so we can disconnect MIDI immediately
            // and prevent any additional NOTE_ON from being processed for this session.
            if(this.session.getCompletionStatus()){
//                session.callOnCompletion();
                close();
            }
            else{
                session.sendPressedNote(noteNumber, velocity);
            }

            // Handy debug format while testing keyboards/channels.
//            System.out.println(
//                    "NOTE_ON | Note: " + Midi.NoteUtil.getNoteAndOctaveBasedOnNumber(noteNumber) + " ("+noteNumber+")" +
//                            " | Velocity: " + velocity +
//                            " | Channel: " + channel
//            );

        }
        // NOTE_OFF returns the key color back to default.
        else if(sm.getCommand() == ShortMessage.NOTE_OFF && sm.getData2() == 0){
            session.sendReleasedNote(sm.getData1());
        }
    }

    /**
     * Receiver shutdown hook.
     * <p>
     * Intentionally a no-op: this receiver does not own the MIDI transmitter/device.
     * Resource cleanup is delegated to {@link ScaleSession#callOnCompletion()} via
     * {@link MidiKeyboardConnection#disconnect()}.
     */
    @Override
    public void close() {

    }
}
