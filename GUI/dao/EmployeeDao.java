/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Data Access Object (DAO) for employee records including creation, updates, and lookups.
 *
 * Author: Team 42
 */

package dao;

import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private static final String TABLE = "employees";
    private static final String COL_ID = "employee_id";
    private static final String COL_NAME = "employee_name";
    private static final String COL_STATUS = "employement_status";
    private static final String COL_IS_MANAGER = "is_manager";
    private static final String COL_HOURS_WORKED = "hours_worked";
    private static final String COL_SALARY = "salary";
    private static final String COL_POSITION = "position";


    /**
     * Finds all Employees in database.
     *
     * @return result list of employees.
     * @throws SQLException if an error occurs
     */

    public static List<Employee> findAll() throws SQLException {
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_STATUS + ", " + COL_IS_MANAGER + ", " + COL_HOURS_WORKED + ", " + COL_SALARY + ", " + COL_POSITION +
                " FROM " + TABLE + " ORDER BY " + COL_ID;

        List<Employee> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
                while (r.next()) {
                    out.add(new Employee(r.getInt(COL_ID),
                            r.getString(COL_NAME),
                            r.getBoolean(COL_STATUS),
                            r.getBoolean(COL_IS_MANAGER),
                            r.getDouble(COL_HOURS_WORKED),
                            r.getDouble(COL_SALARY),
                            r.getString(COL_POSITION)
                    ));
                }
        }
        return out;
    }

    /**
     * Searches for an employee by name.
     *
     * @param name name of employee
     * @return list of employees with that name.
     * @throws SQLException if an error occurs
     */

    public static List<Employee> search(String name) throws SQLException {
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_STATUS + ", " + COL_IS_MANAGER + ", " + COL_HOURS_WORKED + ", " + COL_SALARY + ", " + COL_POSITION +
                " FROM " + TABLE +" WHERE " + COL_NAME + " ILIKE '%" + name + "%' ORDER BY " + COL_ID;

        List<Employee> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new Employee(r.getInt(COL_ID),
                        r.getString(COL_NAME),
                        r.getBoolean(COL_STATUS),
                        r.getBoolean(COL_IS_MANAGER),
                        r.getDouble(COL_HOURS_WORKED),
                        r.getDouble(COL_SALARY),
                        r.getString(COL_POSITION)
                ));
            }
        }
        return out;
    }

    /**
     * Finds an employee by their id.
     *
     * @param id employee id
     * @return employee with matching id
     * @throws SQLException if an error occurs
     */

    public static Employee find(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE employee_id = " + id;
        List<Employee> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new Employee(r.getInt(COL_ID),
                        r.getString(COL_NAME),
                        r.getBoolean(COL_STATUS),
                        r.getBoolean(COL_IS_MANAGER),
                        r.getDouble(COL_HOURS_WORKED),
                        r.getDouble(COL_SALARY),
                        r.getString(COL_POSITION)
                ));
            }
        }
        return out.get(0);
    }

    /**
     * Inserts an employee into the database based on all their attributes.
     *
     * @param _employee_name employee name
     * @param _employee_status employement status of employee
     * @param _isManager if employee is a manager
     * @param _hours_worked hours worked for employee
     * @param _salary salary of employee
     * @param position employee's position
     * @return successful insert
     * @throws SQLException if an error occurs
     */

    public static int insert(String _employee_name, boolean _employee_status, boolean _isManager, double _hours_worked, double _salary, String position) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (" + COL_NAME + ", " + COL_STATUS + ", " + COL_IS_MANAGER + ", " + COL_HOURS_WORKED + ", " + COL_SALARY + ", " + COL_POSITION + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, _employee_name);
            ps.setBoolean(2, _employee_status);
            ps.setBoolean(3, _isManager);
            ps.setDouble(4, _hours_worked);
            ps.setDouble(5, _salary);
            ps.setString(6, position);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates the status of an employee.
     *
     * @param id employee id
     * @param newStatus new status for the employee
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateStatus(int id, boolean newStatus) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_STATUS + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates name of an employee
     *
     * @param id employee id
     * @param newName employee's new name
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateName(int id, String newName) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_NAME + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates employee position
     *
     * @param id employee id
     * @param newPosition employee's new position
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updatePosition(int id, String newPosition) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_POSITION + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newPosition);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    // Promote or demote an employee
    /**
     * Update if employee is manager or not.
     *
     * @param id employee id
     * @param newIsManager if employee is manager or not
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updatePromote(int id, boolean newIsManager) throws SQLException {
        String sql ="UPDATE " + TABLE + " SET " + COL_IS_MANAGER + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, newIsManager);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Update employee's hours worked.
     *
     * @param id employee id
     * @param newHoursWorked employee's new hours worked
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateHoursWorked(int id, double newHoursWorked) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_HOURS_WORKED + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, newHoursWorked);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates an employee's salary.
     *
     * @param id employee id
     * @param newSalary employee's new salary
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateSalary(int id, double newSalary) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_SALARY + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes an employee from the database.
     *
     * @param id employee id
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Sees if an employee with a certain id exists in the database.
     *
     * @param employee_id employee id
     * @return bool if employee exists or not
     * @throws SQLException if an error occurs
     */

    public static boolean employeeExists(int employee_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE employee_id = ?";
        try (Connection c = Db.connect();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employee_id);
            try (ResultSet r = ps.executeQuery()) {
                if (r.next()) {
                    return r.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}