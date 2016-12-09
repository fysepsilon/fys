/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;

/**
 *
 * @author Paras
 */
public class Mail {
    @FXML private final SimpleIntegerProperty mailid;
    @FXML private final SimpleStringProperty subject, message;

    public Mail(int mailidname, String subjectname, String messagename) {
        this.mailid = new SimpleIntegerProperty(mailidname);
        this.subject = new SimpleStringProperty(subjectname);
        this.message = new SimpleStringProperty(messagename);
    }

    public int getMailid() {
        return mailid.get();
    }

    public void setMailid(String mailidname) {
        subject.set(mailidname);
    }
    
    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subjectname) {
        subject.set(subjectname);
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String messagename) {
        message.set(messagename);
    }
}
