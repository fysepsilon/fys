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
public class Bagage {

        @FXML private final SimpleIntegerProperty id;
        @FXML private final SimpleStringProperty status;
        @FXML private final SimpleStringProperty type;
        @FXML private final SimpleStringProperty color;
        @FXML private final SimpleStringProperty brand;
        @FXML private final SimpleStringProperty date;
        @FXML private final SimpleStringProperty information;
        @FXML private SimpleStringProperty first_name;
        @FXML private SimpleStringProperty surname;
        @FXML private SimpleStringProperty address;
        @FXML private SimpleStringProperty residence;
        @FXML private SimpleStringProperty zipcode;
        @FXML private SimpleStringProperty country;
        @FXML private SimpleStringProperty phone;
        @FXML private SimpleStringProperty mail;
        @FXML private SimpleStringProperty labelnumber;
        @FXML private SimpleStringProperty flightnumber;
        @FXML private SimpleStringProperty destination;
        @FXML private SimpleStringProperty airport;
        
        
        public Bagage(Integer idname, String statusname, String typename, String colorname, 
                String brandname, String datename, String informationname, String firstnamename,
                 String surnamename, String addressname, String residencename, String zipcodename, 
                 String countryname, String phonename, String mailname, String labelnumbername, 
                 String flightnumbername, String destinationname, String airportname) {
            this.id = new SimpleIntegerProperty(idname);
            this.status = new SimpleStringProperty(statusname);
            this.type = new SimpleStringProperty(typename);
            this.color = new SimpleStringProperty(colorname);
            this.brand = new SimpleStringProperty(brandname);
            this.date = new SimpleStringProperty(datename);
            this.information = new SimpleStringProperty(informationname);
            this.first_name = new SimpleStringProperty(firstnamename);
            this.surname = new SimpleStringProperty(surnamename);
            this.address = new SimpleStringProperty(addressname);
            this.residence = new SimpleStringProperty(residencename);
            this.zipcode = new SimpleStringProperty(zipcodename);
            this.country = new SimpleStringProperty(countryname);
            this.phone = new SimpleStringProperty(phonename);
            this.mail = new SimpleStringProperty(mailname);
            this.labelnumber = new SimpleStringProperty(labelnumbername);
            this.flightnumber = new SimpleStringProperty(flightnumbername);
            this.destination = new SimpleStringProperty(destinationname);
            this.airport = new SimpleStringProperty(airportname);
            
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(Integer idname) {
            id.set(idname);
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String statusname) {
            status.set(statusname);
        }

        public String getType() {
            return type.get();
        }

        public void setStudentid(String typename) {
            type.set(typename);
        }

        public String getColor() {
            return color.get();
        }

        public void setCijfer(String colorname) {
            color.set(colorname);
        }

        public String getBrand() {
            return brand.get();
        }

        public void setBrand(String brandname) {
            brand.set(brandname);
        }
        
        public String getDate() {
            return date.get();
        }

        public void setDate(String datename) {
            date.set(datename);
        }
        
        public String getInformation() {
            return information.get();
        }

        public void setInformation(String informationname) {
            information.set(informationname);
        }

        /**
         * @return the first_name
         */
        public String getFirst_name() {
            return first_name.get();
        }

        /**
         * @param first_name the first_name to set
         */
        public void setFirst_name(String first_namename) {
            first_name.set(first_namename);
        }

        /**
         * @return the surname
         */
        public String getSurname() {
            return surname.get();
        }

        /**
         * @param surname the surname to set
         */
        public void setSurname(String surnamename) {
            surname.set(surnamename);
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address.get();
        }

        /**
         * @param address the address to set
         */
        public void setAddress(String addressname) {
            address.set(addressname);
        }

        /**
         * @return the residence
         */
        public String getResidence() {
            return residence.get();
        }

        /**
         * @param residence the residence to set
         */
        public void setResidence(String residencename) {
            residence.set(residencename);
        }

        /**
         * @return the zipcode
         */
        public String getZipcode() {
            return zipcode.get();
        }

        /**
         * @param zipcode the zipcode to set
         */
        public void setZipcode(String zipcodename) {
            zipcode.set(zipcodename);
        }

        /**
         * @return the country
         */
        public String getCountry() {
            return country.get();
        }

        /**
         * @param country the country to set
         */
        public void setCountry(String countryname) {
            country.set(countryname);
        }

        /**
         * @return the phone
         */
        public String getPhone() {
            return phone.get();
        }

        /**
         * @param phone the phone to set
         */
        public void setPhone(String phonename) {
            phone.set(phonename);
        }

        /**
         * @return the mail
         */
        public String getMail() {
            return mail.get();
        }

        /**
         * @param mail the mail to set
         */
        public void setMail(String mailname) {
            mail.set(mailname);
        }

        /**
         * @return the labelnumber
         */
        public String getLabelnumber() {
            return labelnumber.get();
        }

        /**
         * @param labelnumber the labelnumber to set
         */
        public void setLabelnumber(String labelnumbername) {
            labelnumber.set(labelnumbername);
        }

        /**
         * @return the flightnumber
         */
        public String getFlightnumber() {
            return flightnumber.get();
        }

        /**
         * @param flightnumber the flightnumber to set
         */
        public void setFlightnumber(String flightnumbername) {
            flightnumber.set(flightnumbername);
        }

        /**
         * @return the destination
         */
        public String getDestination() {
            return destination.get();
        }

        /**
         * @param destination the destination to set
         */
        public void setDestination(String destinationname) {
            destination.set(destinationname);
        }

        /**
         * @return the airport
         */
        public String getAirport() {
            return airport.get();
        }

        /**
         * @param airport the airport to set
         */
        public void setAirport(String airportname) {
            airport.set(airportname);
        }
        

    }