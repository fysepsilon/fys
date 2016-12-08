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
public class Status {
    @FXML private final SimpleStringProperty statusName;
    @FXML private final SimpleIntegerProperty amount;
    
    public Status(String status, int amount){
        this.statusName = new SimpleStringProperty(status);
        this.amount = new SimpleIntegerProperty(amount);
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName.get();
    }

    /**
     * @param statusNames the statusName to set
     */
    public void setStatusName(String statusNames) {
        statusName.set(statusNames);
    }

    /**
     * @return the amount
     */
    public Integer getAmount() {
        return amount.get();
    }

    /**
     * @param amounts the amount to set
     */
    public void setAmount(Integer amounts) {
        amount.set(amounts);
    }
    
    
}
