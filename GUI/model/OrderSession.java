package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the state of the current order session, including
 * active orders, entries, employee information, and shift status.
 *
 * <p>This class follows the singleton design pattern, ensuring
 * only one instance of {@code OrderSession} exists throughout
 * the application lifecycle.</p>
 *
 * <p>It stores session-level information such as the current
 * order being processed, all related order entries, and the
 * employee handling the session.</p>
 *
 * @author Team 42
 */
public class OrderSession {
    private Orders currOrder;
    private final List<OrderEntry> entries = new ArrayList<>();
    private String selectedSize;
    private Integer employeeId;
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean inShift;

    /** Singleton instance of OrderSession. */
    private static final OrderSession instance = new OrderSession();

    /** Private constructor to enforce singleton pattern. */
    private OrderSession() {}

    /**
     * Returns the single instance of the {@code OrderSession}.
     *
     * @return the singleton instance
     */
    public static OrderSession getInstance() {
        return instance;
    }

    /** @return the ID of the employee currently in the session */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID for the current session.
     *
     * @param employeeId the employee’s ID
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /** @return the current active order */
    public Orders getCurrOrder() {
        return currOrder;
    }

    /**
     * Sets the current order for this session.
     *
     * @param order the {@code Orders} object to set
     */
    public void setCurrOrder(Orders order) {
        this.currOrder = order;
    }

    /** @return the list of order entries for the current session */
    public List<OrderEntry> getEntries() {
        return entries;
    }

    /**
     * Adds a new {@code OrderEntry} to the session’s list.
     *
     * @param entry the {@code OrderEntry} to add
     */
    public void addEntry(OrderEntry entry) {
        entries.add(entry);
    }

    /** @return the currently selected size option */
    public String getSelectedSize() {
        return selectedSize;
    }

    /**
     * Sets the selected size for the menu item being ordered.
     *
     * @param selectedSize the size to set
     */
    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    /** @return {@code true} if the employee is currently in a shift, otherwise {@code false} */
    public boolean isInShift() {
        return inShift;
    }

    /**
     * Starts a new shift for the employee and records the start time.
     *
     * @param startTime the timestamp marking the start of the shift
     */
    public void startShift(Timestamp startTime) {
        this.startTime = startTime;
        inShift = true;
    }

    /**
     * Ends the current shift and records the end time.
     *
     * @param endTime the timestamp marking the end of the shift
     */
    public void endShift(Timestamp endTime) {
        this.endTime = endTime;
        inShift = false;
    }

    /** @return the timestamp when the current shift started */
    public Timestamp getStartTime() {
        return startTime;
    }

    /** @return the timestamp when the current shift ended */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Clears the current order session data, including the
     * current order, all entries, and the selected size.
     */
    public void clear() {
        currOrder = null;
        entries.clear();
        selectedSize = null;
    }
}
