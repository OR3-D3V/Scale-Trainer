package UI.TempoComp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AscendButton extends JButton implements ActionListener {
    public AscendButton(){
        this.setText("Ascend");
        this.addActionListener(this);
        this.setPreferredSize(new Dimension(80, 40));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this){
            System.out.println("Ascend");
        }
    }
}




