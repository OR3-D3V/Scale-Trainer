## CURRENT TASKS

* [X] Define scale patterns (Major, Minor, etc.)
* [X] Generate scale notes from root note
* [X] Store current active scale
* [X] Validate user input against scale

## MVP GOAL

Build a stable session control flow so practice can be repeated cleanly without restarting the app.

### Required MVP behaviors

1. Start/Stop Control
   - [X] User can press Start to begin a session.
   - [X] User can press Stop to end the active session immediately.
   - [X] Stopping disconnects MIDI and prevents further note validation until restarted.

2. Restart Flow (Stop -> Start)
   - [X] After pressing Stop, pressing Start creates a fresh session state.
   - [X] No stale progress carries over from the previous run.
   - [X] MIDI input reconnects and rebinds correctly for the restarted session.

3. Reselect Key/Mode Between Runs
   - [X] User can select a new key and/or mode after Stop.
   - [X] Next Start uses the newly selected key/mode.
   - [X] Generated target scale and input tracking reset to the new selection.
   - [X] Key colors reset when Stop is pressed.

4. Completion Feedback Screen/State
   - [ ] When a scale is fully completed, show a visible completion message in the UI.
   - [ ] Completion state should clearly indicate session end.
   - [ ] User should be able to start a new run after completion.

## STATUS NOTE

- Start/Stop/Restart flow is wired and working.
- Key color reset on Stop is implemented.
- Completion display is planned for a separate branch (feature/completion-screen).
- Startup-order issue (key/mode vs MIDI selection order) still needs broader testing.
## PROBLEM STATUS
1. I am not sure if the issue still persists in all scenarios.
2. The scale generation and play-check flow now works.
3. The session now stops when the scale is completed.
4. Still need broader testing for different startup orders and MIDI device switching.
