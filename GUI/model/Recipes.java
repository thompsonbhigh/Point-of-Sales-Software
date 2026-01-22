package model;

/**
 * Represents a recipe linking a menu item to its required inventory items
 * and the corresponding quantity of each ingredient needed.
 *
 * <p>This class models the relationship between menu items and
 * inventory components in the system’s recipe management structure.
 * Each recipe specifies how much of a given inventory item is required
 * to prepare one unit of a menu item.</p>
 *
 * @author Team 42
 */
public class Recipes {
    /** Unique identifier for the recipe. */
    private final int recipe_id;

    /** ID of the menu item associated with this recipe. */
    private final int menuitem_id;

    /** ID of the inventory item used in this recipe. */
    private final int item_id;

    /** Quantity of the inventory item required to make the menu item. */
    private final double quantity_required;

    /**
     * Constructs a new {@code Recipes} object with the specified details.
     *
     * @param recipe_id          the unique recipe identifier
     * @param menuitem_id        the ID of the associated menu item
     * @param item_id            the ID of the inventory item used
     * @param quantity_required  the quantity of the inventory item required
     */
    public Recipes(int recipe_id, int menuitem_id, int item_id, double quantity_required) {
        this.recipe_id = recipe_id;
        this.menuitem_id = menuitem_id;
        this.item_id = item_id;
        this.quantity_required = quantity_required;
    }

    /** @return the unique recipe ID */
    public int getRecipeId() { return recipe_id; }

    /** @return the menu item ID associated with this recipe */
    public int getMenuItemId() { return menuitem_id; }

    /** @return the inventory item ID used in this recipe */
    public int getItemId() { return item_id; }

    /** @return the quantity of the inventory item required for the recipe */
    public double getQuantityRequired() { return quantity_required; }

    /**
     * Returns a string representation of the recipe, including
     * all associated identifiers and required quantity.
     *
     * @return a formatted string containing recipe details
     */
    @Override
    public String toString() {
        return recipe_id + ", " + menuitem_id + ", " + item_id + ", " + quantity_required;
    }
}
