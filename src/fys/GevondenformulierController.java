/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.File;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class GevondenformulierController implements Initializable {

    //Alle inputvelden initialiseren
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ComboBox airport_combo, color_combo, type_combo, destination_combo;
    @FXML
    private TextField name_input, surname_input, labelnumber_input,
            flightnumber_input, brand_input, characteristics_input;
    @FXML
    private Label surname_label, name_label, airport_label, label_label,
            flight_label, destination_label, type_label, brand_label, 
            color_label, characteristics_label, picture_label, loginerror, 
            popup_label, mandatory;
    @FXML
    private TextArea textinfo;
    @FXML
    private TableView<Bagage> table;
    @FXML
    private ObservableList<Bagage> data = FXCollections.observableArrayList(), 
            datafilter = FXCollections.observableArrayList();
    @FXML
    private TableColumn status, type, color, brand, picture, information, 
            firstName, surName;
    @FXML
    private Button picture_button, send_button, popup_verzbutton, 
            popup_annubutton;
    @FXML
    private Pane popup, formulier;
    @FXML
    private final FYS fys = new FYS();
    @FXML
    public String filePath = null;
    @FXML
    private final taal language = new taal();
    @FXML
    private final String[] taal = language.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mandatory.setText("* " + taal[174]);
        airport_label.setText(taal[8] + "* :");
        name_label.setText(taal[9] + ":");
        surname_label.setText(taal[10] + ":");
        label_label.setText(taal[17] + ":");
        flight_label.setText(taal[18] + ":");
        destination_label.setText(taal[19] + ":");
        type_label.setText(taal[20] + "* :");
        brand_label.setText(taal[21] + "* :");
        color_label.setText(taal[22] + "* :");
        characteristics_label.setText(taal[23] + ":");
        picture_label.setText(taal[24] + ":");
        airport_combo.setPromptText(taal[25]);
        destination_combo.setPromptText(taal[25]);
        type_combo.setPromptText(taal[26]);
        color_combo.setPromptText(taal[31]);
        color_combo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        type_combo.getItems().addAll(taal[29], taal[27], taal[30], taal[125], taal[28]);
        picture_button.setText(taal[44]);
        send_button.setText(taal[46]);
        
        //Popup
        popup_label.setText(taal[150]);
        popup_verzbutton.setText(taal[46]);
        popup_annubutton.setText(taal[127]);
        status.setText(taal[48]);
        type.setText(taal[50]);
        color.setText(taal[49]);
        brand.setText(taal[51]);
        picture.setText(taal[24]);
        information.setText(taal[23]);
        firstName.setText(taal[9]);
        surName.setText(taal[10]);
        
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surName.setCellValueFactory(new PropertyValueFactory<>("surName"));

        status.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        picture.setStyle("-fx-alignment: CENTER;");
        information.setStyle("-fx-alignment: CENTER;");
        firstName.setStyle("-fx-alignment: CENTER;");
        surName.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }

    public void getLuggageData() {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "SELECT lost.*, "
                    + "person.first_name, person.surname FROM lost, person "
                    + "WHERE lost.person_id = person.person_id "
                    + "AND lost.type='" + (type_combo.getValue() == null ? ""
                            : fys.getBaggageTypeString(type_combo.getValue().toString())) + "' "
                    + "AND lost.brand = '" + brand_input.getText() + "' "
                    + "AND lost.color = '" + (color_combo.getValue() == null ? ""
                            : fys.getColorString(color_combo.getValue().toString())) + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                String statussql = fys.getStatus(rs.getInt("status"));
                String typesql = fys.getBaggageType(rs.getInt("type"));
                String colorsql = fys.getColor(rs.getInt("color"));
                String brandsql = rs.getString("brand");
                String picturesql = rs.getString("picture");
                String informationsql = rs.getString("characteristics");
                String firstnamesql = rs.getString("first_name");
                String surnamesql = rs.getString("surname");

                data.add(new Bagage(statussql, typesql, colorsql, brandsql,
                        picturesql, informationsql, firstnamesql, surnamesql));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    //Methode om ingevulde data van gevonden bagage naar de database te sturen
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        getLuggageData();
        //Controleren of alles wat ingevuld moet worden is ingevuld
        if ((airport_combo.getValue() == null) || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_combo.getValue() == null)) {
            loginerror.setVisible(false);
            loginerror.setText(taal[93]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
        } else if (fys.checkLost(
                fys.getBaggageTypeString(type_combo.getValue().toString()),
                brand_input.getText(),
                fys.getColorString(color_combo.getValue().toString()))) {
            popup.setVisible(true);
            formulier.setDisable(true);

            int count = fys.countLost(fys.getBaggageTypeString(type_combo.getValue().toString()), brand_input.getText(), fys.getColorString(color_combo.getValue().toString()));
            if (count == 1) {
                textinfo.setText("Er is " + count + " vermist bagagestuk met dezelfde kenmerken gevonden\n"
                        + "als wat er net is ingevuld.\n"
                        + "\n"
                        + "Hieronder staat alle informatie over dit al opgegeven vermiste\n"
                        + "bagagestuk. Je kan jouw ingevulde bagagestuk annuleren of\n"
                        + "toch verzenden.");
            } else {
                textinfo.setText("Er zijn " + count + " vermiste bagagestukken met dezelfde kenmerken gevonden\n"
                        + "als wat er net is ingevuld.\n"
                        + "\n"
                        + "Hieronder staat alle informatie over deze al opgegeven vermiste\n"
                        + "bagagestukken. Je kan jouw ingevulde bagagestuk annuleren of\n"
                        + "toch verzenden.");
            }
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

            String destination;
            if (destination_combo.getValue() == null) {
                destination = " ";
            } else {
                destination = destination_combo.getValue().toString();
            }

            sendToDatabase(airport_combo.getValue().toString(), name_input.getText(),
                    surname_input.getText(), labelnumber_input.getText(), filePath,
                    flightnumber_input.getText(), destination,
                    fys.getBaggageTypeString(type_combo.getValue().toString()),
                    brand_input.getText(), fys.getColorString(color_combo.getValue().toString()),
                    characteristics_input.getText(), dateString, timeString);
        }
    }

    private void sendToDatabase(String airport, String frontname, String surname,
            String labelnumber, String filePath, String flightnumber, String destination,
            int type, String brand, Integer color, String characteristics, String date,
            String time) throws IOException, SQLException {

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            if ((name_input.getText() == null || name_input.getText().trim().isEmpty())
                    || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())) {
                String sql_person = "INSERT INTO bagagedatabase.person (type, language, first_name, surname, IS_SHOW) "
                        + "VALUES ('0', '0', '" + frontname + "', '" + surname + "', '1')";
                stmt.executeUpdate(sql_person);
            } else {
                String sql_person = "INSERT INTO bagagedatabase.person (type, language, first_name, surname) "
                        + "VALUES ('0', '0', '" + frontname + "', '" + surname + "')";
                stmt.executeUpdate(sql_person);
            }

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

                String strB = id_rs.getString("lost_and_found_id");
                lostAndFoundIdStr = strB.replace("\n", ",");
                lostAndFoundId = Integer.parseInt(lostAndFoundIdStr);
            }

            String sql_found = "INSERT INTO bagagedatabase.found (type, brand, color, "
                    + "characteristics, status, picture, person_id, lost_and_found_id) VALUES ('" + type + "', "
                    + "'" + brand + "', '" + color + "', '" + characteristics + "', 0, "
                    + "'" + filePath + "', '" + personId + "', '" + lostAndFoundId + "')";

            stmt.executeUpdate(sql_found);
            id_rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        fys.changeToAnotherFXML(taal[96], "gevondenformulier.fxml");
    }

    @FXML
    public void handleCancelBut(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[96], "gevondenformulier.fxml");
    }

    @FXML
    public void handleVerzendenBut(ActionEvent event) throws IOException, SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateTimeString = dateFormat.format(date);
        String[] tokens = dateTimeString.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException();
        }
        String dateString = tokens[0];
        String timeString = tokens[1];

        String destination;
        if (destination_combo.getValue() == null) {
            destination = " ";
        } else {
            destination = destination_combo.getValue().toString();
        }

        sendToDatabase(airport_combo.getValue().toString(), name_input.getText(),
                surname_input.getText(), labelnumber_input.getText(), filePath,
                flightnumber_input.getText(), destination,
                fys.getBaggageTypeString(type_combo.getValue().toString()),
                brand_input.getText(), fys.getColorString(color_combo.getValue().toString()),
                characteristics_input.getText(), dateString, timeString);
    }

    //Fileselector aanroepen wanneer iemand een afbeelding wil toevoegen
    @FXML
    public void handleFileSelector(ActionEvent event) {
        File file = fys.fileChooser();
        //String fileRaw = file.getAbsolutePath();
        filePath = "fys/luggageImages/" + file.getName();
        //filePath = fileRaw.replace("\\","\\\\");
        System.out.println(filePath);
        picture_button.setText(file.getName());
    }
}
