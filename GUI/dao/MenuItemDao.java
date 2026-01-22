/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for menu items (entrees, premium entrees, sides, and drinks).
 *
 * Author: Team 42
 */

package dao;

import model.Inventory;
import model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDao {
    private static final String TABLE = "menuitem";
    private static final String COL_ID = "menuitem_id";
    private static final String COL_NAME = "menuitem_name";
    private static final String COL_CATEGORY = "category";

    /**
     * Finds a menu item id by it's name.
     *
     * @param itemName menu item name
     * @return id of item
     * @throws SQLException if an error occurs
     */

    public static int findIdByName(String itemName) throws SQLException {
        String sql = "SELECT menuitem_id FROM menuitem where menuitem_name LIKE ?";
        try (Connection c = Db.connect();
            PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, itemName + "%");
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                return r.getInt("menuitem_id");
            }
            throw new SQLException("Menu item not found: " + itemName);
            }
        
    }

    /**
     * Finds menu item by id.
     *
     * @param _id menu item id
     * @return menu item
     * @throws SQLException if an error occurs
     */

    public static MenuItem find(int _id) throws SQLException {
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_CATEGORY +
                " FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, _id);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                return new MenuItem(r.getInt(COL_ID),
                        r.getString(COL_NAME),
                        r.getString(COL_CATEGORY)
                );
            } else {
                return null;
            }
        }
    }

    /**
     * Finds all menu items.
     *
     * @return list of menu items
     * @throws SQLException if an error occurs
     */

    public static List<MenuItem> findAll() throws SQLException {
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_CATEGORY
                    + " FROM " + TABLE + " ORDER BY " + COL_ID;
        List<MenuItem> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                out.add(new MenuItem(r.getInt(COL_ID), 
                        r.getString(COL_NAME), 
                        r.getString(COL_CATEGORY)
                ));
            }
            return out;
        }
    }

    /**
     * Searches for a menu item by name.
     *
     * @param name menu item name
     * @return list of menu items with matching name
     * @throws SQLException if an error occurs
     */

    public static List<MenuItem> search(String name) throws SQLException{
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", " + COL_CATEGORY +
                " FROM " + TABLE + " WHERE " + COL_NAME + " ILIKE '%" + name + "%' ORDER BY " + COL_ID;

        List<MenuItem> out = new ArrayList<>();
        try (Connection c = Db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                out.add(new MenuItem(
                        r.getInt(COL_ID),
                        r.getString(COL_NAME),
                        r.getString(COL_CATEGORY)
                ));
            }
        }
        return out;
    }

    /**
     * Inserts a menu item into the database.
     *
     * @param _name menu item name
     * @param _category type of menu item
     * @return successful insert
     * @throws SQLException if an error occurs
     */

    public static int insert(String _name, String _category) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (" + COL_NAME + ", " + COL_CATEGORY + ") VALUES (?, ?)";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, _name);
            ps.setString(2, _category);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates the name of a menu item.
     *
     * @param _id menu item id
     * @param _name new menu item name
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateName(int _id, String _name) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_NAME + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, _name);
            ps.setInt(2, _id);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates category of a menu item.
     *
     * @param _id menu item id
     * @param _category new menu item category
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateCategory(int _id, String _category) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_CATEGORY + " = ? WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, _category);
            ps.setInt(2, _id);
            return ps.executeUpdate();
        }
    }

    /**
     * Deletes a menu item by id.
     *
     * @param _id menu item id
     * @return successful delete
     * @throws SQLException if an error occurs
     */

    public int delete(int _id) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, _id);
            return ps.executeUpdate();
        }
    }
}