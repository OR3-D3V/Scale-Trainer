## Main 
- Change the UX so it does not detect till you press start.
- When the user presses start it would start grading/checking if it the input is correct

## Scale Session
- Generates A Scale
- Detects Input
- Makes Sure Input Is Correct When The User Clicks
- When The User Is Done Say What notes where correct.


## Midi Input Receiver.
- This should call a function from the Scale Session, everytime a key is clicked.
- To test i want something like when i click pass it to the Scale Session class, let it know something happend.


## Fine Tuning
- Midi Keyboard (Done)
- Scale Session
- Midi Receiver


## Class Roles
- ### Main 
  - Instantiate Classes
- ### MidiInputReceiver
  - Receives Midi Signal and transfers input to the Scale Session Class
- ### MidiKeyboardConnection
  - Has a Transmitter that connects to the MidiInputReceiver Class
  - Gets all midi devices
- ### NoteUtil
  - Does things like convert note value to letters and in reverse.
- ### Scale Session
  - Main Activity
  - Generates Scales
  - Checks If The Scale Is Correct
  - Uses Input and Compare/Checks Actual Scale