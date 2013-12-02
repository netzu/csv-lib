package csv.read.bean.samples;

import csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/10/13
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleBeanClass2 {
    @CSVProperty(name = "id")
    private Integer identifier;

    @CSVProperty(name = "age")
    private Long longAge;

    @CSVProperty(name = "email")
    private String mail;

    @CSVProperty(name = "message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleBeanClass2() {

    }

    public Long getLongAge() {
        return longAge;
    }

    public void setLongAge(Long longAge) {
        this.longAge = longAge;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
