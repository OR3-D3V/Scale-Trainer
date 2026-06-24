package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Visual piano keyboard used by the session.
 * <p>
 * White keys and black keys are added on different layers so black keys stay on top.
 */
public class KeyboardPanel extends JLayeredPane {
    private ArrayList<Integer> whitePositions = new ArrayList<>();
    private HashMap<Integer, Key> keys = new HashMap<>();
    /**
     * @param height parent window height, used to scale the keyboard panel
     */
    public KeyboardPanel(int height){
        this.setPreferredSize(new Dimension(900, height/3));
        this.setLayout(null);
        this.setBackground(new Color(23, 177, 228));
    }

    /**
     * Creates all white keys and maps each one to a MIDI note number.
     *
     * <p><b>Example note mapping</b>: first key starts at note 48 (C3), then
     * skips black-note indexes while laying out white keys left-to-right.</p>
     */
    public void generateWhiteKeyLayout(){
        int currNote = 48;
        int x = 0;
        for(int i =0; i < 29; i++){

            // Skip black MIDI notes here; white layout only maps natural notes.
            while (isBlack(currNote)){
                currNote++;
            }

            WhiteKey curr = new WhiteKey();
            curr.setBounds(x, 0, 40, 90);

            keys.put(currNote, curr);
            currNote++; // Increment currNote
            // White keys go on the default layer.
            add(curr, DEFAULT_LAYER);
//            setComponentZOrder(curr, -1);
            whitePositions.add(x);
            x += curr.getWidth();
        }
    }

    public void generateBlackKeyLayout() {

        int currNote = 48;
        int whiteIndex = 0;

        while (whiteIndex < whitePositions.size()) {
            if (isBlack(currNote)) {
                // Guard index before computing black key position.
                if (whiteIndex - 1 >= 0 && whiteIndex - 1 < whitePositions.size()) {

                    BlackKey currBlack = new BlackKey();

                    // Black key sits between two white keys; anchor from the left white key.
                    int leftWhiteX = whitePositions.get(whiteIndex - 1);
                    // Center black key near the white-key boundary.
                    int blackX = leftWhiteX + 40 - (20 / 2);

                    currBlack.setBounds(blackX, 0, 20, 60);

                    keys.put(currNote, currBlack); // Add it to the map
                    // Black keys use a higher layer so they render in front.
                    add(currBlack, PALETTE_LAYER);
                }

            } else {
                whiteIndex++;
            }

            currNote++;
        }

        revalidate();
        repaint();
    }

    public boolean isBlack(int note) {
        // MIDI note class values for C#, D#, F#, G#, A#.
        int n = note % 12;
        return n == 1 || n == 3 || n == 6 || n == 8 || n == 10;
    }

    public HashMap<Integer, Key> getKeys(){
        return keys;
    }


    public void pressKey(int note, boolean valid){
        if(keys.containsKey(note)){
            if(isBlack(note)){
                BlackKey curr = (BlackKey) keys.get(note);
                curr.pressed();
                curr.setValid(valid);
            }
            else {
                WhiteKey curr = (WhiteKey) keys.get(note);
                curr.pressed();
                curr.setValid(valid);
            }
        }
        else {
            System.out.println(note);
            System.out.println("Key not found / Mapped Properly");
        }
    }


    public void releaseKey(int note){
        if(keys.containsKey(note)){
            if(isBlack(note)){
                BlackKey curr = (BlackKey) keys.get(note);
                curr.released();
            }
            else {
                WhiteKey curr = (WhiteKey) keys.get(note);
                curr.released();
            }
        }
    }

    public void resetColorOfKeys(){
        for(Key curr : keys.values()){
            curr.setCurrentColor(curr.getDefaultColor());
            curr.setValid(false);
        }
        repaint();
    }

}
