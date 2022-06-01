package model;

/**
 * The Appointment class represents the Appointment data from the database that will populate the Appointments Table
 */

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    /**
     * The Appointment constructor initializes the Appointment fields
     * @param appointmentID Appointment ID
     * @param title Appointment Title / Appointment Name
     * @param description Appointment Description
     * @param location Location
     * @param type Appointment Type
     * @param appointmentStart Start Date / Time
     * @param appointmentEnd Start Date / Time
     * @param customerID Customer ID associated with Appointment
     * @param userID User ID associated with Appointment
     * @param contactID Contact ID associated with Appointment
     * @param contactName Contact Name associated with Appointment
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /** Overrides Appointment toString function and changes reference/address to readable prompt
     */
    @Override
    public String toString() {
        return ("Customer: " + customerID + " Appointment ID: " + appointmentID + " Title: " + title + " Type: " + type +
                "\nDescription: "+ description + " Start: " + appointmentStart + " End: " +appointmentEnd);
    }

    // Setters and Getters

    /**
     * The getAppointmentID method gets the Appointment ID
     * @return Appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * The setAppointmentID method updates the Appointment ID
     * @param appointmentID Appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * The getTitle method gets the Appointment Title
     * @return Appointment Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setTitle method updates the Appointment Title
     * @param title Appointment Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getDescription method gets the Appointment description
     * @return Appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setDescription method updates the Appointment description
     * @param description Appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getLocation method gets the Appointment Location
     * @return Appointment Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setLocation method updates the Appointment Location
     * @param location Appointment Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getType method gets the Appointment Type
     * @return Appointment Type
     */
    public String getType() {
        return type;
    }

    /**
     * The setType method updates the Appointment Type
     * @param type Appointment Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getAppointmentStart method gets the Appointment Start Date / Time
     * @return Appointment Start Date / Time
     */
    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    /**
     * The setAppointmentStart method updates the Appointment Start Date / Time
     * @param appointmentStart Appointment Start Date / Time
     */
    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    /**
     * The getAppointmentEnd method gets the Appointment End Date / Time
     * @return Appointment End Date / Time
     */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    /**
     * The setAppointmentEnd method updates the Appointment End Date / Time
     * @param appointmentEnd Appointment End Date / Time
     */
    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    /**
     * The getCustomerID method gets the Customer ID
     * @return Customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * The setCustomerID method updates the Customer ID
     * @param customerID Customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * The getUserID method gets the User ID
     * @return User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The setUserID method updates the User ID
     * @param userID User ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * The getContactID method gets the Contact ID
     * @return Contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * The setContactID method updates the Contact ID
     * @param contactID Contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * The getContactName method gets the Contact Name
     * @return Contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setContactName method updates the Contact name
     * @param contactName Contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


}
