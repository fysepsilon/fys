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
    @FXML private ComboBox airport_combo, color_combo, type_combo;
    @FXML private TextField name_input, surname_input, labelnumber_input, 
            flightnumber_input, destination_input, brand_input, 
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
        taal language = new taal();
        String[] taal = language.getLanguage();
        
        if ((airport_combo.getValue() == null) || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_combo.getValue() == null)) {
            System.out.println(taal[93]);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
                    fys.getBaggageTypeString(type_combo.getValue().toString()), brand_input.getText(), 
                    fys.getColorString(color_combo.getValue().toString()), characteristics_input.getText(), 
                    dateString, timeString);
        }
    }
    
    private void sendToDatabase(String airport, String frontname, String surname,
            String labelnumber, String flightnumber, String destination, int type, 
            String brand, Integer color, String characteristics, String date, 
            String time) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "INSERT INTO bagagedatabase.person (type, language, first_name, surname) "
                    + "VALUES ('0', '0', '" + frontname + "', '" + surname + "')";

            stmt.executeUpdate(sql_person);

            String sql_airport = "INSERT INTO bagagedatabase.airport (date, "
                    + "time, airport_found, label_number, flight_number, destination) "
                    + "VALUES ('" + date + "', '" + time + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";

            stmt.executeUpdate(sql_airport);

            String sql_personID = "SELECT person_id, lost_and_found_id FROM person, airport WHERE "
                    + "person.first_name = '" + frontname + "'AND person.surname = '" + surname + "' "
                    + "AND airport.date = '" + date + "' AND airport.time = '" + time + "' "
                    + "AND airport.airport_found = '" + airport + "' AND airport.label_number = '"
                    + labelnumber + "' AND airport.flight_number = '" + flightnumber + "' "
                    + "AND airport.destination = '" + destination + "'";

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

            String sql_found = "INSERT INTO bagagedatabase.found (type, brand, color, "
                    + "characteristics, status, person_id, lost_and_found_id) VALUES ('" + type + "', "
                    + "'" + brand + "', '" + color + "', '" + characteristics + "', 0, "
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
        taal language = new taal();
        String[] taal = language.getLanguage();
        airport_label.setText(taal[8] + ":");
        name_label.setText(taal[9] + ":");
        surname_label.setText(taal[10] + ":");
        label_label.setText(taal[17] + ":");
        flight_label.setText(taal[18] + ":");
        destination_label.setText(taal[19] + ":");
        type_label.setText(taal[20] + ":");
        brand_label.setText(taal[21] + ":");
        color_label.setText(taal[22] + ":");
        characteristics_label.setText(taal[23] + ":");
        picture_label.setText(taal[24] + ":");
        airport_combo.setPromptText(taal[25]);
        type_combo.setPromptText(taal[26]);
        color_combo.setPromptText(taal[31]);
        color_combo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        type_combo.getItems().addAll(taal[29], taal[27], taal[30], taal[125], taal[28]);
        picture_button.setText(taal[44]);
        send_button.setText(taal[46]);
    }   
}
