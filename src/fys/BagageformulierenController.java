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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class BagageformulierenController implements Initializable {

    //Alle inputvelden initialiseren
    @FXML
    private ComboBox airport_combo, type_combo, color_combo, destination_combo;
    @FXML
    private TextField name_input, surname_input, address_input, residence_input,
            zipcode_input, country_input, phone_input, mail_input, labelnumber_input,
            flightnumber_input, brand_input, characteristics_input;
    @FXML
    private Button picture_button, send_button;
    @FXML
    private Label surname_label, name_label, airport_label, label_label,
            flight_label, destination_label, type_label, brand_label, color_label,
            characteristics_label, picture_label, address_label, residence_label,
            zipcode_label, country_label, phone_label, mail_label, loginerror;
    @FXML
    private FYS fys = new FYS();
    @FXML
    private taal language = new taal();
    @FXML
    private String[] taal = language.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;
    public String filePath = null;

    //Methode om ingevulde data van virmiste bagage naar de database te sturen
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        String password = fys.encrypt(generateRandomPassword(8));
        String[] mailInformation = new String[3];
        int[] language_user = new int[1];
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();

        //Controleren of alles wat ingevuld moet worden is ingevuld
        if ((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (airport_combo.getValue() == null) || (address_input.getText() == null
                || address_input.getText().trim().isEmpty()) || (residence_input.getText() == null
                || residence_input.getText().trim().isEmpty()) || (zipcode_input.getText() == null
                || zipcode_input.getText().trim().isEmpty()) || (mail_input.getText() == null
                || mail_input.getText().trim().isEmpty()) || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_combo.getValue() == null)) {
            //Indien niet alles correct is ingevuld foutmelding geven
            loginerror.setVisible(false);
            loginerror.setText(taal[93]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
        } else if (fys.checkEmailExists(mail_input.getText())) {
            //Indien het ingevulde emailadres al in de database bestaat foutmelding geven
            loginerror.setVisible(false);
            loginerror.setText(taal[121]);
            loginerror.setStyle("-fx-text-fill: red;");
            loginerror.setVisible(true);
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
            if(destination_combo.getValue() == null){
                destination = " ";
            } else{
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

            try {
                //Mail sturen naar klant met zijn/haar inloggegevens
                String sql = "SELECT type, language, first_name, surname, "
                        + "password FROM person WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    mailInformation[0] = rs.getString("first_name").substring(0, 1).toUpperCase()
                            + rs.getString("first_name").substring(1);
                    mailInformation[1] = rs.getString("surname").substring(0, 1).toUpperCase()
                            + rs.getString("surname").substring(1);
                    mailInformation[2] = fys.decrypt(rs.getString("password"));
                    language_user[0] = rs.getInt("language");
                }
                rs.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            if (language_user[0] == 0) { // Mail voor klant (type = 0)
                fys.sendEmail(mail_input.getText(), "Corendon - Logindata", "Dear valued customer, "
                        + "<br><br>There is an account created for you by one of our employees."
                        + "<br>You can login to this account on our web application to view the status of your case."
                        + "<br>You will need the following data to log in:"
                        + "<br>Username: <i>" + mail_input.getText()
                        + "</i><br>Password: <i>" + mailInformation[2]
                        + "</i><br><br>You can change your password in the web application."
                        + "<br>We hope to have informed you sufficiently."
                        + "<br><br>Sincerely,"
                        + "<br><br><b>The Corendon Team</b>", "Sent message successfully....");
            }
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
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //Query om klant toe te voegen aan de database
            String sql_person = "INSERT INTO bagagedatabase.person (type, language, "
                    + "first_name, surname, address, residence, zip_code, country, "
                    + "phone, mail, password) VALUES ('0', '0', '" + frontname + "', "
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

            ResultSet id_rs = stmt.executeQuery(sql_personID);
            //personId en lost_and_foundId opslaan als variabelen
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

            //De gegevens van de verloren bagage toe voegen aan de database
            String sql_lost = "INSERT INTO bagagedatabase.lost (type, brand, color, "
                    + "characteristics, status, picture, person_id, lost_and_found_id) VALUES ('" + type + "', "
                    + "'" + brand + "', '" + color + "', '" + characteristics + "', 1, "
                    + "'" + filePath + "', '" + personId + "', '" + lostAndFoundId + "')";
            stmt.executeUpdate(sql_lost);

            id_rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
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
        
        // WERKT NIET
        airport_combo.setPromptText(taal[25]);   
        color_combo.setPromptText(taal[31]);
        color_combo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        type_combo.setPromptText(taal[26]);
        type_combo.getItems().addAll(taal[29], taal[27], taal[30], taal[125], taal[28]);
        picture_button.setText(taal[44]);
        send_button.setText(taal[46]);
    }
}