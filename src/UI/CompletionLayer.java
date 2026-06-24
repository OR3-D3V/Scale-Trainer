package UI;

import javax.swing.*;
import java.awt.*;

public class CompletionLayer extends JPanel {
    public CompletionLayer(){
        this.setPreferredSize(new Dimension(190, 40));
        setBackground(Color.GRAY);

        // Labels
        JLabel title = new JLabel("SCORE");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        setVisible(true);
    }
}
