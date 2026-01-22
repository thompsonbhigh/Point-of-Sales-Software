package model;

/**
 * Represents a menu item in the system, containing its unique identifier,
 * name, and category (e.g., appetizer, entrée, dessert).
 * <p>
 * This model class is typically used to store and manage data related
 * to items offered on a restaurant or business menu.
 * </p>
 * 
 * @author Team 42
 */
public class MenuItem {

    /** Unique identifier for the menu item. */
    private int menuitem_id;

    /** Name or title of the menu item. */
    private String menuitem_name;

    /** Category of the menu item (e.g., appetizer, entrée, dessert). */
    private String category;

    /**
     * Constructs a new {@code MenuItem} with the specified attributes.
     *
     * @param id   the unique ID of the menu item
     * @param name the name of the menu item
     * @param cat  the category of the menu item
     * 
     * @author Team 42
     */
    public MenuItem(int id, String name, String cat) {
        menuitem_id = id;
        menuitem_name = name;
        category = cat;
    }

    /**
     * Returns the unique menu item ID.
     *
     * @return the menu item ID
     * @author Team 42
     */
    public int getMenuitem_id() {
        return menuitem_id;
    }

    /**
     * Returns the name of the menu item.
     *
     * @return the menu item name
     * @author Team 42
     */
    public String getMenuitem_name() {
        return menuitem_name;
    }

    /**
     * Returns the category of the menu item.
     *
     * @return the menu item category
     * @author Team 42
     */
    public String getMenuitem_category() {
        return category;
    }

    /**
     * Returns a string representation of the menu item,
     * including its ID, name, and category.
     *
     * @return a formatted string representing the menu item
     * @author Team 42
     */
    @Override
    public String toString() {
        return "MenuItem ID: " + menuitem_id + ", Name: " + menuitem_name + ", Category: " + category;
    }
}
