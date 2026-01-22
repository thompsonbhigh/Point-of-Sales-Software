package model;

/**
 * Represents a single entry within an order, containing information
 * about a specific menu item, its size, and price.
 *
 * <p>This class is immutable and provides accessor methods
 * for each field.</p>
 *
 * @author Team 42
 */
public class OrderEntry {
  private final int entry_id;
  private final int order_id;
  private final int menuitem_id;
  private final String size;
  private final double price;

  /**
   * Constructs an OrderEntry with the specified parameters.
   *
   * @param entry_id the unique identifier for this entry
   * @param order_id the ID of the order this entry belongs to
   * @param menuitem_id the ID of the menu item
   * @param size the size of the menu item (e.g., small, medium, large)
   * @param price the price of the item for the given size
   */
  public OrderEntry(int entry_id, int order_id, int menuitem_id, String size, double price) {
    this.entry_id = entry_id;
    this.order_id = order_id;
    this.menuitem_id = menuitem_id;
    this.size = size;
    this.price = price;
  }

  /** @return the unique identifier for this entry */
  public int getEntryId() { return entry_id; }

  /** @return the ID of the order this entry belongs to */
  public int getOrderId() { return order_id; }

  /** @return the ID of the menu item */
  public int getMenuItemId() { return menuitem_id; }

  /** @return the size of the menu item */
  public String getSize() { return size; }

  /** @return the price of the menu item for the specified size */
  public double getPrice() { return price; }
}
