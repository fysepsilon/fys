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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class BagagedatabaseController implements Initializable {
    //@FXML private final TableView<Person> table = new TableView<>();
    @FXML private AnchorPane database_pane, wijzig_pane;
    @FXML private TableView<Bagage> table;
    @FXML private ObservableList<Bagage> data = FXCollections.observableArrayList();
    @FXML private ObservableList<Bagage> datafilter = FXCollections.observableArrayList();
    //@FXML private TableView<Person> table;
    @FXML private TableColumn id, status, type, color, brand, date, information;
    @FXML private TextField colorfilter, brandfilter, datefilter;
    @FXML private ComboBox statusfilter, typefilter;
    @FXML private TextArea characteristicsfilter;
    @FXML private Button filter;
    @FXML private Text status_label, color_label, type_label, brand_label, date_label, extraInfo_label;
    
    @FXML private ComboBox status_combo, airport_combo, type_combo, color_combo;
    @FXML private TextField name_input, surname_input, address_input, 
            residence_input, zipcode_input, country_input, phone_input, 
            mail_input, labelnumber_input, flightnumber_input, destination_input,
            brand_input, characteristics_input;
    @FXML private CheckBox account_checkbox;
    @FXML private Button picture_button, send_button;
    @FXML private Label id_label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        filter.setText(taal[47]);
        status_label.setText(taal[48] + ":");
        color_label.setText(taal[49] + ":");
        type_label.setText(taal[50] + ":");
        brand_label.setText(taal[51] + ":");
        date_label.setText(taal[52] + ":");
        extraInfo_label.setText(taal[53] + ":");
        status.setText(taal[48]);
        color.setText(taal[49]);
        type.setText(taal[50]);
        brand.setText(taal[51]);
        date.setText(taal[52]);
        information.setText(taal[53]);
        statusfilter.getItems().addAll(
                "",
                taal[54],
                taal[55],
                taal[56],
                taal[57],
                taal[58],
                taal[59]);
        typefilter.getItems().addAll(taal[27], taal[28], taal[29], taal[30]);
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
                String type = fys.getBaggageType(rs.getInt("type"));
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
    
    public void handleChange(ActionEvent event) throws IOException {
        int selectedIndex
                = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int dr_id = (table.getSelectionModel().getSelectedItem().id).getValue();
            String dr_status = (table.getSelectionModel().getSelectedItem().status).getValue();
            String dr_airport = (table.getSelectionModel().getSelectedItem().airport).getValue();
            String dr_name = (table.getSelectionModel().getSelectedItem().name).getValue();
            String dr_surname = (table.getSelectionModel().getSelectedItem().surname).getValue();
            String dr_address = (table.getSelectionModel().getSelectedItem().adress).getValue();
            String dr_residence = (table.getSelectionModel().getSelectedItem().residence).getValue();
            String dr_zipcode = (table.getSelectionModel().getSelectedItem().zipcode).getValue();
            String dr_country = (table.getSelectionModel().getSelectedItem().country).getValue();
            String dr_phone = (table.getSelectionModel().getSelectedItem().phone).getValue();
            String dr_mail = (table.getSelectionModel().getSelectedItem().mail).getValue();
            String dr_label = (table.getSelectionModel().getSelectedItem().label).getValue();
            String dr_flight = (table.getSelectionModel().getSelectedItem().flight).getValue();
            String dr_destination = (table.getSelectionModel().getSelectedItem().destination).getValue();
            String dr_type = (table.getSelectionModel().getSelectedItem().type).getValue();
            String dr_brand = (table.getSelectionModel().getSelectedItem().brand).getValue();
            String dr_color = (table.getSelectionModel().getSelectedItem().color).getValue();
            String dr_characteristics = (table.getSelectionModel().getSelectedItem().information).getValue();
            
            doNext(dr_id, dr_status, dr_airport, dr_name, dr_surname, dr_address,
                    dr_residence, dr_zipcode, dr_country, dr_phone, dr_mail,
                    dr_label, dr_flight, dr_destination, dr_type, dr_brand,
                    dr_color, dr_characteristics);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wijzigen van gegevens");
            alert.setContentText("Selecteer eerst een rij in de tabel om deze te wijzigen");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void doNext(int dr_id, String dr_status, String dr_airport, String dr_name,
            String dr_surname, String dr_address, String dr_residence, String dr_zipcode, 
            String dr_country, String dr_phone, String dr_mail, String dr_label, 
            String dr_flight, String dr_destination, String dr_type, String dr_brand, 
            String dr_color, String dr_characteristics){
        
        database_pane.setDisable(true);
        database_pane.setVisible(false);
        wijzig_pane.setDisable(false);
        wijzig_pane.setVisible(true);
        
        id_label.setText(String.valueOf(dr_id));
        status_combo.setValue(dr_status);
        airport_combo.setValue(dr_airport);
        name_input.setText(dr_name);
        surname_input.setText(dr_surname);
        address_input.setText(dr_address);
        residence_input.setText(dr_residence);
        zipcode_input.setText(dr_zipcode);
        country_input.setText(dr_country);
        phone_input.setText(dr_phone);
        mail_input.setText(dr_mail);
        labelnumber_input.setText(dr_label);
        flightnumber_input.setText(dr_flight);
        destination_input.setText(dr_destination);
        type_combo.setValue(dr_type);
        brand_input.setText(dr_brand);
        color_combo.setValue(dr_color);
        characteristics_input.setText(dr_characteristics);
    }
    
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        
        if((name_input.getText() == null || name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (airport_combo.getValue() == null) || (status_combo.getValue() == null)
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zipcode_input.getText() == null || zipcode_input.getText().trim().isEmpty())
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (type_combo.getValue() == null)
                || (brand_input.getText() == null || brand_input.getText().trim().isEmpty())
                || (color_combo.getValue() == null
        )){
            System.out.println("U heeft niet alles ingevuld!");
        } else{            
            sendToDatabase(fys.getStatusString(status_combo.getValue().toString()), airport_combo.getValue().toString(), name_input.getText(), 
                    surname_input.getText(), address_input.getText(), residence_input.getText(), 
                    zipcode_input.getText(), country_input.getText(), phone_input.getText(), 
                    mail_input.getText(), labelnumber_input.getText(), 
                    flightnumber_input.getText(), destination_input.getText(), 
                    fys.getBaggageTypeString(type_combo.getValue().toString()), brand_input.getText(), fys.getColorString(color_combo.getValue().toString()), 
                    characteristics_input.getText());
        }
    }
    
    private void sendToDatabase(int status, String airport, String frontname, String surname, 
            String address, String residence, String zipcode, String country, 
            String phone, String mail, String labelnumber, 
            String flightnumber, String destination, int type, String brand, 
            Integer color, String characteristics) 
            throws IOException, SQLException {
        FYS fys = new FYS();
        
        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            /*String sql_person = "UPDATE bagagedatabase.person_table SET first_name='" + frontname + "',"
                    + "surname='" + surname + "', address='" + address + "',"
                    + "residence='" + residence + "', zip_code='" + zipcode + "',"
                    + "country='" + country + "', phone='" + phone + "',"
                    + "mail='" + mail + "')"; 
                        
            stmt.executeUpdate(sql_person);
            
            String sql_airport = "INSERT INTO bagagedatabase.airport_table (date, "
                    + "time, airport_lost, label_number, flight_number, destination) "
                    + "VALUES ('" + date + "', '" + time + "', '" + airport + "', "
                    + "'" + labelnumber + "', '" + flightnumber + "', '" + destination + "')";
            
            stmt.executeUpdate(sql_airport);
            
            String sql_personID = "SELECT person_id, lost_and_found_id FROM person_table, airport_table WHERE "
                    + "person_table.first_name = '" + frontname + "'AND person_table.surname = '" + surname + "' "
                    + "AND person_table.address = '" + address + "' AND person_table.residence = '" + residence + "' "
                    + "AND person_table.zip_code = '" + zipcode + "' AND person_table.country = '" + country + "' "
                    + "AND person_table.phone = '" + phone + "' AND person_table.mail = '" + mail + "' "
                    + "AND airport_table.date = '" + date + "' AND airport_table.time = '" + time + "' "
                    + "AND airport_table.airport_lost = '" + airport + "' AND airport_table.label_number = '" 
                    + labelnumber + "' AND airport_table.flight_number = '" + flightnumber + "' "
                    + "AND airport_table.destination = '" + destination + "'";
            
            ResultSet id_rs = stmt.executeQuery(sql_personID);
            String personIdStr = null, lostAndFoundIdStr = null;
            int personId = -1, lostAndFoundId = -1;
            while (id_rs.next()) {
                String strA = id_rs.getString("person_id");
                personIdStr = strA.replace("\n", ",");
                personId = Integer.parseInt(personIdStr);
                System.out.println(personId);
                
                String strB = id_rs.getString("lost_and_found_id");
                lostAndFoundIdStr = strB.replace("\n", ",");
                lostAndFoundId = Integer.parseInt(lostAndFoundIdStr);
                System.out.println(lostAndFoundIdStr);
            }   
            
            String sql_lost = "INSERT INTO bagagedatabase.lost_table (type, brand, color, "
                    + "characteristics, status, person_id, lost_and_found_id) VALUES ('" + type + "', "
                    + "'" + brand + "', '" + color + "', '" + characteristics + "', 1, "
                    + "'" + personId + "', '" + lostAndFoundId + "')";*/
            
            String sql_lost = "";
            if(status==0){
                
            }
            else if(status==1){
                sql_lost = "UPDATE bagagedatabase.lost_table SET status='" + status + "',"
                        + "type='" + type + "', brand='" + brand + "',"
                        + "color='" + color + "', characteristics='" + characteristics + "'"
                        + "WHERE id=
            }
            stmt.executeUpdate(sql_lost);
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
