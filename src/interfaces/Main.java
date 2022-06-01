package interfaces;

import com.sun.javafx.runtime.VersionInfo;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.AppointmentQuery;
import util.CustomerQuery;
import util.DBConnection;

import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * MAIN
 */

public class Main extends Application {

    /**
     * Application starting message
     */
    @Override
    public void init(){
        System.out.println("Starting...");
    }

    /**
     * Prompts application to start at Log In Page
     * @param loginPage Scene for login page
     */
    @Override
    public void start(Stage loginPage) throws Exception{

        // Language Resource Bundle
        ResourceBundle rb = ResourceBundle.getBundle("interfaces/Nat");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
        loader.setResources(rb);
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LogIn.fxml")));

        loginPage.setTitle("Software II Project");
        loginPage.setScene(new Scene(login, 450, 400));
        loginPage.show();
    }

    /**
     * Generates stop message when the application is closed
     */
    @Override
    public void stop(){
        System.out.println("Application Stopped.");
    }

    public static void main(String[] args) throws SQLException
    {
        DBConnection.openConnection(); // Connect to database
        // Populate the Main Menu Tables
        CustomerQuery.select();
        AppointmentQuery.select();
        // Populate the Combo Boxes
        CustomerQuery.selectFLD(0);
        CustomerQuery.selectCountries();
        AppointmentQuery.selectContacts();
        AppointmentQuery.selectUsers();

        // Launch Window
        launch(args);

        DBConnection.closeConnection(); // Close connection
    }
}
