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

* [ ] Connect to MIDI input device
* [ ] Listen for MIDI messages (noteOn, noteOff)
* [ ] Map MIDI note → corresponding key
* [ ] Trigger:

    * key.press() on noteOn
    * key.release() on noteOff

---

### 4. Scale Logic

* [ ] Define scale patterns (Major, Minor, etc.)
* [ ] Generate scale notes from root note
* [ ] Store current active scale
* [ ] Validate user input against scale

---

### 5. Visual Feedback

* [ ] Highlight scale notes on keyboard
* [ ] Different colors:

    * pressed note (user input)
    * correct note (in scale)
    * incorrect note
* [ ] Clear/reset highlights

---

### 6. Control Panel (Top UI)

* [ ] Dropdown: select root note (C, D, E…)
* [ ] Dropdown: select scale type (Major, Minor…)
* [ ] Buttons:

    * Start / Generate scale
    * Reveal scale
    * Reset

---

### 7. Practice Mode Logic

* [ ] Generate random scale
* [ ] Wait for user to play notes
* [ ] Track progress through the scale
* [ ] Give feedback (correct / incorrect)
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
* [ ] Create classes:

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

---

### 11. Stretch Goals (Optional)

* [ ] Record user performance
* [ ] Add chord training mode
* [ ] Add different instruments (MIDI output)
* [ ] Save user progress
