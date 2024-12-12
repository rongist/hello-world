package GameNew;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public LeaderboardFrame() {
        setTitle("排行榜");
        setBounds(100, 100, 400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JTextArea leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<String> leaderboard = loadLeaderboard();
        StringBuilder leaderboardText = new StringBuilder("用户名\t分数\t排名\n");
        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboardText.append(leaderboard.get(i)).append("\n");
        }
        leaderboardArea.setText(leaderboardText.toString());

        setVisible(true);
    }

    private List<String> loadLeaderboard() {
        List<String> leaderboard = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                leaderboard.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public static void main(String[] args) {
        new LeaderboardFrame();
    }
}