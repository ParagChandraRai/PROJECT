package todolist;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static todolist.MyFrame.tableName;

public class EditDel extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4;
    JLabel l1, l2, l3, l4;
    JTextField t1, t3, t4;

    public EditDel() {
        super("Edit");
        l1 = new JLabel("Task");
        l3 = new JLabel("Description");
        l4 = new JLabel("Due Date");
        t1 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();
        b1 = new JButton("Search");
        b2 = new JButton("Edit");
        b3 = new JButton("Delete");
        b4 = new JButton("Back");

        l1.setBounds(10, 10, 100, 30);
        t1.setBounds(50, 10, 190, 30);
        b1.setBounds(300, 10, 80, 35);

        l3.setBounds(30, 120, 100, 30);
        t3.setBounds(100, 120, 150, 40);

        l4.setBounds(30, 180, 100, 30);
        t4.setBounds(100, 180, 150, 30);

        b2.setBounds(30, 240, 80, 35);
        b3.setBounds(120, 240, 80, 35);
        b4.setBounds(230, 240, 80, 35);

        add(l1);
        add(l3);
        add(l4);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(t1);

        add(t3);
        add(t4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        setLayout(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Search task
        {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String s1 = t1.getText();
                String query = "SELECT * FROM " + tableName + " WHERE task=?";
                PreparedStatement p = connection.prepareStatement(query);
                p.setString(1, s1);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    t3.setText(rs.getString(3));
                    t4.setText(rs.getString(4));
                } else {
                    t3.setText("");
                    t4.setText("");
                    JOptionPane.showMessageDialog(null, "Error", "No task Found By Given name", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } else if (e.getSource() == b2) // Edit/update task
        {
            String tx1 = t1.getText();
            String tx2 = t3.getText();
            String tx4 = t4.getText();
            if (tx1.isEmpty() || tx2.isEmpty() || tx4.isEmpty())
                JOptionPane.showMessageDialog(null, "Error", "Text Field is Empty", JOptionPane.ERROR_MESSAGE);
            else {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    String taskName = t1.getText();
                    String field2Value = java.time.LocalDate.now() + "";
                    String field3Value = t3.getText();
                    String field4Value = t4.getText();
                    String query = "UPDATE " + tableName + " SET Task=?, Discription=?, DueDate=? WHERE Task=?";
                    PreparedStatement p = connection.prepareStatement(query);
                    p.setString(1, field2Value);
                    p.setString(2, field3Value);
                    p.setString(3, field4Value);
                    p.setString(4, taskName);
                    int updatedRows = p.executeUpdate();
                    if (updatedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Task updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No task Found By Given name", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }
        } else if (e.getSource() == b3) // Delete task
        {
            try {
                Connection connection = DatabaseConnection.getConnection();
                String taskName = t1.getText();
                PreparedStatement p = connection.prepareStatement("DELETE FROM task WHERE task = ?");
                p.setString(1, taskName);
                int deletedRows = p.executeUpdate();
                if (deletedRows > 0) {
                    JOptionPane.showMessageDialog(null, "Task Deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "No task Found By Given name", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } else if (e.getSource() == b4) // Back
        {
            dispose();
            task f = new task();
            f.getContentPane().setBackground(new Color(173, 216, 230));
            f.setSize(600, 400);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
