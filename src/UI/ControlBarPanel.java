package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.JarEntry;

public class ControlBarPanel extends JPanel implements ActionListener {
    // You have to pass in reference data types to the ComboBox e.g(String). Primitive types would not work (int, double).
    private final String[] musicalNotes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    private final String[] mode = {"Major", "Minor"};
    private JComboBox dropDown;
    private JComboBox modeDropDown;

    public ControlBarPanel(){
        // Main Panel
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Scale Panel
        JPanel scale = new JPanel(new FlowLayout());
        scale.setVisible(true);
            //Label
        JLabel label = new JLabel("Key/Scale: ");
            //Combobox
        dropDown = new JComboBox(musicalNotes);
        dropDown.addActionListener(this); // You have to add a listener for it to work.
            //Add all
        scale.add(label);
        scale.add(dropDown);

        //Type or Mode Panel
        JPanel typePanel = new JPanel(new FlowLayout());
        typePanel.setVisible(true);
            //Text
        JLabel typeText = new JLabel("Type: ");
            //Combo
        modeDropDown = new JComboBox(mode);
        modeDropDown.addActionListener(this);
            //Add all
        typePanel.add(typeText);
        typePanel.add(modeDropDown);

        //Add all components to the panel
        this.add(scale);
        this.add(typePanel);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dropDown){
            System.out.println(dropDown.getSelectedItem());
        }
    }
}
