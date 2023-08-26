package todolist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import static todolist.MyFrame.tableName;

class Register extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6;
    JButton b1, b2, b3;
    JTextField t1, t2, t3;
    JPasswordField p1;

    Register() {
        super("Register");

        l1 = new JLabel("New User Register");
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
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Already Account
        {
            dispose();
            MyFrame f = new MyFrame();
            f.getContentPane().setBackground(Color.pink);
            f.getContentPane().setBackground(new Color(173, 216, 230));
            f.setSize(400, 400);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == b2) // Register
        {
            try {
                if ((t1.getText().length() == 0) || (t2.getText().length() == 0)
                        || (t3.getText().length() == 0) || (new String(p1.getPassword()).length() == 0)) {
                    JOptionPane.showMessageDialog(null, "TextField Can not be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Connection connection = DatabaseConnection.getConnection();
                    String uname = t3.getText();
                    PreparedStatement preparedStatement = connection.prepareStatement("select uname from login");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String un = resultSet.getString(1);
                        System.out.println(un);
                        if (uname.equals(un)) {
                            JOptionPane.showMessageDialog(null, "Username already Exist", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    PreparedStatement p = connection.prepareStatement("insert into login values(?,?,?,?)");
                    p.setString(1, t1.getText());
                    p.setString(2, t2.getText());
                    p.setString(3, t3.getText());
                    p.setString(4, new String(p1.getPassword()));
                    p.executeUpdate();

                    Statement statement = connection.createStatement();
                    String createTableQuery = "CREATE TABLE " + uname + " ("
                            + "Date VARCHAR(50),"
                            + "Task VARCHAR(50),"
                            + "Discription VARCHAR(100),"
                            + "DueDate VARCHAR(50)"
                            + ")";

                    tableName = uname;
                    statement.executeUpdate(createTableQuery);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Success");

                    task f = new task();
                    f.getContentPane().setBackground(Color.pink);
                    f.setSize(600, 500);
                    f.setVisible(true);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                }
            } catch (Exception e1) {
                System.out.println("Error while connecting to the database");
            }
        }
    }
}
