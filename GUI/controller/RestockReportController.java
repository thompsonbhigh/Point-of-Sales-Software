/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Generates and displays restock reports for low inventory items.
 *
 * Author: Team 42
 */

package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;

import model.Inventory;
import model.Restock;

import dao.InventoryDao;
import dao.RestockDao;

public class RestockReportController {

    //join class
    public static class RestockReportEntry {
        private String itemName;
        private double onHand;
        private double minimum;
        private boolean restock;

      /**
       * Represents a single entry in the restock report table, 
       * containing item details such as name, quantity on hand, 
       * minimum threshold, and restock status.
       *
       * @param itemName the name of the inventory item
       * @param onHand the current quantity in stock
       * @param minimum the minimum threshold for the item
       * @param restock indicates whether the item needs restocking
       */

        public RestockReportEntry(String itemName, double onHand, double minimum, boolean restock) {
            this.itemName = itemName;
            this.onHand = onHand;
            this.minimum = minimum;
            this.restock = restock;
        }

        public String getItemName() {return itemName;}
        public double getOnHand() {return onHand;}
        public double getMinimum() {return minimum;}
        public boolean getRestock() {return restock;}
    }

    /**
     * Retrieves all restock report entries by joining inventory and restock data.
     * Determines which items are below or equal to their minimum stock level 
     * and returns them as a list of report entries.
     *
     * @return a list of {@link RestockReportEntry} objects representing the current restock report
     */

    public List<RestockReportEntry> restockEntries(){
        List<RestockReportEntry> entries = new ArrayList<>();
        try {
            List<Restock> restocks = RestockDao.findRestocks();
            for (Restock r : restocks) {
                Inventory inv = InventoryDao.findById(r.getItemID());
                String itemName = inv.getItemName();
                double onHand = inv.getQuantity();
                double minimum = r.getMin();
                boolean needsRestock = onHand <= minimum;
                entries.add(new RestockReportEntry(itemName, onHand, minimum, needsRestock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    @FXML private TableView<RestockReportEntry> restockTable;
    @FXML private TableColumn<RestockReportEntry, String> itemColumn;
    @FXML private TableColumn<RestockReportEntry, Double> onHandColumn;
    @FXML private TableColumn<RestockReportEntry, Double> minimumColumn;
    @FXML private TableColumn<RestockReportEntry, Boolean> restockColumn;
    @FXML private Button backButton;
    @FXML private Button restockButton;

    /**
     * Initializes the restock report table by setting up column mappings 
     * and populating it with restock data from the database.
     * This method is automatically invoked when the scene is loaded.
     */
    @FXML
    public void initialize() {
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        onHandColumn.setCellValueFactory(new PropertyValueFactory<>("onHand"));
        minimumColumn.setCellValueFactory(new PropertyValueFactory<>("minimum"));
        restockColumn.setCellValueFactory(new PropertyValueFactory<>("restock"));

        List<RestockReportEntry> entries = restockEntries();
        ObservableList<RestockReportEntry> observableEntries = FXCollections.observableArrayList(entries);
        restockTable.setItems(observableEntries);
    }


    /**
     * Navigates the user back to the Manager Screen interface.
     *
     * @param event the event triggered by user interaction (e.g., button click)
     * @throws IOException if the FXML file for the manager view cannot be loaded
     */
    @FXML
    private void navigateToManagerScreen(ActionEvent event) throws IOException {
        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/manager view.fxml"));
        Scene mainScreenScene = new Scene(mainScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }


    /**
     * Handles the placement of restock orders for items below the minimum threshold.
     * Automatically updates the inventory quantities to their maximum defined levels 
     * and refreshes the report table upon completion.
     *
     * @param event the event triggered by user interaction (e.g., button click)
     */
    @FXML
    private void PlaceRestock(ActionEvent event) {
        // Implement restock order placement logic here
        String sql = "UPDATE inventory set quantity = ? WHERE item_id = ?";
        
        try {
            List<Restock> restocks = RestockDao.findRestocks();

            for (Restock r : restocks) {
                Inventory inv = InventoryDao.findById(r.getItemID());
                double newQuantity = inv.getQuantity() + (r.getMax() - inv.getQuantity());
                try (var c = dao.Db.connect();
                     var ps = c.prepareStatement(sql)) {
                    ps.setDouble(1, newQuantity);
                    ps.setInt(2, r.getItemID());
                    ps.executeUpdate();
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Restock Order");
            alert.setHeaderText(null);
            alert.setContentText("Restock order placed successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<RestockReportEntry> entries = restockEntries();
        ObservableList<RestockReportEntry> observableEntries = FXCollections.observableArrayList(entries);
        restockTable.setItems(observableEntries);
    }

}