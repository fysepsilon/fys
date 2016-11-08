/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class WachtwoordVergetenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label label;

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("login.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
