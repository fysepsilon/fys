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
 * @author Paras
 */
public class WachtwoordVergetenController implements Initializable {

    @FXML private TextField username;
    @FXML private Label label;
    @FXML private Label sendPasswordMessage;
    @FXML private Button sendNewPasswordButton;

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
        
        if ((username.getText() == null || username.getText().trim().isEmpty())) {
            sendPasswordMessage.setText("Gebruikersnaam is leeg gelaten!");
            sendPasswordMessage.setStyle("-fx-text-fill: red;");
            sendPasswordMessage.setVisible(true);
            sendNewPasswordButton.setDisable(false);
        } else {
            if(fys.isValidEmailAddress(username.getText())){
                try {
                    //connectToDatabase(conn, stmt, "test", "root", "root");
                    String sql = "SELECT mail FROM person_table WHERE mail='" + username.getText() + "'";
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

                if ((email == null || email.trim().isEmpty())) {
                    sendPasswordMessage.setText("Deze gebruikersnaam bestaat helaas niet!");
                    sendPasswordMessage.setStyle("-fx-text-fill: red;");
                    sendPasswordMessage.setVisible(true);
                    sendNewPasswordButton.setDisable(false);
                } else{
                    String[] mailInformation = new String[3];
                    try {
                        //connectToDatabase(conn, stmt, "test", "root", "root");
                        String sql = "SELECT first_name, surname, password FROM person_table WHERE type = '1' OR type = '2' AND mail='" + username.getText() + "'";
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            //Retrieve by column name
                            mailInformation[0] = rs.getString("first_name").substring(0, 1).toUpperCase() + rs.getString("first_name").substring(1);
                            mailInformation[1] = rs.getString("surname").substring(0, 1).toUpperCase() + rs.getString("surname").substring(1);
                            mailInformation[2] = fys.decrypt(rs.getString("password"));
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
                    fys.sendEmail(username.getText(), "Corendon - Wachtwoord", "Beste "
                            + mailInformation[0] + " " + mailInformation[1] + ", "
                            + "<br><br>U heeft aangegeven dat u het wachtwoord van uw account wilt opvragen."
                            + "<br>Bij deze uw wachtwoord: <i>" + mailInformation[2]
                            + "</i><br><br> Wij hopen hopen u hiermee goed te hebben geholpen."
                            + "<br>Als u uw wachtwoord niet heeft opgevraagd kunt u deze e-mail verwijderen."
                            + "<br><br>Met vriendelijke groet,"
                            + "<br><br><b>Het Corendon Team</b>", "Sent message successfully....");
                    sendPasswordMessage.setText("Uw wachtwoord is verstuurd naar: " + username.getText() + "!");
                    sendPasswordMessage.setStyle("-fx-text-fill: green;");
                    sendPasswordMessage.setVisible(true);
                    username.setText("");
                    sendNewPasswordButton.setDisable(false);
                }
            } else{
                sendPasswordMessage.setText("Voer wel een gelidige e-mailadres in!");
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
