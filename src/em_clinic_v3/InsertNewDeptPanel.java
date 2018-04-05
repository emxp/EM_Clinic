package em_clinic_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InsertNewDeptPanel extends JPanel {
    JPanel deptMainPanel, deptSouthPanel;
    JLabel newDeptLabel;
    JTextField newDeptTextField;
    JButton deptSaveButton, deptCancelButton;
    Font goodFont = new Font("Century Gothic", Font.BOLD, 13);
    
    public InsertNewDeptPanel() {
        setLayout(new BorderLayout(10, 10));
        
        deptMainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        deptSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 29, 10));
        
        newDeptLabel = new JLabel("Department Name");
        
        newDeptLabel.setFont(goodFont);
        
        newDeptTextField = new JTextField(30);
        newDeptTextField.setFont(goodFont);
        
        deptSaveButton = new JButton("  ADD  ");
        deptSaveButton.setFont(goodFont);
        
        deptCancelButton = new JButton("CANCEL");
        deptCancelButton.setFont(goodFont);
        
        deptCancelButton.addActionListener((e) -> {
            InsertPanel.dialog.dispose();
        });
        
        newDeptTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if ((key >= e.VK_0 && key <= e.VK_9) || (key >= e.VK_NUMPAD0 && key <= e.VK_NUMPAD9)) {
                    newDeptTextField.setEditable(false);
                    newDeptTextField.setBackground(new Color(220, 100, 100));
                } else {
                    newDeptTextField.setEditable(true);
                    newDeptTextField.setBackground(null);
                }
            }
        });
        
        deptSaveButton.addActionListener((e) -> {
            String theText = newDeptTextField.getText();
            
            if(!(theText.equals(""))) {
                insertDepartment(theText);
                InsertPanel.deptCombo.addItem(theText);
                UpdatePanel.deptCombo.addItem(theText);
                InsertPanel.dialog.dispose();
            }else{
                tellFailure();
            }
        });
        
        deptMainPanel.add(newDeptLabel);
        deptMainPanel.add(newDeptTextField);
        
        deptSouthPanel.add(deptSaveButton);
        deptSouthPanel.add(deptCancelButton);
        
        add(deptMainPanel, BorderLayout.CENTER);
        add(deptSouthPanel, BorderLayout.SOUTH);
    }
    
    public void insertDepartment(String dept) {
        PreparedStatement myStmt = null;
        Connection g = null;
        try {
            g = DriverManager.getConnection("jdbc:mysql://localhost/patient", "root", "");
            myStmt = g.prepareStatement("insert into department(deptName) values (?)");
            myStmt.setString(1, dept);

            myStmt.executeUpdate();
            
            g.close();
            myStmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public JTextArea messageOnJOptionPane(String message) {
        JTextArea Label = new JTextArea(message);
        Label.setEditable(false);
        Label.setFont(new Font("Century Gothic", Font.BOLD, 14));
        return Label;
    }

    void tellFailure() {
        JOptionPane.showMessageDialog(InsertNewDeptPanel.this, messageOnJOptionPane("Please insert valid Department name!"), "Patient Information", JOptionPane.WARNING_MESSAGE);
    }
    
    public void makeNightMode() {
        deptMainPanel.setBackground(new Color(83, 83, 83));
        deptSouthPanel.setBackground(new Color(83, 83, 83));
        newDeptLabel.setForeground(Color.WHITE);
        
        newDeptTextField.setBackground(new Color(100, 100, 100));
        newDeptTextField.setForeground(Color.WHITE);
        
        deptSaveButton.setBackground(new Color(60, 60, 60));
        deptSaveButton.setForeground(new Color(255, 255, 255));
        deptCancelButton.setBackground(new Color(60, 60, 60));
        deptCancelButton.setForeground(new Color(255, 255, 255));   
    }
    
    public void makeDayMode() {
        deptMainPanel.setBackground(null);
        deptSouthPanel.setBackground(null);
        newDeptLabel.setForeground(null);
        
        newDeptTextField.setBackground(null);
        newDeptTextField.setForeground(null);
        
        deptSaveButton.setBackground(null);
        deptSaveButton.setForeground(null);
        deptCancelButton.setBackground(null);
        deptCancelButton.setForeground(null);   
    }
}
