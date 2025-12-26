import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
/*

    The MidiSystem is a room, it tell's you what devices exist in the program.
    It is Static, and it only opens the device, lists the device and also attach a receiver

    The MidiDevice is the device itself, it has details on the midi device

    In hierarchy the Midi System comes first, then the Midi Device.
 */
public class MidiKeyboardConnection {
    private static Scanner inp = new Scanner(System.in);
    private Transmitter transmitter;
    private Receiver receiver;

    //This Methods Gets All The Devices And Allows The User To Select A Midi Device.
    public static MidiDevice getDevices() throws MidiUnavailableException {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiDevice> availableDevices = new ArrayList<>();
        //        System.out.println(Arrays.toString(devices));

        // Fix the method loop that gets the devices.
        // This should now get only devices with transmitters.
        System.out.println("Select Your Midi Device");
        int counter = 1;
        for(int i = 0; i < devices.length; i++){
            MidiDevice currentDevice = MidiSystem.getMidiDevice(devices[i]);
            if(currentDevice.getMaxTransmitters() != 0){
                System.out.println(counter + " Device Name: " + devices[i].getName());
                counter++;
                availableDevices.add(currentDevice);
            }
        }
            // Get User Input
        while (true) {
            System.out.print("Select The Device");
            // 1) Validate it's a number BEFORE reading it
            if (!inp.hasNextInt()) {
                System.out.println("Input is not a number.");
                inp.next(); // consume the bad token so we don't get stuck
                continue;
            }

            // 2) Now it's safe to read
            int deviceToSelect = inp.nextInt();

            // 3) Validate range BEFORE indexing
            if (deviceToSelect < 1 || deviceToSelect > devices.length) {
                System.out.println("Input is out of range.");
                continue;
            }

            // 4) Try to obtain the actual MidiDevice
            try {
                MidiDevice device = MidiSystem.getMidiDevice(availableDevices.get(deviceToSelect-1).getDeviceInfo());
                System.out.println("Selected: " + availableDevices.get(deviceToSelect-1).getDeviceInfo() + " Transmitters: " + device.getMaxTransmitters());
                return device;
            } catch (MidiUnavailableException e) {
                System.out.println("That device is not available. Pick another or Make Sure It Is Connected.");
            }
        }

    }
    public void setTransmitter(Transmitter transmitter){
        this.transmitter = transmitter;
    }
    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
        transmitter.setReceiver(receiver);
    }
    public void closeTransmitter(){
        this.transmitter.close();
    }
}
