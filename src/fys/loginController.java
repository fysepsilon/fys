/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
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
    private void handleForgotPasswordAction(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-WachtwoordVergeten", "wachtwoordVergeten.fxml");
    }
    
    @FXML
    private void handleChechLoginAction(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        System.out.println(username.getText() + " " + password.getText());
        //fys.changeToAnotherFXML("wachtwoordVergeten.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
