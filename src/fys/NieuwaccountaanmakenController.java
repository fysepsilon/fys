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
    private TextField name_input, surname_input, address_input, residence_input,
            zipcode_input, country_input, phone_input, mail_input;
    @FXML
    private ComboBox language_combo, type_combo;
    @FXML
    private Label loginerror, surname_label, name_label, type_label, address_label, residence_label,
            zipcode_label, country_label, phone_label, mail_label, language_label;
    @FXML
    private Button SendNewAccount;
    @FXML
    private taal language = new taal();
    @FXML
    private String[] taal = language.getLanguage();
    @FXML
    private FYS fys = new FYS();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;
    @FXML
    private loginController loginController = new loginController();

    @FXML
    private void handleAction(ActionEvent event) throws IOException, SQLException {
        String password = fys.encrypt(generateRandomPassword(8));
        String email = "";

        loginController loginController = new loginController();

        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_email = "SELECT mail FROM person";
            ResultSet rs = stmt.executeQuery(sql_email);
            while (rs.next()) {
                //Retrieve by column name
                email = rs.getString("mail");
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
            loginerror.setText(taal[93]);
            loginerror.setVisible(true);

        } else if (fys.checkEmailExists(mail_input.getText())) {
            //Foutmelding
            loginerror.setText(taal[121]);
            loginerror.setVisible(true);
        } else {
            sendToDatabase_type(name_input.getText(), surname_input.getText(),
                    address_input.getText(), residence_input.getText(), zipcode_input.getText(),
                    country_input.getText(), phone_input.getText(), mail_input.getText(),
                    password, language_combo.getValue().toString(), type_combo.getValue().toString()
            );

            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                //connectToDatabase(conn, stmt, "test", "root", "root");

                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            String[] mailInformation = new String[3];
            int[] language = new int[1];
            int[] mailidOphalen = new int[1];
            String[] mailOphalen = new String[2];
            int[] type = new int[1];

            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                //connectToDatabase(conn, stmt, "test", "root", "root");

                String sql = "SELECT mail FROM person WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    email = rs.getString("mail");
                    //Display values
                    //System.out.print("username: " + email);
                }
                rs.close();

                String sql2 = "SELECT type, language, first_name, surname, password FROM person WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs2 = stmt.executeQuery(sql2);
                while (rs2.next()) {
                    //Retrieve by column name
                    mailInformation[0] = rs2.getString("first_name").substring(0, 1).toUpperCase() + rs2.getString("first_name").substring(1);
                    mailInformation[1] = rs2.getString("surname").substring(0, 1).toUpperCase() + rs2.getString("surname").substring(1);
                    mailInformation[2] = fys.decrypt(rs2.getString("password"));
                    type[0] = rs2.getInt("type");
                    language[0] = rs2.getInt("language");
                    //Display values
                    //System.out.print("username: " + email);
                }
                rs2.close();

                String sql3 = "SELECT mailid, subject, message FROM mail";
                ResultSet rs3 = stmt.executeQuery(sql3);
                while (rs3.next()) {
                    //Retrieve by column name
                    mailidOphalen[0] = rs3.getInt("mailid");
                    mailOphalen[0] = rs3.getString("subject").substring(0, 1).toUpperCase() + rs3.getString("subject").substring(1);
                    mailOphalen[1] = rs3.getString("message").substring(0, 1).toUpperCase() + rs3.getString("message").substring(1);

                    // Replacen in email
                    String getmessage = fys.replaceEmail(mailOphalen[1], mail_input.getText(), mailInformation[2], mailInformation[0], mailInformation[1]);

                    if (language[0] == 0) { // English emails
                        switch (type[0]) {
                            case 0: // Mail voor klant (type = 0)
                                if (mailidOphalen[0] == 1) { // Mailid = 1
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            case 1: // Mail voor servicemedewerker (type = 1)
                                if (mailidOphalen[0] == 2) { // Mailid = 2
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            default:
                                // Mail voor administrator (type = 2)
                                if (mailidOphalen[0] == 3) { // Mailid = 3
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                        }
                    } else if (language[0] == 1) { // Dutch emails
                        switch (type[0]) {
                            case 0:
                                // Mail voor klant (type = 0)
                                if (mailidOphalen[0] == 4) { // Mailid = 4
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            case 1:
                                // Mail voor servicemedewerker (type = 1)
                                if (mailidOphalen[0] == 5) { // Mailid = 5
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            default:
                                // Mail voor administrator (type = 2)
                                if (mailidOphalen[0] == 6) { // Mailid = 6
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                        }
                    } else if (language[0] == 2) { // Spanish emails
                        switch (type[0]) {
                            case 0:
                                // Mail voor klant (type = 0)
                                if (mailidOphalen[0] == 7) { // Mailid = 7
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            case 1:
                                // Mail voor servicemedewerker (type = 1)
                                if (mailidOphalen[0] == 8) { // Mailid = 8
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            default:
                                // Mail voor administrator (type = 2)
                                if (mailidOphalen[0] == 9) { // Mailid = 9
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                        }
                    } else { // Turkisch emails
                        switch (type[0]) {
                            case 0:
                                // Mail voor klant (type = 0)
                                if (mailidOphalen[0] == 10) { // Mailid = 10
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            case 1:
                                // Mail voor servicemedewerker (type = 1)
                                if (mailidOphalen[0] == 11) { // Mailid = 11
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                            default:
                                // Mail voor administrator (type = 2)
                                if (mailidOphalen[0] == 12) { // Mailid = 12
                                    fys.sendEmail(mail_input.getText(), mailOphalen[0], getmessage, "Sent message successfully....");
                                }
                                break;
                        }
                    }
                }
                rs3.close();
                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            loginerror.setText(taal[103]);
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
    private void handleCancel(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[98], "accounts.fxml");
    }

    @FXML
    private void sendToDatabase_type(String firstname, String surname, String address, String residence, String zipcode,
            String country, String phone, String mail, String password, String language, String type) throws IOException, SQLException {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            type = fys.getUserFunctionString(type).toString();
            language = fys.getUserLanguageString(language).toString();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_account = "INSERT INTO bagagedatabase.person (type, mail,"
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

    @FXML
    private void sendToDatabase(String firstname, String surname, String address, String residence, String zipcode,
            String country, String phone, String mail, String password, String language) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            language = fys.getUserLanguageString(language).toString();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_account = "INSERT INTO bagagedatabase.person_table (type, mail,"
                    + "password, language, first_name, surname, address, residence, "
                    + "zip_code, country, phone) VALUES ('0', '" + mail + "', '" + password + "', '" + language + "', '" + firstname + "', "
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
        taal language = new taal();
        String[] taal = language.getLanguage();
        loginController loginController = new loginController();

        name_label.setText(taal[9] + ":");
        surname_label.setText(taal[10] + ":");
        address_label.setText(taal[11] + ":");
        residence_label.setText(taal[12] + ":");
        zipcode_label.setText(taal[13] + ":");
        country_label.setText(taal[14] + ":");
        phone_label.setText(taal[15] + ":");
        mail_label.setText(taal[16] + ":");
        type_label.setText(taal[20] + ":");
        language_label.setText(taal[68] + ":");
        SendNewAccount.setText(taal[46]);
        language_combo.setPromptText(taal[73]);

        if (loginController.getUsertype() == 1) { // Service medewerker (GEEN TYPE FUNCTIE)
            type_label.setVisible(false);
            type_combo.setVisible(false);
        }
        type_combo.setPromptText(taal[74]);
        type_combo.getItems().addAll(
                taal[66],
                taal[64],
                taal[65]);

        language_combo.getItems().addAll(
                taal[69],
                taal[70],
                taal[71],
                taal[72]);
    }
}
