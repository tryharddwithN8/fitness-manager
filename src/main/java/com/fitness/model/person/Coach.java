package com.fitness.model.person;

import java.time.LocalDateTime;

public class Coach extends Person {
    private String username;
    private String password;
    private String courseTeaching;
    private int experience;
    private String role;

    public Coach() {
        super();
    }

    public Coach(String id, String name, String phone, String email, String address, LocalDateTime dob, String username, String password, String courseTeaching, int experience, String role) {
        super(id, name, phone, email, address, dob);
        this.username = username;
        this.password = password;
        this.courseTeaching = courseTeaching;
        this.experience = experience;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourseTeaching() {
        return courseTeaching;
    }

    public void setCourseTeaching(String courseTeaching) {
        this.courseTeaching = courseTeaching;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courseTeaching='" + courseTeaching + '\'' +
                ", experience=" + experience +
                ", role='" + role + '\'' +
                '}';
    }
}
