package com.tomtom.csv.read.bean.samples;


import com.tomtom.csv.read.bean.CSVProperty;
import com.vividsolutions.jts.geom.Geometry;

public class SimpleBeanWithGeometry {


    @CSVProperty(name = "id")
    private Integer identifier;

    @CSVProperty(name = "age")
    private Long longAge;

    @CSVProperty(name = "email")
    private String mail;


    @CSVProperty(name = "geometry")
    private Geometry geometry;

    public SimpleBeanWithGeometry() {

    }


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
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
