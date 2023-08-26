package todolist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static todolist.MyFrame.tableName;

class task extends JFrame implements ActionListener {
    JButton b1, b2, b3;
    DefaultTableModel model = new DefaultTableModel();
    Container cnt = this.getContentPane();
    JTable jtbl = new JTable(model);

    task() {
        super("Task");

        b1 = new JButton("Add Task");
        b2 = new JButton("Edit/Remove Task");
        b3 = new JButton("Logout");

        b1.setBounds(80, 20, 100, 30);
        b2.setBounds(220, 20, 140, 30);
        b3.setBounds(380, 20, 100, 30);

        add(b1);
        add(b2);
        add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        JScrollPane pg = new JScrollPane(jtbl);
        pg.setBounds(80, 80, 400, 200);
        cnt.add(pg);
        cnt.setLayout(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM " + tableName;
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
        } catch (SQLException e1) {
            System.out.println(e1);
        }

        // Set custom renderer for the table header (first row)
        jtbl.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer());

        // Set custom renderer for the table cells
        jtbl.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) // Add Task
        {
            dispose();
            DisplayFrame displayFrame = new DisplayFrame();
            displayFrame.getContentPane().setBackground(new Color(173, 216, 230));
            displayFrame.setSize(500, 300);
            displayFrame.setVisible(true);
            displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == b2) // Edit Remove
        {
            dispose();
            EditDel obj = new EditDel();
            obj.getContentPane().setBackground(new Color(173, 216, 230));
            obj.setSize(400, 400);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (e.getSource() == b3) // Logout
        {
            dispose();
        }
    }

    // Custom table header renderer for changing the background color
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

    // Custom table cell renderer for changing the color of the rows
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);

            if (!isSelected) {
                // Change the background color of alternate rows (excluding the header row)
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
