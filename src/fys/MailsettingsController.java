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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class MailsettingsController implements Initializable {

    @FXML
    private AnchorPane home_pane, edit_pane;
    @FXML
    private Pane filterPane, alertChangePane, mainPane;
    @FXML
    private TableView<Mail> table;
    @FXML
    private ObservableList<Mail> data = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Mail> dataFilter = FXCollections.observableArrayList();
    @FXML
    private TableColumn mailid, subject, message, page, type, language;
    @FXML
    private ComboBox pageFilter, typeFilter, languageFilter;
    @FXML
    private TextArea alertchange_area;
    @FXML
    private HTMLEditor HTMLEditor;
    @FXML
    private Button change_button, send_button, cancel_button, recover_button,
            show_VBox, close_VBOX, filter_button, filter, alertchange_button;
    @FXML
    private Label mailid_label, subject_label, page_label, type_label,
            language_label, info_firstname, info_surname, info_password,
            info_username, info, error, info_label, popup_filterlabel,
            pageFilterLabel, languageFilterLabel, typeFilterLabel,
            mainFilterLabel, alertchange_headerlabel;
    @FXML
    private GridPane mailGridpane, luggagetable;
    @FXML
    private VBox VBox;
    @FXML
    private TextField subject_field, page_field, type_field, language_field;
    @FXML
    private final loginController loginController = new loginController();
    @FXML
    private final FYS fys = new FYS();
    @FXML
    private final taal languages = new taal();
    @FXML
    private final String[] taal = languages.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mailid.setText("ID");
        subject.setText(taal[135]);
        message.setText(taal[136]);
        page.setText(taal[144]);
        type.setText(taal[20]);
        language.setText(taal[68]);
        subject_label.setText(taal[135] + ":");
        page_label.setText(taal[144] + ":");
        type_label.setText(taal[20] + ":");
        language_label.setText(taal[68] + ":");

        info_username.setText(taal[148]);
        info_password.setText(taal[90]);
        info_firstname.setText(taal[9]);
        info_surname.setText(taal[10]);
        info_label.setText(taal[152]);
        info.setText(taal[149]);

        change_button.setText(taal[67]);
        send_button.setText(taal[92]);
        cancel_button.setText(taal[127]);
        recover_button.setText(taal[153]);
        filter_button.setText(taal[47]);

        //Popup
        pageFilter.getItems().addAll(taal[63], taal[95], taal[166], taal[100]);
        typeFilter.getItems().addAll(taal[66], taal[64], taal[65], taal[163]);
        languageFilter.getItems().addAll(taal[69], taal[70], taal[71], taal[72], taal[165]);
        popup_filterlabel.setText(taal[47]);
        pageFilterLabel.setText(taal[144]);
        languageFilterLabel.setText(taal[68]);
        typeFilterLabel.setText(taal[20]);
        mainFilterLabel.setText(taal[167]);
        filter.setText(taal[47]);
        
        
        //Alert
        alertchange_headerlabel.setText(taal[104]);
        alertchange_button.setText(taal[183]);
        alertchange_area.setText(taal[105]);

        //Tabel
        getMailData();
        mailid.setCellValueFactory(new PropertyValueFactory<>("mailid"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        page.setCellValueFactory(new PropertyValueFactory<>("page"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        language.setCellValueFactory(new PropertyValueFactory<>("language"));

        mailid.setStyle("-fx-alignment: CENTER;");
        page.setStyle("-fx-alignment: CENTER;");
        subject.setStyle("-fx-alignment: CENTER;");
        message.setStyle("-fx-alignment: CENTER;");
        type.setStyle("-fx-alignment: CENTER;");
        language.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }

    @FXML
    public void handleCancelFilter(ActionEvent event) throws IOException {
        filterPane.setVisible(false);
        home_pane.setDisable(false);
    }

    @FXML
    public void handleOpenFilter(ActionEvent event) throws IOException {
        filterPane.setVisible(true);
        home_pane.setDisable(true);
    }

    //Wanneer er op de button filteren wordt geklikt.
    @FXML
    private void handleFilterAction(ActionEvent event) throws IOException {
        //Altijd wanneer de filter button wordt geklikt maak de array leeg.
        dataFilter = FXCollections.observableArrayList();

        //Controleer voor elk veld of het gelijk is met de filter velden.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getPage().toLowerCase().contains(pageFilter.getSelectionModel().getSelectedItem().toString().toLowerCase())
                    && data.get(i).getType().toLowerCase().contains(typeFilter.getSelectionModel().getSelectedItem().toString().toLowerCase())
                    && data.get(i).getLanguage().toLowerCase().contains(languageFilter.getSelectionModel().getSelectedItem().toString().toLowerCase())) {
                dataFilter.add(new Mail(data.get(i).getMailid(), data.get(i).getSubject(),
                        data.get(i).getMessage(), data.get(i).getPage(), data.get(i).getType(), data.get(i).getLanguage()));
            }
        }

        //Update de tabel met gegevens die is gevraagd.
        table.setItems(dataFilter);

        //Sluit de popup
        filterPane.setVisible(false);
        home_pane.setDisable(false);
    }

    public void getMailData() {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "SELECT * FROM bagagedatabase.mail";

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    int mailid = rs.getInt("mailid");
                    String subject = rs.getString("subject");
                    String message = rs.getString("message");
                    String page = fys.getPage(rs.getInt("pageid"));
                    String type = fys.getUserFunction(rs.getInt("type"));
                    String language = fys.getUserLanguage(rs.getInt("language"));

                    data.add(new Mail(mailid, subject, message, page, type, language));
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

    public void handleChange(ActionEvent event) throws IOException {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int dr_mailid = (table.getSelectionModel().getSelectedItem().getMailid());
            String dr_subject = (table.getSelectionModel().getSelectedItem().getSubject());
            String dr_message = (table.getSelectionModel().getSelectedItem().getMessage());
            String dr_page = (table.getSelectionModel().getSelectedItem().getPage());
            String dr_type = (table.getSelectionModel().getSelectedItem().getType());
            String dr_language = (table.getSelectionModel().getSelectedItem().getLanguage());

            doNext(dr_mailid, dr_subject, dr_message, dr_page, dr_type, dr_language);
        } else {
            alertChangePane.setVisible(true);
            mainPane.setDisable(true);
        }
    }

    @FXML
    private void handleCloseAlertChange(ActionEvent event) throws IOException {
        alertChangePane.setVisible(false);
        mainPane.setDisable(false);
    }
    
    @FXML
    public void doNext(int dr_mailid, String dr_subject, String dr_message,
            String dr_page, String dr_type, String dr_language) {
        // Switch from an anchorpane to another anchorpane
        home_pane.setDisable(true);
        home_pane.setVisible(false);
        edit_pane.setDisable(false);
        edit_pane.setVisible(true);

        // Fill columns with data
        mailid_label.setText(String.valueOf(dr_mailid));
        subject_field.setText(dr_subject);
        HTMLEditor.setHtmlText(dr_message);
        page_field.setText(String.valueOf(dr_page));
        type_field.setText(String.valueOf(dr_type));
        language_field.setText(String.valueOf(dr_language));

        if (dr_page == taal[100] && dr_page == taal[100]) {
            luggagetable.setVisible(true);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        // Switch from an anchorpane to another anchorpane
        home_pane.setDisable(false);
        home_pane.setVisible(true);
        edit_pane.setDisable(true);
        edit_pane.setVisible(false);
    }

    //Wanneer je op het informatie icoontje klikt
    @FXML
    private void handleBig(ActionEvent event) throws IOException {
        // Switch from an anchorpane to another anchorpane
        VBox.setVisible(true);
        HTMLEditor.setPrefWidth(560);
        show_VBox.setVisible(false);
        mailGridpane.setPrefWidth(560);
        close_VBOX.setVisible(true);
    }

    // Wanneer je op het sluit icoontje klikt
    @FXML
    private void handleCloseBig(ActionEvent event) throws IOException {
        // Switch from an anchorpane to another anchorpane
        VBox.setVisible(false);
        HTMLEditor.setPrefWidth(772);
        show_VBox.setVisible(true);
        mailGridpane.setPrefWidth(772);
        close_VBOX.setVisible(false);
    }

    //Wanneer je op de button herstellen klikt.
    public void handleRecover(ActionEvent event) throws IOException {
            int dr_mailid = (table.getSelectionModel().getSelectedItem().getMailid());
            String dr_subject = (table.getSelectionModel().getSelectedItem().getSubject());
            String dr_message = (table.getSelectionModel().getSelectedItem().getMessage());
            String dr_page = (table.getSelectionModel().getSelectedItem().getPage());
            String dr_type = (table.getSelectionModel().getSelectedItem().getType());
            String dr_language = (table.getSelectionModel().getSelectedItem().getLanguage());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(taal[153]);
            alert.setContentText(taal[147]);

            ButtonType buttonTypeOne = new ButtonType(taal[146]);
            ButtonType buttonTypeCancel = new ButtonType(taal[127], ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {

            mailid_label.setText(String.valueOf(dr_mailid));

                try {
                    sendToDatabase2(Integer.parseInt(mailid_label.getText()));
                } catch (SQLException ex) {
                    // handle any errors
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
            }
    }

    //Herstellen
    private void sendToDatabase2(int dr_mailid) throws IOException, SQLException {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            String sql_select = "SELECT * FROM bagagedatabase.mail WHERE mailid='" + dr_mailid + "'";
            try (ResultSet rs = stmt.executeQuery(sql_select)) {
                while (rs.next()) {
                    //Retrieve by column name
                    String recover_subject = rs.getString("recover_subject");
                    String recover_message = rs.getString("recover_message");

                    //connectToDatabase(conn, stmt, "test", "root", "root");
                    String sql_update = "UPDATE bagagedatabase.mail "
                            + "SET subject='" + recover_subject + "',message='" + recover_message + "' "
                            + "WHERE mailid='" + dr_mailid + "'";

                    stmt.executeUpdate(sql_update);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        fys.changeToAnotherFXML(taal[145], "mailsettings.fxml");

    }

    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        if (((HTMLEditor.getHtmlText() == null || HTMLEditor.getHtmlText().trim().isEmpty())
                || subject_field.getText() == null || subject_field.getText().trim().isEmpty())) {
            // Foutmelding
            error.setText(taal[93]);
            error.setVisible(true);
        } else if (HTMLEditor.getHtmlText().contains("*luggagecolor*")
                || HTMLEditor.getHtmlText().contains("*luggagebrand*")
                || HTMLEditor.getHtmlText().contains("*luggagetype*")
                || HTMLEditor.getHtmlText().contains("*luggagestatus*")
                && page.getText() != taal[100]) {
            error.setText(taal[168]);
            error.setVisible(true);
        } else if (HTMLEditor.getHtmlText().contains("'")) {
            error.setText(taal[169]);
            error.setVisible(true);
        } else if (HTMLEditor.getHtmlText().contains("´")) {
            error.setText(taal[170]);
            error.setVisible(true);
        } else {
            sendToDatabase(Integer.parseInt(mailid_label.getText()), subject_field.getText(), HTMLEditor.getHtmlText());
        }

    }

    private void sendToDatabase(int mailid, String subject, String message) throws IOException, SQLException {
        FYS fys = new FYS();

        try {
            Statement stmt = null;
            Connection conn = null;

            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();

            taal languages = new taal();
            String[] taal = languages.getLanguage();

            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql_person = "UPDATE bagagedatabase.mail SET "
                    + "subject='" + subject + "', message='" + message + "'"
                    + "WHERE mailid='" + mailid + "'";

            stmt.executeUpdate(sql_person);

            fys.changeToAnotherFXML(taal[145], "mailsettings.fxml");

            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
