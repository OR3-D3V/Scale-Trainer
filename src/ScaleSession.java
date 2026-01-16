import com.sun.source.tree.IfTree;

import javax.sound.midi.MidiDevice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScaleSession{
    private String[] generatedScaleAsNotes;
    private boolean completedSession = false;
    private MidiDevice currentMidiDevice;
    private MidiKeyboardConnection midiKeyboardConnection;
    public ScaleSession(MidiDevice currentMidiDevice, MidiKeyboardConnection midiKeyboardConnection){
        this.currentMidiDevice = currentMidiDevice;
        this.midiKeyboardConnection = midiKeyboardConnection;
    }

    public void generateMajorScale(String key, String interval){
        //generated Scale is the scale we would generate, Selected scale is the
        int [] minorScaleInterval = {2, 1, 2, 2, 1, 2, 2};
        int [] majorScaleInterval = {2, 2, 1, 2, 2, 2, 1};
        int [] generatedScale = new int[8];
        int [] selectedScale = new int[7];
        int currInd = 0;
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
//               This would determine how it would add the number in terms of the selected scale interval.
                int noteNum = (generatedScale[i - 1] + selectedScale[i - 1]);
                if(noteNum > 11){
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

    public String[] scaleNumberToNotes(int [] numberScale){
        String[] scaleInLetters = new String[numberScale.length];
        for(int i = 0; i< numberScale.length; i++){
            scaleInLetters[i] = NoteUtil.getNoteBasedOnNumber(numberScale[i]);
        }
        return scaleInLetters;
    }

    private void setGeneratedScaleAsNotes(int [] scaleAsNumbers){
        generatedScaleAsNotes = scaleNumberToNotes(scaleAsNumbers);
    }

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

    private String[] getGeneratedScaleAsNotes(){
        return generatedScaleAsNotes;
    }

    public boolean getCompletionStatus(){
        return completedSession;
    }

    //Close The Devices and The Transmitter
    private void onCompletion(){
        midiKeyboardConnection.closeTransmitter(); // Close Transmitter
        currentMidiDevice.close(); // Closes the Midi Device
    }
}
