/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Paras
 */
public class loginController implements Initializable {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    private Label label;
    @FXML
    private Label loginerror;
    
    @FXML
    private void handleForgotPasswordAction(ActionEvent event) throws IOException {
        //Switch screen to wachtwoordvergeten.
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-WachtwoordVergeten", "wachtwoordVergeten.fxml");
    }
    
    @FXML
    private void handleChechLoginAction(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        //Check if username and password is filled in and correct.
        //Show error if not filled in or not correct.
        if((username.getText() == null || username.getText().trim().isEmpty()) || (password.getText() == null || password.getText().trim().isEmpty())){
            loginerror.setText("Gebruikersnaam en/of wachtwoord veld(en) zijn leeg gelaten!");
            loginerror.setVisible(true);
        } else{
            if(fys.authenticateLogin(username.getText(), password.getText())){
                //Switch screen to Home.
                fys.changeToAnotherFXML("Corendon-WachtwoordVergeten", "wachtwoordVergeten.fxml");
            } else{
                loginerror.setText("Uw gebruikersnaam en wachtwoord komen niet overeen!");
                loginerror.setVisible(true);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
