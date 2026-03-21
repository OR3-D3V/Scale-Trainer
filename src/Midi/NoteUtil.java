package Midi;

import java.util.HashMap;
import java.util.Map;

/**
 * Small helper for converting between MIDI note numbers and note names.
 * <p>
 * This keeps note math in one place so session and receiver code stay cleaner.
 */
public class NoteUtil {
    /**
     * Converts a MIDI note number (or any integer) to a chromatic note name.
     *
     * <p><b>Examples</b></p>
     * <pre>{@code
     * getNoteBasedOnNumber(60) -> "C"
     * getNoteBasedOnNumber(61) -> "C#"
     * getNoteBasedOnNumber(73) -> "C#"
     * }</pre>
     *
     * @param noteNumber raw note number
     * @return note name in one octave class (C..B)
     */
    public static String getNoteBasedOnNumber(int noteNumber){
        Map<Integer, String> noteMap = new HashMap<>();
        noteMap.put(0, "C");
        noteMap.put(1, "C#");
        noteMap.put(2, "D");
        noteMap.put(3, "D#");
        noteMap.put(4, "E");
        noteMap.put(5, "F");
        noteMap.put(6, "F#");
        noteMap.put(7, "G");
        noteMap.put(8, "G#");
        noteMap.put(9, "A");
        noteMap.put(10, "A#");
        noteMap.put(11, "B");

        // Collapse any note number into one octave: 0..11.
        int noteToGet = noteNumber % 12;
        return noteMap.get(noteToGet);
    }

    /**
     * Converts a note name into its chromatic number.
     *
     * <p><b>Examples</b></p>
     * <pre>{@code
     * getNumberBasedOnNote("C")  -> 0
     * getNumberBasedOnNote("F#") -> 6
     * }</pre>
     *
     * @param note note text like C, D#, A
     * @return chromatic index 0..11
     */
    public static int getNumberBasedOnNote(String note){
        Map<String, Integer> noteMap = new HashMap<>();
        noteMap.put("C", 0);
        noteMap.put("C#", 1);
        noteMap.put("D", 2);
        noteMap.put("D#", 3);
        noteMap.put("E", 4);
        noteMap.put("F", 5);
        noteMap.put("F#", 6);
        noteMap.put("G", 7);
        noteMap.put("G#", 8);
        noteMap.put("A", 9);
        noteMap.put("A#", 10);
        noteMap.put("B", 11);
        // Keep asking until input matches one of the known note names.
        while (true) {
            if (!noteMap.containsKey(note.toUpperCase())) {
                System.out.println("Please Enter A Musical Note");
            }
            else{
                return noteMap.get(note.toUpperCase());
            }
        }
    }

    /**
     * Converts MIDI note number to note+octave text.
     *
     * <p><b>Examples</b></p>
     * <pre>{@code
     * getNoteAndOctaveBasedOnNumber(60) -> "C 4"
     * getNoteAndOctaveBasedOnNumber(69) -> "A 4"
     * }</pre>
     *
     * @param noteNumber MIDI note number
     * @return formatted note and octave
     */
    public static String getNoteAndOctaveBasedOnNumber(int noteNumber){
        Map<Integer, String> noteMap = new HashMap<>();
        noteMap.put(0, "C");
        noteMap.put(1, "C#");
        noteMap.put(2, "D");
        noteMap.put(3, "D#");
        noteMap.put(4, "E");
        noteMap.put(5, "F");
        noteMap.put(6, "F#");
        noteMap.put(7, "G");
        noteMap.put(8, "G#");
        noteMap.put(9, "A");
        noteMap.put(10, "A#");
        noteMap.put(11, "B");

        int noteToGet = noteNumber % 12;
        // MIDI octave math: C-1 starts at note 0.
        int octave = (noteNumber/12) - 1;
        return noteMap.get(noteToGet) + " " +octave;
    }
}
