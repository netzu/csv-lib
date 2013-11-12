package com.tomtom.csv.read.bean.samples;

import com.tomtom.csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/10/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoEmptyContructorBean {


    @CSVProperty(name = "id")
    private Integer identifier;

    @CSVProperty(name = "age")
    private Long longAge;

    @CSVProperty(name = "email")
    private String mail;


    public NoEmptyContructorBean(final String dummyParam) {

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
