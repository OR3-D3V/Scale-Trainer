package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;

public class BlackKey extends JPanel {
    private int width = 20;
    private int height = 60;
    private Color color = Color.BLACK;
    private Color borderColor = Color.red;
    private int borderThickness = 2;
    public BlackKey(){
        this.setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
        setBackground(color);
        setVisible(true);
    }

    public void pressed(){
        setBackground(Color.GREEN);
    }

    public void released(){
        setBackground(Color.black);
    }
    public int getWidth(){
        return width;
    }


    public int getHeight(){
        return height;
    }
}
