/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Team Epsilon
 */
public class loginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginerror;
    @FXML
    private static int usertype;
    @FXML
    private static int userstyle;
    @FXML
    private static int userlanguage;
    @FXML
    private static String usersname;
    @FXML
    private static String email;
    @FXML
    private Button logInButton;
    @FXML
    private final FYS fys = new FYS();
    
    /**
     * 
     * @return De type van de gebruiker.
     */
    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int userType) {
        loginController.usertype = userType;
    }
    
    public int getUserstyle() {
        return userstyle;
    }

    public void setUserstyle(int userStyle) {
        loginController.userstyle = userStyle;
    }

    public int getUserlanguage() {
        return userlanguage;
    }

    public void setUserlanguage(int userLanguage) {
        loginController.userlanguage = userLanguage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        loginController.email = email;
    }

    public String getUsersName() {
        return usersname;
    }
    

    public void setUsersName(String usersname) {
        loginController.usersname = usersname;
    }
    
    @FXML
    private void handleInformation(ActionEvent event) {
        fys.UserManual();
    }
    
    //Wanneer de gebruiker op wachtwoord vergeten klikt. 
    //Wordt diegene doorgestuurd naar de pagina van wachtwoord vergeten.
    @FXML
    private void handleForgotPasswordAction(ActionEvent event) throws IOException {
        //Switch screen to wachtwoordvergeten.
        fys.changeToAnotherFXML("Corendon-Forgotpassword", "wachtwoordVergeten.fxml");
    }
    
    //Als de gebruiker op de knop op login klikt.
    @FXML
    private void handleCheckLoginAction(ActionEvent event) throws IOException, SQLException {
        //Controleer of de velden gebruikersnaam of wachtwoord zijn ingevuld lat anders een error zien.
        if ((username.getText() == null || username.getText().trim().isEmpty()) || 
                (password.getText() == null || password.getText().trim().isEmpty())) {
            loginerror.setText("Fields are left blank!");
            loginerror.setVisible(true);
        } else if (authenticateLogin(username.getText(), fys.encrypt(password.getText())) && 
                (getUsertype() == 1 || getUsertype() == 2)) {
                    loginController loginController = new loginController();
            //Kijk wat voor gebruiker inlogt: een admin of servicemedewerker.
            if (loginController.getUsertype() == 1) {
                fys.changeToAnotherFXML("Corendon-Home", "homepage.fxml");
            } else { // Switch screen to HomeAdmin
                fys.changeToAnotherFXML("Corendon-Home", "homepageadmin.fxml");
            }
        //Laat een error zien dat de gebruikersnaam en wachtwoord niet overeenkomen.
        } else {
            loginerror.setText("Username and password do not match!");
            loginerror.setVisible(true);
        }
    }

    private boolean authenticateLogin(String inputUsername, String inputPassword) throws SQLException {
        //Controleer of de gebruikersnaam en wachtwoord samen in de database bestaat.
        String username = "", password = "";
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            String sql = "SELECT mail, password, type, first_name, insertion, surname, style FROM person WHERE mail='" + 
                    inputUsername + "' AND password = '" + inputPassword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                username = rs.getString("mail");
                setEmail(rs.getString("mail"));
                password = rs.getString("password");
                setUsertype(rs.getInt("type"));
                setUserstyle(rs.getInt("style"));
                setUsersName(rs.getString("first_name").substring(0, 1).toUpperCase() + rs.getString("first_name").substring(1)
                        + " " + rs.getString("insertion") + " "
                        + rs.getString("surname").substring(0, 1).toUpperCase() + rs.getString("surname").substring(1));
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
        return !username.equals("") && !password.equals("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logInButton.setDefaultButton(true);
    }

}
