package com.tomtom.csv.write.bean.samples;

import com.tomtom.csv.read.bean.CSVProperty;

/**
 * Sample bean for test purposes.
 */
public class SimpleBean {


    @CSVProperty(name = "name")
    private String name;

    @CSVProperty(name = "id")
    private long id;

    @CSVProperty(name = "age")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
