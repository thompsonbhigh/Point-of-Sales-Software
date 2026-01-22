/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Handles managerial views such as employee management, inventory, and reporting access.
 *
 * Author: Team 42
 */

package controller;

//JavaFX Imports
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
//Exceptions
import java.io.IOException;
import java.lang.foreign.Linker.Option;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
//Dao imports
//model imports
import model.MenuItem;
import model.Recipes;
import model.Schedule;
import model.Employee;
import model.Inventory;

import java.sql.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class ManagerViewController {

    //Inventory
    @FXML private TextField editItem;
    @FXML private TextField invEntry;
    @FXML private TextField invSearch;
    @FXML private ComboBox<String> invFieldSelect;

    @FXML private TableView<model.Inventory> inventoryTable;
    @FXML private TableColumn<model.Inventory, Integer> item_id;
    @FXML private TableColumn<model.Inventory, String> item_name;
    @FXML private TableColumn<model.Inventory, Integer> quantity;
    @FXML private TableColumn<model.Inventory, String> units;

    //Size/Price
    @FXML private TextField editSize;
    @FXML private TextField sizeEntry;
    @FXML private ComboBox<String> sizeFieldSelect;

    @FXML private TableView<model.MenuPrice> sizeTable;
    @FXML private TableColumn<model.MenuPrice, String> size;
    @FXML private TableColumn<model.MenuPrice, Double> price;

    //Employees
    @FXML private TextField empSearch;
    @FXML private TextField empIDSelect;
    @FXML private TextField empInfo;
    @FXML private ComboBox<String> empFieldSelect;

    @FXML private TableView<model.Employee> employeeTable;
    @FXML private TableColumn<model.Employee, Integer> employee_id;
    @FXML private TableColumn<model.Employee, String> employee_name;
    @FXML private TableColumn<model.Employee, Double> salary;
    @FXML private TableColumn<model.Employee, Double> hours_worked;
    @FXML private TableColumn<model.Employee, String> position;
    @FXML private TableColumn<model.Employee, Boolean> employment_status;

    //Schedule
    @FXML private GridPane calendar;
    @FXML private Text month;
    @FXML private ListView<String> currShifts;
    @FXML private TextField date;
    @FXML private TextField schedDelDate;
    @FXML private TextField hourEntry;
    @FXML private TextField schedIdEntry;
    @FXML private TextField schedDelID;

    private YearMonth currYearMonth;

    //Recipes
    @FXML private TextField recSearch;
    @FXML private TextField editRecipe;
    @FXML private TextField recipeEntry;
    @FXML private ComboBox<String> recipeFieldSelect;

    @FXML private TableView<model.Recipes> recipeTable;
    @FXML private TableColumn<model.Recipes, Integer> recipe_id;
    @FXML private TableColumn<model.Recipes, String> menuitem_id_Recipes;
    @FXML private TableColumn<model.Recipes, String> invitem_id;
    @FXML private TableColumn<model.Recipes, Double> quantity_required;

    //Menu Item
    @FXML private TextField menuItemSearch;
    @FXML private TextField editMenu;
    @FXML private TextField menuItemEntry;
    @FXML private ComboBox<String> menuFieldSelect;

    @FXML private TableView<model.MenuItem> menuItemTable;
    @FXML private TableColumn<model.MenuItem, Integer> menuitem_id;
    @FXML private TableColumn<model.MenuItem, String> menuitem_name;
    @FXML private TableColumn<model.MenuItem, String> category;



    /**
     * Navigates back to the Main Screen JavaFX file through user button input 
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToEmployeeScreen(ActionEvent event) throws IOException {
        Parent mealScreenRoot = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Scene mealScreenScene = new Scene(mealScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }

    //REPORT NAVIGATION STARTS HERE

    /**
     * Navigates to the Product Usage Chart JavaFX file through user button input
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToProductUsageReport(ActionEvent event) throws IOException {
        Parent productUsageRoot = FXMLLoader.load(getClass().getResource("/view/ProductUsageChart.fxml"));
        Scene mealScreenScene = new Scene(productUsageRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }

    /**
     * Navigates to the Sales Report JavaFX file through user button input
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToSalesReport(ActionEvent event) throws IOException {
        Parent productUsageRoot = FXMLLoader.load(getClass().getResource("/view/SalesReport.fxml"));
        Scene mealScreenScene = new Scene(productUsageRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }


    /**
     * Navigates to the X Report JavaFX file through user button input
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToXReport(ActionEvent event) throws IOException {
        XAndZReportController.isZReport = false;
        Parent productUsageRoot = FXMLLoader.load(getClass().getResource("/view/XAndZReport.fxml"));
        Scene mealScreenScene = new Scene(productUsageRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }


    /**
     * Navigates to the Z Report JavaFX file through user button input
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToZReport(ActionEvent event) throws IOException {
        XAndZReportController.isZReport = true;
        Parent productUsageRoot = FXMLLoader.load(getClass().getResource("/view/XAndZReport.fxml"));
        Scene mealScreenScene = new Scene(productUsageRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
    }
  

    /**
     * Navigates to the Restock Report JavaFX file through user button input
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToRestockReport(ActionEvent event) throws IOException {
        Parent restockRoot = FXMLLoader.load(getClass().getResource("/view/Restock_Screen.fxml"));
        Scene restockScene = new Scene(restockRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(restockScene);
        window.show();
    }
    //REPORT NAVIGATION ENDS HERE


    /**
     * Alerts the user when an unfinished button has been pressed 
     *
     * @param event User input from button press on JavaFX file
     */
    @FXML
    private void handleButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Function Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText("This button's functionality has not been implemented yet.");
        alert.showAndWait();
    }


    /**
    * Initializes the controller by setting up table columns, populating ComboBox field selections,
    * and loading data from the database into their corresponding TableViews.
    * <p>
    * Specifically, this method performs the following:
    * <ul>
    *   <li>Populates selection ComboBoxes for Inventory, Sizes, Employees, Recipes, and Menu Items.</li>
    *   <li>Configures TableView column cell value factories for data binding.</li>
    *   <li>Retrieves data from the database using DAO classes and loads it into observable lists.</li>
    *   <li>Initializes the calendar view with the current month and year.</li>
    * </ul>
    *
    *  @throws SQLException if a database access error occurs while initializing data.
    */
    @FXML
    private void initialize() throws SQLException {
        //Inventory
        invFieldSelect.getItems().addAll("Name", "Quantity", "Unit");

        item_id.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        item_name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        units.setCellValueFactory(new PropertyValueFactory<>("unit"));

        //Sizes
        sizeFieldSelect.getItems().addAll("Size", "Price");

        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Employees
        empFieldSelect.getItems().addAll("Name", "Salary", "Hours Worked", "Position", "Employment Status");

        employee_id.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        employee_name.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        hours_worked.setCellValueFactory(new PropertyValueFactory<>("hours_worked"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        employment_status.setCellValueFactory(new PropertyValueFactory<>("employment_status"));

        //Recipes
        recipeFieldSelect.getItems().addAll("Menu Item ID", "Inventory ID", "Quantity Required");

        recipe_id.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
        menuitem_id_Recipes.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        invitem_id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        quantity_required.setCellValueFactory(new PropertyValueFactory<>("quantityRequired"));

        //Menu Items
        menuFieldSelect.getItems().addAll("Menu Item Name", "Category");

        menuitem_id.setCellValueFactory(new PropertyValueFactory<>("menuitem_id"));
        menuitem_name.setCellValueFactory(new PropertyValueFactory<>("menuitem_name"));
        category.setCellValueFactory(new PropertyValueFactory<>("menuitem_category"));

        //Fill tables
        try {
            ObservableList<model.Inventory> invData = FXCollections.observableArrayList(InventoryDao.findAll());
            inventoryTable.setItems(invData);
            ObservableList<model.MenuPrice> sizeData = FXCollections.observableArrayList(MenuPriceDao.findAll());
            sizeTable.setItems(sizeData);
            ObservableList<model.Employee> empData = FXCollections.observableArrayList(EmployeeDao.findAll());
            employeeTable.setItems(empData);
            ObservableList<model.Recipes> recipeData = FXCollections.observableArrayList(RecipesDao.findAll());
            recipeTable.setItems(recipeData);
            ObservableList<model.MenuItem> menuData = FXCollections.observableArrayList(MenuItemDao.findAll());
            menuItemTable.setItems(menuData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        currYearMonth = YearMonth.now();
        populateCalendar(currYearMonth);
    }

    /**
     * Refreshes all TableViews by reloading data from their respective database tables.
     * This includes Inventory, Sizes, Employees, Recipes, and Menu Items.
     */

    private void refreshTable() {
        try {
            ObservableList<model.Inventory> invData = FXCollections.observableArrayList(InventoryDao.findAll());
            inventoryTable.setItems(invData);
            ObservableList<model.MenuPrice> sizeData = FXCollections.observableArrayList(MenuPriceDao.findAll());
            sizeTable.setItems(sizeData);
            ObservableList<model.Employee> empData = FXCollections.observableArrayList(EmployeeDao.findAll());
            employeeTable.setItems(empData);
            ObservableList<model.Recipes> recipeData = FXCollections.observableArrayList(RecipesDao.findAll());
            recipeTable.setItems(recipeData);
            ObservableList<model.MenuItem> menuData = FXCollections.observableArrayList(MenuItemDao.findAll());
            menuItemTable.setItems(menuData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Inventory table by reloading all inventory data from the database. 
     */

    private void refreshInv(){
        try {
            ObservableList<model.Inventory> invData = FXCollections.observableArrayList(InventoryDao.findAll());
            inventoryTable.setItems(invData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Sizes table by reloading all size and price data from the database.
     */

    private void refreshSize(){
        try {
            ObservableList<model.MenuPrice> sizeData = FXCollections.observableArrayList(MenuPriceDao.findAll());
            sizeTable.setItems(sizeData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Employee table by reloading all employee records from the database.
     */

    private void refreshEmployee(){
        try {
            ObservableList<model.Employee> empData = FXCollections.observableArrayList(EmployeeDao.findAll());
            employeeTable.setItems(empData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Recipe table by reloading all recipe data from the database.
     */

    private void refreshRecipe(){
        try {
            ObservableList<model.Recipes> recipeData = FXCollections.observableArrayList(RecipesDao.findAll());
            recipeTable.setItems(recipeData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the Menu Items table by reloading all menu items from the database.
     */

    private void refreshMenu(){
        try {
            ObservableList<model.MenuItem> menuData = FXCollections.observableArrayList(MenuItemDao.findAll());
            menuItemTable.setItems(menuData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INVENTORY STARTS HERE

    /**
     * Adds a new inventory item with default placeholder values and refreshes the table.
     *
     * @throws SQLException  if a database access error occurs during insertion.
     */
    @FXML
    private void addItem() throws SQLException {
        InventoryDao.insert("EMPTY", 0, "EMPTY");
        refreshInv();
    }


    /**
     * Searches inventory items based on user input and updates the inventory table with the results.
     *
     * @throws SQLException if a database access error occurs during the search.
     */
    @FXML
    private void inventorySearch() throws SQLException{
        String search = invSearch.getText();
        try {
            ObservableList<model.Inventory> invData = FXCollections.observableArrayList(InventoryDao.search(search));
            inventoryTable.setItems(invData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Edits an existing inventory entry based on the selected field (Name, Quantity, or Unit).
     * Updates the database and refreshes the inventory table.
     *
     * @throws SQLException if a database access error occurs during the update.
     */
    @FXML
    private void editEntry() throws SQLException {
        int id = Integer.parseInt(editItem.getText());
        String edit = invEntry.getText();
        String sel = invFieldSelect.getSelectionModel().getSelectedItem();
        if (sel.equals("Name")) {
            InventoryDao.updateName(id, edit);
        }
        else if (sel.equals("Quantity")) {
            InventoryDao.updateQuantity(id, Integer.parseInt(edit));
        }
        else if (sel.equals("Unit")) {
            InventoryDao.updateUnit(id, edit);
        }
        refreshInv();
    }
    //INVENTORY ENDS HERE

    //SIZE STARTS HERE

    /**
     * Adds a new size record with default placeholder values and refreshes the size table.
     *
     * @throws SQLException if a database access error occurs during insertion.
     */
    @FXML
    private void addSize() throws SQLException {
        MenuPriceDao.insert("EMPTY", 0);
        refreshSize();
    }


    /**
     * Edits a size entry by updating either its name or price, depending on user selection.
     * Refreshes the size table afterward.
     *
     * @throws SQLException if a database access error occurs during the update.
     */
    @FXML
    private void editSizeEntry() throws SQLException {
        String name = editSize.getText();
        String edit = sizeEntry.getText();
        String sel = sizeFieldSelect.getSelectionModel().getSelectedItem();
        if (sel.equals("Size")) {
            MenuPriceDao.updateName(name, edit);
        }
        else if (sel.equals("Price")) {
            MenuPriceDao.updatePrice(name, Integer.parseInt(edit));
        }
        refreshSize();
    }
    //SIZE ENDS HERE

    //EMPLOYEES STARTS HERE

    /**
     * Adds a new employee with default values and refreshes the employee table.
     *
     * @throws SQLException if a database access error occurs during insertion.
     */
    @FXML
    private void addEmployee() throws SQLException {
        EmployeeDao.insert("EMPTY", true, false, 0.0, 0.0, "NONE");
        refreshEmployee();
    }


    /**
     * Searches employees based on user input and updates the employee table with the results.
     *
     * @throws SQLException if a database access error occurs during the search.
     */
    @FXML
    private void employeeSearch() throws SQLException{
        String search = empSearch.getText();
        try {
            ObservableList<model.Employee> empData = FXCollections.observableArrayList(EmployeeDao.search(search));
            employeeTable.setItems(empData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Edits an employee’s record based on the selected field.
     * Supports updates for name, salary, hours worked, position, and employment status.
     *
     * @throws SQLException if a database access error occurs during the update.
     */
    @FXML
    private void editEmployee() throws SQLException {
        int id = Integer.parseInt(empIDSelect.getText());
        String edit = empInfo.getText();
        String sel = empFieldSelect.getSelectionModel().getSelectedItem();
        if (sel.equals("Name")) {
            EmployeeDao.updateName(id, edit);
        }
        else if (sel.equals("Salary")) {
            EmployeeDao.updateSalary(id, Double.parseDouble(edit));
        }
        else if (sel.equals("Hours Worked")) {
            EmployeeDao.updateHoursWorked(id, Double.parseDouble(edit));
        }
        else if (sel.equals("Position")) {
            EmployeeDao.updatePosition(id, edit);
            if (edit.equals("Manager")) {
                EmployeeDao.updatePromote(id, true);
            }
            else  {
                EmployeeDao.updatePromote(id, false);
            }
        }
        else if (sel.equals("Employment Status")) {
            EmployeeDao.updateStatus(id, Boolean.parseBoolean(edit));
        }

        refreshEmployee();
    }


    /**
     * Resets all employees’ hours worked to 0 at the start of a new pay period.
     *
     * @throws SQLException if a database access error occurs during the update.
     */
    @FXML
    private void newPeriod() throws SQLException {
        List<Employee> employees = EmployeeDao.findAll();
        for (Employee employee : employees) {
            EmployeeDao.updateHoursWorked(employee.getEmployee_id(), 0);
        }
        refreshEmployee();
    }
    //EMPLOYEES ENDS HERE

    //SCHEDULE STARTS HERE

    /**
     * Populates the calendar view for a given month and year.
     * Generates day buttons dynamically and binds them to shift display actions.
     *
     * @param yearMonth the month and year to display in the calendar
     */
    @FXML
    private void populateCalendar(YearMonth yearMonth) {
        calendar.getChildren().clear();
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int startOfWeek = firstOfMonth.getDayOfWeek().getValue();

        month.setText(yearMonth.getMonth().toString());

        int day = 1;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                int cell = r * 7 + c + 1;
                if (cell >= startOfWeek && day <= daysInMonth) {
                    LocalDate date = yearMonth.atDay(day);
                    Button dayButton = new Button(day + "");
                    dayButton.setPrefSize(43, 60);
//                    dayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                    dayButton.setOnAction(e -> showShifts(date));
                    calendar.add(dayButton, c, r);
                    day++;
                }
            }
        }
    }


    /**
     * Displays scheduled shifts for the given date in the current shift list view.
     *
     * @param date date the selected date to display shifts for
     */
    @FXML
    private void showShifts(LocalDate date) {
        try{
            List<Schedule> schedules = ScheduleDao.findAll();
            currShifts.getItems().clear();
            for (Schedule s : schedules) {
                if (s.getDate().toLocalDate().equals(date))
                    currShifts.getItems().add("ID: " + s.getEmployeeID() + "\n| " + s.getShift() + "\n| " + s.getPosition());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Displays the previous month in the calendar view.
     */
    @FXML
    private void goPrevMonth() {
        currYearMonth = currYearMonth.minusMonths(1);
        populateCalendar(currYearMonth);
    }


    /**
     * Displays the next month in the calendar view.
     */
    @FXML
    private void goNextMonth() {
        currYearMonth = currYearMonth.plusMonths(1);
        populateCalendar(currYearMonth);
    }


    /**
     * Adds a shift to the schedule for a specified employee and date.
     * Validates inputs and ensures employee existence before insertion.
     *
     * @throws SQLException if a database access error occurs during insertion.
     */
    @FXML
    private void addShift() throws SQLException {
        if (date.getText().isEmpty() || hourEntry.getText().isEmpty() || schedIdEntry.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields.");
            alert.showAndWait();
            return;
        }
        try{
            Date sqlDate = Date.valueOf(date.getText());
            String shift = hourEntry.getText();
            int employeeID = Integer.parseInt(schedIdEntry.getText());

            if (!EmployeeDao.employeeExists(employeeID)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Employee ID does not exist.");
                alert.showAndWait();
                return;
            }

            Employee emp = EmployeeDao.find(employeeID);

            ScheduleDao.insert(sqlDate, shift, emp.getPosition(), employeeID);
            populateCalendar(currYearMonth);
            showShifts(sqlDate.toLocalDate());
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill fields with appropriate values.");
            alert.showAndWait();
        }
    }


    /**
     * Deletes a scheduled shift based on the given employee ID and date.
     * Validates inputs and employee existence before deletion.
     *
     * @throws SQLException if a database access error occurs during deletion.
     */
    @FXML
    private void deleteShift() throws SQLException {
        try {
            int id = Integer.parseInt(schedDelID.getText());
            Date sqlDate = Date.valueOf(schedDelDate.getText());
            if (!EmployeeDao.employeeExists(id)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Employee ID does not exist.");
                alert.showAndWait();
                return;
            }
            ScheduleDao.deleteShift(sqlDate, id);
            populateCalendar(currYearMonth);
            showShifts(sqlDate.toLocalDate());
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill fields with appropriate values.");
            alert.showAndWait();
        }
    }
    //SCHEDULE ENDS HERE

    //RECIPES STARTS HERE

    /**
     * Searches recipes by menu item ID and loads the results into the recipe table.
     *
     * @throws SQLException if a database access error occurs during retrieval.
     */
    @FXML
    private void recipeSearch() throws SQLException {
        int menuID = Integer.parseInt(recSearch.getText());
        ObservableList<Recipes> r = FXCollections.observableArrayList(RecipesDao.findByMenuItemId(menuID));
    }


    /**
     * Adds a new recipe with placeholder values and refreshes the recipe table.
     *
     * @throws SQLException if a database access error occurs during insertion.
     */
    @FXML
    private void addRecipe() throws SQLException {
        RecipesDao.insert(1, 1, 0.0);
        refreshRecipe();
    }


    /**
     * Edits a recipe entry based on user selection (Menu Item ID, Inventory ID, or Quantity Required).
     * Updates the database and refreshes the recipe table.
     *
     * @throws SQLException SQLException if a database access error occurs during update.
     */
    @FXML
    private void editRecipe() throws SQLException {
        String sel = recipeFieldSelect.getSelectionModel().getSelectedItem();
        int recipeID = Integer.parseInt(editRecipe.getText());
        String edit = recipeEntry.getText();
        if (sel.equals("Menu Item ID")) {
            RecipesDao.UpdateMenuID(recipeID, Integer.parseInt(edit));
        }
        else if (sel.equals("Inventory ID")) {
            RecipesDao.UpdateInvID(recipeID, Integer.parseInt(edit));
        }
        else if (sel.equals("Quantity Required")) {
            RecipesDao.UpdateQuantityRequired(recipeID, Double.parseDouble(edit));
        }
        refreshRecipe();
    }
    //RECIPES ENDS HERE

    //MENU ITEMS STARTS HERE

    /**
     * Creates a new menu item with default placeholder values and refreshes the menu table.
     *
     * @throws SQLException if a database access error occurs during insertion.
     */
    @FXML
    private void newMenuItem() throws SQLException {
        MenuItemDao.insert("EMPTY", "Entree");
        refreshMenu();
    }
    

    /**
     * Edits an existing menu item’s name or category based on user selection.
     * Updates the database and refreshes the menu table.
     *
     * @throws SQLException SQLException if a database access error occurs during update.
     */
    @FXML
    private void editMenuItem() throws SQLException {
        String sel = menuFieldSelect.getSelectionModel().getSelectedItem();
        int menuID = Integer.parseInt(editMenu.getText());
        String edit = menuItemEntry.getText();
        if (sel.equals("Menu Item Name")) {
            MenuItemDao.updateName(menuID, edit);
        }
        else if (sel.equals("Category")) {
            MenuItemDao.updateCategory(menuID, edit);
        }
        refreshMenu();
    }


    /**
     * Searches menu items by name or keyword and updates the table with the results.
     */
    @FXML
    private void menuSearch(){
        String search = menuItemSearch.getText();
        try {
            ObservableList<model.MenuItem> menuData = FXCollections.observableArrayList(MenuItemDao.search(search));
            menuItemTable.setItems(menuData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Simulates producing a batch of a selected menu item.
     * Verifies that sufficient inventory exists for each ingredient before decrementing quantities.
     * Displays alerts for success or insufficient inventory.
     */
    @FXML
    private void menuDropBatch(){
        String batch = menuItemSearch.getText();
        
        try {
            batch = MenuItemDao.search(batch).get(0).getMenuitem_name();
            Map<String, Double> result = RecipesDao.joinRecipesByMenuItemID(batch);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Batch Recipe Details");
            alert.setHeaderText("Ingredients and Quantities for: " + batch);
            StringBuilder content = new StringBuilder();

            for (Map.Entry<String, Double> entry : result.entrySet()) {
                content.append(entry.getKey()).append(", Quantity Required: ").append(entry.getValue()).append("\n");
            }
            alert.setContentText(content.toString());
            Optional<ButtonType> resultAlert = alert.showAndWait();

            if (resultAlert.isPresent() && resultAlert.get() == ButtonType.OK) {
                Map<Integer, Double> invMap = new java.util.HashMap<>();
                for (Map.Entry<String, Double> entry : result.entrySet()) {
                    String itemName = entry.getKey();
                    double quantityRequired = entry.getValue();

                    Inventory inv = InventoryDao.search(itemName).get(0);
                    
                    if (quantityRequired <= inv.getQuantity()) {
                        //if sufficient inventory, prepare to update
                        double newquantity = inv.getQuantity() - quantityRequired;
                        invMap.put(inv.getItemID(), newquantity);
                    } else {
                        //if insufficient inventory, alert and exit
                        Alert insufficientAlert = new Alert(Alert.AlertType.ERROR, "Insufficient inventory for item: " + itemName);
                        insufficientAlert.showAndWait();
                        return;
                    }
                }
                
                //execute inventory updates
                for (Map.Entry<Integer, Double> entry : invMap.entrySet()) {
                    int itemID = entry.getKey();
                    double newQuantity = entry.getValue();
                    InventoryDao.updateQuantity(itemID, newQuantity);
                }
            }

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert (Alert.AlertType.ERROR, "Menu Item not found.");
            alert.showAndWait();
            return;
        } catch (SQLException e) {
            Alert alert = new Alert (Alert.AlertType.ERROR, "Drop Failed.");
            alert.showAndWait();
            return;
        }
       
    }
    //MENU ITEMS ENDS HERE
}
