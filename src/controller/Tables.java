package controller;

// Controller for the javafx file Tables

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;
import model.Customer;
import util.AppointmentQuery;
import util.CustomerQuery;
import util.ListManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the Tables page controller
 */


public class Tables implements Initializable {

    private Stage stage;

    // updateSelected to Toggle Update/Add functionality
    public static boolean updateSelected = false;
    public static boolean isUpdateSelected(boolean b) {
        return updateSelected;
    }
    public static void setUpdateSelected(boolean updateSelected) {
        Tables.updateSelected = updateSelected;
    }

    private int appointmentCount = 0; // For reports()

    /**
     * onAction: Goes to the Add Customer Menu
     * @param event When Add is pressed under Customer TableView, it goes to the CreateUpdateCustomer page
     */
    @FXML
    void onActionAddCustomerMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CreateUpdateCustomer.fxml"));
        loader.load();

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Customers
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Goes to the Update Customer Menu
     * @param event When Update is pressed under Customer TableView, it goes to the CreateUpdateCustomer page
     */
    @FXML
    void onActionUpdateCustomerMenu(ActionEvent event) throws IOException, SQLException {

        updateSelected = true; // Toggle Update -> TRUE

        if(customerRecordsTable.getSelectionModel().getSelectedItem() == null)
        {
            return; // No action if nothing is selected
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CreateUpdateCustomer.fxml"));
        loader.load();
        // Send Records Table data to Customer Controller
        CreateUpdateCustomer customerController = loader.getController();
        customerController.sendCustomer(customerRecordsTable.getSelectionModel().getSelectedItem());

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Customers
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Goes to Add Appointment Menu
     * @param event When Add is pressed under Appointment TableView, it goes to the CreateUpdateAppointment page
     */
    @FXML
    void onActionAddAppointmentMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CreateUpdateAppointment.fxml"));
        loader.load();

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Customers
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Goes to AddAppointment Menu
     * @param event When Update is pressed under Appointment Tableview, it goes to the CreateUpdateAppointment page
     */
    @FXML
    void onActionUpdateAppointmentMenu(ActionEvent event) throws IOException, SQLException {

        updateSelected = true;

        if(appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CreateUpdateAppointment.fxml"));
        loader.load();

        // Send Records Table data to Customer Controller
        CreateUpdateAppointment appointmentController = loader.getController();
        appointmentController.sendAppointment(appointmentsTable.getSelectionModel().getSelectedItem());

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Customers
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Deletes Customer from SQL Database Customer Table
     * If a customer has an existing appointment, there will be a warning message
     * @param event When Delete is pressed under Customer TableView, it removes the entry from the database and table
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {

        if(customerRecordsTable.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this Customer. Is that okay?");
        //If user clicks Okay button, they will delete
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            Customer selectedCustomer = (Customer)customerRecordsTable.getSelectionModel().getSelectedItem();

            // Goes through Appointments and searches for active Customer IDs in appointments
            for( Appointment a : ListManager.allAppointments)
            {
                if(selectedCustomer.getCustomerID() == a.getCustomerID()){
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "This customer has at least 1 active appointment scheduled.\n" +
                            "Are you sure you want to delete?");
                    alert2.setHeaderText("WARNING!");
                    Optional<ButtonType> OK = alert2.showAndWait();
                    // If OK is pressed
                    if(OK.isPresent() && OK.get() == ButtonType.OK) {
                        for(Appointment appt : ListManager.getAllAppointments()){
                            if(selectedCustomer.getCustomerID() == appt.getCustomerID()){
                                CustomerQuery.delete(selectedCustomer.getCustomerID()); // Delete from Database
                                ListManager.deleteCustomer(selectedCustomer); // Delete from Tables
                                AppointmentQuery.delete(appt.getAppointmentID());
                                ListManager.deleteAppointment(appt);
                                // Update Appointments
                                appointmentsTable.refresh();
                                return;
                            }
                        }
                    }
                    return;
                }
            }
            CustomerQuery.delete(selectedCustomer.getCustomerID()); // Delete from Database
            ListManager.deleteCustomer(selectedCustomer); // Delete from Tables
        }

    }

    /**
     * onAction: Deletes Appointments from SQL Database and Records Table
     * @param event When Delete is pressed under Appointment TableView, it removes the entry from the database and table
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {

        if(appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel this Appointment. Is that okay?\n" +
                "Appointment ID: " + selectedAppointment.getAppointmentID() + " Type: " + selectedAppointment.getType());
        //If user clicks Okay button, they will delete
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
            AppointmentQuery.delete(selectedAppointment.getAppointmentID());
            ListManager.deleteAppointment(selectedAppointment);
            Alert deleted = new Alert(Alert.AlertType.INFORMATION, "The appointment has been cancelled");
            deleted.setHeaderText("Deleted from Records");
            deleted.showAndWait();
        }
    }

    /**
     * onAction: Default Action - Displays all entries that are in the database in the TableView
     * @param event Displays all appointments
     */
    @FXML
    void sortByAll(ActionEvent event) throws SQLException {
        // Remove all entries currently in the Observable List
        ListManager.getAllAppointments().clear();
        // Repopulate with all appointment data from the database
        AppointmentQuery.select();
    }

    /**
     * onAction: Displays appointments that are in the same month as current date
     * Clears any entries that are currently in the TableView and repopulates with month data
     * @param event Sorts by month
     */
    @FXML
    void sortByMonth(ActionEvent event) throws SQLException {
        // Remove all entries currently in the Observable List
        ListManager.getAllAppointments().clear();
        // Repopulate with only the appointments that are currently in the same month
        AppointmentQuery.filterByMonth();
    }

    /**
     * onAction: Displays appointments that are within 7 days of the current date
     * Clears any entries that are currently in the TableView and repopulates with week data
     * @param event Sorts by week
     */
    @FXML
    void sortByWeek(ActionEvent event) throws SQLException {
        // Remove all entries currently in the Observable List
        ListManager.getAllAppointments().clear();
        // Repopulate with only the appointments that are within a week
        AppointmentQuery.filterByWeek();
    }

    /**
     * onAction: Generate required report details from the Appointment and Customer Information
     * @param event Creates reports
     * @throws SQLException SQL Exception
     */
    @FXML
    void onActionGenerateReports(ActionEvent event) throws SQLException {

        appointmentList.setText(appointmentReports());
        contactScheduleList.setText(contactReports());
        customerContactList.setText(contactInformation());

    }

    /**
     * Goodbye: This closes the application!
     * @param event Exits application
     */
    @FXML
    void onActionCloseApplication(ActionEvent event) {

        System.exit(0);

    }

    /**
     * Generates Alert messages if an appointment is starting 15 min from the current time at login
     * Otherwise, an alert message shows "No Upcoming Appointments"
     */
    public void alert15min() {
        LocalDateTime currentDT = LocalDateTime.now();

        boolean upcoming = false;
        for (Appointment a : ListManager.allAppointments) {
            LocalDateTime startTime = a.getAppointmentStart();
            long elapsedTime = ChronoUnit.MINUTES.between(currentDT, startTime); // Compares difference

            if (elapsedTime > 0 && elapsedTime <= 15) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment: " + a.getAppointmentID() + " , " + a.getTitle() + " " + a.getAppointmentStart());
                alert.setHeaderText("You have an appointment in " + elapsedTime + " minutes");
                alert.showAndWait();
                upcoming = true;
            }
        }
        if (!upcoming) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no upcoming appointments");
            alert.showAndWait();
        }
    }

    /**
     * Generates a report showing the list of appointments
     * @throws SQLException SQL Exception
     * @return appointmentReport Report of all appointments and details
     */
    public String appointmentReports() throws SQLException {


        StringBuilder appointmentReport = new StringBuilder("List of Appointments: \n");
        for(Appointment a : ListManager.allAppointments){
            String type = a.getType();
            Month month = a.getAppointmentStart().getMonth();
            appointmentCount++;
            appointmentReport.append("Appointment Type: ").append(type).append(" | Month: ").append(month).append("\n");
        }
        appointmentReport.append("\nTotal Appointments: ").append(appointmentCount).append("\n");

        // Converts StringBuilder appointmentReport to String
        return appointmentReport.toString();
    }

    /**
     * Generates a report showing the contact's schedules
     * @return String contactReport List of Contact's appointments
     */
    public String contactReports(){

        StringBuilder contactReport = new StringBuilder("Contact Schedules\n");
        for(Contacts con : ListManager.allContacts){
            contactReport.append(con).append("'s Schedule: \n");
            for(Appointment a : ListManager.allAppointments){
                if(a.getContactID() == (con.getContactID())){
                    contactReport.append(a).append("\n");
                }
            }
        }
        return contactReport.toString();
    }

    /**
     * Generates a report showing the customer's contact information
     * @return String contactInformation Customer Name + Phone Number
     */
    public String contactInformation(){

        StringBuilder contactInformation = new StringBuilder("Clients Contact Info: \n");
        for(Customer c : ListManager.allCustomers){
            contactInformation.append("Name: ").append(c.getCustomerName()).append(" Phone: ").append(c.getPhone()).append("\n");
        }
        return contactInformation.toString();
    }

    /**
     * Initializes the controller class.
     *
     * LAMBDA 1:
     * Lambda expression used for SimpleStringProperty to include Division information in Customer Tableview
     * which was required by the PA. This converts the String address with the String division information and
     * combined them into one property to populate in the column
     *         addressCol.setCellValueFactory(c ->newSimpleStringProperty(c.getValue().getAddress() +" , " + c.getValue().getDivision()));
     *
     * LAMBDA 2 & 3:
     * I wanted to avoid using DateTimeFormatter in the essential areas of my code that dealt with
     * adding objects into the ObservableList and/or adding information into the database.
     * I still wanted to have a more readable date/time formatting in the tableview, so I used a lambda
     * function similarly to how I did the address column for Customers to implement the DateTimeFormatter class.
     *  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm a");
     *         appointmentStartCol.setCellValueFactory(start -> new SimpleStringProperty(start.getValue().getAppointmentStart().format(dtf)));
     *         appointmentEndCol.setCellValueFactory(end -> new SimpleStringProperty(end.getValue().getAppointmentEnd().format(dtf)));
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        alert15min();

        // Populates the Records Tables
        customerRecordsTable.setItems(ListManager.getAllCustomers());
        appointmentsTable.setItems(ListManager.getAllAppointments());

        // Set Values for Customer Tableview Columns
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        // TODO: Here are the lambda expressions
        // TODO: Combined Address + Division as a string in the addressCol LAMBDA
        /*
          LAMBDA 1:
          Lambda expression used for SimpleStringProperty to include Division information in Customer Tableview
          which was required by the PA. This converts the String address with the String division information and
          combined them into one property to populate in the column
         */
        addressCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress() + ", " + c.getValue().getDivision()));

        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Set Values for Appointment Tableview Columns
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        // TODO: Formatted Appointment Start and End Columns LAMBDA
        /*
         * LAMBDA 2 & 3
         * I wanted to avoid using DateTimeFormatter in the essential areas of my code that dealt with
         * adding objects into the ObservableList and/or adding information into the database.
         * I still wanted to have a more readable date/time formatting in the tableview, so I used a lambda
         * function similarly to how I did the address column for Customers to implement the DateTimeFormatter class.
         */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm a");
        appointmentStartCol.setCellValueFactory(start -> new SimpleStringProperty(start.getValue().getAppointmentStart().format(dtf)));
        appointmentEndCol.setCellValueFactory(end -> new SimpleStringProperty(end.getValue().getAppointmentEnd().format(dtf)));
        customerIDCol2.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

    }

    // FXML Labels
    @FXML
    private Tab appointmentsTab;

    @FXML
    private Tab customerTab;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private RadioButton sortByAllRBtn;

    @FXML
    private RadioButton sortByMonthRBtn;

    @FXML
    private RadioButton sortByWeekRBtn;

    @FXML
    private ToggleGroup sortByTG;

    @FXML
    private Button closeApplicationBtn;

    @FXML
    private TableColumn<?, ?> customerIDCol;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> phoneNumberCol;

    @FXML
    private TableColumn<?, ?> postalCodeCol;

    @FXML
    private TableColumn<?, ?> appointmentIDCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> customerIDCol2;

    @FXML
    private TableColumn<?, ?> userIDCol;

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableView<Customer> customerRecordsTable;

    @FXML
    private Tab reportsTab;

    @FXML
    private TextArea appointmentList;

    @FXML
    private TextArea contactScheduleList;

    @FXML
    private TextArea customerContactList;

    @FXML
    private Button generateReportsBtn;




}

