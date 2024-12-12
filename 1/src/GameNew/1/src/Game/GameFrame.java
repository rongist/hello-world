package Game;

import javax.swing.*;


public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    GamePanel panel;

    /**
     * GameFrame构造方法
     */
    public GameFrame() {
        // 设置窗体标题
        setTitle("数字连连消");
        // 设置窗体位置和大小
        setBounds(100, 100, 515, 520);
        // 设置窗体不能改变大小
        setResizable(false);
        // 设置窗体居中显示
        setLocationRelativeTo(null);
        // 设置窗体关闭即退出
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel();
        add(panel);
        // 最后显示窗体
        setVisible(true);
    }

    /**
     * 启动游戏
     */
    public void start() {
        panel.start();
    }
}
