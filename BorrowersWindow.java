package Borrowers;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BorrowersWindow extends JDialog {
    

    Connection sqlConnection = null;
    PreparedStatement pst = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    ResultSet queryResult = null;
    ResultSet queryResults = null;
  
    private static JTextField borrowDate ,duedate,searchVideo, searchMember, mIDb, fNameb, middlenameb, lNameb, vcodeb, vlabelb, vcategoryb, vCategorypaymentb;
    private static JLabel Borrowersmessage, searchvLabelb, searchMLabelb, mIdLabelb, flb, mLb, llb, vcdeLabelb, labelb, categoryLabelb, vcpaymentLabelb, borrowingdateLabelb, duedateLabelb;
    private static JButton saveb, resetb, cancelb;
    private static String firstname, middleName, lastname, svcode, slabel, scategory, svPAYMENT;
    private static JDesktopPane borrowersPane;

    public BorrowersWindow(JFrame frame) {
        super(frame,"my title",true);

        searchMLabelb = new JLabel("Search Member by MemberID:");
        searchMLabelb.setBounds(1, 20, 175, 20);
        searchMLabelb.setForeground(Color.BLUE);

        add(searchMLabelb);

        searchMember = new JTextField();
        searchMember.setBounds(176, 20, 100, 20);
        add(searchMember);

        mIdLabelb = new JLabel("MemberID:");
        mIdLabelb.setBounds(1, 50, 90, 20);
        mIdLabelb.setForeground(Color.WHITE);
        add(mIdLabelb);

        mIDb = new JTextField();
        mIDb.setBounds(75, 50, 200, 20);
        add(mIDb);

        flb = new JLabel("FirstName:");
        flb.setBounds(1, 90, 100, 20);
        flb.setForeground(Color.WHITE);
        add(flb);
        fNameb = new JTextField();
        fNameb.setBounds(75, 90, 200, 20);
        add(fNameb);

        mLb = new JLabel("MiddleName:");
        mLb.setBounds(1, 130, 100, 20);
        mLb.setForeground(Color.WHITE);
        add(mLb);
        middlenameb = new JTextField();
        middlenameb.setBounds(75, 170, 200, 20);
        add(middlenameb);

        llb = new JLabel("LastName:");
        llb.setBounds(1, 170, 100, 20);
        llb.setForeground(Color.WHITE);
        add(llb);
        lNameb = new JTextField();
        lNameb.setBounds(75, 130, 200, 20);
        add(lNameb);

        searchvLabelb = new JLabel("Search Video by VideoCode:");
        searchvLabelb.setBounds(300, 20, 200, 20);
        searchvLabelb.setForeground(Color.BLUE);
        add(searchvLabelb);

        searchVideo = new JTextField();
        searchVideo.setBounds(470, 20, 120, 20);
        add(searchVideo);

        vcdeLabelb = new JLabel("VideoCode:");
        vcdeLabelb.setBounds(300, 50, 90, 20);
        vcdeLabelb.setForeground(Color.WHITE);
        add(vcdeLabelb);

        vcodeb = new JTextField();
        vcodeb.setBounds(390, 50, 200, 20);
        add(vcodeb);

        labelb = new JLabel("VideoLabel:");
        labelb.setBounds(300, 90, 90, 20);
        labelb.setForeground(Color.WHITE);
        add(labelb);

        vlabelb = new JTextField();
        vlabelb.setBounds(390, 90, 200, 20);
        add(vlabelb);

        categoryLabelb = new JLabel("VideoCategory:");
        categoryLabelb.setBounds(280, 130, 100, 20);
        categoryLabelb.setForeground(Color.WHITE);
        add(categoryLabelb);

        vcategoryb = new JTextField();
        vcategoryb.setBounds(390, 130, 200, 20);
        add(vcategoryb);

        vcpaymentLabelb = new JLabel("Payments:");
        vcpaymentLabelb.setBounds(300, 170, 90, 20);
        vcpaymentLabelb.setForeground(Color.WHITE);
        add(vcpaymentLabelb);

        vCategorypaymentb = new JTextField();
        vCategorypaymentb.setBounds(390, 170, 200, 20);
        add(vCategorypaymentb);

        borrowingdateLabelb = new JLabel("Borrowing Date:");
        borrowingdateLabelb.setBounds(190, 210, 100, 20);
        borrowingdateLabelb.setForeground(Color.WHITE);
        add(borrowingdateLabelb);

        LocalDate borrowingDate = LocalDate.now();
        borrowDate = new JTextField();
        borrowDate.setText(borrowingDate.toString());
        borrowDate.setEditable(false);
        borrowDate.setBounds(300, 210, 200, 20);
        add(borrowDate);
                
        

        duedateLabelb = new JLabel("DueDate:");
        duedateLabelb.setBounds(220, 250, 90, 20);
        duedateLabelb.setForeground(Color.WHITE);
        add(duedateLabelb);

        LocalDate dueDate = LocalDate.now().plusDays(3);
        duedate = new JTextField();
        duedate.setBounds(300, 250, 200, 20);
        duedate.setText(dueDate.toString());
        duedate.setEditable(false);
        add(duedate); 
        

        //buttons
        saveb = new JButton("SAVE");
        saveb.setBounds(70, 310, 90, 40);
        saveb.setBackground(Color.ORANGE);
        saveb.setForeground(Color.BLUE);
        add(saveb);

        resetb = new JButton("RESET");
        resetb.setBounds(250, 310, 90, 40);
        resetb.setBackground(Color.ORANGE);
        resetb.setForeground(Color.BLUE);
        add(resetb);

        cancelb = new JButton("CANCEL");
        cancelb.setBounds(420, 310, 90, 40);
        cancelb.setBackground(Color.ORANGE);
        cancelb.setForeground(Color.BLUE);
        add(cancelb);


//------------------------------------------------------------------------------------------------------------------------------------------------------       
        saveb.addActionListener((ActionEvent sbe) -> {
            if (mIDb.getText().isEmpty() || fNameb.getText().isEmpty() || middlenameb.getText().isEmpty() || lNameb.getText().isEmpty() || vcodeb.getText().isEmpty() || vlabelb.getText().isEmpty() || vcategoryb.getText().isEmpty() || vCategorypaymentb.getText().isEmpty() || borrowDate.getText().isEmpty() || duedate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ALL FIELDS ARE MADATORY!!!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    int myId = 0;
                    Connection sqlConnections = DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject", "root", "");
                    
                    Statement sqlStatements = sqlConnections.createStatement();
                    
                    String INSERT_QUERY = "INSERT INTO borrowerstable VALUES('" + vcodeb.getText() + "','" + vlabelb.getText() + "','" + vcategoryb.getText() + "','" + vCategorypaymentb.getText() + "','" + mIDb.getText() + "','" + fNameb.getText() + "','" + middlenameb.getText() + "','" + lNameb.getText() + "','" + borrowDate.getText() + "','" + duedate.getText() + "');";
                    
                    String INSERT_QUERYb = "INSERT INTO borrowersRecords VALUES('" + myId++ + "','" + vcodeb.getText() + "','" + vlabelb.getText() + "','" + vcategoryb.getText()+ "','" + vCategorypaymentb.getText() + "','" + mIDb.getText() + "','" + fNameb.getText() + "','" +middlenameb.getText() + "','" + lNameb.getText() + "','" + borrowDate.getText() + "','" + duedate.getText() + "');";
                    
                    String UPDATE_QUERY = "UPDATE invetory SET availability='MISSING' WHERE vcode='" + vcodeb.getText() + "'";
                    
                    sqlStatements.execute(INSERT_QUERY);
                    sqlStatements.execute(INSERT_QUERYb);
                    sqlStatements.execute(UPDATE_QUERY);
                    
                    JOptionPane.showMessageDialog(null, fNameb.getText() + " : " + "records were saved successfully and The Catalogue Updated Accordingly", "SUCCESSFUL RECORD ENTRY", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "SERVER SAYS: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
//---------------------------------------------------------------------------------------------------------------------------------------------------        
        searchMember.addActionListener((ActionEvent sme) -> {
            try {
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject", "root", "");
                
                
                Statement sqlStatements = sqlConnection.createStatement();
                
                pst3 = sqlConnection.prepareStatement("SELECT  count(*) FROM borrowerstable where mID= ? ");
                pst3.setString(1, searchMember.getText());
                queryResults = pst3.executeQuery();
                
                if (queryResults.next()) {            
                    List<JTextField> text = Arrays.asList(fNameb,middlenameb,mIDb,lNameb,searchMember);
                    if (queryResults.getInt(1) >= 5) {
                        text.stream().forEach(e -> e.setText(null));                        
                        
                        JOptionPane.showMessageDialog(null, "DENIED !\n ---You have  more than five videos in your possesion\n---Please return the videos to borrow  new ones", "DENIAL OF SERVICE", JOptionPane.OK_OPTION);
                        
                    } else {
                        try {
                            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject", "root", "");
                            
                            Statement sqlStatement = sqlConnection.createStatement();
                            
                            pst = sqlConnection.prepareStatement("SELECT * FROM membersregistrationtable where mID= ? ");
                            
                            pst.setString(1, searchMember.getText());
                            
                            queryResults = pst.executeQuery();
                            if (queryResults.next()) {
                                
                                String smid = queryResults.getString("mID");
                                String sfirstname = queryResults.getString("firstname");
                                String smiddlename = queryResults.getString("middlename");
                                String slastname = queryResults.getString("lastname");
                                mIDb.setText(smid);
                                fNameb.setText(sfirstname);
                                middlenameb.setText(smiddlename);
                                lNameb.setText(slastname);
                            } else {
                                JOptionPane.showMessageDialog(null, "Server Error");
                            }

                        } catch (SQLException e3) {
                            JOptionPane.showMessageDialog(null, "SERVER SAYS: " + e3.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }
            } catch (SQLException e4) {
                JOptionPane.showMessageDialog(null, "SERVER SAYS: " + e4.getMessage(), "SERVER MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        });
//-----------------------------------------------------------------------------------------------------------------------------------
        searchVideo.addActionListener((ActionEvent ev) -> {
            try {
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject", "root", "");

                Statement sqlStatements = sqlConnection.createStatement();

                pst = sqlConnection.prepareStatement("SELECT * FROM invetory where vcode= ? ");

                pst.setString(1, searchVideo.getText());

                queryResults = pst.executeQuery();

                if (queryResults.next()) {

                    String avail = queryResults.getString("availability");

                    if (avail.equals("MISSING")) {
                        vcodeb.setText(null);
                        vlabelb.setText(null);
                        vcategoryb.setText(null);
                        vCategorypaymentb.setText(null);

                        JOptionPane.showMessageDialog(null, "It is possible that the video you are searching is : \n 1:  currently on loan \n 2:  Does'nt exist", "MISSING VIDEO", JOptionPane.ERROR_MESSAGE);

                    } else {

                        svcode = queryResults.getString("vcode");
                        slabel = queryResults.getString("vlabel");
                        scategory = queryResults.getString("category");
                        svPAYMENT = queryResults.getString("payments");

                        vcodeb.setText(svcode);
                        vlabelb.setText(slabel);
                        vcategoryb.setText(scategory);
                        vCategorypaymentb.setText(svPAYMENT);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "That video is missing");
                }

            } catch (SQLException e2) {
                JOptionPane.showMessageDialog(null, "SERVER SAYS: " + e2.getMessage(), "SERVER MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        });
//---------------------------------------------------------------------------------------------------------------------------------------------------
        resetb.addActionListener((ActionEvent rbe) -> {
            if (mIDb.getText().isEmpty() && fNameb.getText().isEmpty() && middlenameb.getText().isEmpty() && lNameb.getText().isEmpty() && vcodeb.getText().isEmpty() && vlabelb.getText().isEmpty() && vcategoryb.getText().isEmpty() && vCategorypaymentb.getText().isEmpty() && borrowDate.getText().isEmpty() && duedate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "You Can't Reset an Empty Set !", "EMPTY SET", JOptionPane.OK_OPTION);
            } else {
                setTextFieldsTextNull();
            }
        });
//------------------------------------------------------------------------------------------------------------------------------------------------------        
        cancelb.addActionListener((ActionEvent cbe)->{
            int i = JOptionPane.showConfirmDialog(null, "Are you sure that you want to cancel", "CONFIRM", JOptionPane.YES_NO_OPTION);
            try {
                if (i == JOptionPane.YES_OPTION) {
                    int a = JOptionPane.showConfirmDialog(null, "Do you want to perform any other operation ?", "CONFIRM", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {

                    } else {
                       BorrowersWindow.this.dispose();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, " THE SYSTEM SAYS: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public void setTextFieldsTextNull(){
        List<JTextField> setNull = Arrays.asList(fNameb);
        setNull.stream()
               .forEach((JTextField e) -> {
                   e.setText(null);
        });
    }
}


