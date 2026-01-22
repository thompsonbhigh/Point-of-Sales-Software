package model;

import java.sql.Timestamp;

/**
 * Represents an order in the system, containing information such as
 * the employee who handled it, the total cost, and the timestamp
 * of when the order was placed.
 *
 * <p>This class is immutable and provides accessor methods
 * for all fields.</p>
 *
 * @author Team 42
 */
public class Orders {
    private final int order_id;
    private final int employee_id;
    private final double total_cost;
    private final Timestamp time_stamp;

    /**
     * Constructs an Orders object with the specified details.
     *
     * @param order_id the unique identifier for this order
     * @param employee_id the ID of the employee who processed the order
     * @param total_cost the total cost of the order
     * @param time_stamp the timestamp when the order was placed
     */
    public Orders(int order_id, int employee_id, double total_cost, Timestamp time_stamp) {
        this.order_id = order_id;
        this.employee_id = employee_id;
        this.total_cost = total_cost;
        this.time_stamp = time_stamp;
    }

    /** @return the unique identifier for this order */
    public int getOrderId() { return order_id; }

    /** @return the ID of the employee who processed the order */
    public int getEmployeeId() { return employee_id; }

    /** @return the total cost of the order */
    public double getTotalCost() { return total_cost; }

    /** @return the timestamp when the order was placed */
    public Timestamp getTimeStamp() { return time_stamp; }

    /**
     * Returns a formatted string representation of this order,
     * including its ID, total cost, and timestamp.
     *
     * @return a human-readable string representation of the order
     */
    @Override
    public String toString() {
        return order_id + " ($" + String.format("%.2f", total_cost) + ") at " + time_stamp;
    }
}
