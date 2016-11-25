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
    private TableView<HomepageController.Bagage> table1;
    @FXML
    private ObservableList<HomepageController.Bagage> data = FXCollections.observableArrayList();
    @FXML
    private ObservableList<HomepageController.Bagage> datafilter = FXCollections.observableArrayList();
    @FXML
    private ObservableList<HomepageController.Bagage1> data1 = FXCollections.observableArrayList();
    @FXML
    private ObservableList<HomepageController.Bagage1> datafilter1 = FXCollections.observableArrayList();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                String color = rs.getString("color");
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
    
    public void initialize1(URL url, ResourceBundle rb) {
        getLuggageData1();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }

    public void getLuggageData1() {
        FYS fys = new FYS();
        Statement stmt = null;
        Connection conn = null;
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT lost_table.*, airport_table.date FROM lost_table, airport_table "
                    + "WHERE lost_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "ORDER BY date ASC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Retrieve by column name
                String date1 = rs.getString("date");
                String color1 = rs.getString("color");
                String brand1 = rs.getString("brand");

                data1.add(new HomepageController.Bagage1(date1, color1, brand1));
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

    public static class Bagage1 {

        @FXML
        private final SimpleStringProperty date1;
        @FXML
        private final SimpleStringProperty color1;
        @FXML
        private final SimpleStringProperty brand1;

        private Bagage1(String datename, String colorname, String brandname) {
            this.date1 = new SimpleStringProperty(datename);
            this.color1 = new SimpleStringProperty(colorname);
            this.brand1 = new SimpleStringProperty(brandname);
        }

        public String getDate() {
            return date1.get();
        }

        public void setDate(String datename) {
            date1.set(datename);
        }

        public String getColor() {
            return color1.get();
        }

        public void setCijfer(String colorname) {
            color1.set(colorname);
        }

        public String getBrand() {
            return brand1.get();
        }

        public void setBrand(String brandname) {
            brand1.set(brandname);
        }
    }
    
}
