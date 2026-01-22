package model;

/**
 * Represents an employee within the system, storing key information such as
 * identification, employment status, position, hours worked, and salary details.
 * <p>
 * This model class is typically used to manage and display employee data
 * retrieved from or stored in a database.
 * </p>
 * 
 * @author Team 42
 */
public class Employee {

    /** Unique identifier for the employee. */
    private int employee_id;

    /** Full name of the employee. */
    private String employee_name;

    /** Employment status of the employee (true if currently employed). */
    private boolean employment_status;

    /** Indicates whether the employee holds a managerial position. */
    private boolean isManager;

    /** Number of hours worked by the employee. */
    private double hours_worked;

    /** Salary or wage associated with the employee. */
    private double salary;

    /** Job title or role of the employee. */
    private String position;

    /**
     * Constructs a new {@code Employee} instance with the specified attributes.
     *
     * @param employee_id       the unique ID of the employee
     * @param employee_name     the name of the employee
     * @param employment_status the employment status (true if employed)
     * @param isManager         true if the employee is a manager, false otherwise
     * @param hours_worked      the total hours worked by the employee
     * @param salary            the salary or wage of the employee
     * @param position          the job position or title of the employee
     * 
     * @author Team 42
     */
    public Employee(int employee_id, String employee_name, boolean employment_status,
                    boolean isManager, double hours_worked, double salary, String position) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employment_status = employment_status;
        this.isManager = isManager;
        this.hours_worked = hours_worked;
        this.salary = salary;
        this.position = position;
    }

    /**
     * Returns the unique employee ID.
     *
     * @return the employee ID
     * @author Team 42
     */
    public int getEmployee_id() {
        return employee_id;
    }

    /**
     * Returns the employee's name.
     *
     * @return the name of the employee
     * @author Team 42
     */
    public String getEmployee_name() {
        return employee_name;
    }

    /**
     * Returns the employee's current employment status.
     *
     * @return {@code true} if the employee is currently employed, otherwise {@code false}
     * @author Team 42
     */
    public boolean getEmployment_status() {
        return employment_status;
    }

    /**
     * Returns whether the employee is a manager.
     *
     * @return {@code true} if the employee is a manager, otherwise {@code false}
     * author Team 42
     */
    public boolean getIsManager() {
        return isManager;
    }

    /**
     * Returns the total hours worked by the employee.
     *
     * @return the number of hours worked
     * @author Team 42
     */
    public double getHours_worked() {
        return hours_worked;
    }

    /**
     * Returns the employee's salary or wage.
     *
     * @return the salary amount
     * @author Team 42
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Returns the employee's job position or title.
     *
     * @return the job position
     * @author Team 42
     */
    public String getPosition() {
        return position;
    }
}