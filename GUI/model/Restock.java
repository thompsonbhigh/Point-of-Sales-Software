package model;

import java.sql.Date;

/**
 * Represents restocking information for an inventory item, including its
 * maximum and minimum stock levels, shelf life, and last restock date.
 *
 * <p>This class helps manage the inventory's restocking process by tracking
 * thresholds and ensuring that items are replenished when they fall below
 * the required minimum quantity.</p>
 *
 * <p>It also contains a static inner class {@link RestockReportEntry} that
 * represents summarized restock report entries for reporting purposes.</p>
 *
 * @author Team 42
 */
public class Restock {
    /** Unique identifier for the restock record. */
    private int restock_id;

    /** ID of the inventory item associated with this restock record. */
    private int item_id;

    /** Maximum allowed stock quantity for the item. */
    private double max;

    /** Minimum allowed stock quantity for the item before restocking is required. */
    private double min;

    /** Shelf life of the item in days. */
    private int shelf_life;

    /** Date of the most recent restock for the item. */
    private Date lastRestock;

    /**
     * Constructs a {@code Restock} object with the given details.
     *
     * @param restockId   the unique ID of the restock record
     * @param itemId      the ID of the inventory item
     * @param max         the maximum stock level for the item
     * @param min         the minimum stock level before restocking
     * @param shelfLife   the shelf life of the item in days
     * @param lastRestock the date of the last restock
     */
    public Restock(int restockId, int itemId, double max, double min, int shelfLife, Date lastRestock) {
        this.restock_id = restockId;
        this.item_id = itemId;
        this.max = max;
        this.min = min;
        this.shelf_life = shelfLife;
        this.lastRestock = lastRestock;
    }

    /** @return the unique restock ID */
    public int getRestockID() { return restock_id; }

    /** @return the inventory item ID associated with this restock record */
    public int getItemID() { return item_id; }

    /** @return the maximum stock quantity for the item */
    public double getMax() { return max; }

    /** @return the minimum stock quantity before restocking is needed */
    public double getMin() { return min; }

    /** @return the shelf life of the item in days */
    public int getShelfLife() { return shelf_life; }

    /** @return the date of the last restock */
    public Date getLastRestock() { return lastRestock; }

    /**
     * Represents a single entry in a restock report,
     * summarizing an item's current stock status and restock needs.
     *
     * <p>This inner class is typically used when generating
     * reports that indicate which inventory items require restocking.</p>
     *
     * @author Team 42
     */
    public static class RestockReportEntry {
        /** The name of the inventory item. */
        private String itemName;

        /** The current quantity of the item on hand. */
        private double onHand;

        /** The minimum quantity threshold for the item. */
        private double minimum;

        /** Whether the item needs to be restocked. */
        private boolean needsRestock;

        /**
         * Constructs a {@code RestockReportEntry} object with the given details.
         *
         * @param itemName     the name of the inventory item
         * @param onHand       the current quantity of the item on hand
         * @param minimum      the minimum required quantity before restocking
         * @param needsRestock whether the item currently needs restocking
         */
        public RestockReportEntry(String itemName, double onHand, double minimum, boolean needsRestock) {
            this.itemName = itemName;
            this.onHand = onHand;
            this.minimum = minimum;
            this.needsRestock = needsRestock;
        }

        /** @return the name of the inventory item */
        public String getItemName() { return itemName; }

        /** @return the current quantity of the item on hand */
        public double getOnHand() { return onHand; }

        /** @return the minimum quantity threshold for the item */
        public double getMinimum() { return minimum; }

        /** @return {@code true} if the item needs restocking, {@code false} otherwise */
        public boolean needsRestock() { return needsRestock; }
    }
}
