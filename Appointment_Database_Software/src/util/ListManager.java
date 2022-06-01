package util;

// Holder for Observable Lists and related functions

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.time.LocalTime;

public class ListManager {

    // Observable Lists for the Tables in Main Menu
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    // Observable Lists for the Customer Combo Boxes
    public static ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FXCollections.observableArrayList();
    public static ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    // Observable Lists for the Appointment Combo Boxes
    public static ObservableList<Users> allUsers = FXCollections.observableArrayList();
    public static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
    public static ObservableList<LocalTime> allTimes = FXCollections.observableArrayList();

    // CRUD Operations to update the Observable Lists after a SQL task

    /**
     * Updates Customer information in the ObservableList allCustomers
     * @param index the position of the selected Customer chosen in the Customer TableView
     * @param newCustomer the updated Customer data from the Update Customer Menu
     */
    public static void updateCustomer(int index, Customer newCustomer){
        allCustomers.set(index, newCustomer);
    }

    /**
     * Updates Appointment Information in the ObservableList allAppointments
     * @param index the position of the selected Appointment chosen in the Appointmnent TableView
     * @param newAppointment the updated Appointment data from the Update Appointment menu
     */
    public static void updateAppointment(int index, Appointment newAppointment){
        allAppointments.set(index, newAppointment);
    }

    /**
     * Deletes a customer from the ObservableList allCustomers
      * @param selectedCustomer the position of the selected Customer chosen in the Customer TableView
     * @return cancels deletion if nothing is selected
     */
    public static boolean deleteCustomer(Customer selectedCustomer){
        if(selectedCustomer != null)
        {
            return ListManager.getAllCustomers().remove(selectedCustomer);
        }
        return false;
    }

    /**
     * Deletes an appointment from the ObservableList allAppointments
     * @param selectedAppointment the position of the selected Appointment chosen in the Appointment TableView
     * @return cancels deletion if nothing is selected
     */
    public static boolean deleteAppointment(Appointment selectedAppointment){
        if(selectedAppointment != null)
        {
            return ListManager.getAllAppointments().remove(selectedAppointment);
        }
        return false;
    }

    /**
     * Retrieves all objects in the ObservableList allCustomers
     * @return the Customers in the ObservableList
     */
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    /**
     * Retrieves all objects in the ObservableList allAppointments
     * @return the Appointments in the ObservableList
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    // ComboBox Getters

    /**
     * Retrieves all objects in the ObservableList allFirstLevelDivisions for the FLD ComboBox
     * @return the First Level Division data in the Observable List
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        return allFirstLevelDivisions;
    }

    /**
     * Retrieves all objects in the ObservableList allCountries for the Country ComboBox
     * @return the Country data in the Observable List
     */
    public static ObservableList<Countries> getAllCountries() {
        return allCountries;
    }

    /**
     * Retrieves all objects in the ObservableList allUsers for the Users ComboBox
     * @return the User data in the Observable List
     */
    public static ObservableList<Users> getAllUsers() {
        return allUsers;
    }

    /**
     * Retrieves all objects in the ObservableList allContacts for the Contacts ComboBox
     * @return the Contacts data in the Observable List
     */
    public static ObservableList<Contacts> getAllContacts(){
        return allContacts;
    }

    /**
     * Retrieves all LocalTime objects for the converted Business Hours ComboBox
     * @return the converted times from EST
     */
    public static ObservableList<LocalTime> getAllTimes() {
        return allTimes;
    }
}
