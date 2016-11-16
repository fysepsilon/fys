/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import fys.FYS;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Veron
 */
public class accountsController {
    @FXML
    private void handleNieuwAccount(ActionEvent event) throws IOException {
        //Switch screen to wachtwoordvergeten.
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-WachtwoordVergeten", "wachtwoordVergeten.fxml");
    }
}
