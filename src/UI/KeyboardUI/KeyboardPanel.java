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

//        for(int i = 0; i <= 12; i++){
//            if(i == 1 || i == 3 || i == 6 || i == 8 || i == 10){
//                BlackKey currBlack = new BlackKey();
//                currBlack.setBounds(xBlack, 0, currBlack.getWidth(), currBlack.getHeight());
//                add(currBlack);
//                setComponentZOrder(currBlack, 0);
//                xBlack = xWhite - 40 - (currBlack.getWidth() / 2);
//            }
//            else{
//                WhiteKey currWhite = new WhiteKey();
//                currWhite.setBounds(xWhite, 0, currWhite.getWidth(), currWhite.getHeight());
//                xWhite+= currWhite.getWidth();
//                add(currWhite);
//            }
//        }

    }

    /**
     * params: none
     * generates the piano layout
     */

    public void generateWhiteKeyLayout(){
        int x = 0;
        for(int i =0; i < 7; i++){
            WhiteKey curr = new WhiteKey();
            curr.setBounds(x, 0, 40, 90);
            add(curr);
            whitePositions.add(x);
            x += curr.getWidth();
        }
    }

    public void generateBlackKeyLayout(){
        for(int i =0; i < 7; i++){
            if(i != 2 && i !=6){
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
