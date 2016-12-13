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
    @FXML private final SimpleIntegerProperty mailid;
    @FXML private final SimpleStringProperty subject, message, type, language, 
            page;

    public Mail(int mailidname, String subjectname, String messagename, 
            String pagename, String typename, String languagename) {
        this.mailid = new SimpleIntegerProperty(mailidname);
        this.subject = new SimpleStringProperty(subjectname);
        this.message = new SimpleStringProperty(messagename);
        this.page = new SimpleStringProperty(pagename);
        this.type = new SimpleStringProperty(typename);
        this.language = new SimpleStringProperty(languagename);

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
    
    public String getPage() {
        return page.get();
    }

    public void setPage(String pagename) {
        page.set(pagename);
    }
    
    public String getType() {
        return type.get();
    }

    public void setType(String typename) {
        type.set(typename);
    }
    
    public String getLanguage() {
        return language.get();
    }

    public void setLanguage(String languagename) {
        language.set(languagename);
    }
}
