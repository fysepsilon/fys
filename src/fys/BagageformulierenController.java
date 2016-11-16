/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class BagageformulierenController implements Initializable {        
    @FXML private ComboBox airport_combo;
    @FXML private TextField name_input;
    @FXML private TextField address_input;
    @FXML private TextField residence_input;
    @FXML private TextField zipcode_input;
    @FXML private TextField country_input;
    @FXML private TextField phone_input;
    @FXML private TextField mail_input;
    @FXML private CheckBox account_checkbox;
    @FXML private TextField lablenumber_input;
    @FXML private TextField flightnumber_input;
    @FXML private ComboBox type_combo;
    @FXML private TextField brand_input;
    @FXML private TextField color_input;
    @FXML private TextField characteristics_input;
    @FXML private Button picture_button;
    
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        //Check if username and password is filled in and correct.
        //Show error if not filled in or not correct.
        if((name_input.getText() == null || name_input.getText().trim().isEmpty()) 
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zipcode_input.getText() == null || zipcode_input.getText().trim().isEmpty())
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_input.getText() == null || color_input.getText().trim().isEmpty()
        )){
            //Doen indien niet goed
        } else{
            sendToDatabase(name_input.getText());
        }
    }
    
    @FXML
    private void sendToDatabase(String name) throws IOException, SQLException {
        FYS fys = new FYS();
        
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "INSERT INTO bagagedatabase.person_table (first_name, surname, "
                    + "address, residence, zipcode, country, phone, mail) VALUES ('" + name + "', '" + name + "', "
                    + "'" + name + "', '" + name + "', '" + name + "', '" + name + "', '" + name + "', '" + name + "')";
            
            stmt.executeUpdate(sql);
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }       
}