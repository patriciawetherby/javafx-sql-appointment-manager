package util;
// This class handles the SQL queries select/insert/update/delete for the Customer records
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles the SQL queries select/insert/update/delete for Customer Records
 * It also contains additional methods to query foreign key data in first_level_divisions and countries
 */
public abstract class CustomerQuery {

    /**
     * Selects all entries in client_schedule.customers and populates ObservableList allCustomers
     * @throws SQLException SQLException
     */
    public static void select() throws SQLException {

        String mySql = "SELECT * FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.country_ID = countries.country_ID";
        PreparedStatement ps = DBConnection.connection.prepareStatement(mySql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID"), divisionID = rs.getInt("Division_ID"), countryID = rs.getInt("Country_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone"), division = rs.getString("Division"), country = rs.getString("Country");
            ListManager.allCustomers.add(new Customer(customerID, divisionID, countryID, customerName, address, postalCode, phone, division, country));
        }
    }

    /**
     * Selects all entries in client_schedule.first_level_divisions to populate the FLD ComboBox
     * using the countryID information from the database to match the divisionID
     * @param countryID Country ID
     * @throws SQLException SQL Exception
     */
    public static void selectFLD(int countryID) throws SQLException {

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            // Add to FLD Observable List
            ListManager.allFirstLevelDivisions.add(new FirstLevelDivisions(divisionID, division));
        }
    }

    /**
     * Selects all entries in client_schedule.countries to populate the Country ComboBox
     * @throws SQLException SQL Exception
     */
    public static void selectCountries() throws SQLException {

        String mySql = "SELECT * FROM countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(mySql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            // Add to Country Observable List
            ListManager.allCountries.add(new Countries(countryID, country));
        }
    }

    /**
     * Adds Customer data from the Add Customer Page into the database and allCustomers ObservableList
     * @param customerName Customer Name
     * @param address Address, no first level division information
     * @param postalCode Postal Code/Zipcode
     * @param phone Phone Number
     * @param divisionID Division ID
     * @throws SQLException SQL Exception
     */
    public static void insert(String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);

        ps.executeUpdate();
    }

    /**
     * Uses Customer ID to update Customer data from the Update Customer Page into the database and allCustomers ObservableList
     * @param customerName Customer Name
     * @param address Address, no first level division data
     * @param postalCode Postal Code/Zipcode
     * @param phone Phone Number
     * @param divisionID Division ID
     * @param customerID Customer ID
     * @throws SQLException SQL Exception
     */
    public static void update(String customerName, String address, String postalCode, String phone, int divisionID, int customerID) throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);

        ps.executeUpdate();
    }

    /**
     * Uses Customer ID to identify and delete Customer Data from the database and allCustomers ObservableList
     * @param customerID Customer ID
     * @throws SQLException SQL Exception
     */
    public static void delete(int customerID) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.executeUpdate();
    }

    /**
     * Looks up the Division ID using the Division Name - used in CreateUpdateCustomer
     * @param divisionName Division Name
     * @return division ID to look up Division object
     * @throws SQLException SQL Exception
     */
    public static int queryDivisionID(String divisionName) throws SQLException {

        String sql = "SELECT Division_ID FROM First_Level_Divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        int divisionID = 0;
        while(rs.next()){
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * Looks up the CountryID using the Country Name - used in CreateUpdateCustomer
     * @param countryName Country Name
     * @return country ID to look up Country object
     * @throws SQLException SQL Exception
     */
    public static int queryCountryID(String countryName) throws SQLException {

        String sql = "SELECT Country_ID FROM Countries WHERE Country = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();
        int countryID = 0;
        while(rs.next()){
            countryID = rs.getInt("Country_ID");
        }
        return countryID;
    }

    /**
     * Looks up the CustomerID in the database using the Customer Name and Phone Number
     * @param customerName Customer Name
     * @param phoneNumber Phone Number
     * @return customer ID
     * @throws SQLException SQL Exception
     */
    public static int queryCustomerID(String customerName, String phoneNumber) throws SQLException{
        String sql = "SELECT Customer_ID from Customers WHERE Customer_Name = ? AND Phone = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, phoneNumber);
        ResultSet rs = ps.executeQuery();
        int customerID = 0;
        while(rs.next()){
            customerID = rs.getInt("Customer_ID");
        }
        return customerID;
    }

}
