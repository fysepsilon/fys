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
 * @author Team Epsilon
 */
public class Bagage {

    @FXML
    private final SimpleIntegerProperty id, realId, lostAndFoundID, personID;
    @FXML
    private final SimpleStringProperty status, type, color, brand;
    @FXML
    private SimpleStringProperty picture;
    @FXML
    private final SimpleStringProperty date;
    @FXML
    private final SimpleStringProperty information;
    @FXML
    private final SimpleStringProperty firstName;
    @FXML
    private final SimpleStringProperty surName;
    @FXML
    private final SimpleStringProperty address;
    @FXML
    private final SimpleStringProperty residence;
    @FXML
    private final SimpleStringProperty zipcode;
    @FXML
    private final SimpleStringProperty country;
    @FXML
    private final SimpleStringProperty phone;
    @FXML
    private final SimpleStringProperty mail;
    @FXML
    private final SimpleStringProperty labelNumber;
    @FXML
    private final SimpleStringProperty flightNumber;
    @FXML
    private final SimpleStringProperty destination;
    @FXML
    private final SimpleStringProperty airportFound;
    @FXML
    private final SimpleStringProperty airportLost;
    @FXML
    private final SimpleStringProperty tableFrom;
    @FXML
    private SimpleStringProperty time;

    /**
     *
     * @param idValue set value to id.
     * @param statusValue set value to status.
     * @param typeValue set value to type.
     * @param colorValue set value to color.
     * @param brandValue set value to brand.
     * @param dateValue set value to date.
     * @param informationValue set value to information.
     * @param firstNameValue set value to first name.
     * @param surNameValue set value to surName.
     * @param addressValue set value to address.
     * @param residenceValue set value to residence.
     * @param zipcodeValue set value to zip code.
     * @param countryValue set value to country.
     * @param phoneValue set value to phone.
     * @param mailValue set value to mail.
     * @param labelNumberValue set value to label number.
     * @param flightNumberValue set value to flight number.
     * @param destinationValue set value to destination.
     * @param airportFoundValue set value to airport found.
     * @param airportLostValue set value to airport lost.
     * @param tableFromValue set value to table from.
     * @param lostAndFoundIdValue set value to lost and found id.
     * @param personIdValue set value to person id.
     * @param realIdValue set value to real id.
     */
    public Bagage(Integer idValue, String statusValue, String typeValue, String colorValue,
            String brandValue, String pictureValue, String dateValue, String informationValue, String firstNameValue,
            String surNameValue, String addressValue, String residenceValue, String zipcodeValue,
            String countryValue, String phoneValue, String mailValue, String labelNumberValue,
            String flightNumberValue, String destinationValue, String airportFoundValue, String airportLostValue,
            String tableFromValue, Integer lostAndFoundIdValue, Integer personIdValue, Integer realIdValue) {
        this.id = new SimpleIntegerProperty(idValue);
        this.status = new SimpleStringProperty(statusValue);
        this.type = new SimpleStringProperty(typeValue);
        this.color = new SimpleStringProperty(colorValue);
        this.brand = new SimpleStringProperty(brandValue);
        this.picture = new SimpleStringProperty(pictureValue);
        this.date = new SimpleStringProperty(dateValue);
        this.information = new SimpleStringProperty(informationValue);
        this.firstName = new SimpleStringProperty(firstNameValue);
        this.surName = new SimpleStringProperty(surNameValue);
        this.address = new SimpleStringProperty(addressValue);
        this.residence = new SimpleStringProperty(residenceValue);
        this.zipcode = new SimpleStringProperty(zipcodeValue);
        this.country = new SimpleStringProperty(countryValue);
        this.phone = new SimpleStringProperty(phoneValue);
        this.mail = new SimpleStringProperty(mailValue);
        this.labelNumber = new SimpleStringProperty(labelNumberValue);
        this.flightNumber = new SimpleStringProperty(flightNumberValue);
        this.destination = new SimpleStringProperty(destinationValue);
        this.airportFound = new SimpleStringProperty(airportFoundValue);
        this.airportLost = new SimpleStringProperty(airportLostValue);
        this.tableFrom = new SimpleStringProperty(tableFromValue);
        this.lostAndFoundID = new SimpleIntegerProperty(lostAndFoundIdValue);
        this.personID = new SimpleIntegerProperty(personIdValue);
        this.realId = new SimpleIntegerProperty(realIdValue);
    }

    /**
     *
     * @param dateValue set value to date.
     * @param timeValue set value to time.
     * @param colorValue set value to color.
     * @param brandValue set value to brand. Make other variables empty.
     */
    public Bagage(String dateValue, String timeValue, String colorValue, String brandValue) {
        this.id = new SimpleIntegerProperty(0);
        this.realId = new SimpleIntegerProperty(0);
        this.lostAndFoundID = new SimpleIntegerProperty(0);
        this.personID = new SimpleIntegerProperty(0);
        this.status = new SimpleStringProperty("");
        this.picture = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.information = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.surName = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.residence = new SimpleStringProperty("");
        this.zipcode = new SimpleStringProperty("");
        this.country = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.mail = new SimpleStringProperty("");
        this.labelNumber = new SimpleStringProperty("");
        this.flightNumber = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.airportFound = new SimpleStringProperty("");
        this.airportLost = new SimpleStringProperty("");
        this.tableFrom = new SimpleStringProperty("");

        this.date = new SimpleStringProperty(dateValue);
        this.time = new SimpleStringProperty(timeValue);
        this.color = new SimpleStringProperty(colorValue);
        this.brand = new SimpleStringProperty(brandValue);
    }

    public Bagage(String statusValue, String typeValue, String colorValue,
            String brandValue, String pictureValue, String informationValue, 
            String firstNameValue, String surNameValue) {
        this.id = new SimpleIntegerProperty(0);
        this.realId = new SimpleIntegerProperty(0);
        this.lostAndFoundID = new SimpleIntegerProperty(0);
        this.personID = new SimpleIntegerProperty(0);
        this.address = new SimpleStringProperty("");
        this.residence = new SimpleStringProperty("");
        this.zipcode = new SimpleStringProperty("");
        this.country = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.mail = new SimpleStringProperty("");
        this.labelNumber = new SimpleStringProperty("");
        this.flightNumber = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.airportFound = new SimpleStringProperty("");
        this.airportLost = new SimpleStringProperty("");
        this.tableFrom = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");

        this.status = new SimpleStringProperty(statusValue);
        this.type = new SimpleStringProperty(typeValue);
        this.color = new SimpleStringProperty(colorValue);
        this.brand = new SimpleStringProperty(brandValue);
        this.picture = new SimpleStringProperty(pictureValue);
        this.information = new SimpleStringProperty(informationValue);
        this.firstName = new SimpleStringProperty(firstNameValue);
        this.surName = new SimpleStringProperty(surNameValue);

    }

    /**
     *
     * @return the value of id.
     */
    public Integer getId() {
        return id.get();
    }

    /**
     *
     * @param idValue set value to id.
     */
    public void setId(Integer idValue) {
        id.set(idValue);
    }

    /**
     *
     * @return the value of status.
     */
    public String getStatus() {
        return status.get();
    }

    /**
     *
     * @param statusValue set value to status.
     */
    public void setStatus(String statusValue) {
        status.set(statusValue);
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
     * @param typeValue set value to studentid.
     */
    public void setStudentid(String typeValue) {
        type.set(typeValue);
    }

    /**
     *
     * @return value of color.
     */
    public String getColor() {
        return color.get();
    }

    /**
     *
     * @param colorValue set value to color.
     */
    public void setCijfer(String colorValue) {
        color.set(colorValue);
    }

    /**
     *
     * @return value of brand.
     */
    public String getBrand() {
        return brand.get();
    }

    /**
     *
     * @param brandValue set value of brand.
     */
    public void setBrand(String brandValue) {
        brand.set(brandValue);
    }

    /**
     *
     * @return value of date.
     */
    public String getDate() {
        return date.get();
    }

    /**
     *
     * @param dateValue set value to date.
     */
    public void setDate(String dateValue) {
        date.set(dateValue);
    }

    /**
     *
     * @return value of time.
     */
    public String getTime() {
        return time.get();
    }

    /**
     *
     * @param timeValue set value to time
     */
    public void setTime(String timeValue) {
        time.set(timeValue);
    }

    /**
     *
     * @return the value of information.
     */
    public String getInformation() {
        return information.get();
    }

    /**
     *
     * @param informationValue set value to information.
     */
    public void setInformation(String informationValue) {
        information.set(informationValue);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * @param firstNameValue the firstName to set
     */
    public void setFirstName(String firstNameValue) {
        firstName.set(firstNameValue);
    }

    /**
     * @return the surName
     */
    public String getSurName() {
        return surName.get();
    }

    /**
     * @param surNameValue the surName to set
     */
    public void setSurName(String surNameValue) {
        surName.set(surNameValue);
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * @param addressValue the address to set
     */
    public void setAddress(String addressValue) {
        address.set(addressValue);
    }

    /**
     * @return the residence
     */
    public String getResidence() {
        return residence.get();
    }

    /**
     * @param residenceValue the residence to set
     */
    public void setResidence(String residenceValue) {
        residence.set(residenceValue);
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode.get();
    }

    /**
     * @param zipcodeValue the zipcode to set
     */
    public void setZipcode(String zipcodeValue) {
        zipcode.set(zipcodeValue);
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country.get();
    }

    /**
     * @param countryValue the country to set
     */
    public void setCountry(String countryValue) {
        country.set(countryValue);
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * @param phoneValue the phone to set
     */
    public void setPhone(String phoneValue) {
        phone.set(phoneValue);
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail.get();
    }

    /**
     * @param mailValue the mail to set
     */
    public void setMail(String mailValue) {
        mail.set(mailValue);
    }

    /**
     * @return the labelnumber
     */
    public String getLabelNumber() {
        return labelNumber.get();
    }

    /**
     * @param labelNumberValue the labelnumber to set
     */
    public void setLabelNumber(String labelNumberValue) {
        labelNumber.set(labelNumberValue);
    }

    /**
     * @return the flightnumber
     */
    public String getFlightNumber() {
        return flightNumber.get();
    }

    /**
     * @param flightNumberValue the flightnumber to set
     */
    public void setFlightNumber(String flightNumberValue) {
        flightNumber.set(flightNumberValue);
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination.get();
    }

    /**
     * @param destinationValue the destination to set
     */
    public void setDestination(String destinationValue) {
        destination.set(destinationValue);
    }

    /**
     * @return the realid
     */
    public Integer getRealid() {
        return realId.get();
    }

    /**
     * @param realIdValue the realid to set
     */
    public void setRealid(Integer realIdValue) {
        realId.set(realIdValue);
    }

    /**
     * @return the lostAndFoundID
     */
    public Integer getLostAndFoundID() {
        return lostAndFoundID.get();
    }

    /**
     * @param lostAndFoundIdValue the lostAndFoundID to set
     */
    public void setLostAndFoundID(Integer lostAndFoundIdValue) {
        lostAndFoundID.set(lostAndFoundIdValue);
    }

    /**
     * @return the personID
     */
    public Integer getPersonID() {
        return personID.get();
    }

    /**
     * @param personIdValue the personID to set
     */
    public void setPersonID(Integer personIdValue) {
        personID.set(personIdValue);
    }

    /**
     * @return the airportFound
     */
    public String getAirportFound() {
        return airportFound.get();
    }

    /**
     * @param airportFoundValue the airportFound to set
     */
    public void setAirportFound(String airportFoundValue) {
        airportFound.set(airportFoundValue);
    }

    /**
     * @return the airportLost
     */
    public String getAirportLost() {
        return airportLost.get();
    }

    /**
     * @param airportLostValuet the airportLost to set
     */
    public void setAirportLost(String airportLostValue) {
        airportLost.set(airportLostValue);
    }

    /**
     * @return the tableFrom
     */
    public String getTableFrom() {
        return tableFrom.get();
    }

    /**
     * @param tableFromValue the tableFrom to set
     */
    public void setTableFrom(String tableFromValue) {
        tableFrom.set(tableFromValue);
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture.get();
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(String pictureValue) {
        picture.set(pictureValue);
    }
}
