package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

public class Student{
    public int id;
    public String firstName;
    public String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // needed for input field on html pages (in order to serve the right format)
    public Date enrollmentDate;
    public String cpr;

    public Student(int id, String firstName, String lastName, Date enrollmentDate, String cpr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollmentDate = enrollmentDate;
        this.cpr = cpr;
    }

    public Student() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", cpr='" + cpr + '\'' +
                '}';
    }
}
