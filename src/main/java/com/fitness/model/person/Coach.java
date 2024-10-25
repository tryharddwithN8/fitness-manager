package com.fitness.model.person;

import java.time.LocalDateTime;

public class Coach extends Person {
    private String username;
    private String password;
    private String courseTeaching;
    private String experience;
    private String role;
    private String bio;
    private String linkImg;
    public Coach() {
        super();
    }

    public Coach(String id, String name, String phone,String linkImg, String email, String address, LocalDateTime dob, String username, String password, String courseTeaching, String experience, String role, String bio) {
        super(id, email, address);
        this.username = username;
        this.password = password;
        this.courseTeaching = courseTeaching;
        this.experience = experience;
        this.role = role;
        this.bio=bio;
        this.linkImg=linkImg;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
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
                ", name='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courseTeaching='" + courseTeaching + '\'' +
                ", experience=" + experience +
                ", role='" + role + '\'' +
                ", bio='" + bio + '\'' +
                ", linkImg='" + linkImg + '\'' +
                '}';
    }
}
