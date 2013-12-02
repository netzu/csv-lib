package csv.read.bean.samples;

import csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/8/13
 * Time: 11:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanClassWithRawTypes {

    @CSVProperty(name = "id")
    private int id;

    @CSVProperty(name = "email")
    private String mail;

    @CSVProperty(name = "age")
    private long age;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
