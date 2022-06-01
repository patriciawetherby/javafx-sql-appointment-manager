package model;

/**
 * The Users model represents the available users who log in
 * @author Patricia
 */
public class Users {

    int userID;
    String userName;
    String password;

    /**
     * The Constructor initializes the User fields
     * @param userID User ID - used to log in
     * @param userName Username
     * @param password Password - used to log in
     */
    public Users(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Overrides User ComboBox toString function and changes reference/address to readable prompt
     * @return String for User ID ComboBox
     */
    @Override
    public String toString() {
        return ("User ID: " + userID + " (" + userName + ")");
    }

    // Setters and Getters

    /**
     * getUserID method returns the Machine ID
     * @return userID of the User
     */
    public int getUserID() {
        return userID;
    }

    /**
     * setUserID updates the userID field
     * @param userID The ID of the User
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * getUserName method returns the userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setUserName updates the userName field
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getPassword method returns the password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setPassword updates the password field
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
