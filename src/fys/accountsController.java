/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class accountsController implements Initializable {

    //@FXML private final TableView<Person> table = new TableView<>();
    @FXML private TableView<Accounts> table;
    @FXML private ObservableList<Accounts> data = FXCollections.observableArrayList();
    //@FXML private TableView<Person> table;
    @FXML private TableColumn first_name;
    @FXML private TableColumn mail;
    @FXML private TableColumn type;
    @FXML private TableColumn acties;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        getLuggageData();
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        acties.setCellValueFactory(new PropertyValueFactory<>("acties"));
        first_name.setStyle("-fx-alignment: CENTER;");
        mail.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        acties.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    } 
    
    public void getLuggageData() {
        FYS fys = new FYS();
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT * FROM person_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                String first_name = rs.getString("first_name");
                String mail = rs.getString("mail");
                int type = rs.getInt("type");
                String acties = ("Wijzigen/Verwijderen ");
                
                //Display values
//              System.out.print("ID: " + first_name);
//                System.out.print(" status: " + status);
//                System.out.print(" type: " + type);
//                System.out.print(" color: " + color);
//                System.out.print(" brand: " + brand);
//                 System.out.print(" characteristics: " + characteristics);
//                System.out.println();
                data.add(new Accounts(first_name, mail, type, acties));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    public static class Accounts {

        @FXML private final SimpleStringProperty first_name;
        @FXML private final SimpleStringProperty mail;
        @FXML private final SimpleIntegerProperty type;
        @FXML private final SimpleStringProperty acties;
          
        private Accounts(String first_namename, String mailname, Integer typename, String actiesname) {
            this.first_name = new SimpleStringProperty(first_namename);
            this.mail = new SimpleStringProperty(mailname);
            this.type = new SimpleIntegerProperty(typename);
            this.acties = new SimpleStringProperty(actiesname);

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

       
        public Integer getType() {
            return type.get();
        }

        public void setType(Integer typename) {
            type.set(typename);
        }
        
        
        public String getActies() {
            return acties.get();
        }

        public void setActies(String actiesname) {
            acties.set(actiesname);
        }

      }
    
}
