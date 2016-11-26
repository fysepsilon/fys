/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import static fys.FYS.generateRandomPassword;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Veron
 */
public class NieuwaccountaanmakenController implements Initializable {

    @FXML
    private TextField name_input;
    @FXML
    private TextField surname_input;
    @FXML
    private TextField address_input;
    @FXML
    private TextField residence_input;
    @FXML
    private TextField zipcode_input;
    @FXML
    private TextField country_input;
    @FXML
    private TextField phone_input;
    @FXML
    private TextField mail_input;
    @FXML
    private ComboBox language_combo;
    @FXML
    private ComboBox type_combo;
    @FXML
    private Label loginerror;
    @FXML
    private Button SendNewAccount;

    @FXML
    private void handleAction(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        String password = fys.encrypt(generateRandomPassword(8));
        String email = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();
        
        if ((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zipcode_input.getText() == null || zipcode_input.getText().trim().isEmpty())
                || (country_input.getText() == null || country_input.getText().trim().isEmpty())
                || (phone_input.getText() == null || phone_input.getText().trim().isEmpty())
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (language_combo.getValue() == null)
                || (type_combo.getValue() == null)) {
            // Foutmelding
            loginerror.setText("U heeft niet alles ingevuld!");
            loginerror.setVisible(true);

        } else {
            sendToDatabase(name_input.getText(), surname_input.getText(),
                    address_input.getText(), residence_input.getText(), zipcode_input.getText(),
                    country_input.getText(), phone_input.getText(), mail_input.getText(),
                    password, language_combo.getValue().toString(), type_combo.getValue().toString()
            );

            try {
                //connectToDatabase(conn, stmt, "test", "root", "root");
                String sql = "SELECT mail FROM person_table WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    email = rs.getString("mail");
                    //Display values
                    //System.out.print("username: " + email);
                }
                rs.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            String[] mailInformation = new String[3];
            int[] type = new int[1];
            try {
                //connectToDatabase(conn, stmt, "test", "root", "root");
                String sql = "SELECT type, first_name, surname, password FROM person_table WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    mailInformation[0] = rs.getString("first_name").substring(0, 1).toUpperCase() + rs.getString("first_name").substring(1);
                    mailInformation[1] = rs.getString("surname").substring(0, 1).toUpperCase() + rs.getString("surname").substring(1);
                    mailInformation[2] = fys.decrypt(rs.getString("password"));
                    type[0] = rs.getInt("type");

                    //Display values
                    //System.out.print("username: " + email);
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            if (type[0] == 0) { // Mail voor klant (type = 0)
                fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Gewaardeerde klant, "
                        + "<br><br>Er is door een van onze medewerkers een account voor u aangemaakt."
                        + "<br>U kunt met dit account inloggen op onze webapplicatie om de status van uw koffer te bekijken."
                        + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                        + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                        + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                        + "</i><br><br>U kunt uw wachtwoord wijzigen in de webapplicatie."
                        + "<br>Wij hopen u hiermee voldoende te hebben geinformeerd."
                        + "<br><br>Met vriendelijke groet,"
                        + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
            } else if (type[0] == 1) { // Mail voor servicemedewerker (type = 1) 
                fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Beste "
                        + mailInformation[0] + " " + mailInformation[1] + ", "
                        + "<br><br>Er is door een van uw collega's een account voor u aangemaakt als 'Servicemedewerker'."
                        + "<br>U kunt met dit account inloggen in het bagage systeem."
                        + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                        + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                        + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                        + "</i><br><br>U kunt uw wachtwoord wijzigen in de applicatie."
                        + "<br>Wij hopen u hiermee voldoende te hebben geinformeerd."
                        + "<br><br>Met vriendelijke groet,"
                        + "<br><br><b>Het Corendon Team</b><img src='@images/corendon_logo.png'/>", "Sent message successfully....");
            } else { // Mail voor administrator (type = 2)
                fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Beste "
                        + mailInformation[0] + " " + mailInformation[1] + ", "
                        + "<br><br>Er is door een van uw collega's een account voor u aangemaakt als 'Administrator'."
                        + "<br>U kunt met dit account inloggen in het bagage systeem."
                        + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                        + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                        + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                        + "</i><br><br>U kunt uw wachtwoord wijzigen in de applicatie."
                        + "<br>Wij hopen u hiermee voldoende te hebben geinformeerd."
                        + "<br><br>Met vriendelijke groet,"
                        + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
            }
            loginerror.setText("Het account is gemaakt en de e-mail is verstuurd!");
            loginerror.setStyle("-fx-text-fill: green;");
            loginerror.setVisible(true);
            loginerror.setDisable(false);
            
            name_input.setText("");
            surname_input.setText("");
            address_input.setText("");
            residence_input.setText("");
            zipcode_input.setText("");
            country_input.setText("");
            phone_input.setText("");
            mail_input.setText("");
        }
    }

    @FXML
    private void sendToDatabase(String firstname, String surname, String address, String residence, String zipcode,
            String country, String phone, String mail, String password, String language, String type) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            
            type = fys.getUserFunctionString(type).toString();
            language = fys.getUserLanguageString(language).toString();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_account = "INSERT INTO bagagedatabase.person_table (type, mail,"
                    + "password, language, first_name, surname, address, residence, "
                    + "zip_code, country, phone) VALUES ('" + type + "', '" + mail + "', '" + password + "', '" + language + "', '" + firstname + "', "
                    + "'" + surname + "', '" + address + "', '" + residence + "', '" + zipcode + "', "
                    + "'" + country + "', '" + phone + "')";

            stmt.executeUpdate(sql_account);
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
        type_combo.getItems().addAll(
                "Klant",
                "Servicemedewerker",
                "Admin");
        language_combo.getItems().addAll(
                "Engels",
                "Nederlands",
                "Spaans",
                "Turks");
    }
}
