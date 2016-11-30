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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class StatistiekenController implements Initializable {

    @FXML private PieChart piechart;
    @FXML private LineChart<Number, Number> linechart;
    @FXML private int total = 0;
    @FXML private int foundAmount, lostAmount, destroyAmount, settleAmount, neverFoundAmount, depotAmount = 0;
    @FXML private int jan, feb, mar, apr, mei, jun, jul, aug, sep, okt, nov, dec = 0;
    @FXML private ComboBox year, month;
    @FXML private ArrayList<String> years = new ArrayList<String>();
    @FXML private Connection conn = null; 
    @FXML private Statement stmt = null; 
    @FXML private FYS fys = new FYS();
    @FXML private taal language = new taal();
    @FXML private String[] taal = language.getLanguage();
    @FXML private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data(taal[54], 0), new PieChart.Data(taal[55], 0),
            new PieChart.Data(taal[56], 0), new PieChart.Data(taal[57], 0),
            new PieChart.Data(taal[58], 0), new PieChart.Data(taal[59], 0));
    @FXML private XYChart.Series series = new XYChart.Series<>(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT DISTINCT YEAR(STR_TO_DATE(date, \"%Y-%m-%d\")) as date "
                    + "from bagagedatabase.airport_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                years.add(rs.getString("date"));
            }
            rs.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        for (int i = 0; i < years.size(); i++) {
            year.getItems().add(years.get(i));
        }
        month.getItems().addAll(
                taal[109], taal[110], taal[111], taal[112], taal[113], taal[114],
                taal[115], taal[116], taal[117], taal[118], taal[119], taal[120]);
        
        

        piechart.setTitle(taal[75]);
        piechart.setData(pieChartData);
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT x.status, COUNT(x.status) AS Count FROM "
                    + "(SELECT status FROM lost_table "
                    + "UNION ALL "
                    + "SELECT status FROM found_table) x "
                    + "GROUP BY x.status";
            ResultSet rs = stmt.executeQuery(sql);
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
            rs.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        pieChartData.get(0).setPieValue(foundAmount);
        pieChartData.get(1).setPieValue(lostAmount);
        pieChartData.get(2).setPieValue(destroyAmount);
        pieChartData.get(3).setPieValue(settleAmount);
        pieChartData.get(4).setPieValue(neverFoundAmount);
        pieChartData.get(5).setPieValue(depotAmount);
        
        for (PieChart.Data d : piechart.getData()) {
            total += d.getPieValue();
        }
        
        pieChartData.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        (int) data.getPieValue(), " ", data.getName(), ": ", 
                        (total == 0 || (int) data.getPieValue() == 0) ? 0 : (int)( data.getPieValue() / total * 100), "%"
                )
        ));
        
        //LINECHART
        linechart.setTitle(taal[76]);
        linechart.setAnimated(true);
        linechart.getXAxis().setAutoRanging(true); 
        linechart.getYAxis().setAutoRanging(true);
        
        
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
        
        linechart.setCreateSymbols(true);
        linechart.getData().add(series); 
        
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();        
            String sql = "SELECT x.status, x.date, COUNT(x.status) AS Count FROM "
                    + "(SELECT status, date FROM lost_table, airport_table WHERE status = 4 "
                    + "AND lost_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "UNION ALL "
                    + "SELECT status, date FROM found_table, airport_table WHERE status = 4 "
                    + "AND found_table.lost_and_found_id = airport_table.lost_and_found_id) x "
                    + "WHERE status = 4 GROUP BY x.status, x.date";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
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
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
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
    private void handleFilterAction(ActionEvent event) throws IOException {
        //PIECHART
        int luggage = 0, foundAmount = 0, lostAmount = 0, destroyAmount = 0, settleAmount = 0, 
                neverFoundAmount = 0, depotAmount = 0;
        int jan = 0, feb = 0, mar = 0, apr = 0, mei = 0, jun = 0, jul = 0, aug = 0,
                sep = 0, okt = 0, nov = 0, dec = 0;
        total = 0;
        series.getData().clear();
        pieChartData = FXCollections.observableArrayList();
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "SELECT x.status, YEAR(x.date) AS year, MONTH(x.date) AS month, COUNT(x.status) AS Count "
                    + "FROM (SELECT status, date FROM lost_table, airport_table "
                    + "WHERE lost_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "UNION ALL SELECT status, date FROM found_table, airport_table "
                    + "WHERE found_table.lost_and_found_id = airport_table.lost_and_found_id) x "
                    + "WHERE YEAR(x.date) LIKE \"%" + year.getSelectionModel().getSelectedItem().toString() + "%\" "
                    + "AND MONTH(x.date) LIKE \"%" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "%\" "
                    + "GROUP BY x.status";
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
        
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(taal[54], foundAmount), new PieChart.Data(taal[55], lostAmount),
                new PieChart.Data(taal[56], destroyAmount), new PieChart.Data(taal[57], settleAmount),
                new PieChart.Data(taal[58], neverFoundAmount), new PieChart.Data(taal[59], depotAmount));
        piechart.setData(pieChartData);

        for (PieChart.Data d : piechart.getData()) {
            total += d.getPieValue();
        }

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
            String sql = "SELECT x.status, x.date, YEAR(x.date) AS year, MONTH(x.date) AS month, COUNT(x.status) AS Count "
                    + "FROM (SELECT status, date FROM lost_table, airport_table "
                    + "WHERE status = 4 AND lost_table.lost_and_found_id = airport_table.lost_and_found_id "
                    + "UNION ALL "
                    + "SELECT status, date FROM found_table, airport_table "
                    + "WHERE status = 4 AND found_table.lost_and_found_id = airport_table.lost_and_found_id) x "
                    + "WHERE status = 4 AND YEAR(x.date) LIKE \"%" + year.getSelectionModel().getSelectedItem().toString() + "%\" "
                    + "AND MONTH(x.date) LIKE \"%" + fys.getMonthNumber(month.getSelectionModel().getSelectedItem().toString()) + "%\" "
                    + "GROUP BY x.status, x.date";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
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
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println(series.getData());
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
    
    
}
