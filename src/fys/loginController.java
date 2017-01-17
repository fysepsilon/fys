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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Team Epsilon
 */
public class loginController implements Initializable {

    @FXML
    private Pane login_pane, wachtwoord_pane, email_pane;
    @FXML
    private TextField username, passwordforgot, emailphone, emailaddress,
            emailfirstname;
    @FXML
    private PasswordField password, emailpassword;
    @FXML
    private Label loginerror, passworderror, emailerror;
    @FXML
    private static int usertype, userstyle, userlanguage;
    @FXML
    private static String usersname, email;
    @FXML
    private Button logInButton, sendNewPasswordButton, sendEmailButton;
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

    public static void setUserstyle(int userStyle) {
        userstyle = userStyle;
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
    //Wordt diegene doorgestuurd naar de pane van wachtwoord vergeten.
    @FXML
    private void handleForgotPasswordAction(ActionEvent event) throws IOException {
        login_pane.setVisible(false);
        login_pane.setDisable(true);
        wachtwoord_pane.setVisible(true);
        wachtwoord_pane.setDisable(false);
        sendNewPasswordButton.setDefaultButton(true);
        logInButton.setDefaultButton(false);
        sendEmailButton.setDefaultButton(false);
        
        //Make a boolean for the focus 
        final BooleanProperty secondTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        passwordforgot.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && secondTime.get()) {
                wachtwoord_pane.requestFocus(); // Delegate the focus to container
                secondTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        login_pane.setVisible(true);
        login_pane.setDisable(false);
        wachtwoord_pane.setVisible(false);
        wachtwoord_pane.setDisable(true);
        logInButton.setDefaultButton(true);
        sendEmailButton.setDefaultButton(false);
        sendNewPasswordButton.setDefaultButton(false);
    }
    
    @FXML
    private void handleBackEmailAction(ActionEvent event) throws IOException {
        login_pane.setVisible(true);
        login_pane.setDisable(false);
        email_pane.setVisible(false);
        email_pane.setDisable(true);
        emailerror.setText("");
        logInButton.setDefaultButton(true);
        sendEmailButton.setDefaultButton(false);
        sendNewPasswordButton.setDefaultButton(false);
    }
    
    @FXML
    private void handleForgotEmailAction(ActionEvent event) throws IOException {
        login_pane.setVisible(false);
        login_pane.setDisable(true);
        email_pane.setVisible(true);
        email_pane.setDisable(false);
        sendEmailButton.setDefaultButton(true);
        sendNewPasswordButton.setDefaultButton(false);
        logInButton.setDefaultButton(false);
        
        //Make a boolean for the focus 
        final BooleanProperty thirthTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        emailfirstname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && thirthTime.get()) {
                email_pane.requestFocus(); // Delegate the focus to container
                thirthTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    //Dit wordt aangeroepen wanneer de gebruiker op de button wachtwoord verzenden klikt.
    @FXML
    private void handleSendNewPasswordAction(ActionEvent event) throws IOException, SQLException {
        sendNewPasswordButton.setDisable(true);
        int pageid = 3;
        String email = "";
        int type = 0;
        String language = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();

        //Controleer of de velden wachtwoord leeg zijn. Anders laat een error zien.
        if ((passwordforgot.getText() == null || passwordforgot.getText().trim().isEmpty())) {
            passworderror.setText("Username is empty!");
            passworderror.setVisible(true);
            sendNewPasswordButton.setDisable(false);
        } else //Als de emailadres niet klopt volgens de regels dan wordt een error getoond.
         if (FYS.isValidEmailAddress(passwordforgot.getText())) {
                //Haal de mail die is ingevuld.
                try {
                    String sql = "SELECT mail, type, language FROM person WHERE mail='" + passwordforgot.getText() + "' AND (type = '1' OR type = '2')";
                    try (ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            //Retrieve by column name
                            email = rs.getString("mail");
                            language = rs.getString("language");
                            //Display values
                        }
                    }
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }

                //Controleer of de ingevulde veld leeg is. Laat dan een error zien.
                if ((email == null || email.trim().isEmpty())) {
                    passworderror.setText("This username unfortunately does not exists!");
                    passworderror.setVisible(true);
                    sendNewPasswordButton.setDisable(false);
                } else if (fys.Email_Persontype(passwordforgot.getText()) == 1
                        || fys.Email_Persontype(passwordforgot.getText()) == 2) { // Stuur email als gebruiker type 1 of 2 is.

                    // Email bericht filteren op sommige woorden.            
                    String getmessage = fys.replaceEmail(fys.Email_Message(type, fys.Email_Language(passwordforgot.getText()), pageid), passwordforgot.getText());
                    // Email versturen
                    fys.sendEmail(passwordforgot.getText(), fys.Email_Subject(type, fys.Email_Language(passwordforgot.getText()), pageid), getmessage, "Sent message successfully....");

                    passworderror.setText("Your password has been sent to: " + passwordforgot.getText() + "!");
                    passworderror.setStyle("-fx-text-fill: green;");
                    passworderror.setVisible(true);
                    passwordforgot.setText("");
                    sendNewPasswordButton.setDisable(false);
                }
            } else {
                passworderror.setText("Please enter a valid email address!");
                passworderror.setVisible(true);
                sendNewPasswordButton.setDisable(false);
            }
    }

    //Als de gebruiker op de knop op login klikt.
    @FXML
    private void handleCheckLoginAction(ActionEvent event) throws IOException, SQLException {
        //Controleer of de velden gebruikersnaam of wachtwoord zijn ingevuld lat anders een error zien.
        if ((username.getText() == null || username.getText().trim().isEmpty())
                || (password.getText() == null || password.getText().trim().isEmpty())) {
            loginerror.setText("Fields are left blank!");
            loginerror.setVisible(true);
        } else if (authenticateLogin(username.getText(), fys.encrypt(password.getText()))
                && (getUsertype() == 1 || getUsertype() == 2)) {
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

            String sql = "SELECT mail, password, type, first_name, insertion, surname, style FROM person WHERE mail='"
                    + inputUsername + "' AND password = '" + inputPassword + "'";
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

    @FXML
    private void handleSendEmailAction(ActionEvent event) throws IOException, SQLException {
        sendEmailButton.setDisable(true);
        String email = "";
        String language = "";
        Statement stmt = null;
        Connection conn = null;
        conn = fys.connectToDatabase(conn);
        stmt = conn.createStatement();

        //Controleer of de velden wachtwoord leeg zijn. Anders laat een error zien.
        if ((emailfirstname.getText() == null || emailfirstname.getText().trim().isEmpty()
                || emailaddress.getText() == null || emailaddress.getText().trim().isEmpty()
                || emailpassword.getText() == null || emailpassword.getText().trim().isEmpty()
                || emailphone.getText() == null || emailphone.getText().trim().isEmpty())) {
            emailerror.setText("There are fields empty");
            emailerror.setVisible(true);
            sendEmailButton.setDisable(false);
        } else //Als de emailadres niet klopt volgens de regels dan wordt een error getoond.
        {
            //Haal de mail die is ingevuld.
            try {
                String sql = "SELECT mail FROM person "
                        + "WHERE first_name='" + emailfirstname.getText() + "' "
                        + "AND address='" + emailaddress.getText() + "' "
                        + "AND password='" + fys.encrypt(emailpassword.getText()) + "' "
                        + "AND phone='" + emailphone.getText() + "' "
                        + "AND (type = '1' OR type = '2')";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        email = rs.getString("mail");
                    }
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            //Controleer of de ingevulde veld leeg is. Laat dan een error zien.
            if ((email == null || email.trim().isEmpty())) {
                emailerror.setText("The given combination is not found!");
                emailerror.setVisible(true);
                sendEmailButton.setDisable(false);
            } else if (fys.Email_Persontype(email) == 1
                    || fys.Email_Persontype(email) == 2) { // Controleer of de gebruiker type 1 of 2 is.

                emailerror.setText("Your email is: " + email);
                emailerror.setStyle("-fx-text-fill: green;");
                emailerror.setVisible(true);
                emailfirstname.setText("");
                emailaddress.setText("");
                emailpassword.setText("");
                emailphone.setText("");
                sendEmailButton.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Make a boolean for the focus 
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        final BooleanProperty secondTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        final BooleanProperty thirthTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        //Dont focus
        username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                login_pane.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        passwordforgot.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && secondTime.get()) {
                wachtwoord_pane.requestFocus(); // Delegate the focus to container
                secondTime.setValue(false); // Variable value changed for future references
            }
        });
        emailfirstname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && thirthTime.get()) {
                email_pane.requestFocus(); // Delegate the focus to container
                thirthTime.setValue(false); // Variable value changed for future references
            }
        });
        
        logInButton.setDefaultButton(true);
        sendEmailButton.setDefaultButton(false);
        sendNewPasswordButton.setDefaultButton(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username.requestFocus();
            }
        });
    }

}
