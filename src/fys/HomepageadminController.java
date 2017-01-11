/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Team Epsilon
 */
public class HomepageadminController implements Initializable {
    @FXML private AnchorPane AnchorPane;
    @FXML private int foundAmount, lostAmount, destroyAmount, settleAmount, neverFoundAmount, depotAmount = 0;
    @FXML private int jan, feb, mar, apr, mei, jun, jul, aug, sep, okt, nov, dec = 0;
    @FXML private FYS fys = new FYS();
    @FXML private Statement stmt = null;
    @FXML private Connection conn = null;
    @FXML private taal language = new taal();
    @FXML private String[] taal = language.getLanguage();
    @FXML private LineChart<Number, Number> linechart;
    @FXML private Label tableTitle, linechartTitle;
    @FXML private TableView<Status> table;
    @FXML private Calendar calendar = new GregorianCalendar();
    @FXML private Date trialTime = new Date();
    @FXML private ObservableList<Status> data = FXCollections.observableArrayList(
            new Status(0, taal[54], 0), new Status(0, taal[55], 0),
            new Status(0, taal[56], 0), new Status(0, taal[57], 0),
            new Status(0, taal[58], 0), new Status(0, taal[59], 0));
    @FXML private TableColumn week, status, amount;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setStyle("");
        
        //Krijg de datum van vandaag terug en split het in jaar en maand.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateTimeString = dateFormat.format(date);
        String[] tokens = dateTimeString.split("-");
        String year = tokens[0];
        String months = tokens[1];
        calendar.setTime (trialTime);
//        System.out.println("Week number:" + 
//            calendar.get(Calendar.WEEK_OF_YEAR));
       
        //LINECHART
        linechartTitle.setText(taal[76] + " " + fys.getMonthName(months ) +" " + year);
        linechart.setAnimated(true);
        linechart.getXAxis().setAutoRanging(true); 
        linechart.getYAxis().setAutoRanging(true); 
        
        //Voeg de lege data toe aan de piechart en zet alvast de namen van de x en y as.
        XYChart.Series series = new XYChart.Series<>(); 
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
        linechart.setCreateSymbols(true);
        linechart.getData().add(series); 
        
        //Krijg alle gegevens van deze maand van de aangevraagde schadeclaims en voeg ze toe aan variablen.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();        
            String sql = "SELECT date, YEAR(date) AS year, MONTH(date) AS month, COUNT(date) as Count FROM bagagedatabase.insurance_claim "
                    +"WHERE YEAR(date) LIKE \"%" + year + "%\" "
                    + "AND MONTH(date) LIKE \"%" + months + "%\" ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.getString("date") != null){
                    //Retrieve by column name
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
        
        //Voeg de aantal 0 toe in de tabel en de namen van de statussen.
        tableTitle.setText(taal[48] + " " + fys.getMonthName(months ) +" " + year);
        week.setText(taal[143]);
        status.setText(taal[48]);
        amount.setText(taal[132]);
        getStatusData(year, months);
        week.setCellValueFactory(new PropertyValueFactory<>("weekNumber"));
        status.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        week.setStyle("-fx-alignment: CENTER;");
        status.setStyle("-fx-alignment: CENTER;");
        amount.setStyle("-fx-alignment: CENTER;");
        table.setItems(data);
    }
    
    /**
     * 
     * @param year set the value of year.
     * @param months set the value of month.
     */
    public void getStatusData(String year, String months) {
        //Krijg de aantallen van deze maand en voeg ze toe in de tabel.
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");
            String sql = "SELECT x.status, YEAR(x.date) AS year, MONTH(x.date) AS month, COUNT(x.status) AS Count "
                    + "FROM (SELECT status, date FROM lost, airport "
                    + "WHERE lost.lost_and_found_id = airport.lost_and_found_id "
                    + "UNION ALL SELECT status, date FROM found, airport "
                    + "WHERE found.lost_and_found_id = airport.lost_and_found_id) x "
                    + "WHERE YEAR(x.date) LIKE \"%" + year + "%\" "
                    + "AND MONTH(x.date) LIKE \"%" + months + "%\" "
                    + "GROUP BY x.status";
            

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    //Voeg de aantallen toe aan de variables.
                    foundAmount = (rs.getInt("status") == 0 ? rs.getInt("Count") : foundAmount);
                    lostAmount = (rs.getInt("status") == 1 ? rs.getInt("Count") : lostAmount);
                    destroyAmount = (rs.getInt("status") == 2 ? rs.getInt("Count") : destroyAmount);
                    settleAmount = (rs.getInt("status") == 3 ? rs.getInt("Count") : settleAmount);
                    neverFoundAmount = (rs.getInt("status") == 4 ? rs.getInt("Count") : neverFoundAmount);
                    depotAmount = (rs.getInt("status") == 5 ? rs.getInt("Count") : depotAmount);
                }
            }
            
            //Update de tabel met de aantallen.
            data.get(0).setAmount(foundAmount);
            data.get(1).setAmount(lostAmount);
            data.get(2).setAmount(destroyAmount);
            data.get(3).setAmount(settleAmount);
            data.get(4).setAmount(neverFoundAmount);
            data.get(5).setAmount(depotAmount);
            
            //Update de tabel met weeknummer.
            data.get(0).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            data.get(1).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            data.get(2).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            data.get(3).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            data.get(4).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            data.get(5).setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
            conn.close();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
