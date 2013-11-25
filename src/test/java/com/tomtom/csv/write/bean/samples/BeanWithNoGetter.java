package com.tomtom.csv.write.bean.samples;

import com.tomtom.csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/25/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithNoGetter {

    @CSVProperty(name = "name")
    private String name;

    @CSVProperty(name = "id")
    private long id;

    @CSVProperty(name = "age")
    private Integer age;


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
