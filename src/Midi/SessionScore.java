package Midi;

public class SessionScore {
    private int correctNotes;
    private int totalExpectedNotes;
    private int attempts;
    private boolean completed;
    private String progress;
    private double accuracy;


    public int getCorrectNotes() {
        return correctNotes;
    }

    public void incrementCorrectNotes(int correctNotes) {
        this.correctNotes += correctNotes;
        this.attempts += correctNotes;
    }

    public int getTotalExpectedNotes() {
        return totalExpectedNotes;
    }

    public void setTotalExpectedNotes(int totalExpectedNotes) {
        this.totalExpectedNotes = totalExpectedNotes;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts(int attempts) {
        this.attempts += attempts;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
