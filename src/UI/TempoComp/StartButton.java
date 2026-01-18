package UI.TempoComp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends JToggleButton implements ActionListener {
    public StartButton(){
        super("Start");
        this.setForeground(Color.GREEN);
        this.setVisible(true);
        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this){
            if(this.isSelected()){
                this.setText("Stop");
                this.setForeground(Color.RED);
                System.out.println("Starting Now . . . .");
            }
            else {
                this.setText("Start");
                this.setForeground(Color.GREEN);
                System.out.println("Ending . . . . . .");
            }
        }
    }
}
