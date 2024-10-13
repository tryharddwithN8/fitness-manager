package com.fitness.model.person;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User extends Person{
    private String username;
    private String password;
    private double balance;
    private int quantityCourse;
    private ArrayList<String> courseList;
    private String role;

    public User() {
        super();
    }

    public User(String id, String username, String password ,String email, String role, String address) {
        super(id, email, address);
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.quantityCourse = 0;
        this.courseList = null;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
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