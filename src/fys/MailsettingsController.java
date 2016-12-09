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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class MailsettingsController implements Initializable {

    @FXML
    private AnchorPane home_pane, edit_pane;
    @FXML
    private TableView<Mail> table;
    @FXML
    private ObservableList<Mail> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn mailid, subject, message;
    @FXML
    private HTMLEditor HTMLEditor;
    @FXML
    private Button change_button, send_button, cancel_button;
    @FXML
    private Label mailid_label, subject_label;
    @FXML
    private TextField subject_field;
    @FXML
    private loginController loginController = new loginController();
    @FXML
    private FYS fys = new FYS();
    @FXML
    private taal languages = new taal();
    @FXML
    private String[] taal = languages.getLanguage();
    @FXML
    private Statement stmt = null;
    @FXML
    private Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subject.setText(taal[135]);
        message.setText(taal[136]);
        subject_label.setText(taal[135]);

        change_button.setText(taal[67]);
        send_button.setText(taal[46]);
        cancel_button.setText(taal[127]);

        loginController loginController = new loginController();

        getMailData();
        mailid.setCellValueFactory(new PropertyValueFactory<>("mailid"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));

        mailid.setStyle("-fx-alignment: CENTER;");
        subject.setStyle("-fx-alignment: CENTER;");
        message.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
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

                    data.add(new Mail(mailid, subject, message));
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

            doNext(dr_mailid, dr_subject, dr_message);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(taal[133]);
            alert.setContentText(taal[134]);
            alert.showAndWait();
        }
    }

    @FXML
    public void doNext(int dr_mailid, String dr_subject, String dr_message) {
        // Switch from an anchorpane to another anchorpane
        home_pane.setDisable(true);
        home_pane.setVisible(false);
        edit_pane.setDisable(false);
        edit_pane.setVisible(true);

        // Fill columns with data
        mailid_label.setText(String.valueOf(dr_mailid));
        subject_field.setText(dr_subject);
        HTMLEditor.setHtmlText(dr_message);

    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        // Switch from an anchorpane to another anchorpane
        home_pane.setDisable(false);
        home_pane.setVisible(true);
        edit_pane.setDisable(true);
        edit_pane.setVisible(false);
    }

    @FXML
    private void handleSendToDatabase(ActionEvent event) throws IOException, SQLException {
        sendToDatabase(Integer.parseInt(mailid_label.getText()), subject_field.getText(), HTMLEditor.getHtmlText());
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

            fys.changeToAnotherFXML(taal[137], "mailsettings.fxml");

            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
