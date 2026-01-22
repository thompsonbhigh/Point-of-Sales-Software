/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Manages the ordering interface for selecting meals, entrees, sides, and drinks.
 *
 * Author: Team 42
 */

package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import model.Inventory;
import model.OrderSession;
import model.OrderEntry;
import model.MenuItem;

public class MealScreenController implements Initializable{ 

    /**
     * Navigates back to the main meal screen view.
     *
     * @param event the action event triggered by the navigation button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void navigateToMealScreen(ActionEvent event) throws IOException {
        Parent mealScreenRoot = FXMLLoader.load(getClass().getResource("/view/Meal_Screen.fxml"));
        Scene mealScreenScene = new Scene(mealScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }


    /**
     * Handles user selection of a meal size and navigates to the main meal screen.
     *
     * @param event the action event triggered by selecting a size
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void handleSizeClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String size = clickedButton.getText();
        
        OrderSession.getInstance().setSelectedSize(size);

        navigateToMealScreen(event);
    }

    /**
     * Handles user selection of a menu item and adds it to the current order session.
     *
     * @param event the action event triggered by selecting a menu item
     */
    @FXML
    private void handleMenuItemClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String itemName = clickedButton.getText();

        String size = OrderSession.getInstance().getSelectedSize();
    

        if (size == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Select a size before choosing an item!");
            alert.showAndWait();
            return;
        }

        MenuItemDao menuItemDao = new MenuItemDao();
        int menuId;
        try {
            menuId = menuItemDao.findIdByName(itemName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        OrderEntry entry = new OrderEntry(0,0, menuId, size, 0.0);
        OrderSession.getInstance().addEntry(entry);
    }

    @FXML private Button commonButton1;
    @FXML private Button commonButton2;
    @FXML private Button commonButton3;
    @FXML private Button commonButton4;
    @FXML private Button commonButton5;
    @FXML private Button commonButton6;
    @FXML private Button commonButton7;
    @FXML private Button commonButton8;

    private List<Button> commonButtons;

    @FXML private TextField itemNumEntry;
    @FXML private Text orderNum;
    @FXML private TableView<MenuItem> orderTable;
    @FXML private TableColumn<MenuItem, Integer> itemNum;
    @FXML private TableColumn<MenuItem, String> items;
    @FXML private Text price;

    @FXML private Button seasonalButton;
    @FXML private Button seasonalButton2;


    /**
     * Initializes the controller and populates seasonal and common order buttons.
     *
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (commonButton1 != null) {
            commonButtons = Arrays.asList(
                commonButton1, commonButton2, commonButton3, commonButton4,
                commonButton5, commonButton6, commonButton7, commonButton8
            );
            populateCommonOrderButtons();
        }

        if (seasonalButton != null) {
            populateSeasonalButton();
        }
        if (seasonalButton2 != null) {
            populateSeasonalButton2();
        }

        if (itemNum != null) {
            itemNum.setCellValueFactory(new PropertyValueFactory<>("menuitem_id"));
            items.setCellValueFactory(new PropertyValueFactory<>("menuitem_name"));
            refreshOrderTable();
        }
    }


    /**
     * Populates the second seasonal item button with data from the database.
     */

    private void populateSeasonalButton() {
        try {
            MenuItemDao menuItemDao = new MenuItemDao();
            MenuItem seasonalItem = menuItemDao.find(28);

            if (seasonalItem != null && !seasonalItem.getMenuitem_name().equals("EMPTY")) {
                seasonalButton.setText(seasonalItem.getMenuitem_name());
                seasonalButton.setVisible(true);
            } else {
                seasonalButton.setVisible(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            seasonalButton.setVisible(false);
        }
    }

        /**
        * Populates the common order buttons with the most frequent order combinations.
        */

        private void populateSeasonalButton2() {
        try {
            MenuItemDao menuItemDao = new MenuItemDao();
            MenuItem seasonalItem2 = menuItemDao.find(29);

            if (seasonalItem2 != null && !seasonalItem2.getMenuitem_name().equals("EMPTY")) {
                seasonalButton2.setText(seasonalItem2.getMenuitem_name());
                seasonalButton2.setVisible(true);
            } else {
                seasonalButton2.setVisible(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            seasonalButton2.setVisible(false);
        }
    }

    /**
     * Populates the common order buttons with the most frequent order combinations.
     */

    private void populateCommonOrderButtons() {
        OrderEntryDao entryDao = new OrderEntryDao();
        MenuItemDao menuItemDao = new MenuItemDao();
        
        Map<Integer, String> menuItemCache = new HashMap<>();
        try {
            List<MenuItem> allItems = menuItemDao.findAll();

            for (MenuItem item : allItems) {
                menuItemCache.put(item.getMenuitem_id(), item.getMenuitem_name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            for (Button btn : commonButtons) {
                btn.setText("DB Error");
                btn.setDisable(true);
            }
            return;
        }

        try {
            List<String> topOrders = entryDao.findTopCommonOrderFingerprints();

            for (int i = 0; i < commonButtons.size(); i++) {
                Button btn = commonButtons.get(i);
                if (i < topOrders.size()) {
                    String fingerprint = topOrders.get(i);
                    String label = generateLabelFromFingerprint(fingerprint, menuItemCache); 
                    
                    btn.setText(label);
                    btn.setUserData(fingerprint);
                    btn.setDisable(false);
                } else {
                    btn.setText("Common " + (i + 1));
                    btn.setDisable(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        /**
         * Handles logic for if().
         *
         * @param null user input parameter
         * @return result or status value
         */

            if (commonButtons != null) {
                for (Button btn : commonButtons) {
                    btn.setText("Error");
                    btn.setDisable(true);
                }
            }
        }
    }

    /**
     * Generates a formatted label for a common order fingerprint.
     *
     * @param fingerprint a string representing menu item IDs and sizes
     * @param nameCache a map linking menu item IDs to their names
     * @return a formatted string summarizing the common order
     */

    private String generateLabelFromFingerprint(String fingerprint, Map<Integer, String> nameCache) {
        if (fingerprint == null || fingerprint.isEmpty()) {
            return "N/A";
        }

        Map<String, Integer> itemCounts = new HashMap<>();
        String[] items = fingerprint.split(",");

        for (String item : items) {
            try {
                String[] parts = item.split(":");
                int menuId = Integer.parseInt(parts[0]);
                String size = parts[1];
                
                String name = nameCache.getOrDefault(menuId, "Unknown");
                
                String fullItemName = name + " (" + size + ")";
                
                itemCounts.put(fullItemName, itemCounts.getOrDefault(fullItemName, 0) + 1);

            } catch (Exception e) {}
        }

        return itemCounts.entrySet().stream()
                .map(entry -> entry.getValue() + "x " + entry.getKey())
                .collect(Collectors.joining("\n"));
    }
    

    /**
      * Handles the selection of a common order button and adds the corresponding items to the current order.
     *
     * @param event the action event triggered by clicking a common order button
     */
    @FXML
    private void handleCommonOrderClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        
        String fingerprint = (String) clickedButton.getUserData();

        if (fingerprint == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Common Order");
            alert.setHeaderText(null);
            alert.setContentText("No common order is available for this slot.");
            alert.showAndWait();
            return;
        }

        OrderEntryDao entryDao = new OrderEntryDao();
        OrdersDao ordersDao = new OrdersDao();

        try {
            String[] items = fingerprint.split(",");
            
            int orderId = ordersDao.getLastOrderId();
            
            int itemsAddedCount = 0;

            for (String item : items) {
                String[] parts = item.split(":");
                int menuId = Integer.parseInt(parts[0]);
                String size = parts[1];
                
                entryDao.insert(orderId, menuId, size);
                itemsAddedCount++;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Common Order Added");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added " + itemsAddedCount + " items (" + clickedButton.getText().replace("\n", ", ") + ") to order #" + orderId + ".");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Could not add common order");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }


    /**
     * Adds the current session’s selected items to the database order.
     *
     * @param event the action event triggered by pressing the “Add to Order” button
     * @throws IOException if the view fails to update
     */
    @FXML
    private void addToOrder(ActionEvent event) throws IOException {
        OrderSession session = OrderSession.getInstance();
        try {
            OrderEntryDao entryDao = new OrderEntryDao();
            OrdersDao ordersDao = new OrdersDao();
            int orderId = ordersDao.getLastOrderId();

            for (OrderEntry entry : session.getEntries()) {
                entryDao.insert(orderId, entry.getMenuItemId(), entry.getSize());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Added");
            alert.setHeaderText(null);
            alert.setContentText("Order successfully added to order #" + orderId + ".");
            alert.showAndWait();
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Could not add order");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }


    /**
     * Loads the next screen (Meal_Screen_Start.fxml).
     *
     * @param event the action event triggered by pressing the “Next” button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void handleNextButtonClick(ActionEvent event) throws IOException {
        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/Meal_Screen_Start.fxml"));
        Scene mainScreenScene = new Scene(mainScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }


    /**
     * Cancels the current order, deletes it from the database, and returns to the main screen.
     *
     * @param event the action event triggered by pressing the “Cancel” button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void cancelOrder(ActionEvent event) throws IOException {
        OrdersDao ordersDao = new OrdersDao();
        OrderEntryDao entryDao = new OrderEntryDao();
        try {
            int orderId = ordersDao.getLastOrderId();
            int entriesDeleted = entryDao.deleteByOrder(orderId);
            int rowsDeleted = ordersDao.delete();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Canceled");
            alert.setHeaderText(null);
            if (rowsDeleted > 0) {
                alert.setContentText("Order successfully canceled.");
            } else {
                alert.setContentText("Order was not canceled.");
            }
            alert.showAndWait();
            OrderSession.getInstance().clear();
        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Failed to cancel order");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }

        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }


    /**
     * Completes the current meal session and returns to the main screen.
     *
     * @param event the action event triggered by pressing the “Complete” button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void completeMeal(ActionEvent event) throws IOException {
        OrderSession session = OrderSession.getInstance();
        session.clear();
        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }




    /**
     * Refreshes the order table to display the current items and total price.
     */
    @FXML
    private void refreshOrderTable(){
        OrdersDao ordersDao = new OrdersDao();
        OrderEntryDao entryDao = new OrderEntryDao();
        try {
            int orderId = ordersDao.getLastOrderId();
            orderNum.setText(""+orderId);
            List<OrderEntry> entries = entryDao.findOrder(orderId);
            ObservableList<MenuItem> itemList = FXCollections.observableArrayList();
            double totalPrice=ordersDao.find(orderId).getTotalCost();

            for (OrderEntry entry : entries) {
                MenuItem i = MenuItemDao.find(entry.getMenuItemId());
                if (i != null) {
                    itemList.add(i);
                }
            }
            orderTable.setItems(itemList);
            price.setText(String.format("$%.2f", totalPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Removes a selected item from the current order.
     *
     * @throws IOException if the view cannot be refreshed
     */
    @FXML
    private void removeFromOrder() throws IOException {
        OrdersDao ordersDao = new OrdersDao();
        OrderEntryDao entryDao = new OrderEntryDao();
        try {
            int orderId = ordersDao.getLastOrderId();
            int del = Integer.parseInt(itemNumEntry.getText());

            int entriesDeleted = entryDao.deleteItemFromOrder(orderId, del);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item deleted");
            alert.setHeaderText(null);
            if (entriesDeleted > 0) {
                List<OrderEntry> remainingEntries = entryDao.findOrder(orderId);
                double newTotal = 0;
                for (OrderEntry entry : remainingEntries) {
                    newTotal += entry.getPrice();
                }
                ordersDao.updateCost(orderId, newTotal);
                alert.setContentText("Item successfully deleted.");
            } else {
                alert.setContentText("Item was not deleted.");
            }
            alert.showAndWait();

            refreshOrderTable();
        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Failed to delete item");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }

    /**
     * Displays a results dialog with query output.
     *
     * @param results the result string to be displayed
     */

    private void showResultsDialog2(String results) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Query Result");
        alert.setHeaderText("Results from Query 1");
        alert.setContentText(results);
        alert.showAndWait();
    }

    /**
     * Displays a results dialog with query output.
     *
     * @param results the result string to be displayed
     */

    private void showResultsDialog(String results) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Query Result");
        alert.setHeaderText("Results from Query 1");
        alert.setContentText(results);
        alert.showAndWait();
    }
}
