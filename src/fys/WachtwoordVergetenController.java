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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class WachtwoordVergetenController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Label label;
    @FXML
    private Label sendPasswordError;

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-Login", "login.fxml");
    }
    
    @FXML
    private void handleSendNewPasswordAction(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        String email = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();
        
        if ((username.getText() == null || username.getText().trim().isEmpty())) {
            sendPasswordError.setText("Gebruikersnaam is leeg gelaten!");
            sendPasswordError.setVisible(true);
        } else {
            try {
                //connectToDatabase(conn, stmt, "test", "root", "root");
                String sql = "SELECT mail FROM accounts WHERE mail='" + username.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
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
            
            if ((email == null || email.trim().isEmpty())) {
                sendPasswordError.setText("Deze gebruikersnaam bestaat helaas niet!");
                sendPasswordError.setVisible(true);
            } else{
                fys.sendEmail(username.getText(), "Corendon - Wachtwoord", "Beste "
                        + ", "
                        + "<br><br> Hierbij sturen wij jouw wachtwoord van het Corendon systeem. "
                        + "<br>Wachtwoord: "
                        + "<br><br> Wij hopen je goed te hebben geholpen."
                        + "<br><br>Met vriendelijke groet,"
                        + "<br><br><b>Corendon</b>", "Sent message successfully....");
                username.setText("");
                sendPasswordError.setVisible(false);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
