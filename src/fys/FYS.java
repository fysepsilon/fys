/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paras
 */
public class FYS extends Application {
    
    public static Stage parentWindow;
    
    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage; 
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Corendon-Login");
        stage.show();
    }
    
    public void changeToAnotherFXML(String title, String changeToWindow) throws IOException{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource(changeToWindow));
        Stage mainStage;
        mainStage = FYS.parentWindow;
        mainStage.setTitle(title);
        mainStage.getScene().setRoot(window1);
    }
    
    public void connectToDatabase(Connection conn, Statement stmt, String databaseName, String databaseUser, String databasePassword) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?"
                    + "user=" + databaseUser + "&password=" + databasePassword);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    public void authenticateLogin() throws SQLException{
        Statement stmt = null;
        Connection conn = null;
        connectToDatabase(conn, stmt, "test", "root", "root");
        String sql = "SELECT * FROM cijfer";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            //Retrieve by column name
            int id = rs.getInt("id");
            int vakId = rs.getInt("vakId");
            int studentId = rs.getInt("studentId");
            int cijfer = rs.getInt("cijfer");
            int datum = rs.getInt("datum");

            //Display values
            System.out.print("ID: " + id);
            System.out.print(" vakId: " + vakId);
            System.out.print(" studentId: " + studentId);
            System.out.print(" cijfer: " + cijfer);
            System.out.print(" datum: " + datum);
            System.out.println();
        }
        rs.close();
        conn.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
