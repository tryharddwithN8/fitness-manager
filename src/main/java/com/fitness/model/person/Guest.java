package com.fitness.model.person;

import java.time.LocalDateTime;

public class Guest{
    private String phone;

    public Guest() {

    }

    public Guest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
