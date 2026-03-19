package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyboardPanel extends JPanel {
    private ArrayList<Integer> whitePositions = new ArrayList<>();
    private HashMap<Integer, Key> keys = new HashMap<>();
    public KeyboardPanel(int height){
        this.setPreferredSize(new Dimension(900, height/3));
        this.setLayout(null);
        this.setBackground(new Color(23, 177, 228));
    }

    /**
     * params: none
     * generates the piano layout
     */
    public void generateWhiteKeyLayout(){
        int currNote = 48;
        int x = 0;
        for(int i =0; i < 29; i++){

            // If the is black, go to the next one.
            while (isBlack(currNote)){
                currNote++;
            }

            WhiteKey curr = new WhiteKey();
            curr.setBounds(x, 0, 40, 90);

            keys.put(currNote, curr);

            add(curr);
            whitePositions.add(x);
            x += curr.getWidth();
        }
    }

    public void generateBlackKeyLayout() {

        int currNote = 48;
        int whiteIndex = 0;

        while (whiteIndex < whitePositions.size()) {

            if (isBlack(currNote)) {

                // make sure we don't go out of bounds
                if (whiteIndex - 1 >= 0 && whiteIndex - 1 < whitePositions.size()) {

                    BlackKey currBlack = new BlackKey();

                    // Get previous white key
                    int leftWhiteX = whitePositions.get(whiteIndex - 1);
                    // set up the black keys x pozition
                    int blackX = leftWhiteX + 40 - (20 / 2);

                    currBlack.setBounds(blackX, 0, 20, 60);

                    keys.put(currNote, currBlack); // Add it to the map

                    add(currBlack);
                    setComponentZOrder(currBlack, 0);
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
        int n = note % 12;
        return n == 1 || n == 3 || n == 6 || n == 8 || n == 10;
    }

    public HashMap<Integer, Key> getKeys(){
        return keys;
    }


    public void pressKey(int note){
        if(keys.containsKey(note)){
            if(isBlack(note)){
                BlackKey curr = (BlackKey) keys.get(note);
                curr.pressed();
            }
            else {
                WhiteKey curr = (WhiteKey) keys.get(note);
                curr.pressed();
            }
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
}
