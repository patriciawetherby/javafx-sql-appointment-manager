package model;

/**
 * The FirstLevelDivisions class represents the state/province/prefecture data from the database
 * @author Patricia
 */
public class FirstLevelDivisions {

    private int divisionID;
    private String division;

    /**
     * The FirstLevelDivisions constructor initializes the FLD fields
     * @param divisionID Division ID
     * @param division Division Name
     */
    public FirstLevelDivisions(int divisionID, String division){

        this.division = division;
        this.divisionID = divisionID;

    }

    /** Overrides FLD ComboBox toString function and changes reference/address to readable prompt
     */
    @Override
    public String toString(){
        return ("#" + divisionID + " " + division);
    }

    // Setters and Getters

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
}
