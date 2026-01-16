package UI.TempoComp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescendButton extends JButton implements ActionListener {
    public DescendButton(){
        this.setText("Descend");
        this.addActionListener(this);
        this.setPreferredSize(new Dimension(80, 40));
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this){
            System.out.println("Descend");
        }
    }
}
