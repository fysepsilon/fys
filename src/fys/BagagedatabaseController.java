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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class BagagedatabaseController implements Initializable {

    //@FXML private final TableView<Person> table = new TableView<>();
    @FXML private TableView<Bagage> table;
    @FXML private ObservableList<Bagage> data = FXCollections.observableArrayList();
    @FXML private ObservableList<Bagage> datafilter = FXCollections.observableArrayList();
    //@FXML private TableView<Person> table;
    @FXML private TableColumn id, status, type, color, brand, date, information;
    @FXML private TextField colorfilter, brandfilter, datefilter;
    @FXML private ComboBox statusfilter, typefilter;
    @FXML private TextArea characteristicsfilter;
//    @FXML private Button filter;
//    @FXML private Label status_label, color_label, type_label, brand_label, date_label, extrainfo_label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        statusfilter.getItems().addAll(
                "",
                "Gevonden",
                "Vermist",
                "Vernietigd",
                "Afgehandeld",
                "Nooit gevonden",
                "Depot");
        getLuggageData();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        id.setStyle("-fx-alignment: CENTER;");
        status.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        information.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }
    
    @FXML
    private void handleFilterAction(ActionEvent event) throws IOException {
        datafilter = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getColor().toLowerCase().contains(colorfilter.getText().toLowerCase())
                    && data.get(i).getBrand().toLowerCase().contains(brandfilter.getText().toLowerCase())
                    && data.get(i).getDate().toLowerCase().contains(datefilter.getText().toLowerCase())
                    && (statusfilter.getSelectionModel().getSelectedItem().toString().toLowerCase().isEmpty() 
                    ? data.get(i).getStatus().toLowerCase().contains(statusfilter.getSelectionModel().getSelectedItem().toString().toLowerCase()) 
                    : data.get(i).getStatus().toLowerCase().equals(statusfilter.getSelectionModel().getSelectedItem().toString().toLowerCase()))
                    && data.get(i).getType().toLowerCase().contains(typefilter.getSelectionModel().getSelectedItem().toString().toLowerCase())
                    && data.get(i).getInformation().toLowerCase().contains(characteristicsfilter.getText().toLowerCase())) {
                datafilter.add(new Bagage(data.get(i).getId(), data.get(i).getStatus(), 
                        data.get(i).getType(), data.get(i).getColor(), data.get(i).getBrand(), data.get(i).getDate(), data.get(i).getInformation()));
            }
        }
        table.setItems(datafilter);
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
                    + "UNION SELECT lost_table.*, airport_table.date FROM lost_table, airport_table "
                    + "WHERE lost_table.lost_and_found_id = airport_table.lost_and_found_id ORDER BY status";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Retrieve by column name
                int id = rs.getInt("id");
                String status =  fys.getStatus(rs.getInt("status"));
                String type = rs.getString("type");
                String color = fys.getColor(rs.getInt("color"));
                String brand = rs.getString("brand");
                String date = rs.getString("date");
                String characteristics = rs.getString("characteristics");

                data.add(new Bagage(luggage, status, type, color, brand, date, characteristics));
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

        @FXML private final SimpleIntegerProperty id;
        @FXML private final SimpleStringProperty status;
        @FXML private final SimpleStringProperty type;
        @FXML private final SimpleStringProperty color;
        @FXML private final SimpleStringProperty brand;
        @FXML private final SimpleStringProperty date;
        @FXML private final SimpleStringProperty information;
        
        private Bagage(Integer idname, String statusname, String typename, String colorname, String brandname, String datename, String informationname) {
            this.id = new SimpleIntegerProperty(idname);
            this.status = new SimpleStringProperty(statusname);
            this.type = new SimpleStringProperty(typename);
            this.color = new SimpleStringProperty(colorname);
            this.brand = new SimpleStringProperty(brandname);
            this.date = new SimpleStringProperty(datename);
            this.information = new SimpleStringProperty(informationname);
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(Integer idname) {
            id.set(idname);
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String statusname) {
            status.set(statusname);
        }

        public String getType() {
            return type.get();
        }

        public void setStudentid(String typename) {
            type.set(typename);
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
        
        public String getDate() {
            return date.get();
        }

        public void setDate(String datename) {
            date.set(datename);
        }
        
        public String getInformation() {
            return information.get();
        }

        public void setInformation(String informationname) {
            information.set(informationname);
        }
    }
    
}
