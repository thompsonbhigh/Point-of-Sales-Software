package model;

/**
 * Represents an inventory item stored in the system, containing details
 * such as item ID, name, quantity, and unit of measurement.
 * <p>
 * This model is typically used to track available stock items
 * in the inventory database or application interface.
 * </p>
 * 
 * @author Team 42
 */
public class Inventory {

    /** Unique identifier for the inventory item. */
    private final int item_id;

    /** Descriptive name of the inventory item. */
    private final String item_name;

    /** Quantity of the item currently in stock. */
    private final double quantity;

    /** Unit of measurement for the quantity (e.g., kg, L, pcs). */
    private final String unit;

    /**
     * Constructs a new {@code Inventory} object with the specified attributes.
     *
     * @param id   the unique ID of the inventory item
     * @param name the name of the inventory item
     * @param q    the quantity of the item available
     * @param u    the unit of measurement for the quantity
     * 
     * @author Team 42
     */
    public Inventory(int id, String name, double q, String u) {
        this.item_id = id;
        this.item_name = name;
        this.quantity = q;
        this.unit = u;
    }

    /**
     * Returns the unique ID of the inventory item.
     *
     * @return the item ID
     * @author Team 42
     */
    public int getItemID() {
        return item_id;
    }

    /**
     * Returns the name of the inventory item.
     *
     * @return the item name
     * @author Team 42
     */
    public String getItemName() {
        return item_name;
    }

    /**
     * Returns the current quantity of the inventory item in stock.
     *
     * @return the quantity available
     * @author Team 42
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Returns the unit of measurement for the item.
     *
     * @return the unit of measurement (e.g., kg, pcs, L)
     * @author Team 42
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns a string representation of the inventory item,
     * including its ID, name, quantity, and unit.
     *
     * @return a formatted string representing the inventory item
     * @author Team 42
     */
    @Override
    public String toString() {
        return item_id + " | " + item_name + " | " + quantity + " " + unit;
    }
}
