package csv.read.bean.samples;

import csv.read.bean.CSVProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mht
 * Date: 11/10/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedSimpleBean extends SimpleBeanClass {

    @CSVProperty(name = "message")
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
