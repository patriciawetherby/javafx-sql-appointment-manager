package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import util.AppointmentQuery;
import util.ListManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This is the Appointments page controller that handles adding and updating
 */

public class CreateUpdateAppointment implements Initializable {

    Stage stage;

    private boolean localUpdatedSelected = false; // Local updatedSelected, not my best implementation but it works!
    private Appointment appointment; // For sendAppointment function

    /**
     * onAction: Return to Tables / Main Menu without saving Appointment
     * @param event Return to Main Menu
     */
    @FXML
    void onActionGoToTables(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Tables.fxml"));
        loader.load();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); //Switches to Tables
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * onAction: Gets Appointment Data and adds/updates appointment to database and table
     * @param event Saves appointment data
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException, SQLException {

        try {

            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            Contacts contact = contactComboBox.getValue();
            String type = typeTxt.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalTime startTime = startTimeComboBox.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            LocalDateTime appointmentStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(endDate, endTime);
            // Zone is changed in backend with Combo Boxes
//            ZoneId zoneId = ZoneId.systemDefault();
//            ZonedDateTime appointmentStart = ZonedDateTime.of(startDate, startTime, zoneId);
//            ZonedDateTime appointmentEnd = ZonedDateTime.of(endDate, endTime, zoneId);
//            //EST
//            // New Zone withZone, sameInstant
//            ZoneId est = ZoneId.of("America/New_York");
//            ZonedDateTime appointmentStartZone = appointmentStart.withZoneSameInstant(est); //eastern zone id/NewYork
//            ZonedDateTime appointmentEndZone = appointmentEnd.withZoneSameInstant(est);
            Customer customerData = customerIDComboBox.getValue();
            Users userData = userIDComboBox.getValue();
            int customerID = customerData.getCustomerID();
            int userID = userData.getUserID();
            String contactName = contact.getContactName(); // Contact Name is not asked for
            int contactID = contact.getContactID(); // Contact ID is not asked for

            // Catch invalid time inputs

            if(appointmentStart.isAfter(appointmentEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please enter a valid date and time");
                alert.setHeaderText("Error: Date/Time Invalid");
                alert.showAndWait();
                return;
            }

            // Check for Overlapping Appointments - Exclude self
            for(Appointment appt : ListManager.allAppointments) {
                if (appointmentStart.isAfter(appt.getAppointmentStart()) && appointmentStart.isBefore(appt.getAppointmentEnd()) && appt.getAppointmentID() != appointment.getAppointmentID()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please choose a different time");
                    alert.setHeaderText("This appointment overlaps with an existing appointment");
                    alert.showAndWait();
                    return;
                } else if ((appointmentStart.equals(appt.getAppointmentStart()) || appointmentEnd.equals(appt.getAppointmentEnd())) && appt.getAppointmentID() != appointment.getAppointmentID()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Please choose a different time");
                    alert.setHeaderText("This appointment is during the same time as another appointment");
                    alert.showAndWait();
                    return;
                }
            }
            // Case: Doing an update
            if (localUpdatedSelected) {
                int appointmentID = appointment.getAppointmentID();
                AppointmentQuery.update(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID);
                int index = ListManager.getAllAppointments().indexOf(appointment);
                Appointment a = new Appointment(appointmentID, title, description, location, type,
                        appointmentStart, appointmentEnd, customerID, userID, contactID, contactName);
                ListManager.updateAppointment(index, a);
            }
            //Normal Add
            else {
                AppointmentQuery.insert(title, description, location, type, Timestamp.valueOf(appointmentStart), Timestamp.valueOf(appointmentEnd), customerID, userID, contactID);
                // Query Appointment ID that was autogenerated so it can be added to the allAppointments
                int appointmentID = AppointmentQuery.queryAppointmentID(customerID, appointmentStart);
                // Update Appointment Tableview
                ListManager.allAppointments.add(new Appointment(appointmentID, title, description, location, type,
                        appointmentStart, appointmentEnd, customerID, userID, contactID, contactName));
            }


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Tables.fxml"));
            loader.load();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot(); //Switches to Tables
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING); //Alert dialog box
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please do not leave entries blank");
            alert.showAndWait();
        }
        }

    /**
     * Retrieves AppointmentTable data and populates Add/Update screen
     * @param a Takes data from appointments tableview to appointments form
     * @throws SQLException SQL Exception
     */
    public void sendAppointment(Appointment a) throws SQLException {

        this.appointment = a;

        appointmentIDTxt.setText(String.valueOf(a.getAppointmentID()));
        titleTxt.setText(a.getTitle());
        descriptionTxt.setText(a.getDescription());
        locationTxt.setText(a.getLocation());
        Contacts sentContact = new Contacts(a.getContactID(), a.getContactName());
        contactComboBox.setValue(sentContact);
        typeTxt.setText(a.getType());
        startDatePicker.setValue(a.getAppointmentStart().toLocalDate());
        endDatePicker.setValue(a.getAppointmentEnd().toLocalDate());
        startTimeComboBox.setValue(a.getAppointmentStart().toLocalTime());
        endTimeComboBox.setValue(a.getAppointmentEnd().toLocalTime());
        Customer sentCustomer = AppointmentQuery.getCustomerData(a.getCustomerID());
        customerIDComboBox.setValue(sentCustomer);
        Users sentUser = AppointmentQuery.getUserData(a.getUserID());
        userIDComboBox.setValue(sentUser);

        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm)
        // LDT -> String String s = dtf.format
        // String -> LDT LocalDateTime.parse(s,dtf)

    }

    /**
     * Changes the Title from New Appointment to Update Appointment
     * Switches to Update header based on the updateSelected value
     */
    public void changeAppointmentHeader(){

        if (Tables.isUpdateSelected(true)) {
            localUpdatedSelected = true;
            appointmentTitleLbl.setText("Update Appointment");
            // Turn update back off
            Tables.setUpdateSelected(false);
        }
    }

    /**
     * Converts System Time to EST time options in ComboBox
     */
    public void populateTimeCB() {

        // Take current time on system
        LocalDate estDate = LocalDate.now();
        // Beginning of Business Hours ( 8:00am - 10:00pm EST)
        LocalTime estStartTime = LocalTime.of(8, 0);
        ZoneId sysZoneId = ZoneId.systemDefault();
        ZoneId estZoneId = ZoneId.of("America/New_York");
        timeZoneDetectedLbl.setText(String.valueOf(sysZoneId)); // Change Label
        // Create ZonedDateTime object with start dates and times for EST
        ZonedDateTime estZDT = ZonedDateTime.of(estDate, estStartTime, estZoneId);
        // Get ZoneDateTime converted from Local to EST
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(estZDT.toInstant(), ZoneId.systemDefault());
        // Add corresponding times to combo boxes!
        ListManager.allTimes.add(localZDT.toLocalTime());
        // Goes through all the Local Times (Converted) and populates Combo Boxes
        LocalTime lt = localZDT.toLocalTime();
        for (int i = 0; i < 14; i++) {
            lt = lt.plusHours(1);
            ListManager.allTimes.add(lt);
        }
    }

        // FXML Labels
        @FXML
        private Button addAppointmentBtn;

        @FXML
        private Button backToTablesBtn;

        @FXML
        private Label appointmentTitleLbl;

        @FXML
        private TextField appointmentIDTxt;

        @FXML
        private TextField titleTxt;

        @FXML
        private TextArea descriptionTxt;

        @FXML
        private TextField locationTxt;

        @FXML
        private ComboBox<Contacts> contactComboBox;

        @FXML
        private TextField typeTxt;

        @FXML
        private DatePicker startDatePicker;

        @FXML
        private DatePicker endDatePicker;

        @FXML
        private Label timeZoneDetectedLbl;

        @FXML
        private ComboBox<LocalTime> endTimeComboBox;

        @FXML
        private ComboBox<LocalTime> startTimeComboBox;

        @FXML
        private ComboBox<Users> userIDComboBox;

        @FXML
        private ComboBox<Customer> customerIDComboBox;

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        changeAppointmentHeader(); // Switch Appointment Header depending on isUpdatedSelected value
        populateTimeCB();;

        // Populate fields with data + text prompts
        contactComboBox.setItems(ListManager.getAllContacts());
        contactComboBox.setPromptText("Choose a Contact");
        startTimeComboBox.setItems(ListManager.getAllTimes());
        startTimeComboBox.setPromptText("Choose a Start Time");
        endTimeComboBox.setItems(ListManager.getAllTimes());
        endTimeComboBox.setPromptText("Choose an End Time");
        contactComboBox.setItems(ListManager.getAllContacts());
        contactComboBox.setPromptText("Choose a Contact");
        userIDComboBox.setItems(ListManager.getAllUsers());
        userIDComboBox.setPromptText("Choose a User");
        customerIDComboBox.setItems(ListManager.getAllCustomers());
        customerIDComboBox.setPromptText("Choose a Customer");

    }
}

