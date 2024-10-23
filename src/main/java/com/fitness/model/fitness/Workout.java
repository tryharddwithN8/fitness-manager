package com.fitness.model.fitness;

public class Workout {
    private String id;
    private String courseId;
    private String memberId;
    private String date;
    private String startTime;
    private String endTime;
    private String description;
    private String workout_name;
    private int duration_minustes;

    public Workout() {

    }

    public Workout(String workout_name, int duration_minustes,String id, String courseId, String memberId, String date, String startTime, String endTime, String description) {
        this.id = id;
        this.courseId = courseId;
        this.memberId = memberId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.duration_minustes=duration_minustes;
        this.workout_name=workout_name;
    }

    public int getDuration_minustes() {
        return duration_minustes;
    }

    public void setDuration_minustes(int duration_minustes) {
        this.duration_minustes = duration_minustes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String status) {
        this.description = status;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + workout_name  + '\'' +
                ", memberId='" + memberId + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration time='" + duration_minustes + '\'' +
                ", status='" + description + '\'' +
                '}';
    }
}
