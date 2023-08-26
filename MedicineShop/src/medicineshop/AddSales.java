package medicineshop;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class MyFramesal extends JFrame implements ActionListener {
    public static String tableName;
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JButton b1, b2, b3;
    JTextField t1, t2;
    JComboBox<Integer> numberComboBox;
    JComboBox<String> medicineList;
    ArrayList<String> s1 = new ArrayList<>();
    JPanel admin;
    DefaultTableModel model = new DefaultTableModel();
    Container cnt = this.getContentPane();
    JTable jtbl = new JTable(model);
    double total = 0;

    MyFramesal() {
        super("Sales Dashboard");

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(30, 65, 670, 100);

        admin = new JPanel();
        admin.setLayout(null);
        admin.setBounds(30, 180, 670, 430);

        JScrollPane pg = new JScrollPane(jtbl);
        pg.setBounds(60, 100, 550, 200);
        admin.add(pg);

        l1 = new JLabel("Customer Name:");
        l2 = new JLabel("Mobile:");
        b1 = new JButton("Start Billing");
        b3 = new JButton("Save Order and Exit");

        l6 = new JLabel("Total Cost:");
        l7 = new JLabel("");
        t1 = new JTextField();
        t2 = new JTextField();

        l4 = new JLabel("Select Medicine:");
        l5 = new JLabel("Quantity:");
        b2 = new JButton("Add Item");

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT medicine_name FROM medicine";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String medicineName = resultSet.getString("medicine_name");
                s1.add(medicineName);
            }
        } catch (Exception e) {
        }

        Integer[] numbers = new Integer[100];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        medicineList = new JComboBox<>();
        for (String medicineName : s1) {
            medicineList.addItem(medicineName);
        }

        numberComboBox = new JComboBox<>(numbers);
        numberComboBox.setSelectedIndex(0);

        l3 = new JLabel("Sales Dashboard");
        Font newFont = l3.getFont().deriveFont(20f);
        l3.setFont(newFont);

        l1.setBounds(20, 40, 100, 30);
        l2.setBounds(290, 40, 100, 30);

        t1.setBounds(130, 43, 150, 23);
        t2.setBounds(340, 43, 150, 23);
        b1.setBounds(500, 43, 100, 25);

        l4.setBounds(20, 40, 100, 30);
        l5.setBounds(280, 40, 100, 30);

        l6.setBounds(400, 320, 100, 30);
        l7.setBounds(470, 320, 100, 30);
        b3.setBounds(400, 370, 150, 25);

        medicineList.setBounds(120, 40, 150, 25);
        numberComboBox.setBounds(340, 43, 150, 23);
        b2.setBounds(500, 43, 100, 25);

        l3.setBounds(20, 10, 400, 30);

        loginPanel.add(l1);
        loginPanel.add(l2);
        loginPanel.add(t1);
        loginPanel.add(t2);
        loginPanel.add(b1);

        admin.add(l4);
        admin.add(l5);
        admin.add(medicineList);
        admin.add(numberComboBox);
        admin.add(b2);
        admin.add(l6);
        admin.add(l7);
        admin.add(b3);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                "Customer Details",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                Color.red
        );

        loginPanel.setBorder(titledBorder);

        TitledBorder adminTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                "Sales Dashboard",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 16),
                Color.red
        );

        admin.setBorder(adminTitledBorder);

        add(l3);
        add(loginPanel);
        add(admin);
        admin.setVisible(false);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (t1.getText().length() == 0 || t1.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "TextField Can not be Empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                admin.setVisible(true);
                b1.setVisible(false);

                Connection connection = DatabaseConnection.getConnection();
                String query = "SELECT * FROM orders";
                PreparedStatement p = connection.prepareStatement(query);
                ResultSet rs = p.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                String[] colname = new String[cols];
                for (int i = 0; i < cols; i++)
                    colname[i] = rsmd.getColumnName(i + 1);
                model.setColumnIdentifiers(colname);
                while (rs.next()) {
                    Object[] rowData = new Object[cols];
                    for (int i = 0; i < cols; i++) {
                        rowData[i] = rs.getString(i + 1);
                    }
                    model.addRow(rowData);
                }
                jtbl.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer());
                jtbl.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
                p.close();
                connection.close();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        }

        if (e.getSource() == b2) {
            try {
                String selectedMedicine = (String) medicineList.getSelectedItem();
                int selectedQuantity = (int) numberComboBox.getSelectedItem();
                Connection connection = DatabaseConnection.getConnection();

                String q = "SELECT cost FROM medicine WHERE medicine_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(q);
                preparedStatement.setString(1, selectedMedicine);

                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    double costPerUnit = result.getDouble("cost");

                    total += costPerUnit * selectedQuantity;
                    double total1 = costPerUnit * selectedQuantity;

                    String query = "INSERT INTO orders (medicine_name, cost_per_unit, quantity, total) VALUES (?, ?, ?, ?)";
                    PreparedStatement p = connection.prepareStatement(query);
                    p.setString(1, selectedMedicine);
                    p.setString(2, costPerUnit + "");
                    p.setString(3, selectedQuantity + "");
                    p.setString(4, total1 + "");
                    int rowsInserted = p.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, " Added successfully!");
                        model.setRowCount(0);
                        l7.setText(total + "");
                        b1.doClick();
                        p.close();
                        connection.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error", "Failed to Add Task", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e2) {
            }
        }

        if (e.getSource() == b3) {
            try {
                String cn = t1.getText();
                String mob = t2.getText();

                LocalDateTime currentDateTime = LocalDateTime.now();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                String formattedDate = currentDateTime.format(dateFormatter);
                String formattedTime = currentDateTime.format(timeFormatter);

                Connection connection = DatabaseConnection.getConnection();
                String insertQuery = "INSERT INTO sales (CustomerName, Mobile, Date, Total) VALUES (?, ?, ?, ?)";
                PreparedStatement p = connection.prepareStatement(insertQuery);

                p.setString(1, cn);
                p.setString(2, mob);
                p.setString(3, formattedDate);
                p.setString(4, total + "");
                int rowsInserted = p.executeUpdate();
                if (rowsInserted > 0) {
                    String deleteQuery = "DELETE FROM orders";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(deleteQuery);
                    statement.close();
                    connection.close();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error", "Failed to Add Task", JOptionPane.ERROR_MESSAGE);
                }
                p.close();
                connection.close();
            } catch (Exception ex1) {
                System.out.println(ex1);
            }
        }
    }

    class CustomTableHeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);

            cellComponent.setBackground(Color.RED);
            cellComponent.setForeground(Color.WHITE);

            return cellComponent;
        }
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);

            if (!isSelected) {
                if (row % 2 != 0 && row != 0) {
                    cellComponent.setBackground(Color.DARK_GRAY);
                    cellComponent.setForeground(Color.WHITE);
                } else {
                    cellComponent.setBackground(table.getBackground());
                    cellComponent.setForeground(table.getForeground());
                }
            }

            return cellComponent;
        }
    }
}
