package UI.TempoComp;

import UI.ControlBarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends JToggleButton implements ActionListener {

    private ControlBarPanel controlBarPanel;
    public StartButton(ControlBarPanel controlBarPanel){
        super("Start");
        this.controlBarPanel = controlBarPanel;
        this.setForeground(Color.GREEN);
        this.setVisible(true);
        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this){
            if(this.isSelected()){
                controlBarPanel.callStart();
                System.out.println("Starting Now . . . .");
            }
            else {
                controlBarPanel.callStop();
                this.setText("Start");
                this.setForeground(Color.GREEN);
                System.out.println("Ending . . . . . .");
            }
        }
    }
}
