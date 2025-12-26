import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MidiInputReceiver implements javax.sound.midi.Receiver {
    private final ScaleSession session;
    public MidiInputReceiver(ScaleSession session){
        this.session = session;
    }
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

            //Check If Completed and Close
            if(this.session.getCompletionStatus()){
                close();
            }
            else{
                this.session.getInputFromReceiverAndCheckNextNote(noteNumber);
            }

            //Use This For Debugging If The Class Does Not Work Well.
//            System.out.println(
//                    "NOTE_ON | Note: " + NoteUtil.getNoteAndOctaveBasedOnNumber(noteNumber) + " ("+noteNumber+")" +
//                            " | Velocity: " + velocity +
//                            " | Channel: " + channel
//            );

        }
    }


    @Override
    public void close() {

    }
}
