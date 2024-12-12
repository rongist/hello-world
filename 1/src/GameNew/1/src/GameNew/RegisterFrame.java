package GameNew;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public RegisterFrame() {
        setTitle("注册");
        setBounds(100, 100, 400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 50, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 100, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 100, 165, 25);
        panel.add(passwordText);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(150, 150, 80, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                        writer.write(username + "," + password);
                        writer.newLine();
                        JOptionPane.showMessageDialog(panel, "注册成功！");
                        dispose(); // 关闭注册窗体
                        new LoginFrame(); // 打开登录窗体
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "注册失败，请重试");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "用户名或密码不能为空");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterFrame();
    }
}