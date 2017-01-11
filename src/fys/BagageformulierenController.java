/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import static fys.FYS.generateRandomPassword;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class BagageformulierenController implements Initializable {

    //Alle inputvelden initialiseren
    @FXML
    private ComboBox airport_combo, type_combo, color_combo, destination_combo,
            language_combo;
    @FXML
    private TextField name_input, surname_input, address_input, residence_input,
            zipcode_input, country_input, phone_input, mail_input,
            labelnumber_input, flightnumber_input, brand_input,
            characteristics_input;
    @FXML
    private Button picture_button, send_button, popup_verzbutton, 
            popup_annubutton;
    @FXML
    private TableColumn status, type, color, brand, picture, information,
            firstName, surName;
    @FXML
    private Pane popup, formulier;
    @FXML
    private TextArea textinfo;
    @FXML
    private TableView<Bagage> table;
    @FXML
    private ObservableList<Bagage> data = FXCollections.observableArrayList(),
            datafilter = FXCollections.observableArrayList();
    @FXML
    private Label surname_label, name_label, airport_label, label_label,
            flight_label, destination_label, type_label, brand_label,
            color_label, characteristics_label, picture_label, address_label,
            residence_label, zipcode_label, country_label, phone_label,
            mail_label, loginerror, language_label, popup_label;
    @FXML
    private final FYS fys = new FYS();
    @FXML
    private final taal language = new taal();
    @FXML
    private final String[] taal = language.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;
    public String filePath = null;
    public String password = FYS.encrypt(generateRandomPassword(8));

    //Methode om ingevulde data van vermiste bagage naar de database te sturen
    //Wanneer er op de knop Verzenden wordt geklikt
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        getLuggageData();

        //Controleren of alles wat ingevuld moet worden is ingevuld
        if ((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (airport_combo.getValue() == null) || (address_input.getText() == null
                || address_input.getText().trim().isEmpty()) || (residence_input.getText() == null
                || residence_input.getText().trim().isEmpty()) || (zipcode_input.getText() == null
                || zipcode_input.getText().trim().isEmpty()) || (mail_input.getText() == null
                || mail_input.getText().trim().isEmpty()) || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_combo.getValue() == null) || (language_combo.getValue() == null)) {
            //Indien niet alles correct is ingevuld foutmelding geven
            loginerror.setVisible(false);
            loginerror.setText(taal[93]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
        } else if (!fys.isValidEmailAddress(mail_input.getText())) {
            //Indien het ingevulde emailadres al in de database bestaat foutmelding geven
            loginerror.setVisible(false);
            loginerror.setText(taal[159]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
        } else if (fys.checkEmailExists(mail_input.getText())) {
            //Indien het ingevulde emailadres al in de database bestaat foutmelding geven
            loginerror.setVisible(false);
            loginerror.setText(taal[121]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
        } else if (fys.checkFound(
                fys.getBaggageTypeString(type_combo.getValue().toString()),
                brand_input.getText(),
                fys.getColorString(color_combo.getValue().toString()))) {
            popup.setVisible(true);
            formulier.setDisable(true);

            int count = fys.countFound(fys.getBaggageTypeString(type_combo.getValue().toString()), brand_input.getText(), fys.getColorString(color_combo.getValue().toString()));
            if (count == 1) {
                textinfo.setText("Er is " + count + " gevonden bagagestuk met dezelfde kenmerken gevonden\n"
                        + "als wat er net is ingevuld.\n"
                        + "\n"
                        + "Hieronder staat alle informatie over dit al opgegeven gevonden\n"
                        + "bagagestuk. Je kan jouw ingevulde bagagestuk annuleren of\n"
                        + "toch verzenden.");
            } else {
                textinfo.setText("Er zijn " + count + " gevonden bagagestukken met dezelfde kenmerken gevonden\n"
                        + "als wat er net is ingevuld.\n"
                        + "\n"
                        + "Hieronder staat alle informatie over deze al opgegeven gevonden\n"
                        + "bagagestukken. Je kan jouw ingevulde bagagestuk annuleren of\n"
                        + "toch verzenden.");
            }
        } else {
            loginerror.setVisible(false);
            loginerror.setText(taal[124]);
            loginerror.setStyle("-fx-text-fill: green;");
            loginerror.setVisible(true);

            //Huidige datum en tijd opslaan
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            String dateTimeString = dateFormat.format(date);
            //Datum en tijd van elkaar splitsen als aparte variabelen
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

            //Alle ingevulde gegevens naar de database versturen
            sendToDatabase(airport_combo.getValue().toString(), name_input.getText(),
                    surname_input.getText(), address_input.getText(), residence_input.getText(),
                    zipcode_input.getText(), country_input.getText(), phone_input.getText(),
                    mail_input.getText(), labelnumber_input.getText(), filePath,
                    flightnumber_input.getText(), destination,
                    fys.getBaggageTypeString(type_combo.getValue().toString()),
                    brand_input.getText(), fys.getColorString(color_combo.getValue().toString()),
                    characteristics_input.getText(), dateString, timeString, password);
        }
    }

    public void getLuggageData() {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT found.*, "
                    + "person.first_name, person.surname FROM found, person "
                    + "WHERE found.person_id = person.person_id "
                    + "AND found.type='" + fys.getBaggageTypeString(type_combo.getValue().toString()) + "' "
                    + "AND found.brand = '" + brand_input.getText() + "' "
                    + "AND found.color = '" + fys.getColorString(color_combo.getValue().toString()) + "';";
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

    //Methode om alle ingevulde gegevens naar de database te versturen
    private void sendToDatabase(String airport, String frontname, String surname,
            String address, String residence, String zipcode, String country,
            String phone, String mail, String labelnumber, String filePath,
            String flightnumber, String destination, int type, String brand,
            Integer color, String characteristics, String date, String time,
            String password)
            throws IOException, SQLException {

        try {
            int pageid = 2;
            String passwordDecrypted = "";
            int type_email = 0;
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //Query om klant toe te voegen aan de database
            String sql_person = "INSERT INTO bagagedatabase.person (type, language, "
                    + "first_name, surname, address, residence, zip_code, country, "
                    + "phone, mail, password) VALUES ('0', '"
                    + fys.getUserLanguageString(language_combo.getSelectionModel().getSelectedItem().toString())
                    + "', '" + frontname + "', "
                    + "'" + surname + "', '" + address + "', '" + residence + "', "
                    + "'" + zipcode + "', '" + country + "', '" + phone + "', "
                    + "'" + mail + "', '" + password + "')";
            stmt.executeUpdate(sql_person);

            //Query om gegevens van de luchthaven toe te voegen aan de database
            String sql_airport = "INSERT INTO bagagedatabase.airport (date, "
                    + "time, airport_lost, label_number, flight_number, destination) "
                    + "VALUES ('" + date + "', '" + time + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";
            stmt.executeUpdate(sql_airport);

            /*Query om de ID van de klant en luchthaven op te vragen zodat deze
            later als foreign key kunnen worden gebruikt*/
            String sql_personID = "SELECT person_id, lost_and_found_id FROM person, airport WHERE "
                    + "person.first_name = '" + frontname + "'AND person.surname = '" + surname + "' "
                    + "AND person.address = '" + address + "' AND person.residence = '" + residence + "' "
                    + "AND person.zip_code = '" + zipcode + "' AND person.country = '" + country + "' "
                    + "AND person.phone = '" + phone + "' AND person.mail = '" + mail + "' "
                    + "AND airport.date = '" + date + "' AND airport.time = '" + time + "' "
                    + "AND airport.airport_lost = '" + airport + "' AND airport.label_number = '"
                    + labelnumber + "' AND airport.flight_number = '" + flightnumber + "' "
                    + "AND airport.destination = '" + destination + "'";

            //personId en lost_and_foundId opslaan als variabelen
            try (ResultSet id_rs = stmt.executeQuery(sql_personID)) {
                String personIdStr = null, lostAndFoundIdStr = null;
                int personId = -1, lostAndFoundId = -1;
                while (id_rs.next()) {
                    String strA = id_rs.getString("person_id");
                    personIdStr = strA.replace("\n", ",");
                    personId = Integer.parseInt(personIdStr);

                    String strB = id_rs.getString("lost_and_found_id");
                    lostAndFoundIdStr = strB.replace("\n", ",");
                    lostAndFoundId = Integer.parseInt(lostAndFoundIdStr);
                }   //De gegevens van de verloren bagage toe voegen aan de database
                String sql_lost = "INSERT INTO bagagedatabase.lost (type, brand, color, "
                        + "characteristics, status, picture, person_id, lost_and_found_id) VALUES ('" + type + "', "
                        + "'" + brand + "', '" + color + "', '" + characteristics + "', 1, "
                        + "'" + filePath + "', '" + personId + "', '" + lostAndFoundId + "')";
                stmt.executeUpdate(sql_lost);
            }

            // Email bericht filteren op sommige woorden.            
            String getmessage = fys.replaceEmail(fys.Email_Message(type_email, fys.getUserLanguageString(language_combo.getValue().toString()), pageid), mail_input.getText());
            // Email versturen
            fys.sendEmail(mail_input.getText(), fys.Email_Subject(type_email, fys.getUserLanguageString(language_combo.getValue().toString()), pageid), getmessage, "Sent message successfully....");

            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        fys.changeToAnotherFXML(taal[95], "bagageformulieren.fxml");
    }

    @FXML
    public void handleCancelBut(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[95], "bagageformulieren.fxml");
    }

    @FXML
    public void handleVerzendenBut(ActionEvent event) throws IOException, SQLException {
        loginerror.setVisible(false);
        loginerror.setText(taal[124]);
        loginerror.setStyle("-fx-text-fill: green;");
        loginerror.setVisible(true);

        //Huidige datum en tijd opslaan
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateTimeString = dateFormat.format(date);
        //Datum en tijd van elkaar splitsen als aparte variabelen
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

        //Alle ingevulde gegevens naar de database versturen
        sendToDatabase(airport_combo.getValue().toString(), name_input.getText(),
                surname_input.getText(), address_input.getText(), residence_input.getText(),
                zipcode_input.getText(), country_input.getText(), phone_input.getText(),
                mail_input.getText(), labelnumber_input.getText(), filePath,
                flightnumber_input.getText(), destination,
                fys.getBaggageTypeString(type_combo.getValue().toString()),
                brand_input.getText(), fys.getColorString(color_combo.getValue().toString()),
                characteristics_input.getText(), dateString, timeString, password);
    }

    //Fileselector aanroepen wanneer iemand een afbeelding wilt toevoegen
    @FXML
    public void handleFileSelector(ActionEvent event) throws IOException {
        File file = fys.fileChooser();
        //String fileRaw = file.getAbsolutePath();
        filePath = "/fys/src/fys/luggageImages/" + file.getName();

        //filePath = fileRaw.replace("\\","\\\\");
        //System.out.println(filePath);
        picture_button.setText(file.getName());
    }

    //Methode om alle labels op de pagina in de ingestelde taal te zetten
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        airport_label.setText(taal[8] + ":");
        name_label.setText(taal[9] + ":");
        surname_label.setText(taal[10] + ":");
        address_label.setText(taal[11] + ":");
        residence_label.setText(taal[12] + ":");
        zipcode_label.setText(taal[13] + ":");
        country_label.setText(taal[14] + ":");
        phone_label.setText(taal[15] + ":");
        mail_label.setText(taal[16] + ":");
        label_label.setText(taal[17] + ":");
        flight_label.setText(taal[18] + ":");
        destination_label.setText(taal[19] + ":");
        type_label.setText(taal[20] + ":");
        brand_label.setText(taal[21] + ":");
        color_label.setText(taal[22] + ":");
        characteristics_label.setText(taal[23] + ":");
        picture_label.setText(taal[24] + ":");
        language_label.setText(taal[68] + ":");
        language_combo.getItems().addAll(
                taal[69], taal[70], taal[71], taal[72], taal[165]);

        airport_combo.setPromptText(taal[25]);
        destination_combo.setPromptText(taal[25]);
        language_combo.setPromptText(taal[164]);
        color_combo.setPromptText(taal[31]);
        color_combo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        type_combo.setPromptText(taal[26]);
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
}
