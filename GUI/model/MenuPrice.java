package model;

/**
 * Represents the pricing information for a menu item, including
 * its size (e.g., small, medium, large) and corresponding price.
 * <p>
 * This class is typically used to associate different size options
 * with their respective prices for menu items.
 * </p>
 * 
 * author Team 42
 */
public class MenuPrice {

    /** The size option of the menu item (e.g., small, medium, large). */
    private final String size;

    /** The price corresponding to the size option. */
    private final double price;

    /**
     * Constructs a new {@code MenuPrice} with the specified size and price.
     *
     * @param size  the size option of the menu item
     * @param price the price associated with the given size
     * 
     * author Team 42
     */
    public MenuPrice(String size, double price) {
        this.size = size;
        this.price = price;
    }

    /**
     * Returns the size option of the menu item.
     *
     * @return the size of the menu item
     * author Team 42
     */
    public String getSize() {
        return size;
    }

    /**
     * Returns the price associated with this size.
     *
     * @return the price of the menu item for this size
     * author Team 42
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the menu price,
     * including the size and formatted price.
     *
     * @return a formatted string displaying the size and price
     * author Team 42
     */
    @Override
    public String toString() {
        return size + " ($" + String.format("%.2f", price) + ")";
    }
}
