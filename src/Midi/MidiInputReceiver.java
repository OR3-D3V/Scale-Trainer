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
     * <p><b>What we care about</b></p>
     * <ul>
     *   <li>{@code NOTE_ON} with velocity {@code > 0}: key pressed</li>
     *   <li>{@code NOTE_OFF}: key released</li>
     * </ul>
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

            // If session is done, stop receiving. Otherwise update UI/session.
            if(this.session.getCompletionStatus()){
                System.out.println("Here");
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
     * Kept as a no-op for now because transmitter/device cleanup happens elsewhere.
     */
    @Override
    public void close() {

    }
}
