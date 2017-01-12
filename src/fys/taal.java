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
 * @author Team Epsilon
 */
public class taal {

    private final int amountOfLanguageFields = 200;
    private int language = 0;

    public void setLanguage(int taal) {
        this.language = taal;
    }

    public String[] getLanguage() {
        String[] languagefields = new String[amountOfLanguageFields];
        //haal uit de database welke taal is geselecteerd.
        FYS fys = new FYS();
        loginController login = new loginController();
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = fys.connectToDatabase(conn);
            stmt = conn.createStatement();
            String sql = "SELECT language FROM person "
                    + "WHERE type = '" + login.getUsertype() + "' "
                    + "AND mail='" + login.getEmail() + "'";
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

        //Return dan de woorden in array in de taal die is geselecteerd. 
        switch (language) {
            case 0:
                // Engels
                languagefields[0] = "REGISTER MISSING LUGGAGE";
                languagefields[1] = "REGISTER FOUND LUGGAGE";
                languagefields[2] = "LUGGAGE DATABASE";
                languagefields[3] = "ACCOUNTS";
                languagefields[4] = "STATISTICS";
                languagefields[5] = "Welcome";
                languagefields[6] = "Administrator";
                languagefields[7] = "Service employee";
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
                languagefields[26] = "Select the type of luggage";
                languagefields[27] = "Suitcase";
                languagefields[28] = "Sportsbag";
                languagefields[29] = "Handbag";
                languagefields[30] = "Backpack";
                languagefields[31] = "Select the color of the luggage";
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
                languagefields[49] = "Color"; //DUBBEL
                languagefields[50] = "Type"; //DUBBEL
                languagefields[51] = "Brand"; //DUBBEL
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
                languagefields[63] = "Create a new account";
                languagefields[64] = "Service-employee";
                languagefields[65] = "Administrator"; //DUBBEL
                languagefields[66] = "Customer";
                languagefields[67] = "Edit";
                languagefields[68] = "Language";
                languagefields[69] = "English";
                languagefields[70] = "Dutch";
                languagefields[71] = "Spanish";
                languagefields[72] = "Turkish";
                languagefields[73] = "Choose the language";
                languagefields[74] = "Choose the role";
                languagefields[75] = "Status"; //DUBBEL
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
                languagefields[93] = "Some required fields are left blank!";
                languagefields[94] = "E-mail already exists!";
                languagefields[95] = "Register missing luggage"; //DUBBEL?
                languagefields[96] = "Register found luggage"; //DUBBEL?
                languagefields[97] = "Home"; //DUBBEL?
                languagefields[98] = "Accounts"; //DUBBEL?
                languagefields[99] = "Statistics"; //DUBBEL?
                languagefields[100] = "Luggage database"; //DUBBEL?
                languagefields[101] = "Login"; //DUBBEL?
                languagefields[102] = "Settings"; //DUBBEL
                languagefields[103] = "The account is created and an e-mail has "
                        + "been sent!";
                languagefields[104] = "Change data";
                languagefields[105] = "First, select a row in the table to change "
                        + "something";
                languagefields[106] = "Latest luggage";
                languagefields[107] = "Time";
                languagefields[108] = "Insurance claim";
                languagefields[109] = "January";
                languagefields[110] = "February";
                languagefields[111] = "March";
                languagefields[112] = "April";
                languagefields[113] = "May";
                languagefields[114] = "June";
                languagefields[115] = "July";
                languagefields[116] = "August";
                languagefields[117] = "September";
                languagefields[118] = "October";
                languagefields[119] = "November";
                languagefields[120] = "December";
                languagefields[121] = "This e-mail address already exists.";
                languagefields[122] = "Changing";
                languagefields[123] = "Your data has been changed.";
                languagefields[124] = "The data has been added.";
                languagefields[125] = "Shoulder bag";
                languagefields[126] = "Change an account";
                languagefields[127] = "Cancel";
                languagefields[128] = "Remove Account";
                languagefields[129] = "Are you sure you want the account named '";
                languagefields[130] = "' to be deleted?";
                languagefields[131] = "month";
                languagefields[132] = "Amount";
                languagefields[133] = "Deleting data";
                languagefields[134] = "First, select a row in the table to remove "
                        + "it";
                languagefields[135] = "Subject";
                languagefields[136] = "Message";
                languagefields[137] = "EXTRA";
                languagefields[138] = "Export to PDF";
                languagefields[139] = "Check availability";
                languagefields[140] = "Export";
                languagefields[141] = "Date from";
                languagefields[142] = "Date to";
                languagefields[143] = "Week";
                languagefields[144] = "Page";
                languagefields[145] = "Extra"; //DUBBEL?
                languagefields[146] = "Confirm";
                languagefields[147] = "Are you sure you want to reset this email "
                        + "to its normal message?";
                languagefields[148] = "Username";
                languagefields[149] = "Information";
                languagefields[150] = "Warning";
                languagefields[151] = "Use the characters &quot; and ' \n not "
                        + "in an email.";
                languagefields[152] = "You can use certain \nwords in an email "
                        + "that \nmean something. Below are \nthe words that "
                        + "you \ncan use with the \nmeaning behind it.";
                languagefields[153] = "Recover";
                languagefields[154] = "Delete data";
                languagefields[155] = "You are about to delete this luggage and"
                        + " customer data"
                        + " permanently! Are you sure?";
                languagefields[156] = "Delete";
                languagefields[157] = "The data has successfully been removed!";
                languagefields[158] = "Renew the page to update the database";
                languagefields[159] = "E-mailaddress is not correct";
                languagefields[160] = "PDF opens";
                languagefields[161] = "Has just opened a PDF file with the user.";
                languagefields[162] = "User's guide";
                languagefields[163] = "Unknown";
                languagefields[164] = "Select the customers language";
                languagefields[165] = "German";
                languagefields[166] = "Forgot password";
                languagefields[167] = "You can filter the e-mails through here.";
                languagefields[168] = "There has been a code used that not belongs "
                        + "in this email. Click the information icon to see which "
                        + "codes can be used in it.";
                languagefields[169] = "You have the character ' used.";
                languagefields[170] = "You have the character ´ used.";
                languagefields[171] = "Month";
                languagefields[172] = "Year";
                languagefields[173] = "You can filter your statistics through here.";
                languagefields[174] = "Mandatory fields.";
                languagefields[175] = "Goud";
                languagefields[175] = "Gold";
                break;
            case 1:
                // Nederlands
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
                languagefields[28] = "Weekendtas";
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
                languagefields[44] = "Voeg een afbeelding toe";
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
                languagefields[93] = "Er zijn benodigde velden leeg gelaten!";
                languagefields[94] = "E-mailadres bestaat al!";
                languagefields[95] = "Vermiste bagage registreren";
                languagefields[96] = "Gevonden bagage registreren";
                languagefields[97] = "Home";
                languagefields[98] = "Accounts";
                languagefields[99] = "Statistieken";
                languagefields[100] = "Bagage database";
                languagefields[101] = "Login";
                languagefields[102] = "Instellingen";
                languagefields[103] = "Het account is gemaakt en een e-mail is "
                        + "verstuurd!";
                languagefields[104] = "Wijzigen van gegevens";
                languagefields[105] = "Selecteer eerst een rij in de tabel om deze "
                        + "te wijzigen";
                languagefields[106] = "Meest recente bagage";
                languagefields[107] = "Tijd";
                languagefields[108] = "Schadeclaim";
                languagefields[109] = "januari";
                languagefields[110] = "februari";
                languagefields[111] = "maart";
                languagefields[112] = "april";
                languagefields[113] = "mei";
                languagefields[114] = "juni";
                languagefields[115] = "july";
                languagefields[116] = "augustus";
                languagefields[117] = "september";
                languagefields[118] = "oktober";
                languagefields[119] = "november";
                languagefields[120] = "december";
                languagefields[121] = "Dit email adres bestaat al.";
                languagefields[122] = "Aanpassingen";
                languagefields[123] = "Uw gegevens zijn gewijzigd.";
                languagefields[124] = "De gegevens zijn toegevoegd.";
                languagefields[125] = "Schoudertas";
                languagefields[126] = "Account wijzigen";
                languagefields[127] = "Annuleren";
                languagefields[128] = "Account verwijderen";
                languagefields[129] = "Weet je zeker dat je het account met de "
                        + "naam '";
                languagefields[130] = "' wilt verwijderen?";
                languagefields[131] = "maand";
                languagefields[132] = "Aantal";
                languagefields[133] = "Verwijderen van gegevens";
                languagefields[134] = "Selecteer eerst een rij in de tabel om deze "
                        + "te verwijderen";
                languagefields[135] = "Onderwerp";
                languagefields[136] = "Bericht";
                languagefields[137] = "EXTRA";
                languagefields[138] = "Exporteren naar PDF";
                languagefields[139] = "Selecteer periode";
                languagefields[140] = "Exporteer";
                languagefields[141] = "Datum vanaf";
                languagefields[142] = "Datum tot";
                languagefields[143] = "Week";
                languagefields[144] = "Pagina";
                languagefields[145] = "Extra";
                languagefields[146] = "Bevestigen";
                languagefields[147] = "Weet je zeker dat je deze email wilt "
                        + "herstellen naar zijn normale bericht?";
                languagefields[148] = "Gebruikersnaam";
                languagefields[149] = "Informatie";
                languagefields[150] = "Waarschuwing";
                languagefields[151] = "Gebruik de tekens &quot; en ' \nniet "
                        + "in een email.";
                languagefields[152] = "Je kan bepaalde woorden\ngebruiken in "
                        + "een email die \niets betekenen. Hieronder \nstaan "
                        + "de woorden die je \nkan gebruiken met de\n"
                        + "betekenis erachter.";
                languagefields[153] = "Herstellen";
                languagefields[154] = "Verwijderen van gegevens";
                languagefields[155] = "U staat op het punt deze bagage- en "
                        + "klantgegevens permanent"
                        + " te verwijderen! Weet u het zeker?";
                languagefields[156] = "Verwijderen";
                languagefields[157] = "De data is succesvol verwijderd!";
                languagefields[158] = "Vernieuw de pagina om de database te "
                        + "updaten";
                languagefields[159] = "E-mailadres is niet geldig";
                languagefields[160] = "PDF geopend";
                languagefields[161] = "Er is zojuist een PDF bestand geopend met "
                        + "de gebruikershandleiding.";
                languagefields[162] = "Gebruikershandleiding";
                languagefields[163] = "Onbekend";
                languagefields[164] = "Kies hier de taal van de klant";
                languagefields[165] = "Duits";
                languagefields[166] = "Wachtwoord vergeten";
                languagefields[167] = "Je kan via hier de emails filteren.";
                languagefields[168] = "Er is een code gebruikt die niet in deze mail thuis hoort. Klik op het informatie icoon om te kijken welke codes er allemaal gebruikt kunnen worden.";
                languagefields[169] = "Je hebt het teken ' gebruikt.";
                languagefields[170] = "Je hebt het teken ´ gebruikt.";
                languagefields[171] = "Maand";
                languagefields[172] = "Jaar";
                languagefields[173] = "Je kan via hier de statistieken filteren.";
                languagefields[174] = "Verplichte velden.";
                languagefields[175] = "Goud";
                languagefields[176] = "Thema";
                break;
            case 2:
                // Spaans
                languagefields[0] = "FALTA EQUIPAJE REGISTRO";
                languagefields[1] = "ENCONTRADO EQUIPAJE REGISTRO";
                languagefields[2] = "BASE DE DATOS DE EQUIPAJE";
                languagefields[3] = "CUENTAS";
                languagefields[4] = "ESTADÍSTICA";
                languagefields[5] = "Bienvenida";
                languagefields[6] = "Administrador";
                languagefields[7] = "Auxiliar de servicios";
                languagefields[8] = "Aeropuerto";
                languagefields[9] = "Nombres";
                languagefields[10] = "Apellido";
                languagefields[11] = "Dirección";
                languagefields[12] = "Residencia";
                languagefields[13] = "Código postal";
                languagefields[14] = "País";
                languagefields[15] = "Teléfono";
                languagefields[16] = "Correo electrónico";
                languagefields[17] = "Número de etiqueta";
                languagefields[18] = "Número de vuelo";
                languagefields[19] = "Destino";
                languagefields[20] = "Tipo";
                languagefields[21] = "Marca";
                languagefields[22] = "Color";
                languagefields[23] = "Característica";
                languagefields[24] = "Imagen";
                languagefields[25] = "Por favor seleccione su aeropuerto";
                languagefields[26] = "Por favor seleccione su tipo de equipaje";
                languagefields[27] = "Maleta";
                languagefields[28] = "Bolsa de deporte";
                languagefields[29] = "Bolso";
                languagefields[30] = "Mochila";
                languagefields[31] = "Elegir el color del equipaje";
                languagefields[32] = "Marrón";
                languagefields[33] = "Beige";
                languagefields[34] = "Rojo";
                languagefields[35] = "Naranja";
                languagefields[36] = "Amarillo";
                languagefields[37] = "Verde";
                languagefields[38] = "Azul";
                languagefields[39] = "Violeta";
                languagefields[40] = "Rosa";
                languagefields[41] = "Negro";
                languagefields[42] = "Gris";
                languagefields[43] = "Blanco";
                languagefields[44] = "Haga clic aquí para añadir una imagen";
                languagefields[45] = "Crear una cuenta";
                languagefields[46] = "Barco";
                languagefields[47] = "Filtro";
                languagefields[48] = "Estatus";
                languagefields[49] = "Color";
                languagefields[50] = "Tipo";
                languagefields[51] = "Marca";
                languagefields[52] = "Fecha";
                languagefields[53] = "Característica";
                languagefields[54] = "Fundar";
                languagefields[55] = "Que falta";
                languagefields[56] = "Destruido";
                languagefields[57] = "Tratado";
                languagefields[58] = "Nunca encontrado";
                languagefields[59] = "Almacén";
                languagefields[60] = "Acciones";
                languagefields[61] = "Ajustes";
                languagefields[62] = "Cerrar sesión";
                languagefields[63] = "Crear nueva cuenta";
                languagefields[64] = "Auxiliar de servicios";
                languagefields[65] = "Administrador";
                languagefields[66] = "Cliente";
                languagefields[67] = "Modificar";
                languagefields[68] = "Eliminar";
                languagefields[68] = "Idioma";
                languagefields[69] = "Inglés";
                languagefields[70] = "Holandés";
                languagefields[71] = "Español";
                languagefields[72] = "Turco";
                languagefields[73] = "Seleccionar el idioma";
                languagefields[74] = "Elige el papel";
                languagefields[75] = "Estatus";
                languagefields[76] = "Reclamaciones de seguros";
                languagefields[77] = "Número de reclamaciones";
                languagefields[78] = "ene";
                languagefields[79] = "feb";
                languagefields[80] = "mar";
                languagefields[81] = "abr";
                languagefields[82] = "may";
                languagefields[83] = "jun";
                languagefields[84] = "jul";
                languagefields[85] = "ago";
                languagefields[86] = "sep";
                languagefields[87] = "oct";
                languagefields[88] = "nov";
                languagefields[89] = "dic";
                languagefields[90] = "Contraseña";
                languagefields[91] = "Correo electrónico";
                languagefields[92] = "Tienda";
                languagefields[93] = "Hay campos se dejan en blanco!";
                languagefields[94] = "Correo electrónico ya existe!";
                languagefields[95] = "Registro equipaje perdido";
                languagefields[96] = "Registro encontró equipaje";
                languagefields[97] = "Casa";
                languagefields[98] = "Cuentas";
                languagefields[99] = "Estadística";
                languagefields[100] = "Base de datos de equipaje";
                languagefields[101] = "Login";
                languagefields[102] = "Ajustes";
                languagefields[103] = "La cuenta se crea y se envía el e-mail!";
                languagefields[104] = "Cambio de datos";
                languagefields[105] = "En primer lugar, seleccione una fila de la "
                        + "tabla para cambiar esta";
                languagefields[106] = "Última equipaje";
                languagefields[107] = "Tiempo";
                languagefields[108] = "Reclamación de seguro";
                languagefields[109] = "enero";
                languagefields[110] = "febrero";
                languagefields[111] = "marzo";
                languagefields[112] = "abril";
                languagefields[113] = "mayo";
                languagefields[114] = "junio";
                languagefields[115] = "julio";
                languagefields[116] = "agosto";
                languagefields[117] = "septiembre";
                languagefields[118] = "octubre";
                languagefields[119] = "noviembre";
                languagefields[120] = "diciembre";
                languagefields[121] = "Esta dirección de correo electrónico ya "
                        + "existe.";
                languagefields[122] = "Ajustes";
                languagefields[123] = "Se cambia sus datos.";
                languagefields[124] = "Los datos se han añadido.";
                languagefields[125] = "Bolso de bandolera";
                languagefields[126] = "Cuenta de modificación";
                languagefields[127] = "Cancelar";
                languagefields[128] = "Eliminar cuenta";
                languagefields[129] = "Seguro que cuenta llamada '";
                languagefields[130] = "' para quitar?";
                languagefields[131] = "mes";
                languagefields[132] = "Número";
                languagefields[133] = "Eliminación de datos";
                languagefields[134] = "En primer lugar, seleccione una fila en la "
                        + "tabla para eliminarlo";
                languagefields[135] = "Sujeto";
                languagefields[136] = "Mensaje";
                languagefields[137] = "EXTRA";
                languagefields[138] = "Exportar a PDF";
                languagefields[139] = "Comprobar la disponibilidad";
                languagefields[140] = "Exportación";
                languagefields[141] = "Fecha a partir de";
                languagefields[142] = "Fecha de";
                languagefields[143] = "Semana";
                languagefields[144] = "Página";
                languagefields[145] = "Extra";
                languagefields[146] = "Confirmar";
                languagefields[147] = "¿Está seguro de que desea restablecer este "
                        + "mensaje a su mensaje normal?";
                languagefields[148] = "Nombre de usuario";
                languagefields[149] = "Información";
                languagefields[150] = "Advertencia";
                languagefields[151] = "Utilice los caracteres &quot; y ' no en un "
                        + "correo electrónico.";
                languagefields[152] = "Puede usar ciertas palabras \nen un "
                        + "correo electrónico que \nquiere decir algo. "
                        + "A \ncontinuación se presentan \nlas palabras que "
                        + "se pueden \nutilizar con el significado \ndetrás "
                        + "de él.";
                languagefields[153] = "Recuperar";
                languagefields[154] = "Eliminación de datos";
                languagefields[155] = "Estás a punto de eliminar esta equipaje "
                        + "y datos"
                        + " de forma permanente al cliente! ¿Seguro?";
                languagefields[156] = "Eliminar";
                languagefields[157] = "Los datos se han eliminado correctamente!";
                languagefields[158] = "Actualizar la página para actualizar la "
                        + "base de datos";
                languagefields[159] = "El correo electrónico no es válida";
                languagefields[160] = "PDF abre";
                languagefields[161] = "Acaba de abrir un archivo PDF con el "
                        + "usuario.";
                languagefields[162] = "Guía del usuario";
                languagefields[163] = "Desconocido";
                languagefields[164] = "Seleccionar el idioma del cliente";
                languagefields[165] = "Alemán";
                languagefields[166] = "Contraseña";
                languagefields[167] = "Puede filtrar los correos electrónicos a "
                        + "través de aquí.";
                languagefields[168] = "Se ha utilizado un código que no pertenece "
                        + "a este correo electrónico. Haga clic en el icono de "
                        + "información para ver qué códigos se pueden utilizar "
                        + "en él.";
                languagefields[169] = "Tienes el carácter ' usado.";
                languagefields[170] = "Tienes el carácter ´ usado.";
                languagefields[171] = "Mes";
                languagefields[172] = "Año";
                languagefields[173] = "Puedes realizar un filtrado sus estadísticas por aquí.";
                languagefields[174] = "Campos obligatorios.";
                languagefields[175] = "Oro";
                break;
            case 3:
                // Turks
                languagefields[0] = "EKSİK BAGAJ KAYIT";
                languagefields[1] = "BULUNAMADI BAGAJ KAYIT";
                languagefields[2] = "BAGAJ VERİTABANI";
                languagefields[3] = "HESAPLAR";
                languagefields[4] = "İSTATİSTİK";
                languagefields[5] = "Karşılama";
                languagefields[6] = "Yönetici";
                languagefields[7] = "Servis görevlisi";
                languagefields[8] = "Havaalanı";
                languagefields[9] = "Ad";
                languagefields[10] = "Soyadı";
                languagefields[11] = "Adres";
                languagefields[12] = "Konut";
                languagefields[13] = "Posta kodu";
                languagefields[14] = "Ülke";
                languagefields[15] = "Telefon";
                languagefields[16] = "Posta";
                languagefields[17] = "Etiket numarası";
                languagefields[18] = "Uçuş numarası";
                languagefields[19] = "Hedef";
                languagefields[20] = "Tip";
                languagefields[21] = "Işaret";
                languagefields[22] = "Renk";
                languagefields[23] = "Özellik";
                languagefields[24] = "Resim";
                languagefields[25] = "Senin havaalanı seçiniz";
                languagefields[26] = "Bagaj türünü seçiniz";
                languagefields[27] = "Bavul";
                languagefields[28] = "Spor çanta";
                languagefields[29] = "Çanta";
                languagefields[30] = "Sırt çantası";
                languagefields[31] = "Bagaj rengini seçin";
                languagefields[32] = "Kahverengi";
                languagefields[33] = "Bej";
                languagefields[34] = "Kırmızı";
                languagefields[35] = "Turuncu";
                languagefields[36] = "Sarı";
                languagefields[37] = "Yeşil";
                languagefields[38] = "Mavi";
                languagefields[39] = "Menekşe";
                languagefields[40] = "Pembe";
                languagefields[41] = "Siyah";
                languagefields[42] = "Gri";
                languagefields[43] = "Beyaz";
                languagefields[44] = "Bir resim eklemek için buraya tıklayın";
                languagefields[45] = "Hesap oluştur";
                languagefields[46] = "Gemi";
                languagefields[47] = "Filtre";
                languagefields[48] = "Durum";
                languagefields[49] = "Renk";
                languagefields[50] = "Tip";
                languagefields[51] = "Işaret";
                languagefields[52] = "Tarih";
                languagefields[53] = "Özellik";
                languagefields[54] = "Bulundu";
                languagefields[55] = "Eksik";
                languagefields[56] = "Tahrip";
                languagefields[57] = "Tamamlanan";
                languagefields[58] = "Bulmadım";
                languagefields[59] = "Depo";
                languagefields[60] = "Eylemler";
                languagefields[61] = "Ayarlar";
                languagefields[62] = "Oturumu";
                languagefields[63] = "Yeni hesap oluştur";
                languagefields[64] = "Servis görevlisi";
                languagefields[65] = "Yönetici";
                languagefields[66] = "Müşteri";
                languagefields[67] = "Değiştirmek";
                languagefields[68] = "Kaldırmak";
                languagefields[68] = "Dil";
                languagefields[69] = "İngilizce";
                languagefields[70] = "Hollandalı";
                languagefields[71] = "İspanyolca";
                languagefields[72] = "Türk";
                languagefields[73] = "Dil seçin";
                languagefields[74] = "Rolünü seçin";
                languagefields[75] = "Durum";
                languagefields[76] = "Sigorta talepleri";
                languagefields[77] = "Iddiaların sayısı";
                languagefields[78] = "oca";
                languagefields[79] = "şub";
                languagefields[80] = "mar";
                languagefields[81] = "nis";
                languagefields[82] = "may";
                languagefields[83] = "haz";
                languagefields[84] = "tem";
                languagefields[85] = "ağu";
                languagefields[86] = "eyl";
                languagefields[87] = "eki";
                languagefields[88] = "kas";
                languagefields[89] = "ara";
                languagefields[90] = "Şifre";
                languagefields[91] = "Posta";
                languagefields[92] = "Mağaza";
                languagefields[93] = "Boş bırakılan alanlar vardır!";
                languagefields[94] = "Posta zaten var!";
                languagefields[95] = "Kayıt kayıp bagaj";
                languagefields[96] = "Kayıt bagaj bulundu";
                languagefields[97] = "Ev";
                languagefields[98] = "Hesapları";
                languagefields[99] = "Istatistik";
                languagefields[100] = "Bagaj veritabanı";
                languagefields[101] = "Giriş";
                languagefields[102] = "Ayarlar";
                languagefields[103] = "Hesabı oluşturulur ve e-posta gönderilir!";
                languagefields[104] = "Değişim verileri";
                languagefields[105] = "İlk olarak, bu değiştirmek için tablodaki "
                        + "bir satır seçin";
                languagefields[106] = "Son bagaj";
                languagefields[107] = "Zaman";
                languagefields[108] = "Sigorta tazminat talebi";
                languagefields[109] = "Ocak";
                languagefields[110] = "Şubat";
                languagefields[111] = "Mart";
                languagefields[112] = "Nisan";
                languagefields[113] = "Mayıs";
                languagefields[114] = "Haziran";
                languagefields[115] = "Temmuz";
                languagefields[116] = "Ağustos";
                languagefields[117] = "Eylül";
                languagefields[118] = "Ekim";
                languagefields[119] = "Kasım";
                languagefields[120] = "Aralık";
                languagefields[121] = "Bu e-posta adresi zaten var.";
                languagefields[122] = "Ayarlamalar";
                languagefields[123] = "Verileriniz değiştirilir.";
                languagefields[124] = "Veri eklendi.";
                languagefields[125] = "Omuz çantası";
                languagefields[126] = "Değişim hesabı";
                languagefields[127] = "Iptal";
                languagefields[128] = "Hesabı silmek";
                languagefields[129] = "Eğer adında hesabı emin misin '";
                languagefields[130] = "' kaldırmak için?";
                languagefields[131] = "ay";
                languagefields[132] = "Sayı";
                languagefields[133] = "Silme veri";
                languagefields[134] = "İlk olarak, onu çıkarmak için tablodaki "
                        + "bir satır seçin";
                languagefields[135] = "Konu";
                languagefields[136] = "Mesaj";
                languagefields[137] = "EKSTRA";
                languagefields[138] = "PDF ihracat";
                languagefields[139] = "Kontrol et";
                languagefields[140] = "Ihracat";
                languagefields[141] = "Geçmişe dayanmak";
                languagefields[142] = "Tarih";
                languagefields[143] = "Hafta";
                languagefields[144] = "Sayfa";
                languagefields[145] = "Ekstra";
                languagefields[146] = "Onaylamak";
                languagefields[147] = "Eğer normal iletiye Bu e-postayı sıfırlamak "
                        + "istediğinizden emin misiniz?";
                languagefields[148] = "Kullanıcı adı";
                languagefields[149] = "Bilgi";
                languagefields[150] = "Uyarı";
                languagefields[151] = "Bir e-posta karakterleri &quot; ve ' "
                        + "değil kullanın.";
                languagefields[152] = "Bir şey demek bir \ne-posta belirli "
                        + "kelimeleri \nkullanabilirsiniz. Aşağıda \n"
                        + "arkasında anlamı ile \nkullanabileceğiniz "
                        + "kelimelerdir.";
                languagefields[153] = "Kurtarmak";
                languagefields[154] = "Silme veri";
                languagefields[155] = "Bunu sürekli olarak bu bagaj ve müşteri "
                        + "verilerini "
                        + " kaldırmak üzeresiniz! Emin misiniz?";
                languagefields[156] = "Kaldırmak";
                languagefields[157] = "Verileri başarıyla silindi!";
                languagefields[158] = "Veritabanını güncellemek için sayfayı "
                        + "yenileyin";
                languagefields[159] = "E-posta geçerli değil";
                languagefields[160] = "PDF açılır";
                languagefields[161] = "Sadece kullanıcı ile bir PDF dosyası açtı.";
                languagefields[162] = "Kullanıcı kılavuzu";
                languagefields[163] = "Bilinmeyen";
                languagefields[164] = "Müşterinin dili seçin";
                languagefields[165] = "Alman";
                languagefields[166] = "Şifre";
                languagefields[167] = "Burada üzerinden e-postalar filtre "
                        + "edebilirsiniz.";
                languagefields[168] = "Bu e-postaya ait olmayan bir kod "
                        + "kullanıldı. İçinde hangi kodların kullanılabileceğini "
                        + "görmek için bilgi simgesini tıklayın.";
                languagefields[169] = "Kullandığınız karakter ' var.";
                languagefields[170] = "Kullandığınız karakter ´ var.";
                languagefields[171] = "Ay";
                languagefields[172] = "Yıl";
                languagefields[173] = "Burada aracılığıyla istatistiklerini filtre edebilirsiniz.";
                languagefields[174] = "Zorunlu alanlar.";
                languagefields[175] = "Altın";
                break;
            default:
                // Duits
                languagefields[0] = "FEHLENDES GEPÄCK REGISTRIEREN";
                languagefields[1] = "GEFUNDEN GEPÄCK ANMELDEN";
                languagefields[2] = "GEPÄCK DATABASE";
                languagefields[3] = "KONTEN";
                languagefields[4] = "STATISTIK";
                languagefields[5] = "Willkommen";
                languagefields[6] = "Administrator";
                languagefields[7] = "Service-Assistent";
                languagefields[8] = "Flughafen";
                languagefields[9] = "Vorname";
                languagefields[10] = "Nachname";
                languagefields[11] = "Adresse";
                languagefields[12] = "Residenz";
                languagefields[13] = "Postleitzahl";
                languagefields[14] = "Land";
                languagefields[15] = "Telefon";
                languagefields[16] = "E-Mail";
                languagefields[17] = "Label-nummer";
                languagefields[18] = "Flugnummer";
                languagefields[19] = "Ziel";
                languagefields[20] = "Typ";
                languagefields[21] = "Markierung";
                languagefields[22] = "Farbe";
                languagefields[23] = "Feature";
                languagefields[24] = "Bild";
                languagefields[25] = "Bitte wählen Sie Ihre Flughafen";
                languagefields[26] = "Bitte wählen Sie Ihre Art von Gepäck";
                languagefields[27] = "Koffer";
                languagefields[28] = "Wochenendtasche";
                languagefields[29] = "Handtasche";
                languagefields[30] = "Rucksack";
                languagefields[31] = "Wählen Sie die Farbe des Gepäck";
                languagefields[32] = "Braun";
                languagefields[33] = "Beige";
                languagefields[34] = "Rot";
                languagefields[35] = "Orange";
                languagefields[36] = "Gelb";
                languagefields[37] = "Grün";
                languagefields[38] = "Blau";
                languagefields[39] = "Violett";
                languagefields[40] = "Rosa";
                languagefields[41] = "Schwarz";
                languagefields[42] = "Grau";
                languagefields[43] = "Weiß";
                languagefields[44] = "Klicken Sie hier, um ein Bild hinzuzufügen";
                languagefields[45] = "Konto erstellen";
                languagefields[46] = "Schiff";
                languagefields[47] = "Filter";
                languagefields[48] = "Status";
                languagefields[49] = "Farbe";
                languagefields[50] = "Typ";
                languagefields[51] = "Markierung";
                languagefields[52] = "Datum";
                languagefields[53] = "Weitere Informationen";
                languagefields[54] = "Gefunden";
                languagefields[55] = "Fehlt";
                languagefields[56] = "Zerstört";
                languagefields[57] = "Fertiggestellt";
                languagefields[58] = "Nie gefunden";
                languagefields[59] = "Depot";
                languagefields[60] = "Aktionen";
                languagefields[61] = "Einstellungen";
                languagefields[62] = "Austragen";
                languagefields[63] = "Neues Konto erstellen";
                languagefields[64] = "Service-Assistent";
                languagefields[65] = "Administrator";
                languagefields[66] = "Kunde";
                languagefields[67] = "Ändern";
                languagefields[68] = "Entfernen";
                languagefields[68] = "Sprache";
                languagefields[69] = "Englisch";
                languagefields[70] = "Holländisch";
                languagefields[71] = "Spanisch";
                languagefields[72] = "Türkisch";
                languagefields[73] = "Wählen Sie die Sprache";
                languagefields[74] = "Wählen Sie die Rolle";
                languagefields[75] = "Status";
                languagefields[76] = "Versicherungsansprüche";
                languagefields[77] = "Anzahl der Ansprüche";
                languagefields[78] = "jan";
                languagefields[79] = "feb";
                languagefields[80] = "mär";
                languagefields[81] = "apr";
                languagefields[82] = "mai";
                languagefields[83] = "jun";
                languagefields[84] = "jul";
                languagefields[85] = "aug";
                languagefields[86] = "sep";
                languagefields[87] = "okt";
                languagefields[88] = "nov";
                languagefields[89] = "dez";
                languagefields[90] = "Kennwort";
                languagefields[91] = "E-Mail";
                languagefields[92] = "Speicher";
                languagefields[93] = "Es sind Pflichtfelder leer gelassen!";
                languagefields[94] = "E-Mail ist bereits vorhanden!";
                languagefields[95] = "Registrieren fehlende Gepäck";
                languagefields[96] = "Register Gepäck";
                languagefields[97] = "Zuhause";
                languagefields[98] = "Konten";
                languagefields[99] = "Statistik";
                languagefields[100] = "Gepäck-Datenbank";
                languagefields[101] = "Login";
                languagefields[102] = "Einstellungen";
                languagefields[103] = "Das Konto wird erstellt und versendet eine "
                        + "E-Mail!";
                languagefields[104] = "Daten ändern";
                languagefields[105] = "Wählen Sie zunächst eine Zeile in der "
                        + "Tabelle, dies zu ändern";
                languagefields[106] = "Neueste Gepäck";
                languagefields[107] = "Zeit";
                languagefields[108] = "Versicherung Anspruch";
                languagefields[109] = "Januar";
                languagefields[110] = "Februar";
                languagefields[111] = "März";
                languagefields[112] = "April";
                languagefields[113] = "Mai";
                languagefields[114] = "Juni";
                languagefields[115] = "Juli";
                languagefields[116] = "August";
                languagefields[117] = "September";
                languagefields[118] = "Oktober";
                languagefields[119] = "November";
                languagefields[120] = "Dezember";
                languagefields[121] = "Diese E-Mail-Adresse ist bereits vorhanden.";
                languagefields[122] = "Anpassungen";
                languagefields[123] = "Ihre Daten werden geändert.";
                languagefields[124] = "Die Daten wurden hinzugefügt.";
                languagefields[125] = "Schulter";
                languagefields[126] = "Veränderungsrechnung";
                languagefields[127] = "Stornieren";
                languagefields[128] = "Konto löschen";
                languagefields[129] = "Sind Sie sicher, dass Sie den Namen Konto '";
                languagefields[130] = "' so entfernen?";
                languagefields[131] = "Monat";
                languagefields[132] = "Anzahl";
                languagefields[133] = "Löschen von Daten";
                languagefields[134] = "Zuerst wählen Sie eine Zeile in der Tabelle,"
                        + " um sie zu entfernen";
                languagefields[135] = "Thema";
                languagefields[136] = "Nachricht";
                languagefields[137] = "EXTRA";
                languagefields[138] = "Export in PDF";
                languagefields[139] = "Verfügbarkeit prüfen";
                languagefields[140] = "Export";
                languagefields[141] = "Datum aus";
                languagefields[142] = "Datum zu";
                languagefields[143] = "Woche";
                languagefields[144] = "Seite";
                languagefields[145] = "Zusätzlich";
                languagefields[146] = "Bestätigen";
                languagefields[147] = "Sind Sie sicher, dass Sie diese E-Mail an "
                        + "seine normale Nachricht zurücksetzen?";
                languagefields[148] = "Benutzername";
                languagefields[149] = "Informationen";
                languagefields[150] = "Warnung";
                languagefields[151] = "Verwenden Sie die Zeichen &quot; und ' \n"
                        + "eine E-Mail aus.";
                languagefields[152] = "Sie können bestimmte Wörter verwenden \n "
                        + "in einer E-Mail \n "
                        + "bedeuten nichts. Hier \n "
                        + "nbe löschte die Wörter, die Sie verwenden \n "
                        + "Die mit dem \n "
                        + "Sinn dahinter.";
                languagefields[153] = "Genesen";
                languagefields[154] = "Löschen von Daten";
                languagefields[155] = "Sie haben sich im Gepäck und Kundendaten "
                        + "dauerhaft zu entfernen! Sind Sie sicher?";
                languagefields[156] = "Entfernen";
                languagefields[157] = "Die Daten wurden erfolgreich gelöscht!";
                languagefields[158] = "Aktualisieren Sie die Seite, um die "
                        + "Datenbank zu aktualisieren";
                languagefields[159] = "E-Mail-Adresse ist ungültig";
                languagefields[160] = "PDF öffnet";
                languagefields[161] = "Hat nur eine PDF-Datei mit dem Benutzer "
                        + "geöffnet.";
                languagefields[162] = "Benutzer";
                languagefields[163] = "Unbekannt";
                languagefields[164] = "Wählen Sie die Sprache des Kunden";
                languagefields[165] = "Deutsch";
                languagefields[166] = "Kennwort";
                languagefields[167] = "Sie können die E-Mails, die über hier zu "
                        + "filtern.";
                languagefields[168] = "Es wurde ein Code verwendet, der nicht in "
                        + "diese E-Mail gehört. Klicken Sie auf das "
                        + "Informationssymbol, um zu sehen, welche Codes darin "
                        + "verwendet werden können.";
                languagefields[169] = "Sie haben das Zeichen ' verwendet.";
                languagefields[170] = "Sie haben das Zeichen ´ verwendet.";
                languagefields[171] = "Monat";
                languagefields[172] = "Jahr";
                languagefields[173] = "Sie können Ihre Statistiken durch hier filtern.";
                languagefields[174] = "Pflichtfelder.";
                languagefields[175] = "Gold";
                break;
        }
        return languagefields;
    }
}
