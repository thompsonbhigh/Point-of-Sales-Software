/**
 * CSCE 331 — Team 42
 * Project: Panda Express POS System (JavaFX + PostgreSQL)
 *
 * Description: Controls the main navigation and home screen for the Panda Express POS system.
 *
 * Author: Team 42
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Employee;
import model.OrderSession;

import java.io.IOException;
import dao.OrdersDao;
import dao.EmployeeDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.Timestamp;

public class MainScreenController {

    /**
     * Loads MealScreen JavaFX file 
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs
     */
    @FXML
    private void navigateToMealScreen(ActionEvent event) throws IOException {
        Parent mealScreenRoot = FXMLLoader.load(getClass().getResource("/view/Meal_Screen_Start.fxml"));
        Scene mealScreenScene = new Scene(mealScreenRoot);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mealScreenScene);
        window.show();
    }


    /**
     * Alerts the user when an unfinished button has been pressed 
     *
     * @param event User input from button press on JavaFX file
     */
    @FXML
    private void handleOtherButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Function Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText("This button's functionality has not been implemented yet.");
        alert.showAndWait();
    }

    /**
     * Handles navigating to the Manager JavaFX file by validating whether EmployeeID 
     * is a Manager or not
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs with identifying worker
     */
    @FXML
    private void navigateToManagerView(ActionEvent event) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Manager Sign On");
        dialog.setHeaderText("Enter your Employee ID");
        dialog.setContentText("Employee ID:");
        dialog.showAndWait().ifPresent(input -> {
            try {
                int empId = Integer.parseInt(input);
                Employee emp = EmployeeDao.find(empId);
                boolean isManager = emp.getIsManager();
                if (isManager) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sign On Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Manager ID verified.");
                    alert.showAndWait();
                    Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("/view/manager view.fxml"));
                    Scene mainScreenScene = new Scene(mainScreenRoot);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(mainScreenScene);
                    window.show();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Not a Manager");
                    error.setHeaderText(null);
                    error.setContentText("ID " + empId + "not associated with a manager.");
                    error.showAndWait();
                    employeeSignOn(event);
                }
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Invalid Input");
                error.setHeaderText(null);
                error.setContentText("Please enter a valid numeric Employee ID.");
                error.showAndWait();
                employeeSignOn(event);
            }

            catch (Exception e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Database Error");
                error.setHeaderText("Could not validate employee");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        });
    }

    /**
     * Handles starting a new order by attaching the Date and EmployeeID to the order  
     * and storing it in the database 
     *
     * @param event User input from button press on JavaFX file
     * @throws IOException if an error occurs with starting the order 
     */
    @FXML
    private void startOrder(ActionEvent event) throws IOException {
        OrdersDao ordersDao = new OrdersDao();
        LocalDateTime currDateTime = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currTimeStamp = currDateTime.format(myFormat);
        Timestamp ts = Timestamp.valueOf(currTimeStamp);
        try {
            int empId = OrderSession.getInstance().getEmployeeId();
            int orderStarted = ordersDao.insert(empId, ts);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Started");
            alert.setHeaderText(null);
            if (orderStarted > 0) {
                alert.setContentText("Order successfully started.");
                navigateToMealScreen(event);
            } else {
                alert.setContentText("Order was not started.");
            }
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Failed to start order");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }

    /**
     * Validates EmployeeID when signing on through connection with the database
     *
     * @param event User input from button press on JavaFX file
     */
    @FXML
    private void employeeSignOn(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Employee Sign On");
        dialog.setHeaderText("Enter your Employee ID");
        dialog.setContentText("Employee ID:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                int empId = Integer.parseInt(input);

                boolean exists = EmployeeDao.employeeExists(empId);
                if (exists) {
                    OrderSession.getInstance().setEmployeeId(empId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sign On Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID " + empId + " verified and stored.");
                    alert.showAndWait();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Invalid Employee");
                    error.setHeaderText(null);
                    error.setContentText("No employee found with ID " + empId + ".");
                    error.showAndWait();
                    employeeSignOn(event);
                }
            } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Invalid Input");
            error.setHeaderText(null);
            error.setContentText("Please enter a valid numeric Employee ID.");
            error.showAndWait();
            employeeSignOn(event);
        }
            catch (Exception e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Database Error");
                error.setHeaderText("Could not validate employee");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        });
    }

    /**
     * Handles clocking in and clocking out for employees, and logging hours worked to the database
     *
     * @throws SQLException if an error occurs with clocking in/ clocking out
     */
    @FXML
    private void clockInOut() throws SQLException {
        OrderSession os = OrderSession.getInstance();
        LocalDateTime currDateTime = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currTimeStamp = currDateTime.format(myFormat);
        Timestamp ts = Timestamp.valueOf(currTimeStamp);
        if (os.getEmployeeId() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Missing Employee");
            error.setHeaderText(null);
            error.setContentText("No Employee signed in.");
            error.showAndWait();
        }
        else {
            if (os.isInShift()) {
                os.endShift(ts);
                long shiftMS = os.getEndTime().getTime() - os.getStartTime().getTime();
                double hrs = shiftMS / (60 * 60 * 1000.0);
                Employee emp = EmployeeDao.find(os.getEmployeeId());
                hrs += emp.getHours_worked();
                EmployeeDao.updateHoursWorked(os.getEmployeeId(), hrs);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Shift End");
                alert.setHeaderText(null);
                alert.setContentText("Shift ended successfully.");
                alert.showAndWait();
            } else {
                os.startShift(ts);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Shift Start");
                alert.setHeaderText(null);
                alert.setContentText("Shift started successfully.");
                alert.showAndWait();
            }
        }
    }
}
