package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;

public class WhiteKey extends JPanel implements Key{
    private final int height = 100;
    private final int width = 40;
    public WhiteKey(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        setVisible(true);

    }

    public void pressed(){
        this.setBackground(Color.green);
    }

    public void released(){
        this.setBackground(Color.white);
    }

    public int getWidth(){
        return this.width;
    }


    public int getHeight(){
        return this.height;
    }
}
