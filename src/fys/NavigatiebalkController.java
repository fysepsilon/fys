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
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

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
    @FXML private HBox HBoxAdmin, HBoxSM;
    @FXML private MenuItem settings, logout;
    @FXML private taal languages = new taal();
    @FXML private String[] taal = languages.getLanguage();
    @FXML private FYS fys = new FYS();
    
    @FXML
    private void handleBagageformulieren(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[95], "bagageformulieren.fxml");
    }
    
    @FXML
    private void handleGevondenBagageFormulier(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[96], "gevondenformulier.fxml");
    }
    
    @FXML
    private void handleHome(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[97], "homepage.fxml");
    }
    
    @FXML
    private void handleHomeAdmin(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[97], "homepageadmin.fxml");
    }
    
    @FXML
    private void handleAccounts(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[98], "accounts.fxml");
    }
   
    @FXML
    private void handleStaticsAdmin(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[99], "statistieken.fxml");
    }
    
    @FXML
    private void handleMailsettings(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML("mailsettings", "mailsettings.fxml");
    }
    
    @FXML
    private void handleBagagedatabase(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[100], "bagagedatabase.fxml");
    }
    
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[101], "login.fxml");
    }
    
    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        fys.changeToAnotherFXML(taal[102], "instellingen.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //genereer de welkom naam.
        loginController loginController = new loginController();
        String name = loginController.getUsersName().replaceAll("null", "");
        name = name.replaceAll("  ", " ");
        taal language = new taal();
        String[] taal = language.getLanguage();
        //Controleer of een admin of systeembeheerder is ingelogd.
        //Laat box zien als het admin is.
        if(loginController.getUsertype() == 2){
            //Zet alle woorden in de taal die is geinstalleerd.
            missingAdmin.setText(taal[0]);
            foundAdmin.setText(taal[1]);
            luggageAdmin.setText(taal[2]);
            accountAdmin.setText(taal[3]);
            staticsAdmin.setText(taal[4]);            
            HBoxAdmin.setVisible(true);
            HBoxSM.setVisible(false);
            welcomeText.setText( taal[6] + " - " + taal[5] + "! " + name);
        } else{
            //Zet alle woorden in de taal die is geinstalleerd.
            welcomeText.setText( taal[7] + " - " + taal[5] + "! " + name);
            missing.setText(taal[0]);
            found.setText(taal[1]);
            luggage.setText(taal[2]);
            account.setText(taal[3]);
        }
        settings.setText(taal[61]);
        logout.setText(taal[62]);
    }   
}
