package Midi;

import UI.MainFrame;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import java.awt.*;
import java.util.Arrays;

/**
 * Holds the active training/session state.
 * <p>
 * This class knows what scale was generated and reacts to note events coming
 * from MIDI by updating the keyboard UI.
 */
public class ScaleSession{
    /** Generated target scale as note names (for example: C D E F G A B C). */
    private String[] generatedScaleAsNotes;
    // Mutable copy used as session progress state. Items are replaced with marker values as notes are consumed.
    private String[] currentInputScaleAsNotes;

    /** True when a session is complete and can be shut down. */
    private boolean completedSession = false;

    /** Currently active/open MIDI device for this session. */
    private MidiDevice currentMidiDevice;

    /** MIDI connection helper used to close the transmitter on completion. */
    private MidiKeyboardConnection midiKeyboardConnection;
    private MainFrame frame;
    private Synthesizer synthesizer;
    private MidiChannel midiChannel;
    private String selectedKey;
    private String selectedMode;

    //===================== LOGIC =====================
    public void startSession(String key, String mode){
        System.out.println(key  +  " " + mode);
        generateScale(key, mode);
    }

    /**
     * Processes each incoming note against the next expected note in the scale.
     * <p>
     * The first unconsumed slot in {@code currentInputScaleAsNotes} is the expected note.
     * A match is marked as consumed and painted valid; a mismatch is painted invalid.
     * Once the final slot is consumed, the session is flagged complete.
     *
     * <p>Current implementation uses inline sentinel text values ("✅" / "❌") to mark
     * consumed or invalid states. If you later add a constant like NOTE_DONE_MARKER,
     * this is the method where it is read and written.</p>
     *
     * @param noteNumber incoming MIDI note number
     */
    public void ongoingSession(int noteNumber){
        for(int i = 0; i < currentInputScaleAsNotes.length; i++){
            String currNote = currentInputScaleAsNotes[i];

            // "✅" acts like a NOTE_DONE_MARKER sentinel: this expected position is already completed.
            if(currNote.equalsIgnoreCase("✅")){
                continue;
            }
            // "❌" is currently treated as already-handled state as well.
            else if(currNote.equalsIgnoreCase("❌")){
                continue;
            }
            // Send Note If Session is active
            else {
                if(NoteUtil.getNoteBasedOnNumber(noteNumber).equalsIgnoreCase(currNote)){
                    System.out.println(currNote);
                    sendValidNote(noteNumber);
                    // Mark this slot as consumed so the next incoming note is validated against the next scale note.
                    currentInputScaleAsNotes[i] = "✅";

                    // Index 7 is the octave slot in the 8-note generated scale (root to octave).
                    if(i == 7 && currentInputScaleAsNotes[i].equalsIgnoreCase("✅")){
                        completedSession = true;
                        System.out.println("Session Complete");
                    }
                    break;
                }

                // This should send an invalid note if the not is not in the scale and session is active.
                else {
                    sendInvalidNote(noteNumber);
                    break;
                }
            }
        }
    }


    // ==================== SETTERS ====================

    /** Save generated scale using note names. */
    private void setGeneratedScaleAsNotes(int [] scaleAsNumbers){
        generatedScaleAsNotes = scaleNumberToNotes(scaleAsNumbers);
        currentInputScaleAsNotes = Arrays.copyOf(generatedScaleAsNotes, generatedScaleAsNotes.length);
    }

    public void setSelectedKey(String key){
        selectedKey = key;
    }

    public void setSelectedMode(String mode){
        selectedMode = mode;
    }

    public void setFrame(MainFrame frame){
        this.frame = frame;
    }

    /**
     * Injects the shared MIDI connection so completion cleanup can disconnect input.
     *
     * @param midiKeyboardConnection active MIDI connection manager
     */
    public void setMidiKeyboardConnection(MidiKeyboardConnection midiKeyboardConnection) {
        this.midiKeyboardConnection = midiKeyboardConnection;
    }

    // ==================== GETTERS ====================

    /** @return current target scale state */
    private String[] getGeneratedScaleAsNotes(){
        return generatedScaleAsNotes;
    }

    /** @return true when this session is marked complete */
    public boolean getCompletionStatus(){
        return completedSession;
    }


    // ==================== SEND METHODS ====================

    /**
     * Forward MIDI NOTE_ON to keyboard UI.
     *
     * @param note MIDI note number (for example 60 for middle C)
     */
    public void sendPressedNote(int note, int velocity){
//        System.out.println("Got here");
        ongoingSession(note);
        if(midiChannel != null){
            midiChannel.noteOn(note, velocity);
        }
    }

    public void sendValidNote(int noteNumber){
        frame.getKeyboardPanel().pressKey(noteNumber, true);

    }

    public void sendInvalidNote(int noteNumber){
        frame.getKeyboardPanel().pressKey(noteNumber, false);

    }


    /**
     * Forward MIDI NOTE_OFF to keyboard UI.
     *
     * @param note MIDI note number to release
     */
    public void sendReleasedNote(int note){
        if(midiChannel != null){
            midiChannel.noteOff(note);
        }
        frame.getKeyboardPanel().releaseKey(note);
    }

    // ==================== HELPER METHODS ====================

    /** Initialize synthesizer and get the first MIDI channel. */
    public void initSynth() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            midiChannel = synthesizer.getChannels()[0]; // use first channel
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an 8-note scale (root up to octave) and stores it.
     * <p>
     * Supported interval values: "major" and "minor".
     *
     * <p><b>Example</b></p>
     * <pre>{@code
     * generateScale("C", "major") -> [C, D, E, F, G, A, B, C]
     * generateScale("A", "minor") -> [A, B, C, D, E, F, G, A]
     * }</pre>
     *
     * @param key root note name (for example, "C", "F#", or "Bb")
     * @param interval scale type to generate ("major" or "minor")
     */
    public void generateScale(String key, String interval){
        // Step pattern in semitones between scale notes.
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
        // Build each note from the previous one using the selected interval pattern.
        for (int i = 0; i < generatedScale.length; i++){
            if(i == 0){
                generatedScale[i] = NoteUtil.getNumberBasedOnNote(key);
            }
            else if(i == 7){
                generatedScale[i] = generatedScale[0];
            }
            else {
                // noteNum is the previous note + interval(addition)
                int noteNum = (generatedScale[i - 1] + selectedScale[i]);
                if(noteNum > 11){ // If it is more than 11 then go back to 0 as we only have notes from 0 - 11
                    generatedScale[i] = (noteNum % 11) - 1;
                }
                else {
                    generatedScale[i] = noteNum;
                }
            }
        }
        // Print a readable preview and store for later checks.
//        System.out.println("Scale as Numbers = " + Arrays.toString(generatedScale));
        System.out.println("Scale as Letters = " + Arrays.toString(scaleNumberToNotes(generatedScale)));
        setGeneratedScaleAsNotes(generatedScale);
    }

    /**
     * Converts note numbers to note names.
     *
     * @param numberScale notes as chromatic numbers (0-11)
     * @return same shape array, but with note letters
     */
    public String[] scaleNumberToNotes(int [] numberScale){
        String[] scaleInLetters = new String[numberScale.length];
        for(int i = 0; i< numberScale.length; i++){
            scaleInLetters[i] = NoteUtil.getNoteBasedOnNumber(numberScale[i]);
        }
        return scaleInLetters;
    }


    /**
     * Closes MIDI input resources when the session finishes.
     * <p>
     * This method is currently invoked from the MIDI receiver callback path.
     */
    private void onCompletion(){
        if(midiKeyboardConnection != null){
            midiKeyboardConnection.disconnect(); // Disconnect transmitter and active input device.
        }
    }

    /** Public completion hook used by {@link MidiInputReceiver} once session is done. */
    public void callOnCompletion(){
        onCompletion();
    }


}
