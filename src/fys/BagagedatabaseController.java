/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class BagagedatabaseController implements Initializable {

    @FXML
    private AnchorPane database_pane, wijzig_pane;
    @FXML
    private Pane filterPane;
    @FXML
    private TableView<Bagage> table;
    @FXML
    private ObservableList<Bagage> data = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Bagage> dataFilter = FXCollections.observableArrayList();
    @FXML
    private TableColumn id, status, type, color, brand, picture, date, information,
            firstName, surName, address, residence, zipcode, country, phone, mail,
            labelNumber, flightNumber, destination, airportFound, airportLost, tableFrom, lostAndFoundID,
            personID, realId;
    @FXML
    private TextField colorFilter, brandFilter, dateFilter;
    @FXML
    private ComboBox statusFilter, typeFilter;
    @FXML
    private TextArea characteristicsFilter;
    @FXML
    private Button filter;
    @FXML
    private ComboBox statusCombo, airportCombo, typeCombo, colorCombo, destination_combo;
    @FXML
    private TextField nameInput, surNameInput, addressInput,
            residenceInput, zipcodeInput, countryInput, phoneInput,
            mailInput, labelNumberInput, flightNumberInput,
            brandInput, characteristicsInput;
    @FXML
    private Button pictureButton, sendButton, cancelButton, changeButton, removeButton;
    @FXML
    private Label mailLabel, phoneLabel, countryLabel, zipcodeLabel,
            residenceLabel, addressLabel, surNameLabel, nameLabel, idLabel,
            airportLabel, labelLabel, flightLabel, destinationLabel,
            typeLabel, brandLabel, colorLabel, characteristicsLabel,
            pictureLabel, statusLabel, personIdLabel, lafIdLabel,
            tableFromLabel, loginerror, inklapLabel, uitklapLabel;
    @FXML
    private final FYS fys = new FYS();
    @FXML
    private final taal language = new taal();
    @FXML
    private final String[] taal = language.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;
    public String filePath = null;
    public String dr_mail = "";
    public String dr_status = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Zet alle labels, buttons, tabellen in de taal die is geinstalleerd.
        filter.setText(taal[47]);
        colorFilter.setPromptText(taal[49]);
        brandFilter.setPromptText(taal[51]);
        dateFilter.setPromptText(taal[52]);
        characteristicsFilter.setPromptText(taal[53]);
        status.setText(taal[48]);
        color.setText(taal[49]);
        type.setText(taal[50]);
        brand.setText(taal[51]);
        picture.setText(taal[24]);
        date.setText(taal[52]);
        information.setText(taal[23]);
        firstName.setText(taal[9]);
        surName.setText(taal[10]);
        address.setText(taal[11]);
        residence.setText(taal[12]);
        zipcode.setText(taal[13]);
        country.setText(taal[14]);
        phone.setText(taal[15]);
        mail.setText(taal[16]);
        labelNumber.setText(taal[17]);
        flightNumber.setText(taal[18]);
        destination.setText(taal[19]);

        sendButton.setText(taal[46]);
        cancelButton.setText(taal[127]);
        changeButton.setText(taal[67]);
        removeButton.setText(taal[156]);

        airportLabel.setText(taal[8] + ":");
        nameLabel.setText(taal[9] + ":");
        surNameLabel.setText(taal[10] + ":");
        addressLabel.setText(taal[11] + ":");
        residenceLabel.setText(taal[12] + ":");
        zipcodeLabel.setText(taal[13] + ":");
        countryLabel.setText(taal[14] + ":");
        phoneLabel.setText(taal[15] + ":");
        mailLabel.setText(taal[16] + ":");
        labelLabel.setText(taal[17] + ":");
        flightLabel.setText(taal[18] + ":");
        destinationLabel.setText(taal[19] + ":");
        characteristicsLabel.setText(taal[23] + ":");
        pictureLabel.setText(taal[24] + ":");
        statusLabel.setText(taal[48] + ":");
        colorLabel.setText(taal[49] + ":");
        typeLabel.setText(taal[50] + ":");
        brandLabel.setText(taal[51] + ":");
        airportCombo.setPromptText(taal[25]);
        pictureButton.setText(taal[44]);

        airportFound.setText(taal[8] + " " + taal[54]);
        airportLost.setText(taal[8] + " " + taal[55]);
        statusFilter.getItems().addAll(
                "",
                taal[54],
                taal[55],
                taal[56],
                taal[57],
                taal[58],
                taal[59],
                taal[108]);
        typeFilter.getItems().addAll(taal[27], taal[28], taal[29], taal[30]);
        typeCombo.setPromptText(taal[26]);
        colorCombo.setPromptText(taal[31]);
        colorCombo.getItems().addAll(
                taal[32], taal[33], taal[34], taal[35], taal[36],
                taal[37], taal[38], taal[39], taal[40], taal[41], taal[42], taal[43]);
        typeCombo.getItems().addAll(taal[27], taal[28], taal[29], taal[30]);
        statusCombo.getItems().addAll(
                taal[54],
                taal[55],
                taal[56],
                taal[57],
                taal[58],
                taal[59],
                taal[108]);
        getLuggageData();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surName.setCellValueFactory(new PropertyValueFactory<>("surName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        residence.setCellValueFactory(new PropertyValueFactory<>("residence"));
        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        labelNumber.setCellValueFactory(new PropertyValueFactory<>("labelNumber"));
        flightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        airportFound.setCellValueFactory(new PropertyValueFactory<>("airportFound"));
        airportLost.setCellValueFactory(new PropertyValueFactory<>("airportLost"));
        tableFrom.setCellValueFactory(new PropertyValueFactory<>("tableFrom"));
        lostAndFoundID.setCellValueFactory(new PropertyValueFactory<>("lostAndFoundID"));
        personID.setCellValueFactory(new PropertyValueFactory<>("personID"));
        realId.setCellValueFactory(new PropertyValueFactory<>("realId"));
        id.setStyle("-fx-alignment: CENTER;");
        status.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        color.setStyle("-fx-alignment: CENTER;");
        brand.setStyle("-fx-alignment: CENTER;");
        picture.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        information.setStyle("-fx-alignment: CENTER;");
        firstName.setStyle("-fx-alignment: CENTER;");
        surName.setStyle("-fx-alignment: CENTER;");
        address.setStyle("-fx-alignment: CENTER;");
        residence.setStyle("-fx-alignment: CENTER;");
        zipcode.setStyle("-fx-alignment: CENTER;");
        country.setStyle("-fx-alignment: CENTER;");
        phone.setStyle("-fx-alignment: CENTER;");
        mail.setStyle("-fx-alignment: CENTER;");
        labelNumber.setStyle("-fx-alignment: CENTER;");
        flightNumber.setStyle("-fx-alignment: CENTER;");
        destination.setStyle("-fx-alignment: CENTER;");
        airportFound.setStyle("-fx-alignment: CENTER;");
        airportLost.setStyle("-fx-alignment: CENTER;");
        tableFrom.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
        destination_combo.setPromptText(taal[25]);
        inklapLabel.setText("\u21f1");
        uitklapLabel.setText("\u21f2");
        uitklapLabel.setDisable(true);
        uitklapLabel.setVisible(false);
    }
    
    //Wanneer er op de button filteren wordt geklikt.
    @FXML
    private void handleFilterAction(ActionEvent event) throws IOException {
        //Altijd wanneer de filter button wordt geklikt maak de array leeg.
        dataFilter = FXCollections.observableArrayList();

        //Controleer voor elk veld of het gelijk is met de filter velden.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getColor().toLowerCase().contains(colorFilter.getText().toLowerCase())
                    && data.get(i).getBrand().toLowerCase().contains(brandFilter.getText().toLowerCase())
                    && data.get(i).getDate().toLowerCase().contains(dateFilter.getText().toLowerCase())
                    && (statusFilter.getSelectionModel().getSelectedItem().toString().toLowerCase().isEmpty()
                            ? data.get(i).getStatus().toLowerCase().contains(statusFilter.getSelectionModel().getSelectedItem().toString().toLowerCase())
                            : data.get(i).getStatus().toLowerCase().equals(statusFilter.getSelectionModel().getSelectedItem().toString().toLowerCase()))
                    && data.get(i).getType().toLowerCase().contains(typeFilter.getSelectionModel().getSelectedItem().toString().toLowerCase())
                    && data.get(i).getInformation().toLowerCase().contains(characteristicsFilter.getText().toLowerCase())) {
                dataFilter.add(new Bagage(data.get(i).getId(), data.get(i).getStatus(),
                        data.get(i).getType(), data.get(i).getColor(), data.get(i).getBrand(), data.get(i).getPicture(),
                        data.get(i).getDate(), data.get(i).getInformation(), data.get(i).getFirstName(),
                        data.get(i).getSurName(), data.get(i).getAddress(), data.get(i).getResidence(), data.get(i).getZipcode(),
                        data.get(i).getCountry(), data.get(i).getPhone(), data.get(i).getMail(), data.get(i).getLabelNumber(),
                        data.get(i).getFlightNumber(), data.get(i).getDestination(), data.get(i).getAirportFound(), data.get(i).getAirportLost(),
                        data.get(i).getTableFrom(), data.get(i).getLostAndFoundID(), data.get(i).getPersonID(), data.get(i).getRealid()));
            }
        }

        //Update de tabel met gegevens die is gevraagd.
        table.setItems(dataFilter);
    }

    public void getLuggageData() {
        int luggage = 0;
        //Krijg alle bagages vanuit de database.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT found.*, airport.date, airport.airport_found, airport.airport_lost, "
                    + "airport.label_number, airport.flight_number, airport.destination, "
                    + "person.first_name, person.surname, person.address, person.zip_code, "
                    + "person.residence, person.country, person.phone, person.mail, 1 as tablefrom "
                    + "FROM found, airport, person "
                    + "WHERE found.lost_and_found_id = airport.lost_and_found_id "
                    + "AND found.person_id = person.person_id "
                    + "UNION SELECT lost.*, airport.date, airport.airport_found, airport.airport_lost, "
                    + "airport.label_number, airport.flight_number, airport.destination, "
                    + "person.first_name, person.surname, person.address, person.zip_code, person.residence, "
                    + "person.country, person.phone, person.mail, 0 as tablefrom FROM lost, airport, person "
                    + "WHERE lost.lost_and_found_id = airport.lost_and_found_id "
                    + "AND lost.person_id = person.person_id "
                    + "ORDER BY status";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    luggage++;
                    //Retrieve by column name
                    //zet voor elke veld in verschillende variables.
                    int id = rs.getInt("id");
                    String status = fys.getStatus(rs.getInt("status"));
                    String type = fys.getBaggageType(rs.getInt("type"));
                    String color = fys.getColor(rs.getInt("color"));
                    String brand = rs.getString("brand");
                    String picture = rs.getString("picture");
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

                    //Voeg de gegegevens toe in de data array.
                    data.add(new Bagage(luggage, status, type, color, brand, picture, date, characteristics, firstname,
                            surname, address, residence, zipcode, country, phone, mail, labelnumber, flightnumber, destination,
                            airportfound, airportlost, tablefrom, lostAndFoundID, personID, id));
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

    //Wanneer er op de wijzigen button wordt geklikt
    public void handleChange(ActionEvent event) throws IOException {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int dr_id = (table.getSelectionModel().getSelectedItem().getRealid());
            int dr_personId = (table.getSelectionModel().getSelectedItem().getPersonID());
            int dr_lafId = (table.getSelectionModel().getSelectedItem().getLostAndFoundID());
            int drFrom = Integer.parseInt((table.getSelectionModel().getSelectedItem().getTableFrom()));
            dr_status = (table.getSelectionModel().getSelectedItem().getStatus());
            String dr_airport = (table.getSelectionModel().getSelectedItem().getAirportFound());
            if (dr_airport == null) {
                dr_airport = (table.getSelectionModel().getSelectedItem().getAirportLost());
            }
            filePath = (table.getSelectionModel().getSelectedItem().getPicture());
            String dr_name = (table.getSelectionModel().getSelectedItem().getFirstName());
            String dr_surname = (table.getSelectionModel().getSelectedItem().getSurName());
            String dr_address = (table.getSelectionModel().getSelectedItem().getAddress());
            String dr_residence = (table.getSelectionModel().getSelectedItem().getResidence());
            String dr_zipcode = (table.getSelectionModel().getSelectedItem().getZipcode());
            String dr_country = (table.getSelectionModel().getSelectedItem().getCountry());
            String dr_phone = (table.getSelectionModel().getSelectedItem().getPhone());
            dr_mail = (table.getSelectionModel().getSelectedItem().getMail());
            String dr_label = (table.getSelectionModel().getSelectedItem().getLabelNumber());
            String dr_flight = (table.getSelectionModel().getSelectedItem().getFlightNumber());
            String dr_destination = (table.getSelectionModel().getSelectedItem().getDestination());
            String dr_type = (table.getSelectionModel().getSelectedItem().getType());
            String dr_brand = (table.getSelectionModel().getSelectedItem().getBrand());
            String dr_color = (table.getSelectionModel().getSelectedItem().getColor());
            String dr_characteristics = (table.getSelectionModel().getSelectedItem().getInformation());

            doNext(dr_id, dr_personId, dr_lafId, drFrom, dr_status, dr_airport,
                    dr_name, dr_surname, dr_address, dr_residence, dr_zipcode,
                    dr_country, dr_phone, dr_mail, dr_label, dr_flight, dr_destination,
                    dr_type, dr_brand, dr_color, dr_characteristics);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[104]);
            alert.setTitle(taal[104]);
            alert.setContentText(taal[105]);
            alert.showAndWait();
        }
    }
    
    //Wanneer er op de kop Anuleren wordt geklikt
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        pictureButton.setText(taal[44]);
        database_pane.setDisable(false);
        database_pane.setVisible(true);
        wijzig_pane.setDisable(true);
        wijzig_pane.setVisible(false);
    }

    @FXML
    public void doNext(int dr_id, int dr_personId, int dr_lafId, int drFrom, String dr_status,
            String dr_airport, String dr_name, String dr_surname, String dr_address,
            String dr_residence, String dr_zipcode, String dr_country, String dr_phone,
            String dr_mail, String dr_label, String dr_flight, String dr_destination,
            String dr_type, String dr_brand, String dr_color, String dr_characteristics) {
        database_pane.setDisable(true);
        database_pane.setVisible(false);
        wijzig_pane.setDisable(false);
        wijzig_pane.setVisible(true);

        idLabel.setText(String.valueOf(dr_id));
        personIdLabel.setText(String.valueOf(dr_personId));
        lafIdLabel.setText(String.valueOf(dr_lafId));
        tableFromLabel.setText(String.valueOf(drFrom));
        statusCombo.setValue(dr_status);
        airportCombo.setValue(dr_airport);
        nameInput.setText(dr_name);
        surNameInput.setText(dr_surname);
        addressInput.setText(dr_address);
        residenceInput.setText(dr_residence);
        zipcodeInput.setText(dr_zipcode);
        countryInput.setText(dr_country);
        phoneInput.setText(dr_phone);
        mailInput.setText(dr_mail);
        labelNumberInput.setText(dr_label);
        flightNumberInput.setText(dr_flight);
        //destinationInput.setText(dr_destination);
        typeCombo.setValue(dr_type);
        brandInput.setText(dr_brand);
        colorCombo.setValue(dr_color);
        characteristicsInput.setText(dr_characteristics);
    }

    //Wanneer er op de knop Verzenden wordt geklikt
    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        if ((typeCombo.getValue() == null)
                || (brandInput.getText() == null || brandInput.getText().trim().isEmpty())
                || (colorCombo.getValue() == null)) {
            // Foutmelding
            loginerror.setText(taal[93]);
            loginerror.setVisible(true);
        } else {
            String destination;
            if (destination_combo.getValue() == null) {
                destination = " ";
            } else {
                destination = destination_combo.getValue().toString();
            }

            //MAILEN
            if (fys.checkEmailExistsOnChange(mailInput.getText(), dr_mail)) {
                System.out.println("Emailadres bestaat al!");
            //Deze mail wordt verstuurd wanneer de status veranderd en niet op afgehandeld is gezet.
            } else if (fys.getStatusString(dr_status) != fys.getStatusString(statusCombo.getValue().toString())) {
                if (fys.getStatusString(statusCombo.getValue().toString()) != 3) {
                    int pageid = 4;
                    int type_email = 0;

                    // Email bericht filteren op sommige woorden.            
                    String getmessage = fys.replaceEmail(fys.replaceEmail_tF(fys.Email_Message(type_email, fys.Email_Language(mailInput.getText()), pageid), mailInput.getText(), Integer.parseInt(tableFromLabel.getText())), mailInput.getText());
                    // Email versturen
                    fys.sendEmail(mailInput.getText(), fys.Email_Subject(type_email, fys.Email_Language(mailInput.getText()), pageid), getmessage, "Sent message successfully....");

                    sendToDatabase(Integer.parseInt(idLabel.getText()), Integer.parseInt(personIdLabel.getText()),
                            Integer.parseInt(lafIdLabel.getText()), Integer.parseInt(tableFromLabel.getText()),
                            fys.getStatusString(statusCombo.getValue().toString()), airportCombo.getValue().toString(),
                            nameInput.getText(), surNameInput.getText(), addressInput.getText(),
                            residenceInput.getText(), zipcodeInput.getText(), countryInput.getText(),
                            phoneInput.getText(), mailInput.getText(), labelNumberInput.getText(),
                            filePath, flightNumberInput.getText(), destination,
                            fys.getBaggageTypeString(typeCombo.getValue().toString()), brandInput.getText(),
                            fys.getColorString(colorCombo.getValue().toString()), characteristicsInput.getText());
                }
            } else {
                sendToDatabase(Integer.parseInt(idLabel.getText()), Integer.parseInt(personIdLabel.getText()),
                        Integer.parseInt(lafIdLabel.getText()), Integer.parseInt(tableFromLabel.getText()),
                        fys.getStatusString(statusCombo.getValue().toString()), airportCombo.getValue().toString(),
                        nameInput.getText(), surNameInput.getText(), addressInput.getText(),
                        residenceInput.getText(), zipcodeInput.getText(), countryInput.getText(),
                        phoneInput.getText(), mailInput.getText(), labelNumberInput.getText(),
                        filePath, flightNumberInput.getText(), destination,
                        fys.getBaggageTypeString(typeCombo.getValue().toString()), brandInput.getText(),
                        fys.getColorString(colorCombo.getValue().toString()), characteristicsInput.getText());
            }
        }
    }

    private void sendToDatabase(int dr_id, int dr_personId, int dr_lafId, int tableFrom,
            int status, String airport, String frontname, String surname, String address,
            String residence, String zipcode, String country, String phone, String mail,
            String labelnumber, String filePath, String flightnumber, String destination,
            int type, String brand, Integer color, String characteristics)
            throws IOException, SQLException {

        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "UPDATE bagagedatabase.person SET first_name='" + frontname + "',"
                    + "surname='" + surname + "', address='" + address + "',"
                    + "residence='" + residence + "', zip_code='" + zipcode + "',"
                    + "country='" + country + "', phone='" + phone + "',"
                    + "mail='" + mail + "'"
                    + "WHERE person_id='" + dr_personId + "'";
            stmt.executeUpdate(sql_person);

            String sql_lost = "";
            String sql_airport = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            String dateTimeString = dateFormat.format(date);
            String[] tokens = dateTimeString.split(" ");
            if (tokens.length != 2) {
                throw new IllegalArgumentException();
            }
            String dateString = tokens[0];
            String timeString = tokens[1];

            if (tableFrom == 0) {   //0 = bagagedatabase.lost
                sql_lost = "UPDATE bagagedatabase.lost SET status='" + status + "',"
                        + "picture='" + filePath + "', type='" + type + "', brand='" + brand + "',"
                        + "color='" + color + "', characteristics='" + characteristics + "'"
                        + "WHERE id='" + dr_id + "'";

                switch (status) {
                    //case 0: Gaat van lost naar status Gevonden
                    case 0:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_found='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                    //case 3: Gaat van lost naar status Afgehandeld
                    case 3:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_found='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                    //case 6: registreer schadeclaim de status zal niet veranderen.    
                    case 6:
                        sql_airport = "INSERT INTO insurance_claim VALUES(" + dr_id + ", '" + dateString + "', "
                                + "'" + timeString + "', 0);";
                        break;
                    //default: Gaat van lost naar status Vermist, Vernietigd, Nooit Gevonden of Depot 
                    default:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_lost='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                }
            } else if (tableFrom == 1) {  //1 = bagagedatabase.found
                sql_lost = "UPDATE bagagedatabase.found SET status='" + status + "',"
                        + "picture='" + filePath + "', type='" + type + "', brand='" + brand + "',"
                        + "color='" + color + "', characteristics='" + characteristics + "'"
                        + "WHERE id='" + dr_id + "'";

                switch (status) {
                    //case 1: Gaat van found naar status Vermist
                    case 1:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_lost='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                    //case 3: Gaat van found naar status Afgehandeld
                    case 3:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_lost='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                    //case 6: registreer schadeclaim de status zal niet veranderen.     
                    case 6:
                        sql_airport = "INSERT INTO insurance_claim VALUES(" + dr_id + ", '" + dateString + "', "
                                + "'" + timeString + "', 1);";
                        break;
                    //default: Gaat van found naar status Gevonden, Vernietigd, Nooit gevonden, Depot 
                    default:
                        sql_airport = "UPDATE bagagedatabase.airport SET airport_found='" + airport + "',"
                                + "label_number='" + labelnumber + "', flight_number='" + flightnumber + "',"
                                + "destination='" + destination + "'"
                                + "WHERE lost_and_found_id='" + dr_lafId + "'";
                        break;
                }
            }
            if (status != 6) {
                stmt.executeUpdate(sql_lost);
            }
            stmt.executeUpdate(sql_airport);

            /*Indien de status wordt veranderd naar afgehandeld wordt er een DHL-
            formulier gemaakt. DISCLAIMER: Het DHL-formulier dat gemaakt wordt is
            gebaseerd op het officiële DHL-emailshipment formulier dat is te vinden
            op 'http://www.dhl.com/en/express/resource_center/emailship.html'.
            Het formulier is gekopieerd en aangepast d.m.v. Adobe Acrobat DC en is 
            louter bedoeld voor demonstratieve doeleinden.
             */
            if (status == 3) { //Afgehandeld
                //Maak nieuwe PDF-document aan de hand van de template
                File pdfdoc = new File("src/fys/templates/dhltemplate.pdf");
                PDDocument document;
                document = PDDocument.load(pdfdoc);

                //Laad alle inputvelden op het formulier
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
                List<PDField> fields = acroForm.getFields();

                //Vul alle inputvelden in met de waardes uit de database
                for (PDField field : fields) {
                    if (field.getFullyQualifiedName().equals("companyName_field")) {
                        field.setValue("Corendon");
                    }
                    if (field.getFullyQualifiedName().equals("country_field")) {
                        field.setValue(country);
                    }
                    if (field.getFullyQualifiedName().equals("address_field")) {
                        field.setValue(address);
                    }
                    if (field.getFullyQualifiedName().equals("city_field")) {
                        field.setValue(residence);
                    }
                    if (field.getFullyQualifiedName().equals("postcode_field")) {
                        field.setValue(zipcode);
                    }
                    if (field.getFullyQualifiedName().equals("contactPerson_field")) {
                        field.setValue(frontname + " " + surname);
                    }
                    if (field.getFullyQualifiedName().equals("phone_field")) {
                        field.setValue(phone);
                    }
                    if (field.getFullyQualifiedName().equals("type_field")) {
                        field.setValue("International NonDocument");
                    }
                    if (field.getFullyQualifiedName().equals("product_field")) {
                        field.setValue("Express WorldWide");
                    }
                    if (field.getFullyQualifiedName().equals("notification_field")) {
                        field.setValue("fysepsilon@gmail.com ");
                    }
                    if (field.getFullyQualifiedName().equals("content_field")) {
                        field.setValue(fys.getBaggageType(type));
                    }
                    if (field.getFullyQualifiedName().equals("date_field")) {
                        field.setValue(dateString);
                    }
                }

                //Sla het document op
                document.save("src/fys/formulieren/dhlFormulier" + frontname + surname + dr_personId + ".pdf");
                document.close();

                int pageid = 5;
                int type_email = 0;

                // Email bericht filteren op sommige woorden.            
                String getmessage = fys.replaceEmail(fys.replaceEmail_tF(fys.Email_Message(type_email, fys.Email_Language(mailInput.getText()), pageid), mailInput.getText(), Integer.parseInt(tableFromLabel.getText())), mailInput.getText());
                // Email versturen
                fys.sendEmail(mailInput.getText(), fys.Email_Subject(type_email, fys.Email_Language(mailInput.getText()), pageid), getmessage, "Sent message successfully....");
            }
            pictureButton.setText(taal[44]);
            fys.changeToAnotherFXML(taal[100], "bagagedatabase.fxml");
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    //Fileselector aanroepen wanneer iemand een afbeelding wil toevoegen
    @FXML
    public void handleFileSelector(ActionEvent event) throws FileNotFoundException, IOException {
        File file = fys.fileChooser();
        //String fileRaw = file.getAbsolutePath();
        filePath = "fys/src/fys/luggageImages/" + file.getName();
        System.out.println(filePath);
        //filePath = fileRaw.replace("\\","\\\\");
        pictureButton.setText(file.getName());
    }

    //Bagage permanent uit de database verwijderen
    @FXML
    public void handeRemove(ActionEvent event) throws IOException {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            //Verkrijg de id, personId en lafId om data te verwijderen
            int dr_id = (table.getSelectionModel().getSelectedItem().getRealid());
            int dr_personId = (table.getSelectionModel().getSelectedItem().getPersonID());
            int dr_lafId = (table.getSelectionModel().getSelectedItem().getLostAndFoundID());
            int dr_from = Integer.parseInt((table.getSelectionModel().getSelectedItem().getTableFrom()));

            //Vraag of de gebruiker het zeker weet
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle(taal[154]);
            confirm.setHeaderText(taal[154]);
            confirm.setContentText(taal[155]);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    conn = fys.connectToDatabase(conn);
                    stmt = conn.createStatement();
                    String sql_del = "";
                    String sql_del2 = "";
                    String sql_del3 = "";

                    //Verwijder de gegevens die coresponderen aan de id, personId en lafId
                    if (dr_from == 0) { //0 = lost_table
                        sql_del = "DELETE FROM bagagedatabase.lost WHERE id='" + dr_id + "'";
                        sql_del2 = "DELETE FROM bagagedatabase.person WHERE person_id='" + dr_personId + "'";
                        sql_del3 = "DELETE FROM bagagedatabase.airport WHERE lost_and_found_id='" + dr_lafId + "'";
                    } else if (dr_from == 1) { //1 = found_table
                        sql_del = "DELETE FROM bagagedatabase.found WHERE id='" + dr_id + "'";
                        sql_del2 = "DELETE FROM bagagedatabase.person WHERE person_id='" + dr_personId + "'";
                        sql_del3 = "DELETE FROM bagagedatabase.airport WHERE lost_and_found_id='" + dr_lafId + "'";
                    }
                    stmt.executeUpdate(sql_del);
                    stmt.executeUpdate(sql_del2);
                    stmt.executeUpdate(sql_del3);
                    conn.close();

                    fys.changeToAnotherFXML(taal[100], "bagagedatabase.fxml");
//                    Alert info = new Alert(AlertType.INFORMATION);
//                    info.setTitle("Information Dialog");
//                    info.setHeaderText(taal[157]);
//                    info.setContentText(taal[158]);

//                    info.showAndWait();
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
            } else {
                //Ignore
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[154]);
            alert.setTitle(taal[154]);
            alert.setContentText(taal[105]);
            alert.showAndWait();
        }
    }

    public void hideFilter() {
        filterPane.setDisable(true);
        filterPane.setVisible(false);
        table.setLayoutX(112.0);
        table.setPrefWidth(772.0);
        inklapLabel.setDisable(true);
        inklapLabel.setVisible(false);
        uitklapLabel.setDisable(false);
        uitklapLabel.setVisible(true);
        changeButton.setLayoutX(112.0);
    }

    public void showFilter() {
        filterPane.setDisable(false);
        filterPane.setVisible(true);
        table.setLayoutX(324.0);
        table.setPrefWidth(560.0);
        uitklapLabel.setDisable(true);
        uitklapLabel.setVisible(false);
        inklapLabel.setDisable(false);
        inklapLabel.setVisible(true);
        changeButton.setLayoutX(324.0);
    }
}
