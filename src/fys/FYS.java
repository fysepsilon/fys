/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Team Epsilon
 */
public class FYS extends Application {

    private static Stage parentWindow;
    private static final String key = "1234abcd";
    private final static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    /**
     * Verander het hoofscherm in login.fxml
     *
     * @param stage Een hoofdscherm
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 990, 590);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Corendon-Login");

        //stage.setMaxWidth(0);
        //stage.setMaxHeight(0);
        stage.getIcons().add(new Image("http://www.corendon.com/favicon.png"));
        stage.show();

    }

    /**
     *
     * @param title zet titel van het hoofscherm.
     * @param changeToWindow Verander het hoofdscherm in een willekeurige fxml
     * bestand.
     * @throws IOException
     */
    public void changeToAnotherFXML(String title, String changeToWindow) throws IOException {
        loginController loginController = new loginController();
        int style = loginController.getUserstyle();
        String css;
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource(changeToWindow));

        switch (style) {
            case 1:
                css = this.getClass().getResource("style/theme_menu_yellow.css").toExternalForm();
                break;
            case 2:
                css = this.getClass().getResource("style/theme_menu_blue.css").toExternalForm();
                break;
            case 3:
                css = this.getClass().getResource("style/theme_menu_orange.css").toExternalForm();
                break;
            case 4:
                css = this.getClass().getResource("style/theme_menu_green.css").toExternalForm();
                break;
            case 5:
                css = this.getClass().getResource("style/theme_menu_pink.css").toExternalForm();
                break;
            case 6:
                css = this.getClass().getResource("style/theme_menu_grey.css").toExternalForm();
                break;
            case 7:
                css = this.getClass().getResource("style/theme_menu_black.css").toExternalForm();
                break;
            default:
                css = this.getClass().getResource("style/theme_menu_default.css").toExternalForm();
                break;
        }

        window1.getStylesheets().add(css);
        Stage mainStage;
        mainStage = FYS.parentWindow;
        mainStage.setTitle(title);
        mainStage.setResizable(false);
        mainStage.getScene().setRoot(window1);

    }
    
    /**
     * Function for Usermanual
     */
    public void UserManual() {
        int language = 0;
        loginController login = new loginController();
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT language FROM person "
                    + "WHERE type = '" + login.getUsertype() + "' "
                    + "AND mail='" + login.getEmail() + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    language = rs.getInt("language");
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        try {
            File myFile = new File("src/fys/templates/Gebruikershandleiding_EN.pdf");
            if (language == 1) {
                myFile = new File("src/fys/templates/Gebruikershandleiding_NL.pdf");
            } else if (language == 2) {
                myFile = new File("src/fys/templates/Gebruikershandleiding_ES.pdf");
            } else if (language == 3) {
                myFile = new File("src/fys/templates/Gebruikershandleiding_TR.pdf");
            } else if (language == 4) {
                myFile = new File("src/fys/templates/Gebruikershandleiding_DE.pdf");
            }
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
            // no application registered for PDFs
        }
    }

    /**
     *
     * @param length de lengte van het wachtwoord.
     * @return een random wachtwoord met de lengte die is geselecteerd.
     */
    public static String generateRandomPassword(int length) {
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     *
     * @param type style type
     * @return name of style
     */
    public String getUserStyle(int type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type == 1) { //Geel
            return taal[36];
        } else if (type == 2) { // Blauw
            return taal[38];
        } else if (type == 3) { // Oranje
            return taal[35];
        } else if (type == 4) { // Groen
            return taal[37];
        } else if (type == 5) { // Roze
            return taal[40];
        } else if (type == 6) { // Grijs
            return taal[42];
        } else if (type == 7) {
            return taal[41];
        }
        return taal[34]; // Rood
    }

    /**
     *
     * @param type style type
     * @return type of style
     */
    public Integer getUserStyleString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[36])) {
            return 1;
        } else if (type.equals(taal[38])) {
            return 2;
        } else if (type.equals(taal[35])) {
            return 3;
        } else if (type.equals(taal[37])) {
            return 4;
        } else if (type.equals(taal[40])) {
            return 5;
        } else if (type.equals(taal[42])) {
            return 6;
        } else if (type.equals(taal[41])) {
            return 7;
        }
        return 0;
    }

    /**
     *
     * @param type welke type er is geselecteerd in cijfers.
     * @return user type in woorden in de taal van de gebruiker (account).
     */
    public String getUserFunction(int type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type == 1) {
            return taal[64];
        } else if (type == 2) {
            return taal[65];
        } else if (type == 3) { //Onbekend
            return taal[163];
        }
        return taal[66];
    }

    /**
     *
     * @param type Welke type er is geselecteerd in de taal van de gebruiker
     * (account).
     * @return user type in getallen.
     */
    public Integer getUserFunctionString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[64])) {
            return 1;
        } else if (type.equals(taal[65])) {
            return 2;
        } else if (type.equals(taal[163])) { //Onbekend
            return 3;
        }
        return 0;
    }

    /**
     *
     * @param type Welke taal is geselecteerd in getallen.
     * @return de taal in de taal van de gebruiker.
     */
    public String getUserLanguage(int type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        switch (type) {
            case 1:
                return taal[70];
            case 2:
                return taal[71];
            case 3:
                return taal[72];
            case 4:
                return taal[165];
            default:
                break;
        }
        return taal[69];
    }

    /**
     *
     * @param type Welke taal is geselecteerd in de taal van de gebruiker.
     * @return de taal in getallen.
     */
    public Integer getUserLanguageString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[70])) {
            return 1;
        } else if (type.equals(taal[71])) {
            return 2;
        } else if (type.equals(taal[72])) {
            return 3;
        } else if (type.equals(taal[165])) {
            return 4;
        }
        return 0;
    }

    /**
     *
     * @param type Welke bagage type is geselecteerd in de taal van de
     * gebruiker.
     * @return de bagage type in getallen.
     */
    public Integer getBaggageTypeString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[28])) {
            return 1;
        } else if (type.equals(taal[29])) {
            return 2;
        } else if (type.equals(taal[30])) {
            return 3;
        }
        return 0;
    }

    /**
     *
     * @param type Welke bagage type is geselecteerd in getallen.
     * @return de bagage type in de taal van de gebruiker.
     */
    public String getBaggageType(int type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        switch (type) {
            case 1:
                return taal[28];
            case 2:
                return taal[29];
            case 3:
                return taal[30];
            default:
                break;
        }
        return taal[27];
    }

    /**
     *
     * @param language De taal die ingesteld is naar wie de mail moet worde
     * verstuurd.
     * @param status
     * @return
     */
    public String getStatusForMail(int language, int status) {
        switch (language) {
            case 0:
                switch (status) {
                    case 1:
                        return "Lost";
                    case 2:
                        return "Destroyed";
                    case 3:
                        return "Completed";
                    case 4:
                        return "Never found";
                    case 5:
                        return "Depot";
                    case 6:
                        return "Insurance claim";
                    default:
                        break;
                }
                return "Found";
            case 1:
                switch (status) {
                    case 1:
                        return "Vermist";
                    case 2:
                        return "Vernietigd";
                    case 3:
                        return "Afgehandeld";
                    case 4:
                        return "Nooit gevonden";
                    case 5:
                        return "Depot";
                    case 6:
                        return "Schadeclaim";
                    default:
                        break;
                }
                return "Gevonden";
            case 2:
                switch (status) {
                    case 1:
                        return "Que falta";
                    case 2:
                        return "Destruido";
                    case 3:
                        return "Tratado";
                    case 4:
                        return "Nunca encontrado";
                    case 5:
                        return "Almacén";
                    case 6:
                        return "Reclamación de seguro";
                    default:
                        break;
                }
                return "Fundar";
            case 3:
                switch (status) {
                    case 1:
                        return "Eksik";
                    case 2:
                        return "Tahrip";
                    case 3:
                        return "Tamamlanan";
                    case 4:
                        return "Bulmadım";
                    case 5:
                        return "Depo";
                    case 6:
                        return "Sigorta tazminat talebi";
                    default:
                        break;
                }
                return "Bulundu";
        }
        return "";
    }

    /**
     *
     * @param status Wekle status is geselecteerd in getallen.
     * @return de status in de taal van de gebruiker.
     */
    public String getStatus(int status) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        switch (status) {
            case 1:
                return taal[55];
            case 2:
                return taal[56];
            case 3:
                return taal[57];
            case 4:
                return taal[58];
            case 5:
                return taal[59];
            case 6:
                return taal[108];
            default:
                break;
        }
        return taal[54];
    }

    /**
     *
     * @param status Welke status is geselecteerd in de taal van de gebruiker
     * @return de status in getallen
     */
    public Integer getStatusString(String status) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (status.equals(taal[55])) {
            return 1;
        } else if (status.equals(taal[56])) {
            return 2;
        } else if (status.equals(taal[57])) {
            return 3;
        } else if (status.equals(taal[58])) {
            return 4;
        } else if (status.equals(taal[59])) {
            return 5;
        } else if (status.equals(taal[108])) {
            return 6;
        } else {
            return 0;
        }
    }

    public String getPage(int page) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        switch (page) {
            case 1:
                return taal[63];
            case 2:
                return taal[95];
            case 3:
                return taal[166];
            case 4:
                return taal[100];
            case 5:
                return taal[100];
            case 6:
                return taal[100];
            default:
                break;
        }
        return taal[163];
    }

    /**
     *
     * @param month Welke maand is geselecteerd in de taal van de gebruiker.
     * @return de maand in getallen.
     */
    public String getMonthNumber(String month) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (month.equals(taal[109])) {
            return "1";
        } else if (month.equals(taal[110])) {
            return "2";
        } else if (month.equals(taal[111])) {
            return "3";
        } else if (month.equals(taal[112])) {
            return "4";
        } else if (month.equals(taal[113])) {
            return "5";
        } else if (month.equals(taal[114])) {
            return "6";
        } else if (month.equals(taal[115])) {
            return "7";
        } else if (month.equals(taal[116])) {
            return "8";
        } else if (month.equals(taal[117])) {
            return "9";
        } else if (month.equals(taal[118])) {
            return "10";
        } else if (month.equals(taal[119])) {
            return "11";
        } else if (month.equals(taal[120])) {
            return "12";
        }
        return "";
    }

    /**
     *
     * @param month Welke is geselecteerd in getallen.
     * @return de maand in de taal van de gebruiker.
     */
    public String getMonthName(String month) {
        taal language = new taal();
        String[] taal;
        taal = language.getLanguage();
        switch (month) {
            case "01":
                return taal[109];
            case "02":
                return taal[110];
            case "03":
                return taal[111];
            case "04":
                return taal[112];
            case "05":
                return taal[113];
            case "06":
                return taal[114];
            case "07":
                return taal[115];
            case "08":
                return taal[116];
            case "09":
                return taal[117];
            case "10":
                return taal[118];
            case "11":
                return taal[119];
            case "12":
                return taal[120];
            default:
                break;
        }
        return "";
    }

    /**
     *
     * @param color Welke kleur is geselecteerd in getallen.
     * @return de kleur in de taal van de gebruiker.
     */
    public String getColor(int color) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        switch (color) {
            case 1:
                return taal[33];
            case 2:
                return taal[34];
            case 3:
                return taal[35];
            case 4:
                return taal[36];
            case 5:
                return taal[37];
            case 6:
                return taal[38];
            case 7:
                return taal[39];
            case 8:
                return taal[40];
            case 9:
                return taal[41];
            case 10:
                return taal[42];
            case 11:
                return taal[43];
            default:
                break;
        }
        return taal[32];
    }

    /**
     *
     * @param color Welke kleur is geselecteerd in de taal van de gebruiker.
     * @return de kleur in getallen.
     */
    public Integer getColorString(String color) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (color.equals(taal[33])) {
            return 1;
        } else if (color.equals(taal[34])) {
            return 2;
        } else if (color.equals(taal[35])) {
            return 3;
        } else if (color.equals(taal[36])) {
            return 4;
        } else if (color.equals(taal[37])) {
            return 5;
        } else if (color.equals(taal[38])) {
            return 6;
        } else if (color.equals(taal[39])) {
            return 7;
        } else if (color.equals(taal[40])) {
            return 8;
        } else if (color.equals(taal[41])) {
            return 9;
        } else if (color.equals(taal[42])) {
            return 10;
        } else if (color.equals(taal[43])) {
            return 11;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param email Kijk of deze emailadres al bestaat in de database.
     * @return
     */
    public boolean checkEmailExists(String email) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM person "
                    + "WHERE mail='" + email + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    return true;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    /**
     *
     * @param type type from luggage
     * @param brand brand from luggage
     * @param color color from luggage
     * @return true or false
     */
    public boolean checkLost(int type, String brand, int color) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM lost "
                    + "WHERE type='" + type + "' "
                    + "AND brand = '" + brand + "' "
                    + "AND color = '" + color + "';";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    return true;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    /**
     *
     * @param type type from luggage
     * @param brand brand from luggage
     * @param color color from luggage
     * @return count number from lost luggage
     * @throws SQLException
     */
    public int countLost(int type, String brand, int color) throws SQLException {
        int countLost = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM lost "
                    + "WHERE lost.type='" + type + "' "
                    + "AND lost.brand = '" + brand + "' "
                    + "AND lost.color = '" + color + "';";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    countLost = rs.getInt("count(*)");

                    return countLost;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return countLost;
    }

    /**
     *
     * @param type type from luggage
     * @param brand brand from luggage
     * @param color color from luggage
     * @return true or false
     */
    public boolean checkFound(int type, String brand, int color) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM found "
                    + "WHERE type='" + type + "' "
                    + "AND brand = '" + brand + "' "
                    + "AND color = '" + color + "';";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    return true;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    /**
     *
     * @param type type from luggage
     * @param brand brand from luggage
     * @param color color from luggage
     * @return count number from found luggage
     * @throws SQLException
     */
    public int countFound(int type, String brand, int color) throws SQLException {
        int countFound = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM found "
                    + "WHERE type='" + type + "' "
                    + "AND brand = '" + brand + "' "
                    + "AND color = '" + color + "';";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    countFound = rs.getInt("count(*)");

                    return countFound;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return countFound;
    }

    /**
     *
     * @param email Controleer of deze emailadres in de database bestaat.
     * @param emailWas Maar kijk niet naar deze emailadres, omdat dit de oude
     * emailadres was.
     * @return true als het bestaat anders false.
     */
    public boolean checkEmailExistsOnChange(String email, String emailWas) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM person"
                    + " WHERE mail='" + email + "' AND mail not like '" + emailWas + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    return true;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    //Conect to database.
    public Connection connectToDatabase(Connection conn) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/bagagedatabase?user=root&password=root&useSSL=false");
        return conn;
    }

    /**
     *
     * @param to Het emailadres naar wie het moet worden gestuurd.
     * @param subject Het onderwerp van de mail.
     * @param content De inhoud van de mail.
     * @param printMessage Eventueel printen dat het is verstuurd.
     * @throws UnsupportedEncodingException
     */
    public void sendEmail(String to, String subject, String content, String printMessage) throws UnsupportedEncodingException, IOException {
        String from = "admin@corendon.com";
        final String username = "fysepsilon@gmail.com";//Gmail-username
        final String password = "epsilonfys";//Gmail-password

        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message msg = new MimeMessage(session);
            Multipart multipart = new MimeMultipart("related");
            BodyPart htmlPart = new MimeBodyPart();
            multipart.addBodyPart(htmlPart);
            BodyPart imgPart = new MimeBodyPart();
            multipart.addBodyPart(imgPart);

            //Set unicode UTF-8
            msg.setHeader("Content-Type", "text/html; charset=UTF-8");

            // Set From: header field of the header.
            msg.setFrom(new InternetAddress(from, "Corendon"));

            // attaching the multi-part to the message
            msg.setContent(multipart);

            // Set To: header field of the header.
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            msg.setSubject(subject);

            // Setting the image
            imgPart.setHeader("Content-ID", "corendon-logo");

            htmlPart.setContent(content + "<br/><br/><img style=\"height:54px;width:175px;\"src=\"cid:corendon-logo\"/>",
                    "text/html; charset=UTF-8");

            // Loading the image
            DataSource ds = new FileDataSource("src/fys/images/corendon_logo.png");
            imgPart.setDataHandler(new DataHandler(ds));

            // Send message
            Transport.send(msg);

            System.out.println(printMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param getmessage Het bericht uit de database.
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return getmessage = bericht
     * @throws UnsupportedEncodingException
     * @throws SQLException
     */
    public String replaceEmail(String getmessage, String mail_input) throws UnsupportedEncodingException, SQLException {
        getmessage = getmessage.replace("*username*", mail_input);
        getmessage = getmessage.replace("*password*", Email_Password(mail_input));
        getmessage = getmessage.replace("*firstname*", Email_Firstname(mail_input));
        getmessage = getmessage.replace("*surname*", Email_Surname(mail_input));
        getmessage = getmessage.replace("*address*", Email_Address(mail_input));
        getmessage = getmessage.replace("*residence*", Email_Residence(mail_input));
        getmessage = getmessage.replace("*zip_code*", Email_Zipcode(mail_input));
        getmessage = getmessage.replace("*country*", Email_Country(mail_input));
        getmessage = getmessage.replace("*phone*", Email_Phone(mail_input));

        return getmessage;
    }

    /**
     *
     * @param getmessage Het bericht uit de database
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @param tableFrom tableFrom uit db
     * @return getmessage = bericht
     * @throws UnsupportedEncodingException
     * @throws SQLException
     */
    public String replaceEmail_tF(String getmessage, String mail_input, int tableFrom) throws UnsupportedEncodingException, SQLException {
        getmessage = getmessage.replace("*luggagecolor*", Email_LuggageColor(tableFrom, mail_input));
        getmessage = getmessage.replace("*luggagebrand*", Email_LuggageBrand(tableFrom, mail_input));
        getmessage = getmessage.replace("*luggagetype*", Email_LuggageType(tableFrom, mail_input));
        getmessage = getmessage.replace("*luggagestatus*", Email_LuggageStatus(tableFrom, mail_input));
        return getmessage;
    }

    /**
     *
     * @param type type van een account
     * @param language language van een account
     * @param pageid id van de pagina
     * @return mailOphalen
     * @throws SQLException
     */
    public int Email_Mailid(int type, int language, int pageid) throws SQLException {
        int mailOphalen = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT mailid FROM mail WHERE type = '" + type + "' AND language = '" + language + "' AND pageid = '" + pageid + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getInt("mailid");

                    return mailOphalen;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return mailOphalen;
    }

    /**
     *
     * @param type type van een account
     * @param language language van een account
     * @param pageid id van de pagina
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Subject(int type, int language, int pageid) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT subject FROM mail WHERE type = '" + type + "' AND language = '" + language + "' AND pageid = '" + pageid + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("subject").substring(0, 1).toUpperCase() + rs.getString("subject").substring(1);

                    return mailOphalen;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return mailOphalen;
    }

    /**
     *
     * @param type type van een account
     * @param language language van een account
     * @param pageid id van de pagina
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Message(int type, int language, int pageid) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT message FROM mail WHERE type = '" + type + "' AND language = '" + language + "' AND pageid = '" + pageid + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("message").substring(0, 1).toUpperCase() + rs.getString("message").substring(1);

                    return mailOphalen;
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Password(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT password FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = decrypt(rs.getString("password").substring(0, 1).toUpperCase() + rs.getString("password").substring(1));

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public int Email_Personid(String mail_input) throws SQLException {
        int mailOphalen = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT person_id FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getInt("person_id");

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public int Email_Persontype(String mail_input) throws SQLException {
        int mailOphalen = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT type FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getInt("type");

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public int Email_Language(String mail_input) throws SQLException {
        int mailOphalen = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT language FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getInt("language");

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Firstname(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT first_name FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("first_name").substring(0, 1).toUpperCase() + rs.getString("first_name").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Surname(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT surname FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("surname").substring(0, 1).toUpperCase() + rs.getString("surname").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Address(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT address FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("address").substring(0, 1).toUpperCase() + rs.getString("address").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Shipaddress(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT shipaddress FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("shipaddress").substring(0, 1).toUpperCase() + rs.getString("shipaddress").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Residence(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT residence FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("residence").substring(0, 1).toUpperCase() + rs.getString("residence").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Zipcode(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT zip_code FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("zip_code").substring(0, 1).toUpperCase() + rs.getString("zip_code").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Country(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT country FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("country").substring(0, 1).toUpperCase() + rs.getString("country").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * gestuurd.
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_Phone(String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT phone FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    mailOphalen = rs.getString("phone").substring(0, 1).toUpperCase() + rs.getString("phone").substring(1);

                    return mailOphalen;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    public int PersonStyle(String mail_input) throws SQLException {
        int style = 0;

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT style FROM person WHERE mail='" + mail_input + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    style = rs.getInt("style");

                    return style;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return style;
    }

    /**
     *
     * @param tableFrom tableFrom uit db
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_LuggageColor(int tableFrom, String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            if (tableFrom == 1) {

                String sql = "SELECT color FROM found WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getColor(rs.getInt("color"));

                        return mailOphalen;
                    }
                }
            } else {
                String sql = "SELECT color FROM lost WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getColor(rs.getInt("color"));

                        return mailOphalen;
                    }
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param tableFrom tableFrom uit db
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_LuggageBrand(int tableFrom, String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            if (tableFrom == 1) {
                String sql = "SELECT brand FROM found WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = rs.getString("brand").substring(0, 1).toUpperCase() + rs.getString("brand").substring(1);

                        return mailOphalen;
                    }
                }
            } else {
                String sql = "SELECT brand FROM lost WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = rs.getString("brand").substring(0, 1).toUpperCase() + rs.getString("brand").substring(1);

                        return mailOphalen;
                    }
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param tableFrom tableFrom uit db
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_LuggageType(int tableFrom, String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            if (tableFrom == 1) {
                String sql = "SELECT type FROM found WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getBaggageType(rs.getInt("type"));

                        return mailOphalen;
                    }
                }
            } else {
                String sql = "SELECT type FROM lost WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getBaggageType(rs.getInt("type"));

                        return mailOphalen;
                    }
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param tableFrom tableFrom uit db
     * @param mail_input Het email-adres waar de mail naar toe moet worden
     * @return mailOphalen
     * @throws SQLException
     */
    public String Email_LuggageStatus(int tableFrom, String mail_input) throws SQLException {
        String mailOphalen = "";

        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            if (tableFrom == 1) {
                String sql = "SELECT status FROM found WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getStatus(rs.getInt("status"));

                        return mailOphalen;
                    }
                }
            } else {
                String sql = "SELECT status FROM lost WHERE person_id='"
                        + Email_Personid(mail_input) + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        mailOphalen = getStatus(rs.getInt("status"));

                        return mailOphalen;
                    }
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return mailOphalen;
    }

    /**
     *
     * @param email Controlleer of de emailadres klopt volgens de regels.
     * @return true als de email klopt anders false.
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    //Made a method to decrypt strings.
    public static String decrypt(String message) {
        try {
            byte[] bytesrc = convertHexString(message);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            return null;            // Always must return something
        }

    }

    //Made a method to encrypt strings
    public static String encrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;            // Always must return something
        }
    }

    /**
     *
     * @param ss Zet het wachtwood om naar hexadecimaal.
     * @return the string van hexadecimaal.
     */
    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     *
     * @param b Zet het hexadecimaal weer om naar het wachtwoord
     * @return het wachtwoord
     */
    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    public String convertToDutchDate(String date) {
        final int yearAantal = 4;
        String[] tokens = date.split("-");
        if (tokens[0].length() == yearAantal) {
            return date;
        }
        return tokens[2] + "-" + tokens[1] + "-" + tokens[0];
    }

    public File fileChooser() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Error bij het initialeren van de look en feel van de filechooser:\n" + ex);
        }
        JPanel frame = new JPanel();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG & GIF Images", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile;
            //picture_button.setText(selectedFile.getName());
            //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
