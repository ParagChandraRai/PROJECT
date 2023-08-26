package medicineshop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.TitledBorder;

class MyFrame extends JFrame implements ActionListener {
    public static String tableName;
    JLabel l1, l2, l3, img;
    JButton b1, b2;
    JTextField t1;
    JPasswordField p1;
    JLabel imageLabel;

    MyFrame() {
        super("Login");

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(250, 95, 280, 170);
        img = new JLabel();
        ImageIcon imageIcon = new ImageIcon("medicine.jpeg");
        Image image = imageIcon.getImage();

        Image scaledImage = image.getScaledInstance(200, 180, Image.SCALE_SMOOTH);
        img.setIcon(new ImageIcon(scaledImage));
        img.setBounds(30, 60, 200, 250);

        l3 = new JLabel("Medical Shop Management System");
        Font newFont = l3.getFont().deriveFont(20f);
        l3.setFont(newFont);
        l1 = new JLabel("Username:");
        l2 = new JLabel("Password:");
        b1 = new JButton("Login");
        b2 = new JButton("Sinup");
        t1 = new JTextField();
        p1 = new JPasswordField();

        l1.setBounds(20, 40, 100, 30);
        l2.setBounds(20, 90, 100, 30);
        l3.setBounds(80, 10, 400, 30);
        t1.setBounds(110, 43, 150, 23);
        p1.setBounds(110, 93, 150, 23);
        b1.setBounds(440, 300, 80, 35);
        b2.setBounds(340, 300, 80, 35);

        loginPanel.add(l1);
        loginPanel.add(l2);
        loginPanel.add(t1);
        loginPanel.add(p1);
        b1.addActionListener(this);
        b2.addActionListener(this);

        TitledBorder loginPanelTitledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.RED, 2),
            "Login",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("SansSerif", Font.BOLD, 12),
            Color.red
        );

        loginPanel.setBorder(loginPanelTitledBorder);

        add(l3);
        add(loginPanel);
        add(img);
        add(b1);
        add(b2);

        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Login
        {
            String uname = t1.getText();
            tableName = t1.getText();
            String password = new String(p1.getPassword());
            if ((t1.getText().length() == 0) || (new String(p1.getPassword()).length() == 0)) {
                JOptionPane.showMessageDialog(null, "TextField Can not be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement p = connection.prepareStatement("select uname ,password from login");
                ResultSet resultSet = p.executeQuery();
                while (resultSet.next()) {

                    String s1 = resultSet.getString(1);
                    String s2 = resultSet.getString(2);
                    if (s1.equals(uname) && s2.equals(password)) {
                        p.close();
                        connection.close();
                        dispose();

                        JOptionPane.showMessageDialog(null, "Login Successful");
                        MyFrameAd f = new MyFrameAd();
                        f.getContentPane().setBackground(Color.PINK);
                        f.setSize(900, 620);
                        f.setVisible(true);
                       
                    }
                }

                JOptionPane.showMessageDialog(null, "Invalid Username and Password", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e1) {
            }
        }
        if (e.getSource() == b2) // Signup
        {
            dispose();
            Register f = new Register();
            f.getContentPane().setBackground(Color.PINK);
            f.setSize(600, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return;
        }
    }
}

public class Login {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.getContentPane().setBackground(Color.PINK);
        f.setSize(600, 400);
        f.setLocation(420, 220);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
