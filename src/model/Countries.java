package model;

/**
 * The Countries class represents the Country data from the database that will be used for the address
 */

public class Countries {

    int countryID;
    String country;

    /**
     * The Countries constructor initializes the Country fields
     * @param countryID Country ID
     * @param country Country
     */
    public Countries(int countryID, String country){

        this.countryID = countryID;
        this.country = country;
    }

    /** Overrides ComboBox toString function and changes reference/address to readable prompt
     * @return String Country ID + Country for Country ComboBox in CreateUpdateCustomer
     */
    @Override
    public String toString() {
        return ("#" + countryID + " " + country);
    }

    // Setters and Getters

    /**
     * The getCountryID method gets the Country ID
     * @return Country ID
     */
    public int getCountryID(){
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

