package UI;

import UI.TempoComp.AscendButton;
import UI.TempoComp.DescendButton;
import UI.TempoComp.StartButton;
import UI.TempoComp.TempoSlider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Top control strip for scale settings and MIDI device selection.
 * <p>
 * This panel does not open devices by itself. It only notifies {@link MainFrame}
 * when the user picks a device from the drop-down.
 */
public class ControlBarPanel extends JPanel implements ActionListener {
    // You have to pass in reference data types to the ComboBox e.g(String). Primitive types would not work (int, double).
    private final String[] musicalNotes = {"Key", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    private final String[] mode = {"Select mode", "Major", "Minor"};
    private JComboBox noteDropDown;
    private JComboBox modeDropDown;
    private JComboBox midiDevicesDropDown;
    private String[] midiDevices;
    private MainFrame frame;
    private String selectedKey;
    private String selectedMode;
    private StartButton startButton;
    private String midiDeviceName;
    /**
     * Builds all controls shown at the top of the app.
     *
     * @param midiDevices already discovered MIDI device names
     * @param frame frame callback target for actions like device selection
     */
    public ControlBarPanel(String[] midiDevices, MainFrame frame){
        this.midiDevices = midiDevices;
        this.frame = frame;

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
        noteDropDown = new JComboBox(musicalNotes);
        noteDropDown.addActionListener(this); // You have to add a listener for it to work.


        //Add all
        scale.add(label);
        scale.add(noteDropDown);

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

        //Tempo Slider
        JPanel tempoSliderPanel = new JPanel(new FlowLayout());
        JLabel tempoText = new JLabel("Tempo");
        TempoSlider tempoSlider = new TempoSlider();
        tempoSliderPanel.add(tempoText);
        tempoSliderPanel.add(tempoSlider);
        tempoSliderPanel.add(tempoSlider.getTempoValue());


        //Ascend Button
        AscendButton ascendButton = new AscendButton();

        //Descend Button
        DescendButton descendButton = new DescendButton();

        // MIDI device picker (this is what triggers live MIDI routing).
        midiDevicesDropDown = new JComboBox(midiDevices);
        midiDevicesDropDown.addActionListener(this);

        //Add all components to the panel
        this.add(scale);
        this.add(typePanel);
        this.add(tempoSliderPanel);
        this.add(tempoSlider.getTempoValue());
        this.add(ascendButton);
        this.add(descendButton);
        this.add(startButton = new StartButton(this));
        this.add(midiDevicesDropDown);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == noteDropDown){
            if(noteDropDown.getSelectedIndex() != 0){
                selectedKey = Objects.requireNonNull(noteDropDown.getSelectedItem()).toString();
            }
        } else if (e.getSource() == midiDevicesDropDown) {
            // We always pass the selected name back up to MainFrame.
            midiDeviceName = Objects.requireNonNull(midiDevicesDropDown.getSelectedItem()).toString();
            frame.onDeviceSelected(midiDeviceName);
        }
        else if (e.getSource() == modeDropDown){
            if (modeDropDown.getSelectedIndex() != 0){
                selectedMode = Objects.requireNonNull(modeDropDown.getSelectedItem()).toString();
            }
        }
    }

    // ================ GETTERS =================
    public String getSelectedKey(){
        return selectedKey;
    }
    public String getSelectedMode(){
        return selectedMode;
    }

    public String getMidiDeviceName(){
        return midiDeviceName;
    }



    // ================ HELPERS ==================
    public void callStart(){
        if(frame.onStartButtonClicked()){
            startButton.setText("Stop");
            startButton.setForeground(Color.RED);
            startButton.setEnabled(true);
        }
    }

    // Clear all key highlights
    public void callStop(){
        frame.removeAllHighlightedColors();
    }



}
