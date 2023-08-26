package todolist;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//import org.jdatepicker.JDatePicker;

import static todolist.MyFrame.tableName;

class DisplayFrame extends JFrame implements ActionListener {
    JButton b1, b2, b3;
    JLabel l1, l2, l3, l4;
    JTextField t1, t2, t3, t4;
    // JDatePicker dateChooser;

    DisplayFrame() {
        super("Task ");
        // l1 = new JLabel("Date");
        l2 = new JLabel("Task");
        l3 = new JLabel("Description");
        l4 = new JLabel("Due Date");
        b1 = new JButton("Add");
        b2 = new JButton("Close");
        b3 = new JButton("Back");
        // t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();

        // l1.setBounds(20, 20, 100, 30);
        l2.setBounds(30, 30, 100, 30);
        l3.setBounds(30, 70, 100, 30);
        l4.setBounds(30, 140, 100, 30);
        b1.setBounds(30, 200, 80, 35);
        b2.setBounds(150, 200, 80, 35);
        b3.setBounds(270, 200, 80, 35);

        // t1.setBounds(140, 20, 150, 25);
        t2.setBounds(140, 30, 150, 25);
        t3.setBounds(140, 70, 150, 45);
        t4.setBounds(140, 140, 150, 25);

        // add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(b1);
        add(b2);
        add(b3);
        // add(t1);
        add(t2);
        add(t3);
        add(t4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        setLayout(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Add task
        {
            // String s1=t1.getText();
            String s1 = java.time.LocalDate.now() + "";
            String s2 = t2.getText();
            String s3 = t3.getText();
            String s4 = t4.getText();
            if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill the TextFiled", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    String query = "INSERT INTO " + tableName + " (Date, task, Discription, DueDate) VALUES (?, ?, ?, ?)";

                    // Create the PreparedStatement
                    PreparedStatement p = connection.prepareStatement(query);
                    p.setString(1, s1);
                    p.setString(2, s2);
                    p.setString(3, s3);
                    p.setString(4, s4);
                    int rowsInserted = p.executeUpdate();
                    if (rowsInserted > 0) {
                        dispose();
                        JOptionPane.showMessageDialog(null, "Task Added successfully!");
                        task f = new task();
                        f.getContentPane().setBackground(Color.lightGray);
                        f.setSize(600, 400);
                        f.setVisible(true);
                        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    } else {

                        JOptionPane.showMessageDialog(null, "Error", "Failed to Add Task", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }
        } else if (e.getSource() == b2) {
            dispose();
        } else if (e.getSource() == b3) {
            dispose();
            task f = new task();
            f.getContentPane().setBackground(new Color(173, 216, 230));
            // f.getContentPane().setBackground(Color.lightGray);
            f.setSize(600, 400);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
