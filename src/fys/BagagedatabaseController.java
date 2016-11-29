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
    @FXML private TableColumn id, status, type, color, brand, date, information, 
    first_name, surname, address, residence, zipcode, country, phone, mail, 
    labelnumber, flightnumber, destination, airportFound, airportLost, tableFrom, lostAndFoundID,
             personID, realid;
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
    @FXML private Label id_label, personId_label, lafId_label, tableFrom_label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taal language = new taal();
        String[] taal = language.getLanguage();
        filter.setText(taal[47]);
        colorfilter.setPromptText(taal[49]);
        brandfilter.setPromptText(taal[51]);
        datefilter.setPromptText(taal[52]);
        characteristicsfilter.setPromptText(taal[53]);
        status.setText(taal[48]);
        color.setText(taal[49]);
        type.setText(taal[50]);
        brand.setText(taal[51]);
        date.setText(taal[52]);
        information.setText(taal[53]);
        first_name.setText(taal[9]);
        surname.setText(taal[10]);
        address.setText(taal[11]);
        residence.setText(taal[12]);
        zipcode.setText(taal[13]);
        country.setText(taal[14]);
        phone.setText(taal[15]);
        mail.setText(taal[16]);
        labelnumber.setText(taal[17]);
        flightnumber.setText(taal[18]);
        destination.setText(taal[19]);
        airportFound.setText(taal[8] + " " + taal[54]);
        airportLost.setText(taal[8] + " " + taal[55]);
        statusfilter.getItems().addAll(
                "",
                taal[54],
                taal[55],
                taal[56],
                taal[57],
                taal[58],
                taal[59]);
        typefilter.getItems().addAll(taal[27], taal[28], taal[29], taal[30]);
        type_combo.setPromptText(taal[26]);
        color_combo.setPromptText(taal[31]);
        color_combo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        type_combo.getItems().addAll(taal[27], taal[28], taal[29], taal[30]);
        status_combo.getItems().addAll(
                taal[54],
                taal[55],
                taal[56],
                taal[57],
                taal[58],
                taal[59]);
        getLuggageData();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        residence.setCellValueFactory(new PropertyValueFactory<>("residence"));
        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        labelnumber.setCellValueFactory(new PropertyValueFactory<>("labelnumber"));
        flightnumber.setCellValueFactory(new PropertyValueFactory<>("flightnumber"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        airportFound.setCellValueFactory(new PropertyValueFactory<>("airportFound"));
        airportLost.setCellValueFactory(new PropertyValueFactory<>("airportLost"));
        tableFrom.setCellValueFactory(new PropertyValueFactory<>("tableFrom"));
        lostAndFoundID.setCellValueFactory(new PropertyValueFactory<>("lostAndFoundID"));
        personID.setCellValueFactory(new PropertyValueFactory<>("personID"));
        realid.setCellValueFactory(new PropertyValueFactory<>("realid"));
        id.setStyle("-fx-alignment: CENTER;");
        status.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        information.setStyle("-fx-alignment: CENTER;");
        first_name.setStyle("-fx-alignment: CENTER;");
        surname.setStyle("-fx-alignment: CENTER;");
        address.setStyle("-fx-alignment: CENTER;");
        residence.setStyle("-fx-alignment: CENTER;");
        zipcode.setStyle("-fx-alignment: CENTER;");
        country.setStyle("-fx-alignment: CENTER;");
        phone.setStyle("-fx-alignment: CENTER;");
        mail.setStyle("-fx-alignment: CENTER;");
        labelnumber.setStyle("-fx-alignment: CENTER;");
        flightnumber.setStyle("-fx-alignment: CENTER;");
        destination.setStyle("-fx-alignment: CENTER;");
        airportFound.setStyle("-fx-alignment: CENTER;");
        airportLost.setStyle("-fx-alignment: CENTER;");
        tableFrom.setStyle("-fx-alignment: CENTER;");
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
                        data.get(i).getType(), data.get(i).getColor(), data.get(i).getBrand(), 
                        data.get(i).getDate(), data.get(i).getInformation(), data.get(i).getFirst_name(),
                 data.get(i).getSurname(), data.get(i).getAddress(), data.get(i).getResidence(), data.get(i).getZipcode(), 
                data.get(i).getCountry(), data.get(i).getPhone(), data.get(i).getMail(), data.get(i).getLabelnumber(), 
                data.get(i).getFlightnumber(), data.get(i).getDestination(), data.get(i).getAirportFound(), data.get(i).getAirportLost(), 
                        data.get(i).getTableFrom(), data.get(i).getLostAndFoundID(), data.get(i).getPersonID(), data.get(i).getRealid()));
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
            String sql = "SELECT found_table.*, airport_table.date, airport_table.airport_found , airport_table.airport_lost, "
                    + "airport_table.label_number, airport_table.flight_number, airport_table.destination, "
                    + "person_table.first_name, person_table.surname, person_table.address, person_table.zip_code,"
                    + "person_table.residence, person_table.country, person_table.phone, person_table.mail, 1 as tablefrom "
                    + "FROM found_table, airport_table, person_table WHERE found_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "AND found_table.person_id = person_table.person_id "
                    + "UNION SELECT lost_table.*, airport_table.date, airport_table.airport_lost, airport_table.airport_found, airport_table.label_number, "
                    + "airport_table.flight_number, airport_table.destination, person_table.first_name, person_table.surname, "
                    + "person_table.address, person_table.zip_code, person_table.residence, person_table.country, person_table.phone, person_table.mail, 0 as tablefrom "
                    + "FROM lost_table, airport_table, person_table WHERE lost_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "AND lost_table.person_id = person_table.person_id "
                    + "ORDER BY status";
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
                String firstname = rs.getString("first_name");
                String surname = rs.getString("surname");
                String address = rs.getString("address");
                String residence = rs.getString("residence");
                String zipcode = rs.getString("zip_code");
                String country = rs.getString("country");
                String phone = rs.getString("phone");
                String mail = rs.getString("mail");
                String labelnumber = rs.getString("label_number");
                String flightnumber = rs.getString("flight_number");
                String destination = rs.getString("destination");
                String airportfound = rs.getString("airport_found");
                String airportlost = rs.getString("airport_lost");
                String tablefrom = rs.getString("tablefrom");
                int lostAndFoundID = rs.getInt("lost_and_found_id");
                int personID = rs.getInt("person_id");
                
                data.add(new Bagage(luggage, status, type, color, brand, date, characteristics, firstname,
                 surname, address, residence, zipcode, country, phone, mail, labelnumber, flightnumber, destination,
                 airportfound, airportlost, tablefrom, lostAndFoundID, personID, id));
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
    
    public void handleChange(ActionEvent event) throws IOException {
        taal language = new taal();
        String[] taal = language.getLanguage();
        int selectedIndex
                = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int dr_id = (table.getSelectionModel().getSelectedItem().getRealid());
            int dr_personId = (table.getSelectionModel().getSelectedItem().getPersonid());
            int dr_lafId = (table.getSelectionModel().getSelectedItem().getLost_and_found_id());
            int dr_tableFrom = (table.getSelectionModel().getSelectedItem().getTableFrom());
            String dr_status = (table.getSelectionModel().getSelectedItem().getStatus());
            String dr_airport = (table.getSelectionModel().getSelectedItem().getAirportFound());
            String dr_name = (table.getSelectionModel().getSelectedItem().getFirst_name());
            String dr_surname = (table.getSelectionModel().getSelectedItem().getSurname());
            String dr_address = (table.getSelectionModel().getSelectedItem().getAddress());
            String dr_residence = (table.getSelectionModel().getSelectedItem().getResidence());
            String dr_zipcode = (table.getSelectionModel().getSelectedItem().getZipcode());
            String dr_country = (table.getSelectionModel().getSelectedItem().getCountry());
            String dr_phone = (table.getSelectionModel().getSelectedItem().getPhone());
            String dr_mail = (table.getSelectionModel().getSelectedItem().getMail());
            String dr_label = (table.getSelectionModel().getSelectedItem().getLabelnumber());
            String dr_flight = (table.getSelectionModel().getSelectedItem().getFlightnumber());
            String dr_destination = (table.getSelectionModel().getSelectedItem().getDestination());
            String dr_type = (table.getSelectionModel().getSelectedItem().getType());
            String dr_brand = (table.getSelectionModel().getSelectedItem().getBrand());
            String dr_color = (table.getSelectionModel().getSelectedItem().getColor());
            String dr_characteristics = (table.getSelectionModel().getSelectedItem().getInformation());
            
            doNext(dr_id, dr_personId, dr_lafId, dr_tableFrom, dr_status, dr_airport, dr_name, dr_surname, dr_address,
                    dr_residence, dr_zipcode, dr_country, dr_phone, dr_mail,
                    dr_label, dr_flight, dr_destination, dr_type, dr_brand,
                    dr_color, dr_characteristics);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[104]);
            alert.setContentText(taal[105]);
            alert.showAndWait();
        }
    }
    
    @FXML
    public void doNext(int dr_id, int dr_personId, int dr_lafId, int dr_tableFrom, String dr_status, String dr_airport, String dr_name,
            String dr_surname, String dr_address, String dr_residence, String dr_zipcode, 
            String dr_country, String dr_phone, String dr_mail, String dr_label, 
            String dr_flight, String dr_destination, String dr_type, String dr_brand, 
            String dr_color, String dr_characteristics){
        
        database_pane.setDisable(true);
        database_pane.setVisible(false);
        wijzig_pane.setDisable(false);
        wijzig_pane.setVisible(true);
        
        id_label.setText(String.valueOf(dr_id));
        personId_label.setText(String.valueOf(dr_personId));
        lafId_label.setText(String.valueOf(dr_lafId));
        tableFrom_label.setText(String.valueOf(dr_tableFrom));
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
            sendToDatabase(Integer.parseInt(id_label.getText()), Integer.parseInt(personId_label.getText()), Integer.parseInt(lafId_label.getText()), Integer.parseInt(tableFrom_label.getText()), fys.getStatusString(status_combo.getValue().toString()), airport_combo.getValue().toString(), name_input.getText(), 
                    surname_input.getText(), address_input.getText(), residence_input.getText(), 
                    zipcode_input.getText(), country_input.getText(), phone_input.getText(), 
                    mail_input.getText(), labelnumber_input.getText(), 
                    flightnumber_input.getText(), destination_input.getText(), 
                    fys.getBaggageTypeString(type_combo.getValue().toString()), brand_input.getText(), fys.getColorString(color_combo.getValue().toString()), 
                    characteristics_input.getText());
        }
    }
    
    private void sendToDatabase(int dr_id, int dr_personId, int dr_lafId, int tableFrom, int status, String airport, String frontname, String surname, 
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
            String sql_person = "UPDATE bagagedatabase.person_table SET first_name='" + frontname + "',"
                    + "surname='" + surname + "', address='" + address + "',"
                    + "residence='" + residence + "', zip_code='" + zipcode + "',"
                    + "country='" + country + "', phone='" + phone + "',"
                    + "mail='" + mail + "'"
                    + "WHERE person_id='" + dr_personId + "'"; 
                        
            stmt.executeUpdate(sql_person);
            
            String sql_lost = "";
            String sql_airport = "";
            System.out.println(status);
            if(tableFrom==0){   //0 = bagagedatabase.lost_table
                sql_lost = "UPDATE bagagedatabase.found_table SET status='" + status + "',"
                        + "type='" + type + "', brand='" + brand + "',"
                        + "color='" + color + "', characteristics='" + characteristics + "'"
                        + "WHERE id='" + dr_id + "'";
                
                switch (status){
                    //case 0: Gaat van lost_table naar status Gevonden
                    case 0: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_found='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                    //case 3: Gaat van lost_table naar status Afgehandeld
                    case 3: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_found='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                    //default: Gaat van lost_table naar status Vermist, Vernietigd, Nooit Gevonden of Depot 
                    default: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_lost='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                }         
            }
            else if(tableFrom==1){  //1 = bagagedatabase.found_table
                sql_lost = "UPDATE bagagedatabase.lost_table SET status='" + status + "',"
                        + "type='" + type + "', brand='" + brand + "',"
                        + "color='" + color + "', characteristics='" + characteristics + "'"
                        + "WHERE id='" + dr_id + "'";
                
                switch (status) {
                    //case 1: Gaat van found_table naar status Vermist
                    case 1: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_lost='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                    //case 3: Gaat van found_table naar status Afgehandeld
                    case 3: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_lost='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                    //default: Gaat van found_table naar status Gevonden, Vernietigd, Nooit gevonden, Depot 
                    default: sql_airport = "UPDATE bagagedatabase.airport_table SET airport_found='" + airport + "',"
                            + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                            + "destination='" + destination + "'"
                            + "WHERE lost_and_found_id='" + dr_lafId + "'";
                            break;
                }
            }
            stmt.executeUpdate(sql_lost);
            stmt.executeUpdate(sql_airport);
            
            if(status==3){
                //TODO maak DHL-shipment formulier.
            }
            
            taal languages = new taal();
            String[] taal = languages.getLanguage(); 
            fys.changeToAnotherFXML(taal[100], "bagagedatabase.fxml");
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
