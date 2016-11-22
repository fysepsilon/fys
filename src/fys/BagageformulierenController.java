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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    @FXML private TextField surname_input;
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
        
        if((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
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
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = new Date();
            String dateTimeString = dateFormat.format(date);    
            String[] tokens = dateTimeString.split(" ");
            if (tokens.length != 2) {
                throw new IllegalArgumentException();
            }
            String dateString = tokens[0];
            String timeString = tokens[1];
// now do your processing
            
            sendToDatabase(airport_combo.getValue().toString(), name_input.getText(), surname_input.getText(), 
                    address_input.getText(), residence_input.getText(), zipcode_input.getText(),
                    country_input.getText(), phone_input.getText(), mail_input.getText(), 
                    account_checkbox.isSelected(), labelnumber_input.getText(), 
                    flightnumber_input.getText(), destination_input.getText(), 
                    type_combo.getValue().toString(), brand_input.getText(), 
                    color_input.getText(), dateString, timeString);
        }
    }
    
    @FXML
    private void sendToDatabase(String airport, String frontname, String surname, String address, String residence, String zipcode, 
            String country, String phone, String mail, Boolean checkBox, String labelnumber, String flightnumber, 
            String destination, String type, String brand, String color, String date, String time) throws IOException, SQLException {
        FYS fys = new FYS();
        
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "INSERT INTO bagagedatabase.person_table (first_name, surname, address, residence, "
                    + "zipcode, country, phone, mail) VALUES ('" + frontname + "', '" + surname + "', '" + address + "', "
                    + "'" + residence + "', '" + zipcode + "', '" + country + "', '" + phone + "', "
                    + "'" + mail + "')";
            
            String sql_airport = "INSERT INTO bagagedatabase.airport_table (date, time, airport_lost, "
                    + "label_number, flight_number, destination) VALUES ('" + date + "', '" + time + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";
            
            String sql_personID = "SELECT person_id FROM person_table WHERE first_name = '" + frontname + "'"
                    + "AND surname = '" + surname + "' AND address = '" + address + "'"
                    + "AND residence = '" + residence + "' AND zipcode = '" + zipcode + "'"
                    + "AND country = '" + country + "' AND phone = '" + phone + "'"
                    + "AND mail = '" + mail + "'";
            
            String personID = stmt.executeQuery(sql_personID).toString();
            System.out.println(personID);
            
            String sql_lost = "INSERT INTO";
            
            if(checkBox){
                String sql_account = "INSERT INTO bagagedatabase.accounts (type, mail, password, language,"
                        + "first_name, last_name, address, residence, zip_code, country, phone) VALUES"
                        + "(0, '" + mail + "', '33225ecb58b9218a', 0, '" + frontname + "', '" + surname + "',"
                        + "'" + address + "', '" + residence + "', '" + zipcode + "', '" + country + "',"
                        + "'" + phone + "')";
                
                stmt.executeUpdate(sql_account);
            }
            
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