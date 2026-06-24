package Midi;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles MIDI device discovery + live device switching.
 * <p>
 * Think of this class as your MIDI "cable manager": it picks a device,
 * opens it, grabs its transmitter, and points that transmitter at your receiver.
 *
 * <p><b>Example flow</b></p>
 * <pre>{@code
 * MidiKeyboardConnection connection = new MidiKeyboardConnection();
 * String[] names = connection.getDevices();
 * connection.setReceiver(new MidiInputReceiver(session));
 * connection.setMidiDevice(names[0]);
 * }</pre>
 */

public class MidiKeyboardConnection {
    /** Legacy scanner from earlier CLI-based selection flow. */
    private static Scanner inp = new Scanner(System.in);

    /**
     * Current transmitter obtained from the selected MIDI device.
     */
    private Transmitter transmitter;

    /**
     * Receiver currently attached to the configured transmitter.
     */
    private Receiver receiver;

    private MidiDevice activeDevice;

    private int activeDeviceIndex;

    /**
     * Gets only MIDI devices that can actually transmit NOTE events.
     *
     * @return list of selectable MIDI input device names for the UI drop-down
     * @throws MidiUnavailableException if Java MIDI cannot inspect available devices
     */
    public String[] getDevices() throws MidiUnavailableException {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
        List<String> availableDevices = new ArrayList<>();
        if(devices.length == 0){
            return new String[]{"NO MIDI DEVICES AVAILABLE"};
        }
        int counter = 0;
        for(MidiDevice.Info curr: devices){
            // Keep only devices with transmitter support (input-capable devices).
            MidiDevice currentDevice = MidiSystem.getMidiDevice(devices[counter]);
            if(currentDevice.getMaxTransmitters() != 0){
                availableDevices.add(devices[counter].getName());
            }
            counter++;
        }
        return availableDevices.toArray(new String[0]);
    }

    /**
     * Switches active MIDI device using the selected device name from UI.
     * <p>
     * If another device is already open, it is closed first.
     *
     * <p><b>Example</b></p>
     * <pre>{@code
     * connection.setReceiver(new MidiInputReceiver(session));
     * connection.setMidiDevice("MPK mini 3");
     * }</pre>
     *
     * @param name device name selected in the combo box
     */
    public void setMidiDevice(String name){
        try{
            MidiDevice.Info[] allDev =MidiSystem.getMidiDeviceInfo();
            if( activeDevice != null && activeDevice.isOpen()){
                // Hot-swap order: close old device before opening the new one.
                activeDevice.close();
            }

            for (int i =0; i < allDev.length; i++){
                if(allDev[i].getName().equalsIgnoreCase(name) && MidiSystem.getMidiDevice(allDev[i]).getMaxTransmitters() != 0){
                    // Found the selected input-capable device.
                    activeDevice = MidiSystem.getMidiDevice(allDev[i]);
                    activeDevice.open();
                    transmitter = activeDevice.getTransmitter();
                    if(receiver != null){
                        // Receiver may be configured before device selection; bind it now.
                        transmitter.setReceiver(receiver);
                    }
                    break;
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sets the active transmitter manually.
     *
     * @param transmitter transmitter from a selected/open device
     */
    public void setTransmitter(Transmitter transmitter){
        this.transmitter = transmitter;
    }

    /**
     * Stores the receiver used for future device bindings.
     * <p>
     * If a device is already open in {@link #setMidiDevice(String)}, it gets attached there.
     *
     * @param receiver receiver that handles incoming MIDI events
     */
    public void setReceiver(Receiver receiver){
        this.receiver = receiver;
    }

    /**
     * Closes the current transmitter if one is active.
     */
    public void closeTransmitter(){
        if(this.transmitter != null){
            this.transmitter.close();
        }
    }

    /**
     * Fully disconnects MIDI input resources for the current session.
     * <p>
     * Safe to call multiple times. After this method returns, the active transmitter
     * is detached and closed, and the active device is closed if it was open.
     */
    public void disconnect() {
        if (transmitter != null) {
            // Detach receiver first to stop callbacks before closing resources.
            transmitter.setReceiver(null);
            transmitter.close();
            transmitter = null;
        }

        if (activeDevice != null && activeDevice.isOpen()) {
            activeDevice.close();
            activeDevice = null;
        }
    }
}
