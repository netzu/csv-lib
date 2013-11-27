package com.tomtom.csv.read.bean.samples;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/27/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithNoAnnotatedFields {


    private Integer identifier;

    private Long longAge;

    private String mail;


    public BeanWithNoAnnotatedFields() {

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
