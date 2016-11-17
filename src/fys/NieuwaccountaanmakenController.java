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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Veron
 */

// TO DO: BUTTON DONE LINKJE ETC
// TO DO: EMAIL MET PASSWORD VERSTUREN
public class NieuwaccountaanmakenController implements Initializable {

    @FXML private TextField name_input;
    @FXML private TextField surname_input;
    @FXML private TextField address_input;
    @FXML private TextField residence_input;
    @FXML private TextField zipcode_input;
    @FXML private TextField country_input;
    @FXML private TextField phone_input;
    @FXML private TextField mail_input;
    @FXML private ComboBox language_combo;
    @FXML private ComboBox type_combo;
    @FXML private Label loginerror;

    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();

        if ((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zipcode_input.getText() == null || zipcode_input.getText().trim().isEmpty())
                || (country_input.getText() == null || country_input.getText().trim().isEmpty())
                || (phone_input.getText() == null || phone_input.getText().trim().isEmpty())
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (language_combo.getValue() == null)
                || (type_combo.getValue() == null)) 
        {
            // Foutmelding
            loginerror.setText("U heeft niet alles ingevuld!");
            loginerror.setVisible(true);
            
        } else {
            sendToDatabase(name_input.getText(), surname_input.getText(),
                    address_input.getText(), residence_input.getText(), zipcode_input.getText(),
                    country_input.getText(), phone_input.getText(), mail_input.getText(),
                    language_combo.getValue().toString(), type_combo.getValue().toString()
                   );
        }
    }

    @FXML
    private void sendToDatabase(String firstname, String surname, String address, String residence, String zipcode,
            String country, String phone, String mail, String language, String type) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_account = "INSERT INTO bagagedatabase.person_table (type, mail,"
                    + "language, first_name, surname, address, residence, "
                    + "zip_code, country, phone) VALUES ('" + type + "', '" + mail + "', '" + language + "', '" + firstname + "', "
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
        // TODO
    }
}
