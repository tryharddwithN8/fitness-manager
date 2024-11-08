package com.fitness.model.person;

import java.time.LocalDate;
import java.util.ArrayList;

public class User extends Person {
    private String username;
    private String password;
    private double balance;
    private int quantityCourse;
    private ArrayList<String> courseList;
    private String role;
    private String createDate;
    private Boolean isActive;

    public User() {
        super();
    }

    public User(String id, String username, String password, String email, String role, String address) {
        super(id, email, address);
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.quantityCourse = 0;
        this.courseList = null;
        this.isActive = false;
        this.role = role;
    }

    public User(String id, String fullName, LocalDate dob, String email, String phone, String address) {
        super(id,address, dob, email, fullName, phone);
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getQuantityCourse() {
        return quantityCourse;
    }

    public void setQuantityCourse(int quantityCourse) {
        this.quantityCourse = quantityCourse;
    }

    public ArrayList<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<String> courseList) {
        this.courseList = courseList;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addCourse(String course) {
        courseList.add(course);
    }

    public void removeCourse(String course) {
        courseList.remove(course);
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void increaseQuantityCourse() {
        quantityCourse++;
    }

    public void decreaseQuantityCourse() {
        quantityCourse--;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public String getIsActiveString() {
        return isActive != null && isActive ? "true" : "false";
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", dob=" + getDob() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", quantityCourse=" + quantityCourse +
                ", courseList=" + courseList +
                ", role='" + role + '\'' +
                '}';
    }
}