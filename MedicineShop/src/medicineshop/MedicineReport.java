package medicineshop;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.border.TitledBorder;

class MyFrame4 extends JFrame implements ActionListener {
    DefaultTableModel model = new DefaultTableModel();
    JTable jtbl = new JTable(model);
    JButton b1, b2;

    MyFrame4() {
        super("Medicine Report");

        JPanel addPanel = new JPanel();
        addPanel.setLayout(null);
        addPanel.setBounds(20, 30, 600, 430);

        JScrollPane pg = new JScrollPane(jtbl);
        pg.setBounds(30, 50, 580, 380);
        add(pg);

        b1 = new JButton("Print");
        b2 = new JButton("Close");

        TitledBorder addPanelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED, 2),
                "Medicine Management",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 12),
                Color.red
        );

        addPanel.setBorder(addPanelTitledBorder);

        b1.setBounds(420, 500, 80, 30);
        b2.setBounds(520, 500, 80, 30);
        add(b1);
        add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);
        add(addPanel);
        setLayout(null);

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicine";
            PreparedStatement p = connection.prepareStatement(query);
            ResultSet rs = p.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            Vector<String> colname = new Vector<>();
            for (int i = 0; i < cols; i++) {
                colname.add(rsmd.getColumnName(i + 1));
            }
            model.setColumnIdentifiers(colname);
            while (rs.next()) {
                Vector<String> rowData = new Vector<>();
                for (int i = 0; i < cols; i++) {
                    rowData.add(rs.getString(i + 1));
                }
                model.addRow(rowData);
            }
        } catch (Exception e1) {
            System.out.println(e1);
        }

        jtbl.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer());
        jtbl.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        jtbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = jtbl.getSelectedRow();
                    if (selectedRow != -1) {
                        int result = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure you want to delete this row?",
                                "Delete Confirmation",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                Connection connection = DatabaseConnection.getConnection();
                                String deleteQuery = "DELETE FROM medicine WHERE id = ?";
                                int selectedId = Integer.parseInt(jtbl.getValueAt(selectedRow, 0).toString());
                                PreparedStatement p = connection.prepareStatement(deleteQuery);
                                p.setInt(1, selectedId);
                                p.executeUpdate();
                                model.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(null, "Row deleted successfully.");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                jtbl.print();
            } catch (Exception ex) {
            }
        }
        if (e.getSource() == b2) {
            dispose();
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
