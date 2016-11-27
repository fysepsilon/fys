/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Paras
 */
public class taal {
    private final int amountOfLanguageFields = 100;
    private int language = 0;
    
    public String[] getLanguage(){
        String[] languagefields = new String[amountOfLanguageFields];
        
        FYS fys = new FYS();
        loginController login = new loginController();
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT language FROM person_table WHERE type = '" + login.getUsertype() + "' AND mail='" + login.getEmail() + "'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //Retrieve by column name
                    language = rs.getInt("language");
                }
            }
            conn.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        if(language == 1){
            languagefields[0] = "VERMISTE BAGAGE REGISTREREN";
            languagefields[1] = "GEVONDEN BAGAGE REGISTREREN";
            languagefields[2] = "BAGAGE DATABASE";
            languagefields[3] = "ACCOUNTS";
            languagefields[4] = "STATISTIEKEN";
        } else {
            languagefields[0] = "REGISTER MISSING BAGGAGE";
            languagefields[1] = "REGISTER FOUND BAGGAGE";
            languagefields[2] = "BAGGAGE DATABASE";
            languagefields[3] = "ACCOUNTS";
            languagefields[4] = "STATISTICS";
        }
        return languagefields;
    }
}
