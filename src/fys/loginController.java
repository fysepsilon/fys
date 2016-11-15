/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private TextField username;
    @FXML
    private PasswordField password;
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
            if(authenticateLogin(username.getText(), fys.encrypt(password.getText()))){
                //Switch screen to Home.
                fys.changeToAnotherFXML("Corendon-WachtwoordVergeten", "bagageformulieren.fxml");
            } else{
                loginerror.setText("Uw gebruikersnaam en wachtwoord komen niet overeen!");
                loginerror.setVisible(true);
            }
        }
    }
    
    private boolean authenticateLogin(String inputUsername, String inputPassword) throws SQLException {
        FYS fys = new FYS();
        String username = "", password = "";
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "SELECT mail, password FROM accounts WHERE mail='" + inputUsername + "' AND password = '" + inputPassword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("mail");
                password = rs.getString("password");
                //Display values
//                System.out.print("username: " + username);
//                System.out.print(" password: " + password);
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Return boolean values.
        if (username != "" && password != "") {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
