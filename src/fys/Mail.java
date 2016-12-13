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
 * @author Team Epsilon
 */
public class Mail {
    @FXML private final SimpleIntegerProperty mailid, page, type, language;
    @FXML private final SimpleStringProperty subject, message;

    public Mail(int mailidname, String subjectname, String messagename, int pagename, int typename, int languagename) {
        this.mailid = new SimpleIntegerProperty(mailidname);
        this.subject = new SimpleStringProperty(subjectname);
        this.message = new SimpleStringProperty(messagename);
        this.page = new SimpleIntegerProperty(pagename);
        this.type = new SimpleIntegerProperty(typename);
        this.language = new SimpleIntegerProperty(languagename);

    }

    public int getMailid() {
        return mailid.get();
    }

    public void setMailid(int mailidname) {
        mailid.set(mailidname);
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
    
    public int getPage() {
        return page.get();
    }

    public void setPage(int pagename) {
        page.set(pagename);
    }
    
    public int getType() {
        return type.get();
    }

    public void setType(int typename) {
        type.set(typename);
    }
    
    public int getLanguage() {
        return language.get();
    }

    public void setLanguage(int languagename) {
        language.set(languagename);
    }
}
