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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        save.setDefaultButton(true);
        language.getItems().addAll(
                "Engels","Nederlands", "Turks", "Spaans");
        FYS fys = new FYS();
        loginController login = new loginController();
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT person_id, mail, password, language FROM person_table WHERE type = '" + login.getUsertype() + "' AND mail='" + login.getEmail() + "'";
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
        FYS fys = new FYS();
        loginController login = new loginController();
        if((username.getText() == null || username.getText().trim().isEmpty()) || (password.getText() == null || password.getText().trim().isEmpty())){
            error.setText("Velden zijn leeggelaten!");
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        } else if(fys.checkEmailExistsOnChange(username.getText(), login.getEmail())){
            error.setText("E-mailadres bestaat al!");
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        } /*else if(!fys.isValidEmailAddress(username.getText())){
            error.setText("E-mailadres is niet geldig!");
            error.setStyle("-fx-text-fill: red;");
            error.setVisible(true);
        }*/ else{
            Statement stmt = null;
            Connection conn = null;
            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                String sql = "UPDATE person_table SET mail = '" + username.getText()
                        + "', password = '" + fys.encrypt(password.getText()) + "', language = '"
                        + fys.getUserLanguageString(language.getSelectionModel().getSelectedItem().toString())
                        + "' WHERE person_id = " + id + ";";
                stmt.executeUpdate(sql);
                conn.close();
                error.setText("Uw gegevens zijn gewijzigd!");
                error.setStyle("-fx-text-fill: green;");
                error.setVisible(true);
                login.setEmail(username.getText());
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }
}