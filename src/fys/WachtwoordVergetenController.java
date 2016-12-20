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
    private final FYS fys = new FYS();
    
    //Dit wordt aangeroepen wanneer je op de button terug klikt.
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML("Corendon-Login", "login.fxml");
    }
    
    //Dit wordt aangeroepen wanneer de gebruiker op de button wachtwoord verzenden klikt.
    @FXML
    private void handleSendNewPasswordAction(ActionEvent event) throws IOException, SQLException {
        sendNewPasswordButton.setDisable(true);
        int pageid = 3;
        String email = "";
        String type = "";
        String language = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();

        //Controleer of de velden wachtwoord leeg zijn. Anders laat een error zien.
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
                    String sql = "SELECT mail, type, language FROM person WHERE mail='" + username.getText() + "' AND (type = '1' OR type = '2')";
                    try (ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            //Retrieve by column name
                            email = rs.getString("mail");
                            type = rs.getString("type");
                            language = rs.getString("language");
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
                } else if (fys.Email_Persontype(username.getText()) == 1
                        || fys.Email_Persontype(username.getText()) == 2) { // Stuur email als gebruiker type 1 of 2 is.

                    // Email bericht filteren op sommige woorden.            
                    String getmessage = fys.replaceEmail(fys.Email_Message(fys.getUserFunctionString(type), fys.getUserLanguageString(language), pageid), username.getText());
                    // Email versturen
                    fys.sendEmail(username.getText(), fys.Email_Subject(fys.getUserFunctionString(type), fys.getUserLanguageString(language), pageid), getmessage, "Sent message successfully....");

                    sendPasswordMessage.setText("Your password has been sent to: " + username.getText() + "!");
                    sendPasswordMessage.setStyle("-fx-text-fill: green;");
                    sendPasswordMessage.setVisible(true);
                    username.setText("");
                    sendNewPasswordButton.setDisable(false);
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
