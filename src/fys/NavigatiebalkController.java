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
import javafx.scene.control.Label;
import javafx.scene.control.Separator;

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
    @FXML private Button homeAdmin, missingAdmin, foundAdmin, luggageAdmin, accountAdmin
            , staticsAdmin, home, missing, found, luggage, account;
    @FXML private Text welcomeText;   
    @FXML private Separator afscheiding1, afscheiding2, afscheiding3, afscheiding4
            , afscheiding5, afscheiding6, afscheiding7, afscheiding8, afscheiding9;
    @FXML private Label rechteropvulling1, rechteropvulling2;
  
    @FXML
    private void handleBagageformulieren(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Vermiste bagage registreren", "bagageformulieren.fxml");
    }
    
    @FXML
    private void handleGevondenBagageFormulier(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Gevonden bagage registreren", "gevondenformulier.fxml");
    }
    
    @FXML
    private void handleHome(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Home", "homepage.fxml");
    }
    
    @FXML
    private void handleHomeAdmin(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        fys.changeToAnotherFXML("Home", "homepageadmin.fxml");
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
        fys.changeToAnotherFXML("Statistieken", "statistieken.fxml");
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
        fys.changeToAnotherFXML("Corendon-Instellingen", "instellingen.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginController loginController = new loginController();
        String name = loginController.getUsersName().replaceAll("null", "");
        name = name.replaceAll("  ", " ");
        taal language = new taal();
        String[] taal = language.getLanguage();
        if(loginController.getUsertype() == 2){
            missingAdmin.setText(taal[0]);
            foundAdmin.setText(taal[1]);
            luggageAdmin.setText(taal[2]);
            accountAdmin.setText(taal[3]);
            staticsAdmin.setText(taal[4]);
            homeAdmin.setVisible(true);
            missingAdmin.setVisible(true);
            foundAdmin.setVisible(true);
            luggageAdmin.setVisible(true);
            accountAdmin.setVisible(true);
            staticsAdmin.setVisible(true);
            afscheiding5.setVisible(true);
            afscheiding6.setVisible(true);
            afscheiding7.setVisible(true);
            afscheiding8.setVisible(true);
            afscheiding9.setVisible(true);
            rechteropvulling2.setVisible(true);
            home.setVisible(false);
            missing.setVisible(false);
            found.setVisible(false);
            luggage.setVisible(false);
            account.setVisible(false);
            afscheiding1.setVisible(false);
            afscheiding2.setVisible(false);
            afscheiding3.setVisible(false);
            afscheiding4.setVisible(false);
            rechteropvulling1.setVisible(false);

            welcomeText.setText("Administrator - Welkom! " + name);
        } else{
            welcomeText.setText("Servicemedewerker - Welkom! " + name);
            missing.setText(taal[0]);
            found.setText(taal[1]);
            luggage.setText(taal[2]);
            account.setText(taal[3]);
        }
    }   
}