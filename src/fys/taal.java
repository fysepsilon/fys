/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

/**
 *
 * @author Paras
 */
public class taal {
    private final int amountOfLanguageFields = 4;
    
    public String[] getLanguage(int language){
        //if(language == 0)
        String[] languagefields = new String[amountOfLanguageFields];
        languagefields[0] = "VERMISTE BAGAGE REGISTREREN";
        languagefields[1] = "GEVONDEN BAGAGE REGISTREREN";
        languagefields[2] = "BAGAGE DATABASE";
        languagefields[3] = "ACCOUNTS";
        languagefields[4] = "STATISTIEKEN";
        return languagefields;
    }
}
