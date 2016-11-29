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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class accountsController implements Initializable {

    @FXML private Button NewAccountButton;
    @FXML private TableView<Accounts> table;
    @FXML private ObservableList<Accounts> data = FXCollections.observableArrayList();
    @FXML private TableColumn first_name, mail, type;

    @FXML
    private void handlenieuwaccount(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        taal language = new taal();
        String[] taal = language.getLanguage();
        fys.changeToAnotherFXML(taal[63], "nieuwaccountaanmaken.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        first_name.setText(taal[9]);
        mail.setText(taal[16]);
        type.setText(taal[20]);
        NewAccountButton.setText(taal[63]);
        
        getLuggageData();
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        first_name.setStyle("-fx-alignment: CENTER;");
        mail.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }

    public void getLuggageData() {
        taal language = new taal();
        String[] taal = language.getLanguage();
        FYS fys = new FYS();
        String type_text;
        
        loginController loginController = new loginController();
        if (loginController.getUsertype() == 2) { //Show button bij administrator (type = 2)
            NewAccountButton.setVisible(true);
        }
        
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");  
            if (loginController.getUsertype() != 1) { //SQL bij administrator (type = 2)
                String sql = "SELECT * FROM bagagedatabase.person_table";
                
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        String first_name = rs.getString("first_name");
                        String mail = rs.getString("mail");
                        int type = rs.getInt("type");
                        type_text = fys.getUserFunction(type);
                        
                        data.add(new Accounts(first_name, mail, type_text));
                    }
                }
            } else { //SQL bij servicemedewerker (type = 1)
                String sql = "SELECT * FROM bagagedatabase.person_table WHERE type = '0'";
                
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        String first_name = rs.getString("first_name");
                        String mail = rs.getString("mail");
                        int type = rs.getInt("type");
                        type_text = fys.getUserFunction(type);
                        
                        data.add(new Accounts(first_name, mail, type_text));
                    }
                }
            }
            conn.close();
                        
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static class Accounts {

        @FXML
        private final SimpleStringProperty first_name;
        @FXML
        private final SimpleStringProperty mail;
        @FXML
        private final SimpleStringProperty type;

        private Accounts(String first_namename, String mailname, String typename) {
            this.first_name = new SimpleStringProperty(first_namename);
            this.mail = new SimpleStringProperty(mailname);
            this.type = new SimpleStringProperty(typename);
        }

        public String getFirst_name() {
            return first_name.get();
        }

        public void setFirst_name(String first_namename) {
            first_name.set(first_namename);
        }

        public String getMail() {
            return mail.get();
        }

        public void setMail(String mailname) {
            mail.set(mailname);
        }

        public String getType() {
            return type.get();
        }

        public void setType(String typename) {
            type.set(typename);
        }
    }
}
