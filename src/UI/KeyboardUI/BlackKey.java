package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;

public class BlackKey extends JPanel implements Key{
    private int width = 20;
    private int height = 60;
    private Color color = Color.BLACK;
    private Color borderColor = Color.black;
    private int borderThickness = 2;
    private boolean valid;
    private Color defaultColor = Color.black;
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
        if(valid){
            this.setBackground(Color.green);
        }
        else {
            setBackground(Color.red);
        }
    }
    public int getWidth(){
        return width;
    }


    public int getHeight(){
        return height;
    }

    @Override
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Color getCurrentColor(){
        return this.getBackground();
    }

    public void setCurrentColor(Color currentColor){
        this.setBackground(currentColor);
    }
}
