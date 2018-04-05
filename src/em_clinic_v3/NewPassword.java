/*
 * @This code is written by: Endriyas in May 22, 2017
 * I assure that my code is 100% mistake free!!
 */
package em_clinic_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

public class NewPassword extends JPanel {

    JPanel deptMainPanel, deptSouthPanel;
    JLabel oldLabel, newLabel, conLabel, incorrectLabel;
    JPasswordField oldField, newField, confirmField;
    JButton deptSaveButton, deptCancelButton;
    Font goodFont = new Font("Century Gothic", Font.BOLD, 13);

    public NewPassword() {
        setLayout(new BorderLayout(10, 10));

        deptMainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        deptSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 29, 10));

        oldLabel = new JLabel("       Old Password");
        newLabel = new JLabel("      New Password");
        conLabel = new JLabel("Confirm Password");
        incorrectLabel = new JLabel("");

        oldLabel.setFont(goodFont);
        newLabel.setFont(goodFont);
        conLabel.setFont(goodFont);
        incorrectLabel.setFont(new Font("Century Gothic", Font.PLAIN, 13));
        incorrectLabel.setForeground(Color.red);

        oldField = new JPasswordField(30);
        newField = new JPasswordField(30);
        confirmField = new JPasswordField(30);
        oldField.setFont(goodFont);
        newField.setFont(goodFont);
        confirmField.setFont(goodFont);

        deptSaveButton = new JButton("  CHANGE  ");
        deptSaveButton.setFont(goodFont);

        deptCancelButton = new JButton("CANCEL");
        deptCancelButton.setFont(goodFont);

        deptCancelButton.addActionListener((e) -> {
            EM_Clinic_v3.dialog.dispose();
        });

        deptSaveButton.addActionListener(e -> {
            String old = String.valueOf(oldField.getPassword());
            String newP = String.valueOf(newField.getPassword());
            String conP = String.valueOf(confirmField.getPassword());
            if (old.equals(getPassword())) {//chaking the correctness of the pasword
                if (newP.equals(conP)) {
                    updatePassword(old, newP);
                    tellSuccess();
                    EM_Clinic_v3.dialog.dispose();
                } else {
                    incorrectLabel.setText("The Passwords you type do not match Please retype!");
                }
            } else {
                incorrectLabel.setText("Incorrect Old Password!");
            }
        });

        deptMainPanel.add(oldLabel);
        deptMainPanel.add(oldField);
        deptMainPanel.add(newLabel);
        deptMainPanel.add(newField);
        deptMainPanel.add(conLabel);
        deptMainPanel.add(confirmField);
        deptMainPanel.add(incorrectLabel);

        deptSouthPanel.add(deptSaveButton);
        deptSouthPanel.add(deptCancelButton);

        add(deptMainPanel, BorderLayout.CENTER);
        add(deptSouthPanel, BorderLayout.SOUTH);
    }

    public String getPassword() {
        Statement statement = null;
        ResultSet result = null;
        Connection g = null;
        String pass = null;
        try {
            g = DriverManager.getConnection("jdbc:mysql://localhost/patient", "root", "");
            statement = g.createStatement();
            result = statement.executeQuery("select *from user");

            while (result.next()) {
                pass = result.getObject(1).toString();
            }
            statement.close();
            result.close();
            g.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pass;
    }

    public void updatePassword(String oldpass, String newpass) {
        PreparedStatement myStmt = null;
        Connection g = null;
        try {
            g = DriverManager.getConnection("jdbc:mysql://localhost/patient", "root", "");
            myStmt = g.prepareStatement("update user"
                    + " set password=?"
                    + " where password=?");

            myStmt.setString(1, newpass);
            myStmt.setString(2, oldpass);

            myStmt.executeUpdate();
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

    void tellSuccess() {
        JOptionPane.showMessageDialog(NewPassword.this, messageOnJOptionPane("Change Password Successfull"), "Patient Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public void makeNightMode() {
        deptMainPanel.setBackground(new Color(83, 83, 83));
        deptSouthPanel.setBackground(new Color(83, 83, 83));

        oldLabel.setForeground(Color.WHITE);
        newLabel.setForeground(Color.WHITE);
        conLabel.setForeground(Color.WHITE);

        oldField.setBackground(new Color(100, 100, 100));
        oldField.setForeground(Color.WHITE);
        confirmField.setBackground(new Color(100, 100, 100));
        confirmField.setForeground(Color.WHITE);
        newField.setBackground(new Color(100, 100, 100));
        newField.setForeground(Color.WHITE);

        deptSaveButton.setBackground(new Color(60, 60, 60));
        deptSaveButton.setForeground(new Color(255, 255, 255));
        deptCancelButton.setBackground(new Color(60, 60, 60));
        deptCancelButton.setForeground(new Color(255, 255, 255));
    }

    public void makeDayMode() {
        deptMainPanel.setBackground(null);
        deptSouthPanel.setBackground(null);

        oldLabel.setForeground(null);
        newLabel.setForeground(null);
        conLabel.setForeground(null);

        oldField.setBackground(null);
        oldField.setForeground(null);
        confirmField.setBackground(null);
        confirmField.setForeground(null);
        newField.setBackground(null);
        newField.setForeground(null);

        deptSaveButton.setBackground(null);
        deptSaveButton.setForeground(null);
        deptCancelButton.setBackground(null);
        deptCancelButton.setForeground(null);
    }
}
