# 🎹 Java MIDI Synthesizer & Channels — Notes

## 🧠 Big Picture

```text
MIDI Keyboard → sends instructions (no sound)
Synthesizer → generates sound
Channel → plays the sound
```

* Your keyboard does **NOT** produce sound
* It sends messages like: `"Play note 60"`
* The **synthesizer** turns that into audio

---

## 🔊 What is a Synthesizer?

Think of it as a **virtual instrument engine**

```text
Synthesizer = the piano (sound generator)
```

You must:

* Get it
* Open it

Otherwise:

```text
No sound ❌
```

---

## 🎯 What is a Channel?

> A **channel is an instrument lane inside the synthesizer**

### 🎵 Analogy (Band)

```text
Channel 0 → Piano 🎹
Channel 1 → Guitar 🎸
Channel 2 → Drums 🥁
```

Each channel:

* Can play different notes
* Can have a different instrument

---

## 🧠 In Java

```java
channel = synth.getChannels()[0];
```

This means:

```text
"Use channel 0 (usually a piano)"
```

---

## 🔥 Why Channels Exist

MIDI supports:

```text
16 channels (0–15)
```

Each one can:

* Play independently
* Use different instruments
* Play at the same time

---

## 🎹 Playing Notes

### Start a note

```java
channel.noteOn(note, velocity);
```

### Stop a note

```java
channel.noteOff(note);
```

---

## 🧠 What is a Note?

MIDI notes are numbers:

```text
60 = Middle C
61 = C#
62 = D
...
```

---

## 🔊 What is Velocity?

```text
Range: 0–127
```

* Low → soft sound
* High → loud sound

---

## 🔄 What Happens When You Press a Key

```text
Press key
   ↓
MIDI keyboard sends signal
   ↓
Receiver gets it
   ↓
Session processes it
   ↓
channel.noteOn(note, velocity)
   ↓
Synthesizer plays sound 🔊
```

---

## 🧠 Mental Model

```text
Synthesizer = sound engine
Channel = instrument/player
noteOn = press key
noteOff = release key
```

---

## ⚠️ Important Rules

### 1. Must open the synthesizer

```java
synth.open();
```

---

### 2. Only create ONE synthesizer

```text
Do NOT recreate it for every note ❌
```

---

### 3. Always pair noteOn with noteOff

```text
Missing noteOff → sound never stops 😭
```

---

## 🧩 Your App Structure

```text
MIDI Keyboard
   ↓
MidiInputReceiver
   ↓
ScaleSession
   ↓
Synthesizer (channel)
   ↓
Sound 🔊
```

---

## 👍 Summary

* Synthesizer = produces sound
* Channel = plays notes
* noteOn = start sound
* noteOff = stop sound

---

## 🚀 Next Steps (Optional)

* Change instrument (piano → guitar)
* Use multiple channels
* Use real velocity from MIDI input
* Add sustain effects
