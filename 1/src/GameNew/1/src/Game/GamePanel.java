package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel {
    private static final long serialVersionUID = 2L;
    private static final int sx = 50;// 左边距
    private static final int sy = 50;// 上边距
    private static final int w = 40; // 小方格宽高
    private static final int rw = 400; // 网格总宽高

    private int pj = 0, pi = 0; // 记录两个点击选中的按钮，第一个被点击的按钮坐标
    private int cc = 0;// 被点击选中的按钮个数
    private int[][] map;// 存放游戏数据的二维数组
    private boolean isEnd = false; // 游戏结束标志
    private JButton[][] btnMap; // 存放按钮的二维数组，与map对应
    private int score; // 记录分数
    private JButton restart; // 重新开始按钮
    private Timer timer; // 定时器
    private int timestamp; // 时间戳

    public GamePanel() {
        // 设置布局为不使用预设的布局
        setLayout(null);
    }

    /**
     * 开始游戏
     */
    public void start() {
        // 创建游戏数据地图
        map = MapTool.createMap();
        btnMap = new JButton[10][10];
        score = 0;
        timestamp = 0;
        isEnd = false;

        // 创建按钮，设置按钮属性，监听事件，并添加到按钮数组和窗体中
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                JButton btn = new JButton(map[i][j] + "");
                btn.setBounds(sx + (j * w) + 2, sy + (i * w) + 2, w - 2, w - 2);
                btn.setForeground(Color.RED);
                btn.setFont(new Font("Arial", 0, 30));
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createRaisedBevelBorder());
                btn.setFocusPainted(false);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 如果游戏结束，返回，不执行后面的代码
                        if (isEnd) {
                            return;
                        }
                        for (int i = 0; i < btnMap.length; i++) {
                            for (int j = 0; j < btnMap[i].length; j++) {
                                if (e.getSource().equals(btnMap[i][j])) {
                                    // 被选中的方格个数增加一个
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

        // 定时器，用来刷新时间
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timestamp++;
                repaint();
            }
        });
        timer.start();
    }

    /**
     * 判断是否游戏结束
     * 1、判断二维数组map中的所有元素是否均为0， 全部为0返回true表示游戏结束
     * 2、有不为0的，判断二维数组map中是否还有重复值，没有重复值返回true表示游戏结束
     * 否则返回false游戏继续
     *
     * @param map 二维数组，元素为int类型
     * @return
     */
    public boolean isEnd(int[][] map) {
        int count_0 = 0;
        int count = 0;
        HashSet<Integer> hashSet = new HashSet<Integer>();
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

    /**
     * 重载JPanel父类的paintComponent方法，用来绘制网格，以及游戏结束等
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // 获取分钟
            int min = timestamp / 60;
            // 获取秒数
            int sec = timestamp % 60;
            // 判断是否结束游戏
            if (isEnd) {
                // 设置画笔颜色为红色
                g.setColor(Color.RED);
                // 设置字体 微软雅黑 加粗 62号
                g.setFont(new Font("微软雅黑", 0, 62));
                // 绘制GAME OVER字样
                g.drawString("游戏结束", 60, 150);
                // 设置字体 微软雅黑 加粗 40号
                g.setFont(new Font("微软雅黑", 0, 40));
                // 绘制得分
                g.drawString("得分：" + score, 80, 230);
                // 绘制用时
                g.drawString("用时：" + String.format("%02d", min) + ":" + String.format("%02d", sec), 80, 280);
            } else {
                // 设置字体 微软雅黑 加粗 20号
                g.setFont(new Font("微软雅黑", Font.BOLD, 20));
                // 设置画笔颜色为黑色
                g.setColor(Color.BLACK);
                // 绘制时间显示框
                g.fillRect(100, 8, 80, 30);
                // 绘制分数显示框
                g.fillRect(400, 8, 50, 30);
                // 设置画笔颜色为红色
                g.setColor(Color.RED);
                // 绘制时间提示标签
                g.drawString("时间：", 50, 30);
                // 绘制时间
                g.drawString(String.format("%02d", min) + ":" + String.format("%02d", sec), 110, 30);
                // 绘制分数提示标签
                g.drawString("分数：", 350, 30);
                // 绘制分数
                g.drawString(String.format("%03d", score) + "", 405, 30);

                // 绘制外层矩形框
                g.drawRect(sx, sy, rw, rw);
                // 绘制水平10个，垂直10个方格。 即水平方向9条线，垂直方向9条线， 外围四周4条线已经画过了，不需要再画。 同时内部64个方格填写数字。
                for (int i = 1; i < 10; i++) {
                    // 绘制第i条竖直线
                    g.drawLine(sx + (i * w), sy, sx + (i * w), sy + rw);

                    // 绘制第i条水平线
                    g.drawLine(sx, sy + (i * w), sx + rw, sy + (i * w));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制按钮显示和隐藏
     *
     * @param i
     * @param j
     */
    private void drawButton(int i, int j) {
        if (map[i][j] != 0) {
            btnMap[i][j].setVisible(true);
        } else {
            btnMap[i][j].setVisible(false);
        }
    }

    /**
     * 比较两次点击的按钮对应的数字
     *
     * @param cj
     * @param ci
     */
    private void compare(int cj, int ci) {
        /**
         * 如果cc是1，表示当前一共选中了一个方格，用pj，pi来记住这个方格的位置； 否则，表示现在选中的这个方格要与之前选中的方案比较，决定是否要删除
         */
        if (cc == 1) {
            pj = cj;
            pi = ci;
            printMap(ci, cj);
            // 将所点击的方格背景设置为灰色
            btnMap[ci][cj].setBackground(Color.LIGHT_GRAY);
            drawButton(ci, cj);
        } else {// 此时，cc肯定是大于1的，表示要比较两个方格的值是否相同
            printMap(ci, cj);
            map = MapTool.removed(map, pi, pj, ci, cj);// 让MapTool类的remove方法去判断上一次所选的（pj,pi）处的方格值与本次选择的(cj,ci)处的方格值是否可以消掉
            // 处理第一个方格
            btnMap[ci][cj].setBackground(Color.WHITE);
            drawButton(ci, cj);
            // 处理第二个方格
            btnMap[pi][pj].setBackground(Color.WHITE);
            drawButton(pi, pj);
            cc = 0;// 将cc的值复位

            if (map[pi][pj] == map[ci][cj]) {
                score += 10;
            }
            isEnd = isEnd(map);
            // 游戏结束
            if (isEnd) {
                // 关闭定时器
                timer.stop();
                // 隐藏剩余的按钮
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (map[i][j] != 0) {
                            btnMap[i][j].setVisible(false);
                        }
                    }
                }
                // 创建添加重新开始按钮
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

    /**
     * 打印网格数据
     *
     * @param ci
     * @param cj
     */
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
                    System.out.print(
                            ((ci == i && cj == j || pi == i && pj == j) ? "[" + map[i][j] + "]" : " " + map[i][j] + " ")
                                    + "  ");
                }
            }
            System.out.println();
        }
    }
}