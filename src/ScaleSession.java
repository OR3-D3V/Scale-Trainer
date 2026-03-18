import com.sun.source.tree.IfTree;

import javax.sound.midi.MidiDevice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents one guided scale-practice session.
 * <p>
 * A session generates a target scale from a root key and interval type, then
 * evaluates incoming MIDI note presses one-by-one against the expected note
 * sequence. The current status is printed after each checked note and, once the
 * session is completed, associated MIDI resources are closed.
 */
public class ScaleSession{
    /**
     * Target scale represented as note names, later replaced position-by-position
     * with status markers ("✅" or "❌") as the user plays.
     */
    private String[] generatedScaleAsNotes;

    /**
     * Indicates whether the session has reached completion and shutdown logic
     * has been triggered.
     */
    private boolean completedSession = false;

    /**
     * Active MIDI device used by this session.
     */
    private MidiDevice currentMidiDevice;

    /**
     * Connection helper used to close the transmitter when the session completes.
     */
    private MidiKeyboardConnection midiKeyboardConnection;
    
    /**
     * Creates a scale session bound to the selected MIDI device and connection.
     *
     * @param currentMidiDevice active MIDI device chosen for this session
     * @param midiKeyboardConnection MIDI connection wrapper associated with the device
     */
    public ScaleSession(MidiDevice currentMidiDevice, MidiKeyboardConnection midiKeyboardConnection){
        this.currentMidiDevice = currentMidiDevice;
        this.midiKeyboardConnection = midiKeyboardConnection;
    }

    /**
     * Generates an eight-note scale (root through octave) for the given key and
     * interval pattern, prints it, and stores it as the session target.
     * <p>
     * Supported interval values are "major" and "minor" (case-insensitive).
     *
     * @param key root note name (for example, "C", "F#", or "Bb")
     * @param interval scale type to generate ("major" or "minor")
     */
    public void generateScale(String key, String interval){
        //generated Scale is the scale we would generate, Selected scale is the
        int [] minorScaleInterval = {0, 2, 1, 2, 2, 1, 2, 2};
        int [] majorScaleInterval = {0, 2, 2, 1, 2, 2, 2, 1};
        int [] generatedScale = new int[8];
        int [] selectedScale = new int[8];

        if(interval.equalsIgnoreCase("major")){
            selectedScale = majorScaleInterval;
        }
        else if(interval.equalsIgnoreCase("minor")){
            selectedScale = minorScaleInterval;
        }
        // Iterate through the list and add the new values to the list.
        for (int i = 0; i < generatedScale.length; i++){
            if(i == 0){
                generatedScale[i] = NoteUtil.getNumberBasedOnNote(key);
            }
            else if(i == 7){
                generatedScale[i] = generatedScale[0];
            }
            else {
                /**
                 *  This would generate a the current note number,
                 *  noteNumber would be previous note number + tone type 1 for semi tone 2 for tone
                 *  8 is G#, next key would be either tone or semi-tone.
                 *  If semi-tone then next key would be 9 which would be + selectedScale[i]
                 */

                int noteNum = (generatedScale[i - 1] + selectedScale[i]);
                if(noteNum > 11){ // If it is more than 11 then go back to 0 as we only have notes from 0 - 11
                    generatedScale[i] = (noteNum % 11) - 1;
                }
                else {
                    generatedScale[i] = noteNum;
                }
            }
        }
        //Print The Scale As Letters And Also Call The Setter Methods
//        System.out.println("Scale as Numbers = " + Arrays.toString(generatedScale));
        System.out.println("Scale as Letters = " + Arrays.toString(scaleNumberToNotes(generatedScale)));
        setGeneratedScaleAsNotes(generatedScale);
    }

    /**
     * Converts a scale represented as semitone indexes into note names.
     *
     * @param numberScale scale values as note numbers in chromatic space
     * @return array of note names with the same length/order as the input array
     */
    public String[] scaleNumberToNotes(int [] numberScale){
        String[] scaleInLetters = new String[numberScale.length];
        for(int i = 0; i< numberScale.length; i++){
            scaleInLetters[i] = NoteUtil.getNoteBasedOnNumber(numberScale[i]);
        }
        return scaleInLetters;
    }

    /**
     * Stores the generated scale in note-name form for later input validation.
     *
     * @param scaleAsNumbers generated scale values to persist as note names
     */
    private void setGeneratedScaleAsNotes(int [] scaleAsNumbers){
        generatedScaleAsNotes = scaleNumberToNotes(scaleAsNumbers);
    }

    /**
     * Checks the next expected note in the session against an incoming MIDI note,
     * updates progress markers, and prints the latest scale status.
     * <p>
     * The first unmatched position is evaluated:
     * a match is marked "✅", a mismatch is marked "❌". When all positions are
     * already marked, the session is finalized and resources are closed.
     *
     * @param noteAsNumber MIDI note number received from the input callback
     */
    public void getInputFromReceiverAndCheckNextNote(int noteAsNumber){
        String [] generatedScaleAsNoteTemp = getGeneratedScaleAsNotes();
        String note = NoteUtil.getNoteBasedOnNumber(noteAsNumber);
//        System.out.println(note);

        for(int i = 0; i < generatedScaleAsNoteTemp.length; i++){
            if(!generatedScaleAsNoteTemp[i].equalsIgnoreCase("✅") & !generatedScaleAsNoteTemp[i].equalsIgnoreCase("❌")){
                if(generatedScaleAsNoteTemp[i].equalsIgnoreCase(note)){
                    generatedScaleAsNoteTemp[i] = "✅";
                    System.out.println(Arrays.toString(generatedScaleAsNoteTemp));
                    break;
                }
                else {
                    generatedScaleAsNoteTemp[i] = "❌";
                    System.out.println(Arrays.toString(generatedScaleAsNoteTemp));
                    break;
                }
            }
            else if(i == 7) {
                completedSession = true;
                onCompletion();
            }
        }
    }

    /**
     * Returns the mutable session state array containing pending notes and
     * completion markers.
     *
     * @return current generated scale state
     */
    private String[] getGeneratedScaleAsNotes(){
        return generatedScaleAsNotes;
    }

    /**
     * Reports whether this session has completed.
     *
     * @return {@code true} when completion has been reached, otherwise {@code false}
     */
    public boolean getCompletionStatus(){
        return completedSession;
    }

    /**
     * Performs completion shutdown by closing the MIDI transmitter and device.
     */
    private void onCompletion(){
        midiKeyboardConnection.closeTransmitter(); // Close Transmitter
        currentMidiDevice.close(); // Closes the Midi Device
    }
}
