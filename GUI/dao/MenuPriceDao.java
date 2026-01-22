/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for menu prices, including size tiers and premium pricing logic.
 *
 * Author: Team 42
 */

package dao;

import model.MenuPrice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuPriceDao {
    
    private static final String TABLE = "menuprice";
    private static final String COL_SIZE = "size";
    private static final String COL_PRICE = "price";

    /**
     * Finds all menu prices.
     *
     * @return list of menu prices
     * @throws SQLException if an error occurs
     */

    public static List<MenuPrice> findAll() throws SQLException {
    String sql = "SELECT " + COL_SIZE + ", " + COL_PRICE +
                 " FROM " + TABLE + " ORDER BY " + COL_SIZE;
    List<MenuPrice> out = new ArrayList<>();
    try (Connection c = Db.connect();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery(sql)) {
      while (r.next()) {
        out.add(new MenuPrice(
            r.getString(COL_SIZE),
            r.getDouble(COL_PRICE)
        ));
      }
    }
    return out;
  }

  /**
   * Inserts a menu price into the database.
   *
   * @param size size
   * @param price price of size
   * @return successful insert
   * @throws SQLException if an error occurs
   */

  public static int insert(String size, double price) throws SQLException {
    String sql = "INSERT INTO " + TABLE + " (" + COL_SIZE + ", " + COL_PRICE + ") VALUES (?, ?)";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, size);
      ps.setDouble(2, price);
      return ps.executeUpdate(); // rows affected (expect 1)
    }
  }

    /**
     * Updates size of menu price.
     *
     * @param size old size
     * @param newSizeName new size
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int updateName(String size, String newSizeName) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_SIZE + "=? WHERE " + COL_SIZE + "=?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newSizeName);
            ps.setString(2, size);
            return ps.executeUpdate();
        }
    }

  /**
   * Updates the price of a menu price.
   *
   * @param size size
   * @param newPrice new price
   * @return successful price
   * @throws SQLException if an error occurs
   */

  public static int updatePrice(String size, double newPrice) throws SQLException {
    String sql = "UPDATE " + TABLE + " SET " + COL_PRICE + "=? WHERE " + COL_SIZE + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setDouble(1, newPrice);
      ps.setString(2, size);
      return ps.executeUpdate();
    }
  }

  /**
   * Deletes a menu price by size
   *
   * @param size size
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public static int delete(String size) throws SQLException {
    String sql = "DELETE FROM " + TABLE + " WHERE " + COL_SIZE + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, size);
      return ps.executeUpdate();
    }
  }
}