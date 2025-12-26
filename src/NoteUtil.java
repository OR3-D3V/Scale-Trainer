import java.util.HashMap;
import java.util.Map;

public class NoteUtil {
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

        int noteToGet = noteNumber % 12;
        return noteMap.get(noteToGet);
    }

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
        while (true) {
            if (!noteMap.containsKey(note.toUpperCase())) {
                System.out.println("Please Enter A Musical Note");
            }
            else{
                return noteMap.get(note.toUpperCase());
            }
        }
    }

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
        int octave = (noteNumber/12) - 1;
        return noteMap.get(noteToGet) + " " +octave;
    }
}
