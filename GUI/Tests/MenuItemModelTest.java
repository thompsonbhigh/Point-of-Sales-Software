package Tests;

import dao.*;
import model.MenuItem;

import java.sql.*;
import java.util.List;

public class MenuItemModelTest {
    public static MenuItemDao menuItemDao = new MenuItemDao();
    public static int lastInsertedId;

    public static void getLastInsertedId() {
        String sql = "SELECT MAX(menuitem_id) FROM menuitem";
        try (Connection c = Db.connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery();) {
            if (r.next()) {
                lastInsertedId = r.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_findIdByName() {
        try {
           int id = MenuItemDao.findIdByName("test");
           System.out.println("Menu Item ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_findAll() {
        try {
            List<MenuItem> menuItems = menuItemDao.findAll();
            for (MenuItem mi : menuItems) {
                System.out.println(mi);            
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_insert() {
        try {
            int rowsInserted = menuItemDao.insert("Test Item", "Entree");
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_find() {
        try {
            getLastInsertedId();
            MenuItem mi = menuItemDao.find(lastInsertedId);
            System.out.println("Found menu item: " + mi);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updateCategory() {
        try {
            getLastInsertedId();
            int rowsUpdated = menuItemDao.updateCategory(lastInsertedId, "Premium Entree");
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_updateName() {
        try {
            getLastInsertedId();
            int rowsUpdated = menuItemDao.updateName(lastInsertedId, "Updated Test Item");
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test_delete() {
        try {
            getLastInsertedId();
            int rowsDeleted = menuItemDao.delete(lastInsertedId);
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        switch (args[0]) {
            case "findIdByName":
                test_findIdByName();
                break;
            case "findAll":
                test_findAll();
                break;
            case "insert":
                test_insert();
                break;
            case "find":
                test_find();
                break;
            case "updateCategory":
                test_updateCategory();
                break;
            case "updateName":
                test_updateName();
                break;
            case "delete":
                test_delete();
                break;
            default:
                System.out.println("Invalid argument. Use one of: findAll, insert, find, updateCategory, updateName, delete");
        }
    }
}
