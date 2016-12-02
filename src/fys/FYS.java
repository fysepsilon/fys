/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

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
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Paras
 */
public class FYS extends Application {
    
    private static Stage parentWindow;
    private static final String key = "1234abcd";
    private final static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    
    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage; 
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Corendon-Login");
        stage.getIcons().add(new Image("http://www.corendon.com/favicon.png"));
        stage.show();  
    }
    
    //Made a method to switch to FXML screens.
    public void changeToAnotherFXML(String title, String changeToWindow) throws IOException{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource(changeToWindow));
        Stage mainStage;
        mainStage = FYS.parentWindow;
        mainStage.setTitle(title);
        mainStage.setResizable(false);
        mainStage.getScene().setRoot(window1);
    }
    
    public static String generateRandomPassword(int length) {
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
    
    public String getUserFunction(int type){
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type == 1) {
            return taal[64];
        } else if (type == 2) {
            return taal[65];
        } 
        return taal[66];
    }
    
    public Integer getUserFunctionString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[64])) {
            return 1;
        } else if (type.equals(taal[65])) {
            return 2;
        }
        return 0;
    }
    
     public String getUserLanguage(int type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type == 1) {
            return taal[70];
        } else if (type == 2) {
            return taal[71];
        } else if (type == 3) {
            return taal[72];
        }
        return taal[69];
    }
     
    public Integer getUserLanguageString(String type) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (type.equals(taal[70])) {
            return 1;
        } else if (type.equals(taal[71])) {
            return 2;
        } else if (type.equals(taal[72])) {
            return 3;
        }  
        return 0;
    }
    
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
    
    public Integer getStatusString(String status) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (status.equals(taal[55])){
            return 1;
        } else if(status.equals(taal[56])){
            return 2;
        } else if(status.equals(taal[57])){
            return 3;
        } else if(status.equals(taal[58])){
            return 4;
        } else if(status.equals(taal[59])){
            return 5;
        }  else if(status.equals(taal[108])){
            return 6;
        } else {
            return 0;
        }
    }
    
    public String getMonthNumber(String month) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (month.equals(taal[109])) {
            return "01";
        } else if (month.equals(taal[110])) {
            return "02";
        } else if (month.equals(taal[111])) {
            return "03";
        } else if (month.equals(taal[112])) {
            return "04";
        } else if (month.equals(taal[113])) {
            return "05";
        } else if (month.equals(taal[114])) {
            return "06";
        } else if (month.equals(taal[115])) {
            return "07";
        } else if (month.equals(taal[116])) {
            return "08";
        } else if (month.equals(taal[117])) {
            return "09";
        } else if (month.equals(taal[118])) {
            return "10";
        } else if (month.equals(taal[119])) {
            return "11";
        } else if (month.equals(taal[120])) {
            return "12";
        }
        return "";
    }
    
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

    public Integer getColorString(String color) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        if (color.equals(taal[33])){
            return 1;
        } else if (color.equals(taal[34])){
            return 2;
        } else if (color.equals(taal[35])){
            return 2;
        } else if (color.equals(taal[36])){
            return 2;
        } else if (color.equals(taal[37])){
            return 2;
        } else if (color.equals(taal[38])){
            return 2;
        } else if (color.equals(taal[39])){
            return 2;
        } else if (color.equals(taal[40])){
            return 2;
        } else if (color.equals(taal[41])){
            return 2;
        } else if (color.equals(taal[42])){
            return 2;
        } else if (color.equals(taal[43])){
            return 2;
        } else {
            return 0;
        }
    }
    
    public boolean checkEmailExists(String email){
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM person_table "
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
    
        public boolean checkEmailExistsOnChange(String email, String emailWas){
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM person_table"
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
        
    public void sendEmail(String to, String subject, String content, String printMessage) throws UnsupportedEncodingException{
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
            Message message = new MimeMessage(session);
            
            //Set unicode UTF-8
            message.setHeader("Content-Type", "text/html; charset=UTF-8");

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from, "Corendon"));


            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(content, "text/html; charset=UTF-8");

            // Send message
            Transport.send(message);

            System.out.println(printMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
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
        try{
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
        } catch(Exception e){
            return null;            // Always must return something
        }
    }
    
    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }
    
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
