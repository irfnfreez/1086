package iptrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class IPTRevision extends JFrame implements ActionListener,ItemListener{
    JLabel lblTitle, lblName, lblID, lblEmail, lblPL, lblPN, lblPS, lblFee,lblOutput;
    JTextField txtName, txtID, txtEmail,txtFee;
    JComboBox<String> cbPL, cbPN, cbPS;
    JButton btnSubmit, btnView, btnReset,btnCalculate;
    JTextArea txtDisplay;
    Integer inewfee;
    
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/revisionipt";
    String uname = "root";
    String pass = "";
    
    IPTRevision(){
        setTitle("Student Enrollment System Sunway University");
        lblTitle = new JLabel("SUNWAY UNIVERSITY");
        
        lblName = new JLabel("Name : ");
        lblID = new JLabel("ID : ");
        lblEmail = new JLabel("Email : ");
        lblPL = new JLabel("Program Level : ");
        lblPN = new JLabel("Program Name : ");
        lblPS = new JLabel("Program Session : ");
        lblFee = new JLabel("Total Fee");
        
        lblOutput = new JLabel("Your Details Will be Show Here :");
        
        txtName = new JTextField(20);
        txtID = new JTextField(20);
        txtEmail = new JTextField(20);
        txtFee = new JTextField();
        txtDisplay = new JTextArea();

        cbPL = new JComboBox<>(new String[]{"Please select your Program","DIPLOMA", "DEGREE"});
        cbPN = new JComboBox<>(new String[]{"Please select your Program"});
        cbPS = new JComboBox<>(new String[]{"Please select your Program","2023", "2024", "2025"});
        
        
        // Initialize buttons
        btnSubmit = new JButton("Submit");
        btnView = new JButton("View");
        btnReset = new JButton("Reset");
        btnCalculate = new JButton("Calculate");
        
        add(lblTitle);
        add(lblName);
        add(lblID);
        add(lblEmail);
        add(lblPL);
        add(lblPN);
        add(lblPS);
        add(lblOutput);
        
        add(txtName);
        add(txtID);
        add(txtEmail);
        
        add(cbPL);
        add(cbPN);
        add(cbPS);
        
        add(lblFee);
        add(txtFee);
        txtFee.setEditable(false);
        
        
        add(btnSubmit);
        add(btnView);
        add(btnReset);
        add(btnCalculate);
        
        //addtionlistener
        btnCalculate.addActionListener(this);
        btnView.addActionListener(this);
        btnReset.addActionListener(this);
        btnSubmit.addActionListener(this);
        
        add(txtDisplay);
        
        lblTitle.setBounds(250, 20, 200, 30);
        lblName.setBounds(50, 60, 150, 30);
        lblID.setBounds(50, 100, 150, 30);
        lblEmail.setBounds(50, 140, 150, 30);
        lblPL.setBounds(50, 180, 150, 30);
        lblPN.setBounds(50, 220, 150, 30);
        lblPS.setBounds(50, 260, 150, 30);
        
        txtName.setBounds(180, 60, 200, 30);
        txtID.setBounds(180, 100, 200, 30);
        txtEmail.setBounds(180, 140, 200, 30);
        
        cbPL.setBounds(180, 180, 200, 30);
        cbPN.setBounds(180, 220, 200, 30);
        cbPS.setBounds(180, 260, 200, 30);
        
        lblFee.setBounds(50, 300, 150, 30);
        txtFee.setBounds(180, 300, 200, 30);

        btnSubmit.setBounds(50, 340, 100, 30);
        btnView.setBounds(160, 340, 100, 30);
        btnReset.setBounds(270, 340, 100, 30);
        btnCalculate.setBounds(380, 340, 100, 30); 

        lblOutput.setBounds(50, 380, 250, 30);
        txtDisplay.setBounds(50, 420, 350, 150);
        
        setLayout(null);
        setSize(700,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         cbPL.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedProgram = (String) e.getItem();
                    cbPN.removeAllItems();

                    if ("DIPLOMA".equals(selectedProgram)) {
                        cbPN.addItem("Diploma Program 1");
                        cbPN.addItem("Diploma Program 2");
                        cbPN.addItem("Diploma Program 3");
                        inewfee = 200; 
                    } else if ("DEGREE".equals(selectedProgram)) {
                        cbPN.addItem("Degree Program 1");
                        cbPN.addItem("Degree Program 2");
                        cbPN.addItem("Degree Program 3");
                        inewfee = 500; 
                    }
                }
            }
        });
        
    }

    public static void main(String[] args) {
        IPTRevision test = new IPTRevision();
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnCalculate) {
        Integer total = inewfee; // Use the correct variable name
        txtFee.setText(String.valueOf(total));
    } else if (e.getSource() == btnReset) {
        // Clear all text fields
        txtName.setText("");
        txtID.setText("");
        txtEmail.setText("");
        txtFee.setText("");
        txtDisplay.setText("");

        // Reset combo boxes to default state
        cbPL.setSelectedIndex(0); // Assuming the first item is the default
        cbPN.removeAllItems(); // Clear program name combo box
        cbPN.addItem("Please select your Program"); // Add default item back
        cbPS.setSelectedIndex(0); // Assuming the first item is the default
    } else if (e.getSource() == btnSubmit) {
        
        String name = txtName.getText();
        String student = txtID.getText();
        String email = txtEmail.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, uname, pass);
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO ipt (Name, Student, Email) " +
                         "VALUES ('"+name+"', '"+student+"', '"+email+"')";
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data successfully added");
            conn.close();
            
        //Project_IPT pipt = new Project_IPT();
        //pipt.setVisible(true);
        this.dispose();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
        }
    } else if (e.getSource() == btnView) {

        String name = txtName.getText();
        String id = txtID.getText();
        String email = txtEmail.getText();
        String programLevel = (String) cbPL.getSelectedItem();
        String programName = (String) cbPN.getSelectedItem();
        String programSession = (String) cbPS.getSelectedItem();
        String fee = txtFee.getText();

        txtDisplay.append("Name : "+ name + "\n");
        txtDisplay.append("ID : "+ id + "\n");
        txtDisplay.append("Email : "+ email + "\n");
        txtDisplay.append("Program Level : "+ programLevel + "\n");
        txtDisplay.append("program Name : "+ programName + "\n");
        txtDisplay.append("program Session : "+ programSession + "\n");
        txtDisplay.append("Fee : "+ fee + "\n");
    }
}

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}