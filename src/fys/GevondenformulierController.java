/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lucas Lageweg
 */
public class GevondenformulierController implements Initializable {
    @FXML private ComboBox airport_combo, type_combo;
    @FXML private TextField name_input, surname_input, labelnumber_input, 
            flightnumber_input, destination_input, brand_input, color_input, 
            characteristics_input;
    private CheckBox account_checkbox;
    @FXML private Button picture_button;
    @FXML private Label surname_label, name_label, airport_label, label_label, 
            flight_label, destination_label, type_label, brand_label, color_label,
            characteristics_label, picture_label;
    @FXML private Button send_button;
    
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();

        if ((airport_combo.getValue() == null) || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_input.getText() == null || color_input.getText().trim().isEmpty())) {
            System.out.println("U heeft niet alles ingevuld!");
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = new Date();
            String dateTimeString = dateFormat.format(date);
            String[] tokens = dateTimeString.split(" ");
            if (tokens.length != 2) {
                throw new IllegalArgumentException();
            }
            String dateString = tokens[0];
            String timeString = tokens[1];

            sendToDatabase(airport_combo.getValue().toString(), name_input.getText(),
                    surname_input.getText(), labelnumber_input.getText(), 
                    flightnumber_input.getText(), destination_input.getText(), 
                    type_combo.getValue().toString(), brand_input.getText(), 
                    color_input.getText(), characteristics_input.getText(), 
                    dateString, timeString);
        }
    }
    
    private void sendToDatabase(String airport, String frontname, String surname,
            String labelnumber, String flightnumber, String destination, String type, 
            String brand, String color, String characteristics, String date, 
            String time) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "INSERT INTO bagagedatabase.person_table (type, language, first_name, surname) "
                    + "VALUES ('0', '0', '" + frontname + "', '" + surname + "')";

            stmt.executeUpdate(sql_person);

            String sql_airport = "INSERT INTO bagagedatabase.airport_table (date, "
                    + "time, airport_found, label_number, flight_number, destination) "
                    + "VALUES ('" + date + "', '" + time + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";

            stmt.executeUpdate(sql_airport);

            String sql_personID = "SELECT person_id, lost_and_found_id FROM person_table, airport_table WHERE "
                    + "person_table.first_name = '" + frontname + "'AND person_table.surname = '" + surname + "' "
                    + "AND airport_table.date = '" + date + "' AND airport_table.time = '" + time + "' "
                    + "AND airport_table.airport_lost = '" + airport + "' AND airport_table.label_number = '"
                    + labelnumber + "' AND airport_table.flight_number = '" + flightnumber + "' "
                    + "AND airport_table.destination = '" + destination + "'";

            ResultSet id_rs = stmt.executeQuery(sql_personID);
            String personIdStr = null, lostAndFoundIdStr = null;
            int personId = -1, lostAndFoundId = -1;
            while (id_rs.next()) {
                String strA = id_rs.getString("person_id");
                personIdStr = strA.replace("\n", ",");
                personId = Integer.parseInt(personIdStr);
                System.out.println(personId);

                String strB = id_rs.getString("lost_and_found_id");
                lostAndFoundIdStr = strB.replace("\n", ",");
                lostAndFoundId = Integer.parseInt(lostAndFoundIdStr);
                System.out.println(lostAndFoundIdStr);
            }

            String sql_found = "INSERT INTO bagagedatabase.found_table (type, brand, color, "
                    + "characteristics, status, person_id, lost_and_found_id) VALUES ('" + type + "', "
                    + "'" + brand + "', '" + color + "', '" + characteristics + "', 'Gevonden', "
                    + "'" + personId + "', '" + lostAndFoundId + "')";

            stmt.executeUpdate(sql_found);
            id_rs.close();
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