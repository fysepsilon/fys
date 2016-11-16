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
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class NavigatiebalkController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleBagageformulieren(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Vermiste bagage registreren", "bagageformulieren.fxml");
    }
    
    @FXML
    private void handleHome(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Home", "homepage.fxml");
    }
    
    @FXML
    private void handleAccounts(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Accounts", "accounts.fxml");
    }
    
    @FXML
    private void handleBagagedatabase(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Bagage database", "bagagedatabase.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
