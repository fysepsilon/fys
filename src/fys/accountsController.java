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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class accountsController implements Initializable {

    @FXML
    private Button NewAccountButton, change_button;
    @FXML
    private TableView<Accounts> table;
    @FXML
    private ObservableList<Accounts> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn first_name, surname, type, mail, address,
            residence, zip_code, country, phone, language_column, personID;
    @FXML
    private AnchorPane database_pane, wijzig_pane;
    @FXML
    private ComboBox type_combo, language_combo;
    @FXML
    private TextField first_name_input, surname_input, mail_input, address_input, residence_input, zip_code_input, country_input, phone_input;
    @FXML
    private Label loginerror, type_label, language_label, first_name_label, surname_label, address_label, residence_label, zip_code_label, country_label, phone_label, mail_label, personId_label;

    @FXML
    private void handlenieuwaccount(ActionEvent event) throws IOException {
        FYS fys = new FYS();
        taal language = new taal();
        String[] taal = language.getLanguage();
        fys.changeToAnotherFXML(taal[63], "nieuwaccountaanmaken.fxml");
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        database_pane.setDisable(false);
        database_pane.setVisible(true);
        wijzig_pane.setDisable(true);
        wijzig_pane.setVisible(false);
    }

    public void handleRemove(ActionEvent event) throws IOException {
        taal language = new taal();
        String[] taal = language.getLanguage();
        int selectedIndex
                = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int dr_personId = (table.getSelectionModel().getSelectedItem().getPersonId());
            String dr_first_name = (table.getSelectionModel().getSelectedItem().getFirst_name());
            String dr_surname = (table.getSelectionModel().getSelectedItem().getSurname());


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(taal[128]);
            alert.setContentText(taal[129] + dr_first_name + " " + dr_surname + taal[130]);

            ButtonType buttonTypeOne = new ButtonType("Bevestigen");
            ButtonType buttonTypeCancel = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {

                personId_label.setText(String.valueOf(dr_personId));

                try {
                    sendToDatabase2(Integer.parseInt(personId_label.getText()));
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[104]);
            alert.setContentText(taal[105]);
            alert.showAndWait();
        }
    }

    private void sendToDatabase2(int dr_personId)
            throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            taal languages = new taal();
            String[] taal = languages.getLanguage();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_remove = "DELETE FROM bagagedatabase.person_table "
                    + "WHERE person_id='" + dr_personId + "'";

            stmt.executeUpdate(sql_remove);

            fys.changeToAnotherFXML(taal[98], "accounts.fxml");

            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        taal language = new taal();
        String[] taal = language.getLanguage();
        first_name.setText(taal[9]);
        surname.setText(taal[10]);
        address.setText(taal[11]);
        residence.setText(taal[12]);
        zip_code.setText(taal[13]);
        country.setText(taal[14]);
        phone.setText(taal[15]);
        mail.setText(taal[16]);
        type.setText(taal[20]);
        language_column.setText(taal[68]);
        NewAccountButton.setText(taal[63]);
        change_button.setText(taal[126]);

        first_name_label.setText(taal[9] + ":");
        surname_label.setText(taal[10] + ":");
        address_label.setText(taal[11] + ":");
        residence_label.setText(taal[12] + ":");
        zip_code_label.setText(taal[13] + ":");
        country_label.setText(taal[14] + ":");
        phone_label.setText(taal[15] + ":");
        mail_label.setText(taal[16] + ":");
        type_label.setText(taal[20] + ":");
        language_label.setText(taal[68] + ":");

        type_combo.getItems().addAll(
                taal[66],
                taal[64],
                taal[65]);
        language_combo.setPromptText(taal[73]);
        language_combo.getItems().addAll(
                taal[69],
                taal[70],
                taal[71],
                taal[72]);

        getLuggageData();
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        residence.setCellValueFactory(new PropertyValueFactory<>("residence"));
        zip_code.setCellValueFactory(new PropertyValueFactory<>("zip_code"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        language_column.setCellValueFactory(new PropertyValueFactory<>("language_column"));
        personID.setCellValueFactory(new PropertyValueFactory<>("personID"));

        first_name.setStyle("-fx-alignment: CENTER;");
        surname.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        mail.setStyle("-fx-alignment: CENTER;");
        address.setStyle("-fx-alignment: CENTER;");
        residence.setStyle("-fx-alignment: CENTER;");
        zip_code.setStyle("-fx-alignment: CENTER;");
        country.setStyle("-fx-alignment: CENTER;");
        phone.setStyle("-fx-alignment: CENTER;");
        language_column.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);

    }

    public void getLuggageData() {
        taal language = new taal();
        String[] taal = language.getLanguage();
        FYS fys = new FYS();
        String type_text;
        String language_text;

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
            if (loginController.getUsertype() == 2) { //SQL bij administrator (type = 2)
                String sql = "SELECT * FROM bagagedatabase.person_table";

                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        String first_name = rs.getString("first_name");
                        String surname = rs.getString("surname");
                        int type = rs.getInt("type");
                        type_text = fys.getUserFunction(type);
                        String mail = rs.getString("mail");
                        String address = rs.getString("address");
                        String residence = rs.getString("residence");
                        String zip_code = rs.getString("zip_code");
                        String country = rs.getString("country");
                        String phone = rs.getString("phone");
                        int language_column = rs.getInt("language");
                        language_text = fys.getUserLanguage(language_column);

                        int personId = rs.getInt("person_id");

                        data.add(new Accounts(first_name, surname, type_text,
                                mail, address, residence, zip_code, country,
                                phone, language_text, personId));
                    }
                }
            } else { //SQL bij servicemedewerker (type = 1)
                String sql = "SELECT * FROM bagagedatabase.person_table WHERE type = '0'";

                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        //Retrieve by column name
                        String first_name = rs.getString("first_name");
                        String surname = rs.getString("surname");
                        int type = rs.getInt("type");
                        type_text = fys.getUserFunction(type);
                        String mail = rs.getString("mail");
                        String address = rs.getString("address");
                        String residence = rs.getString("residence");
                        String zip_code = rs.getString("zip_code");
                        String country = rs.getString("country");
                        String phone = rs.getString("phone");
                        int language_column = rs.getInt("language");
                        language_text = fys.getUserLanguage(language_column);

                        int personId = rs.getInt("person_id");

                        data.add(new Accounts(first_name, surname, type_text,
                                mail, address, residence, zip_code, country,
                                phone, language_text, personId));
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
        private final SimpleStringProperty surname;
        @FXML
        private final SimpleStringProperty type;
        @FXML
        private final SimpleStringProperty mail;
        @FXML
        private final SimpleStringProperty address;
        @FXML
        private final SimpleStringProperty residence;
        @FXML
        private final SimpleStringProperty zip_code;
        @FXML
        private final SimpleStringProperty country;
        @FXML
        private final SimpleStringProperty phone;
        @FXML
        private final SimpleStringProperty language_column;
        @FXML
        private final SimpleIntegerProperty personId;

        private Accounts(String first_namename, String surnamename,
                String typename, String mailname, String addressname,
                String residencename, String zip_codename, String countryname,
                String phonename, String language_columnname, int personidname) {
            this.first_name = new SimpleStringProperty(first_namename);
            this.surname = new SimpleStringProperty(surnamename);
            this.type = new SimpleStringProperty(typename);
            this.mail = new SimpleStringProperty(mailname);
            this.address = new SimpleStringProperty(addressname);
            this.residence = new SimpleStringProperty(residencename);
            this.zip_code = new SimpleStringProperty(zip_codename);
            this.country = new SimpleStringProperty(countryname);
            this.phone = new SimpleStringProperty(phonename);
            this.language_column = new SimpleStringProperty(language_columnname);
            this.personId = new SimpleIntegerProperty(personidname);
        }

        public String getFirst_name() {
            return first_name.get();
        }

        public void setFirst_name(String first_namename) {
            first_name.set(first_namename);
        }

        public String getSurname() {
            return surname.get();
        }

        public void setSurname(String surnamename) {
            surname.set(surnamename);
        }

        public String getType() {
            return type.get();
        }

        public void setType(String typename) {
            type.set(typename);
        }

        public String getMail() {
            return mail.get();
        }

        public void setMail(String mailname) {
            mail.set(mailname);
        }

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String addressname) {
            address.set(addressname);
        }

        public String getResidence() {
            return residence.get();
        }

        public void setResidence(String residencename) {
            residence.set(residencename);
        }

        public String getZip_code() {
            return zip_code.get();
        }

        public void setZip_code(String zip_codename) {
            zip_code.set(zip_codename);
        }

        public String getCountry() {
            return country.get();
        }

        public void setCountry(String countryname) {
            country.set(countryname);
        }

        public String getPhone() {
            return phone.get();
        }

        public void setPhone(String phonename) {
            phone.set(phonename);
        }

        public String getLanguage_column() {
            return language_column.get();
        }

        public void setLanguagecolumn(String language_columnname) {
            language_column.set(language_columnname);
        }

        public int getPersonId() {
            return personId.get();
        }

        public void setPersonId(int personidname) {
            personId.set(personidname);
        }
    }

    public void handleChange(ActionEvent event) throws IOException {
        taal language = new taal();
        String[] taal = language.getLanguage();
        int selectedIndex
                = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String dr_first_name = (table.getSelectionModel().getSelectedItem().getFirst_name());
            String dr_surname = (table.getSelectionModel().getSelectedItem().getSurname());
            String dr_type_combo = (table.getSelectionModel().getSelectedItem().getType());
            String dr_mail = (table.getSelectionModel().getSelectedItem().getMail());
            String dr_address = (table.getSelectionModel().getSelectedItem().getAddress());
            String dr_residence = (table.getSelectionModel().getSelectedItem().getResidence());
            String dr_zip_code = (table.getSelectionModel().getSelectedItem().getZip_code());
            String dr_country = (table.getSelectionModel().getSelectedItem().getCountry());
            String dr_phone = (table.getSelectionModel().getSelectedItem().getPhone());
            String dr_language_combo = (table.getSelectionModel().getSelectedItem().getLanguage_column());
            int dr_personId = (table.getSelectionModel().getSelectedItem().getPersonId());

            doNext(dr_first_name, dr_surname, dr_type_combo, dr_mail, dr_address, dr_residence, dr_zip_code, dr_country, dr_phone, dr_language_combo, dr_personId);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[104]);
            alert.setContentText(taal[105]);
            alert.showAndWait();
        }
    }

    @FXML
    public void doNext(String dr_first_name, String dr_surname, String dr_type_combo, String dr_mail, String dr_address, String dr_residence, String dr_zip_code, String dr_country, String dr_phone, String dr_language_combo, int dr_personId) {
        database_pane.setDisable(true);
        database_pane.setVisible(false);
        wijzig_pane.setDisable(false);
        wijzig_pane.setVisible(true);

        first_name_input.setText(dr_first_name);
        surname_input.setText(dr_surname);
        type_combo.setValue(dr_type_combo);
        mail_input.setText(dr_mail);
        address_input.setText(dr_address);
        residence_input.setText(dr_residence);
        zip_code_input.setText(dr_zip_code);
        country_input.setText(dr_country);
        phone_input.setText(dr_phone);
        language_combo.setValue(dr_language_combo);
        personId_label.setText(String.valueOf(dr_personId));

    }

    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        FYS fys = new FYS();
        taal languages = new taal();
        String[] taal = languages.getLanguage();

        if ((first_name_input.getText() == null || first_name_input.getText().trim().isEmpty())
                || (surname_input.getText() == null || surname_input.getText().trim().isEmpty())
                || (type_combo.getValue() == null)
                || (mail_input.getText() == null || mail_input.getText().trim().isEmpty())
                || (address_input.getText() == null || address_input.getText().trim().isEmpty())
                || (residence_input.getText() == null || residence_input.getText().trim().isEmpty())
                || (zip_code_input.getText() == null || zip_code_input.getText().trim().isEmpty())
                || (country_input.getText() == null || country_input.getText().trim().isEmpty())
                || (phone_input.getText() == null || phone_input.getText().trim().isEmpty())
                || (language_combo.getValue() == null)) {
            // Foutmelding
            loginerror.setText(taal[93]);
            loginerror.setVisible(true);
        } else {
            sendToDatabase(Integer.parseInt(personId_label.getText()),
                    first_name_input.getText(), surname_input.getText(),
                    fys.getUserFunctionString(type_combo.getValue().toString()),
                    mail_input.getText(), address_input.getText(),
                    residence_input.getText(), zip_code_input.getText(),
                    country_input.getText(), phone_input.getText(),
                    fys.getUserLanguageString(language_combo.getValue().toString())
            );
        }
    }

    private void sendToDatabase(int dr_personId, String first_name,
            String surname, int type_combo, String mail, String address,
            String residence, String zip_code, String country, String phone,
            int language_combo)
            throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            taal languages = new taal();
            String[] taal = languages.getLanguage();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "UPDATE bagagedatabase.person_table SET "
                    + "first_name='" + first_name + "',"
                    + "surname='" + surname + "', type='" + type_combo + "', "
                    + "mail='" + mail + "', address='" + address + "',"
                    + "residence='" + residence + "', zip_code='" + zip_code + "',"
                    + "country='" + country + "', phone='" + phone + "',"
                    + "language='" + language_combo + "'"
                    + "WHERE person_id='" + dr_personId + "'";

            stmt.executeUpdate(sql_person);

            fys.changeToAnotherFXML(taal[98], "accounts.fxml");

            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
