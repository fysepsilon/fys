/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Veron
 */
public class accountsController {

    @FXML private Button nieuwaccount;

    @FXML
    private void handleNieuwAccount(ActionEvent event) throws IOException {
        //Switch screen to homepage.
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Nieuw account aanmaken", "homepage.fxml");
    }

    @FXML public static void main (String[] args) {
        FYS fys = new FYS();
        try {
            Statement stmt = null;
            Connection conn = null;
            
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            
            //connectToDatabase(conn, stmt, "test", "root", "root");
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT first_name FROM bagagedatabase.accounts");
            while ( rs.next() ) {
                String lastName = rs.getString("Lname");
                System.out.println(lastName);
            }
            conn.close();
       } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
