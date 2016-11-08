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
    
    //Made a method to switch to FXML screens.
    public void changeToAnotherFXML(String title, String changeToWindow) throws IOException{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource(changeToWindow));
        Stage mainStage;
        mainStage = FYS.parentWindow;
        mainStage.setTitle(title);
        mainStage.getScene().setRoot(window1);
    }
    
    public boolean authenticateLogin(String inputUsername, String inputPassword) throws SQLException{
        String username = "", password = "";
        try {
            Statement stmt = null;
            Connection conn = null;
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=root");
            stmt = conn.createStatement();
            
            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "SELECT mail, password FROM accounts WHERE mail='" + inputUsername +"' AND password = '" + inputPassword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("mail");
                password = rs.getString("password");
                //Display values
//                System.out.print("username: " + username);
//                System.out.print(" password: " + password);
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        //Return boolean values.
        if(username != "" && password != ""){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
