/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: DAO for recipe mappings connecting menu items to ingredient usage.
 *
 * Author: Team 42
 */

package dao;
import model.Recipes;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesDao {
    private static final String TABLE = "recipes";
    private static final String COL_RECIPE_ID = "recipe_id";
    private static final String COL_MENUITEM_ID = "menuitem_id";
    private static final String COL_ITEM_ID = "item_id";
    private static final String COL_QUANTITY_REQUIRED = "quantity_required";

  /**
   * Finds all recipes in the database.
   *
   * @return list of all recipes
   * @throws SQLException if an error occurs
   */

  public static List<Recipes> findAll() throws SQLException {
    String sql = "SELECT " + COL_RECIPE_ID + ", " + COL_MENUITEM_ID + ", " + COL_ITEM_ID + ", " + COL_QUANTITY_REQUIRED +
                 " FROM " + TABLE + " ORDER BY " + COL_RECIPE_ID;
    List<Recipes> out = new ArrayList<>();
    try (Connection c = Db.connect();
         Statement s = c.createStatement();
         ResultSet r = s.executeQuery(sql)) {
      while (r.next()) {
        out.add(new Recipes(
            r.getInt(COL_RECIPE_ID),
            r.getInt(COL_MENUITEM_ID),
            r.getInt(COL_ITEM_ID),
            r.getDouble(COL_QUANTITY_REQUIRED)
        ));
      }
    }
    return out;
  }

  /**
   * Finds the recipe for a menu item by id.
   *
   * @param id menu item id
   * @return list of recipe items
   * @throws SQLException if an error occurs
   */

  public static List<Recipes> findByMenuItemId(int id) throws SQLException {
      String sql = "SELECT * FROM " + TABLE + " WHERE " + COL_MENUITEM_ID + " = " + id;
      List<Recipes> out = new ArrayList<>();
      try (Connection c = Db.connect();
           Statement s = c.createStatement();
           ResultSet r = s.executeQuery(sql)) {
          while (r.next()) {
              out.add(new Recipes(
                      r.getInt(COL_RECIPE_ID),
                      r.getInt(COL_MENUITEM_ID),
                      r.getInt(COL_ITEM_ID),
                      r.getDouble(COL_QUANTITY_REQUIRED)
              ));
          }
      }
      return out;
  }

  /**
   * Inserts a new recipe into the database.
   *
   * @param menuitemID menu item id
   * @param itemID inventory id
   * @param quantiyRequired quantity used in recipe
   * @return successful insert
   * @throws SQLException if an error occurs
   */

  public static int insert(int menuitemID, int itemID, double quantiyRequired) throws SQLException {
    String sql = "INSERT INTO " + TABLE + " (" + COL_MENUITEM_ID + ", " + COL_ITEM_ID + ", " + COL_QUANTITY_REQUIRED +  ") VALUES (?, ?, ?)";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, menuitemID);
      ps.setInt(2, itemID);
      ps.setDouble(3, quantiyRequired);
      return ps.executeUpdate(); // rows affected (expect 1)
    }
  }

    /**
     * Updates menu item id by recipe id
     *
     * @param recipeID recipe id
     * @param menuID menu id
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int UpdateMenuID(int recipeID, int menuID) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_MENUITEM_ID + "=? WHERE " + COL_RECIPE_ID + "=?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, menuID);
            ps.setInt(2, recipeID);
            return ps.executeUpdate();
        }
    }

    /**
     * Updates inventory item id.
     *
     * @param recipeID recipe id
     * @param invID inventory id
     * @return successful update
     * @throws SQLException if an error occurs
     */

    public static int UpdateInvID(int recipeID, int invID) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " + COL_ITEM_ID + "=? WHERE " + COL_RECIPE_ID + "=?";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, invID);
            ps.setInt(2, recipeID);
            return ps.executeUpdate();
        }
    }

  /**
   * Updates quantity required for a recipe.
   *
   * @param recipeID recipe id
   * @param quantityRequired new quantity required
   * @return successful update
   * @throws SQLException if an error occurs
   */
  public static int UpdateQuantityRequired(int recipeID, double quantityRequired) throws SQLException {
    String sql = "UPDATE " + TABLE + " SET " + COL_QUANTITY_REQUIRED + "=? WHERE " + COL_RECIPE_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setDouble(1, quantityRequired);
      ps.setInt(2, recipeID);
      return ps.executeUpdate();
    }
  }

  /**
   * Connects recipe ids by menu item name.
   *
   * @param menu_name menu item name
   * @return map of recipes matching to menu item
   * @throws SQLException if an error occurs
   */
  public static Map<String, Double> joinRecipesByMenuItemID(String menu_name) {
    String sql = "Select i.item_name, r.quantity_required" +
                 " From recipes r" +
                 " Join inventory i on r.item_id = i.item_id" +
                 " Join menuitem m on r.menuitem_id = m.menuitem_id" +
                 " Where m.menuitem_name = ?";
    Map<String, Double> resultMap = new HashMap<>();
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, menu_name);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String item = rs.getString("item_name");
        double quantityRequired = rs.getDouble("quantity_required");
        resultMap.put(item, quantityRequired);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resultMap;
  }


  /**
   * Deletes a recipe by id.
   *
   * @param recipeID recipe id
   * @return successful delete
   * @throws SQLException if an error occurs
   */

  public int delete(int recipeID) throws SQLException {
    String sql = "DELETE FROM " + TABLE + " WHERE " + COL_RECIPE_ID + "=?";
    try (Connection c = Db.connect();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, recipeID);
      return ps.executeUpdate();
    }
  }
}