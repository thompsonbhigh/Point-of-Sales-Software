package model;

import java.sql.Date;

/**
 * Represents a scheduled work shift for an employee, including the
 * date, shift time, position, and employee assignment.
 *
 * <p>This class is used to manage employee scheduling and helps keep
 * track of when and where employees are assigned to work within the
 * system.</p>
 *
 * @author Team 42
 */
public class Schedule {
    /** The date of the scheduled shift. */
    private final Date scheduleDate;

    /** The shift designation (e.g., "Morning", "Afternoon", "Evening"). */
    private final String shift;

    /** The position assigned for this scheduled shift. */
    private final String position;

    /** The unique ID of the employee assigned to this schedule. */
    private final int employee_id;

    /**
     * Constructs a {@code Schedule} object with the given details.
     *
     * @param d    the date of the scheduled shift
     * @param s    the shift designation (e.g., "Morning", "Evening")
     * @param p    the position assigned to the employee
     * @param eID  the ID of the employee assigned to this schedule
     */
    public Schedule(Date d, String s, String p, int eID) {
        this.scheduleDate = d;
        this.shift = s;
        this.position = p;
        this.employee_id = eID;
    }

    /**
     * Returns the date of the scheduled shift.
     *
     * @return the scheduled date
     */
    public Date getDate() { return scheduleDate; }

    /**
     * Returns the shift designation.
     *
     * @return the shift (e.g., "Morning", "Afternoon", "Evening")
     */
    public String getShift() { return shift; }

    /**
     * Returns the position assigned for this scheduled shift.
     *
     * @return the position title
     */
    public String getPosition() { return position; }

    /**
     * Returns the ID of the employee assigned to this schedule.
     *
     * @return the employee ID
     */
    public int getEmployeeID() { return employee_id; }

    /**
     * Returns a string representation of the schedule,
     * displaying the date, shift, position, and employee ID.
     *
     * @return a formatted string describing the schedule
     */
    @Override
    public String toString() {
        return scheduleDate + " | " + shift + " | " + position + " | " + employee_id;
    }
}
