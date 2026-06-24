**Scale Trainer – Development Task List**

---

### 1. Keyboard UI (Foundation)

* [x] Generate white keys
* [x] Generate black keys
* [x] Fix positioning and layering
* [x] Add borders/styling for keys
* [x] Support multiple octaves (dynamic generation)
* [x] Resize keyboard based on window size (optional)

---

### 2. Key Data Mapping

* [x] Assign a MIDI note number to each key
* [x] Store keys in a structure (e.g., Map<note, KeyPanel>)
* [x] Create methods:

    * press()
    * release()
    * highlight()

---

### 3. MIDI Integration

* [x] Connect to MIDI input device
* [x] Listen for MIDI messages (noteOn, noteOff)
* [x] Map MIDI note → corresponding key
* [x] Trigger:

    * key.press() on noteOn
    * key.release() on noteOff

---

### 4. Scale Logic

* [x] Define scale patterns (Major, Minor)
* [x] Generate scale notes from root note
* [x] Store current active scale
* [x] Validate user input against scale

---

### 5. Visual Feedback

* [x] Highlight scale notes on keyboard through press/release feedback
* [x] Different colors:

    * pressed note (yellow while held)
    * correct note (green on release)
    * incorrect note (red on release)
* [x] Clear/reset highlights on Stop

---

### 6. Control Panel (Top UI)

* [x] Dropdown: select root note (C, D, E…)
* [x] Dropdown: select scale type (Major, Minor…)
* [x] Dropdown: select MIDI input device
* [x] Button: Start / Generate scale
* [x] Button: Stop (toggles with Start)
* [ ] Button: Reveal scale
* [ ] Button: Reset

---

### 7. Practice Mode Logic

* [ ] Generate random scale
* [x] Wait for user to play notes
* [x] Track progress through the scale
* [x] Give feedback (correct / incorrect)
* [x] Session stops when scale is completed
* [x] Session resets cleanly on Stop → Start
* [ ] Move to next scale automatically

---

### 8. UX Improvements

* [ ] Show completion feedback screen (planned: feature/completion-screen)
* [ ] Add labels to keys (optional)
* [ ] Smooth color transitions (optional)
* [ ] Add instructions text
* [ ] Add scoring system (optional)

---

### 9. Code Structure / Cleanup

* [ ] Separate UI and logic further (reduce coupling)
* [x] Create classes:

    * KeyboardPanel
    * Key interface + WhiteKey / BlackKey
    * MidiKeyboardConnection
    * ScaleSession
* [ ] Avoid hardcoding values (use constants)

---

### 10. Testing & Debugging

* [ ] Test MIDI input reliability across devices
* [ ] Test key highlighting accuracy
* [ ] Test edge cases (fast input, repeated notes)
* [ ] Ensure no UI freezing (thread handling)
* [ ] Re-test startup order issue (key/mode before MIDI vs MIDI before key/mode)

---

### 11. Stretch Goals (Optional)

* [ ] Record user performance
* [ ] Add chord training mode
* [ ] Add different instruments (MIDI output)
* [ ] Save user progress
