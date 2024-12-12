package GameNew;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 2L;
    private static final int sx = 50;
    private static final int sy = 50;
    private static final int w = 40;
    private static final int rw = 400;

    private int pj = 0, pi = 0;
    private int cc = 0;
    private int[][] map;
    private boolean isEnd = false;
    private JButton[][] btnMap;
    private int score;
    private JButton restart;
    private Timer timer;
    private int timestamp;

    public GamePanel() {
        this.score = score;
        setLayout(null);
    }

    public void start() {
        this.removeAll();
        map = MapTool.createMap();
        btnMap = new JButton[10][10];
        score = 0;
        timestamp = 0;
        isEnd = false;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                JButton btn = new JButton(map[i][j] + "");
                btn.setBounds(sx + (j * w) + 2, sy + (i * w) + 2, w - 2, w - 2);
                btn.setForeground(Color.RED);
                btn.setFont(new Font("Arial", 0, 30));
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createRaisedBevelBorder());
                btn.setFocusPainted(false);
                btn.setVisible(true);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isEnd) {
                            return;
                        }
                        for (int i = 0; i < btnMap.length; i++) {
                            for (int j = 0; j < btnMap[i].length; j++) {
                                if (e.getSource().equals(btnMap[i][j])) {
                                    cc++;
                                    compare(j, i);
                                }
                            }
                        }
                    }
                });
                btnMap[i][j] = btn;
                this.add(btn);
            }
        }

        if (restart != null) {
            restart.setVisible(false);
            this.remove(restart);
            restart = null;
        }
        repaint();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timestamp++;
                repaint();
            }
        });
        timer.start();
    }

    public boolean isEnd(int[][] map) {
        int count_0 = 0;
        int count = 0;
        HashSet<Integer> hashSet = new HashSet<>();
        for (int[] ms : map) {
            for (int m : ms) {
                count++;
                if (m != 0) {
                    hashSet.add(m);
                } else {
                    count_0++;
                }
            }
        }

        for (int[] ms : map) {
            for (int m : ms) {
                if (m != 0) {
                    if (hashSet.size() + count_0 == count) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            int min = timestamp / 60;
            int sec = timestamp % 60;
            if (isEnd) {
                g.setColor(Color.RED);
                g.setFont(new Font("微软雅黑", 0, 62));
                g.drawString("游戏结束", 60, 150);
                g.setFont(new Font("微软雅黑", 0, 40));
                g.drawString("得分：" + score, 80, 230);
                g.drawString("用时：" + String.format("%02d", min) + ":" + String.format("%02d", sec), 80, 280);
            } else {
                g.setFont(new Font("微软雅黑", Font.BOLD, 20));
                g.setColor(Color.BLACK);
                g.fillRect(100, 8, 80, 30);
                g.fillRect(400, 8, 50, 30);
                g.setColor(Color.RED);
                g.drawString("时间：", 50, 30);
                g.drawString(String.format("%02d", min) + ":" + String.format("%02d", sec), 110, 30);
                g.drawString("分数：", 350, 30);
                g.drawString(String.format("%03d", score) + "", 405, 30);

                g.drawRect(sx, sy, rw, rw);
                for (int i = 1; i < 10; i++) {
                    g.drawLine(sx + (i * w), sy, sx + (i * w), sy + rw);
                    g.drawLine(sx, sy + (i * w), sx + rw, sy + (i * w));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawButton(int i, int j) {
        if (map[i][j] != 0) {
            btnMap[i][j].setVisible(true);
        } else {
            btnMap[i][j].setVisible(false);
        }
    }

    private void compare(int cj, int ci) {
        if (cc == 1) {
            pj = cj;
            pi = ci;
            printMap(ci, cj);
            btnMap[ci][cj].setBackground(Color.LIGHT_GRAY);
            drawButton(ci, cj);
        } else {
            printMap(ci, cj);
            map = MapTool.removed(map, pi, pj, ci, cj);
            btnMap[ci][cj].setBackground(Color.WHITE);
            drawButton(ci, cj);
            btnMap[pi][pj].setBackground(Color.WHITE);
            drawButton(pi, pj);
            cc = 0;

            if (map[pi][pj] == map[ci][cj]) {
                score += 10;
            }
            isEnd = isEnd(map);
            if (isEnd) {
                timer.stop();
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (map[i][j] != 0) {
                            btnMap[i][j].setVisible(false);
                        }
                    }
                }
                restart = new JButton("重新开始");
                restart.setBackground(Color.WHITE);
                restart.setBounds(180, 350, 120, 40);
                restart.setBorder(BorderFactory.createRaisedBevelBorder());
                restart.setFocusPainted(false);
                restart.setForeground(Color.RED);
                restart.setFont(new Font("微软雅黑", 0, 20));
                restart.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        start();
                    }
                });
                this.add(restart);
                repaint();
            }
        }
        repaint();
    }

    private void printMap(int ci, int cj) {
        if (ci == pi && cj == pj) {
            System.out.println("ci:" + ci + ", cj:" + cj);
        } else {
            System.out.println("ci:" + ci + ", cj:" + cj + ", pi:" + pi + ", pj:" + pj);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (ci == pi && cj == pj) {
                    System.out.print(((ci == i && cj == j) ? "[" + map[i][j] + "]" : " " + map[i][j] + " ") + "  ");
                } else {
                    System.out.print(((ci == i && cj == j || pi == i && pj == j) ? "[" + map[i][j] + "]" : " " + map[i][j] + " ") + "  ");
                }
            }
            System.out.println();
        }
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void refreshPanel() {
        this.removeAll();
        btnMap = new JButton[10][10];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                JButton btn = new JButton(map[i][j] + "");
                btn.setBounds(sx + (j * w) + 2, sy + (i * w) + 2, w - 2, w - 2);
                btn.setForeground(Color.RED);
                btn.setFont(new Font("Arial", 0, 30));
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createRaisedBevelBorder());
                btn.setFocusPainted(false);
                btn.setVisible(map[i][j] != 0);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isEnd) {
                            return;
                        }
                        for (int i = 0; i < btnMap.length; i++) {
                            for (int j = 0; j < btnMap[i].length; j++) {
                                if (e.getSource().equals(btnMap[i][j])) {
                                    cc++;
                                    compare(j, i);
                                }
                            }
                        }
                    }
                });
                btnMap[i][j] = btn;
                this.add(btn);
            }
        }
        repaint();
    }

    private void saveScore(String username, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true))) {
            writer.write(username + "\t" + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// 在游戏结束或保存游戏时调用 saveScore 方法
    if (isEnd) {
        timer.stop();
        saveScore(username, score); // 保存分数到排行榜
        // 其他逻辑...
    }

    public int[][] getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public int getTimestamp() {
        return timestamp;
    }
}