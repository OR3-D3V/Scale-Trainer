package UI.KeyboardUI;

public interface Key {
    public void pressed();
    public void released();
    public int getWidth();
    public int getHeight();

    void setValid(boolean valid);
}
