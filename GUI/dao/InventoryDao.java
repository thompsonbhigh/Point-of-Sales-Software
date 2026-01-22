/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for inventory items, quantities, and stock-level queries in the Panda Express POS database.
 *
 * Author: Team 42
 */

package dao; 

import model.Inventory; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao {
    private static final String TABLE = "inventory"; 
    private static final String COL_ID = "item_id";
    private static final String COL_NAME = "item_name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_UNIT = "unit";

    /**
     * Finds all inventory items.
     *
     * @return list of all inventory items
     * @throws SQLException if an error occurs
     */

    public static List<Inventory> findAll() throws SQLException{
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_QUANTITY + ", " + COL_UNIT +
                     " FROM " + TABLE + " ORDER BY " + COL_ID;

        List<Inventory> out = new ArrayList<>();
        try (Connection c = Db.connect();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new Inventory(
                    r.getInt(COL_ID),
                    r.getString(COL_NAME),
                    r.getInt(COL_QUANTITY),
                    r.getString(COL_UNIT)
                ));
            }
        }
        return out;
    }

    /**
     * Searches in the inventory by name
     *
     * @param name name of inventory item
     * @return inventory items with matching name
     * @throws SQLException if an error occurs
     */

    public static List<Inventory> search(String name) throws SQLException{
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_QUANTITY + ", " + COL_UNIT +
                     " FROM " + TABLE + " WHERE " + COL_NAME + " ILIKE '%" + name + "%' ORDER BY " + COL_ID;

        List<Inventory> out = new ArrayList<>();
        try (Connection c = Db.connect();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new Inventory(
                    r.getInt(COL_ID),
                    r.getString(COL_NAME),
                    r.getInt(COL_QUANTITY),
                    r.getString(COL_UNIT)
                ));
            }
        }
        return out;
    }

    /**
     * Inserts an inventory item into the database.
     *
     * @param name name of inventory item
     * @param quantity amount of inventory item
     * @param unit units for quantity
     * @return successful insert
     * @throws SQLException if an error occurs
     */

    public static int insert(String name, int quantity, String unit) throws SQLException{
        String sql = "INSERT INTO " + TABLE + " (" + COL_NAME + ", " + COL_QUANTITY
                     + ", " + COL_UNIT +") VALUES (?, ?, ?)";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, quantity);
            ps.setString(3, unit);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates name for inventory item by id.
     *
     * @param ID inventory item id
     * @param name new name of inventory item
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateName(int ID, String name) throws SQLException{
        String sql = "UPDATE " + TABLE + " SET " + COL_NAME + "=? WHERE " + COL_ID + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, ID);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates quantity of inventory item
     *
     * @param ID inventory item id
     * @param quantity new quantity
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateQuantity(int ID, double quantity) throws SQLException{
        String sql = "UPDATE " + TABLE + " SET " + COL_QUANTITY + "=? WHERE " + COL_ID + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, quantity);
            ps.setInt(2, ID);
            return ps.executeUpdate();
        }
    }   

    /**
     * Updates the unit of an inventory item.
     *
     * @param ID inventory item id
     * @param unit new unit
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateUnit(int ID, String unit) throws SQLException{
        String sql = "UPDATE " + TABLE + " SET " + COL_UNIT + "=? WHERE " + COL_ID + "=?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, unit);
            ps.setInt(2, ID);
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes an inventory item by id.
     *
     * @param ID inventory item id
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int delete(int ID) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ID);
            return ps.executeUpdate();
        }
    }

    /**
     * Finds an inventory item by id.
     *
     * @param itemID inventory item id
     * @return successful update
     */

    public static Inventory findById(int itemID) {
        String sql = "Select * from " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setInt(1, itemID);
            try (ResultSet r = ps.executeQuery()) {
                if (r.next()) {
                    return new Inventory(
                        r.getInt(COL_ID),
                        r.getString(COL_NAME),
                        r.getInt(COL_QUANTITY),
                        r.getString(COL_UNIT)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}