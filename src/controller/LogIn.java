package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This is the controller for the login page
 */


public class LogIn implements Initializable {

    Stage stage;
    ResourceBundle rb = ResourceBundle.getBundle("interfaces/Nat");
    private int loginAttempts = 0;
    private boolean loginSuccess = false;
    private boolean loginError = false;

    /**
     * onAction: When login credentials are valid, the screen shows the Tables screen
     * Login attempts and records are gathered here
     * @param event Verifies Login Information
     */
    @FXML
    void onActionGoToTables(ActionEvent event) throws IOException {

        String userID = userTxt.getText();
        String password = passwordTxt.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy hh:mm a");
        String fileName = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        if (((userID.equals("test") && password.equals("test")) || (userID.equals("admin")) && password.equals("admin"))){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Tables.fxml"));
            loader.load();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot(); //Switches to Tables
            stage.setScene(new Scene(scene));
            stage.show();

            loginAttempts++;
            loginSuccess = true;
            System.out.println("\nSuccessful login by " + userID + " on " + LocalDateTime.now().format(dtf));
            outputFile.println("\nSuccessful login by " + userID + " on " + LocalDateTime.now().format(dtf));

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //Alert dialog box
            alert.setTitle(rb.getString("errorTitle"));
            alert.setHeaderText(rb.getString("errorHeader"));
            alert.setContentText(rb.getString("errorText"));
            alert.showAndWait();

            loginAttempts++;
            loginError = false;
            if(userID.isBlank()){
                System.out.println("\nFailed login attempt by [BLANK] on " + LocalDateTime.now().format(dtf));
                outputFile.println("\nFailed login attempt by [BLANK] on " + LocalDateTime.now().format(dtf));
            }
            else{
                System.out.println("\nFailed login attempt by " + userID + " on " + LocalDateTime.now().format(dtf));
                outputFile.println("\nFailed login attempt by " + userID + " on " + LocalDateTime.now().format(dtf));
            }

        }

        System.out.println("\nLogin Attempts: " + loginAttempts);
        outputFile.close();

    }

    /**
     * Closes Application when Exit button is pressed
     * @param event Exits the application
     */
    @FXML
    void onActionCloseApplication(ActionEvent event) {
        System.exit(0);
    }

    // FXML Labels

    @FXML
    private Label loginTitle;

    @FXML
    private Label userLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label tzDetectedLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TextField userTxt;

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //System.out.println(Locale.getDefault());

        ZoneId zoneId = ZoneId.systemDefault();
        timeZoneLabel.setText(String.valueOf(zoneId));

        // Language Changes EN <-> FR
        loginTitle.setText(rb.getString("loginTitle"));
        userLbl.setText(rb.getString("user"));
        userTxt.setPromptText(rb.getString("userPrompt"));
        passwordLbl.setText(rb.getString("password"));
        passwordTxt.setPromptText(rb.getString("passwordPrompt"));
        tzDetectedLbl.setText(rb.getString("timeZoneDetected"));
        loginBtn.setText(rb.getString("loginBtn"));
        exitBtn.setText(rb.getString("exitBtn"));



    }
}

