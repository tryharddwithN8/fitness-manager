package com.fitness.model.fitness;

public class Progress {
    private String id;
    private String courseId;
    private String memberId;
    private int progress;
    private String note;

    public Progress() {

    }

    public Progress(String id, String courseId, String memberId, int progress, String note) {
        this.id = id;
        this.courseId = courseId;
        this.memberId = memberId;
        this.progress = progress;
        this.note = note;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", progress=" + progress +
                ", note='" + note + '\'' +
                '}';
    }
}