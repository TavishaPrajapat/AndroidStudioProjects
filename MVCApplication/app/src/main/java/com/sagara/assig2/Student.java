package com.sagara.assig2;

public class Student {
    private String studentName;
    private String studentID;
    private String course;

    // Constructor
    public Student(String studentName, String studentID, String course) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.course = course;
    }

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}