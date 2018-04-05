/*
 * @This code is written by: Endriyas in May 21, 2017
 * I assure that my code is 100% mistake free!!
 */
package em_clinic_v3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection myConn;

    public PatientDAO() throws Exception {

        String user = "root";
        String password = "pass";
        String dburl = "jdbc:mysql://localhost/patient";

        try {
            // connect to database
            myConn = DriverManager.getConnection(dburl, user, password);
        } catch (SQLException ex) {
        }
    }

    public void deletePatient(String patientId) throws SQLException {
        PreparedStatement myStmt = null;

        try {
            myStmt = myConn.prepareStatement("delete from patientinfo where ID=?");
            myStmt.setString(1, patientId);
            myStmt.executeUpdate();

        } finally {
            close(myStmt);
        }
    }

    public void updatePatient(Patient thepatient) throws SQLException {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("update patientinfo"
                    + " set ID=?, Name=?, Sex=?, Age=?, Department=?, Card_Number=?"
                    + " where ID=?");

            // set params
            myStmt.setString(1, thepatient.getID());
            myStmt.setString(2, thepatient.getName());
            myStmt.setString(3, thepatient.getSex());
            myStmt.setInt(4, thepatient.getAge());
            myStmt.setString(5, thepatient.getDepartment());
            myStmt.setInt(6, thepatient.getCard());
            myStmt.setString(7, thepatient.getID());

            // execute SQL
            myStmt.executeUpdate();
        } finally {
            close(myStmt);
        }
    }

    public void addPatient(Patient thepatient) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("insert into patientinfo"
                    + " (ID, Name, Sex, Age, Department, Card_Number)"
                    + " values (?, ?, ?, ?, ?, ?)");

            // set params
            myStmt.setString(1, thepatient.getID());
            myStmt.setString(2, thepatient.getName());
            myStmt.setString(3, thepatient.getSex());
            myStmt.setInt(4, thepatient.getAge());
            myStmt.setString(5, thepatient.getDepartment());
            myStmt.setInt(6, thepatient.getCard());

            // execute SQL
            myStmt.executeUpdate();
        } finally {
            close(myStmt);
        }

    }

    public List<Patient> getAllPatient() throws Exception {
        List<Patient> list = new ArrayList<>();

        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("select * from patientinfo order by Card_Number");

            while (myRs.next()) {
                Patient tempPatient = convertRowToPatient(myRs);
                list.add(tempPatient);
            }

            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    public List<Patient> searchALLPatients(String text) throws Exception {
        List<Patient> list = new ArrayList<>();

        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            text = "%" + text + "%";
            myStmt = myConn.prepareStatement("select * from patientinfo where ID like ? or Name like ? or Sex like ? or Age like ? or Department like ? or Card_Number like ?");

            myStmt.setString(1, text);
            myStmt.setString(2, text);
            myStmt.setString(3, text);
            myStmt.setString(4, text);
            myStmt.setString(5, text);
            myStmt.setString(6, text);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                Patient tempPatient = convertRowToPatient(myRs);
                list.add(tempPatient);
            }

            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    public List<Patient> searchPatients(String text, String column) throws Exception {
        List<Patient> list = new ArrayList<>();

        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            text = "%" + text + "%";
            myStmt = myConn.prepareStatement("select * from patientinfo where " + column + " like ?");
            myStmt.setString(1, text);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                Patient tempPatient = convertRowToPatient(myRs);
                list.add(tempPatient);
            }
            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    private Patient convertRowToPatient(ResultSet myRs) throws SQLException {

        String id = myRs.getString("ID");
        String name = myRs.getString("Name");
        String sex = myRs.getString("Sex");
        int age = myRs.getInt("Age");
        String dep = myRs.getString("Department");
        int card = myRs.getInt("Card_Number");

        Patient tempPatient = new Patient(id, name, sex, age, dep, card);

        return tempPatient;
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
            throws SQLException {

        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {
            myStmt.close();
        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }

    private void close(Statement myStmt) throws SQLException {
        close(null, myStmt, null);
    }

}
