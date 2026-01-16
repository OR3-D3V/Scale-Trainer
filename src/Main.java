import UI.MainFrame;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) throws MidiUnavailableException {
        //All Instances Of Required Objects

        //Frame
        MainFrame frame = new MainFrame();

        //Core
        MidiKeyboardConnection midiKeyboardConnection = new MidiKeyboardConnection();
        MidiDevice currentMidiDevice = MidiKeyboardConnection.getDevices();
        ScaleSession scaleSession = new ScaleSession(currentMidiDevice, midiKeyboardConnection);
        Scanner scanner = new Scanner(System.in);
        MidiInputReceiver inputReceiver = new MidiInputReceiver(scaleSession);

        //Open The Device.
        currentMidiDevice.open();

//      Get The Transmitter From The Midi Device.
//      Transmitter is basically what your midi keyboard uses to send messages.
        midiKeyboardConnection.setTransmitter(currentMidiDevice.getTransmitter());


//      Ask user to choose a scale and the key.
        System.out.println("Enter a Key");
        String key = scanner.nextLine();
        System.out.println("Enter the Interval (Minor / Major)");
        String interval = scanner.nextLine();
        scaleSession.generateMajorScale(key, interval);

//      The receiver is made by me. It gets a midi message and a time stamp, everytime I click on a key.
//        You have to tell the transmitter from the device to use the receiver you used. It will automatically know what to do.
        midiKeyboardConnection.setReceiver(inputReceiver);

    }
}

