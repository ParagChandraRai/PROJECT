package medicineshop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

class MyFrameAd extends JFrame implements ActionListener {
    JLabel l3, img;
    JButton b1, b2, b3, b4, b5, b6;

    MyFrameAd() {
        super("Admin Section");
        setSize(900, 600);
        setLocation(20, 20);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(20, 95, 180, 420);

        JPanel admin = new JPanel();
        admin.setLayout(null);
        admin.setBounds(220, 90, 620, 430);

        img = new JLabel();
        ImageIcon imageIcon = new ImageIcon("store.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        img.setIcon(new ImageIcon(scaledImage));
        img.setBounds(10, -80, 600, 600);

        l3 = new JLabel("Medical Shop Management System");
        Font newFont = l3.getFont().deriveFont(20f);
        l3.setFont(newFont);

        b1 = new JButton("Add Medicine");
        b2 = new JButton("Medicine Report");
        b3 = new JButton("Add Sales");
        b4 = new JButton("Sales Report");
        b5 = new JButton("About Project");
        b6 = new JButton("Logout");

        b1.setBounds(30, 30, 130, 35);
        b2.setBounds(30, 95, 130, 35);
        b3.setBounds(30, 155, 130, 35);
        b4.setBounds(30, 215, 130, 35);
        b5.setBounds(30, 275, 130, 35);
        b6.setBounds(30, 335, 130, 35);

        l3.setBounds(250, 10, 400, 30);

        loginPanel.add(b1);
        loginPanel.add(b2);
        loginPanel.add(b3);
        loginPanel.add(b4);
        loginPanel.add(b5);
        loginPanel.add(b6);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        admin.add(img);

        TitledBorder loginPanelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                "Admin Menu",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 12),
                Color.red
        );

        loginPanel.setBorder(loginPanelTitledBorder);

        TitledBorder adminTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                "Admin Dashboard",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 12),
                Color.red
        );

        admin.setBorder(adminTitledBorder);

        add(l3);
        add(loginPanel);
        add(admin);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            // Open Add Medicine frame
            MyFrame2 f = new MyFrame2();
            f.getContentPane().setBackground(Color.PINK);
            f.setLocation(420, 220);
            f.setSize(690, 600);
            f.setVisible(true);
        } else if (e.getSource() == b2) {
            // Open Medicine Report frame
            MyFrame4 f = new MyFrame4();
            f.getContentPane().setBackground(Color.PINK);
            f.setLocation(420, 150);
            f.setSize(690, 600);
            f.setVisible(true);
        } else if (e.getSource() == b3) {
            // Open Add Sales frame
            MyFramesal f = new MyFramesal();
            f.getContentPane().setBackground(Color.PINK);
            f.setLocation(420, 150);
            f.setSize(780, 700);
            f.setVisible(true);
        } else if (e.getSource() == b4) {
            // Open Sales Report frame
            MyFrames f = new MyFrames();
            f.getContentPane().setBackground(Color.PINK);
            f.setLocation(420, 220);
            f.setSize(690, 600);
            f.setVisible(true);
        } else if (e.getSource() == b5) {
            // Open About Project frame
            MyFrameA f = new MyFrameA();
            f.getContentPane().setBackground(Color.PINK);
            f.setLocation(420, 220);
            f.setSize(700, 420);
            f.setVisible(true);
        } else if (e.getSource() == b6) {
            // Close the Admin Section frame
            dispose();
        }
    }
}
