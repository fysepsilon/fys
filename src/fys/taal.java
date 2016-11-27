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
            languagefields[5] = "Welkom";
            languagefields[6] = "Administrator";
            languagefields[7] = "Servicemedewerker";
            languagefields[8] = "Luchthaven";
            languagefields[9] = "Voornaam";
            languagefields[10] = "Achternaam";
            languagefields[11] = "Adres";
            languagefields[12] = "Woonplaats";
            languagefields[13] = "Postcode";
            languagefields[14] = "Land";
            languagefields[15] = "Telefoon";
            languagefields[16] = "E-mail";
            languagefields[17] = "Labelnummer";
            languagefields[18] = "Vluchtnummer";
            languagefields[19] = "Bestemming";
            languagefields[20] = "Type";
            languagefields[21] = "Merk";
            languagefields[22] = "Kleur";
            languagefields[23] = "Kenmerken";
            languagefields[24] = "Afbeelding";
            languagefields[25] = "Kies hier uw luchthaven";
            languagefields[26] = "Kies hier uw type bagage";
            languagefields[27] = "Koffer";
            languagefields[28] = "Sporttas";
            languagefields[29] = "Handtas";
            languagefields[30] = "Rugzak";
            languagefields[31] = "Kies hier de kleur van de bagage";
            languagefields[32] = "Bruin";
            languagefields[33] = "Beige";
            languagefields[34] = "Rood";
            languagefields[35] = "Oranje";
            languagefields[36] = "Geel";
            languagefields[37] = "Groen";
            languagefields[38] = "Blauw";
            languagefields[39] = "Paars";
            languagefields[40] = "Roze";
            languagefields[41] = "Zwart";
            languagefields[42] = "Grijs";
            languagefields[43] = "Wit";
            languagefields[44] = "Klik hier om een afbeelding toe te voegen";
            languagefields[45] = "Aanmaken account";
            languagefields[46] = "Verzenden";
            languagefields[47] = "Filteren";
            languagefields[48] = "Status";
            languagefields[49] = "Kleur";
            languagefields[50] = "Type";
            languagefields[51] = "Merk";
            languagefields[52] = "Datum";
            languagefields[53] = "Extra informatie";
            languagefields[54] = "Gevonden";
            languagefields[55] = "Vermist";
            languagefields[56] = "Vernietigd";
            languagefields[57] = "Afgehandeld";
            languagefields[58] = "Nooit gevonden";
            languagefields[59] = "Depot";
            languagefields[60] = "Acties";
            languagefields[61] = "Instellingen";
            languagefields[62] = "Uitloggen";
            languagefields[63] = "Nieuw account aanmaken";
            languagefields[64] = "Servicemedewerker";
            languagefields[65] = "Administrator";
            languagefields[66] = "Klant";
            languagefields[67] = "Wijzigen";
            languagefields[68] = "Verwijderen";
            languagefields[68] = "Taal";
            languagefields[69] = "Engels";
            languagefields[70] = "Nederlands";
            languagefields[71] = "Spaans";
            languagefields[72] = "Turks";
            languagefields[73] = "Kies hier de taal";
            languagefields[74] = "Kies hier de rol";
            languagefields[75] = "Status";
            languagefields[76] = "Schadeclaims";
            languagefields[77] = "Aantal schadeclaims";
            languagefields[78] = "jan";
            languagefields[79] = "feb";
            languagefields[80] = "mar";
            languagefields[81] = "apr";
            languagefields[82] = "mei";
            languagefields[83] = "jun";
            languagefields[84] = "jul";
            languagefields[85] = "aug";
            languagefields[86] = "sep";
            languagefields[87] = "okt";
            languagefields[88] = "nov";
            languagefields[89] = "dec";
            languagefields[90] = "Wachtwoord";
            languagefields[91] = "E-mailadres";
            languagefields[92] = "Opslaan";
        } else {
            languagefields[0] = "REGISTER MISSING BAGGAGE";
            languagefields[1] = "REGISTER FOUND BAGGAGE";
            languagefields[2] = "BAGGAGE DATABASE";
            languagefields[3] = "ACCOUNTS";
            languagefields[4] = "STATISTICS";
            languagefields[5] = "Welcome";
            languagefields[6] = "Administrator";
            languagefields[7] = "Service employ";
            languagefields[8] = "Airport";
            languagefields[9] = "Name";
            languagefields[10] = "Surname";
            languagefields[11] = "Address";
            languagefields[12] = "Residence";
            languagefields[13] = "Zipcode";
            languagefields[14] = "Country";
            languagefields[15] = "Telephone";
            languagefields[16] = "E-mail";
            languagefields[17] = "Tagnumber";
            languagefields[18] = "Flightnumber";
            languagefields[19] = "Destination";
            languagefields[20] = "Type";
            languagefields[21] = "Brand";
            languagefields[22] = "Color";
            languagefields[23] = "Characterize";
            languagefields[24] = "Picture";
            languagefields[25] = "Select airport";
            languagefields[26] = "Select the type of baggage";
            languagefields[27] = "Suitcase";
            languagefields[28] = "Sportsbag";
            languagefields[29] = "Handbag";
            languagefields[30] = "Backpack";
            languagefields[31] = "Select the color of the baggage";
            languagefields[32] = "Brown";
            languagefields[33] = "Beige";
            languagefields[34] = "Red";
            languagefields[35] = "Orange";
            languagefields[36] = "Yellow";
            languagefields[37] = "Green";
            languagefields[38] = "Blue";
            languagefields[39] = "Purple";
            languagefields[40] = "Pink";
            languagefields[41] = "Black";
            languagefields[42] = "Grey";
            languagefields[43] = "White";
            languagefields[44] = "Click here to add a picture";
            languagefields[45] = "Create account";
            languagefields[46] = "Send";
            languagefields[47] = "Filter";
            languagefields[48] = "Status";
            languagefields[49] = "Color";
            languagefields[50] = "Type";
            languagefields[51] = "Brand";
            languagefields[52] = "Date";
            languagefields[53] = "Extra information";
            languagefields[54] = "Found";
            languagefields[55] = "Lost";
            languagefields[56] = "Destroyed";
            languagefields[57] = "Completed";
            languagefields[58] = "Never found";
            languagefields[59] = "Depot";
            languagefields[60] = "Actions";
            languagefields[61] = "Settings";
            languagefields[62] = "Log out";
            languagefields[63] = "Make a new account";
            languagefields[64] = "Service-employee";
            languagefields[65] = "Administrator";
            languagefields[66] = "Customer";
            languagefields[67] = "Edit";
            languagefields[68] = "Delete";
            languagefields[68] = "Language";
            languagefields[69] = "English";
            languagefields[70] = "Dutch";
            languagefields[71] = "Spanish";
            languagefields[72] = "Turkish";
            languagefields[73] = "Choose the language";
            languagefields[74] = "Choose the role";
            languagefields[75] = "Status";
            languagefields[76] = "Insurance claims";
            languagefields[77] = "Amount of Insurance claims";
            languagefields[78] = "Jan";
            languagefields[79] = "Feb";
            languagefields[80] = "Mar";
            languagefields[81] = "Apr";
            languagefields[82] = "May";
            languagefields[83] = "Jun";
            languagefields[84] = "Jul";
            languagefields[85] = "Aug";
            languagefields[86] = "Sep";
            languagefields[87] = "Oct";
            languagefields[88] = "Nov";
            languagefields[89] = "Dec";
            languagefields[90] = "Password";
            languagefields[91] = "E-mailaddress";
            languagefields[92] = "Save";
        }
        return languagefields;
    }
}
