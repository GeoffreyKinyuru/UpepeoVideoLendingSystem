
package InventoryModule.InventoryReport;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.MessageFormat;
import net.proteanit.sql.DbUtils;

public class Report extends JDialog
{
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
        
    private static JDesktopPane overRollContainer=new JDesktopPane();
    private static JLabel selectReportType=new JLabel("Select Report Type: ");
    private static String [] comboBoxObjects={"Select Report Type","Full Report"};
    private static JComboBox reportTypeOptions=new JComboBox<>(comboBoxObjects);
    
   
    private static JButton generateFullReport=new JButton("Generate");
    
     private static  JTable reportArea;
    private static JScrollPane tableViewPort;
    private static JPanel scrollPaneHolder;
    
    private static JButton printFullReport = new JButton("Print_Full_Report");
    
    public Report(JFrame frame)
    {
        super(frame,"Catalogue Records",false);
        
        selectReportType.setForeground(Color.RED);
        selectReportType.setBounds(120,10,120,20);
        add(selectReportType);
        
        reportTypeOptions.setBounds(240,10,150,20);
        reportTypeOptions.setBackground(Color.WHITE);
        add(reportTypeOptions);
        
         reportTypeOptionsEvent e=new reportTypeOptionsEvent();
        reportTypeOptions.addActionListener(e);
        
       generateFullReport.setBounds(450,10,90,20);
        generateFullReport.setBackground(Color.red);
        generateFullReport.setForeground(Color.BLACK);
        add(generateFullReport);
        
        generateFullReportEvent fe = new generateFullReportEvent();
        generateFullReport.addActionListener(fe);
        
         String [] columnNames=
                {
                    "Video ID","Video Label","Video Category","Video Payments","Entry Date" 
                };
         
                 Object [][] data=
                {
                    {"","","","",""}, 
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""},
                    {"","","","",""}
                    
                };
                
        reportArea=new JTable(data,columnNames);
         reportArea.setPreferredScrollableViewportSize(new Dimension(650,200));
         reportArea.setFillsViewportHeight(true);        
         reportArea.setGridColor(Color.yellow);
        reportArea.setBackground(Color.WHITE);
       
        
        tableViewPort=new JScrollPane();
       
         tableViewPort.setViewportView(reportArea);
          
        
        scrollPaneHolder = new JPanel();        
        scrollPaneHolder.setBounds(10,40,675,250);       
        scrollPaneHolder.add(tableViewPort);
        scrollPaneHolder.setBackground(Color.PINK);
        add(scrollPaneHolder);
        
        
        printFullReport.setBounds(250,300,170,50);
        printFullReport.setBackground(Color.ORANGE);
        printFullReport.setForeground(Color.BLUE);
        add(printFullReport);
        
        printFullReportEvent pFr = new printFullReportEvent();
        printFullReport.addActionListener(pFr);
        
        
        addWindowListener(new java.awt.event.WindowAdapter() 
         {
            public void windowActivated(java.awt.event.WindowEvent evt) 
            {
                formWindowActivated(evt);
            }
        }
                          );
         
    }
    
     // ComboBox Events
    
    public class reportTypeOptionsEvent implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            int optionHolder= reportTypeOptions.getSelectedIndex();
            if(optionHolder==1)
            {
                
                printFullReport.setEnabled(true);
                
                generateFullReport.setVisible(true);
            }else
            {
                printFullReport.setEnabled(false);
               
                generateFullReport.setVisible(false);
            }
        }
    }
    
    
     //formWindowActivation Event
    
    private void formWindowActivated(java.awt.event.WindowEvent evt)
    {                                     
       
       generateFullReport.setVisible(false);
       printFullReport.setEnabled(false);
       
    } 
    
    
     // generateFullReport Event
    
    public class generateFullReportEvent implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            try
            {
               conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/upepeoproject","root","");
               Statement sqlStatement = conn.createStatement();
               String Select_All_Borrowers = "SELECT * FROM invetory ";
               pst=conn.prepareStatement(Select_All_Borrowers);
               rs = pst.executeQuery();
               reportArea.setModel(DbUtils.resultSetToTableModel(rs));
            } catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null,"Server Says:"+e.getMessage(),"Server Message",JOptionPane.OK_OPTION);
            }
        }
    }
   
     // Full Report
    
    public class printFullReportEvent implements ActionListener
    {
        public void actionPerformed(ActionEvent ev)
        {
            MessageFormat reportHeader = new MessageFormat("Video Catalogue Report");
            MessageFormat reportFooter = new MessageFormat(" Page{0, number, Integer}");
            
            try
            {
                reportArea.print(JTable.PrintMode.NORMAL, reportHeader, reportFooter);
            }catch(java.awt.print.PrinterException e)
            {
                JOptionPane.showMessageDialog(null, " Error Occured since "+e.getMessage());
            }
        }
    }
    
}
