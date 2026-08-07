package UI;

import javax.swing.*;
import java.awt.*;

public class CompletionLayer extends JPanel {
    private double accuracy;

    public CompletionLayer(){

        this.setPreferredSize(new Dimension(190, 40));
        setBackground(Color.GRAY);

        // Labels
        JLabel title = new JLabel("<html>"+
                "SCORE <br>" +
                "</html>");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);
        setVisible(false);
    }

    // Currently only display if it is completed.
    public void setCompleted(int notesCorrect, int attempts){
        double accuracy = ((double) notesCorrect / attempts) * 100;
        JLabel stats = new JLabel(
                "<html>" +
                        "Notes Correct: " + notesCorrect + "/8<br>" +
                        "Attempts: " + attempts + "<br>" +
                        String.format("Accuracy: %.1f%%", accuracy) +
                        "</html>"
        );
        add(stats);
        setVisible(true);
        revalidate();
        repaint();
    }
}
