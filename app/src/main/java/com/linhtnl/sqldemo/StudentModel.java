package com.linhtnl.sqldemo;

public class StudentModel {
    private String StudentCode;
    private String fullName;
    private String dateOfBirth;

    //Constructor

    public StudentModel() {
        StudentCode = fullName = "";
    }

    public StudentModel(String studentCode, String fullName, String dateOfBirth) {
        StudentCode = studentCode;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStudentCode(String studentCode) {
        StudentCode = studentCode;
    }

    @Override
    public String toString() {
        return  StudentCode + " - " + fullName + " - " + dateOfBirth;

    }
}
