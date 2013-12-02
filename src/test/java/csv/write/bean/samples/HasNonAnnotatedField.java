package csv.write.bean.samples;

import csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/27/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class HasNonAnnotatedField {

    @CSVProperty(name = "name")
    private String name;

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
