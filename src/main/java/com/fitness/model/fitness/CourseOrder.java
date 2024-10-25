package com.fitness.model.fitness;

public class CourseOrder extends Course{
    public String username;

    public CourseOrder() {
    }

    public CourseOrder(String id, String coachName, String name, String description, String coachId, String schedule, int maxParticipants, int currentParticipants, double fee, double discount, String username) {
        super(id, coachName, name, description, coachId, schedule, maxParticipants, currentParticipants, fee, discount);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
