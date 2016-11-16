/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @FXML private TextField labelnumber_input;
    @FXML private TextField flightnumber_input;
    @FXML private TextField destination_input;
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
                || (airport_combo.getValue() == null)
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zipcode_input.getText() == null || zipcode_input.getText().trim().isEmpty())
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_input.getText() == null || color_input.getText().trim().isEmpty()
        )){
            System.out.println("U heeft niet alles ingevuld!");
        } else{
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String dateString = dateFormat.format(date);
            
            sendToDatabase(airport_combo.getValue().toString(), name_input.getText(), 
                    address_input.getText(), residence_input.getText(), zipcode_input.getText(),
                    country_input.getText(), phone_input.getText(), mail_input.getText(), 
                    account_checkbox.isSelected(), labelnumber_input.getText(), 
                    flightnumber_input.getText(), destination_input.getText(), 
                    type_combo.getValue().toString(), brand_input.getText(), 
                    color_input.getText(), dateString);
        }
    }
    
    @FXML
    private void sendToDatabase(String airport, String name, String address, String residence, String zipcode, 
            String country, String phone, String mail, Boolean checkbox, String labelnumber, String flightnumber, 
            String destination, String type, String brand, String color, String date) throws IOException, SQLException {
        FYS fys = new FYS();
        
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "INSERT INTO bagagedatabase.person_table (first_name, address, residence, "
                    + "zipcode, country, phone, mail) VALUES ('" + name + "', '" + address + "', "
                    + "'" + residence + "', '" + zipcode + "', '" + country + "', '" + phone + "', "
                    + "'" + mail + "')";
            
            String sql_airport = "INSERT INTO bagagedatabase.airport_table (date, airport_lost, "
                    + "label_number, flight_number, destination) VALUES ('" + date + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";
            
            stmt.executeUpdate(sql_person);
            stmt.executeUpdate(sql_airport);
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