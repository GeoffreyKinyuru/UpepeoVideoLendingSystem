package InventoryModule;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.text.JTextComponent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Catalogue extends JDialog {
    // private com.toedter.calendar.JDateChooser CataloguejDateChooser;

    private static JMenuBar mainMenu;
    private static JTextField vid, vL, date2;
    private static JComboBox<String> vCat, vAvail;
    private static JButton addV, newV, cancel;
    private JLabel l1, vidl, vcl, vl, va, ed;

    public Catalogue(JFrame frame) {
        super(frame, "Catalogue Window", true);

        vidl = new JLabel("Video ID:");
        vidl.setBounds(5, 20, 60, 20);
        add(vidl);//end of vidl
//VideoId (vid)
        vid = new JTextField();
        vid.setBounds(100, 20, 100, 20);
        add(vid);// End of my Video id section
//videCategoryLabel
        vcl = new JLabel("VideoCategory:");
        vcl.setBounds(5, 80, 90, 20);
        add(vcl);

        vCat = new JComboBox<>();
        vCat.setBounds(100, 80, 100, 20);
        vCat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Category", "Horror", "Catoon", "Thriller", "General"}));
        add(vCat);

//end of vCat
//VIdeoLabel
        vl = new JLabel("Video Label:");
        vl.setBounds(230, 20, 80, 20);
        add(vl);
//videoLabel;
        vL = new JTextField();
        vL.setBounds(300, 20, 100, 20);
        add(vL);//end of VideoLabel

//videAvailLabel
        va = new JLabel("Availability:");
        va.setBounds(230, 80, 80, 20);
        add(va);
//videoAvailability
        vAvail = new JComboBox<>();
        vAvail.setBounds(300, 80, 100, 20);
        vAvail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Availability", "Available", "Missing"}));
        add(vAvail);//end of VideoAvailability

//Entry date ed
        ed = new JLabel("Entry Date:");
        ed.setBounds(400, 50, 80, 20);
        add(ed);//end of Entry Date
//Date TextField
        LocalDate currentDate = LocalDate.now();
        LocalDate plusDays = currentDate.plusDays(3);
        date2 = new JTextField();
        date2.setText(plusDays.toString());
        date2.setBounds(490, 50, 100, 20);
        add(date2);
//Add Button
        addV = new JButton("ADD");
        addV.setBounds(70, 120, 90, 30);
        addV.setBackground(Color.ORANGE);
        addV.setForeground(Color.BLUE);
        add(addV);//end of add button
//New Video Button
        newV = new JButton("NEXT");
        newV.setBounds(250, 120, 90, 30);
        newV.setBackground(Color.ORANGE);
        newV.setForeground(Color.BLUE);
        add(newV);//end of new button
//Cancel Button
        cancel = new JButton("CANCEL");
        cancel.setBounds(420, 120, 90, 30);
        cancel.setBackground(Color.ORANGE);
        cancel.setForeground(Color.BLUE);
        add(cancel);//End of cancel button

// BUTTONS EVENTS
//add Button
        aEvent ae = new aEvent();
        addV.addActionListener(ae);
//Cancel Button
        cEvent ce = new cEvent();
        cancel.addActionListener(ce);
// Next button
        nEvent ne = new nEvent();
        newV.addActionListener(ne);

    }

    public class aEvent implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            String id, label, dat, vc, va, ve;
            String videoPayment;
            String category = (String) vCat.getSelectedItem();
            switch (category) {
                case "Horror":
                    videoPayment = " KSH 40 ";
                    break;
                case "General":
                    videoPayment = " KSH 50 ";
                    break;
                case "Comedy":
                    videoPayment = " KSH 70 ";
                    break;
                case "Thriller":
                    videoPayment = " KSH 60 ";
                    break;
                case "Catoon":
                    videoPayment = " KSH 80 ";
                    break;
                default:
                    videoPayment = " KSH 0 ";
                    break;
            }

            id = vid.getText();

            label = vL.getText();

            dat = date2.getText();
            vc = (String) vCat.getSelectedItem();
            va = (String) vAvail.getSelectedItem();

            if (id.isEmpty() || label.isEmpty() || dat.isEmpty() || vc.isEmpty() || va.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error! Empty set", "Error Message", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject", "root", "");
                    //create a connection to the database
                    Statement sqlStatements = sqlConnection.createStatement();
                    String INSERT_QUERY = "INSERT INTO invetory VALUES('" + id + "','" + label + "','" + vc + "','" + videoPayment + "','" + va + "','" + dat + "');";
                    sqlStatements.execute(INSERT_QUERY);
                    JOptionPane.showMessageDialog(null, "The video: " + label + " " + "was  added successfully", "SUCCESSFUL VIDEO ENTRY", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SERVER SAYS: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }

    public class cEvent implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            int c = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel ?", "Confirm Message", JOptionPane.YES_NO_CANCEL_OPTION);
            try {
                if (c == JOptionPane.YES_OPTION) {
                    int op = JOptionPane.showConfirmDialog(null, "Do you wish to perform other operations ?", "OTHER OPERATIONS", JOptionPane.YES_NO_OPTION);
                    if (op == JOptionPane.YES_OPTION) {

                    } else {
                        System.exit(0);
                    }
                } else {

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public class nEvent implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            vid.setText(null);
            vAvail.setSelectedIndex(0);
            vCat.setSelectedIndex(0);
            vL.setText(null);
            date2.setText(null);
        }
    }

}
