import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * Receives MIDI messages from a configured transmitter and forwards note input
 * to the active {@link ScaleSession}.
 * <p>
 * This receiver only reacts to {@link ShortMessage} NOTE_ON events with velocity
 * greater than zero (key press). Other MIDI message types are ignored.
 */
public class MidiInputReceiver implements javax.sound.midi.Receiver {
    private final ScaleSession session;

    /**
     * Creates a receiver bound to a scale session.
     *
     * @param session session used to validate incoming note presses and completion state
     */
    public MidiInputReceiver(ScaleSession session){
        this.session = session;
    }

    /**
     * Callback invoked by the Java MIDI runtime when a MIDI event is delivered
     * to this receiver.
     * <p>
     * Processing flow:
     * <ol>
     *   <li>Ignore non-{@link ShortMessage} messages.</li>
     *   <li>Accept only NOTE_ON with velocity {@code > 0}.</li>
     *   <li>If the session is complete, call {@link #close()}.</li>
     *   <li>Otherwise pass the note number to the session for validation.</li>
     * </ol>
     *
     * @param message incoming MIDI message payload
     * @param timeStamp MIDI timestamp supplied by the transmitter
     */
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if(!(message instanceof ShortMessage)){
            return;
        }

        // A ShortMessage contains a MIDI event where getCommand() tells you what happened,
        // getData1() tells you which note, and getData2() tells you how strongly it was pressed.
        
        ShortMessage sm = (ShortMessage) message;

        // Check for NOTE_ON with velocity > 0 (key press)
//        When I press a key, getCommand() tells me what happened.
        //In this case it was a NOTE_ON, and NOTE_ON is represented by a constant numeric value,
        // so we compare the command to that constant.

        if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() > 0) {
            //This checks if the command constant is the same as the NOTE_ON Constant. and checks if the key was pressed.
            int noteNumber = sm.getData1();   // 0–127 What Key.
            int velocity   = sm.getData2();   // 0–127 How hard
            int channel    = sm.getChannel(); // 0–15

            //Only send the noteNumber if the transmitter is not closed.
            if(this.session.getCompletionStatus()){
                close();
            }
            else{
                session.sendPressedNote(noteNumber);

                // Enable this for CLI
//                this.session.getInputFromReceiverAndCheckNextNote(noteNumber);
            }

            //Use This For Debugging If The Class Does Not Work Well.
//            System.out.println(
//                    "NOTE_ON | Note: " + NoteUtil.getNoteAndOctaveBasedOnNumber(noteNumber) + " ("+noteNumber+")" +
//                            " | Velocity: " + velocity +
//                            " | Channel: " + channel
//            );

        }
        // If the note is note being pressed set the key color to its default
        else if(sm.getCommand() == ShortMessage.NOTE_OFF && sm.getData2() == 0){
            session.sendReleasedNote(sm.getData1());
        }
    }

    /**
     * Called when receiver processing should stop.
     * <p>
     * This implementation is currently a no-op and exists as a lifecycle hook
     * for future cleanup behavior.
     */
    @Override
    public void close() {

    }
}
