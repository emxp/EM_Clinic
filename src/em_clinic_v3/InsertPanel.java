package em_clinic_v3;

import static em_clinic_v3.EM_Clinic_v3.mainTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InsertPanel extends JPanel {

    JPanel mainPanel, southPanel, deptManPanel, deptSouthPanl;

    static JDialog dialog = new JDialog();

    JTextField[] the_4_textFields = new JTextField[4];
    JLabel[] the_6_Labels = new JLabel[6];
    JLabel newDptLabel;
    JTextField neDeptTextField;
    JRadioButton genderRadioButtons[] = new JRadioButton[2];
    ButtonGroup buttonGroup;
    static JComboBox<String> deptCombo;
    JButton addDeptButton, saveButton, cancelButton;
    Font goodFont = new Font("Century Gothic", Font.BOLD, 13);

    InsertNewDeptPanel deptPanel = new InsertNewDeptPanel();
    PatientDAO dAO;

    public InsertPanel() {
        try {
            dAO = new PatientDAO();
        } catch (Exception ex) {
        }
        setLayout(new BorderLayout(10, 5));

        mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 29, 10));

        for (int r = 0; r < 6; r++) {
            the_6_Labels[r] = new JLabel();
            the_6_Labels[r].setFont(goodFont);
            if (r < 4) {
                the_4_textFields[r] = new JTextField(25);
                the_4_textFields[r].setFont(goodFont);
            }
            if (r < 2) {
                genderRadioButtons[r] = new JRadioButton();
                genderRadioButtons[r].setFont(goodFont);
            }
        }

        ////////////////////////////////////////////////////////////////////
        deptCombo = new JComboBox<>();

        assignDepartment();

        addDeptButton = new JButton(new ImageIcon(getClass().getResource("addpic.png")));
        buttonGroup = new ButtonGroup();

        saveButton = new JButton("  SAVE  ");
        cancelButton = new JButton("CANCEL");

        saveButton.setFont(goodFont);
        cancelButton.setFont(goodFont);

        buttonGroup.add(genderRadioButtons[0]);
        buttonGroup.add(genderRadioButtons[1]);

        the_6_Labels[0].setText("                      ID");
        the_6_Labels[1].setText("               NAME");
        the_6_Labels[2].setText("                   SEX");
        the_6_Labels[3].setText("                  AGE");
        the_6_Labels[4].setText("    DEPARTMENT");
        the_6_Labels[5].setText("CARD  NUMBER");

        genderRadioButtons[0].setText("MALE");
        genderRadioButtons[1].setText("FEMALE");

        deptCombo.setFont(goodFont);

        addDeptButton.addActionListener((es) -> {
            dialog.setTitle("Add A New Department");
            dialog.setModal(true);
            dialog.setSize(600, 130);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.add(deptPanel);

            dialog.setVisible(true);
        });

        cancelButton.addActionListener((e) -> {
            EM_Clinic_v3.insertionDialog.dispose();
        });

        saveButton.addActionListener(e -> {
            String id = the_4_textFields[0].getText();
            String name = the_4_textFields[1].getText();
            String sex = "";
            if (genderRadioButtons[0].isSelected()) {
                sex = "Male";
            } else if (genderRadioButtons[1].isSelected()) {
                sex = "Female";
            }

            int age = 0, card = 0;

            String dep = deptCombo.getSelectedItem().toString();

            try {
                age = Integer.parseInt(the_4_textFields[2].getText());
                card = Integer.parseInt(the_4_textFields[3].getText());
            } catch (NumberFormatException ee) {
                System.out.println(ee);
            }

            if (!validateEmptyInput(id, name, sex, the_4_textFields[2].getText(), dep, the_4_textFields[3].getText())) {
                tellInfo("Please fill all fields!");
            } else if (!validateAgeInput(the_4_textFields[2].getText())) {
                tellInfo("Age must be between 0 and 100!");
                the_4_textFields[2].setText(null);
            } else if (the_4_textFields[1].getBackground().equals(new Color(220, 100, 100)) || the_4_textFields[2].getBackground().equals(new Color(220, 100, 100))) {
                tellInfo("Please enter the correct format!");
            } else {
                try {
                    if (!dAO.searchPatients(card + "", "Card_Number").isEmpty()) {
                        tellInfo("There is a patient by the Card Number " + card + " ! \n\nPlease, change the Card Number.");
                        the_4_textFields[3].setText(null);
                    }else if (!dAO.searchPatients(id + "", "ID").isEmpty()) {
                        tellInfo("There is a patient by the ID " + id + " ! \n\nPlease, change the ID.");
                        the_4_textFields[0].setText(null);
                    } else {
                        Patient pat = new Patient(id, name, sex, age, dep, card);
                        try {
                            dAO.addPatient(pat);
                            tellSuccess();
                            refrashTable();
                            EM_Clinic_v3.insertionDialog.dispose();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(InsertPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        the_4_textFields[1].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if ((key >= e.VK_0 && key <= e.VK_9) || (key >= e.VK_NUMPAD0 && key <= e.VK_NUMPAD9)) {
                    the_4_textFields[1].setEditable(false);
                    the_4_textFields[1].setBackground(new Color(220, 100, 100));
                } else {
                    the_4_textFields[1].setEditable(true);
                    the_4_textFields[1].setBackground(null);
                }
            }
        });

        the_4_textFields[2].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                String theText = the_4_textFields[2].getText() + e.getKeyChar();

                if ((key >= e.VK_0 && key <= e.VK_9) || (key >= e.VK_NUMPAD0 && key <= e.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
                    the_4_textFields[2].setEditable(true);
                    the_4_textFields[2].setBackground(null);
                } else {
                    the_4_textFields[2].setEditable(false);
                    the_4_textFields[2].setBackground(new Color(220, 100, 100));
                }
            }
        });
        
        the_4_textFields[3].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                String theText = the_4_textFields[3].getText() + e.getKeyChar();

                if ((key >= e.VK_0 && key <= e.VK_9) || (key >= e.VK_NUMPAD0 && key <= e.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
                    the_4_textFields[3].setEditable(true);
                    the_4_textFields[3].setBackground(null);
                } else {
                    the_4_textFields[3].setEditable(false);
                    the_4_textFields[3].setBackground(new Color(220, 100, 100));
                }
            }
        });

        mainPanel.add(the_6_Labels[0]);
        mainPanel.add(the_4_textFields[0]);
        mainPanel.add(the_6_Labels[1]);
        mainPanel.add(the_4_textFields[1]);
        mainPanel.add(the_6_Labels[2]);
        mainPanel.add(genderRadioButtons[0]);
        mainPanel.add(new JLabel("                       "));
        mainPanel.add(genderRadioButtons[1]);
        mainPanel.add(new JLabel("                                   "));
        mainPanel.add(the_6_Labels[3]);
        mainPanel.add(the_4_textFields[2]);
        mainPanel.add(new JLabel("  "));
        mainPanel.add(the_6_Labels[4]);
        mainPanel.add(deptCombo);
        mainPanel.add(addDeptButton);
        mainPanel.add(the_6_Labels[5]);
        mainPanel.add(the_4_textFields[3]);

        southPanel.add(saveButton);
        southPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void assignDepartment() {
        PreparedStatement ps;
        Statement statement = null;
        ResultSet result = null;
        Connection g = null;
        try {
            g = DriverManager.getConnection("jdbc:mysql://localhost/patient", "root", "");
            statement = g.createStatement();
            result = statement.executeQuery("select *from department");

            while (result.next()) {
                deptCombo.addItem(result.getObject(1).toString());
            }
            statement.close();
            result.close();
            g.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void refrashTable() {
        try {
            PatientDAO patientDAO = new PatientDAO();
            List<Patient> patient = patientDAO.getAllPatient();
            PatientTableModel model = new PatientTableModel(patient);
            EM_Clinic_v3.mainTable.setModel(model);
            EM_Clinic_v3.statusLabel.setText("    " + mainTable.getRowCount() + "  Patients.");
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTextArea messageOnJOptionPane(String message) {
        JTextArea Label = new JTextArea(message);
        Label.setEditable(false);
        Label.setFont(new Font("Century Gothic", Font.BOLD, 14));
        return Label;
    }

    void tellInfo(String text) {
        JOptionPane.showMessageDialog(InsertPanel.this, messageOnJOptionPane(text), "Patient Information", JOptionPane.INFORMATION_MESSAGE);
    }

    void tellSuccess() {
        JOptionPane.showMessageDialog(InsertPanel.this, messageOnJOptionPane("Registration Successfull"), "Patient Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean validateEmptyInput(String id, String name, String sex, String age, String dept, String card) {
        //returns false if any string is empty
        return !(id.equals("") || name.equals("") || sex.equals("") || age.equals("") || dept.equals("") || card.equals(""));
    }

    public boolean validateAgeInput(String age) {
        //returns false if age is not between 0 and 100
        int intAge = Integer.parseInt(age);
        return intAge >= 0 && intAge <= 100;
    }

    public void makeNightMode() {
        mainPanel.setBackground(new Color(83, 83, 83));
        southPanel.setBackground(new Color(83, 83, 83));

        for (int r = 0; r < the_4_textFields.length; r++) {
            the_4_textFields[r].setBackground(new Color(100, 100, 100));
            the_4_textFields[r].setForeground(Color.WHITE);
        }

        for (int r = 0; r < the_6_Labels.length; r++) {
            the_6_Labels[r].setForeground(Color.WHITE);
        }

        addDeptButton.setBackground(new Color(60, 60, 60));
        addDeptButton.setForeground(new Color(255, 255, 255));
        saveButton.setBackground(new Color(60, 60, 60));
        saveButton.setForeground(new Color(255, 255, 255));
        cancelButton.setBackground(new Color(60, 60, 60));
        cancelButton.setForeground(new Color(255, 255, 255));

        deptCombo.setBackground(new Color(60, 60, 60));
        genderRadioButtons[0].setForeground(Color.WHITE);
        genderRadioButtons[1].setForeground(Color.WHITE);

        deptPanel.makeNightMode();
    }

    public void makeDayMode() {
        mainPanel.setBackground(null);
        southPanel.setBackground(null);

        for (int r = 0; r < the_4_textFields.length; r++) {
            the_4_textFields[r].setBackground(null);
            the_4_textFields[r].setForeground(null);
        }

        for (int r = 0; r < the_6_Labels.length; r++) {
            the_6_Labels[r].setForeground(null);
        }

        addDeptButton.setBackground(null);
        addDeptButton.setForeground(null);
        saveButton.setBackground(null);
        saveButton.setForeground(null);
        cancelButton.setBackground(null);
        cancelButton.setForeground(null);

        deptCombo.setBackground(null);
        genderRadioButtons[0].setForeground(null);
        genderRadioButtons[1].setForeground(null);

        deptPanel.makeDayMode();
    }
}
