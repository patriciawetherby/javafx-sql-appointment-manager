package model;

/**
 * The Customer class represents the Customer data from the database that will populate the Customer Records Table
 */
public class Customer {

    int customerID, divisionID, countryID;
    String customerName;
    String address;
    String postalCode;
    String phone, division, country;

    /**
     * The Customer constructor initializes the Customer fields
     * @param customerID Customer ID
     * @param divisionID Division ID
     * @param countryID Country ID
     * @param customerName Customer Name
     * @param address Customer Address
     * @param postalCode Postal Code/ Zipcode
     * @param phone Phone Number
     * @param division Division Name
     * @param country Country Name
     */
    public Customer(int customerID, int divisionID, int countryID, String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerID = customerID;
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * Overrides Customer ComboBox toString function and changes reference/address to readable prompt
     * @return String for Customer ID Combo Box in CreateUpdateAppointments
     */
    @Override
    public String toString() {
        return ("Customer ID: " + customerID + " (" + customerName + ")");
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
     * The getDivisionID method gets the division ID
     * @return Division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * The setDivisionID method updates the Division ID
     * @param divisionID Division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * The getCountryID method gets the Country ID
     * @return Country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * The setCountryID method updates the Country ID
     * @param countryID Country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * The getCustomerName method gets the Customer Name
     * @return Customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The setCustomerName method updates the Customer name
     * @param customerName Customer Name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * The getAddress method gets the Customer Address
     * @return Customer Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * The setAddress method updates the Customer Address
     * @param address Customer Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * The getPostalCode method gets the Postal Code / Zipcode
     * @return Postal Code / Zipcode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The setPostalCode method updates the Postal Code / Zipcode
     * @param postalCode Postal Code / Zipcode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * The getPhone method gets the Customer Phone Number
     * @return Customer Phone Number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * The setPhone method updates the Customer phone number
     * @param phone Customer phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * The getDivision method gets the division name
     * @return Division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * The setDivision method updates the Division name
     * @param division Division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * The getCountry method gets the Country name
     * @return Country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * The setCountry method updates the Country name
     * @param country Country name
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
