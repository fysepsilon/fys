/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import fys.FYS;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Veron
 */
public class HomepageController implements Initializable {
    @FXML
    private void handleBagageformulieren(ActionEvent event) throws IOException {
        //Switch screen to wachtwoordvergeten.
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Bagage formulieren", "bagageformulieren.fxml");
    }
    @FXML
    private void handleAccounts(ActionEvent event) throws IOException {
        //Switch screen to wachtwoordvergeten.
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Accounts", "accounts.fxml");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
