/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import fys.FYS;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Veron
 */
public class HomepageController implements Initializable {

    //@FXML private final TableView<Person> table = new TableView<>();
    @FXML
    private TableView<HomepageController.Bagage> table;
    @FXML
    private ObservableList<HomepageController.Bagage> data = FXCollections.observableArrayList();
    @FXML
    private ObservableList<HomepageController.Bagage> datafilter = FXCollections.observableArrayList();
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn color;
    @FXML
    private TableColumn brand;
     @FXML
    private TableColumn date1;
    @FXML
    private TableColumn color1;
    @FXML
    private TableColumn brand1;
    @FXML
    private Label recentlabel;
    @FXML
    private Label lostlabel;
    @FXML
    private Label foundlabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        recentlabel.setText(taal[106]);
        lostlabel.setText(taal[55]);
        foundlabel.setText(taal[54]);
        date.setText(taal[52]);       
        color.setText(taal[49]);
        brand.setText(taal[51]);
        date1.setText(taal[52]);       
        color1.setText(taal[49]);
        brand1.setText(taal[51]);
        
        getLuggageData();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
      
        
    }

    public void getLuggageData() {
        FYS fys = new FYS();
        Statement stmt = null;
        Connection conn = null;
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT found_table.*, airport_table.date FROM found_table, airport_table "
                    + "WHERE found_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "ORDER BY date ASC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Retrieve by column name
                String date = rs.getString("date");
                String color = fys.getColor(rs.getInt("color"));
                String brand = rs.getString("brand");

                data.add(new HomepageController.Bagage(date, color, brand));
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

    public static class Bagage {

        @FXML
        private final SimpleStringProperty date;
        @FXML
        private final SimpleStringProperty color;
        @FXML
        private final SimpleStringProperty brand;

        private Bagage(String datename, String colorname, String brandname) {
            this.date = new SimpleStringProperty(datename);
            this.color = new SimpleStringProperty(colorname);
            this.brand = new SimpleStringProperty(brandname);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String datename) {
            date.set(datename);
        }

        public String getColor() {
            return color.get();
        }

        public void setCijfer(String colorname) {
            color.set(colorname);
        }

        public String getBrand() {
            return brand.get();
        }

        public void setBrand(String brandname) {
            brand.set(brandname);
        }
    }
    
}
