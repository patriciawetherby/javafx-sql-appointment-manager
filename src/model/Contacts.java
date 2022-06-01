package model;

/**
 * The Contacts class represents the available contacts for the Appointment forms and records
 */

public class Contacts {

    int contactID;
    String contactName;

    /**
     * The Contacts constructor initializes the Contact fields
     * @param contactID Contact ID
     * @param contactName Contact Name
     */
    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Overrides Contacts ComboBox toString function and changes reference/address to readable prompt
     * @return String Contacts for Contacts ComboBox in CreateUpdateAppointments
     */
    @Override // Changes from reference to String
    public String toString() {
        return (contactName);
    }

    // Setters and Getters

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
     * @return Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setContactName method updates the Contact Name
     * @param contactName Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
