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
    private void handleAction(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        String password = fys.encrypt(generateRandomPassword(8));
        taal languages = new taal();
        String[] taal = languages.getLanguage();
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
            loginerror.setText(taal[93]);
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
            int[] language = new int[1];
            int[] type = new int[1];
            
            try {
                //connectToDatabase(conn, stmt, "test", "root", "root");
                String sql = "SELECT type, language, first_name, surname, password FROM person_table WHERE mail='" + mail_input.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    mailInformation[0] = rs.getString("first_name").substring(0, 1).toUpperCase() + rs.getString("first_name").substring(1);
                    mailInformation[1] = rs.getString("surname").substring(0, 1).toUpperCase() + rs.getString("surname").substring(1);
                    mailInformation[2] = fys.decrypt(rs.getString("password"));
                    type[0] = rs.getInt("type");
                    language[0] = rs.getInt("language");
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

            if (language[0] == 0) { // English emails
                switch (type[0]) {
                    case 0:
                        // Mail voor klant (type = 0)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindata", "Valued customer, "
                                + "<br><br>There is an account created for you by one of our employees."
                                + "<br>You can log in to this account on our web application to view the status of your case."
                                + "<br>You will need the following data to log in:"
                                + "<br>Username: <i>" + mail_input.getText()
                                + "</i><br>Password: <i>" + mailInformation[2]
                                + "</i><br><br>You can change your password in the web application."
                                + "<br>We hope to have informed you sufficiently."
                                + "<br><br>Sincerely,"
                                + "<br><br><b>The Corendon Team</b>", "Sent message successfully....");
                        break;
                    case 1:
                        // Mail voor servicemedewerker (type = 1)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindata", "Valued "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>One of your colleagues created an account for you as 'Service Employee'."
                                + "<br>You can log in to this account in the baggage system."
                                + "<br>You will need the following data to log in:"
                                + "<br>Username: <i>" + mail_input.getText()
                                + "</i><br>Password: <i>" + mailInformation[2]
                                + "</i><br><br>You can change your password in the application."
                                + "<br>We hope to have informed you sufficiently."
                                + "<br><br>Sincerely,"
                                + "<br><br><b>The Corendon Team</b>", "Sent message successfully....");
                        break;
                    default:
                        // Mail voor administrator (type = 2)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindata", "Valued "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>One of your colleagues created an account for you as 'Administrator'."
                                + "<br>You can log in to this account in the baggage system."
                                + "<br>You will need the following data to log in:"
                                + "<br>Username: <i>" + mail_input.getText()
                                + "</i><br>Password: <i>" + mailInformation[2]
                                + "</i><br><br>You can change your password in the application."
                                + "<br>We hope to have informed you sufficiently."
                                + "<br><br>Sincerely,"
                                + "<br><br><b>The Corendon Team</b>", "Sent message successfully....");
                        break;
                }
            } else if (language[0] == 1) { // Dutch emails
                switch (type[0]) {
                    case 0:
                        // Mail voor klant (type = 0)
                        fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Gewaardeerde klant, "
                                + "<br><br>Er is door een van onze medewerkers een account voor u aangemaakt."
                                + "<br>U kunt met dit account inloggen op onze webapplicatie om de status van uw koffer te bekijken."
                                + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                                + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                                + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                                + "</i><br><br>U kunt uw wachtwoord wijzigen in de webapplicatie."
                                + "<br>Wij hopen u hiermee voldoende te hebben geïnformeerd."
                                + "<br><br>Met vriendelijke groet,"
                                + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
                        break;
                    case 1:
                        // Mail voor servicemedewerker (type = 1)
                        fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Beste "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Er is door een van uw collega's een account voor u aangemaakt als 'Servicemedewerker'."
                                + "<br>U kunt met dit account inloggen in het bagage systeem."
                                + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                                + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                                + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                                + "</i><br><br>U kunt uw wachtwoord wijzigen in de applicatie."
                                + "<br>Wij hopen u hiermee voldoende te hebben geïnformeerd."
                                + "<br><br>Met vriendelijke groet,"
                                + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
                        break;
                    default:
                        // Mail voor administrator (type = 2)
                        fys.sendEmail(mail_input.getText(), "Corendon - Inloggegevens", "Beste "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Er is door een van uw collega's een account voor u aangemaakt als 'Administrator'."
                                + "<br>U kunt met dit account inloggen in het bagage systeem."
                                + "<br>U heeft de volgende gegevens nodig om in te kunnen loggen:"
                                + "<br>Gebruikersnaam: <i>" + mail_input.getText()
                                + "</i><br>Wachtwoord: <i>" + mailInformation[2]
                                + "</i><br><br>U kunt uw wachtwoord wijzigen in de applicatie."
                                + "<br>Wij hopen u hiermee voldoende te hebben geïnformeerd."
                                + "<br><br>Met vriendelijke groet,"
                                + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
                        break;
                }
            } else if (language[0] == 2) { // Spanish emails
                switch (type[0]) {
                    case 0:
                        // Mail voor klant (type = 0)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindatos", "Estimado cliente, "
                                + "<br><br>Hay una cuenta creada para usted por uno de nuestros empleados."
                                + "<br>Puede iniciar sesión con la cuenta en nuestra aplicación web para ver el estado de su caso."
                                + "<br>Necesitará la siguiente información para iniciar sesión:"
                                + "<br>Nombre de usuario: <i>" + mail_input.getText()
                                + "</i><br>Contraseña: <i>" + mailInformation[2]
                                + "</i><br><br>Puede cambiar su contraseña en la aplicación web."
                                + "<br>Esperamos que te han informado lo suficiente."
                                + "<br><br>Atentamente,"
                                + "<br><br><b>El equipo de Corendon</b>", "Sent message successfully....");
                        break;
                    case 1:
                        // Mail voor servicemedewerker (type = 1)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindatos", "Mejor "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Hay uno de sus colegas crearon una cuenta para usted como 'Servicio del empleado'."
                                + "<br>Puede iniciar sesión en la cuenta en el sistema de equipajes."
                                + "<br>Necesitará la siguiente información para iniciar sesión:"
                                + "<br>Nombre de usuario: <i>" + mail_input.getText()
                                + "</i><br>Contraseña: <i>" + mailInformation[2]
                                + "</i><br><br>Puede cambiar su contraseña en la aplicación."
                                + "<br>Esperamos que te han informado lo suficiente."
                                + "<br><br>Atentamente,"
                                + "<br><br><b>El equipo de Corendon</b>", "Sent message successfully....");
                        break;
                    default:
                        // Mail voor administrator (type = 2)
                        fys.sendEmail(mail_input.getText(), "Corendon - Logindatos", "Mejor "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Hay uno de sus colegas crearon una cuenta para usted como un 'Administrador'."
                                + "<br>Puede iniciar sesión en la cuenta en el sistema de equipajes."
                                + "<br>Necesitará la siguiente información para iniciar sesión:"
                                + "<br>Nombre de usuario: <i>" + mail_input.getText()
                                + "</i><br>Contraseña: <i>" + mailInformation[2]
                                + "</i><br><br>Puede cambiar su contraseña en la aplicación."
                                + "<br>Esperamos que te han informado lo suficiente."
                                + "<br><br>Atentamente,"
                                + "<br><br><b>El equipo de Corendon</b>", "Sent message successfully....");
                        break;
                }
            } else { // Turkisch emails
                switch (type[0]) {
                    case 0:
                        // Mail voor klant (type = 0)
                        fys.sendEmail(mail_input.getText(), "Corendon - Giriş", "Değerli müşterimiz, "
                                + "<br><br>Çalışanlarımızın biri tarafından sizin için oluşturulan bir hesap vardır."
                                + "<br>Sen davanın durumunu görüntülemek için web uygulamasında bu hesaba giriş yapabilirsiniz."
                                + "<br>Oturum açmak için aşağıdaki bilgilere ihtiyacınız olacaktır:"
                                + "<br>Kullanıcı adı: <i>" + mail_input.getText()
                                + "</i><br>Şifre: <i>" + mailInformation[2]
                                + "</i><br><br>Bu web uygulamasında şifrenizi değiştirebilirsiniz."
                                + "<br>Biz yeterince sizi haberdar etmek istedik."
                                + "<br><br>Saygılarımızla,"
                                + "<br><br><b>Corendon Takımı</b>", "Sent message successfully....");
                        break;
                    case 1:
                        // Mail voor servicemedewerker (type = 1)
                        fys.sendEmail(mail_input.getText(), "Corendon - Giriş", "En iyi "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Senin meslektaşlarımdan biri 'Hizmet Çalışan' olarak sizin için bir hesap oluşturmuş var."
                                + "<br>Sen bagaj sisteminde bu hesaba giriş yapabilirsiniz."
                                + "<br>Oturum açmak için aşağıdaki bilgilere ihtiyacınız olacaktır:"
                                + "<br>Kullanıcı adı: <i>" + mail_input.getText()
                                + "</i><br>Şifre: <i>" + mailInformation[2]
                                + "</i><br><br>Sen uygulamasına şifrenizi değiştirebilirsiniz."
                                + "<br>Biz yeterince sizi haberdar etmek istedik."
                                + "<br><br>Saygılarımızla,"
                                + "<br><br><b>Corendon Takımı</b>", "Sent message successfully....");
                        break;
                    default:
                        // Mail voor administrator (type = 2)
                        fys.sendEmail(mail_input.getText(), "Corendon - Giriş", "En iyi "
                                + mailInformation[0] + " " + mailInformation[1] + ", "
                                + "<br><br>Senin meslektaşlarımdan biri, bir 'Yönetici' olarak sizin için bir hesap oluşturmuş var."
                                + "<br>Sen bagaj sisteminde bu hesaba giriş yapabilirsiniz."
                                + "<br>Oturum açmak için aşağıdaki bilgilere ihtiyacınız olacaktır:"
                                + "<br>Kullanıcı adı: <i>" + mail_input.getText()
                                + "</i><br>Şifre: <i>" + mailInformation[2]
                                + "</i><br><br>Sen uygulamasına şifrenizi değiştirebilirsiniz."
                                + "<br>Biz yeterince sizi haberdar etmek istedik."
                                + "<br><br>Saygılarımızla,"
                                + "<br><br><b>Corendon Takımı</b>", "Sent message successfully....");
                        break;
                }
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
        taal language = new taal();
        String[] taal = language.getLanguage();
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
