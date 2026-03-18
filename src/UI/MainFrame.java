package UI;
import UI.KeyboardUI.KeyboardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;

public class MainFrame extends JFrame implements ActionListener {
    public MainFrame(){
        this.setTitle("Scale Trainer App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.setSize(1160, 500);
        setResizable(false);
        this.add(new ControlBarPanel(), BorderLayout.NORTH);

        // Piano Section
        KeyboardPanel keyboardPanel = new KeyboardPanel(this.getHeight());
        add(keyboardPanel, BorderLayout.SOUTH);
        setVisible(true);
        keyboardPanel.generateWhiteKeyLayout();
        keyboardPanel.generateBlackKeyLayout();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
