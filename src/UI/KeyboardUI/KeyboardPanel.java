package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KeyboardPanel extends JPanel {
    ArrayList<Integer> whitePositions = new ArrayList<>();

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
        int x = 0;
        for(int i =0; i < 29; i++){
            WhiteKey curr = new WhiteKey();
            curr.setBounds(x, 0, 40, 90);
            add(curr);
            whitePositions.add(x);
            x += curr.getWidth();
        }
    }

    public void generateBlackKeyLayout(){
        for(int i =0; i < 27; i++){
            if(i != 2 && i != 6 && i != 9 && i != 13 && i != 16 && i != 20 && i != 23){
                BlackKey currBlack = new BlackKey();

                int leftWhiteX = whitePositions.get(i);
                int blackX = leftWhiteX+ 40 - (currBlack.getWidth() /2);

                currBlack.setBounds(blackX, 0, currBlack.getWidth(), currBlack.getHeight());
                add(currBlack);
                setComponentZOrder(currBlack, 0);
            }
        }
        revalidate();
        repaint();
    }
}
