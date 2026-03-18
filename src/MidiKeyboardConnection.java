import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Manages interactive MIDI input device selection and message routing.
 * <p>
 * This class works with Java Sound MIDI APIs in two stages:
 * first selecting a {@link MidiDevice} that can produce outgoing MIDI messages
 * through a {@link Transmitter}, then wiring that transmitter to an application
 * {@link Receiver} implementation.
 * <p>
 * Typical flow:
 * <ol>
 *   <li>Call {@link #getDevices()} to choose an available MIDI input device.</li>
 *   <li>Obtain the device transmitter externally and call {@link #setTransmitter(Transmitter)}.</li>
 *   <li>Provide the target receiver via {@link #setReceiver(Receiver)}.</li>
 *   <li>Call {@link #closeTransmitter()} during shutdown.</li>
 * </ol>
 */
public class MidiKeyboardConnection {
    /**
     * Shared console scanner used to read numeric device selections from standard input.
     */
    private static Scanner inp = new Scanner(System.in);

    /**
     * Current transmitter obtained from the selected MIDI device.
     */
    private Transmitter transmitter;

    /**
     * Receiver currently attached to the configured transmitter.
     */
    private Receiver receiver;

    /**
     * Lists all MIDI devices that can provide a transmitter, then blocks until the user
     * selects one from the console.
     * <p>
     * The method repeatedly prompts until a numeric selection is entered and the selected
     * device can be acquired from {@link MidiSystem}. Devices with no transmitter support
     * are excluded from the displayed list.
     *
     * <p><b>Example: interactive device selection</b></p>
     * <pre>{@code
     * MidiKeyboardConnection connection = new MidiKeyboardConnection();
     * MidiDevice device = MidiKeyboardConnection.getDevices();
     * device.open();
     * }</pre>
     *
     * <p><b>Example: full wiring flow</b></p>
     * <pre>{@code
     * MidiKeyboardConnection connection = new MidiKeyboardConnection();
     * MidiDevice device = MidiKeyboardConnection.getDevices();
     * device.open();
     *
     * connection.setTransmitter(device.getTransmitter());
     * connection.setReceiver(new MidiInputReceiver(scaleSession));
     * }</pre>
     *
     * @return the selected {@link MidiDevice} instance
     * @throws MidiUnavailableException if device information cannot be queried before
     *                                  interactive selection starts
     * @implNote Input range validation compares against the total number of discovered
     * devices, while selection is performed against the filtered list of transmitter-capable
     * devices.
     */
    public static MidiDevice getDevices() throws MidiUnavailableException {
        // devices is all the information of all the MidiDevices
        // Midisytem.getMidiDeviceInfo returns an array of all MidiDevice Information
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        // Only devices that can transmit MIDI input are selectable by the user.
        ArrayList<MidiDevice> availableDevices = new ArrayList<>();

        //        System.out.println(Arrays.toString(devices));

        // Print a user-facing numbered list of transmitter-capable devices.
        System.out.println("Select Your Midi Device");
        int counter = 1;
        for(int i = 0; i < devices.length; i++){
            //Get's a midi device that is returned when we pass the information of the device
            MidiDevice currentDevice = MidiSystem.getMidiDevice(devices[i]);

            // Only add the device to the availableDevices if it has more than one transmitter
            if(currentDevice.getMaxTransmitters() != 0){
                System.out.println(counter + " Device Name: " + devices[i].getName());
                counter++;
                availableDevices.add(currentDevice);
            }
        }

            // Read and validate selection until a usable device is returned.
        while (true) {
            System.out.print("Select The Device");
            // Ensure the next token is numeric before calling nextInt().
            if (!inp.hasNextInt()) {
                System.out.println("Input is not a number.");
                inp.next(); // consume the invalid token to avoid an infinite prompt loop
                continue;
            }

            // Convert the user's 1-based menu input into an integer selection.
            int deviceToSelect = inp.nextInt();

            // Reject values outside the menu range before accessing the device list.
            if (deviceToSelect < 1 || deviceToSelect > devices.length) {
                System.out.println("Input is out of range.");
                continue;
            }

            // Retrieve the chosen device from the filtered list and return it if available.
            try {
                MidiDevice device = MidiSystem.getMidiDevice(availableDevices.get(deviceToSelect-1).getDeviceInfo());
                System.out.println("Selected: " + availableDevices.get(deviceToSelect-1).getDeviceInfo() + " Transmitters: " + device.getMaxTransmitters());
                return device;
            } catch (MidiUnavailableException e) {
                System.out.println("That device is not available. Pick another or Make Sure It Is Connected.");
            }
        }

    }

    /**
     * Stores the transmitter that will later forward messages to a receiver.
     *
     * @param transmitter transmitter retrieved from the chosen MIDI device
     */
    public void setTransmitter(Transmitter transmitter){
        this.transmitter = transmitter;
    }

    /**
     * Stores the receiver and immediately wires it to the currently configured transmitter.
     * <p>
     * This method assumes {@link #setTransmitter(Transmitter)} has already been called.
     *
     * @param receiver receiver that should consume incoming MIDI messages
     */
    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
        transmitter.setReceiver(receiver);
    }

    /**
     * Closes the currently stored transmitter.
     * <p>
     * Call this when MIDI input routing is no longer needed to release transmitter resources.
     */
    public void closeTransmitter(){
        this.transmitter.close();
    }
}
