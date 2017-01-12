/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class StatistiekenController implements Initializable {

    @FXML
    private Pane home_pane, filterPane, pdfPane;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private int total = 0;
    @FXML
    private int foundAmount, lostAmount, destroyAmount, settleAmount, neverFoundAmount, depotAmount = 0;
    @FXML
    private int jan, feb, mar, apr, mei, jun, jul, aug, sep, okt, nov, dec = 0;
    @FXML
    private ComboBox year, month;
    @FXML
    private Button exportToPDF, filter, openpopup, cancel, exporttopdf;
    @FXML
    private Label mainFilterLabel, maandFilterLabel, jaarFilterLabel,
            popup_filterlabel, ErrorLabel, dateToLabel, dateFromLabel;
    @FXML
    private TextField dateFrom, dateTo;
    @FXML
    private ArrayList<String> years = new ArrayList<String>();
    @FXML
    private Connection conn = null;
    @FXML
    private Statement stmt = null;
    @FXML
    private final FYS fys = new FYS();
    @FXML
    private final taal language = new taal();
    @FXML
    private final String[] taal = language.getLanguage();
    @FXML
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data(taal[54], 0), new PieChart.Data(taal[55], 0),
            new PieChart.Data(taal[56], 0), new PieChart.Data(taal[57], 0),
            new PieChart.Data(taal[58], 0), new PieChart.Data(taal[59], 0));
    @FXML
    private XYChart.Series series = new XYChart.Series<>();
    @FXML
    private String[] ExportToPdfTexts = {"Status", "Insurance claims", "Amount of Insurance claims",
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Found",
        "Lost", "Destroyed", "Completed", "Never found", "Depot"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * De taal veranderen
         */
        exportToPDF.setText(taal[138]);
        openpopup.setText(taal[47]);
        filter.setText(taal[47]);
        mainFilterLabel.setText(taal[173]);
        maandFilterLabel.setText(taal[171]);
        jaarFilterLabel.setText(taal[172]);
        popup_filterlabel.setText(taal[47]);
        dateToLabel.setText(taal[142]);
        dateFromLabel.setText(taal[141]);
        dateFrom.setPromptText("01-01-1970");
        dateTo.setPromptText("31-12-2016");
        cancel.setText(taal[127]);
        exporttopdf.setText(taal[140]);
        /**
         * Krijg alle unieke jaren vanuit de database. Wanneer de bagage
         * geregistreerd zijn als vermist of gevonden.
         */
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT DISTINCT YEAR(STR_TO_DATE(date, \"%Y-%m-%d\")) as date "
                    + "from bagagedatabase.luggage_status";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //voeg alle jaren in de array van years.
                years.add(rs.getString("date"));
            }
            rs.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Voeg alle jaren en maanden vanuit array naar combobox.
        for (int i = 0; i < years.size(); i++) {
            year.getItems().add(years.get(i));
        }
        month.getItems().addAll(
                taal[109], taal[110], taal[111], taal[112], taal[113], taal[114],
                taal[115], taal[116], taal[117], taal[118], taal[119], taal[120]);

        //PIECHART
        //Begin met het zetten van de titel van de pieChart.
        //Voeg vervolgens lege data toe aan de pieChart.
        pieChart.setTitle(taal[75]);
        pieChart.setData(pieChartData);
        int luggage = 0;

        //Krijg alle data van de bagages vanuit de database en voeg de aantal toe aan de aantallen van die status.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT status, COUNT(status) AS Count FROM "
                    + "bagagedatabase.luggage_status GROUP BY status;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                luggage++;
                //Voeg per status de aantallen toe.
                foundAmount = (rs.getInt("status") == 0 ? rs.getInt("Count") : foundAmount);
                lostAmount = (rs.getInt("status") == 1 ? rs.getInt("Count") : lostAmount);
                destroyAmount = (rs.getInt("status") == 2 ? rs.getInt("Count") : destroyAmount);
                settleAmount = (rs.getInt("status") == 3 ? rs.getInt("Count") : settleAmount);
                neverFoundAmount = (rs.getInt("status") == 4 ? rs.getInt("Count") : neverFoundAmount);
                depotAmount = (rs.getInt("status") == 5 ? rs.getInt("Count") : depotAmount);
            }
            rs.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Voeg per status de aantal toe in de data array.
        pieChartData.get(0).setPieValue(foundAmount);
        pieChartData.get(1).setPieValue(lostAmount);
        pieChartData.get(2).setPieValue(destroyAmount);
        pieChartData.get(3).setPieValue(settleAmount);
        pieChartData.get(4).setPieValue(neverFoundAmount);
        pieChartData.get(5).setPieValue(depotAmount);

        //Voor elk aantal tel ze met elkaar op en sla het op bij total.
        for (PieChart.Data d : pieChart.getData()) {
            total += d.getPieValue();
        }

        //Verander de tekst van elke piechartdata. naar: (aantal statusnaam: percentage).
        pieChartData.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        (int) data.getPieValue(), " ", data.getName(), ": ",
                        (total == 0 || (int) data.getPieValue() == 0) ? 0 : (int) (data.getPieValue() / total * 100), "%"
                )
        ));

        //LINECHART
        lineChart.setTitle(taal[76]);
        lineChart.setAnimated(true);
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);

        //Voeg de lege data toe aan de piechart en zet alvast de namen van de x en y as.
        series.setName(taal[77]);
        series.getData().add(new XYChart.Data<>(taal[78], 0));
        series.getData().add(new XYChart.Data(taal[79], 0));
        series.getData().add(new XYChart.Data<>(taal[80], 0));
        series.getData().add(new XYChart.Data(taal[81], 0));
        series.getData().add(new XYChart.Data<>(taal[82], 0));
        series.getData().add(new XYChart.Data(taal[83], 0));
        series.getData().add(new XYChart.Data<>(taal[84], 0));
        series.getData().add(new XYChart.Data(taal[85], 0));
        series.getData().add(new XYChart.Data<>(taal[86], 0));
        series.getData().add(new XYChart.Data(taal[87], 0));
        series.getData().add(new XYChart.Data<>(taal[88], 0));
        series.getData().add(new XYChart.Data(taal[89], 0));

        //Voeg de lege data toe aan de linechart.
        lineChart.setCreateSymbols(true);
        lineChart.getData().add(series);

        //Krijg alle gegevens van de aangevraagde schadeclaims en voeg ze toe aan variablen.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT date, COUNT(date) as Count FROM bagagedatabase.luggage_status "
                    + "WHERE status = 6 GROUP BY date;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                if (rs.getString("date") != null) {
                    //Krijg van elke date record de maand eruit.
                    String str[] = rs.getString("date").split("-");
                    int month = Integer.parseInt(str[1]);

                    //Zet per maand de aantallen toe in de variable.
                    jan = (month == 1 ? jan += rs.getInt("Count") : jan);
                    feb = (month == 2 ? feb += rs.getInt("Count") : feb);
                    mar = (month == 3 ? mar += rs.getInt("Count") : mar);
                    apr = (month == 4 ? apr += rs.getInt("Count") : apr);
                    mei = (month == 5 ? jan += rs.getInt("Count") : mei);
                    jun = (month == 6 ? jun += rs.getInt("Count") : jun);
                    jul = (month == 7 ? jul += rs.getInt("Count") : jul);
                    aug = (month == 8 ? aug += rs.getInt("Count") : aug);
                    sep = (month == 9 ? sep += rs.getInt("Count") : sep);
                    okt = (month == 10 ? okt += rs.getInt("Count") : okt);
                    nov = (month == 11 ? nov += rs.getInt("Count") : nov);
                    dec = (month == 12 ? dec += rs.getInt("Count") : dec);
                }
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Update de waarden die in de linechart zijn toegevoegd. 
        series.getData().set(0, new XYChart.Data<>(taal[78], jan));
        series.getData().set(1, new XYChart.Data(taal[79], feb));
        series.getData().set(2, new XYChart.Data<>(taal[80], mar));
        series.getData().set(3, new XYChart.Data(taal[81], apr));
        series.getData().set(4, new XYChart.Data<>(taal[82], mei));
        series.getData().set(5, new XYChart.Data(taal[83], jun));
        series.getData().set(6, new XYChart.Data<>(taal[84], jul));
        series.getData().set(7, new XYChart.Data(taal[85], aug));
        series.getData().set(8, new XYChart.Data<>(taal[86], sep));
        series.getData().set(9, new XYChart.Data(taal[87], okt));
        series.getData().set(10, new XYChart.Data<>(taal[88], nov));
        series.getData().set(11, new XYChart.Data(taal[89], dec));
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

    //Wanneer de gebruiker op filteren klikt.
    @FXML
    private void handleFilterAction(ActionEvent event) throws IOException, InterruptedException {
        //PIECHART
        //close filterpane.
        filterPane.setVisible(false);
        home_pane.setDisable(false);
        //Maak alle variablen en arrays weer leeg.
        int luggage = 0, foundAmount = 0, lostAmount = 0, destroyAmount = 0, settleAmount = 0,
                neverFoundAmount = 0, depotAmount = 0;
        int jan = 0, feb = 0, mar = 0, apr = 0, mei = 0, jun = 0, jul = 0, aug = 0,
                sep = 0, okt = 0, nov = 0, dec = 0;
        total = 0;
        series.getData().clear();
        pieChartData = FXCollections.observableArrayList();

        //Krijg alle data voor die piechart waar het jaar en maand gelijk is aan het geselecteerde jaar en maand.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT status, COUNT(status) AS Count, YEAR(date) as year, MONTH(date) as month FROM "
                    + "bagagedatabase.luggage_status ";
            if ((year.getSelectionModel().getSelectedItem().toString() == null
                    || year.getSelectionModel().getSelectedItem().toString().trim().isEmpty())
                    && (month.getSelectionModel().getSelectedItem().toString() == null
                    || month.getSelectionModel().getSelectedItem().toString().trim().isEmpty())) {
                sql += "WHERE YEAR(date) LIKE \"" + year.getSelectionModel().getSelectedItem().toString() + "%\" "
                        + "AND MONTH(date) LIKE \"" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "%\" "
                        + "GROUP BY status";
            } else if ((year.getSelectionModel().getSelectedItem().toString() == null
                    || year.getSelectionModel().getSelectedItem().toString().trim().isEmpty())) {
                sql += "WHERE YEAR(date) LIKE \"%%\" "
                        + "AND MONTH(date) = \"" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "%\" "
                        + "GROUP BY status";
            } else if (month.getSelectionModel().getSelectedItem().toString() == null
                    || month.getSelectionModel().getSelectedItem().toString().trim().isEmpty()) {
                sql += "WHERE YEAR(date) = \"" + year.getSelectionModel().getSelectedItem().toString() + "%\" "
                        + "AND MONTH(date) LIKE \"%%\" "
                        + "GROUP BY status";
            } else {
                sql += "WHERE YEAR(date) = \"" + year.getSelectionModel().getSelectedItem().toString() + "%\" "
                        + "AND MONTH(date) = \"" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "%\" "
                        + "GROUP BY status";
            }
            //Voeg de aantallen van de statussen toe aan de variablen. 
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    luggage++;
                    //System.out.println(rs.getString("status") + " " + rs.getInt("Count"));
                    //Retrieve by column name
                    foundAmount = (rs.getInt("status") == 0 ? rs.getInt("Count") : foundAmount);
                    lostAmount = (rs.getInt("status") == 1 ? rs.getInt("Count") : lostAmount);
                    destroyAmount = (rs.getInt("status") == 2 ? rs.getInt("Count") : destroyAmount);
                    settleAmount = (rs.getInt("status") == 3 ? rs.getInt("Count") : settleAmount);
                    neverFoundAmount = (rs.getInt("status") == 4 ? rs.getInt("Count") : neverFoundAmount);
                    depotAmount = (rs.getInt("status") == 5 ? rs.getInt("Count") : depotAmount);
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Voeg de data toe aan de piechart array aan de juiste status. 
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(taal[54], foundAmount), new PieChart.Data(taal[55], lostAmount),
                new PieChart.Data(taal[56], destroyAmount), new PieChart.Data(taal[57], settleAmount),
                new PieChart.Data(taal[58], neverFoundAmount), new PieChart.Data(taal[59], depotAmount));

        //Update de titel
        pieChart.setTitle(taal[75]);

        //Update de gegevens van de piechart.
        pieChart.setData(pieChartData);

        //Voor elke aantal tel ze met elkaar op en sla het op bij total.
        for (PieChart.Data d : pieChart.getData()) {
            total += d.getPieValue();
        }

        //Verander de tekst van elke piechartdata. naar: (aantal statusnaam: percentage).
        pieChartData.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        (int) data.getPieValue(), " ", data.getName(), ": ",
                        (total == 0 || (int) data.getPieValue() == 0) ? 0 : (int) (data.getPieValue() / total * 100), "%"
                )
        ));

        //LINECHART
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //Krijg alle schadeclaims voor de linechart waar het jaar en maand gelijk is aan het geselecteerde jaar en maand.
            String sql = "SELECT date, YEAR(date) AS year, MONTH(date) AS month, COUNT(date) as Count FROM luggage_status ";
            if ((year.getSelectionModel().getSelectedItem().toString() == null || year.getSelectionModel().getSelectedItem().toString().trim().isEmpty())
                    && (month.getSelectionModel().getSelectedItem().toString() == null || month.getSelectionModel().getSelectedItem().toString().trim().isEmpty())) {
                sql += "WHERE YEAR(date) LIKE \"%%\" "
                        + "AND MONTH(date) LIKE \"%%\" and status = 6 GROUP BY date ";
            } else if ((year.getSelectionModel().getSelectedItem().toString() == null || year.getSelectionModel().getSelectedItem().toString().trim().isEmpty())) {
                sql += "WHERE YEAR(date) LIKE \"%%\" "
                        + "AND MONTH(date) = \"" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "\" "
                        + "and status = 6 GROUP BY date ";
            } else if (month.getSelectionModel().getSelectedItem().toString() == null || month.getSelectionModel().getSelectedItem().toString().trim().isEmpty()) {
                sql += "WHERE YEAR(date) = \"" + year.getSelectionModel().getSelectedItem().toString() + "\" "
                        + "AND MONTH(date) LIKE \"%%\" and status = 6 GROUP BY date ";
            } else {
                sql += "WHERE YEAR(date) = \"" + year.getSelectionModel().getSelectedItem().toString() + "\" "
                        + "AND MONTH(date) = \"" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "\" and status = 6 "
                        + "GROUP BY date ";
            }
            //Voeg de aantal schadeclaims per maand toe aan variablen.
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                if (rs.getString("date") != null) {
                    //Krijg van elke date record de maand eruit.
                    String str[] = rs.getString("date").split("-");
                    int month = Integer.parseInt(str[1]);
                    jan = (month == 1 ? jan += rs.getInt("Count") : jan);
                    feb = (month == 2 ? feb += rs.getInt("Count") : feb);
                    mar = (month == 3 ? mar += rs.getInt("Count") : mar);
                    apr = (month == 4 ? apr += rs.getInt("Count") : apr);
                    mei = (month == 5 ? jan += rs.getInt("Count") : mei);
                    jun = (month == 6 ? jun += rs.getInt("Count") : jun);
                    jul = (month == 7 ? jul += rs.getInt("Count") : jul);
                    aug = (month == 8 ? aug += rs.getInt("Count") : aug);
                    sep = (month == 9 ? sep += rs.getInt("Count") : sep);
                    okt = (month == 10 ? okt += rs.getInt("Count") : okt);
                    nov = (month == 11 ? nov += rs.getInt("Count") : nov);
                    dec = (month == 12 ? dec += rs.getInt("Count") : dec);
                }
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        //Update de linechart naar de waardes die gewenst is.
        lineChart.setTitle(taal[76]);
        series.setName(taal[77]);
        series.getData().add(new XYChart.Data<>(taal[78], jan));
        series.getData().add(new XYChart.Data(taal[79], feb));
        series.getData().add(new XYChart.Data<>(taal[80], mar));
        series.getData().add(new XYChart.Data(taal[81], apr));
        series.getData().add(new XYChart.Data<>(taal[82], mei));
        series.getData().add(new XYChart.Data(taal[83], jun));
        series.getData().add(new XYChart.Data<>(taal[84], jul));
        series.getData().add(new XYChart.Data(taal[85], aug));
        series.getData().add(new XYChart.Data<>(taal[86], sep));
        series.getData().add(new XYChart.Data(taal[87], okt));
        series.getData().add(new XYChart.Data<>(taal[88], nov));
        series.getData().add(new XYChart.Data(taal[89], dec));
    }

    @FXML
    private void handleCancelPdf(ActionEvent event) throws IOException {
        home_pane.setDisable(false);
        pdfPane.setVisible(false);
    }
    
    @FXML
    private void handleOpenPdf(ActionEvent event) throws IOException {
        home_pane.setDisable(true);
        pdfPane.setVisible(true);
    }

    //Wanneer de gebruiker op exporteren klikt.
    @FXML
    private void handleExportToPDFAction(ActionEvent event) throws IOException {
        if ((dateFrom.getText() == null || dateFrom.getText().trim().isEmpty())
                || (dateTo.getText() == null
                || dateTo.getText().trim().isEmpty())) {
            ErrorLabel.setText(taal[93]);
            ErrorLabel.setVisible(true);
        } else {
            String dateFromInput = dateFrom.getText();
            String dateToInput = dateTo.getText();
            //Doe dit alleen wanneer er waardes zijn ingevuld.
            lineChart.setAnimated(false);
            pieChart.setAnimated(false);
            //PIECHART
            //Maak alle data en aantallen weer leeg.
            int luggage = 0, foundAmount = 0, lostAmount = 0, destroyAmount = 0, settleAmount = 0,
                    neverFoundAmount = 0, depotAmount = 0;
            int jan = 0, feb = 0, mar = 0, apr = 0, mei = 0, jun = 0, jul = 0, aug = 0,
                    sep = 0, okt = 0, nov = 0, dec = 0;
            total = 0;
            series.getData().clear();
            pieChartData = FXCollections.observableArrayList();

            //Krijg alle data voor de pietchart die tussen de periode van DateFrom en DateTo ligt.
            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                //connectToDatabase(conn, stmt, "test", "root", "root"); 
                String sql = "SELECT status, date, COUNT(status) AS Count FROM bagagedatabase.luggage_status "
                        + "WHERE date >= \"" + fys.convertToDutchDate(dateFromInput) + "\" "
                        + "AND date <= \"" + fys.convertToDutchDate(dateToInput) + "\" "
                        + "GROUP BY status";
                //Voeg alle aantallen per status toe aan variabelen.
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        luggage++;
                        //Retrieve by column name
                        foundAmount = (rs.getInt("status") == 0 ? rs.getInt("Count") : foundAmount);
                        lostAmount = (rs.getInt("status") == 1 ? rs.getInt("Count") : lostAmount);
                        destroyAmount = (rs.getInt("status") == 2 ? rs.getInt("Count") : destroyAmount);
                        settleAmount = (rs.getInt("status") == 3 ? rs.getInt("Count") : settleAmount);
                        neverFoundAmount = (rs.getInt("status") == 4 ? rs.getInt("Count") : neverFoundAmount);
                        depotAmount = (rs.getInt("status") == 5 ? rs.getInt("Count") : depotAmount);
                    }
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            //Voeg de waardes toe aan de array.
            pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data(ExportToPdfTexts[15], foundAmount), new PieChart.Data(ExportToPdfTexts[16], lostAmount),
                    new PieChart.Data(ExportToPdfTexts[17], destroyAmount), new PieChart.Data(ExportToPdfTexts[18], settleAmount),
                    new PieChart.Data(ExportToPdfTexts[19], neverFoundAmount), new PieChart.Data(ExportToPdfTexts[20], depotAmount));

            //Update de titel
            pieChart.setTitle(ExportToPdfTexts[0]);

            //Update de piechart met de gevraagde gegevens.
            pieChart.setData(pieChartData);

            //Voor elke aantal tel ze met elkaar op en sla het op bij total.
            for (PieChart.Data d : pieChart.getData()) {
                total += d.getPieValue();
            }

            //Verander de tekst van elke piechartdata. naar: (aantal statusnaam: percentage).
            pieChartData.forEach(data -> data.nameProperty().bind(
                    Bindings.concat(
                            (int) data.getPieValue(), " ", data.getName(), ": ",
                            (total == 0 || (int) data.getPieValue() == 0) ? 0 : (int) (data.getPieValue() / total * 100), "%"
                    )
            ));

            //LINECHART
            //Krijg alle data voor de linechart die tussen de periode van DateFrom en DateTo ligt.
            try {
                conn = fys.connectToDatabase(conn);
                stmt = conn.createStatement();
                String sql = "SELECT date, COUNT(date) as Count FROM bagagedatabase.luggage_status "
                        + "WHERE status = 6 AND date >= \"" + fys.convertToDutchDate(dateFromInput) + "\" "
                        + "AND date <= \"" + fys.convertToDutchDate(dateToInput) + "\" "
                        + "GROUP BY date";
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //Retrieve by column name
                    //Voeg alle aantallen per maand toe aan variabelen.
                    if (rs.getString("date") != null) {
                        //Krijg van elke date record de maand eruit.
                        String str[] = rs.getString("date").split("-");
                        int month = Integer.parseInt(str[1]);
                        jan = (month == 1 ? jan += rs.getInt("Count") : jan);
                        feb = (month == 2 ? feb += rs.getInt("Count") : feb);
                        mar = (month == 3 ? mar += rs.getInt("Count") : mar);
                        apr = (month == 4 ? apr += rs.getInt("Count") : apr);
                        mei = (month == 5 ? jan += rs.getInt("Count") : mei);
                        jun = (month == 6 ? jun += rs.getInt("Count") : jun);
                        jul = (month == 7 ? jul += rs.getInt("Count") : jul);
                        aug = (month == 8 ? aug += rs.getInt("Count") : aug);
                        sep = (month == 9 ? sep += rs.getInt("Count") : sep);
                        okt = (month == 10 ? okt += rs.getInt("Count") : okt);
                        nov = (month == 11 ? nov += rs.getInt("Count") : nov);
                        dec = (month == 12 ? dec += rs.getInt("Count") : dec);
                    }
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            //Update de titel
            lineChart.setTitle(ExportToPdfTexts[1]);

            //Update de linechart naar de waardes die gewenst is.
            series.setName(ExportToPdfTexts[2]);
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[3], jan));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[4], feb));
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[5], mar));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[6], apr));
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[7], mei));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[8], jun));
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[9], jul));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[10], aug));
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[11], sep));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[12], okt));
            series.getData().add(new XYChart.Data<>(ExportToPdfTexts[13], nov));
            series.getData().add(new XYChart.Data(ExportToPdfTexts[14], dec));

            //Update de piechart en linechart voordat er een screenshot van genomen wordt.
            lineChart.applyCss();
            lineChart.layout();
            pieChart.applyCss();
            pieChart.layout();

            //Maak een screenshot van de piechart en linechart.
            savePieChartAsPng();
            saveLineChartAsPng();

            try {

                //Krijg de datum van vandaag voor pdf.
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String dateString = dateFormat.format(date);

                //Krijg de content van de template pdf en maake nieuw pdf aan.
                File pdfdoc = new File("src/fys/templates/statisticstemplate.pdf");
                PDDocument document = null;
                document = PDDocument.load(pdfdoc);
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
                List<PDField> fields = acroForm.getFields();

                // set the text in the form-field <-- does work
                //Verander voor elk veld de waardes.
                for (PDField field : fields) {
                    if (field.getFullyQualifiedName().equals("found")) {
                        field.setValue(String.valueOf(foundAmount));
                    }
                    if (field.getFullyQualifiedName().equals("lost")) {
                        field.setValue(String.valueOf(lostAmount));
                    }
                    if (field.getFullyQualifiedName().equals("destroyed")) {
                        field.setValue(String.valueOf(destroyAmount));
                    }
                    if (field.getFullyQualifiedName().equals("completed")) {
                        field.setValue(String.valueOf(settleAmount));
                    }
                    if (field.getFullyQualifiedName().equals("neverfound")) {
                        field.setValue(String.valueOf(neverFoundAmount));
                    }
                    if (field.getFullyQualifiedName().equals("depot")) {
                        field.setValue(String.valueOf(depotAmount));
                    }

                    if (field.getFullyQualifiedName().equals("jan")) {
                        field.setValue(String.valueOf(jan));
                    }
                    if (field.getFullyQualifiedName().equals("feb")) {
                        field.setValue(String.valueOf(feb));
                    }
                    if (field.getFullyQualifiedName().equals("mar")) {
                        field.setValue(String.valueOf(mar));
                    }
                    if (field.getFullyQualifiedName().equals("apr")) {
                        field.setValue(String.valueOf(apr));
                    }
                    if (field.getFullyQualifiedName().equals("may")) {
                        field.setValue(String.valueOf(mei));
                    }
                    if (field.getFullyQualifiedName().equals("jun")) {
                        field.setValue(String.valueOf(jun));
                    }
                    if (field.getFullyQualifiedName().equals("jul")) {
                        field.setValue(String.valueOf(jul));
                    }
                    if (field.getFullyQualifiedName().equals("aug")) {
                        field.setValue(String.valueOf(aug));
                    }
                    if (field.getFullyQualifiedName().equals("sep")) {
                        field.setValue(String.valueOf(sep));
                    }
                    if (field.getFullyQualifiedName().equals("oct")) {
                        field.setValue(String.valueOf(okt));
                    }
                    if (field.getFullyQualifiedName().equals("nov")) {
                        field.setValue(String.valueOf(nov));
                    }
                    if (field.getFullyQualifiedName().equals("dec")) {
                        field.setValue(String.valueOf(dec));
                    }
                    if (field.getFullyQualifiedName().equals("date")) {
                        field.setValue(String.valueOf(dateString));
                    }
                    if (field.getFullyQualifiedName().equals("period")) {
                        field.setValue(String.valueOf(dateFromInput + " | " + dateToInput));
                    }
                }

                //Retrieving the page
                PDPage page = document.getPage(0);

                //Creating PDImageXObject object
                loginController login = new loginController();
                PDImageXObject pieChartImage = PDImageXObject.createFromFile("src/fys/statistieken/PieChart_" + login.getUsersName() + ".png", document);
                PDImageXObject lineChartImage = PDImageXObject.createFromFile("src/fys/statistieken/LineChart_" + login.getUsersName() + ".png", document);

                //creating the PDPageContentStream object
                PDPageContentStream contents = new PDPageContentStream(document, page, true, true, true);

                //Drawing the image in the PDF document
                contents.drawImage(pieChartImage, 75, 0, 350, 300);
                contents.drawImage(lineChartImage, 425, 0, 350, 300);

                //Closing the PDPageContentStream object
                contents.close();

                //Sla het docment op.
                document.save("src/fys/statistieken/statistics" + dateFromInput + dateToInput + ".pdf");
                document.close();

                //Verwijder de plaatjes die waren opgeslagen.
                savePieChartAsPng().delete();
                saveLineChartAsPng().delete();
                
                //Sluit de popup
                home_pane.setDisable(false);
                pdfPane.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(StatistiekenController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }

    public File savePieChartAsPng() {
        WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
        // TODO: probably use a file chooser here
        loginController login = new loginController();
        File file = new File("src/fys/statistieken/PieChart_" + login.getUsersName() + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
        return file;
    }

    public File saveLineChartAsPng() {
        WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
        // TODO: probably use a file chooser here
        loginController login = new loginController();
        File file = new File("src/fys/statistieken/LineChart_" + login.getUsersName() + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
        return file;
    }
}
