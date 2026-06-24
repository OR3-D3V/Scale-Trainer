package UI.KeyboardUI;

import java.awt.*;

public interface Key {
    public void pressed();
    public void released();
    public int getWidth();
    public int getHeight();
    public Color getDefaultColor();
    public void setDefaultColor(Color defaultColor);
    public Color getCurrentColor();
    public void setCurrentColor(Color currentColor);
    void setValid(boolean valid);
}
