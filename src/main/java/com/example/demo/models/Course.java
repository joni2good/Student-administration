package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Course {
    private int id;
    private String loc;
    private String cName;
    private int ects;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    private Date startDate;

    public Course(int id, String loc, String cName, int ects, Date startDate) {
        this.id = id;
        this.loc = loc;
        this.cName = cName;
        this.ects = ects;
        this.startDate = startDate;
    }

    public Course(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", loc='" + loc + '\'' +
                ", cName='" + cName + '\'' +
                ", ects=" + ects +
                ", startDate=" + startDate +
                '}';
    }
}
