# Scale Trainer

Scale Trainer is a Java desktop app that helps people learn piano scales with real-time MIDI input and visual keyboard feedback.

It is built for both:
- beginners who want structured scale practice
- advanced players who want fast, repeatable scale drills with immediate correctness feedback

## What the app does

- Lets you choose:
  - a root key (A, A#, B, C, C#, D, D#, E, F, F#, G, G#)
  - a scale mode (Major or Minor)
  - a MIDI input device
- Starts a scale training session from the selected key and mode
- Listens to your MIDI keyboard input
- Checks each note against the expected next note in the generated scale
- Colors notes on the on-screen keyboard:
  - valid note -> green
  - invalid note -> red
- Plays note audio through Java Synthesizer while receiving MIDI input
- Ends the session when the full 8-note scale is completed (root to octave)

## Current implemented feature set

### MIDI
- MIDI device discovery and selection
- Live MIDI NOTE_ON / NOTE_OFF handling through a receiver
- Device transmitter wiring to app receiver

### Scale logic
- Major and Minor 8-note scale generation
- Session progress tracking through expected sequence
- Completion state detection when the scale is fully completed

### UI
- Main window with:
  - control bar (key, mode, tempo UI, start, MIDI device picker)
  - piano keyboard visualization
- Dynamic white and black key rendering
- Per-note visual press/release feedback with validity coloring

## Project structure

- `src/Main.java` - app entry point and object wiring
- `src/Midi/` - MIDI input, note utilities, and session logic
  - `MidiKeyboardConnection.java`
  - `MidiInputReceiver.java`
  - `ScaleSession.java`
  - `NoteUtil.java`
- `src/UI/` - Swing UI
  - `MainFrame.java`
  - `ControlBarPanel.java`
  - `KeyboardUI/` (white/black key rendering and mapping)
  - `TempoComp/` (tempo slider and buttons)

## Who this is for

Scale Trainer is designed to support:
- learners building note familiarity and finger confidence
- intermediate players improving scale consistency
- advanced players doing focused warmups and accuracy checks

## Notes

- The app currently supports Major and Minor scale generation.
- Tempo controls and some advanced practice modes are present in UI foundations but not yet fully wired into session behavior.
