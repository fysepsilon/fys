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
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class StatistiekenController implements Initializable {

    @FXML
    private PieChart piechart;
    private int total = 0;
    private int foundAmount, lostAmount, destroyAmount, settleAmount, neverFoundAmount, depotAmount = 0;
    private int jan, feb, mar, apr, mei, jun, jul, aug, sep, okt, nov, dec = 0;
    
    @FXML
    private LineChart<Number, Number> linechart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Gevonden", 0),
                        new PieChart.Data("Vermist", 0),
                        new PieChart.Data("Vernietigd", 0),
                        new PieChart.Data("Afgehandeld", 0),
                        new PieChart.Data("Nooit gevonden", 0),
                        new PieChart.Data("Depot", 0));

        piechart.setTitle("Statussen");
        piechart.setData(pieChartData);
        FYS fys = new FYS();
        Statement stmt = null;
        Connection conn = null;
        int luggage = 0;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            //connectToDatabase(conn, stmt, "test", "root", "root");           
            String sql = "  SELECT x.status, COUNT(x.status) AS Count FROM "
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
        linechart.setTitle("Schadeclaims");
        linechart.setAnimated(true);
        linechart.getXAxis().setAutoRanging(true); 
        linechart.getYAxis().setAutoRanging(true); 
        
        XYChart.Series series = new XYChart.Series<>(); 
        series.setName("Aantal schadeclaims");
        series.getData().add(new XYChart.Data<>("jan", 0)); 
        series.getData().add(new XYChart.Data("feb", 0));
        series.getData().add(new XYChart.Data<>("mar", 0)); 
        series.getData().add(new XYChart.Data("apr", 0)); 
        series.getData().add(new XYChart.Data<>("mei", 0)); 
        series.getData().add(new XYChart.Data("jun", 0)); 
        series.getData().add(new XYChart.Data<>("jul", 0)); 
        series.getData().add(new XYChart.Data("aug", 0));
        series.getData().add(new XYChart.Data<>("sep", 0)); 
        series.getData().add(new XYChart.Data("okt", 0)); 
        series.getData().add(new XYChart.Data<>("nov", 0)); 
        series.getData().add(new XYChart.Data("dec", 0));
        
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
                    + "WHERE status = 4 and date LIKE '%' GROUP BY x.status, x.date";
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
        
        series.getData().set(0, new XYChart.Data<>("jan", jan));
        series.getData().set(1, new XYChart.Data("feb", feb));
        series.getData().set(2, new XYChart.Data<>("mar", mar)); 
        series.getData().set(3, new XYChart.Data("apr", apr)); 
        series.getData().set(4, new XYChart.Data<>("mei", mei)); 
        series.getData().set(5, new XYChart.Data("jun", jun)); 
        series.getData().set(6, new XYChart.Data<>("jul", jul)); 
        series.getData().set(7, new XYChart.Data("aug", aug));
        series.getData().set(8, new XYChart.Data<>("sep", sep)); 
        series.getData().set(9, new XYChart.Data("okt", okt)); 
        series.getData().set(10, new XYChart.Data<>("nov", nov)); 
        series.getData().set(11, new XYChart.Data("dec", dec));
    }
    
    
}
