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

    public String getFirst_name() {
        return first_name.get();
    }

    public void setFirst_name(String first_namename) {
        first_name.set(first_namename);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surnamename) {
        surname.set(surnamename);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String typename) {
        type.set(typename);
    }

    public String getMail() {
        return mail.get();
    }

    public void setMail(String mailname) {
        mail.set(mailname);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String addressname) {
        address.set(addressname);
    }

    public String getResidence() {
        return residence.get();
    }

    public void setResidence(String residencename) {
        residence.set(residencename);
    }

    public String getZip_code() {
        return zip_code.get();
    }

    public void setZip_code(String zip_codename) {
        zip_code.set(zip_codename);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String countryname) {
        country.set(countryname);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phonename) {
        phone.set(phonename);
    }

    public String getLanguage_column() {
        return language_column.get();
    }

    public void setLanguagecolumn(String language_columnname) {
        language_column.set(language_columnname);
    }

    public int getPersonId() {
        return personId.get();
    }

    public void setPersonId(int personidname) {
        personId.set(personidname);
    }
}
