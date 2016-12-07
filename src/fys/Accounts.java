/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

/**
 *
 * @author Paras
 */
public class Accounts {

    @FXML private final SimpleStringProperty first_name, surname, type, mail, address,
            residence, zip_code, country, phone, language_column;
    @FXML private final SimpleIntegerProperty personId;
    
    /**
     * 
     * @param first_namename set value to first_name.
     * @param surnamename set value to surname.
     * @param typename set value to type.
     * @param mailname set value to mail.
     * @param addressname set value to address.
     * @param residencename set value to residence.
     * @param zip_codename set value to zipcdoe.
     * @param countryname set value to country.
     * @param phonename set value to phone.
     * @param language_columnname set valueto language.
     * @param personidname set value to personid.
     */
    public Accounts(String first_namename, String surnamename,
            String typename, String mailname, String addressname,
            String residencename, String zip_codename, String countryname,
            String phonename, String language_columnname, int personidname) {
        this.first_name = new SimpleStringProperty(first_namename);
        this.surname = new SimpleStringProperty(surnamename);
        this.type = new SimpleStringProperty(typename);
        this.mail = new SimpleStringProperty(mailname);
        this.address = new SimpleStringProperty(addressname);
        this.residence = new SimpleStringProperty(residencename);
        this.zip_code = new SimpleStringProperty(zip_codename);
        this.country = new SimpleStringProperty(countryname);
        this.phone = new SimpleStringProperty(phonename);
        this.language_column = new SimpleStringProperty(language_columnname);
        this.personId = new SimpleIntegerProperty(personidname);
    }
    
    /**
     * 
     * @return the value of firstname.
     */
    public String getFirst_name() {
        return first_name.get();
    }
    
    /**
     * 
     * @param first_namename set value to firstname. 
     */
    public void setFirst_name(String first_namename) {
        first_name.set(first_namename);
    }
    
    /**
     * 
     * @return the value of surname.
     */
    public String getSurname() {
        return surname.get();
    }

    /**
     * 
     * @param surnamename set value to surname.
     */
    public void setSurname(String surnamename) {
        surname.set(surnamename);
    }
    
    /**
     * 
     * @return the value of type.
     */
    public String getType() {
        return type.get();
    }   
    
    /**
     * 
     * @param typename set value to type.
     */
    public void setType(String typename) {
        type.set(typename);
    }
    
    /**
     * 
     * @return the value of mail.
     */
    public String getMail() {
        return mail.get();
    }
    
    /**
     *
     * @param mailname set value to mail.
     */
    public void setMail(String mailname) {
        mail.set(mailname);
    }
    
    /**
     * 
     * @return the value of address.
     */
    public String getAddress() {
        return address.get();
    }
    
    /**
     * 
     * @param addressname set value to address.
     */
    public void setAddress(String addressname) {
        address.set(addressname);
    }
    
    /**
     * 
     * @return the value of residence.
     */
    public String getResidence() {
        return residence.get();
    }

    /**
     * 
     * @param residencename set value to residence.
     */
    public void setResidence(String residencename) {
        residence.set(residencename);
    }
    
    /**
     * 
     * @return the value of zipcode.
     */
    public String getZip_code() {
        return zip_code.get();
    }
    
    /**
     * 
     * @param zip_codename set value to zipcode.
     */
    public void setZip_code(String zip_codename) {
        zip_code.set(zip_codename);
    }
    
    /**
     * 
     * @return the value of country.
     */
    public String getCountry() {
        return country.get();
    }
    
    /**
     * 
     * @param countryname set value to country.
     */
    public void setCountry(String countryname) {
        country.set(countryname);
    }
    
    /**
     * 
     * @return the value of phone.
     */
    public String getPhone() {
        return phone.get();
    }
    
    /**
     * 
     * @param phonename set value to phone.
     */
    public void setPhone(String phonename) {
        phone.set(phonename);
    }
    
    /**
     * 
     * @return the value of language.
     */
    public String getLanguage_column() {
        return language_column.get();
    }
    
    /**
     * 
     * @param language_columnname set value of language.
     */
    public void setLanguagecolumn(String language_columnname) {
        language_column.set(language_columnname);
    }
    
    /**
     * 
     * @return the value of personid.
     */
    public int getPersonId() {
        return personId.get();
    }
    
    /**
     * 
     * @param personidname set the value of personid.
     */
    public void setPersonId(int personidname) {
        personId.set(personidname);
    }
}
