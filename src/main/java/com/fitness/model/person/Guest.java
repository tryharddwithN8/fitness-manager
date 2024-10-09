package com.fitness.model.person;

import java.time.LocalDateTime;

public class Guest extends Person {
    public Guest() {
        super();
    }

    public Guest(String id, String name, String phone, String email, String address, LocalDateTime dob) {
        super(id, name, phone, email, address, dob);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", dob=" + getDob() +
                '}';
    }
}
