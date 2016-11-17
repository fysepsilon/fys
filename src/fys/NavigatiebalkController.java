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
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class NavigatiebalkController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Button homeAdmin;
    @FXML private Button missingAdmin;
    @FXML private Button foundAdmin;
    @FXML private Button luggageAdmin;
    @FXML private Button accountAdmin;
    @FXML private Button staticsAdmin;
    @FXML private Button home;
    @FXML private Button missing;
    @FXML private Button found;
    @FXML private Button luggage;
    @FXML private Button account;
    @FXML private Text welcomeText;
    
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
    private void handleHomeAdmin(ActionEvent event) throws IOException {
        FYS fys = new FYS();
    }
    
    @FXML
    private void handleAccounts(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Accounts", "accounts.fxml");
    }
    
    @FXML
    private void handleAccountsAdmin(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Accounts", "accounts.fxml");
    }
    
    @FXML
    private void handleStaticsAdmin(ActionEvent event) throws IOException {
        FYS fys = new FYS();
    }
    
    @FXML
    private void handleBagagedatabase(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Bagage database", "bagagedatabase.fxml");
    }
    
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Corendon-Login", "login.fxml");
    }
    
    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        FYS fys = new FYS();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController loginController = new loginController();
        String name = loginController.getUsersName().replaceAll("null", "");
        name = name.replaceAll("  ", " ");
        if(loginController.getUsertype().equals("1")){
            homeAdmin.setVisible(true);
            missingAdmin.setVisible(true);
            foundAdmin.setVisible(true);
            luggageAdmin.setVisible(true);
            accountAdmin.setVisible(true);
            staticsAdmin.setVisible(true);
            home.setVisible(false);
            missing.setVisible(false);
            found.setVisible(false);
            luggage.setVisible(false);
            account.setVisible(false);
            welcomeText.setText("Administrator - Welkom! " + name);
        } else{
            welcomeText.setText("Servicemedewerker - Welkom! " + name);
        }
    }    
}
