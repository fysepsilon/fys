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
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Paras
 */
public class HomepageadminController implements Initializable {
    @FXML private LineChart<Number, Number> linechart;
    @FXML private int jan, feb, mar, apr, mei, jun, jul, aug, sep, okt, nov, dec = 0;
    @FXML private Connection conn = null;
    @FXML private Statement stmt = null;
    @FXML private FYS fys = new FYS();
    @FXML private taal language = new taal();
    @FXML private String[] taal = language.getLanguage();
    @FXML private XYChart.Series series = new XYChart.Series<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //LINECHART
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateTimeString = dateFormat.format(date);
        String[] tokens = dateTimeString.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException();
        }
        tokens = tokens[0].split("-");
        String year = tokens[0];
        String months = tokens[1];
        linechart.setTitle(taal[76] + " " + fys.getMonthName(months) + " " + year);
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
            
            String sql = "SELECT date, YEAR(date) AS year, MONTH(date) AS month, COUNT(date) as Count "
                    + "FROM insurance_claim "
                    + "WHERE YEAR(date) LIKE \"%" + year + "%\" "
                    + "AND MONTH(date) LIKE \"%" + months + "%\";";
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
    
}
