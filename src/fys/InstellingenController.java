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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class InstellingenController implements Initializable {

    private Integer id;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ComboBox language;
    @FXML private Button save;
    @FXML private Label error;
    @FXML private Text email_label, password_label, language_label;
    @FXML private final taal languages = new taal();
    @FXML private String[] taal = languages.getLanguage();
    @FXML private final FYS fys = new FYS();
    @FXML private final loginController login = new loginController();
    @FXML private Statement stmt = null;
    @FXML private Connection conn = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Zet alle labels, buttons en combobox op de instellingen pagina naar de taal die is ingesteld. 
        email_label.setText(taal[91] + ":");
        password_label.setText(taal[90] + ":");
        language_label.setText(taal[68] + ":");
        save.setText(taal[92]);
        save.setDefaultButton(true);
        language.getItems().addAll(
                taal[69],taal[70], taal[71], taal[72]);
        
        //Krijg de gegevens van de gebruiker die ingelogd is.
        //Vul de textfields in met gegevens die zijn opgehaald.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT person_id, mail, password, language FROM person WHERE type = '" + login.getUsertype() + "' AND mail='" + login.getEmail() + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    id = rs.getInt("person_id");
                    username.setText(rs.getString("mail"));
                    password.setText(FYS.decrypt(rs.getString("password")));
                    language.getSelectionModel().select(rs.getInt("language"));
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    @FXML
    private void handleSaveAction(ActionEvent event) throws IOException {
        
        //Als gebruikersnaam en wachtwoord leeg is laat een error zien.
        if((username.getText() == null || username.getText().trim().isEmpty()) || (password.getText() == null || password.getText().trim().isEmpty())){
            error.setText(taal[93]);
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        //Als de gebruikersnaam al bestaat laat een error zien.
        } else if(fys.checkEmailExistsOnChange(username.getText(), login.getEmail())){
            error.setText(taal[94]);
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        //Als de emailadres niet geldig is volgens de regels dan wordt er een error getoond.
        } else if(!FYS.isValidEmailAddress(username.getText())){
            error.setText("E-mailadres is niet geldig!");
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        //Anders update de gegevens in de database.    
        } else{
            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                String sql = "UPDATE person SET mail = '" + username.getText()
                        + "', password = '" + FYS.encrypt(password.getText()) + "', language = '"
                        + fys.getUserLanguageString(language.getSelectionModel().getSelectedItem().toString())
                        + "' WHERE person_id = " + id + ";";
                stmt.executeUpdate(sql);
                conn.close();
                login.setEmail(username.getText());
                languages.setLanguage(fys.getUserLanguageString(language.getSelectionModel().getSelectedItem().toString()));
                taal = languages.getLanguage();
                
                //Geef een melding in de taal die is geinstalleerd dat de gegevens zijn aangepast.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(taal[122]);
                alert.setTitle(taal[122]);
                alert.setContentText(taal[123]);
                alert.showAndWait();
                fys.changeToAnotherFXML(taal[102], "instellingen.fxml");
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }
}