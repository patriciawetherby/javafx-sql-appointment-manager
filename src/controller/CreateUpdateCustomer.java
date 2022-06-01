package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;
import util.CustomerQuery;
import util.ListManager;

/**
 * This is the Customers page controller that handles adding and updating
 */


public class CreateUpdateCustomer implements Initializable {

    Stage stage;
    private Customer customer; // For sendCustomer function
    private boolean localUpdatedSelected = false; // Local updatedSelected, not my best implementation but it works!

    /**
     * onAction: Goes back to Tables/MainMenu without saving Customer
     * @param event Returns to Main Menu
     */
    @FXML
    void onActionGoToTables(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Tables.fxml"));
        loader.load();

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Tables
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Change FLD ComboBox to match information in Country ComboBox
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionChangeComboBox(ActionEvent event) throws SQLException {

        ListManager.allFirstLevelDivisions.clear();
        CustomerQuery.selectFLD(countryComboBox.getValue().getCountryID());
        FLDComboBox.setItems(ListManager.getAllFirstLevelDivisions());

    }

    /**
     * onAction: Adds/Updates Customer based on updatedSelected value
     * @param event Saves customer data
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException, SQLException {

        try {

            String customerName = customerNameTxt.getText();
            String address = addressTxt.getText();
            Countries countryData = countryComboBox.getValue();
            FirstLevelDivisions fldData = FLDComboBox.getValue();
            String postalCode = postalCodeTxt.getText();
            String phoneNumber = phoneNumTxt.getText();
            // Split up country and division objects from Combo Box
            int divisionID = CustomerQuery.queryDivisionID(String.valueOf(fldData.getDivision()));
            int countryID = CustomerQuery.queryCountryID(String.valueOf(countryData.getCountry()));
            String divisionName = fldData.getDivision();
            String countryName = countryData.getCountry();

            //Case: Doing an update
            if (localUpdatedSelected) {
                System.out.println("A customer was updated");
                int customerID = customer.getCustomerID();
                CustomerQuery.update(customerName, address, postalCode, phoneNumber, divisionID, customerID);
                int index = ListManager.getAllCustomers().indexOf(customer);
                Customer c = new Customer(customerID, divisionID, countryID, customerName, address, postalCode, phoneNumber, divisionName, countryName);
                ListManager.updateCustomer(index, c);

            } else {
                CustomerQuery.insert(customerName, address, postalCode, phoneNumber, divisionID);
                int customerID = CustomerQuery.queryCustomerID(customerName, phoneNumber);

                // Update Customer Tableview
                ListManager.allCustomers.add(new Customer(customerID, divisionID, countryID, customerName, address, postalCode, phoneNumber, divisionName, countryName));
            }

            // After saving: Move back to the Main Menu: Tables
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Tables.fxml"));
            loader.load();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot(); //Switches to Tables
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING); //Alert dialog box
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each field");
            alert.showAndWait();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING); //Alert dialog box
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please do not leave entries blank");
            alert.showAndWait();
        }
    }

    /**
     * Populates update screen with the Customer values from Customer Records Table
     * @param c Takes data from tableview to appointments form
     */
    public void sendCustomer(Customer c){

        this.customer = c;

        customerIDTxt.setText(String.valueOf(c.getCustomerID()));
        customerNameTxt.setText(c.getCustomerName());
        addressTxt.setText(c.getAddress());
        Countries sentCountry = new Countries(c.getCountryID(), c.getCountry());
        FirstLevelDivisions sentFLD = new FirstLevelDivisions(c.getDivisionID(), c.getDivision());
        countryComboBox.setValue(sentCountry);
        FLDComboBox.setValue(sentFLD);
        postalCodeTxt.setText(c.getPostalCode());
        phoneNumTxt.setText(c.getPhone());

    }

    /**
     * Changes the Title from New Customer to Update Customer.
     * Switches to Update header based on the updateSelected value
     */
    public void changeCustomerHeader(){

        if(Tables.isUpdateSelected(true)){
            localUpdatedSelected = true;
            customerTitleLbl.setText("Update Customer");
            // Turn update back off
            Tables.setUpdateSelected(false);
        }
    }

    // FXML Labels
    @FXML
    private ComboBox<FirstLevelDivisions> FLDComboBox;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button backToTablesBtn;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField phoneNumTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private Label customerTitleLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        changeCustomerHeader(); // Switches the Update Header based on the updateSelected value

        // Set Combo Box Values
        FLDComboBox.setItems(ListManager.getAllFirstLevelDivisions());
        System.out.println(ListManager.getAllFirstLevelDivisions());
        FLDComboBox.setVisibleRowCount(5);
        FLDComboBox.setPromptText("Choose a Division");
        countryComboBox.setItems(ListManager.getAllCountries());
        countryComboBox.setPromptText("Choose a Country");

    }

}

