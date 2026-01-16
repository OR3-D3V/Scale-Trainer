package UI.TempoComp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TempoSlider extends JSlider implements ChangeListener {
    private JLabel tempoValue = new JLabel();
    public TempoSlider(){
        super(10, 150, 90);
        this.setMinorTickSpacing(20);
        this.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == this){
            System.out.println(tempoValue.getText());
            tempoValue.setText(this.getValue() + " BPM");
        }
    }

    public JLabel getTempoValue() {
        return tempoValue;
    }
}

