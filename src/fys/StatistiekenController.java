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
import javafx.scene.chart.CategoryAxis;
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
        
        //LINECHART
        linechart.setTitle("Schadeclaims");
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
        
        System.out.println(series.getData().get(0));
        
        linechart.setCreateSymbols(true);
        linechart.getData().add(series); 
    }
    
    
}
