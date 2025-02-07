package org.example;

import java.io.Serializable;

public class ScheduleCenter implements Serializable {
    private String date;
    private String centerName;

    // constructor
    public ScheduleCenter(String date, String centerName) {
        this.date = date;
        this.centerName = centerName;
    }

    // getters
    public String getDate() {
        return date;
    }
    public String getCenterName() {
        return centerName;
    }

    // setters
    public void setDate(String date) {
        this.date = date;
    }
    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
}
