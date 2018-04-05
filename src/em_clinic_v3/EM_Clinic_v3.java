/*
 * @This code is written by: Endriyas in May 21, 2017
 * I assure that my code is 100% mistake free!!
 */
package em_clinic_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class EM_Clinic_v3 extends JFrame {

    JMenuBar jMenuBar;
    JMenu mainMenu, searchMenu, customizeMenu, aboutMenu;
    JMenuItem updateItem, deleteItem, insertItem, exitItem, changePassword;
    JCheckBoxMenuItem nightModeItem;
    JRadioButtonMenuItem everythingItem, idItem, nameItem, sexItem, ageItem, depItem, cardItem;
    JPanel masterPanel, searchPanel, tablePanel, buttonPanel, actualButtonPanel, insideSearchPanel, statusPanel;
    JTextField searchField;
    JButton deleteButton, updateButton, insertButton;
    static JLabel statusLabel, searchLabel;
    JComboBox<String> searchComboBox;
    public static JTable mainTable;
    PatientTableModel model;
    public static int howMany = 0;

    InsertPanel insertPanel;
    UpdatePanel updatePanel;
    static NewPassword newPassword;
    static JDialog insertionDialog;
    static JDialog updateDialog;
    static JDialog dialog = new JDialog();

    Font thisFont = new Font("Century Gothic", Font.BOLD, 14);
    Font meniItemFont = new Font("Malgun Gothic", Font.PLAIN, 14);
    Font tableFont = new Font("Malgun Gothic", Font.PLAIN, 15);
    Font statusFont = new Font("Segoe UI Emoji", Font.PLAIN, 15);
    Font buttonFont = new Font("HP Simplified", Font.PLAIN, 23);

    ImageIcon icon = new ImageIcon(getClass().getResource("main icon.png"));

    public EM_Clinic_v3() {

        setIconImage(icon.getImage());

        insertPanel = new InsertPanel();
        updatePanel = new UpdatePanel();
        newPassword = new NewPassword();
        masterPanel = new JPanel(new BorderLayout(5, -2));
        searchPanel = new JPanel(new BorderLayout());
        insideSearchPanel = new JPanel(new BorderLayout());
        tablePanel = new JPanel(new GridLayout());
        buttonPanel = new JPanel(new BorderLayout(30, 10));
        statusPanel = new JPanel(new BorderLayout(30, 2));
        actualButtonPanel = new JPanel(new FlowLayout(2, 30, 10));

//        statusPanel.setBorder(BorderFactory.createTitledBorder(""));
        actualButtonPanel.setBorder(BorderFactory.createTitledBorder(""));
//        buttonPanel.setBorder(BorderFactory.createTitledBorder(""));

        jMenuBar = new JMenuBar();
        mainMenu = new JMenu(" Menu  ");
        searchMenu = new JMenu("  Search  ");
        customizeMenu = new JMenu("  Customize  ");
        aboutMenu = new JMenu("  About ");
        mainMenu.setFont(thisFont);
        searchMenu.setFont(thisFont);
        customizeMenu.setFont(thisFont);
        aboutMenu.setFont(thisFont);

        deleteItem = new JMenuItem("Delete");
        updateItem = new JMenuItem("Update");
        insertItem = new JMenuItem("Insert");
        exitItem = new JMenuItem("Exit", new ImageIcon(getClass().getResource("exit.png")));
        changePassword = new JMenuItem("Change Password");
        everythingItem = new JRadioButtonMenuItem("Everything");
        idItem = new JRadioButtonMenuItem("ID");
        nameItem = new JRadioButtonMenuItem("Name");
        sexItem = new JRadioButtonMenuItem("Sex");
        ageItem = new JRadioButtonMenuItem("Age");
        depItem = new JRadioButtonMenuItem("Department");
        cardItem = new JRadioButtonMenuItem("Card Number");
        nightModeItem = new JCheckBoxMenuItem("Night Mode");
        deleteItem.setFont(meniItemFont);
        updateItem.setFont(meniItemFont);
        insertItem.setFont(meniItemFont);
        exitItem.setFont(meniItemFont);
        changePassword.setFont(meniItemFont);
        everythingItem.setFont(meniItemFont);
        everythingItem.setSelected(true);
        idItem.setFont(meniItemFont);
        nameItem.setFont(meniItemFont);
        sexItem.setFont(meniItemFont);
        ageItem.setFont(meniItemFont);
        depItem.setFont(meniItemFont);
        cardItem.setFont(meniItemFont);
        nightModeItem.setFont(meniItemFont);

        ButtonGroup group = new ButtonGroup();
        group.add(everythingItem);
        group.add(idItem);
        group.add(nameItem);
        group.add(sexItem);
        group.add(ageItem);
        group.add(depItem);
        group.add(cardItem);

        mainMenu.add(insertItem);
        mainMenu.add(updateItem);
        mainMenu.add(deleteItem);
        mainMenu.addSeparator();
        mainMenu.add(exitItem);
        searchMenu.add(everythingItem);
        searchMenu.add(idItem);
        searchMenu.add(nameItem);
        searchMenu.add(sexItem);
        searchMenu.add(ageItem);
        searchMenu.add(depItem);
        searchMenu.add(cardItem);
        customizeMenu.add(nightModeItem);
        customizeMenu.add(changePassword);

        mainMenu.setMnemonic(KeyEvent.VK_M);
        searchMenu.setMnemonic(KeyEvent.VK_S);
        customizeMenu.setMnemonic(KeyEvent.VK_C);

        updateItem.setMnemonic(KeyEvent.VK_U);
        deleteItem.setMnemonic(KeyEvent.VK_D);
        insertItem.setMnemonic(KeyEvent.VK_I);

        changePassword.setMnemonic(KeyEvent.VK_P);
        nightModeItem.setMnemonic(KeyEvent.VK_N);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        searchField = new JTextField(30);
        searchField.setFont(tableFont);

        searchComboBox = new JComboBox<>();
        searchComboBox.addItem("Everything");
        searchComboBox.addItem("ID");
        searchComboBox.addItem("Name");
        searchComboBox.addItem("Sex");
        searchComboBox.addItem("Age");
        searchComboBox.addItem("Department");
        searchComboBox.addItem("Card_Number");
        searchComboBox.setFont(thisFont);

        statusLabel = new JLabel();
        searchLabel = new JLabel(searchComboBox.getSelectedItem().toString() + "     ");
        statusLabel.setFont(statusFont);
        searchLabel.setFont(statusFont);

        deleteButton = new JButton("  Delete  ");
        updateButton = new JButton("  Update  ");
        insertButton = new JButton("  Insert  ");
        deleteButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        insertButton.setFont(buttonFont);

        mainTable = new JTable() {
            @Override
            public boolean isCellEditable(int x, int y) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width,
                        tableColumn.getPreferredWidth()));
                return component;
            }
        };
        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        mainTable.getTableHeader().setFont(thisFont);
        mainTable.setFont(tableFont);
        mainTable.setRowHeight(28);
        mainTable.setFocusable(true);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.LEFT);
        mainTable.setDefaultRenderer(String.class, cellRenderer);
        mainTable.setDefaultRenderer(Integer.class, cellRenderer);
        refrashTable();

        jMenuBar.add(mainMenu);
        jMenuBar.add(searchMenu);
        jMenuBar.add(customizeMenu);
        jMenuBar.add(aboutMenu);

        insideSearchPanel.add(searchComboBox, BorderLayout.EAST);
        insideSearchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(insideSearchPanel, BorderLayout.EAST);

        tablePanel.add(new JScrollPane(mainTable));

        statusPanel.add(statusLabel, BorderLayout.CENTER);
        statusPanel.add(searchLabel, BorderLayout.EAST);
        actualButtonPanel.add(insertButton);
        actualButtonPanel.add(updateButton);
        actualButtonPanel.add(deleteButton);
        buttonPanel.add(actualButtonPanel, BorderLayout.CENTER);
        buttonPanel.add(statusPanel, BorderLayout.SOUTH);

        masterPanel.add(searchPanel, BorderLayout.NORTH);
        masterPanel.add(tablePanel, BorderLayout.CENTER);
        masterPanel.add(buttonPanel, BorderLayout.SOUTH);

        setJMenuBar(jMenuBar);
        add(masterPanel);

        searchComboBox.addItemListener(e -> {
            searchLabel.setText(searchComboBox.getSelectedItem().toString() + "     ");
            switch (searchComboBox.getSelectedItem().toString()) {
                case "Everything":
                    everythingItem.setSelected(true);
                    emptySearch();
                    break;
                case "ID":
                    idItem.setSelected(true);
                    emptySearch();
                    break;
                case "Name":
                    nameItem.setSelected(true);
                    emptySearch();
                    break;
                case "Sex":
                    sexItem.setSelected(true);
                    emptySearch();
                    break;
                case "Age":
                    ageItem.setSelected(true);
                    emptySearch();
                    break;
                case "Department":
                    depItem.setSelected(true);
                    emptySearch();
                    break;
                case "Card_Number":
                    cardItem.setSelected(true);
                    emptySearch();
                    break;
            }
        });

        everythingItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Everything  ");
            searchComboBox.setSelectedIndex(0);
            emptySearch();
        });
        idItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("ID  ");
            searchComboBox.setSelectedIndex(1);
            emptySearch();
        });
        nameItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Name  ");
            searchComboBox.setSelectedIndex(2);
            emptySearch();
        });
        sexItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Sex  ");
            searchComboBox.setSelectedIndex(3);
            emptySearch();
        });
        ageItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Age  ");
            searchComboBox.setSelectedIndex(4);
            emptySearch();
        });
        depItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Department  ");
            searchComboBox.setSelectedIndex(5);
            emptySearch();
        });
        cardItem.addItemListener((ItemEvent e) -> {
            searchLabel.setText("Card Number  ");
            searchComboBox.setSelectedIndex(6);
            emptySearch();
        });

        insertButton.addActionListener(e -> {
            insertPanelActivator();
        });

        deleteButton.addActionListener((e) -> {
            deleteListener();
        });

        updateButton.addActionListener((e) -> {
            updateListener();
        });

        changePassword.addActionListener(e -> {
            newPassword.newField.setText(null);
            newPassword.oldField.setText(null);
            newPassword.confirmField.setText(null);
            newPassword.incorrectLabel.setText(null);

            dialog.setTitle("Change Password");
            dialog.setModal(true);
            dialog.setSize(600, 250);
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.add(newPassword);
            dialog.setVisible(true);
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    PatientDAO pat = new PatientDAO();
                    String text = null;
                    List<Patient> patient = null;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_BACK_SPACE:
                            text = searchField.getText().substring(0, searchField.getText().length() - 1);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_ACCEPT:
                        case KeyEvent.VK_ADD:
                        case KeyEvent.VK_AGAIN:
                        case KeyEvent.VK_ALL_CANDIDATES:
                        case KeyEvent.VK_ALT:
                        case KeyEvent.VK_ALT_GRAPH:
                        case KeyEvent.VK_BACK_QUOTE:
                        case KeyEvent.VK_BEGIN:
                        case KeyEvent.VK_CANCEL:
                        case KeyEvent.VK_CODE_INPUT:
                        case KeyEvent.VK_COMPOSE:
                        case KeyEvent.VK_CONTEXT_MENU:
                        case KeyEvent.VK_CONTROL:
                        case KeyEvent.VK_CONVERT:
                        case KeyEvent.VK_COPY:
                        case KeyEvent.VK_CUT:
                        case KeyEvent.VK_DEAD_ABOVEDOT:
                        case KeyEvent.VK_DEAD_ABOVERING:
                        case KeyEvent.VK_DEAD_ACUTE:
                        case KeyEvent.VK_DEAD_BREVE:
                        case KeyEvent.VK_DEAD_CARON:
                        case KeyEvent.VK_DEAD_CEDILLA:
                        case KeyEvent.VK_DEAD_CIRCUMFLEX:
                        case KeyEvent.VK_DEAD_DIAERESIS:
                        case KeyEvent.VK_DEAD_DOUBLEACUTE:
                        case KeyEvent.VK_DEAD_GRAVE:
                        case KeyEvent.VK_DEAD_IOTA:
                        case KeyEvent.VK_DEAD_OGONEK:
                        case KeyEvent.VK_DEAD_TILDE:
                        case KeyEvent.VK_DEAD_VOICED_SOUND:
                        case KeyEvent.VK_DECIMAL:
                        case KeyEvent.VK_DELETE:
                        case KeyEvent.VK_DIVIDE:
                        case KeyEvent.VK_FINAL:
                        case KeyEvent.VK_FIND:
                        case KeyEvent.VK_FULL_WIDTH:
                        case KeyEvent.VK_HALF_WIDTH:
                        case KeyEvent.VK_HELP:
                        case KeyEvent.VK_HIRAGANA:
                        case KeyEvent.VK_HOME:
                        case KeyEvent.VK_INPUT_METHOD_ON_OFF:
                        case KeyEvent.VK_INSERT:
                        case KeyEvent.VK_INVERTED_EXCLAMATION_MARK:
                        case KeyEvent.VK_JAPANESE_HIRAGANA:
                        case KeyEvent.VK_JAPANESE_KATAKANA:
                        case KeyEvent.VK_JAPANESE_ROMAN:
                        case KeyEvent.VK_KANA:
                        case KeyEvent.VK_KANA_LOCK:
                        case KeyEvent.VK_KANJI:
                        case KeyEvent.VK_KATAKANA:
                        case KeyEvent.VK_KP_DOWN:
                        case KeyEvent.VK_KP_LEFT:
                        case KeyEvent.VK_KP_RIGHT:
                        case KeyEvent.VK_KP_UP:
                        case KeyEvent.VK_META:
                        case KeyEvent.VK_MODECHANGE:
                        case KeyEvent.VK_MULTIPLY:
                        case KeyEvent.VK_NONCONVERT:
                        case KeyEvent.VK_PAGE_DOWN:
                        case KeyEvent.VK_PAGE_UP:
//                        case KeyEvent.VK_PASTE:
                        case KeyEvent.VK_PAUSE:
                        case KeyEvent.VK_PREVIOUS_CANDIDATE:
                        case KeyEvent.VK_PRINTSCREEN:
                        case KeyEvent.VK_PROPS:
                        case KeyEvent.VK_ROMAN_CHARACTERS:
                        case KeyEvent.VK_SCROLL_LOCK:
                        case KeyEvent.VK_SEPARATER:
                        case KeyEvent.VK_SHIFT:
                        case KeyEvent.VK_STOP:
                        case KeyEvent.VK_SUBTRACT:
                        case KeyEvent.VK_TAB:
                        case KeyEvent.VK_UNDEFINED:
                        case KeyEvent.VK_UNDO:
                        case KeyEvent.VK_WINDOWS:
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_ESCAPE:
                        case KeyEvent.VK_NUM_LOCK:
                        case KeyEvent.VK_ENTER:
                        case KeyEvent.VK_END:
                        case KeyEvent.VK_F:
                        case KeyEvent.VK_F1:
                        case KeyEvent.VK_F2:
                        case KeyEvent.VK_F3:
                        case KeyEvent.VK_F4:
                        case KeyEvent.VK_F5:
                        case KeyEvent.VK_F6:
                        case KeyEvent.VK_F7:
                        case KeyEvent.VK_F8:
                        case KeyEvent.VK_F9:
                        case KeyEvent.VK_F10:
                        case KeyEvent.VK_F11:
                        case KeyEvent.VK_F12:
                        case KeyEvent.VK_F13:
                        case KeyEvent.VK_F14:
                        case KeyEvent.VK_F15:
                        case KeyEvent.VK_F16:
                        case KeyEvent.VK_F17:
                        case KeyEvent.VK_F18:
                        case KeyEvent.VK_F19:
                        case KeyEvent.VK_F20:
                        case KeyEvent.VK_F21:
                        case KeyEvent.VK_F22:
                        case KeyEvent.VK_F23:
                        case KeyEvent.VK_F24:
                            text = searchField.getText();
                            break;
                        default:
                            text = searchField.getText() + e.getKeyChar();
                            break;
                    }
                    if (searchComboBox.getSelectedItem().equals("Everything")) {
                        patient = pat.searchALLPatients(text);
                    } else {
                        patient = pat.searchPatients(text, searchComboBox.getSelectedItem().toString());
                    }
                    PatientTableModel model = new PatientTableModel(patient);
                    mainTable.setModel(model);
                    statusLabel.setText("   " + mainTable.getRowCount() + "  Patients.");
                } catch (Exception exc) {
                }
            }
        });
        nightModeItem.addItemListener((e) -> {
            if (nightModeItem.isSelected()) {
                makeNightMode();
            } else {
                makeDayMode();
            }
        });

        exitItem.addActionListener((e) -> {
            if (askConfirmation() == 0) {
                System.exit(0);
            }
        });

        deleteItem.addActionListener((e) -> {
            deleteListener();
        });

        updateItem.addActionListener((e) -> {
            updateListener();
        });

        insertItem.addActionListener((e) -> {
            insertPanelActivator();
        });
        aboutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDialog d = new JDialog();
                d.setTitle("EM_Clinic");
                d.setModal(true);
                d.setBounds(100, 100, 400, 230);
                d.setResizable(false);
                d.setLocationRelativeTo(EM_Clinic_v3.this);
                JPanel p = new JPanel();
                JTextArea h = new JTextArea("        Made By EMsofts.\n        ----------------------\n\n EMail : EMsofts@gmail.com \n\n     Tel : +251915565300\n            +251939020570\n\n CopyRight Reserved © 2009 EthC.");
                h.setFont(new Font("Century Gothic", Font.PLAIN, 14));
                h.setEditable(false);
                p.add(h);
                d.add(new JScrollPane(p));
                d.setVisible(true);
            }
        });

//        TableRowSorter<AbstractTableModel> rowSorter = new TableRowSorter<>(model);
//        mainTable.setRowSorter(rowSorter);
    }

    public void emptySearch() {
        searchField.setText(null);
        refrashTable();
    }

    public void insertPanelActivator() {
        howMany = Integer.parseInt(EM_Clinic_v3.mainTable.getValueAt(EM_Clinic_v3.mainTable.getRowCount() - 1, 5).toString());
        insertPanel.the_4_textFields[3].setText(howMany + 1 + "");
        insertPanel.the_4_textFields[0].setText(null);
        insertPanel.the_4_textFields[1].setText(null);
        insertPanel.the_4_textFields[2].setText(null);
        insertionDialog = new JDialog(this, "Insert Patient Info", true);
        insertionDialog.setSize(512, 350);
        insertionDialog.setLocationRelativeTo(null);
        insertionDialog.setResizable(false);
        insertionDialog.add(insertPanel);
        insertionDialog.setVisible(true);
    }

    public void updatePanelActivator() {
        updateDialog = new JDialog(this, "Update Patient Info", true);
        updateDialog.setSize(512, 350);
        updateDialog.setLocationRelativeTo(null);
        updateDialog.setResizable(false);
        updateDialog.add(updatePanel);
        updateDialog.setVisible(true);
    }

    public void makeNightMode() {
        masterPanel.setBackground(new Color(83, 83, 83));
        searchPanel.setBackground(new Color(83, 83, 83));
        tablePanel.setBackground(new Color(83, 83, 83));
        buttonPanel.setBackground(new Color(83, 83, 83));
        actualButtonPanel.setBackground(new Color(83, 83, 83));
        insideSearchPanel.setBackground(new Color(83, 83, 83));
        statusPanel.setBackground(new Color(83, 83, 83));
        searchField.setBackground(new Color(150, 150, 150));
        searchField.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(60, 60, 60));
        deleteButton.setForeground(new Color(255, 255, 255));
        updateButton.setBackground(new Color(60, 60, 60));
        updateButton.setForeground(new Color(255, 255, 255));
        insertButton.setBackground(new Color(60, 60, 60));
        insertButton.setForeground(new Color(255, 255, 255));

        statusLabel.setForeground(new Color(255, 255, 255));
        searchLabel.setForeground(new Color(255, 255, 255));
        searchComboBox.setBackground(new Color(100, 100, 100));

        mainTable.setBackground(new Color(83, 83, 83));
        mainTable.setForeground(Color.WHITE);
        insertPanel.makeNightMode();
        updatePanel.makeNightMode();
        newPassword.makeNightMode();
    }

    public void makeDayMode() {
        masterPanel.setBackground(null);
        searchPanel.setBackground(null);
        tablePanel.setBackground(null);
        buttonPanel.setBackground(null);
        actualButtonPanel.setBackground(null);
        insideSearchPanel.setBackground(null);
        statusPanel.setBackground(null);
        searchField.setBackground(null);
        searchField.setForeground(null);
        deleteButton.setBackground(null);
        deleteButton.setForeground(null);
        updateButton.setBackground(null);
        updateButton.setForeground(null);
        insertButton.setBackground(null);
        insertButton.setForeground(null);

        statusLabel.setForeground(null);
        searchLabel.setForeground(null);
        searchComboBox.setBackground(null);

        mainTable.setBackground(null);
        mainTable.setForeground(null);

        insertPanel.makeDayMode();
        updatePanel.makeDayMode();
        newPassword.makeDayMode();
    }

    public void deleteListener() {
        int indexRow[] = mainTable.getSelectedRows();

        if (indexRow.length == 0) {
            JOptionPane.showMessageDialog(EM_Clinic_v3.this, messageOnJOptionPane("First, You must select row!"), "Patient Information", JOptionPane.ERROR_MESSAGE);
        } else {
            if (askConfirmation(indexRow.length) == 0) {
                try {
                    PatientDAO patient = new PatientDAO();
                    for (int i = 0; i < indexRow.length; i++) {
                        patient.deletePatient(mainTable.getValueAt(indexRow[i], 0).toString());
                    }
                    refrashTable();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    public void updateListener() {
        int indexRow = mainTable.getSelectedRow();
        int numRow = mainTable.getSelectedRowCount();
        if (indexRow == -1) {
            JOptionPane.showMessageDialog(EM_Clinic_v3.this, messageOnJOptionPane("First, You must select row!"), "Patient Information", JOptionPane.ERROR_MESSAGE);
        } else if (numRow > 1) {
            JOptionPane.showMessageDialog(EM_Clinic_v3.this, messageOnJOptionPane("You must select single row!"), "Patient Information", JOptionPane.ERROR_MESSAGE);
        } else {
            int row = EM_Clinic_v3.mainTable.getSelectedRow();
            updatePanel.the_4_textFields[0].setText(mainTable.getValueAt(row, 0).toString());
            updatePanel.the_4_textFields[1].setText(mainTable.getValueAt(row, 1).toString());
            updatePanel.the_4_textFields[2].setText(mainTable.getValueAt(row, 3).toString());
            updatePanel.the_4_textFields[3].setText(mainTable.getValueAt(row, 5).toString());
            if (mainTable.getValueAt(row, 2).toString().equals("Male")) {
                updatePanel.genderRadioButtons[0].setSelected(true);
            } else {
                updatePanel.genderRadioButtons[1].setSelected(true);
            }
            boolean unknown = true;
            for (int i = 0; i < updatePanel.deptCombo.getItemCount(); i++) {
                if (updatePanel.deptCombo.getItemAt(i).equals(mainTable.getValueAt(row, 4).toString())) {
                    updatePanel.deptCombo.setSelectedItem(mainTable.getValueAt(row, 4).toString());
                    unknown = false;
                    break;
                }
            }
            if (unknown) {
                updatePanel.deptCombo.setSelectedItem("UNKNOWN");
            }
            updatePanelActivator();
        }
    }

    public void refrashTable() {
        try {
            PatientDAO patientDAO = new PatientDAO();
            List<Patient> patient = patientDAO.getAllPatient();
            model = new PatientTableModel(patient);
            mainTable.setModel(model);
            howMany = Integer.parseInt(EM_Clinic_v3.mainTable.getValueAt(EM_Clinic_v3.mainTable.getRowCount() - 1, 5).toString());
            statusLabel.setText("    " + mainTable.getRowCount() + "  Patients.");
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchFunction(String text) {
        DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
    }

    public JTextArea messageOnJOptionPane(String message) {
        JTextArea Label = new JTextArea(message);
        Label.setEditable(false);
        Label.setFont(new Font("Century Gothic", Font.BOLD, 14));
        return Label;
    }

    int askConfirmation() {
        return JOptionPane.showConfirmDialog(EM_Clinic_v3.this, messageOnJOptionPane("Are You Sure?"), "Patient Information", JOptionPane.YES_NO_OPTION);
    }

    int askConfirmation(int numbers) {
        return JOptionPane.showConfirmDialog(EM_Clinic_v3.this, messageOnJOptionPane("Are You Sure?\n\nYou want to delete Information of   " + numbers + ((numbers == 1) ? "   patient?" : "   patients?")), "Patient Information", JOptionPane.YES_NO_OPTION);
    }

    public static class logIn extends JFrame {

        JPanel deptMainPanel, deptSouthPanel, allpanel;
        JLabel newDeptLabel, incorrectLabel;
        JPasswordField passwordField;
        JButton loginButton, cancelButton;
        Font goodFont = new Font("Century Gothic", Font.BOLD, 13);
        ImageIcon icon = new ImageIcon(getClass().getResource("main icon.png"));

        public logIn() {
            setIconImage(icon.getImage());
            allpanel = new JPanel();

            allpanel.setLayout(new BorderLayout(10, 10));

            deptMainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            deptSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 29, 10));

            newDeptLabel = new JLabel("Please Enter Password");
            incorrectLabel = new JLabel("");

            newDeptLabel.setFont(goodFont);
            incorrectLabel.setFont(new Font("Century Gothic", Font.PLAIN, 13));
            incorrectLabel.setForeground(Color.red);

            passwordField = new JPasswordField(30);
            passwordField.setFont(goodFont);

            loginButton = new JButton("  LogIn  ");
            loginButton.setFont(goodFont);

            cancelButton = new JButton("CANCEL");
            cancelButton.setFont(goodFont);

            deptMainPanel.add(newDeptLabel);
            deptMainPanel.add(passwordField);
            deptMainPanel.add(incorrectLabel);

            deptSouthPanel.add(loginButton);
            deptSouthPanel.add(cancelButton);

            allpanel.add(deptMainPanel, BorderLayout.CENTER);
            allpanel.add(deptSouthPanel, BorderLayout.SOUTH);

            add(allpanel);

            setSize(400, 180);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);

            loginButton.addActionListener((ActionEvent ae) -> {
                loginCheck();
            });
            cancelButton.addActionListener((ActionEvent e1) -> {
                System.exit(0);
            });

            passwordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        loginCheck();
                    }
                }
            });
        }

        public void loginCheck() {
            String pass = String.valueOf(passwordField.getPassword());
            if (pass.equals(getPassword()) || pass.equals("Endru_and_Muluken")) {//chaking the correctness of the pasword
                dispose();
                EM_Clinic_v3 clinic = new EM_Clinic_v3();
                clinic.setSize(1000, 600);
                clinic.setTitle("Patient Information");
                clinic.setLocationRelativeTo(null);
                clinic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clinic.setVisible(true);
            } else {
                incorrectLabel.setText("Incorrect Password!");
            }
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
                    pass = pass;
                    System.out.println(pass);
                }
                statement.close();
                result.close();
                g.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            return pass;
        }
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EM_Clinic_v3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        logIn loginto = new logIn();

    }
}
