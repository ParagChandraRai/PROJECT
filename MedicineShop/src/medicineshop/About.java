package medicineshop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class MyFrameA extends JFrame {
    public static String tableName;
    JLabel l1, img, descriptionLabel;
    
    MyFrameA() {
        super("About Project");
        img = new JLabel();
        ImageIcon imageIcon = new ImageIcon("store.jpg");
        Image image = imageIcon.getImage();
        
        Image scaledImage = image.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        img.setIcon(new ImageIcon(scaledImage));
        img.setBounds(30, 40, 250, 300);
        add(img);

        l1 = new JLabel("About Medical Shop Management System");
        Font newFont = l1.getFont().deriveFont(20f);
        l1.setFont(newFont);
        l1.setForeground(Color.BLUE); // Set the font color to red
        l1.setBounds(10, 10, 500, 30);

        descriptionLabel = new JLabel("<html><div style='text-align: left; "
                + "padding-left: 20px;'>"
                + "<p>This project aims to provide an efficient solution for managing various aspects of a medical shop.</p>"
                + "<p>It helps in tracking inventory, sales, and managing customer Sales Report, Medicine Report.</p>"
                + "<p>The system provides a user-friendly interface for ease of use.</p>"
                + "<p>Developed by: PARAG CHANDRA RAI</p>"
                + "<p>Version: 1.0</p></div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        descriptionLabel.setBounds(290, 40, 400, 300);
        add(descriptionLabel);
        add(l1);
        setLayout(null);
    }
}
