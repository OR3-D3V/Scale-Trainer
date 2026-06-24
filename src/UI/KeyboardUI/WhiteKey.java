package UI.KeyboardUI;

import javax.swing.*;
import java.awt.*;

/**
 * One white piano key component.
 * <p>
 * The key turns green when pressed and back to white when released.
 */
public class WhiteKey extends JPanel implements Key{
    private final int height = 100;
    private final int width = 40;
    private boolean valid = false;
    private Color defaultColor = Color.white;
    /**
     * Builds the key UI with fixed dimensions used by {@link KeyboardPanel}.
     */
    public WhiteKey(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        setVisible(true);

    }

    /** Mark key as active/pressed. */
    public void pressed(){
        setBackground(Color.yellow);
    }

    /** Reset key color when note is released. */
    public void released(){
        if(valid){
            this.setBackground(Color.green);
        }
        else {
            setBackground(Color.red);
        }
    }

    public int getWidth(){
        return this.width;
    }


    public int getHeight(){
        return this.height;
    }

    public void setValid(boolean valid){
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
