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

    /** True when a session is complete and can be shut down. */
    private boolean completedSession = false;

    /** Currently active/open MIDI device for this session. */
    private MidiDevice currentMidiDevice;

    /** MIDI connection helper used to close the transmitter on completion. */
    private MidiKeyboardConnection midiKeyboardConnection;
    private MainFrame frame;
    private Synthesizer synthesizer;
    private MidiChannel midiChannel;


    public void initSynth() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            midiChannel = synthesizer.getChannels()[3]; // use first channel
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

    /** Save generated scale using note names. */
    private void setGeneratedScaleAsNotes(int [] scaleAsNumbers){
        generatedScaleAsNotes = scaleNumberToNotes(scaleAsNumbers);
    }


    /** @return current target scale state */
    private String[] getGeneratedScaleAsNotes(){
        return generatedScaleAsNotes;
    }

    /** @return true when this session is marked complete */
    public boolean getCompletionStatus(){
        return completedSession;
    }

    /** Close MIDI resources when a session finishes. */
    private void onCompletion(){
        midiKeyboardConnection.closeTransmitter(); // Close Transmitter
        currentMidiDevice.close(); // Closes the Midi Device
    }

    /**
     * Forward MIDI NOTE_ON to keyboard UI.
     *
     * @param note MIDI note number (for example 60 for middle C)
     */
    public void sendPressedNote(int note, int velocity){
        System.out.println("Got here");
        if(midiChannel != null){
            midiChannel.noteOn(note, velocity);
        }
        frame.getKeyboardPanel().pressKey(note);
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

    public void setFrame(MainFrame frame){
        this.frame = frame;
    }
}
