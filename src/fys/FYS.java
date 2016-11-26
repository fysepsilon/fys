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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Paras
 */
public class FYS extends Application {
    
    private static Stage parentWindow;
    private static final String key = "1234abcd";
    private final static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    
    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage; 
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Corendon-Login");
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
        if (type == 1) {
            return "Servicemedewerker";
        } else if (type == 2) {
            return "Admin";
        } 
        return "Klant";
    }
    
    public Integer getUserFunctionString(String type) {
        if (type.equals("Servicemedewerker")) {
            return 1;
        } else if (type == "Admin") {
            return 2;
        }
        return 0;
    }
    
    public Integer getUserLanguageString(String type) {
        if (type.equals("Nederlands")) {
            return 1;
        } else if (type == "Spaans") {
            return 2;
        } else if (type == "Turks") {
            return 3;
        }  
        return 0;
    }
    
    public String getStatus(int status) {
        switch (status) {
            case 1:
                return "Vermist";
            case 2:
                return "Vernietigd";
            case 3:
                return "Afgehanded";
            case 4:
                return "Nooit gevonden";
            case 5:
                return "Depot";
            default:
                break;
        }
        return "Gevonden";
    }
    
    public Integer getStatusString(String status) {
        switch (status) {
            case "Vermist":
                return 1;
            case "Vernietigd":
                return 2;
            case "Afgehanded":
                return 3;
            case "Nooit gevonden":
                return 4;
            case "Depot":
                return 5;
            default:
                break;
        }
        return 0;
    }
    
    public String getColor(int color) {
        switch (color) {
            case 1:
                return "Beige";
            case 2:
                return "Rood";
            case 3:
                return "Oranje";
            case 4:
                return "Geel";
            case 5:
                return "Groen";
            case 6:
                return "Blauw";
            case 7:
                return "Paars";
            case 8:
                return "Roze";
            case 9:
                return "Zwart";
            case 10:
                return "Grijs";
            case 11:
                return "Wit";    
            default:
                break;
        }
        return "Bruin";
    }

    public Integer getColorString(String color) {
        switch (color) {
            case "Beige":
                return 1;
            case "Rood":
                return 2;
            case "Oranje":
                return 3;
            case "Geel":
                return 4;
            case "Groen":
                return 5;
            case "Blauw":
                return 6;
            case "Paars":
                return 7;
            case "Roze":
                return 8;
            case "Zwart":
                return 9;
            case "Grijs":
                return 10;
            case "Wit":
                return 11;    
            default:
                break;
        }
        return 0;
    }
    
    //Conect to database.
    public Connection connectToDatabase(Connection conn) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/bagagedatabase?user=root&password=root");
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

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from, "Corendon"));


            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(content, "text/html");

            // Send message
            Transport.send(message);

            System.out.println(printMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
