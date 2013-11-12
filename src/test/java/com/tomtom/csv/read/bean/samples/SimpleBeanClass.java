package com.tomtom.csv.read.bean.samples;

import com.tomtom.csv.read.bean.CSVProperty;

public class SimpleBeanClass {

    @CSVProperty(name = "id")
    private Integer identifier;

    @CSVProperty(name = "age")
    private Long longAge;

    @CSVProperty(name = "email")
    private String mail;


    public SimpleBeanClass() {

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
