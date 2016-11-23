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
import javafx.scene.chart.NumberAxis;
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
    private int foundAmount = 0;
    private int lostAmount = 0;
    private int destroyAmount = 0;
    private int settleAmount = 0;
    private int neverFoundAmount = 0;
    private int depotAmount = 0;
    
    @FXML
    public LineChart linechart;

    @FXML
    NumberAxis xAxis = new NumberAxis("Number saved", 1, 10.1, 1);

    @FXML
    NumberAxis yAxis = new NumberAxis("Calculated Value", 0, 100, 1);
    
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
                foundAmount = (rs.getString("status").equals("Gevonden") ? rs.getInt("Count") : foundAmount);
                lostAmount = (rs.getString("status").equals("Vermist") ? rs.getInt("Count") : lostAmount);
                destroyAmount = (rs.getString("status").equals("Vernietigd") ? rs.getInt("Count") : destroyAmount);
                settleAmount = (rs.getString("status").equals("Afgehandeld") ? rs.getInt("Count") : settleAmount);
                neverFoundAmount = (rs.getString("status").equals("Nooit gevonden") ? rs.getInt("Count") : neverFoundAmount);
                depotAmount = (rs.getString("status").equals("Depot") ? rs.getInt("Count") : depotAmount);
            }
            rs.close();
            conn.close();
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
        
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList(
                new LineChart.Series<Double, Double>("Series 1", FXCollections.observableArrayList(
                        new XYChart.Data<Double, Double>(0.0, 1.0),
                        new XYChart.Data<Double, Double>(1.2, 1.4),
                        new XYChart.Data<Double, Double>(2.2, 1.9),
                        new XYChart.Data<Double, Double>(2.7, 2.3),
                        new XYChart.Data<Double, Double>(2.9, 0.5)
                )),
                new LineChart.Series<Double, Double>("Series 2", FXCollections.observableArrayList(
                        new XYChart.Data<Double, Double>(0.0, 1.6),
                        new XYChart.Data<Double, Double>(0.8, 0.4),
                        new XYChart.Data<Double, Double>(1.4, 2.9),
                        new XYChart.Data<Double, Double>(2.1, 1.3),
                        new XYChart.Data<Double, Double>(2.6, 0.9)
                ))
        );
        linechart = new LineChart(xAxis, yAxis, lineChartData);
    }
    
    
}
