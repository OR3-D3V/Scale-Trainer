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

* [X] Assign a MIDI note number to each key
* [X] Store keys in a structure (e.g., Map<note, KeyPanel>)
* [X] Create methods:

    * press()
    * release()
    * highlight()

---

### 3. MIDI Integration

* [X] Connect to MIDI input device
* [X] Listen for MIDI messages (noteOn, noteOff)
* [X] Map MIDI note → corresponding key
* [X] Trigger:

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
* [ ] Different colors:

    * pressed note (user input)
    * correct note (in scale)
    * incorrect note
* [ ] Clear/reset highlights

---

### 6. Control Panel (Top UI)

* [x] Dropdown: select root note (C, D, E…)
* [x] Dropdown: select scale type (Major, Minor…)
* [x] Dropdown: select MIDI input device
* [ ] Buttons:

    * [x] Start / Generate scale
    * Reveal scale
    * Reset

---

### 7. Practice Mode Logic

* [ ] Generate random scale
* [x] Wait for user to play notes
* [x] Track progress through the scale
* [x] Give feedback (correct / incorrect)
* [ ] Move to next scale

---

### 8. UX Improvements

* [ ] Add labels to keys (optional)
* [ ] Smooth color transitions (optional)
* [ ] Add instructions text
* [ ] Add scoring system (optional)

---

### 9. Code Structure / Cleanup

* [ ] Separate UI and logic (avoid mixing)
* [x] Create classes:

    * KeyboardPanel
    * KeyPanel
    * MIDIHandler
    * ScaleGenerator
* [ ] Avoid hardcoding values (use constants)

---

### 10. Testing & Debugging

* [ ] Test MIDI input reliability
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
