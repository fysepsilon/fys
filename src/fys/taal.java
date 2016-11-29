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
    private final int amountOfLanguageFields = 200;
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
        
        if(language == 0){ // Engels
            languagefields[0] = "REGISTER MISSING LUGGAGE";
            languagefields[1] = "REGISTER FOUND LUGGAGE";
            languagefields[2] = "LUGGAGE DATABASE";
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
            languagefields[93] = "Fields are left blank!";
            languagefields[94] = "E-mail already exists!"; 
            languagefields[95] = "Register missing luggage";   
            languagefields[96] = "Register found luggage";   
            languagefields[97] = "Home";   
            languagefields[98] = "Accounts";   
            languagefields[99] = "Statistics";   
            languagefields[100] = "Luggage database";   
            languagefields[101] = "Login";   
            languagefields[102] = "Settings";   
        } else if(language == 1) { // Nederlands
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
            languagefields[93] = "Velden zijn leeggelaten!";
            languagefields[94] = "E-mailadres bestaat al!"; 
            languagefields[95] = "Vermiste bagage registreren";   
            languagefields[96] = "Gevonden bagage registreren";   
            languagefields[97] = "Home";   
            languagefields[98] = "Accounts";   
            languagefields[99] = "Statistieken";   
            languagefields[100] = "Bagage database";   
            languagefields[101] = "Login";   
            languagefields[102] = "Instellingen";   
        } else if(language == 2) { // Spaans
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
            languagefields[93] = "Los campos se dejan en blanco!";  
            languagefields[94] = "Correo electrónico ya existe!";    
            languagefields[95] = "Registro equipaje perdido";   
            languagefields[96] = "Registro encontró equipaje";   
            languagefields[97] = "Casa";   
            languagefields[98] = "Cuentas";   
            languagefields[99] = "Estadística";   
            languagefields[100] = "Base de datos de equipaje";   
            languagefields[101] = "Login";   
            languagefields[102] = "Ajustes";   
        } else { //Turks
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
            languagefields[93] = "Alanlar boş bırakılır!";  
            languagefields[94] = "Posta zaten var!";       
            languagefields[95] = "Kayıt kayıp bagaj";   
            languagefields[96] = "Kayıt bagaj bulundu";   
            languagefields[97] = "Ev";   
            languagefields[98] = "Hesapları";   
            languagefields[99] = "Istatistik";   
            languagefields[100] = "Bagaj veritabanı";   
            languagefields[101] = "Giriş";   
            languagefields[102] = "Ayarlar";   
        }
        return languagefields;
    }
}
