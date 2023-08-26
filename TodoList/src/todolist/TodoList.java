package todolist;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;
import java.sql.*;

class MyFrame extends JFrame implements ActionListener {
    public static String tableName;
    JLabel l1, l2;
    JButton b1, b2, b3;
    JTextField t1;
    JPasswordField p1;
    JLabel imageLabel;

    MyFrame() {
        super("Login");

        l1 = new JLabel("Username:");
        l2 = new JLabel("Password:");
        b1 = new JButton("Login");
        b2 = new JButton("Signup");
        b3 = new JButton("Reset");
        t1 = new JTextField();
        p1 = new JPasswordField();

        l1.setBounds(20, 40, 100, 30);
        l2.setBounds(20, 90, 100, 30);
        t1.setBounds(110, 43, 150, 23);
        p1.setBounds(110, 93, 150, 23);

        b1.setBounds(20, 200, 80, 35);
        b2.setBounds(120, 200, 80, 35);
        b3.setBounds(220, 200, 80, 35);

        add(l1);
        add(l2);
        add(t1);
        add(p1);
        add(b1);
        add(b2);
        add(b3);

        setLayout(null);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Login
        {
            String uname = t1.getText();
            tableName = t1.getText(); // Static variable access in the package
            String password = new String(p1.getPassword());
            try {
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement p = connection.prepareStatement("select uname ,password from login");
                ResultSet resultSet = p.executeQuery();
                while (resultSet.next()) {
                    String s1 = resultSet.getString(1);
                    String s2 = resultSet.getString(2);
                    if (s1.equals(uname) && s2.equals(password)) {
                        dispose();
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        task f = new task();
                        f.getContentPane().setBackground(new Color(173, 216, 230));
                        f.setSize(600, 500);
                        f.setVisible(true);
                        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "Invalid Username and Password", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == b2) // Signup
        {
            dispose();
            Register f = new Register();
            f.getContentPane().setBackground(new Color(173, 216, 230));
            f.setSize(600, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        if (e.getSource() == b3) // Reset
        {
            t1.setText("");
            p1.setText("");
        }
    }
}

public class TodoList {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.getContentPane().setBackground(new Color(173, 216, 230));
        f.setSize(400, 400);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
