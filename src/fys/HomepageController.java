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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author Team Epsilon
 */
public class HomepageController implements Initializable {

    //@FXML private final TableView<Person> table = new TableView<>();
    @FXML private TableView<Bagage> table;
    @FXML private ObservableList<Bagage> data = FXCollections.observableArrayList(), datafilter = FXCollections.observableArrayList();
    @FXML private TableView<Bagage> table1;
    @FXML private ObservableList<Bagage> data1 = FXCollections.observableArrayList();
    @FXML private TableColumn date, color, brand, time, date1, color1, brand1, time1;
    @FXML private Label recentlabel, lostlabel, foundlabel;
    @FXML private FYS fys = new FYS();
    @FXML private Statement stmt = null;
    @FXML private Connection conn = null;
    @FXML private taal language = new taal();
    @FXML private String[] taal = language.getLanguage();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recentlabel.setText(taal[106]);
        lostlabel.setText(taal[55]);
        foundlabel.setText(taal[54]);
        
        date.setText(taal[52]);  
        time.setText(taal[107]);
        color.setText(taal[49]);
        brand.setText(taal[51]);
        date1.setText(taal[52]);  
        time1.setText(taal[107]);
        color1.setText(taal[49]);
        brand1.setText(taal[51]);

        
        getLuggageData();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date.setStyle("-fx-alignment: CENTER;");
        time.setStyle("-fx-alignment: CENTER;");        
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
      
        getLuggageData1();
        date1.setCellValueFactory(new PropertyValueFactory<>("date"));
        time1.setCellValueFactory(new PropertyValueFactory<>("time"));
        color1.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand1.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date1.setStyle("-fx-alignment: CENTER;");
        time1.setStyle("-fx-alignment: CENTER;");        
        color1.setStyle("-fx-alignment: CENTER;");
        brand1.setStyle("-fx-alignment: CENTER;");
        table1.setItems(data1);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateTimeString = dateFormat.format(date);
        String[] tokens = dateTimeString.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException();
        }
        tokens = tokens[0].split("-");
        String year = tokens[0];
        String months = tokens[1];
    }

    public void getLuggageData() {
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT found.*, airport.date,time FROM found, airport "
                    + "WHERE found.lost_and_found_id = airport.lost_and_found_id "
                    + "ORDER BY date DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Retrieve by column name
                String date = rs.getString("date");
                String time = rs.getString("time");
                String color = fys.getColor(rs.getInt("color"));
                String brand = rs.getString("brand");

                data.add(new Bagage(date, time, color, brand));
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
    
    public void getLuggageData1() {
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT lost.*, airport.date,time FROM lost, airport "
                    + "WHERE lost.lost_and_found_id = airport.lost_and_found_id "
                    + "ORDER BY date DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Retrieve by column name
                String date1 = rs.getString("date");
                String time1 = rs.getString("time");
                String color1 = fys.getColor(rs.getInt("color"));
                String brand1 = rs.getString("brand");

                data1.add(new Bagage(date1, time1, color1, brand1));
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
}
