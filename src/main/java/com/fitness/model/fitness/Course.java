package com.fitness.model.fitness;

public class Course {
    private String id;
    private String name;
    private String description;
    private String coachId,coachName;
    private String schedule;
    private int maxParticipants;
    private int currentParticipants;
    private double fee,discount;

    public Course() {

    }

    public Course(String id, String coachName, String name, String description, String coachId, String schedule, int maxParticipants, int currentParticipants, double fee,double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coachId = coachId;
        this.schedule = schedule;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.fee = fee;
        this.discount=discount;
        this.coachName=coachName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coachId='" + coachId + '\'' +
                ", schedule='" + schedule + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", currentParticipants=" + currentParticipants +
                ", fee=" + fee +
                ", discount"+discount+
                ", coachName"+coachName+
                '}';
    }
}
