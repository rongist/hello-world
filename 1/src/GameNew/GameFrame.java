package GameNew;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    GamePanel panel;

    // 构造方法，初始化游戏窗口
    public GameFrame() {
        setTitle("数字消消乐"); // 设置窗体标题
        setBounds(100, 100, 515, 520); // 设置窗体位置和大小
        setResizable(false); // 设置窗体不能改变大小
        setLocationRelativeTo(null); // 设置窗体居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体关闭即退出
        panel = new GamePanel(); // 创建游戏面板
        add(panel); // 将游戏面板添加到窗体中
        setJMenuBar(createMenuBar()); // 创建并设置菜单栏
        setVisible(true); // 显示窗体
    }

    // 创建菜单栏
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // 创建菜单栏
        JMenu fileMenu = new JMenu("文件"); // 创建文件菜单

        // 创建新建菜单项并添加监听器
        JMenuItem newItem = new JMenuItem("新建");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.start(); // 新建游戏
            }
        });

        // 创建保存菜单项并添加监听器
        JMenuItem saveItem = new JMenuItem("保存");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame(); // 保存游戏
            }
        });

        // 创建恢复菜单项并添加监听器
        JMenuItem loadItem = new JMenuItem("恢复");
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame(); // 恢复游戏
            }
        });

        // 创建重新开始菜单项并添加监听器
        JMenuItem restartItem = new JMenuItem("重新开始");
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.start(); // 重新开始游戏
            }
        });

        // 将菜单项添加到文件菜单
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(restartItem);
        menuBar.add(fileMenu); // 将文件菜单添加到菜单栏
        return menuBar; // 返回菜单栏
    }

    // 保存游戏状态
    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_save.dat"))) {
            oos.writeObject(panel.getMap()); // 保存地图数据
            oos.writeInt(panel.getScore()); // 保存分数
            oos.writeInt(panel.getTimestamp()); // 保存时间戳
            JOptionPane.showMessageDialog(this, "游戏进度已保存"); // 显示保存成功消息
        } catch (IOException e) {
            e.printStackTrace(); // 打印异常信息
        }
    }

    // 恢复游戏状态
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.dat"))) {
            int[][] savedMap = (int[][]) ois.readObject(); // 读取地图数据
            int savedScore = ois.readInt(); // 读取分数
            int savedTimestamp = ois.readInt(); // 读取时间戳

            panel.setMap(savedMap); // 设置地图数据
            panel.setScore(savedScore); // 设置分数
            panel.setTimestamp(savedTimestamp); // 设置时间戳
            panel.refreshPanel(); // 刷新面板
            JOptionPane.showMessageDialog(this, "游戏进度已恢复"); // 显示恢复成功消息
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // 打印异常信息
            JOptionPane.showMessageDialog(this, "无法恢复游戏进度"); // 显示恢复失败消息
        }
    }

    // 启动游戏
    public void start() {
        panel.start(); // 启动游戏
    }


}