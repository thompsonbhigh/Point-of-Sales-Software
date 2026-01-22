package Tests;
import dao.*;
import model.Employee;

import java.sql.*;
import java.util.List;

public class employeeModelTest {
    // public static EmployeeDao EmployeeDao = new EmployeeDao();
    public static int lastInsertId;

    public static void getLastInsertedId() {
        String sql = "SELECT MAX(employee_id) FROM employees";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery();) {
            if (r.next()) {
                lastInsertId = r.getInt(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_findAll() {
        try {
            //test findAll method: PASS
            List<Employee> employees = EmployeeDao.findAll();

            for (Employee e : employees) {
            System.out.println("ID: " + e.getEmployee_id() +
                    ", Name: " + e.getEmployee_name() +
                    ", Status: " + e.getEmployment_status() +
                    ", Is Manager: " + e.getIsManager() +
                    ", Hours Worked: " + e.getHours_worked() +
                    ", Salary: " + e.getSalary());
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_insert() {  // PASS
        try {
            //test insert method: PASS
            int rowsInserted = EmployeeDao.insert("Employee Test", true, false, 3.14, 0.01, "Test");
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updateStatus() { // PASS
         try{
            getLastInsertedId();
            int rowsUpdated = EmployeeDao.updateStatus(lastInsertId, false);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updatePromote() {
        try{
            getLastInsertedId();
            int rowsUpdated = EmployeeDao.updatePromote(lastInsertId,true);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updateHoursWorked() {
        try{
            getLastInsertedId();
            int rowsUpdated = EmployeeDao.updateHoursWorked(lastInsertId, 10000);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updateSalary() {
        try {
            getLastInsertedId();
            int rowsUpdated = EmployeeDao.updateSalary(lastInsertId, 15.00);
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_delete() {  // PASS
        try {
            getLastInsertedId();
            EmployeeDao.delete(lastInsertId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        switch (args[0]) {
            case "findAll": 
                test_findAll(); 
                break;
            case "insert": 
                test_insert(); 
                break;
            case "updateStatus": 
                test_updateStatus(); 
                break;
            case "updatePromote": 
                test_updatePromote(); 
                break;
            case "updateHoursWorked": 
                test_updateHoursWorked(); 
                break;
            case "updateSalary": 
                test_updateSalary(); 
                break;
            case "delete": 
                test_delete(); 
                break;
            case "last": 
                getLastInsertedId(); 
                System.out.println("Last Inserted ID: " + lastInsertId); 
                break;
            default: 
                System.out.println("Invalid argument. Please use one of the following: findAll, insert, updateStatus, updatePromote, updateHoursWorked, updateSalary, delete");
        }

    }
}
