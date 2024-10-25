package com.fitness.model.person;

import java.time.LocalDate;

public abstract class Person {
    private String id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private LocalDate dob;

    public Person() {

    }

    public Person(String id, String email, String address) {
        this.id = id;
        this.fullName = null;
        this.phone = null;
        this.email = email;
        this.address = address;
        this.dob = null;
    }

    public Person(String id, String address, LocalDate dob, String email, String fullName, String phone) {
        this.id = id;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
