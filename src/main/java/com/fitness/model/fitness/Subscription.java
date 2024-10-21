package com.fitness.model.fitness;

public class Subscription {
    private String id;
    private String memberId;
    private String membername;
    private String courseId;
    private String startDate;
    private String endDate;
    private double fee;
    private String status;

    public Subscription() {

    }

    public Subscription(String id, String memberId,String membername, String courseId, String startDate, String endDate, double fee, String status) {
        this.id = id;
        this.memberId = memberId;
        this.membername=membername;
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fee = fee;
        this.status = status;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + membername + '\'' +
                ", courseId='" + courseId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", fee=" + fee +
                ", status='" + status + '\'' +
                '}';
    }
}