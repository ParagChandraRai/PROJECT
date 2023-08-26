package medicineshop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class Register extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5;
    JButton b1, b2;
    JTextField t1, t2, t3;
    JPasswordField p1;

    Register() {
        super("Register");

        l1 = new JLabel("Admin Register");
        l2 = new JLabel("Name");
        l3 = new JLabel("Email-Id");
        l4 = new JLabel("Username");
        l5 = new JLabel("Password");
        b1 = new JButton("Already have account? Sign in");
        b2 = new JButton("Register");

        l1.setFont(new Font("Times New Roman", Font.BOLD, 20));

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        p1 = new JPasswordField();

        l1.setBounds(200, 2, 200, 50);
        l2.setBounds(20, 80, 60, 70);
        t1.setBounds(90, 105, 150, 25);
        l3.setBounds(290, 80, 80, 70);
        t2.setBounds(350, 105, 150, 25);

        l4.setBounds(20, 150, 60, 70);
        t3.setBounds(90, 175, 150, 25);
        l5.setBounds(290, 150, 80, 70);
        p1.setBounds(350, 175, 150, 25);

        b1.setBounds(90, 300, 220, 30);
        b2.setBounds(340, 300, 100, 30);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(t1);
        add(t2);
        add(t3);
        add(l5);
        add(p1);
        add(b1);
        add(b2);

        setLayout(null);

        b1.addActionListener(this);
        b2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Already have an account
        {
            dispose();
            MyFrame f = new MyFrame();
            f.getContentPane().setBackground(Color.PINK);
            f.setSize(600, 400);
            f.setLocation(420, 220);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == b2) // Register
        {
            try {
                if ((t1.getText().length() == 0) || (t2.getText().length() == 0) || (t3.getText().length() == 0) || (new String(p1.getPassword()).length() == 0)) {
                    JOptionPane.showMessageDialog(null, "Text fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Connection connection = DatabaseConnection.getConnection();
                    String uname = t3.getText();

                    // Check if the username already exists in the database
                    PreparedStatement checkUsernameStmt = connection.prepareStatement("SELECT uname FROM login WHERE uname = ?");
                    checkUsernameStmt.setString(1, uname);
                    ResultSet resultSet = checkUsernameStmt.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Insert the new user into the login table
                    PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO login (name, emailid, uname, password) VALUES (?, ?, ?, ?)");
                    insertStmt.setString(1, t1.getText());
                    insertStmt.setString(2, t2.getText());
                    insertStmt.setString(3, t3.getText());
                    insertStmt.setString(4, new String(p1.getPassword()));
                    int rowsInserted = insertStmt.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "User registered successfully");
                        insertStmt.close();
                        connection.close();
                        dispose();
                        MyFrame f = new MyFrame();
                        f.getContentPane().setBackground(Color.PINK);
                        f.setSize(600, 400);
                        f.setLocation(420, 220);
                        f.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to register user", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    insertStmt.close();
                    checkUsernameStmt.close();
                    connection.close();
                }
            } catch (SQLException e1) {
                System.out.println("Error while connecting to the database: " + e1.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
