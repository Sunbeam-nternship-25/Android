package com.vaishnavidayma.studentfeedback.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {

    private int student_id ;
    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String password;
    private int prn_no;
    private String group_name;
    private  String course_name;

    public Student(int student_id, String firstName, String lastName, String email, String password, int prn_no, String group_name, String course_name) {
        this.student_id = student_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.prn_no = prn_no;
        this.group_name = group_name;
        this.course_name = course_name;
    }

    public Student() {
    }



    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrn_no() {
        return prn_no;
    }

    public void setPrn_no(int prn_no) {
        this.prn_no = prn_no;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
