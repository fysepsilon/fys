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
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class WachtwoordVergetenController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Label label;
    @FXML
    private Label sendPasswordMessage;
    @FXML
    private Button sendNewPasswordButton;

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-Login", "login.fxml");
    }

    @FXML
    private void handleSendNewPasswordAction(ActionEvent event) throws IOException, SQLException {
        sendNewPasswordButton.setDisable(true);
        FYS fys = new FYS();
        String email = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();

        //Controleer of de velden gebruikersnaam of wachtwoord leeg zijn. Anders laat een error zien.
        if ((username.getText() == null || username.getText().trim().isEmpty())) {
            sendPasswordMessage.setText("Username is empty!");
            sendPasswordMessage.setStyle("-fx-text-fill: red;");
            sendPasswordMessage.setVisible(true);
            sendNewPasswordButton.setDisable(false);
        } else //Als de emailadres niet klopt volgens de regels dan wordt een error getoond.
        {
            if (FYS.isValidEmailAddress(username.getText())) {
                //Haal de mail die is ingevuld.
                try {
                    String sql = "SELECT mail FROM person WHERE mail='" + username.getText() + "' AND (type = '1' OR type = '2')";
                    try (ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            //Retrieve by column name
                            email = rs.getString("mail");
                            //Display values
                        }
                    }
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
                //Controleer of de ingevulde veld leeg is. Laat dan een error zien.
                if ((email == null || email.trim().isEmpty())) {
                    sendPasswordMessage.setText("This username unfortunately does not exists!");
                    sendPasswordMessage.setStyle("-fx-text-fill: red;");
                    sendPasswordMessage.setVisible(true);
                    sendNewPasswordButton.setDisable(false);
                } else {
                    // Stuur email als gebruiker type 1 of 2 is.
                    if (fys.Email_Persontype(username.getText()) == 1 || fys.Email_Persontype(username.getText()) == 2) {
                        // Replacen in email
                        String getmessage = fys.replaceEmail(fys.Email_Message(), username.getText());

                        if (fys.Email_Mailid() == 14) { // Mailid = 14
                            fys.sendEmail(username.getText(), fys.Email_Subject(), getmessage, "Sent message successfully....");
                        }

                        sendPasswordMessage.setText("Your password has been sent to: " + username.getText() + "!");
                        sendPasswordMessage.setStyle("-fx-text-fill: green;");
                        sendPasswordMessage.setVisible(true);
                        username.setText("");
                        sendNewPasswordButton.setDisable(false);
                    }
                }
            } else {
                sendPasswordMessage.setText("Please enter a valid email address!");
                sendPasswordMessage.setStyle("-fx-text-fill: red;");
                sendPasswordMessage.setVisible(true);
                sendNewPasswordButton.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sendNewPasswordButton.setDefaultButton(true);
    }

}
