package util;

import model.Appointment;
import model.Contacts;
import model.Customer;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class handles the SQL queries select/insert/update/delete for Appointment Records
 * It also contains additional methods to query foreign key data in customers, contacts, and users
 * Sort By methods are used to repopulate the Appointments TableView by month/week from current date
 */
public class AppointmentQuery {

    /**
     * Selects all entries in client_schedule.appointments and populates allAppointments ObservableList
     * @throws SQLException SQL Exception
     */
    public static void select() throws SQLException {

        String mySql = "SELECT * FROM appointments a JOIN users u ON a.User_ID = u.User_ID JOIN contacts c ON a.Contact_ID = c.Contact_ID";
        PreparedStatement ps = DBConnection.connection.prepareStatement(mySql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contactName = queryContactName(contactID);
            LocalDateTime appointmentStart = start.toLocalDateTime();
            LocalDateTime appointmentEnd = end.toLocalDateTime();
            ListManager.allAppointments.add(new Appointment(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName));
        }
    }

    /**
     * Selects all entries in client_schedule.contacts and populates Contact ComboBox
     * @throws SQLException SQL Exception
     */
    public static void selectContacts() throws SQLException {

        String sql = "SELECT * FROM Contacts";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            //String email = rs.getString("Email");
            ListManager.allContacts.add(new Contacts(contactID, contactName));
        }
    }

    /**
     * Selects all entries in client_schedule.users and populates User ComboBox
     * @throws SQLException SQL Exception
     */
    public static void selectUsers() throws SQLException{

        String sql = "SELECT * FROM Users";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            ListManager.allUsers.add(new Users(userID, username, password));
        }

    }

    // Use to Populate Combo Boxes

    /**
     * Selects all entries in client_schedule.customers to populate the Customer ID ComboBox with Customer IDs
     * @param customerID Customer ID - ComboBox also contains Customer
     * @return Customer objects to populate ComboBox
     * @throws SQLException SQL Exception
     */
    public static Customer getCustomerData(int customerID) throws SQLException {

        String sql = "SELECT * FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.country_ID = countries.country_ID AND Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        // variables for scope reasons
        int divisionID = 0;
        int countryID = 0;
        String customerName = "";
        String address = "";
        String postalCode = "";
        String phone = "";
        String division = "";
        String country = "";
        while(rs.next()) {
            divisionID = rs.getInt("Division_ID");
            countryID = rs.getInt("Country_ID");
            customerName = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postalCode = rs.getString("Postal_Code");
            phone = rs.getString("Phone");
            division = rs.getString("Division");
            country = rs.getString("Country");
        }
        return new Customer(customerID, divisionID, countryID, customerName, address, postalCode, phone, division, country);
    }

    /**
     * Selects all entries in client_schedule.users to populate the User ID ComboBox
     * @param userID User ID - ComboBox also contains User Name
     * @return User objects to populate the ComboBox
     * @throws SQLException SQL Exception
     */
    public static Users getUserData(int userID) throws SQLException{

        String sql = "SELECT * FROM Users WHERE User_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        // variables for scope reasons
        String userName = "";
        String password = "";
        while(rs.next()){
            userName = rs.getString("User_Name");
            password = rs.getString("Password");
        }
        return new Users(userID, userName, password);
    }

    /**
     * Adds Appointment data from the Add Appointment Page into the database and allAppointments ObservableList
     * @param title Title of Appointment
     * @param description Appointment description
     * @param location Location
     * @param type Appointment Type
     * @param start Start Time and Date
     * @param end End Time and Date
     * @param customerID CustomerID associated with appointment
     * @param userID UserID associated with appointment
     * @param contactID ContactID/Contact associated with appointment
     * @throws SQLException SQL Exception
     */
    public static void insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID ) throws SQLException {

        String sql = "INSERT INTO appointments ( Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.executeUpdate();
    }

    /**
     * Uses Appointment ID to update Appointment data from the Update Appointment Page into the database and allAppointments ObservableList
     * @param appointmentID Appointment ID
     * @param title Appointment Title
     * @param description Description
     * @param location Location
     * @param type Appointment Type
     * @param appointmentStart Start Date and Time
     * @param appointmentEnd End Date and Time
     * @param customerID Customer ID associated with Appointment
     * @param userID User ID associated with Appointment
     * @param contactID Contact ID/Contact associated with Appointment
     * @throws SQLException SQL Exception
     */
    public static void update(int appointmentID, String title, String description, String location, String type,
                             LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID) throws SQLException{
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ? , End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?"; //AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setInt(4, contactID);
        ps.setString(5, type);
        ps.setTimestamp(6, Timestamp.valueOf(appointmentStart));
        ps.setTimestamp(7, Timestamp.valueOf(appointmentEnd));
        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, appointmentID);

        ps.executeUpdate();
    }

    /**
     * Uses Appointment ID to identify and delete Appointment Data from the database and allAppointments ObservableList
     * @param appointmentID Appointment ID
     * @throws SQLException SQL Exception
     */
    public static void delete(int appointmentID) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    // Queries for getting various IDs for Appointments

    /**
     * Looks up the Appointment ID using the Customer ID - used in CreateUpdateAppointment
     * Variables chosen with the assumption that a customer cannot be at two different appointments at the same time
     * @param customerID CustomerID
     * @param start Start Date/Time
     * @return The Appointment ID to look up the Appointment object
     * @throws SQLException SQL Exception
     */
    public static int queryAppointmentID(int customerID, LocalDateTime start) throws SQLException {
        String sql = "SELECT Appointment_ID FROM Appointments WHERE Customer_ID = ? AND Start = ?";
        // Since one customer cannot be in two appointments at once
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setTimestamp(2, Timestamp.valueOf(start));
        ResultSet rs = ps.executeQuery();
        int appointmentID = 0;
        while(rs.next()){
            appointmentID = rs.getInt("Appointment_ID");
        }
        return appointmentID;
    }

    /**
     * Looks up the Contact Name using the Contact ID - used in CreateUpdateAppointment
     * @param contactID Contact ID
     * @return The Contact name to get the contact objects
     * @throws SQLException SQL Exception
     */
    public static String queryContactName(int contactID) throws SQLException {

        String sql = "SELECT Contact_Name FROM Contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        String contactName = "";
        while(rs.next()){
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    // For Sort Functions in Tables

    /**
     * Uses curdate to populate allAppointments based on current month value
     * @throws SQLException SQL Exception
     */
    public static void filterByMonth() throws SQLException {
        String sql = "SELECT * FROM appointments WHERE MONTH(start) = month(curdate())";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contactName = queryContactName(contactID);
            LocalDateTime appointmentStart = start.toLocalDateTime();
            LocalDateTime appointmentEnd = end.toLocalDateTime();
            ListManager.allAppointments.add(new Appointment(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName));
        }
    }

    /**
     * Uses curdate to populate allAppointments based on 7 day interval from current date/time
     * @throws SQLException SQL Exception
     */
    public static void filterByWeek() throws SQLException {
        String sql = "SELECT * FROM appointments WHERE start >= CURDATE() AND start <= DATE_ADD(curdate(), INTERVAL 7 DAY)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = queryContactName(contactID);
                LocalDateTime appointmentStart = start.toLocalDateTime();
                LocalDateTime appointmentEnd = end.toLocalDateTime();
                ListManager.allAppointments.add(new Appointment(appointmentID, title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName));
            }
    }

}


