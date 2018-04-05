package em_clinic_v3;

import java.util.List;
import javax.swing.table.AbstractTableModel;

class PatientTableModel extends AbstractTableModel {

//    public static final int OBJECT_COL = -1;
    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int SEX_COL = 2;
    private static final int AGE_COL = 3;
    private static final int DEPARTMENT_COL = 4;
    private static final int CARD_NUMBER_COL = 5;

    private String[] columnNames = {"ID", "Name", "Sex", "Age", "Department", "Card Number"};
    private List<Patient> patients;

    public PatientTableModel(List<Patient> thePatients) {
        patients = thePatients;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Patient tempPatient = patients.get(row);

        switch (col) {
            case ID_COL:
                return tempPatient.getID();
            case NAME_COL:
                return tempPatient.getName();
            case SEX_COL:
                return tempPatient.getSex();
            case AGE_COL:
                return tempPatient.getAge();
            case DEPARTMENT_COL:
                return tempPatient.getDepartment();
            case CARD_NUMBER_COL:
                return tempPatient.getCard();
//            case OBJECT_COL:
//                return tempPatient;
            default:
                return tempPatient.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
